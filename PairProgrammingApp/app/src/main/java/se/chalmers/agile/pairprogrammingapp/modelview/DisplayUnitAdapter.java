package se.chalmers.agile.pairprogrammingapp.modelview;

/**
 * Created by wanziguelva on 16-04-24.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.Unit;

/**
 * This class serves as an adapter to format the Unit object into recyclerview for the DisplayUnitActivity.
 */
public class DisplayUnitAdapter extends RecyclerView.Adapter<DisplayUnitAdapter.ViewHolder> {
    private ArrayList<Unit> mDataSet;
    private static OnUnitItemClickedListener mListener;

    public DisplayUnitAdapter(ArrayList<Unit> data, OnUnitItemClickedListener listener) {
        mDataSet = data;
        mListener = listener;
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit, parent, false);

        //the viewholder will hold the newly created textview
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;

    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mUnitName.setText(mDataSet.get(position).getUnitName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mUnitName;
        private OnUnitItemClickedListener mListener;

        public ViewHolder(View v, OnUnitItemClickedListener listener) {
            super(v);
            mListener = listener;
            mUnitName = (TextView) v.findViewById(R.id.unit_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onUnitItemClick(getAdapterPosition());
        }
    }




    /**
     * Used when adding a new item.
     */
    // TODO: will be implemented later on
    public void addItem(Unit Unit, int index) {
        mDataSet.add(index, Unit);
        notifyItemInserted(index);
    }

    /**
     * Used when deleting an item
     */
    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnUnitItemClickedListener {
        void onUnitItemClick(int position);
    }
}
