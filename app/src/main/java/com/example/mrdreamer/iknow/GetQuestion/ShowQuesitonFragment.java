package com.example.mrdreamer.iknow.GetQuestion;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrdreamer.iknow.Question;
import com.example.mrdreamer.iknow.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.sql.BatchUpdateException;

/**
 * Created by stack on 8/19/16.
 */
public class ShowQuesitonFragment extends Fragment implements Contract.View {

    private Contract.Presenter presenter;
    private RadioButton Ans_a,Ans_b,Ans_c,Ans_d;
    private TextView question_text;
    private Question question;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root=inflater.inflate(R.layout.question,container,false);

        Ans_a=(RadioButton)root.findViewById(R.id.a_radio);
        Ans_b=(RadioButton)root.findViewById(R.id.b_radio);
        Ans_c=(RadioButton)root.findViewById(R.id.c_radio);
        Ans_d=(RadioButton)root.findViewById(R.id.d_radio);
        question_text=(TextView)root.findViewById(R.id.question_textview);

        radioGroup=(RadioGroup)root.findViewById(R.id.group);
        FloatingActionButton floatingActionButton=(FloatingActionButton)getActivity().findViewById(R.id.next_quesiton_fab);
        floatingActionButton.setOnClickListener((v)->{
            String result=null;

            for(int i=0;i<radioGroup.getChildCount();i++){
                RadioButton rd=(RadioButton)radioGroup.getChildAt(i);
                if(rd.isChecked()){
                    if(question.getRightIndex()==i+1){
                        result="Bingo!";
                    }
                    else result="Oh No!Wrong!";
                }
            }
            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
         //   if(Question.isAnswerRight())





            Log.i(getClass().getSimpleName(),"TabClicked");
            presenter.getQuesiton();
        });

        presenter.getQuesiton();




        return root;
    }






    @Override
    public void setQuestion(Question q) {
        this.question=q;
        question_text.setText(q.getQuestion());
        Ans_a.setText(q.getAns_a());
        Ans_b.setText(q.getAns_b());
        Ans_c.setText(q.getAns_c());
        Ans_d.setText(q.getAns_d());

    }

    @Override
    public void setPresenter(Contract.Presenter p) {
        this.presenter=p;
    }
}
