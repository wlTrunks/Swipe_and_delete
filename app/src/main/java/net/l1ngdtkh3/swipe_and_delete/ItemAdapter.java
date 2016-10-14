package net.l1ngdtkh3.swipe_and_delete;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter {
    private List<String> itemlist;
    List<String> itemListRemoval = new ArrayList<>();
    private MainActivity mainActivity;

    public ItemAdapter(MainActivity mainActivity, List<String> itemlist) {
        this.itemlist = itemlist;
        this.mainActivity = mainActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        final String item = itemlist.get(position);
        if (itemListRemoval.contains(item)) {
            holder.itemView.setOnClickListener(null);
            itemHolder.getSwipableView().setVisibility(View.INVISIBLE);
            itemHolder.getItemContainer().setVisibility(View.VISIBLE);
            itemHolder.itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "DELETE " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    removeItem(position);
                }
            });
            itemHolder.itemCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListRemoval.remove(item);
                    itemHolder.getItemContainer().setVisibility(View.INVISIBLE);
                    Toast.makeText(v.getContext(), "CANCEL " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    mainActivity.refreshAdapter(holder.getAdapterPosition());
                    notifyItemChanged(itemlist.indexOf(item));
                    mainActivity.click(holder);
//                itemHolder.itemCancel.setEnabled(false);
//                itemHolder.itemDelete.setEnabled(false);
                }
            });
        } else {
            itemHolder.getSwipableView().setVisibility(View.VISIBLE);
            itemHolder.itemText.setText(item);
            holder.itemView.setOnClickListener(itemAdapterClickListener(position));
            itemHolder.itemDelete.setOnClickListener(null);
            itemHolder.itemCancel.setOnClickListener(null);
        }

    }

    @NonNull
    public static View.OnClickListener itemAdapterClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SWIPE", " itemView = " + position);
            }
        };
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public void removeItem(int position) {
        Log.d("SWIPEDELETE", "int = " + position);
        Log.d("SWIPEDELETE", "itemsize = " + itemlist.size());
        if (itemlist.size() > 0 && position >= 0) {
            itemlist.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemlist.size());
        }
    }

    public void pendingRemoval(int position) {
        String item = itemlist.get(position);
        if (!itemListRemoval.contains(item)) {
            itemListRemoval.add(item);
            notifyItemChanged(position);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemText;
        RelativeLayout itemContainer;
        RelativeLayout itemContext;
        TextView itemDelete;
        TextView itemCancel;
        View item_container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.details);
            item_container = itemView.findViewById(R.id.item_container);
            itemContainer = (RelativeLayout) itemView.findViewById(R.id.item_container);
            itemDelete = (TextView) item_container.findViewById(R.id.delete);
            itemCancel = (TextView) item_container.findViewById(R.id.cancel);
            itemContext = (RelativeLayout) itemView.findViewById(R.id.front);

        }

        public ViewGroup getSwipableView() {
            return itemContext;
        }

        public ViewGroup getItemContainer() {
            return itemContainer;
        }
    }
}
