package com.gy.ifanr.View.Widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

/**
 * Created by gy939 on 16/9/21.
 */
public class MenuHelper {

	private PopupWindow popupWindow;
	private Context mContext;
	private View topView;
	private FrameLayout container;
	private View content;

	public MenuHelper(Context context, View topView,View content, FrameLayout containerView) {
		mContext = context;
		this.topView = topView;
		this.container = containerView;
		container.getForeground().setAlpha(0);
		this.content = content;
		initPopupWindow();
		
	}

	private void initPopupWindow() {
		popupWindow = new PopupWindow(content, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				if (container != null) {
					container.getForeground().setAlpha(0);
				}
			}
		});
	}
	
	public void showMenu() {
		if (popupWindow.isShowing()) {
			dismiss();
		}else {
			popupWindow.setOutsideTouchable(true);
			popupWindow.setTouchable(true);
			popupWindow.showAsDropDown(topView, 0, 0);
			if (container != null) {
				container.getForeground().setAlpha(120);
			}
		}
	}

	public void dismiss(){
		if (popupWindow.isShowing())
			popupWindow.dismiss();
	}

}
