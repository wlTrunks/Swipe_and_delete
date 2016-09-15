package net.l1ngdtkh3.swipe_and_delete;


import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;


public class MyItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

    public MyItemTouchHelper(RecyclerView mRecyclerView, ItemAdapter itemAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.itemAdapter = itemAdapter;
        this.recyclerView = mRecyclerView;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT) | makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(true);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(true);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.setVisibility(View.VISIBLE);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "DELETE " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                itemAdapter.removeItem(viewHolder.getAdapterPosition());
//                recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "CANCEL " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                ((ItemAdapter.ItemViewHolder) viewHolder).itemContainer.setVisibility(View.INVISIBLE);
                recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                clearView(recyclerView, viewHolder);
                ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(false);
                ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(false);
            }
        });
    }

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
    }
}
