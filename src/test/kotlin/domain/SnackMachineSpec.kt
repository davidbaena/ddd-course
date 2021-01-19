package domain

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Test

class SnackMachineSpec {
    @Test
    fun `return empties money in transaction`() {
        val snackMachine = SnackMachine()
        snackMachine.insertMoney(Money.QUARTER_CENT)
        snackMachine.returnMoney()
        assertThat(snackMachine.moneyInTransaction, `is`(Money.ZERO))
    }

    @Test
    fun `insert money goes to the money transaction`() {
        val snackMachine = SnackMachine()
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.insertMoney(Money.DOLLAR)
        assertThat(snackMachine.moneyInTransaction, `is`(Money.DOLLAR + Money.DOLLAR))
    }

    @Test
    fun `cannot insert a money once a time`() {
        val snackMachine = SnackMachine()
        val money = Money.DOLLAR + Money.DOLLAR

        try {
            snackMachine.insertMoney(money)
            Assert.fail("Money does not throws IllegalStateException")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(true)
        }
    }

    @Test
    fun `money in transaction goes to money inside after purchase`() {
        val snackMachine = SnackMachine()
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.buySnack(1)

        assertThat(snackMachine.moneyInTransaction, `is`(Money.ZERO))
        assertThat(snackMachine.moneyInside, `is`(Money.DOLLAR + Money.DOLLAR))
    }

    @Test
    fun `buy snack trades inserted money for a snack`() {
        val snackMachine = SnackMachine()
        snackMachine.loadSnacks(0, Snack("Some snack"), 10, 1.00)
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.buySnack(0)

        assertThat(snackMachine.moneyInTransaction, `is`(Money.ZERO))
        assertThat(snackMachine.moneyInside, `is`(Money.DOLLAR))

        val slot1 = snackMachine.slots.find { slot -> slot.position == 0 }!!
        assertThat(slot1.quantity, `is`(9))
    }
}