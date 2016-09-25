package com.example.mrdreamer.iknow.GetQuestion;

import com.example.mrdreamer.iknow.Question;
import com.example.mrdreamer.iknow.User;

/**
 * Created by stack on 8/19/16.
 */
public interface Contract {
    interface View{
        void setQuestion(Question q);
        void setPresenter(Presenter p);
    }

    interface Presenter{
        void getQuesiton();
        void onQuestionFetchedCallback(Question q);
        //void populate();
    }

    interface Model{
        void FetechQuestion();
        void SetAnswerQuestionMode(GetQuestionModel.AnswerQuestionMode answerQuestionMode);
    }

}
