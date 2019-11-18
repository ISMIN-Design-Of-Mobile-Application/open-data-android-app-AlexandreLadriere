package com.ismin.opendataapp

import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi

fun curveImageViewCorner(image: ImageView, curveRadius: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        image.outlineProvider = object : ViewOutlineProvider() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(
                    0,
                    0,
                    (view!!.width + curveRadius).toInt(),
                    view.height,
                    curveRadius
                )
            }
        }
        image.clipToOutline = true
    }
}
