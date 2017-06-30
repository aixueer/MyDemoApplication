package xin.com.mydemoapplication.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * Created by zoudong on 2016/10/27.
 * 下拉刷新回弹效果
 */

public class StretchSwipeRefreshLayout extends SwipeRefreshLayout {
    private static final int MSG_REST_POSITION = 0x01;

    /**
     * The max scroll height.
     */
    private static final int MAX_SCROLL_HEIGHT = 400;
    /**
     * Damping, the smaller the greater the resistance
     */
    private static final float SCROLL_RATIO = 0.5f;
    private static final int INVALID_POINTER = -1;
    private View mChildRootView;
    private float mTouchY;
    private boolean mTouchStop = false;
    private int mTouchSlop;
    private int mActivePointerId = INVALID_POINTER;
    private int mScrollY = 0;
    private int mScrollDy = 0;
    private int offScrollY = 0;
    private Handler mHandler = new Handler() {

        private ValueAnimator mAnimator;

        @Override
        public void handleMessage(Message msg) {
            if (MSG_REST_POSITION == msg.what) {
                if (mScrollY != offScrollY && mTouchStop) {
                    if (mAnimator != null) {
                        mAnimator.cancel();
                    }
                    mAnimator = ValueAnimator.ofInt(mScrollY, offScrollY);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer animatedValue = (Integer) animation.getAnimatedValue();
                            if (animatedValue.intValue() == offScrollY) {
                                mAnimator = null;
                            } else {
                                mAnimator = animation;
                            }
                            mChildRootView.getLayoutParams().height = animatedValue.intValue();
                            mChildRootView.requestLayout();
                        }
                    });
                    mAnimator.start();
                }
            }
        }
    };
    private boolean mIsBeingDragged;

    public StretchSwipeRefreshLayout(Context context) {
        super(context);

        init();
    }

    public StretchSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void setPathView(View childRootView) {
        mChildRootView = childRootView;
        offScrollY = mChildRootView.getLayoutParams().height;
    }

    private void init() {
        // set scroll mode
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        offScrollY = dip2px(getContext(), 50);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setProgressViewOffset(false, dip2px(getContext(), 50), dip2px(getContext(), 150));
    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (isEnabled() && mChildRootView != null) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                    float initialDownY = getMotionEventExY(ev, mActivePointerId);
                    if (initialDownY == -1) {
                        initialDownY = 0F;
                    }
                    mIsBeingDragged = false;
                    mTouchY = initialDownY;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mActivePointerId != INVALID_POINTER) {
                        float y = getMotionEventExY(ev, mActivePointerId);
                        if (y == -1) {
                            y = 0;
                        }
                        float yDiff = y - mTouchY;
                        if (yDiff > mTouchSlop && !mIsBeingDragged) {
                            mTouchY = mTouchY + mTouchSlop;
                            mIsBeingDragged = true;
                        }
                    }
                    break;

                case MotionEventCompat.ACTION_POINTER_UP:
                    onSecondaryPointerUp(ev);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIsBeingDragged = false;
                    mActivePointerId = INVALID_POINTER;
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (null != mChildRootView && isEnabled()) {
            doTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void doTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex = -1;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                break;

            case MotionEvent.ACTION_MOVE: {
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    return;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollTop = (mTouchY - y) * SCROLL_RATIO;
                mTouchY = y;
                if (mIsBeingDragged) {
                    mChildRootView.getLayoutParams().height += -overscrollTop * SCROLL_RATIO;
                    mChildRootView.requestLayout();
                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    return;
                }
                mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP: {
//                mScrollY = mChildRootView.getScrollY();
                mScrollY = mChildRootView.getLayoutParams().height;
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                if (mScrollY != offScrollY) {
                    mTouchStop = true;
                    mScrollDy = (int) (mScrollY / 20.0f);
                    mHandler.sendEmptyMessage(MSG_REST_POSITION);
                }
            }
        }

    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private float getMotionEventExY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private boolean isNeedMove() {
        int viewHeight = mChildRootView.getMeasuredHeight();
        int scrollHeight = getHeight();
        int offset = viewHeight - scrollHeight;
        int scrollY = getScrollY();

        return scrollY == 0 || scrollY == offset;
    }

}
