package com.example.custom_view

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View


class RoundButtonView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val INSIDE_COLOR = Color.YELLOW
        private const val TEXT_COLOR = Color.BLACK
        private const val BORDER_COLOR = Color.BLACK
        private const val BORDER_WIDTH = 4.0f

        const val OFF = 0L
        const val ON = 1L
    }

    private var faceColor = INSIDE_COLOR
    private var textColor = TEXT_COLOR
    private var borderColor = BORDER_COLOR
    private var borderWidth = BORDER_WIDTH

    private val paint = Paint()
    private val mouthPath = Path()
    private var size = 0

    var state = OFF
        set(state) {
            field = state
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        // Obtain a typed array of attributes
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.RoundButtonView,
            0, 0
        )

        // Extract custom attributes into member variables
        state = typedArray.getInt(R.styleable.RoundButtonView_state, OFF.toInt()).toLong()
        faceColor = typedArray.getColor(R.styleable.RoundButtonView_faceColor, INSIDE_COLOR)
        textColor = typedArray.getColor(R.styleable.RoundButtonView_textColor, TEXT_COLOR)
        borderColor = typedArray.getColor(R.styleable.RoundButtonView_borderColor, BORDER_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.RoundButtonView_borderWidth, BORDER_WIDTH)

        // TypedArray objects are shared and must be recycled.
        typedArray.recycle()
    }


    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFaceBackground(canvas: Canvas) {
        paint.color = faceColor
        paint.style = Paint.Style.FILL

        val radius = size / 2f

        canvas.drawCircle(size / 2f, size / 2f, radius, paint)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        paint.color = textColor
        paint.style = Paint.Style.FILL

        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        canvas.drawOval(leftEyeRect, paint)

        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)

        canvas.drawOval(rightEyeRect, paint)
    }

    private fun drawMouth(canvas: Canvas) {
        // Clear
        mouthPath.reset()

        mouthPath.moveTo(size * 0.22f, size * 0.7f)

        if (state == ON) {
            // Happy mouth path
            mouthPath.quadTo(size * 0.5f, size * 0.80f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.90f, size * 0.22f, size * 0.7f)
        } else {
            // Sad mouth path
            mouthPath.quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f)
        }

        paint.color = textColor
        paint.style = Paint.Style.FILL

        // Draw mouth path
        canvas.drawPath(mouthPath, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        size = Math.min(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()

        bundle.putLong("state", state)
        bundle.putParcelable("superState", super.onSaveInstanceState())

        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var viewState = state

        if (viewState is Bundle) {
            this.state = viewState.getLong("state", OFF)
            viewState = viewState.getParcelable("superState")!!
        }

        super.onRestoreInstanceState(viewState)
    }
}
