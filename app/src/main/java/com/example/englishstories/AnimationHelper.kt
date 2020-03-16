package com.example.englishstories

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation

object AnimationHelper {

    fun animate(holderItemView: View){
        val anim = TranslateAnimation(-100f, 0.0f, 0.0f, 0.0f)
        anim.duration = 1000
        holderItemView.startAnimation(anim)

    }
}