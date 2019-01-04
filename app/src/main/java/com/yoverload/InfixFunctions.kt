package com.yoverload

/**
 * Created by tom.egan on 04-Jan-2019.
 */
infix fun Any?.ifNull(block: () -> Unit) {
    if (this == null) block()
}