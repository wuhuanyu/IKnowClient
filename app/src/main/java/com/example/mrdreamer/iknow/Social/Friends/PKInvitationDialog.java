package com.example.mrdreamer.iknow.Social.Friends;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.IKnowApplication;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by mrdreamer on 2016/9/13.
 */
public class PKInvitationDialog extends DialogFragment {
    String nameClicked;
    public static PKInvitationDialog newInstance(String name){
        PKInvitationDialog dialog=new PKInvitationDialog();
        Bundle bundle=new Bundle();
        bundle.putString("clickedname",name);
        dialog.setArguments(bundle);
        return dialog;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_pk_invitation,null);
        TextView textView=(TextView)view.findViewById(R.id.dialog_pk_invitation_nameclicked_text);
        String name=getArguments().getString("clickedName");
        if(name!=null){
            textView.setText(name);
        }
        User user=User.getUser();
        builder.setView(view).setPositiveButton("SendPKInvitation",(dialog, which) ->{
            if(!(user==null?false:user.getIsLoginBoolean())){
                Toast.makeText(getActivity(), IKnowApplication.getAppContext().getString(R.string.login_alert), Toast.LENGTH_SHORT).show();
            }
            else{
                SendPKInvitationEngine.SendInvitation(user.getName(), name, new SendPKInvitationEngine.SendInvitationCallback() {
                    @Override
                    public void onSendInvitationSuccess(String room) {
                        Toast.makeText(IKnowApplication.getAppContext(),"SendInvitationSuccesss",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(),PKInvitation.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("sender",user.getName());
                        bundle.putString("room",room);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    @Override
                    public void onSendInvitationFailed(String errorInfo) {
                        Toast.makeText(IKnowApplication.getAppContext(),"Error: "+errorInfo,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("Cancel",(dialog1,which1)->{
            PKInvitationDialog.this.getDialog().cancel();
        });
        return builder.create();
    }
}


class SendPKInvitationEngine{
    public interface SendInvitationCallback{
        void onSendInvitationSuccess(String room);
        void onSendInvitationFailed(String errorInfo);
    }
    public static final String SEND_PK_INVITATION_LINK=
            Constants.PROTOCOL+Constants.SERVER+"/send_pk_request.php";
    public static void  SendInvitation(String sender,String receiver,SendInvitationCallback callback){
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... params) {
                try{
                    HttpURLConnection connection= ConnectionUtils.getConnection(SEND_PK_INVITATION_LINK,"POST");
                    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    String data="sender_name="+params[0]+"&receiver_name="+params[1];
                    writer.write(data);
                    writer.flush();
                    writer.close();
                    BufferedReader reader=new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    );
                    StringBuilder stringBuilder=new StringBuilder();
                    String line=null;
                    while((line=reader.readLine())!=null){
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                } catch (Exception e) {
                    Log.i(getClass().getSimpleName(),e.getMessage());
                    return "error: "+e.getMessage();
                }
            }
            @Override
            protected void onPostExecute(String result){
                int resultCode=-1;
                String room="";
                switch (resultCode){
                    case -1:
                        callback.onSendInvitationFailed("Some error happened");
                        break;
                    case 0:
                        callback.onSendInvitationSuccess(room);
                }
            }
        }.execute(sender,receiver);
    }
}
