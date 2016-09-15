package net.l1ngdtkh3.swipe_and_delete;


import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class MyItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ItemAdapter itemAdapter;

    public MyItemTouchHelper(ItemAdapter itemAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.itemAdapter = itemAdapter;
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
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        ((ItemAdapter.ItemViewHolder) viewHolder).itemCancel.setEnabled(true);
        ((ItemAdapter.ItemViewHolder) viewHolder).itemDelete.setEnabled(true);
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
