package net.theluckycoder.expandablecardview

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.transition.TransitionManager
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ExpandableCardView : CardView {

    private val tvTitle by bind<TextView>(R.id.tv_card_title)
    private val tvDescription by bind<TextView>(R.id.tv_card_desc)
    private val imgExpand by bind<ImageView>(R.id.img_card_expand)
    private val btnAction by bind<Button>(R.id.btn_card_action)

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

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableCardView)
            setCardTitle(typedArray.getString(R.styleable.ExpandableCardView_title) ?: "")
            setCardDescription(typedArray.getString(R.styleable.ExpandableCardView_description) ?: "")

            typedArray.recycle()
        }

        setOnClickListener {
            expandCollapseCard(null)
        }
    }

    /**
     * Sets the title of the card
     * @param title The text to be set as title
     */
    fun setCardTitle(title: CharSequence) {
        tvTitle.text = title
    }

    /**
     * Sets the title of the card
     * @param resId String resource to display as title
     */
    fun setCardTitle(@StringRes resId: Int) {
        setCardTitle(context.getString(resId))
    }

    /**
     * Sets the description of the card
     * @param description The text to be set as description
     */
    fun setCardDescription(description: CharSequence) {
        tvDescription.text = description
    }

    /**
     * Sets the title of the card
     * @param resId String resource to display as description
     */
    fun setCardDescription(@StringRes resId: Int) {
        setCardDescription(context.getString(resId))
    }

    /**
     * Sets the ClickListener of the action button
     * @param text Text to display for the action
     * @param listener Callback to be invoked when the action is clicked
     */
    fun setAction(text: CharSequence, listener: View.OnClickListener) {
        btnAction.text = text
        btnAction.setOnClickListener(listener)
    }

    /**
     * Sets the ClickListener of the action button
     * @param resId String resource to display for the action
     * @param listener Callback to be invoked when the action is clicked
     */
    fun setAction(@StringRes resId: Int, listener: View.OnClickListener) {
        setAction(context.getString(resId), listener)
    }

    /**
     * Automatically expand or collapse the card when it`s clicked
     * @param sceneRoot Required for animation. Leave null to disable animation
     */
    fun setExpandCollapseListener(sceneRoot: ViewGroup?) {
        setOnClickListener {
            expandCollapseCard(sceneRoot)
        }
    }

    /**
     * Expand or collapse the card
     * @param sceneRoot Required for animation. Leave null to disable animation
     */
    fun expandCollapseCard(sceneRoot: ViewGroup?) {
        sceneRoot?.let {
            TransitionManager.beginDelayedTransition(it)
        }

        val rotation = if (tvDescription.visibility == View.VISIBLE) {
            tvDescription.visibility = View.GONE
            btnAction.visibility = View.GONE
            0f
        } else {
            tvDescription.visibility = View.VISIBLE
            if (btnAction.hasOnClickListeners()) {
                btnAction.visibility = View.VISIBLE
            }
            180f
        }

        ViewCompat.animate(imgExpand)
            .rotation(rotation)
            .withLayer()
            .setDuration(500)
            .setInterpolator(OvershootInterpolator())
            .start()
    }

    private fun <T : View> View.bind(@IdRes res: Int): Lazy<T> =
        lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}
