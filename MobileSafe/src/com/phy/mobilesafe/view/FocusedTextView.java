package com.phy.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * ��ȡ�����TextView
 */
public class FocusedTextView extends TextView {
	//�ô���new����ʱ,�ߴ˷���
	public FocusedTextView(Context context) {
		super(context);
	}
	//��style��ʽ�Ļ����ߴ˷���
	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	//������ʱ���ߴ˷���
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
	}

	/**
	 * ��ʾ��û��ȡ����
	 * �����Ч�����ȵ��ô˺����ж��Ƿ��ȡ����,��true,����ƲŻ���Ч��
	 * �������ǲ���ʵ���ϸÿռ��Ƿ��н���,���Ƕ�ǿ�Ʒ���true,�������������
	 */
	@Override
	public boolean isFocused() {
		return true;
	}
	
}
