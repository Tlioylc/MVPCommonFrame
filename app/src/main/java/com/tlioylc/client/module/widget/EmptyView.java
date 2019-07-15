package com.tlioylc.client.module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.tlioylc.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : tlioylc
 * e-mail : tlioylc@gmail.com
 * date   : 2019/5/9上午10:17
 * desc   : 空白UI展示
 */
public class EmptyView extends RelativeLayout {
    @BindView(R.id.empty_view_img)
    ImageView emptyViewImg;
    @BindView(R.id.empty_view_text)
    TextView emptyViewText;
    @BindView(R.id.empty_view_content)
    RelativeLayout emptyViewContent;
    @BindView(R.id.empty_view_reload_text)
    TextView emptyViewReloadText;

    public EmptyView(Context context) {
        super(context);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_empty_view, this);
        ButterKnife.bind(v);
    }

    public void updateView(String title, int rsd, OnClickListener onClickListener) {
        emptyViewImg.setImageResource(rsd);
        emptyViewText.setText(title);
        initClickView(onClickListener);
    }

    private void initClickView(OnClickListener onClickListener) {
        if (onClickListener == null) {
            emptyViewReloadText.setVisibility(GONE);
        } else {
            emptyViewReloadText.setVisibility(VISIBLE);
            emptyViewReloadText.setOnClickListener(onClickListener);
        }
    }

    public void setBg(int Color) {
        emptyViewContent.setBackgroundColor(Color);
    }

    public void updateView(int stringRsd, OnClickListener onClickListener) {
        emptyViewText.setText(stringRsd);
        initClickView(onClickListener);
        if(onClickListener == null){
            emptyViewImg.setImageResource(R.mipmap.ic_empty_view_icon);
        }else {
            emptyViewImg.setImageResource(R.mipmap.ic_empty_view_net_error);
        }
    }

    public void updateView(String title, OnClickListener onClickListener) {
        emptyViewText.setText(title);
        initClickView(onClickListener);
        if(onClickListener == null){
            emptyViewImg.setImageResource(R.mipmap.ic_empty_view_icon);
        }else {
            emptyViewImg.setImageResource(R.mipmap.ic_empty_view_net_error);
        }
    }
}
