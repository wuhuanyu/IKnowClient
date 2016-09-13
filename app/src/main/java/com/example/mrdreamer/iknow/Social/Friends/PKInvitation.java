package com.example.mrdreamer.iknow.Social.Friends;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.Buffer;
import java.security.spec.ECField;

/**
 * Created by stack on 9/11/16.
 */
public class PKInvitation extends AppCompatActivity {
    private Button leaveBtn,OKBtn;
    private TextView senderText;


    public void onCreate(Bundle savedInsanceState)
    {
        super.onCreate(savedInsanceState);
        setContentView(R.layout.activity_pk_invitation);
        leaveBtn=(Button)findViewById(R.id.activity_pk_invitation_leave_btn);
        OKBtn=(Button)findViewById(R.id.activity_pk_invitation_ok_btn);
        senderText=(TextView)findViewById(R.id.activity_pk_invitation_sender_text);
        initView();


    }
    private void initView(){

        Intent intent=getIntent();
        if(intent==null) return;
        Bundle args=intent.getExtras();
        if(args==null)return;
        String sender=args.getString("sender");
        String room=args.getString("room");
        Log.i(getClass().getSimpleName(),sender+room);
        OKBtn.setOnClickListener((v)->{

        });
        leaveBtn.setOnClickListener((v)->{

        });
        senderText.setText(sender);

    }
}


class  PKInvitationHandleEngine{
    public static final String PK_INIVTATION_HANDLE_LINK= Constants.PROTOCOL+Constants.SERVER+"/pk_invitation_handle.php";
    public interface HandleCallback{
        void onCancleSucessfully();
        void onCancleFailed();
        void onAcceptSuccessfully();
        void onAcceptFailed();
    }
    public static class HandleObject{
        boolean accept;
        String reactorName,room;
        public HandleObject(boolean accept,String reactorName,String room){
            this.accept=accept;
            this.reactorName=reactorName;
            this.room=room;
        }
        public boolean isAccept(){
           return this.accept;
        }
        public int isAccepteCode(){
            if(accept)return 1;
            else return 2;
        }
        public String getReactorName(){return this.reactorName;}
        public String getRoom(){return this.getRoom();}
    }

    public enum resultCode{
            ACCEPT_SUCCESS,
        ACCEPT_FAIL,
        DECLINE_SUCCESS,
        DECLINE_FAIL;
    }
    public static void PKInvitationHandle(HandleObject handleObject,HandleCallback callback){

        new AsyncTask<HandleObject,Void,String>(){
            @Override
            protected String doInBackground(HandleObject... params) {
               try{
                   HttpURLConnection connection= ConnectionUtils.getConnection(PK_INIVTATION_HANDLE_LINK,"POST");
                   String data="room"+handleObject.getRoom()+
                           "&reactor="+handleObject.getReactorName()+
                           "&accept_or_not"+handleObject.isAccepteCode();
                   BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                   writer.write(data);
                   writer.flush();
                   writer.close();
                   BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));







               } catch (Exception e) {
                   e.printStackTrace();
               }
                return null;
            }
        }.execute(handleObject);

    }


}
