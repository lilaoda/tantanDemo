package lhy.tantan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Liheyu on 2017/6/23.
 * Email:liheyu999@163.com
 */

public class TantanRecyclerView extends RecyclerView {

    public TantanRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TantanRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TantanRecyclerView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        ArrayList<View> touchables = getTouchables();
        for (int i=0;i<touchables.size();i++){
            Log.e("TAG",getChildAdapterPosition(touchables.get(i))+"");
        }
//        View focusedChild = getFocusedChild();
//        int childAdapterPosition = getChildAdapterPosition(focusedChild);
//        Log.e("tantan",childAdapterPosition+"'");
//        Log.e("tantan",getAdapter().getItemCount()-1+"'");
//        if(childAdapterPosition==getAdapter().getItemCount()-1){
//            return false;
//        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public void addOnItemTouchListener(OnItemTouchListener listener) {
        super.addOnItemTouchListener(listener);
    }
}
