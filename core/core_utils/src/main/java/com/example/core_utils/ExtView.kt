package com.example.core_utils

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(image: String) {
    Glide.with(this).load(image).into(this)
}
fun RadioButton.setupColorStateList() {
    if (Build.VERSION.SDK_INT >= 21) {
        val selectedColor = Color.parseColor("#FF9600")

        val unselectedColor = Color.parseColor("#E2E2E2")

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(R.attr.state_enabled, -R.attr.state_checked),
                intArrayOf(R.attr.state_enabled, R.attr.state_checked)
            ),
            intArrayOf(
                unselectedColor,
                selectedColor
            )
        )
        buttonTintList = colorStateList
    }
}

fun EditText.addTextChange(yesButton: Button, noButton: Button) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s.isNullOrEmpty()) {
                yesButton.visibility = View.INVISIBLE
                noButton.visibility = View.VISIBLE
            } else {
                yesButton.visibility = View.VISIBLE
                noButton.visibility = View.INVISIBLE
            }
        }
    })
}


