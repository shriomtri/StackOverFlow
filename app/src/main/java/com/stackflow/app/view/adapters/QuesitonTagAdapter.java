package com.stackflow.app.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stackflow.app.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuesitonTagAdapter extends RecyclerView.Adapter<QuesitonTagAdapter.ViewHolder> {

    private Context context;
    private List<String> tagList;

    public QuesitonTagAdapter(Context context, List<String> tagList) {
        this.context = context;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.question_tag_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tag = tagList.get(position);
        holder.textView.setText(tag);
    }

    @Override
    public int getItemCount() {
        return tagList == null ? 0 : tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.question_tagTV);
        }
    }
}
