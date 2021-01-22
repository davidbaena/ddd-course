package domain

import utils.AggregateRoot

class SnackMachine : AggregateRoot() {

    var moneyInside: Money = Money.ZERO
    var moneyInTransaction: Double = 0.0

    private val slots: MutableList<Slot> = mutableListOf(
        Slot(snackMachine = this, 0),
        Slot(snackMachine = this, 1),
        Slot(snackMachine = this, 2)
    )

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

        moneyInTransaction += money.amount
        moneyInside += money
    }

    fun returnMoney() {
        val moneyReturn = moneyInside.change(moneyInTransaction)
        moneyInside -= moneyReturn

        moneyInTransaction = 0.0
    }

    fun buySnack(position: Int) {
        val slot = slots[position]

        if (slot.snackPile.price > moneyInTransaction)
            throw IllegalArgumentException()


        slot.snackPile = slot.snackPile.subtractOne()
        slots[position] = slot

        val change = moneyInside.change(moneyInTransaction - slot.snackPile.price)

        if (change.amount < moneyInTransaction - slot.snackPile.price)
            throw IllegalArgumentException()

        moneyInside -= change
        moneyInTransaction = 0.0
    }

    fun getSnackPile(position: Int): SnackPile = slots[position].snackPile

    fun loadSnacks(position: Int, snackPile: SnackPile) {
        slots[position] = Slot(this, position, snackPile)
    }

    fun loadMoney(money: Money) {
        moneyInside += money
    }
}