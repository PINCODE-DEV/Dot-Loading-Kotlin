package app.dgmall.core.utils.base.loading.core

interface CheckValidation {
    fun isCountValid(value: Int): Boolean

    fun isDurationValid(value: Int): Boolean
}