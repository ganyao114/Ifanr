package com.gy.ifanr.View.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gy.ifanr.R;
import com.gy.ifanr.View.Listener.OnFragmentClick;

/**
 * 基类Fragment，捕获Click事件以收起菜单
 * Created by gy939 on 2016/9/21.
 */
public abstract class BaseFragment extends Fragment{

    private View view;

    private OnFragmentClick fragmentClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_layout,container,false);
        //这里其实建议使用类似EventBus的消息框架做组件间通讯。
        fragmentClick = (OnFragmentClick) getActivity();
        view.findViewById(R.id.mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("gy","点击了"+hashCode());
                fragmentClick.OnFragClick(getNo());
            }
        });
        ((TextView)view.findViewById(R.id.hello)).setText(getClass().getName());
        return view;
    }

    protected abstract int getNo();

}
