package com.example.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.utils.DensityUtil;

public class SlideItemView extends FrameLayout {
    private static final String TAG = "SlideItemView";
    private View mItemContentView;
    private View mDeleteView;
    private int mItemContentWidth;
    private int mDeleteWidth;
    //高度一样
    private int mViewHeight;
    //delete距itemContent的左边距为8dp
    private final int marginLeftDp = 8;
    private int marginLeftPx;
    private Scroller mScroller;
    private OnStateChangeListener mOnStateChangeListener;

    //防止move事件运算
    private int mDeleteItemWidth;

    public SlideItemView(@NonNull Context context) {
        this(context, null);
    }

    public SlideItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlideItemView(@NonNull Context context, @Nullable  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //添加在layout xml里面的view 被LayoutInflater 解析完addview 之后再回调的onFinishInflate 方法。
    //而这个方法的执行顺序，仅次于构造函数，比measure和layout都要早
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mItemContentView = getChildAt(0);
        mDeleteView = getChildAt(1);
        marginLeftPx = DensityUtil.dp2px(getContext(), marginLeftDp);
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mItemContentWidth = mItemContentView.getMeasuredWidth();
        mDeleteWidth = mDeleteView.getMeasuredWidth();
        mViewHeight = mDeleteView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mDeleteItemWidth =  mDeleteWidth + marginLeftPx;
        //指定删除btn位置
        mDeleteView.layout(mItemContentWidth + marginLeftPx, getScrollY(), mItemContentWidth + mDeleteItemWidth, mViewHeight);
    }

    private float mStartX;
    private float mStartY;
    private float mDownX;
    private float mDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = mStartX = ev.getX();
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onDown(this);
                }
                Log.e(TAG, "onInterceptTouchEvent: ");
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                //与ScrollX保持一致
                mStartX = endX;
                if (Math.abs(endX - mDownX) > 8) {
                    isIntercept = true;
                }
                break;
            default:
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = mStartX = event.getX();
                mDownY = mStartY = event.getY();
                Log.e(TAG, "onTouchEvent: ");
                break;
            case MotionEvent.ACTION_MOVE:
                //反拦截
                if (Math.abs(mDownX) > Math.abs(mDownY) && Math.abs(mDownX) > 8) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }

                float endX = event.getX();
                float endY = event.getY();
                //与ScrollX保持一致
                float dX = mStartX - endX;
                float toScrollX = getScrollX() + dX;
                //限制拖动范围
                if (toScrollX < 0.0f) {
                    toScrollX = 0.0f;
                } else if (toScrollX > mDeleteItemWidth) {
                    toScrollX = mDeleteItemWidth;
                }
                scrollTo((int) toScrollX, getScrollY());
                mStartX = endX;
                mStartY = endY;
                break;
            case MotionEvent.ACTION_UP:
                int distanceX = getScrollX();
                //松开时定义回弹
                if (distanceX > mDeleteWidth / 2) {
                    openDelItem();
                } else {
                    closeDelItem();
                }
                break;
            default:
        }
        return true;
    }

    /**
     * 回弹--关闭
     */
    public void closeDelItem() {
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onClose(this);
        }

        mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), getScrollY());
        invalidate();
    }


    /**
     * 回弹--打开  注：此处未计算Y 没必要  实际上 其他close也没必要 得到的getScroll其实为0
     */
    public void openDelItem() {
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onOpen(this);
        }

        int dX = mDeleteItemWidth - getScrollX();
        mScroller.startScroll(getScrollX(), getScrollY(), dX, getScrollY());
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public interface OnStateChangeListener {
        void onDown(SlideItemView view);
        void onOpen(SlideItemView view);
        void onClose(SlideItemView view);
    }

    public void setOnStateChangeListener(OnStateChangeListener stateChangeListener) {
        mOnStateChangeListener = stateChangeListener;
    }
}
