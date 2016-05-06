package se.chalmers.agile.pairprogrammingapp.modelview;

/**
 * Created by wanziguelva on 16-04-24.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Unit> mDataset;
    private static MyClickListener myClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mUnitName;
        public TextView mUnitProgress;
        public TextView mID;
        public ViewHolder(View v) {
            super(v);
            mUnitName = (TextView) v.findViewById(R.id.unit_name);
            mUnitProgress = (TextView) v.findViewById(R.id.unit_progress);
            mID = (TextView) v.findViewById(R.id.unit_id);
            Log.i(LOG_TAG, "Addling Listener");
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(),v);
        }
    }

    public void setOnItemClickListener (MyClickListener myClickListener){
        this.myClickListener = myClickListener;
    }

    /**
     * Provide a suitable constructor (depends on the kind of dataset)
     */
    public DisplayUnitAdapter (ArrayList<Unit> myDataset){
        mDataset = myDataset;
    }


    /**
     * Create new views (invoked by the layout manager)
     */
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_items, parent, false);

        //the viewholder will hold the newly created textview
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mUnitName.setText(mDataset.get(position).getmUnitName());
        holder.mUnitProgress.setText(mDataset.get(position).getmUnitProgress());
        holder.mID.setText(mDataset.get(position).getmID());
    }

    /**
     * Used when adding a new item.
     */
    // TODO: will be implemented later on
    public void addItem(Unit Unit, int index) {
        mDataset.add(index, Unit);
        notifyItemInserted(index);
    }

    /**
     * Used when deleting an item
     */
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
