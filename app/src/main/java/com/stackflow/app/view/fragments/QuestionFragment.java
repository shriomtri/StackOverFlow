package com.stackflow.app.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paginate.Paginate;
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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class QuestionFragment extends Fragment implements LifecycleOwner, QuestionTitleAdapter.QCallback, Paginate.Callbacks{

    private FragmentQuestionTabBinding binding;
    private HomeViewModel viewModel;
    private QuestionTitleAdapter adapter;

    private int listType = 0;
    private String interestTag = null;
    private boolean interestChanged = false;

    private boolean hasMore = true;
    private boolean isLoading = false;
    private int page = 1;

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

        Bundle args = getArguments();
        if(args != null){ listType = args.getInt("TYPE"); }

        if(listType == 1) {
            viewModel.getSelectedTag().observe(this, s -> {
                interestTag = s;
                interestChanged = !interestChanged;
                getRelatedQuestions(interestTag);
            });
        }else if(listType == 2){
            getHotQuestions();
        }else if(listType == 3){
            getWeekQuestions();
        }

        setRecyclerView();
        return binding.getRoot();
    }

    private void setRecyclerView() {

        binding.questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuestionTitleAdapter(this,getContext());
        binding.questionRecyclerView.setAdapter(adapter);

        Paginate.with(binding.questionRecyclerView, this)
                .setLoadingTriggerThreshold(4)
                .setLoadingListItemSpanSizeLookup(() -> 3)
                .build();

    }

    private void getRelatedQuestions(String tag) {
        //(getContext()).showProgressDialog(this);

        isLoading = true;

        Map<String, String> map = new HashMap<>();
        map.put(Constants.QueryParam.PAGE, String.valueOf(page));
        map.put(Constants.QueryParam.PAGE_SIZE, "10");
        map.put(Constants.QueryParam.ORDER, "desc");
        map.put(Constants.QueryParam.SORT, "activity");
        map.put(Constants.QueryParam.TAGGED, tag);
        map.put(Constants.QueryParam.SITE, "stackoverflow");
        map.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        getQuestionList(map);
    }

    private void getHotQuestions() {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.QueryParam.PAGE, String.valueOf(page));
        map.put(Constants.QueryParam.PAGE_SIZE, "10");
        map.put(Constants.QueryParam.ORDER, "desc");
        map.put(Constants.QueryParam.SORT, "hot");
        map.put(Constants.QueryParam.SITE, "stackoverflow");
        map.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        getQuestionList(map);

    }

    private void getWeekQuestions() {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.QueryParam.PAGE, String.valueOf(page));
        map.put(Constants.QueryParam.PAGE_SIZE, "10");
        map.put(Constants.QueryParam.ORDER, "desc");
        map.put(Constants.QueryParam.SORT, "week");
        map.put(Constants.QueryParam.SITE, "stackoverflow");
        map.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        getQuestionList(map);
    }

    private void getQuestionList(Map<String, String> map) {
        viewModel.trendingQuestions(map).observe(this, questionResponseList -> {

            //cancelProgressDialog();

            if (questionResponseList != null && questionResponseList.getItems() != null) {
                List<Question> questionList = questionResponseList.getItems();
                isLoading = false;
                hasMore = questionResponseList.getHasMore();
                page++;
                adapter.setData(questionList, interestChanged);
                interestChanged = false;
            }
        });
    }

    @Override
    public void questioniClicked(Question question) {
        //TODO perform action on question list item clicked
    }

    @Override
    public void onLoadMore() {
        getRelatedQuestions(interestTag);
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return !hasMore;
    }
}
