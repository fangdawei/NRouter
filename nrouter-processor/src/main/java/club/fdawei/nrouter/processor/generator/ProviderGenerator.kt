package club.fdawei.nrouter.processor.generator

import club.fdawei.nrouter.annotation.Interceptor
import club.fdawei.nrouter.annotation.Provider
import club.fdawei.nrouter.annotation.Route
import club.fdawei.nrouter.annotation.SchemeAware
import club.fdawei.nrouter.processor.common.Context
import club.fdawei.nrouter.processor.util.UtilsProvider
import club.fdawei.nrouter.processor.util.annotationValue
import club.fdawei.nrouter.processor.util.identityName
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
    private val schemeElements = mutableListOf<TypeElement>()

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
        val list: MutableList<VariableElement> = autowiredElements[typeElement]
            ?: mutableListOf<VariableElement>().apply {
                autowiredElements[typeElement] = this
            }
        list.add(element)
    }

    fun addSchemeWith(typeElement: TypeElement) {
        schemeElements.add(typeElement)
    }

    private fun isEmpty(): Boolean {
        return routeElements.isEmpty() &&
                interceptorElements.isEmpty() &&
                providerElements.isEmpty() &&
                autowiredElements.isEmpty() &&
                schemeElements.isEmpty()
    }

    fun clear() {
        routeElements.clear()
        interceptorElements.clear()
        providerElements.clear()
        autowiredElements.clear()
        schemeElements.clear()
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
            .addFunction(buildFunProvideScheme())
            .build()
    }

    private fun buildFunProvideRoute(): FunSpec {
        val funBuilder = FunSpec.builder(ClassInfo.MultiProvider.FUN_PROVIDE_NAME)
            .addModifiers(KModifier.FINAL, KModifier.OVERRIDE)
            .addParameter(
                ClassInfo.MultiProvider.FUN_ARG_REGISTRY,
                TypeBox.ROUTE_REGISTRY
            )

        val activity = getTypeMirror(TypeBox.ACTIVITY_NAME)
        val service = getTypeMirror(TypeBox.SERVICE_NAME)
        val fragment = getTypeMirror(TypeBox.FRAGMENT_NAME)
        val v4fragment = getTypeMirror(TypeBox.V4_FRAGMENT_NAME)
        val creatable = getTypeMirror(TypeBox.CREATABLE_NAME)
        val routeHandler = getTypeMirror(TypeBox.ROUTE_HANDLER_NAME)

        funBuilder.addComment("register route node")
        routeElements.forEach {
            val route = it.getAnnotation(Route::class.java)
            val path = route.path
            val flags = route.flags
            val desc = route.desc
            val handlerValue = it.annotationValue(
                Route::class,
                ClassInfo.Route.VAL_HANDLER
            )?.value
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
                    TypeBox.CREATABLE_ROUTE_HANDLER
                }
                else -> {
                    null
                }
            }
            if (handler != null) {
                funBuilder.addStatement(
                    "${ClassInfo.MultiProvider.FUN_ARG_REGISTRY}.${ClassInfo.RouteRegistry.FUN_REGISTER}" +
                            "(%T(%S, %T::class, %L, %S, %T(%T::class, { %T() })))",
                    TypeBox.ROUTE_NODE_META, path, it.asType(), flags, desc, TypeBox.TYPE_BUNDLE, handler, handler
                )
            }
        }

        funBuilder.addComment("register interceptor")
        interceptorElements.forEach {
            val interceptor = it.getAnnotation(Interceptor::class.java)
            val path = interceptor.path
            val priority = interceptor.priority
            val desc = interceptor.desc
            val interceptorType = it.asClassName()
            funBuilder.addStatement(
                "${ClassInfo.MultiProvider.FUN_ARG_REGISTRY}.${ClassInfo.RouteRegistry.FUN_REGISTER}" +
                        "(%T(%S, %L, %S, %T(%T::class, { %T() })))",
                TypeBox.INTERCEPTOR_META, path, priority, desc, TypeBox.TYPE_BUNDLE, interceptorType, interceptorType
            )
        }

        return funBuilder.build()
    }

    private fun buildFunProvideInject(): FunSpec {
        val funBuilder = FunSpec.builder(ClassInfo.MultiProvider.FUN_PROVIDE_NAME)
            .addModifiers(KModifier.FINAL, KModifier.OVERRIDE)
            .addParameter(
                ClassInfo.MultiProvider.FUN_ARG_REGISTRY,
                TypeBox.INJECT_REGISTRY
            )

        val autowiredProvider = getTypeMirror(TypeBox.AUTOWIRED_PROVIDER_NAME)

        funBuilder.addComment("register provider")
        providerElements.forEach {
            val sourceList = it.annotationValue(
                Provider::class,
                ClassInfo.Provider.VAL_SOURCE
            )?.value as? List<*>
            if (sourceList != null && isSubType(it.asType(), autowiredProvider)) {
                val typeNameList = sourceList.mapNotNull { any ->
                    ((any as? AnnotationValue)?.value as? TypeMirror)?.asTypeName()
                }
                val providerType = it.asClassName()
                val codeBuilder = CodeBlock.builder()
                codeBuilder.addStatement(
                    "${ClassInfo.MultiProvider.FUN_ARG_REGISTRY}." +
                            "${ClassInfo.InjectRegistry.FUN_REGISTER}(%T(%T(%T::class, { %T() })).apply {",
                    TypeBox.PROVIDER_META, TypeBox.TYPE_BUNDLE, providerType, providerType
                )
                codeBuilder.indent()
                typeNameList.forEach { type ->
                    codeBuilder.addStatement("${ClassInfo.ProviderMeta.FUN_ADD_SOURCE}(%T::class)", type)
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
                "${ClassInfo.MultiProvider.FUN_ARG_REGISTRY}.${ClassInfo.InjectRegistry.FUN_REGISTER}" +
                        "(%T(%T::class, %T(%T::class, { %T() })))",
                TypeBox.INJECTOR_META, it.asClassName(), TypeBox.TYPE_BUNDLE, injectorType, injectorType
            )
        }

        return funBuilder.build()
    }

    private fun buildFunProvideScheme(): FunSpec {
        val funBuilder = FunSpec.builder(ClassInfo.MultiProvider.FUN_PROVIDE_NAME)
            .addModifiers(KModifier.FINAL, KModifier.OVERRIDE)
            .addParameter(
                ClassInfo.MultiProvider.FUN_ARG_REGISTRY,
                TypeBox.SCHEME_REGISTRY
            )

        funBuilder.addComment("register scheme")
        schemeElements.forEach {
            val scheme = it.getAnnotation(SchemeAware::class.java)
            val handlerType = it.asClassName()
            funBuilder.addStatement(
                "${ClassInfo.MultiProvider.FUN_ARG_REGISTRY}.${ClassInfo.SchemeRegistry.FUN_REGISTER}" +
                        "(%T(%L, %T(%T::class, { %T() })))",
                TypeBox.SCHEME_META, scheme.priority, TypeBox.TYPE_BUNDLE, handlerType, handlerType
            )
        }

        return funBuilder.build()
    }

    fun genKtFile(filer: Filer) {
        if (!isEmpty()) {
            val providerName = ClassInfo.PROVIDER_NAME_PREFIX +
                    context.moduleName + ClassInfo.PROVIDER_NAME_SUFFIX
            FileSpec.builder(ClassInfo.PROVIDER_PACKAGE, providerName)
                .addType(buildModuleProvider(providerName))
                .addComment("Generated automatically by NRouter. Do not modify!")
                .build()
                .writeTo(filer)
        }
    }
}