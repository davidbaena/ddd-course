package domain

import utils.Entity

class SnackMachine : Entity() {

    var moneyInside: Money = Money.ZERO
    var moneyInTransaction: Money = Money.ZERO
    val slots: MutableList<Slot> = mutableListOf(
        Slot(snackMachine = this, 0, null, 0, 0.00),
        Slot(snackMachine = this, 1, null, 0, 0.00),
        Slot(snackMachine = this, 2, null, 0, 0.00)
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

        moneyInTransaction = money + moneyInTransaction
    }

    fun returnMoney() {
        moneyInTransaction = Money.ZERO
    }

    fun buySnack(position: Int) {
        val slot = slots[position]
        slot.quantity -= 1
        slots[position] = slot

        moneyInside += moneyInTransaction
        moneyInTransaction = Money.ZERO
    }

    fun loadSnacks(position: Int, snack: Snack, quantity: Int, price: Double) {
        val slot = slots[position]
        slot.snack = snack
        slot.quantity = quantity
        slot.price = price
        slots[position] = slot
        print(slots)
    }
}