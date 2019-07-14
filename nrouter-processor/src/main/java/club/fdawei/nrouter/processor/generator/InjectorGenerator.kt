package club.fdawei.nrouter.processor.generator

import club.fdawei.nrouter.annotation.Autowired
import club.fdawei.nrouter.processor.common.Context
import club.fdawei.nrouter.processor.util.UtilsProvider
import club.fdawei.nrouter.processor.util.identityName
import club.fdawei.nrouter.processor.util.kotlinType
import com.squareup.kotlinpoet.*
import javax.annotation.processing.Filer
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by david on 2019/06/06.
 */
class InjectorGenerator(
    private val context: Context,
    private val utils: UtilsProvider
) {

    private val autowiredElements = mutableMapOf<TypeElement, MutableList<VariableElement>>()

    fun addAutowiredWith(typeElement: TypeElement, element: VariableElement) {
        var list: MutableList<VariableElement>? = autowiredElements[typeElement]
        if (list == null) {
            list = mutableListOf()
            autowiredElements[typeElement] = list
        }
        list.add(element)
    }

    fun clear() {
        autowiredElements.clear()
    }

    private fun buildInjector(
        name: String,
        type: TypeElement,
        variableList: List<VariableElement>
    ): TypeSpec {
        val funBuilder = FunSpec.builder(ClassInfo.Injector.FUN_INJECT)
            .addModifiers(KModifier.FINAL, KModifier.OVERRIDE)
            .addParameter(ClassInfo.Injector.FUN_ARG_TARGET, ANY)
            .addParameter(ClassInfo.Injector.FUN_ARG_SOURCE, ANY)
            .addParameter(
                ClassInfo.Injector.FUN_ARG_PROVIDER,
                TypeBox.AUTOWIRED_PROVIDER
            )
            .addParameter(
                ClassInfo.Injector.FUN_ARG_DATA,
                TypeBox.ACTION_BUNDLE
            )
        funBuilder.beginControlFlow(
            "if (${ClassInfo.Injector.FUN_ARG_TARGET} !is %T)",
            type.asType()
        )
        funBuilder.addStatement("return")
        funBuilder.endControlFlow()
        variableList.forEach {
            val autowired = it.getAnnotation(Autowired::class.java)
            val name = if (autowired.name.isNotEmpty()) {
                autowired.name
            } else {
                it.simpleName.toString()
            }
            funBuilder.addStatement(
                "${ClassInfo.Injector.FUN_ARG_TARGET}.%L = " +
                        "${ClassInfo.Injector.FUN_ARG_PROVIDER}." +
                        "${ClassInfo.AutowiredProvider.FUN_GET_AUTOWIRED}(" +
                        "${ClassInfo.Injector.FUN_ARG_SOURCE}, " +
                        "%S, " +
                        "%S, " +
                        "%T::class, " +
                        "${ClassInfo.Injector.FUN_ARG_DATA})",
                it.simpleName.toString(), name, autowired.path, it.kotlinType()
            )
        }
        return TypeSpec.classBuilder(name)
            .addSuperinterface(TypeBox.INJECTOR)
            .addFunction(funBuilder.build())
            .build()
    }

    fun genKtFile(filer: Filer) {
        autowiredElements.forEach {
            val pkgName = utils.elementUtils.getPackageOf(it.key).toString()
            val typeLocalName = it.key.identityName(pkgName)
            val injectorName = typeLocalName + ClassInfo.INJECTOR_NAME_SUFFIX
            FileSpec.builder(pkgName, injectorName)
                .addType(buildInjector(injectorName, it.key, it.value))
                .addComment("Generated automatically by NRouter. Do not modify!")
                .build()
                .writeTo(filer)
        }
    }
}