package kr.enak.minecraft.plugins.nomeltingice.utils

inline fun <T> Iterable<T>.forEachTry(action: (T) -> Unit) = this.forEach {
    try {
        action(it)
    } catch (ignored: Throwable) {
    }
}

inline fun <K, V> Map<out K, V>.forEachTry(action: (Map.Entry<K, V>) -> Unit) = this.forEach {
    try {
        action(it)
    } catch (ignored: Throwable) {
    }
}
