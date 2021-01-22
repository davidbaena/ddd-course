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
        assertThat(snackMachine.moneyInTransaction, `is`(0.0))
    }

    @Test
    fun `insert money goes to the money transaction`() {
        val snackMachine = SnackMachine()
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.insertMoney(Money.DOLLAR)
        assertThat(snackMachine.moneyInTransaction, `is`(Money.DOLLAR.amount + Money.DOLLAR.amount))
    }

    @Test
    fun `cannot insert a money once a time`() {
        val snackMachine = SnackMachine()
        val money = Money.DOLLAR + Money.DOLLAR

        assertThatThrow<IllegalArgumentException> {
            snackMachine.insertMoney(money)
        }
    }

    @Test
    fun `money in transaction goes to money inside after purchase`() {
        val snackMachine = SnackMachine()
        snackMachine.loadSnacks(0, SnackPile(Snack("Some snack"), 10, 1.00))
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.buySnack(0)

        assertThat(snackMachine.moneyInTransaction, `is`(0.0))
        assertThat(snackMachine.moneyInside, `is`(Money.DOLLAR + Money.DOLLAR))
    }

    @Test
    fun `buy snack trades inserted money for a snack`() {
        val snackMachine = SnackMachine()
        snackMachine.loadSnacks(0, SnackPile(Snack("Some snack"), 10, 1.00))
        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.buySnack(0)

        assertThat(snackMachine.moneyInTransaction, `is`(0.0))
        assertThat(snackMachine.moneyInside, `is`(Money.DOLLAR))
        val slot1 = snackMachine.getSnackPile(0)
        assertThat(slot1.quantity, `is`(9))
    }

    @Test
    fun `cannot purchase when there is no snacks`() {
        val snackMachine = SnackMachine()

        assertThatThrow<IllegalArgumentException> {
            snackMachine.buySnack(0)
        }
    }

    @Test
    fun `cannot purchase if not enough money inserted`() {
        val snackMachine = SnackMachine()
        snackMachine.loadSnacks(0, SnackPile(Snack("Some snack"), 10, 2.00))
        snackMachine.insertMoney(Money.DOLLAR)

        assertThatThrow<IllegalArgumentException> {
            snackMachine.buySnack(0)
        }
    }

    @Test
    fun `snack machine returns money with highest denomination first`() {
        val snackMachine = SnackMachine()
        snackMachine.loadMoney(Money.DOLLAR)

        snackMachine.insertMoney(Money.QUARTER_CENT)
        snackMachine.insertMoney(Money.QUARTER_CENT)
        snackMachine.insertMoney(Money.QUARTER_CENT)
        snackMachine.insertMoney(Money.QUARTER_CENT)

        snackMachine.returnMoney()

        assertThat(snackMachine.moneyInside.oneDollarCount, `is`(0))
        assertThat(snackMachine.moneyInside.quarterCentCount, `is`(4))
    }


    @Test
    fun `after purchase change is returned`() {
        val snackMachine = SnackMachine()
        snackMachine.loadMoney(Money.TEN_CENT * 10)
        snackMachine.loadSnacks(0, SnackPile(Snack("Some snack"), 10, 0.50))

        snackMachine.insertMoney(Money.DOLLAR)
        snackMachine.buySnack(0)

        assertThat(snackMachine.moneyInside, `is`(1.5))
        assertThat(snackMachine.moneyInTransaction, `is`(0))

    }

    @Test
    fun `cannot buy snack if there is no change in snack machine`() {

        val snackMachine = SnackMachine()
        snackMachine.loadMoney(Money.TEN_CENT)
        snackMachine.loadSnacks(0, SnackPile(Snack("Some snack"), 10, 0.50))

        snackMachine.insertMoney(Money.DOLLAR)

        assertThatThrow<IllegalArgumentException> {
            snackMachine.buySnack(0)
        }
    }

    private inline fun <reified T : Throwable> assertThatThrow(fn: () -> Unit) {
        try {
            fn()
            Assert.fail("Does not throws IllegalStateException")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(true)
        }
    }
}