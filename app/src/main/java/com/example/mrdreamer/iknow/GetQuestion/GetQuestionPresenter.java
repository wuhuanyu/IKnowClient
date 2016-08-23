package com.example.mrdreamer.iknow.GetQuestion;

import android.support.annotation.NonNull;

import com.example.mrdreamer.iknow.Question;

/**
 * Created by stack on 8/19/16.
 */
public class GetQuestionPresenter implements Contract.Presenter{
    private Contract.View view;
    private Contract.Model model;
    public GetQuestionPresenter(@NonNull Contract.View view){
        this.view=view;
        this.model=new GetQuestionModel(this);
        view.setPresenter(this);

    }

    @Override
    public void getQuesiton() {
        model.FetechQuestion();


    }

    @Override
    public void onQuestionFetchedCallback(Question q) {

        view.setQuestion(q);

    }




}
