package domain

import utils.Entity

class Slot(
    var snackMachine: SnackMachine,
    var position: Int,
    var snackPile: SnackPile = SnackPile(null, 0, 0.0)
) : Entity() {

}