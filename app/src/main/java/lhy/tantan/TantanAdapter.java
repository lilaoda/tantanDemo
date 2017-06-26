package lhy.tantan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Liheyu on 2017/6/21.
 * Email:liheyu999@163.com
 */

public class TantanAdapter extends RecyclerView.Adapter<TantanAdapter.MyHolder> implements ItemTouchHelperListener {

    private List<Integer> list;
    private Context context;
    private int mOriginCount;

    public TantanAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
        mOriginCount = list.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position));
        holder.textView.setText(position + 1 + "/" + mOriginCount);
        if (position != list.size() - 1) {
            holder.itemView.setOnTouchListener(null);
            holder.itemView.setEnabled(false);
            holder.itemView.setFocusable(false);
        }
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "我被点击了", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.e("onMove", "onMove");
        Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(int position, int direction) {
        list.remove(position);
        notifyItemRemoved(position);//移除单个
    }

    public void removeFirstLevel() {
        list.remove(list.size() - 1);
        notifyDataSetChanged();//点击不喜欢时调用，刷新全部数据
    }

    public List getDatas() {
        return list;
    }

    public void refreshDatas(List list) {
        if (list == null) {
            return;
        }
        this.list = list;
        this.mOriginCount = list.size();
        notifyDataSetChanged();
    }


    static class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            textView = (TextView) itemView.findViewById(R.id.text_postion);
        }
    }
}


