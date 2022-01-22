package com.gmail.mateendev3.androiddbrcview.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.mateendev3.androiddbrcview.R;
import com.gmail.mateendev3.androiddbrcview.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    //declaring members
    private List<Student> studentList;
    private OnItemLongClickListener onItemLongClickListener;

    //public constructor
    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    //setter for studentList
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvRollNo.setText(Integer.toString(student.getRollNo()));
        holder.tvName.setText(student.getName());
        holder.layout.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(holder.getAdapterPosition(), holder.layout);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        if (studentList != null && studentList.size() > 0) {
            return studentList.size();
        }
        return 0;
    }

    /**
     * Inner ViewHolder class named StudentViewHolder
     */
    public static final class StudentViewHolder extends RecyclerView.ViewHolder {
        //declaring member views
        private final TextView tvRollNo, tvName;
        private final ConstraintLayout layout;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            //initializing members
            tvRollNo = itemView.findViewById(R.id.tv_roll_no);
            tvName = itemView.findViewById(R.id.tv_name);
            layout = itemView.findViewById(R.id.root_layout);
        }
    }

    //contract/interface to handle clicks
    public interface OnItemLongClickListener {
        void onItemLongClick(int position, ConstraintLayout layout);
    }

    //setter to set click listener
    public void setOnLongItemClickListener(OnItemLongClickListener l) {
        this.onItemLongClickListener = l;
    }
}
