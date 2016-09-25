package com.example.mrdreamer.iknow.Social.AddUser;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.IKnowApplication;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.DataListAdapter;
import com.example.mrdreamer.iknow.User;
import com.example.mrdreamer.iknow.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by stack on 8/25/16.
 */
public class SearchUserFragment extends ListFragment implements AddFriends.DataLoadCallback{
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
           // AddUserEngine.SendRequest("mike",users.get(position).getName(),this);
           // AddFriendDialog dialog=new AddFriendDialog();
            //dialog.setNameClicked(users.get(position).getName());
            //dialog.show(getFragmentManager(),"info");
            AddFriendDialog dialog=AddFriendDialog.newInstance(users.get(position).getName());
            Log.i(getClass().getSimpleName(),users.get(position).getName());
            dialog.show(getFragmentManager(),"Info");
        });




    }

    @Override
    public void onNoDataLoaded(String info) {
        infoView.setText(info);

    }





}


class AddUserEngine{
    interface GetResultCallBack{
        void onResultFetched(int resultCode);
        void onNoResultFetched(String info);
    }
    private static final String SEND_REQUEST_LINK= Constants.PROTOCOL+Constants.SERVER+"/send_request.php";
    private static final String METHOD="POST";
    public static void SendRequest(String anotherName,GetResultCallBack callBack){
        User user= User.getUser();
        if(user==null? true:!user.getIsLoginBoolean()){
            Context context=IKnowApplication.getAppContext();
            Utils.makeToast(context,context.getString(R.string.login_alert));
            return;

        }
           new AsyncTask<String,Void,String>(){
                private HttpURLConnection connection;
               @Override
               protected String doInBackground(String... params) {
                   try {
                       connection= ConnectionUtils.getConnection(SEND_REQUEST_LINK,METHOD);
                       BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                       String data="user_one_name="+user.getName()+"&user_two_name="+params[0];
                       Log.i(getClass().getSimpleName(),data);
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
                       Log.i("AddUserEngine",builder.toString());
                       return builder.toString();

                   } catch (Exception e) {
                       e.printStackTrace();
                       Log.e("AddFriendEngine Error","Error"+e.getMessage()) ;
                       return "error: "+e.getMessage();
                   }
                   finally {
                       if(connection!=null)
                           connection.disconnect();
                   }

                   //return null;
               }
               @Override
               protected void onPostExecute(String result){
                 //  int resultCode=-1;
                   Log.i(getClass().getSimpleName(),result);
                   if(result.contains("error")){
                       callBack.onNoResultFetched(result);
                       return;
                   }
                   if(result==null){
                       callBack.onNoResultFetched("No Result");
                   }
                   try {
                      //JSONObject jsonRootObeject=new JSONObject(result);
                      Integer resultCode=Integer.parseInt(result);
                       callBack.onResultFetched(resultCode);
                     //   resultCode=jsonRootObeject.getInt("result_code");
                      // JSONArray jsonArray=jsonRootObeject.optJSONArray("result_code");
                      // JSONObject jsonObject=jsonArray.getJSONObject(0);
                      // int resultCode=jsonObject.getInt("")
                   } catch (Exception e) {
                       Log.e("AddUserEngin Error","Error"+e.getMessage());
                       callBack.onNoResultFetched("error :"+e.getMessage());
                   }
                  // callBack.onResultFetched(resultCode);
               }
           }.execute(anotherName);
    }
}
