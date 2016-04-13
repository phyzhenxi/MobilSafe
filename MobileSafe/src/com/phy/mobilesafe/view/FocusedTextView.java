package com.phy.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 获取焦点的TextView
 */
public class FocusedTextView extends TextView {
	//用代码new对象时,走此方法
	public FocusedTextView(Context context) {
		super(context);
	}
	//有style样式的话会走此方法
	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	//有属性时会走此方法
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
	}

	/**
	 * 表示有没获取焦点
	 * 跑马灯效果首先调用此函数判断是否获取焦点,是true,跑马灯才会有效果
	 * 所以我们不管实际上该空间是否有焦点,我们都强制返回true,让跑马灯跑起来
	 */
	@Override
	public boolean isFocused() {
		return true;
	}
	
}
