package com.xrd.mycustomgrid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WJ on 2019/4/19.
 */

public class MyCustomGridView extends ViewGroup {

    /**
     * 显示的行数 默认3列
     */
    private int mColumnCount=3;
    /**
     * 默认间距
     */
    private final float DEFAULT_SPACING = 2.5f;
    private float mSpacing;

    private int mItemWidth;
    private int mItemHeight;
    private Context mContext;
    private float mRowSpacing;
    private float mColumnSpacing;

    public MyCustomGridView(Context context) {
        this(context,null);
    }

    public MyCustomGridView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MyCustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        mSpacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_SPACING,
                context.getResources().getDisplayMetrics());
        mRowSpacing=mSpacing;
        mColumnSpacing=mSpacing;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = MeasureSpec.getSize(widthMeasureSpec);

            mItemWidth = (int) ((width - getPaddingLeft() - getPaddingRight() - (mColumnCount-1) * mSpacing) / mColumnCount);
            View childView = getChildAt(0);
            childView.measure(0,0);
            int measuredHeight = childView.getMeasuredHeight();
//            mItemHeight = mSetItemHeight==0?(int) (mItemWidth / mItemAspectRatio):mSetItemHeight;
            mItemHeight = measuredHeight;

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = mItemWidth;
            layoutParams.height = mItemHeight;
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    getDesiredHeight(mItemHeight), MeasureSpec.EXACTLY);
        }

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(
                    getDesiredWidth(mItemWidth), MeasureSpec.EXACTLY), heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec,
                                int parentHeightMeasureSpec) {
        final LayoutParams lp = child.getLayoutParams();
        //获取子控件的宽高约束规则
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    private int getDesiredHeight(int mItemHeight) {
        int totalHeight = getPaddingTop() + getPaddingBottom();
        int count = getChildCount();
        if (count > 0) {
            int row = (count - 1) / mColumnCount;
            totalHeight = (int) ((row + 1) * mItemHeight + (row) * mColumnSpacing) + totalHeight;
        }
        return totalHeight;
    }

    private int getDesiredWidth(int mItemWidth) {
        int totalWidth = getPaddingLeft() + getPaddingRight();
        int count = getChildCount();
        if (count > 0) {
            if (count < mColumnCount) {
                totalWidth = (int) (count * mItemWidth + (count - 1) * mRowSpacing) + totalWidth;
            } else {
                totalWidth = (int) (count * mItemWidth + (count - 1) * mRowSpacing) + totalWidth;
            }

        }
        return totalWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);

            int column = i % mColumnCount;
            int row = i / mColumnCount;
            int left = (int) (getPaddingLeft() + column * (mRowSpacing + mItemWidth));
            int top = (int) (getPaddingTop() + row * (mColumnSpacing + mItemHeight));

            view.layout(left, top, left + mItemWidth, top + mItemHeight);
        }
    }

    public MyCustomGridView setSpaceing(float rowSpacing,float columnSpacing) {
        mRowSpacing = rowSpacing;
        mColumnSpacing=columnSpacing;
        invalidate();
        return this;
    }
    public MyCustomGridView setColumnCount(int count){
        mColumnCount=count;
        return this;
    }

    public void addItemView(View view){
        addView(view);
    }
}
