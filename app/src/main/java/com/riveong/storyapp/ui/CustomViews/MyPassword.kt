package com.riveong.storyapp.ui.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.riveong.storyapp.R
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.core.text.set

class MyPassword : AppCompatEditText {

    private lateinit var clearButtonImage: Drawable


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Password must at least be 8 length"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        maxLines = 1
        transformationMethod = PasswordTransformationMethod.getInstance()
    }


    private fun init() {
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.the_absolute) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.length < 8) {
                    error = context.getString(R.string.need_more_char_8)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 8) {
                    error = context.getString(R.string.need_more_char_8)
                }


            }

            override fun afterTextChanged(s: Editable) {
                if (s.length < 8) {
                    error = context.getString(R.string.need_more_char_8)
                }


            }
        })
    }

    private fun roasting8Length() {
        text?.clear()
        setText(R.string.need_more_char_8)
    }

}