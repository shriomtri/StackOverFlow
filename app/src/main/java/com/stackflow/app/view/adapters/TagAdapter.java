package com.stackflow.app.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stackflow.app.R;
import com.stackflow.app.service.model.PopularTag;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private List<PopularTag> popularTagList = null;
    private Context context;
    private TagClickListener tagClickListener;

    public TagAdapter(Context context) {
        this.context = context;
        tagClickListener = (TagClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String tagName = popularTagList.get(position).getName();
        holder.tag.setText(tagName);
        holder.tag.setOnClickListener(v -> {
            tagClickListener.tagClicked(tagName);
        });
    }

    @Override
    public int getItemCount() {
        return popularTagList == null ? 0 : popularTagList.size();
    }

    public void swapData(List<PopularTag> popularTags) {
        this.popularTagList = popularTags;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.hashtag_item);
        }
    }

    public interface TagClickListener{
        void tagClicked(String tag);
    }
}
