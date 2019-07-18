package club.fdawei.nrouter.api.base

/**
 * Created by david on 2019/07/11.
 */
class LRULinkedHashMap<K, V>(
    val max: Int
) : LinkedHashMap<K, V>(16, 0.75f, true) {

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        return this.size >= this.max
    }
}