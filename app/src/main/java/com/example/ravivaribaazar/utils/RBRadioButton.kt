package com.example.ravivaribaazar.utils

import android.content.Context
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class RBRadioButton(context: Context, attributeSet: AttributeSet) : AppCompatRadioButton(context,attributeSet)
{
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}