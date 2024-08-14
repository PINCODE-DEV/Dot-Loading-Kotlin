package app.dgmall.core.utils.base.loading

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import app.dgmall.core.utils.base.R
import app.dgmall.core.utils.base.loading.core.BaseLoading
import app.dgmall.core.utils.base.loading.core.CheckValidation
import app.dgmall.core.utils.base.loading.core.CheckValidationImpl
import app.dgmall.core.utils.base.loading.core.Convertor
import app.dgmall.core.utils.base.loading.core.ConvertorImpl
import app.dgmall.core.utils.base.loading.core.LoadingConstant

class Loading : BaseLoading {
    private var convertor: Convertor? = null
    private var checkValidation: CheckValidation? = null

    private var DOTS_SIZE = LoadingConstant.DEFAULT_DOTS_SIZE
    private var DOTS_COUNT = LoadingConstant.DEFAULT_DOTS_COUNT
    private var DURATION = LoadingConstant.DEFAULT_DURATION
    private var COLOR = LoadingConstant.DEFAULT_COLOR

    private lateinit var animator: Array<ObjectAnimator?>
    private var onLayoutReach: Boolean = false

    constructor(context: Context) : super(context) {
        initView(
            context,
            null,
            LoadingConstant.DEFAULT_DOTS_SIZE,
            LoadingConstant.DEFAULT_DOTS_COUNT,
            LoadingConstant.DEFAULT_COLOR
        )
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(
            context,
            attrs,
            LoadingConstant.DEFAULT_DOTS_SIZE,
            LoadingConstant.DEFAULT_DOTS_COUNT,
            LoadingConstant.DEFAULT_COLOR
        )
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(
            context,
            attrs,
            LoadingConstant.DEFAULT_DOTS_SIZE,
            LoadingConstant.DEFAULT_DOTS_COUNT,
            LoadingConstant.DEFAULT_COLOR
        )
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (!onLayoutReach) {
            onLayoutReach = true

            animateView(true)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        for (i in 0 until DOTS_COUNT) {
            if (animator[i]!!.isRunning) {
                animator[i]!!.removeAllListeners()
                animator[i]!!.end()
                animator[i]!!.cancel()
            }
        }
    }

    @SuppressLint("Recycle")
    override fun initView(
        context: Context,
        attrs: AttributeSet?,
        dotsSize: Int,
        dotsCount: Int,
        color: Int
    ) {
        convertor = ConvertorImpl()
        checkValidation = CheckValidationImpl()

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Loading)
            setColor(
                typedArray.getColor(
                    R.styleable.Loading_dots_color,
                    LoadingConstant.DEFAULT_COLOR
                )
            )
            setDuration(
                typedArray.getInt(
                    R.styleable.Loading_dots_duration,
                    LoadingConstant.DEFAULT_DURATION
                )
            )
            setDotsCount(
                typedArray.getInt(
                    R.styleable.Loading_dots_count,
                    LoadingConstant.DEFAULT_DOTS_COUNT
                )
            )
            // setSize(typedArray.getInt(R.styleable.LoadingFady_dots_size, 2));
            setSize(
                typedArray.getDimensionPixelSize(
                    R.styleable.Loading_dots_size,
                    LoadingConstant.DEFAULT_DOTS_SIZE
                )
            )
        }
        super.initView(context, attrs, DOTS_SIZE, DOTS_COUNT, COLOR)
    }

    private fun animateView(show: Boolean) {
        animator = arrayOfNulls(DOTS_COUNT)
        for (i in 0 until DOTS_COUNT) {
            val A = PropertyValuesHolder.ofFloat("alpha", 0.2f)
            val B = PropertyValuesHolder.ofFloat("alpha", 1.0f)
            val alpha = if (show) A else B
            animator[i] = ObjectAnimator.ofPropertyValuesHolder(getChildAt(i), alpha)
            animator[i]!!.repeatCount = 0
            animator[i]!!.repeatMode = ValueAnimator.REVERSE
            animator[i]!!.setDuration(DURATION.toLong())
            animator[i]!!.startDelay = (DURATION * i).toLong()
            animator[i]!!.start()
        }
        animator[DOTS_COUNT - 1]!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animateView(!show)
            }
        })
    }

    private fun setDotsCount(value: Int) {
        if (checkValidation!!.isCountValid(value)) {
            this.DOTS_COUNT = value
            initView(
                context,
                null,
                LoadingConstant.DEFAULT_DOTS_SIZE,
                LoadingConstant.DEFAULT_DOTS_COUNT,
                LoadingConstant.DEFAULT_COLOR
            )
        }
    }

    private fun setDuration(value: Int) {
        if (checkValidation!!.isDurationValid(value)) {
            this.DURATION = value
            initView(
                context,
                null,
                LoadingConstant.DEFAULT_DOTS_SIZE,
                LoadingConstant.DEFAULT_DOTS_COUNT,
                LoadingConstant.DEFAULT_COLOR
            )
        }
    }

    private fun setColor(value: Int) {
        this.COLOR = value
        initView(
            context,
            null,
            LoadingConstant.DEFAULT_DOTS_SIZE,
            LoadingConstant.DEFAULT_DOTS_COUNT,
            LoadingConstant.DEFAULT_COLOR
        )
    }


    private fun setSize(value: Int) {
        this.DOTS_SIZE = value
        initView(
            context,
            null,
            LoadingConstant.DEFAULT_DOTS_SIZE,
            LoadingConstant.DEFAULT_DOTS_COUNT,
            LoadingConstant.DEFAULT_COLOR
        )
    }
}
