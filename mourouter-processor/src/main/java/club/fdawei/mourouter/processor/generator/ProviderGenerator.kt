package club.fdawei.mourouter.processor.generator

import club.fdawei.mourouter.annotation.Interceptor
import club.fdawei.mourouter.annotation.Provider
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.processor.common.Context
import club.fdawei.mourouter.processor.util.UtilsProvider
import club.fdawei.mourouter.processor.util.annotationValue
import club.fdawei.mourouter.processor.util.identityName
import com.squareup.kotlinpoet.*
import javax.annotation.processing.Filer
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * Create by david on 2019/05/26.
 */
class ProviderGenerator(
    private val context: Context,
    private val utils: UtilsProvider
) {

    private val routeElements = mutableListOf<TypeElement>()
    private val interceptorElements = mutableListOf<TypeElement>()
    private val providerElements = mutableListOf<TypeElement>()
    private val autowiredElements = mutableMapOf<TypeElement, MutableList<VariableElement>>()
    private var appProviderCreated = false

    fun addRouteWith(element: TypeElement) {
        routeElements.add(element)
    }

    fun addInterceptorWith(element: TypeElement) {
        interceptorElements.add(element)
    }

    fun addProviderWith(element: TypeElement) {
        providerElements.add(element)
    }

    fun addAutowiredWith(typeElement: TypeElement, element: VariableElement) {
        var list: MutableList<VariableElement>? = autowiredElements[typeElement]
        if (list == null) {
            list = mutableListOf()
            autowiredElements[typeElement] = list
        }
        list.add(element)
    }

    fun clear() {
        routeElements.clear()
        interceptorElements.clear()
        providerElements.clear()
        autowiredElements.clear()
    }

    private fun getTypeMirror(type: String): TypeMirror? {
        return utils.elementUtils.getTypeElement(type)?.asType()
    }

    private fun isSubType(sub: TypeMirror?, main: TypeMirror?): Boolean {
        return if (sub == null || main == null) {
            false
        } else {
            utils.typeUtils.isSubtype(sub, main)
        }
    }

    private fun buildModuleProvider(name: String): TypeSpec {
        return TypeSpec.classBuilder(name)
            .addSuperinterface(TypeBox.MULTI_PROVIDER)
            .addFunction(buildFunProvideRoute())
            .addFunction(buildFunProvideInject())
            .build()
    }

    private fun buildFunProvideRoute(): FunSpec {
        val funBuilder = FunSpec.builder(ClassInfo.MultiProvider.FUN_PROVIDE_NAME)
            .addModifiers(KModifier.FINAL, KModifier.OVERRIDE)
            .addParameter(ClassInfo.MultiProvider.FUN_ARG_ROUTE_TABLE, TypeBox.ROUTE_TABLE)

        val activity = getTypeMirror(TypeBox.ACTIVITY_NAME)
        val service = getTypeMirror(TypeBox.SERVICE_NAME)
        val fragment = getTypeMirror(TypeBox.FRAGMENT_NAME)
        val v4fragment = getTypeMirror(TypeBox.V4_FRAGMENT_NAME)
        val creatable = getTypeMirror(TypeBox.CREATABLE_NAME)
        val routeHandler = getTypeMirror(TypeBox.ROUTE_HANDLER_NAME)

        funBuilder.addComment("register handler")
        routeElements.forEach {
            val route = it.getAnnotation(Route::class.java)
            val path = route.path
            val flags = route.flags
            val handlerValue = it.annotationValue(Route::class, ClassInfo.Route.VAL_HANDLER)?.value
            val handlerTypeMirror = handlerValue as? TypeMirror
            val handlerTypeName = if (isSubType(handlerTypeMirror, routeHandler)) {
                handlerTypeMirror?.asTypeName()
            } else {
                null
            }
            val handler: TypeName? = when {
                handlerTypeName != null -> {
                    handlerTypeName
                }
                isSubType(it.asType(), activity) -> {
                    TypeBox.ACTIVITY_ROUTE_HANDLER
                }
                isSubType(it.asType(), service) -> {
                    TypeBox.SERVICE_ROUTE_HANDLER
                }
                isSubType(it.asType(), fragment) || isSubType(it.asType(), v4fragment) -> {
                    TypeBox.FRAGMENT_ROUTE_HANDLER
                }
                isSubType(it.asType(), creatable) -> {
                    TypeBox.INSTANCE_ROUTE_HANDLER
                }
                else -> {
                    null
                }
            }
            if (handler != null) {
                FunSpec
                funBuilder.addStatement(
                    "${ClassInfo.MultiProvider.FUN_ARG_ROUTE_TABLE}.${ClassInfo.RouteTable.FUN_REGISTER_HANDLER}" +
                            "(%T(%S, %T::class, %L, {%T(it)}))",
                    TypeBox.HANDLER_META_DATA, path, it.asType(), flags, handler
                )
            }
        }

        funBuilder.addComment("register interceptor")
        interceptorElements.forEach {
            val interceptor = it.getAnnotation(Interceptor::class.java)
            val path = interceptor.path
            val priority = interceptor.priority
            funBuilder.addStatement(
                "${ClassInfo.MultiProvider.FUN_ARG_ROUTE_TABLE}.${ClassInfo.RouteTable.FUN_REGISTER_INTERCEPTOR}" +
                        "(%T(%S, %L, %T()))",
                TypeBox.INTERCEPTOR_META_DATA, path, priority, it.asType()
            )
        }

        return funBuilder.build()
    }

    private fun buildFunProvideInject(): FunSpec {
        val funBuilder = FunSpec.builder(ClassInfo.MultiProvider.FUN_PROVIDE_NAME)
            .addModifiers(KModifier.FINAL, KModifier.OVERRIDE)
            .addParameter(ClassInfo.MultiProvider.FUN_ARG_INJECT_TABLE, TypeBox.INJECT_TABLE)

        val autowiredProvider = getTypeMirror(TypeBox.AUTOWIRED_PROVIDER_NAME)

        funBuilder.addComment("register provider")
        providerElements.forEach {
            val sourceList = it.annotationValue(Provider::class, ClassInfo.Provider.VAL_SOURCE)?.value as? List<*>
            if (sourceList != null && isSubType(it.asType(), autowiredProvider)) {
                val typeNameList = sourceList.mapNotNull { any ->
                    ((any as? AnnotationValue)?.value as? TypeMirror)?.asTypeName()
                }
                val codeBuilder = CodeBlock.builder()
                codeBuilder.addStatement(
                    "${ClassInfo.MultiProvider.FUN_ARG_INJECT_TABLE}." +
                            "${ClassInfo.InjectTable.FUN_REGISTER_PROVIDER}(%T({ %T() }).apply {",
                    TypeBox.PROVIDER_META_DATA, it.asClassName()
                )
                codeBuilder.indent()
                typeNameList.forEach { type ->
                    codeBuilder.addStatement("${ClassInfo.ProviderMetaData.FUN_ADD_SOURCE}(%T::class)", type)
                }
                codeBuilder.unindent()
                codeBuilder.addStatement("})")
                funBuilder.addCode(codeBuilder.build())
            }
        }

        funBuilder.addComment("register injector")
        autowiredElements.keys.forEach {
            val pkgName = utils.elementUtils.getPackageOf(it).toString()
            val injectorName = it.identityName(pkgName) + ClassInfo.INJECTOR_NAME_SUFFIX
            val injectorType = ClassName.bestGuess("$pkgName.$injectorName")
            funBuilder.addStatement(
                "${ClassInfo.MultiProvider.FUN_ARG_INJECT_TABLE}.${ClassInfo.InjectTable.FUN_REGISTER_INJECTOR}" +
                        "(%T(%T::class, { %T() }))",
                TypeBox.INJECTOR_META_DATA, it.asType().asTypeName(), injectorType
            )
        }

        return funBuilder.build()
    }

    private fun buildAppProvider(name: String): TypeSpec {
        val funBuilder = FunSpec.builder(ClassInfo.AbsAppProvider.FUN_INIT_PROVIDERS)
            .addModifiers(KModifier.FINAL, KModifier.OVERRIDE)
        return TypeSpec.classBuilder(name)
            .superclass(TypeBox.ABS_APP_PROVIDER)
            .addFunction(funBuilder.build())
            .build()
    }

    fun genKtFile(filer: Filer) {
        if (context.isAppModule && !appProviderCreated) {
            FileSpec.builder(ClassInfo.PROVIDER_PACKAGE, ClassInfo.APP_PROVIDER_NAME)
                .addType(buildAppProvider(ClassInfo.APP_PROVIDER_NAME))
                .addComment("Generated automatically by MouRouter. Do not modify!")
                .build()
                .writeTo(filer)
            appProviderCreated = true
        }
        if (routeElements.isNotEmpty() ||
            interceptorElements.isNotEmpty() ||
            providerElements.isNotEmpty()
        ) {
            val providerName = ClassInfo.PROVIDER_NAME_PREFIX +
                    context.moduleName + ClassInfo.PROVIDER_NAME_SUFFIX
            FileSpec.builder(ClassInfo.PROVIDER_PACKAGE, providerName)
                .addType(buildModuleProvider(providerName))
                .addComment("Generated automatically by MouRouter. Do not modify!")
                .build()
                .writeTo(filer)
        }
    }
}