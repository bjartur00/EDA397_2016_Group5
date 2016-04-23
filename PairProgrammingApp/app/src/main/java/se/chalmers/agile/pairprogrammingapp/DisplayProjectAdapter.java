package se.chalmers.agile.pairprogrammingapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wanziguelva on 16-04-23.
 */
public class DisplayProjectAdapter extends RecyclerView.Adapter<DisplayProjectAdapter.ViewHolder> {
    //in this case, the input of the view contents is an array of strings.
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Project> mDataset;
    private static MyClickListener myClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        //each data item is just a string in this scase;
        public TextView mProName;
        public TextView mProProgress;
        public ViewHolder(View v) {
            super(v);
            mProName = (TextView) v.findViewById(R.id.pro_name);
            mProProgress = (TextView) v.findViewById(R.id.pro_progress);
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
    //provide a suitable constructor (depends on the kind of dataset)
    public DisplayProjectAdapter (ArrayList<Project> myDataset){
        mDataset = myDataset;
    }

    //Create new views (invoked by the layout manager)

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_items, parent, false);

        //the viewholder will hold the newly created textview
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    //replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mProName.setText(mDataset.get(position).getmProName());
        holder.mProProgress.setText(mDataset.get(position).getmProProgress());
    }

    //used when adding a new item, will be implemented
    public void addItem(Project project, int index) {
        mDataset.add(index,project);
        notifyItemInserted(index);
    }

    //used when deleting an item
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
    //Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}


