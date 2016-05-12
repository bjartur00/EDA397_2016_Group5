package se.chalmers.agile.pairprogrammingapp.modelview;

/**
 * Created by wanziguelva on 16-04-23.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.Project;

/**
 * This class serves as an adapter to format the project object into recyclerview for the DisplayProjectActivity.
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private ArrayList<Project> mDataSet;
    private OnProjectItemClickedListener mListener;

    public ProjectListAdapter(ArrayList<Project> data, OnProjectItemClickedListener listener){
        mDataSet = data;
        mListener = listener;
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);

        //the viewholder will hold the newly created textview
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;

    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        holder.mProName.setText(mDataSet.get(position).getProjectName());
        //holder.mProProgress.setText(mDataSet.get(position).getmProProgress());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private OnProjectItemClickedListener mListener;

        public TextView mProName;
        public TextView mProProgress;
        public ViewHolder(View v, OnProjectItemClickedListener listener) {
            super(v);
            mProName = (TextView) v.findViewById(R.id.pro_name);
            mProProgress = (TextView) v.findViewById(R.id.pro_progress);
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onProjectItemClicked(getAdapterPosition());
            }
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnProjectItemClickedListener {
        void onProjectItemClicked(int position);
    }
}


