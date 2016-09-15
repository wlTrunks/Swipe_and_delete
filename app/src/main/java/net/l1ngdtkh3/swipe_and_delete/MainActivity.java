package net.l1ngdtkh3.swipe_and_delete;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList itemList = new ArrayList<>();
    private Paint p = new Paint();
    private ItemAdapter itemAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));

        for (int i = 1; i < 10; i++) {
            itemList.add("ITEM " + i);
        }
        itemAdaper = new ItemAdapter(itemList);
        mRecyclerView.setAdapter(itemAdaper);
        initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(true);
                ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(true);
            }

//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                if (viewHolder instanceof ItemAdapter.ItemViewHolder) {
//                    int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//                    return makeMovementFlags(0, swipeFlags);
//                } else
//                    return 0;
//            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (viewHolder.getAdapterPosition() == -1) {
                    return;
                }

//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // Fade out the view as it is swiped out of the parent's bounds
//                    final float alpha = 1.0f - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
//                    viewHolder.itemView.setAlpha(alpha);
//                    viewHolder.itemView.setTranslationX(dX);
//                    ((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView().setAlpha(alpha);
//                    ((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView().setTranslationX(dX);
//                    final float alpha1 = Math.abs(dX) / (float) viewHolder.itemView.getWidth();
//                    ((ItemAdapter.ItemViewHolder) viewHolder).getItemContainer().setAlpha(alpha1);
//                }
// else {
                if (dX > 0 || dX < 0) {
                    getDefaultUIUtil().onDraw(c, recyclerView, ((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView(), dX, dY, actionState, isCurrentlyActive);
                }
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
//
//            @Override
//            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                getDefaultUIUtil().clearView(((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView());
//            }

//            @Override
//            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                getDefaultUIUtil().onDrawOver(c, recyclerView, ((ItemAdapter.ItemViewHolder) viewHolder).getItemContainer(), dX, dY, actionState, isCurrentlyActive);
//            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
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
