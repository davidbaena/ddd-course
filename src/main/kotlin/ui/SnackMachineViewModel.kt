package ui

import logic.Money
import logic.SnackMachine
import kotlin.system.exitProcess

class SnackMachineViewModel(private val snackMachine: SnackMachine) {

    private val insertCentCommand = Command { insertMoney(Money.CENT) }
    private val insertTenCentCommand = Command { insertMoney(Money.TEN_CENT) }
    private val insertQuarterCentCommand = Command { insertMoney(Money.QUARTER_CENT) }
    private val insertDollarCommand = Command { insertMoney(Money.DOLLAR) }
    private val insertFiveDollarCommand = Command { insertMoney(Money.FIVE_DOLLAR) }
    private val insertTwentyDollarCommand = Command { insertMoney(Money.TWENTY_DOLLAR) }
    private val buySnack = Command { buySnack() }
    private val returnMoney = Command { returnMoney() }

    private val moneyInTransaction
        get() = snackMachine.moneyInTransaction

    private val moneyInside
        get() = snackMachine.moneyInTransaction + snackMachine.moneyInside

    private var message: String = ""
        set(value) {
            field = value
            notifies()
        }

    private fun insertMoney(coinOrNote: Money) {
        snackMachine.insertMoney(coinOrNote)
        message = "You have inserted $coinOrNote"
    }

    private fun returnMoney() {
        snackMachine.returnMoney()
        message = "Money returned"
    }

    private fun buySnack() {
        snackMachine.buySnack()
        message = "You bought a snack"
    }

    private fun showError() {
        println("Wrong number")
        notifies()
    }

    private fun notifies() {
        render()
    }

    fun render() {
        println(this)
        println("Press key")
        val key = readLine()!!.toInt()

        when (key) {
            0 -> exitProcess(0)
            1 -> insertCentCommand.exec()
            2 -> insertTenCentCommand.exec()
            3 -> insertQuarterCentCommand.exec()
            4 -> insertDollarCommand.exec()
            5 -> insertFiveDollarCommand.exec()
            6 -> insertTwentyDollarCommand.exec()
            7 -> returnMoney.exec()
            8 -> buySnack.exec()
            else -> Command { showError() }.exec()
        }
    }

    override fun toString(): String {
        return """"
            $message
            Total Money inserted: $moneyInTransaction
            Money inside: $moneyInside
            |0) Exit
            |1) Put 0.01$
            |2) Put 0.10$
            |3) Put 0.25$
            |4) Put 1$
            |5) Put 5$
            |6) Put 20$
            |7) Return Money
            |8) Buy Snack
            
            """.trimMargin()
    }
}

class Command(private val fn: () -> Unit) {
    fun exec() {
        fn()
    }
}