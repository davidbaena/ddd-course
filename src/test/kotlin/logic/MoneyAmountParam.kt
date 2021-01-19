package logic

import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(
    Parameterized::class
)
class MoneyAmountParam(
    private val oneCent: Int,
    private val tenCent: Int,
    private val quarterCent: Int,
    private val oneDollar: Int,
    private val fiveDollar: Int,
    private val twentyDollar: Int,
    private val expected: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(1, 0, 0, 0, 0, 0, "cents 1"),
                arrayOf(0, 1, 0, 0, 0, 0, "cents 10"),
                arrayOf(0, 0, 1, 0, 0, 0, "cents 25"),
                arrayOf(1, 0, 0, 1, 0, 0, "1,01$"),
                arrayOf(0, 1, 0, 0, 1, 0, "5,10$"),
                arrayOf(0, 1, 0, 0, 0, 1, "20,10$")
            )
        }
    }

    @Test
    fun `to string should return amount of money`() {
        val money = Money(oneCent, tenCent, quarterCent, oneDollar, fiveDollar, twentyDollar)
        Assert.assertThat(money.toString(), Is.`is`(expected))
    }
}