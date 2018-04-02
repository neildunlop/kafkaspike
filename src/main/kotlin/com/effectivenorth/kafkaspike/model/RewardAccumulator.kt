package com.effectivenorth.kafkaspike.model

data class RewardAccumulator(val customerName: String, val purchaseTotal: Double) {

    override fun toString(): String {
        return "RewardAccumulator{" +
                "customerName='" + customerName + '\''.toString() +
                ", purchaseTotal=" + purchaseTotal +
                '}'.toString()
    }

    constructor(purchase: Purchase) : this(purchase.lastname + "," + purchase.firstname, purchase.price * purchase.quantity)

}