package lhy.tantan;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Liheyu on 2017/6/20.
 * Email:liheyu999@163.com
 */

public class TantanLayoutManager extends RecyclerView.LayoutManager {

    private ItemTouchHelper itemTouchHelper;
    private RecyclerView recyclerView;

    public TantanLayoutManager(RecyclerView recyclerView, ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

//    @Override
//    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        Log.d(TAG + "dy", dy + "'");//往上滑DY为正值
//       offsetChildrenVertical(-dy);
//      //  getFocusedChild().setTranslationY(dy);
//        return dy;
//    }
//
//    @Override
//    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        Log.d(TAG + "dx", dx + "'");//往右滑DX为负值
//        offsetChildrenHorizontal(-dx);
//       // getFocusedChild().setTranslationX(dx);
//        return dx;
//    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        int beginPosition = 0;
        if (itemCount >= CardConfig.MAX_SHOW_COUNT) {
            beginPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }
        for (int position = beginPosition; position < itemCount; position++) {
            final View view = recycler.getViewForPosition(position);
            addView(view);//叠加，显示的是最后面的一张 只添加要显示的张数
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
//            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
//            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
//                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
//                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
            layoutDecoratedWithMargins(view, widthSpace / 2, 30,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    30 + getDecoratedMeasuredHeight(view));
            int level = itemCount - position - 1;//第几层,最上面为第0层
            float scaleXValue = CardConfig.MAX_SCALE_THRESHOLD / (CardConfig.MAX_SHOW_COUNT - 1) * level;
            float translationY = CardConfig.MAX_TRANSLATION_VALUE / (CardConfig.MAX_SHOW_COUNT - 1) * level;
            view.setScaleX(1 - scaleXValue);
            view.setScaleY(1 - scaleXValue);
            view.setTranslationY(translationY + scaleXValue * view.getMeasuredHeight() / 2);
        }
    }
}
