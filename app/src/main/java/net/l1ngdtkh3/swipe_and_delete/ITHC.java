package net.l1ngdtkh3.swipe_and_delete;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

/**
 * Created by Altarix on 06.10.2016.
 */

public class ITHC extends ItemTouchHelperExtension.Callback {
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;

    public ITHC(ItemAdapter itemAdapter, RecyclerView recyclerView) {
        this.itemAdapter = itemAdapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.START | ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("SWIPE", " direction = " + direction);
        Log.d("SWIPE", " viewHolder  onSwiped = " + (viewHolder == null));
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
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            ItemAdapter.ItemViewHolder holder = (ItemAdapter.ItemViewHolder) viewHolder;
            holder.getSwipableView().setTranslationX(dX);
        }
    }
}
