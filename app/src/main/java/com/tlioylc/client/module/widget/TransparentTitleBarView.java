package com.tlioylc.client.module.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.ConvertUtils;
import com.tlioylc.client.R;
import com.tlioylc.client.module.base.BaseConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/5/9上午11:40
 * desc   : 通用顶部title
 */
public class TransparentTitleBarView extends RelativeLayout {
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
    public static final int RIGHT_IMG_BTN_3 = R.id.title_bar_right_img3;
    @BindView(R.id.title_bar_main_title_img)
    ImageView titleBarMainTitleImg;
    @BindView(R.id.title_bar_right_img2)
    ImageView titleBarRightImg2;
    @BindView(R.id.title_bar_right_img3)
    ImageView titleBarRightImg3;
    @BindView(R.id.title_bar_content)
    RelativeLayout titleBarContent;
    @BindView(R.id.title_bar_main_second_title_text)
    TextView titleBarMainSecondTitleText;

    public TransparentTitleBarView(Context context) {
        this(context, null);
    }

    public TransparentTitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransparentTitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TransparentTitleBar, defStyleAttr, 0);

        String strR = typedArray.getString(R.styleable.TransparentTitleBar_right_text);
        titleBarRightText.setTextColor(typedArray.getColor(R.styleable.TransparentTitleBar_right_text_color, 0xFF333333));
        if (!TextUtils.isEmpty(strR)) {
            titleBarRightText.setText(strR);
            titleBarRightText.setVisibility(VISIBLE);
        }

        titleBarMainTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable.TransparentTitleBar_title_size, ConvertUtils.dp2px(18)));
        titleBarMainTitleText.setText(typedArray.getString(R.styleable.TransparentTitleBar_title));
        titleBarMainTitleText.setTextColor(typedArray.getColor(R.styleable.TransparentTitleBar_title_text_color, 0xFF333333));
        int leftImg = typedArray.getResourceId(R.styleable.TransparentTitleBar_left_img, -1);
        if (leftImg != -1) {
            titleBarLeftImg.setVisibility(VISIBLE);
            titleBarLeftImg.setImageResource(leftImg);
        }
        int rightImg = typedArray.getResourceId(R.styleable.TransparentTitleBar_right_img, 0);
        if (rightImg != -1) {
            titleBarRightImg.setVisibility(VISIBLE);
            titleBarRightImg.setImageResource(rightImg);
        }
        titleBarContent.setBackgroundColor(typedArray.getColor(R.styleable.TransparentTitleBar_title_bg, 0xFFFFFFFF));

        String leftImageClick = typedArray.getString(R.styleable.TransparentTitleBar_left_image_click);
        if (null != leftImageClick) {
            setTitleBarLeftImgClick(new DeclaredOnClickListener(this, leftImageClick));
        }

        String rightImageClick = typedArray.getString(R.styleable.TransparentTitleBar_right_image_click);
        if (null != rightImageClick) {
            setTitleBarRightImgClick(new DeclaredOnClickListener(this, rightImageClick));
        }

        String rightTextClick = typedArray.getString(R.styleable.TransparentTitleBar_right_text_click);
        if (null != rightTextClick) {
            setTitleBarRightTextClick(new DeclaredOnClickListener(this, rightTextClick));
        }

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

    public void setMarginTop(int color, int marginTop) {
        this.setTitleBg(color);
        MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
        layoutParams.setMargins(0, marginTop, 0, 0);
    }
    public void setMarginTop(int marginTop) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
        layoutParams.setMargins(0, marginTop, 0, 0);
    }

    public void setTitleBarLeftImg(int rsd, OnClickListener onClick) {
        titleBarLeftImg.setVisibility(VISIBLE);
        titleBarLeftImg.setImageResource(rsd);
        titleBarLeftImg.setOnClickListener(onClick);
    }

    public void setTitleBarLeftImgClick(OnClickListener onClick) {
        titleBarLeftImg.setOnClickListener(onClick);
    }

    public void setTitleBarRightImgClick(OnClickListener onClick) {
        titleBarRightImg.setOnClickListener(onClick);
    }

    public void setTitleBarRightTextClick(OnClickListener onClick) {
        titleBarRightText.setOnClickListener(onClick);
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

    public void setTitleBarRightText(String rsd) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
    }

    public void setTitleBarRightText(String rsd, OnClickListener onClick, String color) {
        titleBarRightText.setVisibility(VISIBLE);
        titleBarRightText.setText(rsd);
        titleBarRightText.setTextColor(Color.parseColor(color));
        titleBarRightText.setOnClickListener(onClick);
    }

    public void hideRightText() {
        titleBarRightText.setVisibility(GONE);
    }

    public void setRightTextColor(int color) {
        titleBarRightText.setTextColor(color);
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

    public void setTitleBarRightImg3(int rsd, OnClickListener onClick) {
        titleBarRightImg3.setVisibility(VISIBLE);
        titleBarRightImg3.setImageResource(rsd);
        titleBarRightImg3.setOnClickListener(onClick);
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
        titleBarContent.setBackgroundColor(Color.parseColor(color));
    }

    public void setTitleBg(int color) {
        titleBarContent.setBackgroundColor(color);
    }

    public void setTitleBgRes(int resId) {
        titleBarContent.setBackgroundResource(resId);
    }

    public void setTitle(String title) {
        titleBarMainTitleText.setText(title);
    }

    public String getTitleStr(){
        return titleBarMainTitleText.getText().toString();
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

    public void setSecondTitle (String secondTitle) {
        if (TextUtils.isEmpty(secondTitle)) {
            titleBarMainSecondTitleText.setVisibility(View.GONE);
            return;
        }
        titleBarMainSecondTitleText.setVisibility(View.VISIBLE);
        titleBarMainSecondTitleText.setText(secondTitle);
    }


    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;

        private Method mResolvedMethod;
        private Context mResolvedContext;

        public DeclaredOnClickListener(@NonNull View hostView, @NonNull String methodName) {
            mHostView = hostView;
            mMethodName = methodName;
        }

        @Override
        public void onClick(@NonNull View v) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView.getContext(), mMethodName);
            }

            try {
                mResolvedMethod.invoke(mResolvedContext, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for app:right_image_click|left_image_click", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for app:right_image_click|left_image_click", e);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        final Method method = context.getClass().getMethod(mMethodName, View.class);
                        if (method != null) {
                            mResolvedMethod = method;
                            mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    // Failed to find method, keep searching up the hierarchy.
                }

                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }

            final int id = mHostView.getId();
            final String idText = id == NO_ID ? "" : " with id '"
                    + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + "(View) in a parent or ancestor Context for aapp:right_image_click|left_image_click "
                    + "attribute defined on view " + mHostView.getClass() + idText);
        }
    }
}
