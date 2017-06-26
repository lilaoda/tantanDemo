package lhy.tantan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Integer> list;
    private ImageButton ibtnDislike;
    private ImageButton ibtnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ibtnDislike = (ImageButton) findViewById(R.id.ibtn_dislike);
        ibtnLike = (ImageButton) findViewById(R.id.ibtn_like);

        initData();
        initView();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(R.mipmap.girl1);
        list.add(R.mipmap.girl10);
        list.add(R.mipmap.girl2);
        list.add(R.mipmap.girl11);
        list.add(R.mipmap.girl3);
        list.add(R.mipmap.girl12);
        list.add(R.mipmap.girl4);
        list.add(R.mipmap.girl13);
        list.add(R.mipmap.girl5);
        list.add(R.mipmap.girl14);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        Log.e("width,height:", outMetrics.widthPixels + "___" + outMetrics.heightPixels + "-density-" + outMetrics.density + "-densityDpi-" + outMetrics.densityDpi);
        //480___800-density-1.5-densityDpi-240
    }

    private void initView() {
        final TantanAdapter adapter = new TantanAdapter(this, list);
        final TantanItemTouchCallBack callback = new TantanItemTouchCallBack(recyclerView, adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        TantanLayoutManager layoutManager = new TantanLayoutManager(recyclerView, itemTouchHelper);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        ibtnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteLeft(ibtnDislike,ibtnLike, recyclerView, false);
            }
        });
        ibtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteLeft(ibtnDislike,ibtnLike, recyclerView, true);
            }
        });
    }
}
