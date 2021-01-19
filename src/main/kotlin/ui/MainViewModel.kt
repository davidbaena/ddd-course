package ui

import domain.SnackMachine

fun main() {
    val snackMachineViewModel = SnackMachineViewModel(SnackMachine())
    snackMachineViewModel.render()
}