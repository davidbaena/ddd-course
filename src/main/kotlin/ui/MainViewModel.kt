package ui

import logic.SnackMachine

fun main() {
    val snackMachineViewModel = SnackMachineViewModel(SnackMachine())
    snackMachineViewModel.render()
}