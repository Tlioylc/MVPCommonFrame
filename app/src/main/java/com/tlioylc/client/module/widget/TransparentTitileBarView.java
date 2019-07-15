package com.tlioylc.client.module.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.tlioylc.client.R;
import com.tlioylc.client.module.bese.BaseConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/5/9上午11:40
 * desc   : 通用顶部title
 */
public class TransparentTitileBarView extends RelativeLayout {
    @BindView(R.id.title_bar_main_title_text)
    TextView titleBarMainTitleText;
    @BindView(R.id.title_bar_left_img)
    ImageView titleBarLeftImg;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_right_img)
    ImageView titleBarRightImg;


    public static final int RIGHT_TEXT_BTN = R.id.title_bar_right_text;
    public static final int LEFT_IMG_BTN = R.id.title_bar_left_img;
    public static final int RIGHT_IMG_BTN_1 = R.id.title_bar_right_img;
    public static final int RIGHT_IMG_BTN_2 = R.id.title_bar_right_img2;
    @BindView(R.id.title_bar_main_title_img)
    ImageView titleBarMainTitleImg;
    @BindView(R.id.title_bar_right_img2)
    ImageView titleBarRightImg2;
    @BindView(R.id.title_bar_left_text)
    TextView titleBarLeftText;
    @BindView(R.id.title_bar_system_status_height)
    View titleBarSystemStatusHeight;

    public TransparentTitileBarView(Context context) {
        super(context);
        initView();
    }

    public TransparentTitileBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TransparentTitileBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER_HORIZONTAL);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_transparent_title_bar, this);
        ButterKnife.bind(v);

        int high = (int) getResources().getDimension(R.dimen.bttm_bar_high);
        setMinimumHeight(high);

        TextPaint tp = titleBarMainTitleText.getPaint();
        tp.setFakeBoldText(true);
    }

    public void showStatusBar(){
        titleBarSystemStatusHeight.setVisibility(VISIBLE);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BaseConfig.INSTANCE.getSTATUS_BAR_SAFE_HEIGHT());
        titleBarSystemStatusHeight.setLayoutParams(layoutParams);
    }

    public void setTitleBarLeftImg(int rsd, OnClickListener onClick) {
        titleBarLeftImg.setVisibility(VISIBLE);
        titleBarLeftImg.setImageResource(rsd);
        titleBarLeftImg.setOnClickListener(onClick);
    }

    public void setTitleBarRightText(int rsd, OnClickListener onClick) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
        titleBarRightText.setOnClickListener(onClick);
    }

    public void setTitleBarRightText(String rsd, OnClickListener onClick) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
        titleBarRightText.setOnClickListener(onClick);
    }

    public void setTitleBarRightText(int rsd) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
    }
    public void setTitleBarRightText(String rsd) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
    }
    public TextView getRightTv() {
        return titleBarRightText;
    }


    public void setTitleBarRightText(String rsd, OnClickListener onClick, String color) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
        titleBarRightText.setTextColor(Color.parseColor(color));
        titleBarRightText.setOnClickListener(onClick);
    }

    public void setTitleBarLeftText(int rsd, OnClickListener onClick) {
        titleBarLeftText.setVisibility(VISIBLE);
        titleBarLeftText.setText(rsd);
        titleBarLeftText.setOnClickListener(onClick);
    }

    public void setTitleBarLeftText(String rsd, OnClickListener onClick) {
        titleBarLeftText.setVisibility(VISIBLE);
        titleBarLeftText.setText(rsd);
        titleBarLeftText.setOnClickListener(onClick);
    }

    public void setTitleBarLeftText(String rsd, OnClickListener onClick, String color) {
        titleBarLeftText.setVisibility(VISIBLE);
        titleBarLeftText.setText(rsd);
        titleBarLeftText.setTextColor(Color.parseColor(color));
        titleBarLeftText.setOnClickListener(onClick);
    }

    public void hideRightText() {
        titleBarRightText.setVisibility(GONE);
    }

    public void setTitleBarRightImg(int rsd, OnClickListener onClick) {
        titleBarRightImg.setVisibility(VISIBLE);
        titleBarRightImg.setImageResource(rsd);
        titleBarRightImg.setOnClickListener(onClick);
    }

    public void setTitleBarRightImg2(int rsd, OnClickListener onClick) {
        titleBarRightImg2.setVisibility(VISIBLE);
        titleBarRightImg2.setImageResource(rsd);
        titleBarRightImg2.setOnClickListener(onClick);
    }

    public void updateRightText(String rsd) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
    }

    public void setTitleBarMainTitleImg(int rsd) {
        titleBarMainTitleImg.setVisibility(VISIBLE);
        titleBarMainTitleImg.setImageResource(rsd);
    }

    public void setTitleBg(String color) {
         setBackgroundColor(Color.parseColor(color));
    }

    public void setTitleBg(int color) {
         setBackgroundColor(color);
    }

    public void setTitle(String title) {
        titleBarMainTitleText.setText(title);
    }

    public void setTitleColor(String color) {
        titleBarMainTitleText.setTextColor(Color.parseColor(color));
    }

    public void setTitleColor(int color) {
        titleBarMainTitleText.setTextColor(color);
    }

    public void setTitle(int title) {
        titleBarMainTitleText.setText(title);
    }
}
