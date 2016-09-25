package com.example.mrdreamer.iknow.GetQuestion;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.Question;
import com.example.mrdreamer.iknow.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by stack on 8/19/16.
 */
public class GetQuestionModel implements Contract.Model{

    private  Contract.Presenter presenter;
    private AnswerQuestionMode answerQuestionMode;
    private static GetQuestionModel model;

    public static GetQuestionModel newInstance(Contract.Presenter p){
       if(model==null){
           model=new GetQuestionModel(p);
           return model;
       }

        else
           return model;
    }
    private  GetQuestionModel(Contract.Presenter p){
        this.presenter=p;
    }

    @Override
    public void FetechQuestion() {
        if(answerQuestionMode==null){
            Log.e(getClass().getSimpleName(),"answerQuestion is null!");
            return;
        }
        answerQuestionMode.UserFetchQuestion();
    }
    @Override
    public void SetAnswerQuestionMode(AnswerQuestionMode answerQuestionMode) {
        this.answerQuestionMode=answerQuestionMode;
    }

     private static class FetechQuestionTask extends AsyncTask<Void,Void,Question>{
        static final String LINK= Constants.PROTOCOL+Constants.SERVER+"/GetQuestion.php";
        Contract.Presenter presenter;
        boolean Success=false;
     //   Question question=null;
        public FetechQuestionTask(@NonNull Contract.Presenter p){
            this.presenter=p;

        }
        @Override
        protected Question doInBackground(Void... params) {
            try {
                HttpURLConnection connection= ConnectionUtils.getConnection(LINK,"POST");
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write("");
                writer.flush();
                writer.close();
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null){
                    builder.append(line);
                }
                reader.close();
                connection.disconnect();
                return JsonToQuestion(builder.toString());
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(),e.getMessage());
            } catch (JSONException e) {
                Log.e(getClass().getSimpleName(),e.getMessage());
            }
            return null;
        }
        @Override
        public void onPostExecute(Question q){
            Log.i(getClass().getSimpleName(),q.toString());
            presenter.onQuestionFetchedCallback(q);
        }
        public Question JsonToQuestion(String string) throws JSONException {
            JSONObject jsoRootObject=new JSONObject(string);
            JSONArray jsonArray=jsoRootObject.optJSONArray("question");
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            String Content=jsonObject.optString("content");
            String Ans_a=jsonObject.optString("ans_a");
            String Ans_b=jsonObject.optString("ans_b");
            String Ans_c=jsonObject.optString("ans_c");
            String Ans_d=jsonObject.optString("ans_d");
            int RightIndex=jsonObject.optInt("right_index");
            return new Question(Content,Ans_a,Ans_b,Ans_c,Ans_d,RightIndex);
        }
    }



     private static class FetchQuestionInPKModeEngine{
        private static final String  FETCH_QUESTION_IN_PK_MODE_LINK= Constants.PROTOCOL+Constants.SERVER+
                "/send_pk_question.php";
        public static void  FetchQuestionInPKMode(String room,String playerOne,String playerTwo){

            new AsyncTask<String,Void,Void>(){

                @Override
                protected Void doInBackground(String... params) {
                    String room=params[0],playerOne=params[1],playerTwo=params[2];

                    try{
                        HttpURLConnection connection=ConnectionUtils.getConnection(FETCH_QUESTION_IN_PK_MODE_LINK,"POST");
                        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(
                                connection.getOutputStream()
                        ));

                        String data="room="+room+"&player_one="+playerOne+"&player_two="+playerTwo;
                        writer.write(data);
                        writer.flush();
                        writer.close();
                        connection.disconnect();
                       // return  null;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   return null;
                }
            }.execute();


        }
    }



     public  interface AnswerQuestionMode{
        void  UserFetchQuestion();
    }



     public  static class PKMode implements AnswerQuestionMode{

         private String room,playerOne,playerTwo;
         public PKMode(String room,String playerOne,String playerTwo){
             this.room=room;
             this.playerOne=playerOne;
             this.playerTwo=playerTwo;
         }

        @Override
        public void UserFetchQuestion() {
            FetchQuestionInPKModeEngine.FetchQuestionInPKMode(this.room,this.playerOne,this.playerTwo);

        }
    }

     public  static  class SingleMode implements AnswerQuestionMode{
         private Contract.Presenter presenter;
        public SingleMode(Contract.Presenter p){
            this.presenter=p;
        }
        @Override
        public void UserFetchQuestion() {
            new FetechQuestionTask(presenter).execute();
        }
    }
}
