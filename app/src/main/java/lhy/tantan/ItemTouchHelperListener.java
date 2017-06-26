package lhy.tantan;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Liheyu on 2017/6/21.
 * Email:liheyu999@163.com
 */

public interface ItemTouchHelperListener {

    void onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);

    void onSwiped(int position, int direction);

    void removeFirstLevel();
}
