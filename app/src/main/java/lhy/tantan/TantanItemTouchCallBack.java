package lhy.tantan;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

/**
 * Created by Liheyu on 2017/6/20.
 * Email:liheyu999@163.com
 */

public class TantanItemTouchCallBack extends ItemTouchHelper.Callback {

    private ItemTouchHelperListener itemHelperListener;
    private RecyclerView recyclerView;

    public TantanItemTouchCallBack(RecyclerView recyclerView, ItemTouchHelperListener itemHelperListener) {
        this.recyclerView = recyclerView;
        this.itemHelperListener = itemHelperListener;
    }

    // 触摸就会掉用，0代表不执行，失效
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.e("getMovementFlags", recyclerView.getAdapter().getItemCount() - 1 + "");
        int dragFlags = 0/*ItemTouchHelper.START|ItemTouchHelper.END*/;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN;

        //只有最上面的那层才响应事件，其它层不响应
        int adapterPosition = viewHolder.getAdapterPosition();
        Log.e("ddd", adapterPosition + "");
        Log.e("ddd", recyclerView.getAdapter().getItemCount() - 1 + "");
        if (adapterPosition != recyclerView.getAdapter().getItemCount() - 1) {
            swipeFlags = 0;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    //交互结束清除所有状态
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setTranslationY(0);
        viewHolder.itemView.setTranslationX(0);
        viewHolder.itemView.setRotation(0);
        viewHolder.itemView.setScaleX(1);
        viewHolder.itemView.setScaleY(1);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemHelperListener.onSwiped(viewHolder.getAdapterPosition(), direction);
    }

    //随手势滑动，多次调用
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ACTION_STATE_SWIPE && isCurrentlyActive) {
            double absXY = Math.abs(Math.sqrt(viewHolder.itemView.getWidth() * viewHolder.itemView.getWidth() + viewHolder.itemView.getHeight() * viewHolder.itemView.getHeight()));
            float faction = (float) (Math.abs(Math.sqrt(dX * dX + dY * dY)) / (absXY * getSwipeThreshold(viewHolder)));
            if (faction > 1) {
                faction = 1;
            }

            viewHolder.itemView.setScaleX(1 - CardConfig.SWIPE_MAX_SCALE_THRESHOLD * faction);
            viewHolder.itemView.setScaleY(1 - CardConfig.SWIPE_MAX_SCALE_THRESHOLD * faction);
            if (dX < 0) {
                viewHolder.itemView.setRotation(360 - CardConfig.SWIPE_MAX_ROTATION_DEGREE * faction);
            } else {
                viewHolder.itemView.setRotation(CardConfig.SWIPE_MAX_ROTATION_DEGREE * faction);
            }

            int adapterItemCount = recyclerView.getAdapter().getItemCount();
            int beginPosition = 0;
            if (adapterItemCount >= CardConfig.MAX_SHOW_COUNT) {
                beginPosition = adapterItemCount - CardConfig.MAX_SHOW_COUNT;
            }
            for (int position = beginPosition; position < adapterItemCount - 1; position++) {
                int level = adapterItemCount - position - 1;//第几层
                View childView = recyclerView.getChildAt(recyclerView.getChildCount() - 1 - level);//此时的位置是MANAGER添加进去的位置
                float scaleXValue = CardConfig.MAX_SCALE_THRESHOLD / (CardConfig.MAX_SHOW_COUNT - 1) * (level - faction);
                float translationY = CardConfig.MAX_TRANSLATION_VALUE / (CardConfig.MAX_SHOW_COUNT - 1) * (level - faction);
                childView.setScaleX(1 - scaleXValue);
                childView.setScaleY(1 - scaleXValue);
                childView.setTranslationY(translationY + childView.getMeasuredHeight() * scaleXValue / 2);
            }
        }
    }

    public void deleteLeft(final ImageButton dislike, final ImageButton ibtnDislike, final RecyclerView recyclerView, boolean isRight) {
        ibtnDislike.setEnabled(false);
        dislike.setEnabled(false);
        int itemCount = recyclerView.getAdapter().getItemCount();
        if (itemCount == 0) return;
        final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(itemCount - 1);
        final ObjectAnimator translationX = ObjectAnimator.ofFloat(viewHolder.itemView, "TranslationX", 0, isRight ? recyclerView.getWidth() : -recyclerView.getWidth());
        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(viewHolder.itemView, "TranslationY", -50, 0);
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(viewHolder.itemView, "TranslationY", 0, recyclerView.getHeight() / 2);
        translationY1.setDuration(80);
        translationY2.setDuration(220);
        ObjectAnimator animatorB = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 0.7F);
        ObjectAnimator animatorC = ObjectAnimator.ofFloat(viewHolder.itemView, "rotation", isRight ? 25.0F : -25.0F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translationY1).before(translationY2).with(translationX).with(animatorB).with(animatorC);
        animatorSet.setDuration(300);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                itemHelperListener.removeFirstLevel();
                clearView(recyclerView, viewHolder);
                ibtnDislike.setEnabled(true);
                dislike.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        //点击喜欢或不喜欢按钮时，其它层进行放大至原位
        int adapterItemCount = recyclerView.getAdapter().getItemCount();
        int beginPosition = 0;
        if (adapterItemCount >= CardConfig.MAX_SHOW_COUNT) {
            beginPosition = adapterItemCount - CardConfig.MAX_SHOW_COUNT;
        }
        for (int position = beginPosition; position < adapterItemCount - 1; position++) {
            int level = adapterItemCount - position - 1;//第几层
            View childView = recyclerView.getChildAt(recyclerView.getChildCount() - 1 - level);//此时的位置是MANAGER添加进去的位置
            float scaleXValue = CardConfig.MAX_SCALE_THRESHOLD / (CardConfig.MAX_SHOW_COUNT - 1) * (level - 1);
            float translationY = CardConfig.MAX_TRANSLATION_VALUE / (CardConfig.MAX_SHOW_COUNT - 1) * (level - 1);
            childView.setScaleX(1 - scaleXValue);
            childView.setScaleY(1 - scaleXValue);
            childView.setTranslationY(translationY + childView.getMeasuredHeight() * scaleXValue / 2);
        }
    }
}
