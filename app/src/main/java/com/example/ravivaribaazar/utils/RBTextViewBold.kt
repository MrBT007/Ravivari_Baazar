package com.example.ravivaribaazar.utils

import android.content.AttributionSource
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class RBTextViewBold(context:Context,attrs:AttributeSet):AppCompatTextView(context,attrs)
{
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}