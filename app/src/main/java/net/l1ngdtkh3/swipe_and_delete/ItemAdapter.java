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

    public ItemAdapter(ArrayList itemlist) {
        this.itemlist = itemlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        itemHolder.itemText.setText((CharSequence) itemlist.get(position));
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

    public class ItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        TextView itemText;
        ViewGroup itemContainer;
        ViewGroup itemContext;
        TextView itemDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.item_text);
            itemDelete = (TextView) itemView.findViewById(R.id.item_delete);
            itemContainer = (ViewGroup) itemView.findViewById(R.id.item_container);
            itemContext = (ViewGroup) itemView.findViewById(R.id.item_context);
            itemDelete.setOnClickListener(this);

        }
        public ViewGroup getSwipableView() {
            return itemContext;
        }

        public ViewGroup getItemContainer() {
            return itemContainer;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
