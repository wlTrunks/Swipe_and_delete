package net.l1ngdtkh3.swipe_and_delete

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import net.l1ngdtkh3.swipe_and_delete.ItemAdapter.ItemViewHolder

class SwipeItemCallback : ItemTouchHelper.Callback() {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.START or ItemTouchHelper.END)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val holder = viewHolder as ItemViewHolder
        holder.update()
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (viewHolder.absoluteAdapterPosition == -1) {
            return
        }
        val holder = viewHolder as ItemViewHolder
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) getDefaultUIUtil().onDraw(
            c,
            recyclerView,
            holder.binding.front,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val foreground: View = (viewHolder as ItemViewHolder).binding.front
        getDefaultUIUtil().clearView(foreground)
    }
}