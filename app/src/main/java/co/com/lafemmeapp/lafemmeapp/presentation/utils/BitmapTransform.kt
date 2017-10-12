package co.com.lafemmeapp.lafemmeapp.presentation.utils

import com.squareup.picasso.Transformation
import android.graphics.Bitmap


/**
 * Created by oscarg798 on 9/14/17.
 */
class BitmapTransform(private val mMaxWidth: Int, private val mMaxHeight: Int) : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val targetWidth: Int
        val targetHeight: Int
        val aspectRatio: Double

        if (source.width > source.height) {
            targetWidth = mMaxWidth
            aspectRatio = source.height.toDouble() / source.width.toDouble()
            targetHeight = (targetWidth * aspectRatio).toInt()
        } else {
            targetHeight = mMaxHeight
            aspectRatio = source.width.toDouble() / source.height.toDouble()
            targetWidth = (targetHeight * aspectRatio).toInt()
        }

        val result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false)
        if (result != source) {
            source.recycle()
        }
        return result
    }

    override fun key(): String {
        return mMaxWidth.toString() + "x" + mMaxHeight
    }
}