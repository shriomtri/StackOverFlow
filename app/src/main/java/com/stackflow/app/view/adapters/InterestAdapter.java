package com.stackflow.app.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stackflow.app.R;
import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.SelectedInterest;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewModel> {

    private Context context;
    private List<PopularTag> tagList;
    private InterestClickListener interestClickListener;


    public InterestAdapter(Context context) {
        this.context = context;
        this.tagList = new ArrayList<>();
        interestClickListener = (InterestClickListener) context;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_list_item, parent, false);
        return new ViewModel(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {

        PopularTag popularTag = tagList.get(position);
        String interestName = String.valueOf(popularTag.getName().charAt(0)).toUpperCase() + popularTag.getName().substring(1);

        holder.interestName.setText(interestName);
        holder.itemView.setOnClickListener(v -> {
            interestClickListener.tagSelected(interestName, position);
        });
    }

    @Override
    public int getItemCount() {
        return tagList == null ? 0 : tagList.size();
    }

    //swap the data
    public void swapData(List<PopularTag> items) {
        this.tagList.clear();
        this.tagList = items;
        notifyDataSetChanged();
    }

    //remove selected interest
    public void remove(SelectedInterest selectedInterests) {

        tagList.remove(selectedInterests.getPosition());
        notifyItemRemoved(selectedInterests.getPosition());
        notifyItemRangeChanged(selectedInterests.getPosition(),tagList.size());
    }

    public void insert(SelectedInterest removedInterest) {

        PopularTag popularTag = new PopularTag();
        popularTag.setName(removedInterest.getTag());
        popularTag.setCount(0L);
        popularTag.setHasSynonyms(false);
        popularTag.setModeratorOnly(false);
        popularTag.setRequired(false);

        tagList.add(removedInterest.getPosition(), popularTag);
        notifyItemInserted(removedInterest.getPosition());
        notifyItemRangeChanged(removedInterest.getPosition(),tagList.size());
    }


    class ViewModel extends RecyclerView.ViewHolder {

        TextView interestName;

        ViewModel(@NonNull View itemView) {
            super(itemView);
            interestName = itemView.findViewById(R.id.interest_name);
        }
    }

    public interface InterestClickListener {
        void tagSelected(String tag, Integer position);
    }

}
