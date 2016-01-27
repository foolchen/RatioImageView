package com.foolchen.library.riv;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 按照比例布局的ImageView
 *
 * @author chenchong
 *         16/1/12
 *         上午11:48
 */
public class RatioImageView extends ImageView {
    private int mHorizontalWeight;
    private int mVerticalWeight;
    private int mBaseLine;
    private int mSpecifiedWidth, mSpecifiedHeight;

    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WIDTH, HEIGHT})
    public @interface Base {}

    public RatioImageView(Context context) {
        super(context);
        init(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
            mHorizontalWeight = ta.getInt(R.styleable.RatioImageView_horizontal_weight, 1);
            mVerticalWeight = ta.getInt(R.styleable.RatioImageView_vertical_weight, 1);
            mBaseLine = ta.getInt(R.styleable.RatioImageView_base, WIDTH);
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 0, desiredHeight = 0;
        if (mBaseLine == WIDTH) {
            if (mSpecifiedWidth != 0) {
                desiredWidth = mSpecifiedWidth;
            } else {
                desiredWidth = MeasureSpec.getSize(widthMeasureSpec);
            }
            desiredHeight = (int) ((float) desiredWidth / mHorizontalWeight * mVerticalWeight);
        }

        if (mBaseLine == HEIGHT) {
            if (mSpecifiedHeight != 0) {
                desiredHeight = mSpecifiedHeight;
            } else {
                desiredHeight = MeasureSpec.getSize(heightMeasureSpec);
            }
            desiredWidth = (int) ((float) desiredHeight / mVerticalWeight * mHorizontalWeight);
        }

        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    /** 设定View的宽度,此时View的基准会变为{@link #WIDTH} */
    public void setWidth(int width) {
        mSpecifiedWidth = width;
        setBase(WIDTH);
    }

    /** 设定View的高度,此时View的基准会变为{@link #HEIGHT} */
    public void setHeight(int height) {
        mSpecifiedHeight = height;
        setBase(HEIGHT);
    }

    /** 设置按比例布局的基准线 */
    public void setBase(@Base int base) {
        mBaseLine = base;
        requestLayout();
    }

    @Base
    /**获取当前的基准线*/
    public int getBase() {
        return mBaseLine;
    }
}