package utils

abstract class ValueObject<T> {

    override fun equals(other: Any?): Boolean {
        val valueObject: T = other as? T ?: return false
        return equalsCore(valueObject)
    }

    abstract fun equalsCore(other: T): Boolean
}