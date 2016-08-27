package com.example.mrdreamer.iknow.Social.Friends;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mrdreamer.iknow.AccountManage.AccountAbstract;
import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.RequestInfoAdapter;
import com.example.mrdreamer.iknow.Social.User;
import com.example.mrdreamer.iknow.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by stack on 8/26/16.
 */
public class RequestInfo extends AppCompatActivity{
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestinfo);
        Utils.addFragmentToActivity(this,new RequestInfoFragment(),R.id.request_info_fragment_container);


    }


 public static    class RequestInfoFragment extends ListFragment implements GetRequestInfoDataSource.LoadDataCallback {
    private ListView listView;
    private TextView textView;
     private ArrayList<User>users=null;
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.frag_requestinfo,viewGroup,false);
        listView=(ListView)view.findViewById(android.R.id.list);
        textView=(TextView)view.findViewById(android.R.id.empty);
          GetRequestInfoDataSource.getRequestInfo(this);
        return view;

    }

     @Override
     public void onRequestInfoLoaded(ArrayList<User> result_1_user, ArrayList<User> result_4_user, ArrayList<User> result_5_user, ArrayList<User> result_6_user) {
         ArrayList<User> users=new ArrayList<>();
         users.addAll(result_1_user);
         users.addAll(result_4_user);
         users.addAll(result_5_user);
         users.addAll(result_6_user);

         Log.i(getClass().getSimpleName(),users.toString());
         RequestInfoAdapter adapter=new RequestInfoAdapter(getActivity(),R.layout.request_info_config,result_1_user);
         listView.setAdapter(adapter);
     }

     @Override
     public void onNoDataLoaded(String info) {

     }
 }

}







class GetRequestInfoDataSource{
    public static final String METHOD="POST";
    public   static final String GET_REQUEST_INFO_LINK= Constants.PROTOCOL+Constants.SERVER+"/get_request_info.php";
 interface LoadDataCallback{
     void onRequestInfoLoaded(ArrayList<User> result_1_user,
                              ArrayList<User> result_4_user,
                              ArrayList<User> result_5_user,
                              ArrayList<User> result_6_user);
     void onNoDataLoaded(String info);
 }

    public static void getRequestInfo(LoadDataCallback callback){

        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                try {
                    HttpURLConnection connection= ConnectionUtils.getConnection(GET_REQUEST_INFO_LINK,METHOD);
                    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

                    String data="name="+params[0]+"&password="+params[1];
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
                    reader.close();
                    connection.disconnect();
                    return builder.toString();

                } catch (Exception e) {
                   // callback.onNoDataLoaded("error: "+e.getMessage());
                    e.printStackTrace();
                    return "error: "+e.getMessage();
                }finally {


                }
               // return null;
            }

            @Override
            protected void onPostExecute(String result){
                if(result.contains("error")){
                    callback.onNoDataLoaded(result);
                    return;
                }
                if(result==null){
                    callback.onNoDataLoaded("NoData");
                    return;
                }
                try {
                    JSONObject jsonRootObject=new JSONObject(result);
                    JSONArray jsonArray=jsonRootObject.optJSONArray("result");
                //    for(int i=0;i<jsonArray.length();i++){

                 //   }

                    ArrayList<User> result_1_user=JsonObejectToUsers(1,jsonArray.getJSONObject(0));
                    ArrayList<User> result_4_user=JsonObejectToUsers(4,jsonArray.getJSONObject(1));

                    ArrayList<User> result_5_user=JsonObejectToUsers(5,jsonArray.getJSONObject(2));
                    ArrayList<User> result_6_user=JsonObejectToUsers(6,jsonArray.getJSONObject(3));

                    callback.onRequestInfoLoaded(result_1_user,result_4_user,result_5_user,result_6_user);
                } catch (Exception e) {
                    callback.onNoDataLoaded("error: "+e.getMessage());
                    e.printStackTrace();
                }

            }

            private ArrayList<User> JsonObejectToUsers(int tag,JSONObject jsonObject) throws JSONException{
                ArrayList<User>user=new ArrayList<User>();
                JSONArray jsonArray=jsonObject.getJSONArray(Integer.toString(tag));
                for(int i=0;i<jsonArray.length();i++){
                    user.add(new User(jsonArray.getString(i)));
                }

                return user;


            }
        }.execute("mike","why");
    }


}
