package club.fdawei.nrouter.api.action

import android.os.Bundle
import android.os.Parcelable
import club.fdawei.nrouter.api.base.TypeDataContainer

/**
 * Create by david on 2019/05/25.
 */
interface Action<H> {

    fun arg(value: Any): H

    fun arg(container: TypeDataContainer): H


    fun withFlags(vararg flag: Int): H

    fun withData(bundle: Bundle?): H

    fun withString(k: String, v: String): H

    fun withStrings(map: Map<String, String>): H

    fun withStringArrayList(k: String, v: ArrayList<String>): H

    fun withInt(k: String, v: Int): H

    fun withIntArrayList(k: String, v: ArrayList<Int>): H

    fun withLong(k: String, v: Long): H

    fun withBoolean(k: String, v: Boolean): H

    fun withParcelable(k: String, v: Parcelable): H

    fun withParcelableArrayList(k: String, v: ArrayList<out Parcelable>): H
}