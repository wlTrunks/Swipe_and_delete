package net.l1ngdtkh3.swipe_and_delete;



import android.graphics.Paint;
import android.graphics.Rect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> itemList = new ArrayList<>();
    private Paint p = new Paint();
    private ItemAdapter itemAdaper;
    private MyItemTouchHelper myItemTouchHelper;
    private ItemTouchHelper.Callback myCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));

        itemAdaper = new ItemAdapter(MainActivity.this, itemList);
        myItemTouchHelper = new MyItemTouchHelper(mRecyclerView, itemAdaper);
        for (int i = 1; i < 10; i++) {
            itemList.add("ITEM " + i);
        }
        mRecyclerView.setAdapter(itemAdaper);
        initSwipe();
    }
     public void click(RecyclerView.ViewHolder holder){
         myCallback.clearView(mRecyclerView, holder);
     }
        ItemTouchHelper itemTouchHelper;
    protected void refreshAdapter(int position) {
        itemAdaper.notifyItemChanged(position);
    }

    private void initSwipe() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_item, mRecyclerView, false);
        HeaderDecorationExpanded hd = HeaderDecorationExpanded.with(mRecyclerView)
                .inflate(R.layout.header_item)
                .parallax(0.2f)
                .dropShadowDp(4)
                .build();
        mRecyclerView.addItemDecoration(hd);
        myCallback = new MyItemTouchHelper(mRecyclerView, itemAdaper);
        itemTouchHelper = new ItemTouchHelper(myCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }
}
