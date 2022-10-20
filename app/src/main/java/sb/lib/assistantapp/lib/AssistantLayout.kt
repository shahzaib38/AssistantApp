package sb.lib.assistantapp.lib

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.view.ViewCompat
import sb.lib.assistantapp.R

class AssistantLayout @JvmOverloads constructor(context: Context,attr:AttributeSet?=null ,defStyle:Int = 0): ViewGroup(context,attr,defStyle) {


    enum class Drawer{


        OPENED ,CLOSED

    }


    private var messagelayoutWidth: Int = 0

    private var animatedWidth :Int = 0

    private var minHeight: Int = 0
    private var topMargin = 8
    private var bottomMargin  = 8

    private var leftMaring = 30
    private var rightMargin = 15


    private var heightOfLayout = 0

    private var widthOfLayout =  0


    private var drawerView = Drawer.CLOSED


    override fun onMeasure(widthMeasureSpec: Int , heightMeasureSpec: Int) {


        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)


        for(i:Int in 0 until childCount step 1){

            val child = getChildAt(i)

            when( child.id ){

                R.id.imageCircleId -> {


                    val cover20PercentWidth = (widthSize * 22 ) / 100
                    val cover20PercentHeight = (heightSize * 22) / 100

                     minHeight =  Math.min(cover20PercentWidth ,cover20PercentHeight)

                    val widthMeasureSpecs = MeasureSpec.makeMeasureSpec(minHeight , MeasureSpec.EXACTLY)
                    val heightMeasureSpecs = MeasureSpec.makeMeasureSpec(minHeight , MeasureSpec.EXACTLY)

                    child.measure(widthMeasureSpecs, heightMeasureSpecs)

                }

                R.id.messageId -> {

                    val circleImage =    findViewById<ImageCircleView>(R.id.imageCircleId)
                    heightOfLayout =     circleImage.measuredHeight + bottomMargin + topMargin
                    val widthOfLayout =  widthSize - leftMaring - rightMargin - circleImage.measuredWidth/2

                    val heightOf = (heightOfLayout * 80)/100

                    val measureWidthSpecs = MeasureSpec.makeMeasureSpec(widthOfLayout , MeasureSpec.EXACTLY)
                    val measureHeightSpecs = MeasureSpec.makeMeasureSpec(heightOf , MeasureSpec.EXACTLY)

                    child.measure(measureWidthSpecs , measureHeightSpecs)

                }

            }
        }


        setMeasuredDimension(widthMeasureSpec ,heightOfLayout )

    }

    override fun onLayout(changed : Boolean, left : Int, top : Int, right  : Int, bottom   : Int) {


        for(i:Int in 0 until childCount step 1){

            val child = getChildAt(i)



            when(child.id){

                R.id.imageCircleId ->{
                   // child.bringToFront()
                    child.z = 10f


                    child.layout(leftMaring
                        , topMargin
                        , leftMaring + child.measuredWidth
                        , child.measuredHeight + bottomMargin)



                }

                R.id.messageId ->{


                    val leftXCordinate = leftMaring + findViewById<ImageCircleView>(R.id.imageCircleId).measuredWidth/2

                    messagelayoutWidth = child.measuredWidth

                   val topY = (height *20)/100
                    child.layout(leftXCordinate,topY/2, leftXCordinate + animatedWidth ,topY/2 + child.measuredHeight)


                }




            }



        }

    }


    private fun animateLayout(initalValue :Int ,EndValue :Int ){

        ValueAnimator.ofInt(initalValue,EndValue).apply {

            this.duration = 1000
            this.interpolator = LinearInterpolator()

            addListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                    if(drawerView == Drawer.CLOSED) {
                        drawerView = Drawer.OPENED

                    }else{
                        drawerView = Drawer.CLOSED


                    }
                }

                override fun onAnimationEnd(p0: Animator?) {
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }


            })
            addUpdateListener {

                animatedWidth = it.animatedValue as Int

                requestLayout()

            }
            start()
        }

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {


        val left = leftMaring
        val top = topMargin
        val right = leftMaring + minHeight
        val bottom = topMargin + minHeight

        if(event == null) return false

        when( event.action ){

            MotionEvent.ACTION_UP ->{

                if(event.x >= left && event.x<=right  && event.y >= top && event.y<=bottom){


                    if(drawerView == Drawer.CLOSED) {
                        animateLayout(0,  messagelayoutWidth - rightMargin )

                    }else if(drawerView == Drawer.OPENED){
                        animateLayout( messagelayoutWidth - rightMargin ,0)

                    }


                }

            }

        }


        return true

    }

}
