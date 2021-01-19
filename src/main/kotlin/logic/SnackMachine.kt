package logic

import ddd.Entity

class SnackMachine(
    var moneyInside: Money = Money.ZERO,
    var moneyInTransaction: Money = Money.ZERO
) : Entity() {

    fun insertMoney(money: Money) {
        val coinsAndNotes = listOf(
            Money.CENT,
            Money.CENT,
            Money.TEN_CENT,
            Money.QUARTER_CENT,
            Money.DOLLAR,
            Money.FIVE_DOLLAR,
            Money.TWENTY_DOLLAR
        )
        if (!coinsAndNotes.contains(money)) throw IllegalArgumentException()

        moneyInTransaction = money + moneyInTransaction
    }

    fun returnMoney() {
        moneyInTransaction = Money.ZERO
    }

    fun buySnack() {
        moneyInside += moneyInTransaction
        moneyInTransaction = Money.ZERO
    }
}