package se.chalmers.agile.pairprogrammingapp.modelview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.Note;

/**
 * Created by wissam on 09/04/16.
 */
public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private ArrayList<Note> mNotes;
    private OnNoteItemClickedListener mListener;

    public NotesListAdapter(ArrayList<Note> notes, OnNoteItemClickedListener listener) {
        this.mNotes = notes;
        this.mListener = listener;
    }

    @Override
    public NotesListAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        NoteViewHolder noteViewHolder = new NoteViewHolder(view, mListener);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(NotesListAdapter.NoteViewHolder holder, int position) {
        holder.setData(position, mNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void deleteItem(int index) {
        if (index < mNotes.size()) {
            mNotes.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void notifyItemAdded() {
        notifyItemInserted(mNotes.size() - 1);
    }

    public void notifyItemModified(int position) {
        notifyItemChanged(position);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        private int position;
        private OnNoteItemClickedListener listener;

        private TextView tvDate;
        private TextView tvContent;

        public NoteViewHolder(View itemView, OnNoteItemClickedListener listener) {
            super(itemView);

            this.listener = listener;

            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);

            //itemView.findViewById(R.id.item).setOnClickListener(this);
            itemView.findViewById(R.id.btnDelete).setOnClickListener(this);
        }

        public void setData(int position, Note note) {
            this.position = position;

            tvDate.setText(sDateFormat.format(note.getDate()));
            tvContent.setText(note.getContent());
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                if (v.getId() == R.id.item) {
                    listener.onNoteItemClicked(getAdapterPosition());
                } else if (v.getId() == R.id.btnDelete) {
                    listener.onDeleteNoteClicked(getAdapterPosition());
                }
            }
        }
    }

    public interface OnNoteItemClickedListener {
        void onNoteItemClicked(int position);

        void onDeleteNoteClicked(int position);
    }
}
