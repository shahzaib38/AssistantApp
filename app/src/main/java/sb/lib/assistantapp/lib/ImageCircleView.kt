package sb.lib.assistantapp.lib

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import sb.lib.assistantapp.R

class ImageCircleView @JvmOverloads constructor(context : Context ,attr:AttributeSet?=null ,defStyle:Int =0)  : View( context , attr , defStyle ) {


    private val animtedPaint: Paint =Paint().apply {

        this.color = Color.parseColor("#B2DFDB")

    }

    private var image: Bitmap? =null
    private val paint = Paint().apply {

        this.isAntiAlias =true
        this.color = Color.parseColor("#00796B")

    }

    var animatedCenter = 0
    override fun onFinishInflate() {
        super.onFinishInflate()


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureWidthSize =  MeasureSpec.getSize(widthMeasureSpec)

        val measureHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val defaultSize = Math.min(measureWidthSize ,measureHeightSize)

        setMeasuredDimension(defaultSize ,defaultSize)
    }


    override fun onDraw(canvas: Canvas?) {

        if(canvas == null ) return

        val sphereSize = (width *80)/100


        canvas.drawCircle(width/2f,width/2f ,animatedCenter.toFloat() ,animtedPaint)

       canvas.drawCircle(width/2f ,width/2f ,sphereSize/2f ,paint)


        canvas.drawBitmap(image!! ,width/2f -image!!.width/2 ,width/2f - image!!.height/2 ,paint)


    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)



        image =  drawableToBitmap(context.resources.getDrawable(R.drawable.ic_headphones,null))





        

        val center = width/2
        ValueAnimator.ofInt(0,center).apply {

            this.duration = 1000
            this.startDelay = 500
            this.interpolator = LinearInterpolator()

            this.repeatMode = ValueAnimator.REVERSE
            this.repeatCount = ValueAnimator.INFINITE

            addUpdateListener {

                animatedCenter = it.animatedValue as Int

                invalidate()
            }

            start()
        }

    }

    private  fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }



}