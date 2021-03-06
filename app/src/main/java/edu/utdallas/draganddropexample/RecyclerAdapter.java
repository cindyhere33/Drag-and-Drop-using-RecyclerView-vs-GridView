package edu.utdallas.draganddropexample;

/**
 * Created by sxk159231 on 2/6/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

import java.util.List;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements DraggableItemAdapter<RecyclerAdapter.MyViewHolder> {
    private List<ItemModel> items;
    private MyViewHolder holder;


    View dragHandle;

    RecyclerAdapter(List<ItemModel> items) {
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.griditem, parent, false);
        v.setMinimumWidth(100);
        v.setMinimumHeight(100);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set text
        holder.iv.setImageDrawable(items.get(position).getDrawable());
        final int dragState = holder.getDragStateFlags();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends AbstractDraggableItemViewHolder {
        ImageView iv;
        View dragHandle;
        FrameLayout frame;

        public MyViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv);
        }
    }

    @Override
    public boolean onCheckCanStartDrag(RecyclerAdapter.MyViewHolder holder, int position, int x, int y) {
        View itemView = holder.itemView;
        View dragHandle = holder.dragHandle;

        int handleWidth = dragHandle.getWidth();
        int handleHeight = dragHandle.getHeight();
        int handleLeft = dragHandle.getLeft();
        int handleTop = dragHandle.getTop();

        return (x >= handleLeft) && (x < handleLeft + handleWidth) &&
                (x >= handleTop) && (x < handleTop + handleHeight);
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        ItemModel removed = items.remove(fromPosition);
        items.add(toPosition, removed);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(RecyclerAdapter.MyViewHolder holder, int position) {
        // just return null for default behavior
        return null;
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return true;
    }

}
