package logic

import ddd.ValueObject

data class Money(
    val oneCentCount: Int = 0,
    val tenCentCount: Int = 0,
    val quarterCentCount: Int = 0,
    val oneDollarCount: Int = 0,
    val fiveDollarCount: Int = 0,
    val twentyDollarCount: Int = 0
) : ValueObject<Money>() {
    companion object {
        val ZERO = Money(0, 0, 0, 0, 0, 0)
        val CENT = Money(1, 0, 0, 0, 0, 0)
        val TEN_CENT = Money(0, 1, 0, 0, 0, 0)
        val QUARTER_CENT = Money(0, 0, 1, 0, 0, 0)
        val DOLLAR = Money(0, 0, 0, 1, 0, 0)
        val FIVE_DOLLAR = Money(0, 0, 0, 0, 1, 0)
        val TWENTY_DOLLAR = Money(0, 0, 0, 0, 0, 1)
    }

    val amount: Double
        get() = oneCentCount * 0.01 +
                tenCentCount * 0.1 +
                quarterCentCount * 0.25 +
                oneDollarCount * 1.0 +
                fiveDollarCount * 5 +
                twentyDollarCount * 20

    init {
        if (oneCentCount < 0) throw IllegalStateException("Cannot create money with negative values")
        if (tenCentCount < 0) throw IllegalStateException("Cannot create money with negative values")
        if (quarterCentCount < 0) throw IllegalStateException("Cannot create money with negative values")
        if (oneDollarCount < 0) throw IllegalStateException("Cannot create money with negative values")
        if (fiveDollarCount < 0) throw IllegalStateException("Cannot create money with negative values")
        if (twentyDollarCount < 0) throw IllegalStateException("Cannot create money with negative values")

    }

    operator fun plus(money: Money): Money {
        return Money(
            oneCentCount = money.oneCentCount + oneCentCount,
            tenCentCount = money.tenCentCount + tenCentCount,
            quarterCentCount = money.quarterCentCount + quarterCentCount,
            oneDollarCount = money.oneDollarCount + oneDollarCount,
            fiveDollarCount = money.fiveDollarCount + fiveDollarCount,
            twentyDollarCount = money.twentyDollarCount + twentyDollarCount
        )
    }

    operator fun minus(money: Money): Money {
        return Money(
            oneCentCount = oneCentCount - money.oneCentCount,
            tenCentCount = tenCentCount - money.tenCentCount,
            quarterCentCount = quarterCentCount - money.quarterCentCount,
            oneDollarCount = oneDollarCount - money.oneDollarCount,
            fiveDollarCount = fiveDollarCount - money.fiveDollarCount,
            twentyDollarCount = twentyDollarCount - money.twentyDollarCount
        )
    }

    override fun toString(): String {
        return if (amount < 1.0) {
            "cents ${(amount * 100).toInt()}"
        } else {
            "%.2f".format(amount) + "$"
        }
    }

    override fun equalsCore(other: Money): Boolean {
        return other.oneCentCount == oneCentCount &&
                other.tenCentCount == tenCentCount &&
                other.quarterCentCount == quarterCentCount &&
                other.oneDollarCount == oneDollarCount &&
                other.fiveDollarCount == fiveDollarCount &&
                other.twentyDollarCount == twentyDollarCount
    }
}

