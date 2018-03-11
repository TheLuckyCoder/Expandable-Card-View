package net.theluckycoder.expandablecardview

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * A CardView that can be expanded
 */
open class ExpandableCardView : CardView {

    private val tvTitle by bind<TextView>(R.id.tv_card_title)
    private val tvDescription by bind<TextView>(R.id.tv_card_desc)
    private val ivArrow by bind<ImageView>(R.id.iv_card_expand)
    private val btnAction by bind<Button>(R.id.btn_card_action)
    private val layoutContent by bind<View>(R.id.layout_content)

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        inflate(context, R.layout.view_expandable_card, this)

        var expanded = false
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableCardView)
            cardTitle = typedArray.getString(R.styleable.ExpandableCardView_title) ?: ""
            cardDescription = typedArray.getString(R.styleable.ExpandableCardView_description) ?: ""
            expanded = typedArray.getBoolean(R.styleable.ExpandableCardView_expanded, false)
            expandDuration = typedArray.getInt(R.styleable.ExpandableCardView_expand_duration, 400).toLong()

            typedArray.recycle()
        }

        post {
            if (expanded) {
                expand(false)
            } else {
                setHeightToZero(false)
            }
        }

        setOnClickListener {
            if (isExpanded) {
                collapse(true)
            } else {
                expand(true)
            }
        }
    }

    private fun <T : View> View.bind(@IdRes res: Int): Lazy<T> =
        lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }

    private fun rotateArrow(rotation: Float, animate: Boolean) {
        ViewCompat.animate(ivArrow)
            .rotation(rotation)
            .withLayer()
            .setDuration(if (animate) expandDuration else 0)
            .start()
    }

    private fun setHeightToZero(animate: Boolean) {
        if (animate) {
            animate(layoutContent.height, 0)
        } else {
            setContentHeight(0)
        }
    }

    private fun setHeightToContentHeight(animate: Boolean) {
        measureContentView()
        val targetHeight = layoutContent.measuredHeight
        if (animate) {
            animate(0, targetHeight)
        } else {
            setContentHeight(targetHeight)
        }
    }

    private fun setContentHeight(height: Int) {
        layoutContent.layoutParams.height = height
        layoutContent.requestLayout()
    }

    private fun measureContentView() {
        val widthMS = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST)
        val heightMS = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        layoutContent.measure(widthMS, heightMS)
    }

    private fun animate(from: Int, to: Int) {
        val valuesHolder: PropertyValuesHolder = PropertyValuesHolder.ofInt("prop", from, to)

        val animator = ValueAnimator.ofPropertyValuesHolder(valuesHolder)
        animator.duration = expandDuration
        animator.addUpdateListener {
            val value = animator.getAnimatedValue("prop") as Int? ?: 0
            layoutContent.layoutParams.height = value
            layoutContent.requestLayout()
            invalidate()
        }
        animator.start()
    }

    /**
     * Check if the card is expanded
     */
    var isExpanded = false
        private set

    /**
     * Expand the Card
     */
    open fun expand(animate: Boolean) {
        if (isExpanded) return

        setHeightToContentHeight(animate)
        rotateArrow(180f, animate)
        isExpanded = true
    }

    /**
     * Collapse the Card
     */
    open fun collapse(animate: Boolean) {
        if (!isExpanded) return

        setHeightToZero(animate)
        rotateArrow(0f, animate)
        isExpanded = false
    }

    /**
     * @property cardTitle The title of the card
     */
    open var cardTitle: CharSequence
        get() = tvTitle.text
        set(title) {
            tvTitle.text = title
        }

    /**
     * Sets the title of the card
     * @param resId String resource to display as title
     * @see cardTitle
     */
    open fun setCardTitle(@StringRes resId: Int) {
        cardTitle = context.getString(resId)
    }

    /**
     * @property cardDescription The description of the card
     */
    open var cardDescription: CharSequence
        get() = tvDescription.text
        set(description) {
            tvDescription.text = description
        }

    /**
     * Sets the title of the card
     * @param resId String resource to display as description
     * @see cardDescription
     */
    open fun setCardDescription(@StringRes resId: Int) {
        cardDescription = context.getString(resId)
    }

    /**
     * @property expandDuration The duration of the expand animation
     * @throws IllegalArgumentException if the duration is <= 0
     */
    open var expandDuration: Long = 400
        set(duration) {
            if (duration > 0) {
                field = duration
            } else {
                throw IllegalArgumentException("Card Expand Duration can not be smaller than or equal to 0")
            }
        }

    /**
     * Set the action to be displayed
     * @param text Text to display for the action
     * @param listener Callback to be invoked when the action is clicked
     */
    open fun setAction(text: CharSequence, listener: View.OnClickListener) {
        btnAction.text = text
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener(listener)
    }

    /**
     * Set the action to be displayed
     * @param resId String resource to display for the action
     * @param listener Callback to be invoked when the action is clicked
     */
    open fun setAction(@StringRes resId: Int, listener: View.OnClickListener) {
        setAction(context.getString(resId), listener)
    }
}
