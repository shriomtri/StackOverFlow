package com.stackflow.app.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stackflow.app.R;
import com.stackflow.app.service.model.PopularTag;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewModel> {

    private Context context;
    private List<PopularTag> tagList;
    private List<String> selected = new ArrayList<>(4);
    InterestClickListener interestClickListener;

    private int interestCount = 0;


    public InterestAdapter(Context context, List<PopularTag> tagList) {
        this.context = context;
        this.tagList = tagList;
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
        holder.interestName.setText(popularTag.getName());
        holder.interestCB.setChecked(false);
        holder.itemView.setOnClickListener(v -> {
            if(holder.interestCB.isChecked()){
                //remove from list
                interestCount--;
                selected.remove(popularTag.getName());
                holder.interestCB.setChecked(false);
                interestClickListener.tagSelected(false);
            }else{
                //add to list
                if(interestCount < 4) {
                    interestCount++;
                    selected.add(popularTag.getName());
                    holder.interestCB.setChecked(true);
                    interestClickListener.tagSelected(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tagList == null ? 0 : tagList.size();
    }

    public void swapData(List<PopularTag> items) {
        this.tagList = items;
        notifyDataSetChanged();
    }

    public List<String> selected() {
        return selected;
    }


    class ViewModel extends RecyclerView.ViewHolder {

        CheckBox interestCB;
        TextView interestName;

        ViewModel(@NonNull View itemView) {
            super(itemView);
            interestCB = itemView.findViewById(R.id.select_interest);
            interestName = itemView.findViewById(R.id.interest_name);
        }
    }

    public interface InterestClickListener{
        void tagSelected(boolean isChecked);
    }

}
