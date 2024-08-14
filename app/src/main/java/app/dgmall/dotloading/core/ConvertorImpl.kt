package app.dgmall.core.utils.base.loading.core

class ConvertorImpl : Convertor {
    override fun convertDotSize(value: Int): Int {
        val size = when (value) {
            0 -> 10
            1 -> 15
            3 -> 30
            4 -> 40
            else -> 20
        }
        return size
    }

    override fun convertDotSize(value: DotSize?): Int {
        val size = when (value) {
            DotSize.TINY -> 10
            DotSize.SMALL -> 15
            DotSize.BIG -> 30
            DotSize.HUGE -> 40
            else -> 20
        }
        return size
    }
}