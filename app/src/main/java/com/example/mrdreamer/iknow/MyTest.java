package com.example.mrdreamer.iknow;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import com.example.mrdreamer.iknow.Social.Friends.PKInvitation;

import java.util.List;

/**
 * Created by stack on 9/11/16.
 */
public class MyTest extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btn=(Button )findViewById(R.id.btn);
        btn.setOnClickListener((v)->{
           Intent intent=new Intent("com.example.mrdreamer.iknow.Social.Friends.PKInvitation");
        //    Intent intent=new Intent(this, PKInvitation.class);
            PackageManager packageManager=getPackageManager();
            List activities=packageManager.queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            if(activities.size()>0){

                startActivity(intent);
            }
           // intent.setAction("PK");
          //  startActivity(intent);
        });
    }
}
