package com.hzm.vdrawer;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hzm on 2018/1/5. 垂直的DrawerLayout
 */

public class VDrawerLayout extends ViewGroup {

    private View mBottomContentView;
    private View mDrawerView;
    ViewDragHelper dragHelper;

    ViewDragHelper.Callback cb=new ViewDragHelperCallBack();

    public VDrawerLayout(Context context) {
       this(context,null);
    }

    public VDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        dragHelper= ViewDragHelper.create(this, 2.0f, cb);
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
    }

   private class ViewDragHelperCallBack extends ViewDragHelper.Callback {

       @Override
       public boolean tryCaptureView(View child, int pointerId) {
           return child == mDrawerView;
       }

       @Override
       public void onEdgeTouched(int edgeFlags, int pointerId) {
           if (edgeFlags== ViewDragHelper.EDGE_BOTTOM){
               dragHelper.captureChildView(mDrawerView,pointerId);
           }
       }

       @Override
       public int clampViewPositionVertical(View child, int top, int dy) {
           return Math.min(Math.max(top, 0), mDrawerView.getHeight());
       }


       @Override
       public void onViewReleased(View releasedChild, float xvel, float yvel) {
            float movePrecent = (releasedChild.getHeight() + releasedChild.getTop()) / (float) releasedChild.getHeight();
            int finalTop = ( movePrecent < 1.5f) ? 0 : releasedChild.getHeight();
            dragHelper.settleCapturedViewAt(releasedChild.getLeft(), finalTop);
            invalidate();
       }
   }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mBottomContentView=getChildAt(0);
        mDrawerView=getChildAt(1);

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mBottomContentView.layout(l,t,r,b);
        mDrawerView.layout(l,t,r,b);
    }


    public void openDrawer(){
        dragHelper.smoothSlideViewTo(mDrawerView,mDrawerView.getLeft(),0);
        invalidate();
    }

    public void closeDrawer(){
        dragHelper.smoothSlideViewTo(mDrawerView,mDrawerView.getLeft(),mDrawerView.getHeight());
        invalidate();
    }
}
