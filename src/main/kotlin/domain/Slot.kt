package domain

import utils.Entity

class Slot(
    var snackMachine: SnackMachine,
    var position: Int,
    var snack: Snack?,
    var quantity: Int,
    var price: Double
) : Entity() {
}