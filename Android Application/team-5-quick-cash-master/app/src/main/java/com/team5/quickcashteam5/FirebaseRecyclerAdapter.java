package com.team5.quickcashteam5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class FirebaseRecyclerAdapter extends RecyclerView.Adapter<FirebaseRecyclerAdapter.ViewHolder> {
    private List<Job> AdapterData;
    private LayoutInflater AdapterInflater;
    private ItemClickListener AdapterClickListener;
    private Context myContext;
    private final Calendar calendar = Calendar.getInstance();

    FirebaseRecyclerAdapter(Context context, List<Job> data) {
        this.AdapterInflater = LayoutInflater.from(context);
        this.AdapterData = data;
        this.myContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = AdapterInflater.inflate(R.layout.job_list_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    // Add data from the list of jobs into a display window
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job myJob = this.getItem(position);

        String JobTitle = myJob.getTitle();
        String JobLocation = myJob.getLocation();
        double JobPayment = myJob.getPaymentAmount();
        calendar.setTime(myJob.getDeadline());

        String JobPaymentString, JobDeadline;

        if (JobTitle == "" || JobTitle == null) { JobTitle = "No title provided"; }
        if (JobLocation == "" || JobLocation == null) { JobLocation = "No location provided"; }

        if (JobPayment == 0) { JobPaymentString = "Payment not provided"; }
        else { JobPaymentString = Double.toString(JobPayment); }

        if (calendar.get(Calendar.YEAR) == 1969) { JobDeadline = "No deadline provided"; }
        else { JobDeadline = calendar.toString(); }

        holder.JobTitleDisplay.setText(JobTitle);
        holder.JobLocationDisplay.setText(myContext.getString(R.string.JobLocation) + ": " + JobLocation);
        holder.JobPaymentDisplay.setText(myContext.getString(R.string.JobPayment) + ": " + JobPaymentString);
        holder.JobDeadlineDisplay.setText(myContext.getString(R.string.JobDeadline) + ": " + JobDeadline);
    }

    @Override
    public int getItemCount() {
        return AdapterData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView JobTitleDisplay;
        TextView JobLocationDisplay;
        TextView JobDeadlineDisplay;
        TextView JobPaymentDisplay;

        ViewHolder(View itemView) {
            super(itemView);
            JobTitleDisplay = itemView.findViewById(R.id.JobTitle);
            JobLocationDisplay = itemView.findViewById(R.id.JobLocation);
            JobDeadlineDisplay = itemView.findViewById(R.id.JobDeadline);
            JobPaymentDisplay = itemView.findViewById(R.id.JobPayment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (AdapterClickListener != null) {
                AdapterClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    Job getItem(int id) {
        return AdapterData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.AdapterClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}