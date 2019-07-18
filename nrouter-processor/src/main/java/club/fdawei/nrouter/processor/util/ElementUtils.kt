@file:JvmName("ElementUtils")

package club.fdawei.nrouter.processor.util

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap
import kotlin.reflect.jvm.internal.impl.name.FqName

/**
 * Created by david on 2019/06/06.
 */
fun TypeElement.identityName(pkgName: String): String {
    return this.qualifiedName.toString()
        .replace("$pkgName.", "")
        .replace('.', '_')
}

fun Element.kotlinType(): TypeName {
    val ktTypeName = JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(
        FqName(this.asType().asTypeName().toString())
    )?.asSingleFqName()?.asString()
    return if (ktTypeName == null) {
        this.asType().asTypeName()
    } else {
        ClassName.bestGuess(ktTypeName)
    }
}