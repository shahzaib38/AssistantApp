package sb.lib.assistantapp.lib

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.graphics.toRectF

class MessageLayout @JvmOverloads constructor(context: Context , attr:AttributeSet?=null , defStyle:Int = 0) : ViewGroup(context,attr,defStyle) {


    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int , heightMeasureSpec: Int) {


        val widthSize  = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        println("width Size ${widthSize}")


        for(i:Int in 0 until  childCount step  1){

            val child = getChildAt(i)

            val widthOfLayout = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY)
            val heightOfLayout = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.EXACTLY)

            child.measure(widthOfLayout,heightOfLayout)


        }


        setMeasuredDimension(widthSize , heightSize)
    }



    private val paintStroke = Paint().apply {

        this.color = Color.parseColor("#0277BD")

    }


    private val paint = Paint().apply {

        this.color = Color.WHITE

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas ==null)return



        canvas.drawRoundRect(Rect(0,0,width,height).toRectF(),20f,20f,paintStroke)

        canvas.drawRoundRect(Rect(5,5,width-5,height-5).toRectF(),20f,20f,paint)



    }


    override fun onLayout(changed : Boolean, left  : Int, top   : Int, right : Int , bottom  : Int) {


        for(i:Int in 0 until childCount step 1){

            val child = getChildAt(i)

            child.layout(0,0 , right,bottom)

        }

    }


}