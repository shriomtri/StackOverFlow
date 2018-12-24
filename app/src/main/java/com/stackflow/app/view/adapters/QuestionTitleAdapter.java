package com.stackflow.app.view.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stackflow.app.R;
import com.stackflow.app.service.model.Question;
import com.stackflow.app.view.fragments.QuestionFragment;

import java.util.List;

public class QuestionTitleAdapter extends RecyclerView.Adapter<QuestionTitleAdapter.ViewHolder> {

    private Context context;
    private List<Question> questionList;
    private QCallback qCallback;


    public QuestionTitleAdapter(Context context) {
        this.qCallback = (QCallback) context;
        this.context = context;
    }

    public QuestionTitleAdapter(QuestionFragment questionFragment, Context context) {
        this.qCallback = (QCallback) questionFragment;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
       viewHolder.questionTitle.setText(questionList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return questionList == null ? 0 : questionList.size();
    }

    public void swapData(List<Question> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionTitle = itemView.findViewById(R.id.title_textView);

        }

    }

    public interface QCallback {
        void questioniClicked(Question question);
    }
}
