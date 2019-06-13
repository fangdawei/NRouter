@file:JvmName("AnnotationUtils")

package club.fdawei.mourouter.processor.util

import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Created by david on 2019/05/30.
 */
fun Element.annotationMirror(annotation: KClass<out Annotation>): AnnotationMirror? {
    this.annotationMirrors.forEach {
        if (it.annotationType.asTypeName() == annotation.asTypeName()) {
            return it
        }
    }
    return null
}

fun Element.annotationValue(annotation: KClass<out Annotation>, key: String): AnnotationValue? {
    val mirror = this.annotationMirror(annotation)
    return if (mirror == null) {
        null
    } else {
        mirror.elementValues.forEach { (executableElement, value) ->
            if (executableElement.simpleName.toString() == key) {
                return value
            }
        }
        return null
    }
}