package app.dgmall.core.utils.base.loading.core

class CheckValidationImpl: CheckValidation {
    override fun isCountValid(value: Int): Boolean {
        return value > 0
    }

    override fun isDurationValid(value: Int): Boolean {
        return value > 0
    }
}