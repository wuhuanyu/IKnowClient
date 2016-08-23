package com.example.mrdreamer.iknow.Social;
//import com.example.mrdreamer.iknow.Social

import android.os.AsyncTask;
import android.util.Log;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.AccountManage.LogoutTask;
import com.example.mrdreamer.iknow.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class GetFriendsModel implements Contract.Model{
    private Contract.Presenter presenter;
    private String name,password;
    private static final String GET_FRIENDS_LINK=Constants.PROTOCOL+Constants.SERVER+"/get_friend_list.php";
    private static final String SEARCH_USER_LINK=Constants.PROTOCOL+Constants.SERVER+"/search_user.php";
    private boolean isSearch=false;
    private String query=null;
    public GetFriendsModel(Contract.Presenter presenter,String name,String password){
        this.presenter=presenter;
        this.name=name;
        this.password=password;
        presenter.setModel(this);
    }

    //  public GetFriendsModel(Contract.Presenter presenter,String query){

    // }
    public GetFriendsModel(Contract.Presenter presenter,String query){
        this.presenter=presenter;
        presenter.setModel(this);
        this.isSearch=true;
        this.query=query;

    }

    public void FetchData() {
        //new LogoutTask().execute(,)
        if(isSearch)
            new LoadUsersTask().execute(query);
        new LoadFriendsTask().execute(name,password);

    }

    private abstract class LoadDataTask extends AsyncTask<String,Void,String>{

        @Override
        protected  abstract String doInBackground(String... params);
          @Override
        protected void onPostExecute(String result){
            if(result.contains("error")){
                presenter.onNoDataLoadedCallback(result);
            }
            else
            if(result==null){
                presenter.onNoDataLoadedCallback("NoData");
            }
            else
            {
                ArrayList<User> friends=new ArrayList<>();
                try {
                    JSONObject jsonRootObject=new JSONObject(result);
                    JSONArray jsonArray=jsonRootObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        friends.add(JsonObejectToUser(jsonArray.getJSONObject(i)));
                    }
                    presenter.onDataListLoadedCallback(friends);
                } catch (JSONException e) {
                    presenter.onNoDataLoadedCallback("error: "+e.getMessage());
                    // e.printStackTrace();
                }

            }
        }


        public  User JsonObejectToUser(JSONObject jsonObject) throws JSONException {
            String name=jsonObject.getString("name");
            int isLoginInt=jsonObject.getInt("login");
//            boolean isLogin=jsonObject.getBoolean("login");
            boolean isLogin=false;
            if(isLoginInt==1){
                isLogin=true;
            }
            return new User(name,isLogin);
//            JSONObject jsonRootObject=new JSONObject(string);
            //           JSONArray jsonArray=jsonRootObject.optJSONArray()

        }

    }

    private class LoadUsersTask extends LoadDataTask{

        @Override

        protected String doInBackground(String... params) {
            HttpURLConnection connection=null;
            String query=params[0];
            try {
                connection= ConnectionUtils.getConnection(SEARCH_USER_LINK,"POST");
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                String data="name="+query;
                writer.write(data);
                writer.flush();
                writer.close();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null){
                    builder.append(line);
                }
                Log.i(getClass().getSimpleName(),builder.toString());
                connection.disconnect();
                return builder.toString();
            } catch (IOException e) {
                Log.i(getClass().getSimpleName()+" error: ",e.getMessage());
                if(connection!=null){
                    connection.disconnect();
                }

                return "error: "+e.getMessage();

                // e.printStackTrace();
            }
            //return null;
        }
    }


    private class LoadFriendsTask extends LoadDataTask{


        @Override

        protected String doInBackground(String... params) {
            HttpURLConnection connection=null;
            String name=params[0];
            String password=params[1];
            try {
                connection= ConnectionUtils.getConnection(GET_FRIENDS_LINK,"POST");
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                String data="name="+name+"&password="+password;
                writer.write(data);
                writer.flush();
                writer.close();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null){
                    builder.append(line);
                }
                Log.i(getClass().getSimpleName(),builder.toString());
                connection.disconnect();
                return builder.toString();
            } catch (IOException e) {
                Log.i(getClass().getSimpleName()+" error: ",e.getMessage());
                if(connection!=null){
                    connection.disconnect();
                }

                return "error: "+e.getMessage();

                // e.printStackTrace();
            }
            //return null;
        }

    }

}