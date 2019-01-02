package com.stackflow.app.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stackflow.app.R;
import com.stackflow.app.databinding.FragmentQuestionTabBinding;
import com.stackflow.app.service.model.Question;
import com.stackflow.app.util.Constants;
import com.stackflow.app.util.SharedPrefUtil;
import com.stackflow.app.view.adapters.QuestionTitleAdapter;
import com.stackflow.app.viewmodel.HomeViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class QuestionFragment extends Fragment implements QuestionTitleAdapter.QCallback{

    private FragmentQuestionTabBinding binding;
    private HomeViewModel viewModel;
    private QuestionTitleAdapter adapter;
    private int listType = 0;
    private String listTag = null;

    public QuestionFragment() {
        //empty constructor required
    }

    public static QuestionFragment instance(int type){
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("TYPE",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question_tab, container, false);
        setRecyclerView();

        Bundle args = getArguments();
        if(args != null){
            listType = args.getInt("TYPE");
        }

        if(listType == 1) {
            viewModel.getSelectedTag().observe(this, s -> {
                listTag = s;
                getRelatedQuestions(listTag);
            });
        }

        return binding.getRoot();
    }

    private void setRecyclerView() {

        binding.questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuestionTitleAdapter(this,getContext());
        binding.questionRecyclerView.setAdapter(adapter);

    }

    private void getRelatedQuestions(String tag) {
        //(getContext()).showProgressDialog(this);

        Map<String, String> map = new HashMap<>();
        map.put(Constants.QueryParam.PAGE, "1");
        map.put(Constants.QueryParam.PAGE_SIZE, "10");
        map.put(Constants.QueryParam.ORDER, "desc");
        map.put(Constants.QueryParam.SORT, "activity");
        map.put(Constants.QueryParam.TAGGED, tag);
        map.put(Constants.QueryParam.SITE, "stackoverflow");
        map.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        viewModel.trendingQuestions(map).observe(this, questionResponseList -> {

            //cancelProgressDialog();

            if (questionResponseList != null && questionResponseList.getItems() != null) {
                List<Question> questionList = questionResponseList.getItems();
                adapter.swapData(questionList);
            }
        });
    }

    @Override
    public void questioniClicked(Question question) {

    }
}
