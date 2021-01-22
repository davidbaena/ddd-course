package domain

import utils.ValueObject

data class SnackPile(
    val snack: Snack?,
    val quantity: Int,
    val price: Double
) : ValueObject<SnackPile>() {

    init {
        if (quantity < 0) {
            throw IllegalArgumentException()
        }

        if (price < 0) {
            throw IllegalArgumentException()
        }

        //TODO check price has not 0.001 precision
    }

    override fun equalsCore(other: SnackPile): Boolean {
        TODO("Not yet implemented")
    }

    fun subtractOne(): SnackPile = SnackPile(snack, quantity - 1, price)
}