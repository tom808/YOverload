package com.yoverload

import com.yoverload.network.Item

interface ItemReceiver {
    fun receiveItem(item: Item)
}
