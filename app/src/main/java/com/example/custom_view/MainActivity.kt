package com.example.custom_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        left_toggle_button.setOnClickListener {
            right_checkable_button.isEnabled = !right_checkable_button.isEnabled
        }

        right_checkable_button.setOnClickListener {
            when (smile_view.state) {
                RoundButtonView.ON -> smile_view.state = RoundButtonView.OFF
                RoundButtonView.OFF -> smile_view.state = RoundButtonView.ON
            }
        }
    }
}
