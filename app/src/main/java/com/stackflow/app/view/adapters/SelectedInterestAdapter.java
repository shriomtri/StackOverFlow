package com.stackflow.app.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stackflow.app.R;
import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.SelectedInterest;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedInterestAdapter extends RecyclerView.Adapter<SelectedInterestAdapter.ViewHolder> {

    private List<SelectedInterest> selectedList = new ArrayList<>();
    private Context context;
    private RemoveClickListener removeClickListener;

    public SelectedInterestAdapter(Context context) {
        this.context = context;
        removeClickListener = (RemoveClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_interest_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.selectedTV.setText(selectedList.get(position).getTag());
        holder.removeBtn.setOnClickListener(v -> {
            removeClickListener.tagRemoved(selectedList.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return selectedList == null ? 0 : selectedList.size();
    }

    public void swapData(List<SelectedInterest> selectedInterest) {
        this.selectedList = selectedInterest;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView selectedTV;
        ImageButton removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedTV = itemView.findViewById(R.id.selected_interestTV);
            removeBtn = itemView.findViewById(R.id.remove_selectedBtn);
        }
    }

    public interface RemoveClickListener {
        void tagRemoved(SelectedInterest selectedInterest, int position);
    }
}
