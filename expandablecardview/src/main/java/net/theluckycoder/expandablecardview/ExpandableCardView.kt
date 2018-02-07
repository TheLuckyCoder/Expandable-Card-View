package net.theluckycoder.expandablecardview

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.transition.ChangeBounds
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.transition.TransitionSet
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

open class ExpandableCardView : CardView {

    private val tvTitle by bind<TextView>(R.id.tv_card_title)
    private val tvDescription by bind<TextView>(R.id.tv_card_desc)
    private val imgExpand by bind<ImageView>(R.id.iv_card_expand)
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
            cardTitle = typedArray.getString(R.styleable.ExpandableCardView_title) ?: ""
            cardDescription = typedArray.getString(R.styleable.ExpandableCardView_description) ?: ""
            setExpanded(typedArray.getBoolean(R.styleable.ExpandableCardView_expanded, false))

            typedArray.recycle()
        }

        setOnClickListener {
            setExpanded(!isExpanded)
        }
    }

    /**
     * @property cardTitle The title of the card
     */
    var cardTitle: CharSequence
        get() = tvTitle.text
        set(title) {
            tvTitle.text = title
        }

    /**
     * Sets the title of the card
     * @param resId String resource to display as title
     * @see cardTitle
     */
    fun setCardTitle(@StringRes resId: Int) {
        cardTitle = context.getString(resId)
    }

    /**
     * @property cardDescription The description of the card
     */
    var cardDescription: CharSequence
        get() = tvDescription.text
        set(description) {
            tvDescription.text = description
        }

    /**
     * Sets the title of the card
     * @param resId String resource to display as description
     * @see cardDescription
     */
    fun setCardDescription(@StringRes resId: Int) {
        cardDescription = context.getString(resId)
    }

    /**
     * Set the action to be displayed
     * @param text Text to display for the action
     * @param listener Callback to be invoked when the action is clicked
     */
    fun setAction(text: CharSequence, listener: View.OnClickListener) {
        btnAction.text = text
        btnAction.setOnClickListener(listener)
    }

    /**
     * Set the action to be displayed
     * @param resId String resource to display for the action
     * @param listener Callback to be invoked when the action is clicked
     */
    fun setAction(@StringRes resId: Int, listener: View.OnClickListener) {
        setAction(context.getString(resId), listener)
    }

    /**
     * Automatically expand or collapse the card when clicked
     * @param sceneRoot Required for animation
     */
    fun setExpandCollapseListener(sceneRoot: ViewGroup) {
        setOnClickListener {
            setExpanded(!isExpanded, sceneRoot)
        }
    }

    /**
     * Check if the card is expanded or not
     */
    open val isExpanded get() = tvDescription.visibility == View.VISIBLE

    /**
     * Expand or collapse the card
     * @param expand
     * @param sceneRoot Required for change bounds transition. Leave null to disable animation
     */
    open fun setExpanded(expand: Boolean, sceneRoot: ViewGroup? = null) {
        sceneRoot?.let {
            val transitionSet = TransitionSet().apply {
                addTransition(Fade())
                addTransition(ChangeBounds())
                duration = 400
            }
            TransitionManager.beginDelayedTransition(it, transitionSet)
        }

        if (isExpanded && !expand) {
            tvDescription.visibility = View.GONE
            btnAction.visibility = View.GONE
            rotateImage(0f)
        } else if (!isExpanded && expand) {
            tvDescription.visibility = View.VISIBLE
            if (btnAction.hasOnClickListeners()) {
                btnAction.visibility = View.VISIBLE
            }
            rotateImage(180f)
        }
    }

    private fun rotateImage(value: Float) {
        ViewCompat.animate(imgExpand)
            .rotation(value)
            .withLayer()
            .setDuration(500)
            .setInterpolator(OvershootInterpolator())
            .start()
    }

    private fun <T : View> View.bind(@IdRes res: Int): Lazy<T> =
        lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}
