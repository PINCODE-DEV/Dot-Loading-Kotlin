package app.dgmall.core.utils.base.loading.core

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import app.dgmall.core.utils.base.loading.core.LoadingConstant.DEFAULT_COLOR
import app.dgmall.core.utils.base.loading.core.LoadingConstant.DEFAULT_DOTS_COUNT
import app.dgmall.core.utils.base.loading.core.LoadingConstant.DEFAULT_DOTS_SIZE

abstract class BaseLoading : LinearLayout {
    private var DOTS_SIZE: Int = DEFAULT_DOTS_SIZE
    private var DOTS_COUNT: Int = DEFAULT_DOTS_COUNT
    private var COLOR: Int = DEFAULT_COLOR

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    protected open fun initView(
        context: Context,
        attrs: AttributeSet?,
        dotsSize: Int,
        dotsCount: Int,
        color: Int
    ) {
        DOTS_SIZE = dotsSize
        DOTS_COUNT = dotsCount
        COLOR = color

        adjustView()

        removeAllViews()

        addDots()
    }

    private fun adjustView() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        val layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setLayoutParams(layoutParams)
        setBackgroundColor(Color.TRANSPARENT)
    }

    private fun addDots() {
        val layoutParams2 =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        for (i in 0 until DOTS_COUNT) {
            val circleView = CircleView(context, DOTS_SIZE, COLOR, true)
            val rel = LinearLayout(context)
            layoutParams2.setMargins(DOTS_SIZE / 2, DOTS_SIZE / 2, DOTS_SIZE / 2, DOTS_SIZE / 2)
            rel.gravity = Gravity.CENTER
            rel.addView(circleView)
            addView(rel, layoutParams2)
        }
    }
}