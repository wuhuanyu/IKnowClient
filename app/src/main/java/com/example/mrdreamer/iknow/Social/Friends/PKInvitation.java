package com.example.mrdreamer.iknow.Social.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrdreamer.iknow.R;

import org.w3c.dom.Text;

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
