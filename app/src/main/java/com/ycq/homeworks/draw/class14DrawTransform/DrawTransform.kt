package com.ycq.homeworks.draw.class13DrawText

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.ycq.homeworks.util.dpInt
import com.ycq.homeworks.util.getAvatar
import kotlin.reflect.typeOf

/**
 * 平移，变换
 */
class DrawTransform(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    private val bitmap = getAvatar(50.dpInt)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val rect  = Rect()
    val rectF  = RectF()

    val rect1  = Rect()
    val rectF1  = RectF()

    val flipDegree = 30f
    private val clipPathTop = Path()
    private val clipPathBottom = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2f
        centerY = height / 2f
        offsetX = centerX - (bitmap.width / 2)
        offsetY = centerY - (bitmap.height / 2)
        rect.set(0, 0 , bitmap.width , bitmap.height / 2)
        rectF.set(offsetX, offsetY , offsetX + bitmap.width, offsetY + bitmap.height / 2)

        rect1.set(0, bitmap.height / 2 , bitmap.width , bitmap.height)
        rectF1.set(offsetX, offsetY + bitmap.height / 2 , offsetX + bitmap.width, offsetY + bitmap.height)

        clipPathTop.addRect(0f, 0f, width.toFloat(), centerY, Path.Direction.CW)
        clipPathBottom.addRect(0f, centerY, width.toFloat(), height.toFloat(), Path.Direction.CW)
    }

    private var cameraDegree = 0f
    set(value) {
        field = value
        invalidate()
    }

    private val animator  = ObjectAnimator.ofFloat(this, "cameraDegree",
    0f, 50f).apply {
        duration = 2000
    }
    init {
        setOnClickListener {
            animator.start()
        }
    }
    private val density = Resources.getSystem().displayMetrics.density
    private val camera = Camera().apply {
//        rotateX(degree)
//        rotateY(degree)
//        rotateZ(degree)
        setLocation(0f, 0f, -6 * density)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        translateAndScale(canvas)
//        skew(canvas)
//        rotateCamera(canvas)
        rotateCameraCanvasClip(canvas)

    }
    private fun translateAndScale(canvas: Canvas){
        canvas.save()
        canvas.translate(centerX, centerY)
        canvas.scale(1.5f, 1.5f)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        canvas.restore()
    }

    private fun skew(canvas: Canvas){
        canvas.save()
        canvas.skew(0.1f, 0.1f)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        canvas.restore()
    }

    /**
     * 非 canvas clip
     */
    private fun rotateCamera(canvas: Canvas){
        canvas.drawBitmap(bitmap, rect, rectF, paint)
        canvas.save()
        canvas.translate(centerX, centerY)
        camera.save()
        camera.rotateX(cameraDegree)
        camera.applyToCanvas(canvas)
        canvas.translate(-centerX, -centerY)
//        canvas.drawLine(0f, 0f, centerX, centerY, paint)
        canvas.drawBitmap(bitmap, rect1, rectF1, paint)
        camera.restore()
        canvas.restore()
    }

    /**
     * canvas clip
     */
    private fun rotateCameraCanvasClip(canvas: Canvas){
        canvas.save()
        canvas.rotate(-flipDegree, centerX, centerY)
        canvas.clipPath(clipPathTop)
        canvas.rotate(flipDegree, centerX, centerY)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
        canvas.restore()

        canvas.save()
        canvas.rotate(-flipDegree, centerX, centerY)
        canvas.translate(centerX, centerY)
        camera.save()
        camera.rotateX(cameraDegree)
        camera.applyToCanvas(canvas)
        canvas.translate(-centerX, -centerY)
        canvas.clipPath(clipPathBottom)
        canvas.rotate(flipDegree, centerX, centerY)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
        camera.restore()
        canvas.restore()
    }
}