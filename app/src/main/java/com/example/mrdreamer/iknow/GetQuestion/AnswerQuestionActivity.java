package com.example.mrdreamer.iknow.GetQuestion;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.mrdreamer.iknow.Question;

/**
 * Created by stack on 8/19/16.
 */
public class AnswerQuestionActivity extends AppCompatActivity{


    private class QuestionHandler extends Handler{

        public QuestionHandler(){}
        public QuestionHandler(Looper l){super(l);}

        @Override
        public void handleMessage(Message message)
        {

        }

       // super(l);


    }

}
