package ddd

abstract class Entity {
    private val id: Long = 0

    override fun equals(other: Any?): Boolean {
        other as Entity

        if (this === other) return true // Reference equals

        if (javaClass != other.javaClass) return false // Reference equals

        if (id == 0L || other.id == 0L) return false

        if (id != other.id) return false //Structural equals

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}