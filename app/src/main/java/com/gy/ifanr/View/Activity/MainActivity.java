package com.gy.ifanr.View.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.gy.ifanr.R;
import com.gy.ifanr.View.Fragment.FirstFragment;
import com.gy.ifanr.View.Fragment.FragmentAdapter;
import com.gy.ifanr.View.Fragment.SecFragment;
import com.gy.ifanr.View.Fragment.ThrFragment;
import com.gy.ifanr.View.Listener.OnFragmentClick;
import com.gy.ifanr.View.Widget.MenuHelper;
import com.gy.ifanr.View.Widget.MyViewPage;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Animator.AnimatorListener,View.OnClickListener, OnFragmentClick {

    private List<Fragment> fragments;

    private MyViewPage viewPager;
    private RelativeLayout content;
    private TabLayout mTabLayout;
    private FrameLayout viewpagecontent;
    private FrameLayout vpParent;
    private float scale = 0.6f;
    private float x,y;
    private ImageButton menubt,scalebt;

    private boolean scalling = false;
    private boolean isScalled = false;

    private MenuHelper moremenu;
    private View moremenuView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        addFrags();
        setView();
    }

    private void findview(){
        viewPager = (MyViewPage) findViewById(R.id.view_pager);
        content = (RelativeLayout) findViewById(R.id.content);
        viewpagecontent = (FrameLayout) findViewById(R.id.frag_content);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        vpParent = (FrameLayout) findViewById(R.id.vp_content);
        menubt = (ImageButton) findViewById(R.id.btn_menu);
        scalebt = (ImageButton) findViewById(R.id.btn_scale);
        moremenuView = View.inflate(this,R.layout.grid_menu_layout,null);
    }

    private void setView(){
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(6);
        menubt.setOnClickListener(this);
        scalebt.setOnClickListener(this);
        moremenu = new MenuHelper(this,menubt,moremenuView,viewpagecontent);
        vpParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }


    private void addFrags(){
        List<String> titles = new ArrayList<>();
        titles.add("页面1");
        titles.add("页面2");
        titles.add("页面3");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecFragment());
        fragments.add(new ThrFragment());
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    //对ViewPage的父视图进行Y轴缩放，同时对ViewPage进行X轴缩放，保持x/y比例
    private void scaleViewPage(){
        x = vpParent.getWidth()/2 + vpParent.getX();
        y = vpParent.getHeight() + vpParent.getY();
        ViewPropertyAnimator.animate(vpParent).setListener(this).scaleY(scale);
        ViewPropertyAnimator.animate(viewPager).scaleX(scale);
        ViewHelper.setPivotX(viewPager, x);
        ViewHelper.setPivotY(viewPager, y);
        ViewHelper.setPivotX(vpParent, x);
        ViewHelper.setPivotY(vpParent, y);
        ViewHelper.setScaleX(viewPager,scale);
        ViewHelper.setScaleY(vpParent,scale);
    }

    //缩放还原
    private void restoreViewPage(){
        x = vpParent.getWidth()/2 + vpParent.getX();
        y = vpParent.getHeight() + vpParent.getY();
        ViewPropertyAnimator.animate(vpParent).setListener(this).scaleY(1);
        ViewPropertyAnimator.animate(viewPager).scaleX(1);
        ViewHelper.setPivotX(viewPager, x);
        ViewHelper.setPivotY(viewPager, y);
        ViewHelper.setPivotX(vpParent, x);
        ViewHelper.setPivotY(vpParent, y);
        ViewHelper.setScaleX(viewPager,1);
        ViewHelper.setScaleY(vpParent,1);
    }

    //相关视图元素进行控制
    @Override
    public void onAnimationStart(Animator animator) {
        scalling = true;
        if (!isScalled){
            mTabLayout.setVisibility(View.INVISIBLE);
            menubt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        scalling = false;
        isScalled = !isScalled;
        if (!isScalled){
            mTabLayout.setVisibility(View.VISIBLE);
            menubt.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scale:
                doScale();
                break;
            case R.id.btn_menu:
                moremenu.showMenu();
                break;
        }
    }

    private void doScale() {
        if (scalling)
            return;
        if (isScalled){
            restoreViewPage();
        }else {
            scaleViewPage();
        }
    }

    @Override
    public void OnFragClick(int no) {
        if (isScalled){
            viewPager.setCurrentItem(no);
            doScale();
        }
    }
}
