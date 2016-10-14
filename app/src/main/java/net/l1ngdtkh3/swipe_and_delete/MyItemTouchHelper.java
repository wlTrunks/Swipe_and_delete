package net.l1ngdtkh3.swipe_and_delete;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

    public MyItemTouchHelper(RecyclerView mRecyclerView, ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;
        this.recyclerView = mRecyclerView;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.START | ItemTouchHelper.END);
    }

//    @Override
//    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        Log.d("ITEMTOUCH", "holder ? null =" + (viewHolder == null));
//        if (viewHolder != null) {
//            ItemAdapter.ItemViewHolder holder = (ItemAdapter.ItemViewHolder) viewHolder;
//            getDefaultUIUtil().onSelected(holder.getSwipableView());
//        }
//    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
//        Log.d("SWIPE", " direction = " + direction);
//        Log.d("SWIPE", " viewHolder  onSwiped = " + (viewHolder == null));
        final ItemAdapter.ItemViewHolder holder = (ItemAdapter.ItemViewHolder) viewHolder;
        Log.d("ITEMTOUCH", "holder ? null =" + (viewHolder == null));
        itemAdapter.pendingRemoval(viewHolder.getAdapterPosition());
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "DELETE " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.bringToFront();
//        ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.invalidate();
//        ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(true);
//        ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(true);
//        ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.setVisibility(View.VISIBLE);
//        ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.setVisibility(View.INVISIBLE);
//        ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "DELETE " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                itemAdapter.removeItem(viewHolder.getAdapterPosition());
//                recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
//            }
//        });
//        ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "CANCEL " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.setVisibility(View.INVISIBLE);
//                holder.itemView.setOnClickListener(ItemAdapter.itemAdapterClickListener(viewHolder.getAdapterPosition()));
//                recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
//                clearView(recyclerView, viewHolder);
//                ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(false);
//                ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(false);
//                ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.setVisibility(View.VISIBLE);
//                ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.bringToFront();
//                ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.invalidate();
//            }
//        });
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        ItemAdapter.ItemViewHolder holder = (ItemAdapter.ItemViewHolder) viewHolder;
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
            getDefaultUIUtil().onDraw(c, recyclerView, holder.getSwipableView(), dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;

        // not sure why, but this method get's called for viewholder that are already swiped away
        if (viewHolder.getAdapterPosition() == -1) {
            // not interested in those
            return;
        }

////        holder.getSwipableView().setTranslationX(dX);
////        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
////            getDefaultUIUtil().onDraw(c, recyclerView, ((ItemAdapter.ItemViewHolder) viewHolder).getItemContainer(), dX, dY, actionState, isCurrentlyActive);
////        }
//        Rect rect = new Rect(holder.itemView.getLeft(), holder.itemView.getTop(), holder.itemView.getRight(), holder.itemView.getBottom());
//        view = View.inflate(recyclerView.getContext(), R.layout.layout_back, null);
//        //Measure the view at the exact dimensions (otherwise the text won't center correctly)
//        int widthSpec = View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY);
//        int heightSpec = View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY);
//        view.measure(widthSpec, heightSpec);
//        view.layout(0, 0, rect.width(), rect.height());
//        c.save();
//        c.translate(rect.left, rect.top);
//        view.draw(c);
//        c.restore();
    }

//    @Override
//    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        ItemAdapter.ItemViewHolder holder = (ItemAdapter.ItemViewHolder) viewHolder;
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
//            getDefaultUIUtil().onDrawOver(c, recyclerView, holder.getSwipableView(), dX, dY, actionState, isCurrentlyActive);
//        Rect rect = new Rect(holder.itemView.getLeft(), holder.itemView.getTop(), holder.itemView.getRight(), holder.itemView.getBottom());
//        View view = View.inflate(recyclerView.getContext(), R.layout.layout_back, null);
//        //Measure the view at the exact dimensions (otherwise the text won't center correctly)
//        int widthSpec = View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY);
//        int heightSpec = View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY);
//        view.measure(widthSpec, heightSpec);
//        view.layout(0, 0, rect.width(), rect.height());
//        c.save();
//        c.translate(rect.left, rect.top);
//        view.draw(c);
//        c.restore();
//    }


    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        View foreground = ((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView();
        getDefaultUIUtil().clearView(foreground);
    }
}
