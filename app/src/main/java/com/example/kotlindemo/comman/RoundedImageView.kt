package com.example.kotlindemo.comman

import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.Config
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet

class RoundedImageView : androidx.appcompat.widget.AppCompatImageView {


    internal lateinit var mContext: Context

    constructor(context: Context) : super(context) {

        this.mContext = context

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onDraw(canvas: Canvas) {

        val drawable = drawable ?: return

        if (width == 0 || height == 0) {
            return
        }

        val b = (drawable as BitmapDrawable).bitmap

        try {
            val bitmap = b.copy(Config.ARGB_8888, true)
            val w = width
            val h = height
            val roundBitmap = getCroppedBitmap(bitmap, w)
            canvas.drawBitmap(roundBitmap, 0f, 0f, null)
        } catch (e: Exception) {


            val w = width
            val h = height


            val roundBitmap = getCroppedBitmap(b, w)
            canvas.drawBitmap(roundBitmap, 0f, 0f, null)// TODO: handle exception
        }


    }

    companion object {

        fun getCroppedBitmap(bmp: Bitmap, radius: Int): Bitmap {
            val sbmp: Bitmap
            if (bmp.width != radius || bmp.height != radius)
                sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false)
            else
                sbmp = bmp
            val output = Bitmap.createBitmap(sbmp.width, sbmp.height, Config.ARGB_8888)
            val canvas = Canvas(output)
            val shader = BitmapShader(sbmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val paint = Paint()
            val rect = Rect(0, 0, sbmp.width, sbmp.height)

            paint.isAntiAlias = true
            paint.shader = shader
            paint.isFilterBitmap = true
            paint.isDither = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = Color.parseColor("#BAB399")
            canvas.drawCircle(sbmp.width / 2 + 0.7f, sbmp.height / 2 + 0.7f, sbmp.width / 2 + 0.1f, paint)
            paint.xfermode = PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(sbmp, rect, rect, paint)


            paint.shader = null
            paint.style = Paint.Style.STROKE
            paint.color = Color.parseColor("#99C9C9C9")
            paint.strokeWidth = 7f
            //paint.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
            canvas.drawCircle(sbmp.width / 2 + 0.7f, sbmp.height / 2 + 0.7f, sbmp.width / 2 + 0.1f, paint)


            /////////////////////////////////////////

            return output
        }
    }


}
