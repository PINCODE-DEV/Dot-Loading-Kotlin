package app.dgmall.core.utils.base.loading.core

interface Convertor {
    fun convertDotSize(value: Int): Int

    fun convertDotSize(value: DotSize?): Int
}
