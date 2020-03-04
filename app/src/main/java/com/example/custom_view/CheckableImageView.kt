package com.example.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import android.widget.ImageView


class CheckableImageView : ImageView, Checkable {

    private var checkedState = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun isChecked(): Boolean {
        return checkedState
    }

    override fun setChecked(state: Boolean) {
        if (state != checkedState) {
            checkedState = state
            refreshDrawableState()
        }
    }

    override fun toggle() {
        isChecked = !checkedState
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}