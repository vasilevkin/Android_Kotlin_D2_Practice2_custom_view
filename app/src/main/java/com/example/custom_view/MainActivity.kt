package com.example.custom_view

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    var x = 0f
    var y = 0f
    var sDown: String? = null
    var sMove: String? = null
    var sUp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        left_toggle_button.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                right_checkable_button.isEnabled = !right_checkable_button.isEnabled
                state_text_view.setText("left_toggle_button                   " + "          " + "MotionEvent == ACTION_UP")
            }
            return@setOnTouchListener true
        }

        right_checkable_button.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        x = event?.x?.toFloat() ?: 0f
        y = event?.y?.toFloat() ?: 0f

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                sDown = "Down: $x,$y" + "        " + "MotionEvent == ACTION_DOWN"
                sMove = ""
                sUp = ""
            }
            MotionEvent.ACTION_MOVE -> {
                sDown = "                           " + "          " + "MotionEvent == ACTION_MOVE"
                sMove = "Move: $x,$y"
            }
            MotionEvent.ACTION_UP -> {
                sDown = "                           " + "          " + "MotionEvent == ACTION_UP"
                sMove = ""
                sUp = "Up: $x,$y"
                when (smile_view.state) {
                    RoundFaceView.ON -> smile_view.state = RoundFaceView.OFF
                    RoundFaceView.OFF -> smile_view.state = RoundFaceView.ON
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                sDown =
                    "                           " + "          " + "MotionEvent == ACTION_CANCEL"
            }
        }
        state_text_view.setText(sDown.toString() + "\n" + sMove + "\n" + sUp)
        return true
    }
}
