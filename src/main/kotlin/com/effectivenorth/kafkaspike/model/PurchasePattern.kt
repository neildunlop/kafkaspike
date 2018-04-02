package com.effectivenorth.kafkaspike.model

import java.util.*

data class PurchasePattern(val zipCode: String, val item: String, val date: Date) {

    constructor(purchase: Purchase) : this(purchase.zipCode, purchase.itemPurchased, purchase.purchaseDate)

    override fun toString(): String {
        return "PurchasePattern{" +
                " zipCode='$zipCode'," +
                " item='$item," +
                " date=" + date +
                '}'.toString()
    }
}
