package com.example.mrdreamer.iknow.GetQuestion;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.Question;

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
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by stack on 8/19/16.
 */
public class GetQuestionModel implements Contract.Model{

    private Contract.Presenter presenter;
    public  GetQuestionModel(Contract.Presenter p){
        this.presenter=p;
    }


    @Override
    public void FetechQuestion() {
        new FetechQuestionTask(presenter).execute();

    }


    public static class FetechQuestionTask extends AsyncTask<Void,Void,Question>{
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

               // BufferedWriter writer=new BufferedWriter(new InputStream(connection.getInputStream()))
             //   BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));


                StringBuilder builder=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null){
                    builder.append(line);
                }
                Log.i(getClass().getSimpleName(),builder.toString());
                reader.close();
                connection.disconnect();
                return JsonToQuestion(builder.toString());




            } catch (IOException e) {
               // e.printStackTrace();
                Log.e(getClass().getSimpleName(),e.getMessage());
            } catch (JSONException e) {
               // e.printStackTrace();

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




}
