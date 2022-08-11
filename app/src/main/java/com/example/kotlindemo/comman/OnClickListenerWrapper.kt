package com.example.kotlindemo.comman

import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils

class OnClickListenerWrapper(private val mListener: OnClickListener) : OnClickListener {
    private var mLastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = AnimationUtils.currentAnimationTimeMillis()
        if (currentTime - mLastClickTime > DEFAULT_MIN_INTERVAL) {
            mListener.onClick(v)
            mLastClickTime = currentTime
        }
    }

    companion object {
        private val DEFAULT_MIN_INTERVAL: Long = 500
    }
}
