package com.gy.ifanr.View.Widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.gy.ifanr.View.Fragment.FragmentAdapter;


/**
 * Created by gy939 on 2016/9/22.
 */
public class MyViewPage extends ViewPager{

    public MyViewPage(Context context) {
        super(context);
    }

    public MyViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 拦截ViewPage范围以外的Touch事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        FragmentAdapter adapter = (FragmentAdapter) getAdapter();
        int[] xy = new int[2];
        getLocationInWindow(xy);
        int x = xy[0];
        int y = xy[1];
        Log.e("gy","x="+x+"y="+y);
        if (ev.getRawY() < y){
            Log.e("gy",y+"-"+ev.getRawY());
            return true;
        }
        if (getWidth() - x < ev.getRawX()){
            //在右边
            Log.e("gy","右边");
            return true;
        }else if (x > ev.getRawX()){
            //在左边
            Log.e("gy","左边");
            return true;
        }else {
            //中间在区域内不做拦截
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 向ViewPage范围以外的子View分发Touch事件，
     * 因为设置了ClipChildren属性以后，子View已经可以超出父View的范围了
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        FragmentAdapter adapter = (FragmentAdapter) getAdapter();
        int fragno = adapter.getCurPosition();
        int[] xy = new int[2];
        getLocationInWindow(xy);
        int x = xy[0];
        int y = xy[1];
        if (getWidth() - x < ev.getRawX()){
            //在右边
            Log.e("gy","右边");
            //在右边将事件分发给当前Page的右边一个Page
            if (fragno < adapter.getCount() - 1)
                adapter.getItem(fragno + 1).getView().dispatchTouchEvent(ev);
        }else if (x > ev.getRawX()){
            //在左边
            Log.e("gy","左边");
            //在左边边将事件分发给当前Page的左边一个Page
            if (fragno > 0)
                adapter.getItem(fragno - 1).getView().dispatchTouchEvent(ev);
        }else {
            //中间
        }
        return super.onTouchEvent(ev);
    }


}
