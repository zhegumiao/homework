package com.ycq.homeworks.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import com.ycq.homeworks.R
import kotlin.random.Random

public val Int.dp : Float
    get() = this * Resources.getSystem().displayMetrics.density
public val Int.dpInt : Int
    get() = this * Resources.getSystem().displayMetrics.density.toInt()

public val Float.dp : Float
    get() = this * Resources.getSystem().displayMetrics.density

fun View.getAvatar(): Drawable {
    return this.resources.getDrawable(R.drawable.panda)
}

fun Context.getAvatar(size: Int): Bitmap{
    return decodeSampledBitmapFromResource(this.resources, R.drawable.panda,
        size, size
    )
}

fun View.getAvatar(size: Int): Bitmap{
    return decodeSampledBitmapFromResource(this.resources, R.drawable.panda,
        size, size
    )
}

fun decodeSampledBitmapFromResource(
    res: Resources,
    resId: Int,
    reqWidth: Int,
    reqHeight: Int
): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, this)

        // Calculate inSampleSize
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false

        BitmapFactory.decodeResource(res, resId, this)
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

val rnd = Random(System.currentTimeMillis())

/**
 * @return @ColorInt
 */
fun getRandomColor(): Int {
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

fun getRandomInt(util: Int): Int {
    return rnd.nextInt(util)
}