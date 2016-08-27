package com.example.mrdreamer.iknow.Social.AddUser;

import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.Contract;
import com.example.mrdreamer.iknow.Social.DataListAdapter;
import com.example.mrdreamer.iknow.Social.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

/**
 * Created by stack on 8/25/16.
 */
public class SearchUserFragment extends ListFragment implements AddFriends.DataLoadCallback,AddUserEngine.GetResultCallBack{
    private ListView searchUserList;
    private TextView infoView;
    private DataListAdapter dataListAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.frag_getfriends,viewGroup,false);
        searchUserList=(ListView)view.findViewById(android.R.id.list) ;
//        infater=(TE)
        infoView=(TextView)view.findViewById(android.R.id.empty);
        return view;


    }


    @Override
    public void onDataLoaded(ArrayList<User> users) {
        dataListAdapter=new DataListAdapter(getActivity(),R.layout.userinfo_list_config,users);
        searchUserList.setAdapter(dataListAdapter);
        searchUserList.setOnItemClickListener((parent, view, position, id)->{
         //   Toast.makeText(getActivity(),users.get(position).getName(),Toast.LENGTH_SHORT).show();
            AddUserEngine.SendRequest("mike","mike1",this);
        });

    }

    @Override
    public void onNoDataLoaded(String info) {
        infoView.setText(info);

    }

    @Override
    public void onResultFetched(int resultCode) {
        Log.i("SearchUser",Integer.toString(resultCode));

    }

    @Override
    public void onNoResultFetched(String info) {

    }
}


class AddUserEngine{
    interface GetResultCallBack{
        void onResultFetched(int resultCode);
        void onNoResultFetched(String info);
    }
    private static final String SEND_REQUEST_LINK= Constants.PROTOCOL+Constants.SERVER+"/send_request.php";
    private static final String METHOD="POST";
    public static void SendRequest(String actionUserName,String anotherName,GetResultCallBack callBack){
           new AsyncTask<String,Void,String>(){
                private HttpURLConnection connection;
               @Override
               protected String doInBackground(String... params) {
                   try {
                       connection= ConnectionUtils.getConnection(SEND_REQUEST_LINK,METHOD);
                       BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                       String data="user_one_name="+params[0]+"&user_two_name="+params[1];
                       writer.write(data);
                       writer.flush();
                       writer.close();

                       BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                       StringBuilder builder=new StringBuilder();
                       String line=null;
                       while((line=reader.readLine())!=null){
                            builder.append(line);
                       }
                       reader.close();
                       return builder.toString();

                   } catch (Exception e) {
                       e.printStackTrace();
                       Log.e("AddUserEngine Error","Error"+e.getMessage()) ;
                       return "error: "+e.getMessage();
                   }
                   finally {
                       if(connection==null)
                           connection.disconnect();
                   }

                   //return null;
               }
               @Override
               protected void onPostExecute(String result){
                   int resultCode=-1;
                   if(result.contains("error")){
                       callBack.onNoResultFetched(result);
                       return;
                   }
                   if(result==null){
                       callBack.onNoResultFetched("No Result");
                   }
                   try {
                       JSONObject jsonRootObeject=new JSONObject(result);
                        resultCode=jsonRootObeject.getInt("result_code");
                      // JSONArray jsonArray=jsonRootObeject.optJSONArray("result_code");
                      // JSONObject jsonObject=jsonArray.getJSONObject(0);
                      // int resultCode=jsonObject.getInt("")
                   } catch (JSONException e) {
                       Log.e("AddUserEngin Error","Error"+e.getMessage());
                       callBack.onNoResultFetched("error :"+e.getMessage());
                   }
                   callBack.onResultFetched(resultCode);
               }
           }.execute(actionUserName,anotherName);
    }
}
