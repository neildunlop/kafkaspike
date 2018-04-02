package com.effectivenorth.kafkaspike.model

import java.util.*

data class Purchase(val firstname: String, val lastname: String, var creditCardNumber: String,
                    val itemPurchased: String, val quantity: Int, val price: Double, val purchaseDate: Date,
                    val zipCode: String) {

    private val CC_NUMBER_REPLACEMENT = "xxxx-xxxx-xxxx-"

    fun maskCreditCard() {
        Objects.requireNonNull(this.creditCardNumber, "Credit Card can't be null")
        val last4Digits = this.creditCardNumber.split("-")[3]
        this.creditCardNumber = CC_NUMBER_REPLACEMENT + last4Digits
    }
}



