package domain

import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test


class MoneySpec {
    @Test
    fun `sum of tow moneys produce result`() {

        //Arrange
        val money1 = Money(1, 2, 3, 4, 5)
        val money2 = Money(1, 2, 3, 4, 5)

        //Act
        val result = money1 + money2

        //Assert
        assertThat(result.oneCentCount, `is`(2))
    }

    @Test
    fun `two money instances equal if contain the same money amount`() {
        //Arrange
        val money1 = Money(1, 2, 3, 4, 5)
        val money2 = Money(1, 2, 3, 4, 5)

        assertThat(money1, `is`(money2))
    }

    @Test
    fun `two money instances do not equal if contain different money amount`() {
        //Arrange
        val money1 = Money(1, 2, 3, 4, 6)
        val money2 = Money(1, 2, 3, 4, 5)

        assertThat(money1, `is`(not(money2)))
    }

    @Test
    fun `cannot create money with negative value`() {
        try {
            val money1 = Money(-1, 0, 0, 0, 0)
            fail("Money does not throws IllegalStateException")
        } catch (e: IllegalStateException) {
            assertTrue(true)
        }
    }

    @Test
    fun amount_is_calculated_correctly() {
        val money = Money(1, 1, 1, 1, 1, 1)
        assertThat(money.amount, `is`(26.36))
    }

    @Test
    fun substraction_of_two_moneys_produces_correct_result() {
        val money1 = Money(10, 10, 10, 10, 10, 10)
        val money2 = Money(2, 2, 2, 2, 2, 2)

        val result = money1 - money2

        assertThat(result.oneCentCount, `is`(8))
        assertThat(result.tenCentCount, `is`(8))
        assertThat(result.quarterCentCount, `is`(8))
        assertThat(result.oneDollarCount, `is`(8))
        assertThat(result.fiveDollarCount, `is`(8))
        assertThat(result.twentyDollarCount, `is`(8))

    }

    @Test
    fun cannot_substract_more_than_exists() {
        val money1 = Money(1, 0, 0, 0, 0, 0)
        val money2 = Money(0, 1, 0, 0, 0, 0)
        try {
            val result = money1 - money2
            fail("Money does not throws IllegalStateException")
        } catch (e: IllegalStateException) {
            assertTrue(true)
        }
    }
}