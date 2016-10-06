package net.l1ngdtkh3.swipe_and_delete;


import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MyItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

    public MyItemTouchHelper(RecyclerView mRecyclerView, ItemAdapter itemAdapter) {
        super(0, ItemTouchHelper.START | ItemTouchHelper.END);
        this.itemAdapter = itemAdapter;
        this.recyclerView = mRecyclerView;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//        return makeMovementFlags(0, swipeFlags);
        return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        super.onSelectedChanged(viewHolder, actionState);
        Log.d("SWIPE", " actionState = " + actionState);
        Log.d("SWIPE", " viewHolder  = " + (viewHolder == null));
        if (viewHolder != null) {
            Log.d("SWIPE", " viewHolder position = " + viewHolder.getAdapterPosition());
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                getDefaultUIUtil().onSelected(((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView());
            } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                getDefaultUIUtil().onSelected(((ItemAdapter.ItemViewHolder) viewHolder).itemView);
            }
        }
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("SWIPE", " direction = " + direction);
        final ItemAdapter.ItemViewHolder holder = (ItemAdapter.ItemViewHolder) viewHolder;
        ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.bringToFront();
        ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.invalidate();
        ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(true);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(true);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.setVisibility(View.VISIBLE);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.setVisibility(View.INVISIBLE);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "DELETE " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                itemAdapter.removeItem(viewHolder.getAdapterPosition());
                recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "CANCEL " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.setVisibility(View.INVISIBLE);
                holder.itemView.setOnClickListener(ItemAdapter.itemAdapterClickListener(viewHolder.getAdapterPosition()));
                recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                clearView(recyclerView, viewHolder);
                ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(false);
                ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(false);
                ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.setVisibility(View.VISIBLE);
                ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.bringToFront();
                ((ItemAdapter.ItemViewHolder) viewHolder).itemContext.invalidate();
            }
        });
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        if (viewHolder.getAdapterPosition() == -1) {
//            return;
//        }
        if (!isCurrentlyActive) return;
//        ItemAdapter.ItemViewHolder holder = (ItemAdapter.ItemViewHolder) viewHolder;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX > 0 || dX < 0) {
//                holder.itemContext.setTranslationX(dX);
                getDefaultUIUtil().onDraw(c, recyclerView, ((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView(), dX, dY, actionState, isCurrentlyActive);
            } else return;
//        } else {
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            getDefaultUIUtil().onDraw(c, recyclerView, ((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView(), dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        View foreground = ((ItemAdapter.ItemViewHolder) viewHolder).getSwipableView();
        getDefaultUIUtil().clearView(foreground);
    }
}
