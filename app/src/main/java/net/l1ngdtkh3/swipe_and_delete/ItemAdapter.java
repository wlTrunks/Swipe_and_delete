package net.l1ngdtkh3.swipe_and_delete;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter {
    private ArrayList itemlist;
    private MainActivity mainActivity;

    public ItemAdapter(MainActivity mainActivity, ArrayList itemlist) {
        this.itemlist = itemlist;
        this.mainActivity = mainActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        final String item = (String) itemlist.get(position);
        itemHolder.itemText.setText((CharSequence) itemlist.get(position));
        itemHolder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "DELETE " + position, Toast.LENGTH_SHORT).show();
                removeItem(position);
            }
        });
        itemHolder.itemCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "CANCEL " + position, Toast.LENGTH_SHORT).show();
                mainActivity.refreshAdapter(position);
                itemHolder.itemCancel.setEnabled(false);
                itemHolder.itemDelete.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public void removeItem(int position) {
        itemlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemlist.size());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemText;
        ViewGroup itemContainer;
        ViewGroup itemContext;
        TextView itemDelete;
        TextView itemCancel;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.item_text);
            itemDelete = (TextView) itemView.findViewById(R.id.item_delete);
            itemCancel = (TextView) itemView.findViewById(R.id.item_cancel);
            itemContainer = (ViewGroup) itemView.findViewById(R.id.item_container);
            itemContext = (ViewGroup) itemView.findViewById(R.id.item_context);

        }

        public ViewGroup getSwipableView() {
            return itemContext;
        }

        public ViewGroup getItemContainer() {
            return itemContainer;
        }
    }
}
