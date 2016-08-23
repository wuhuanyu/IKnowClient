package com.example.mrdreamer.iknow;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrdreamer.iknow.AccountManage.LoginTask;
import com.example.mrdreamer.iknow.AccountManage.LogoutTask;

/**
 * Created by mrdreamer on 05/08/16.
 */
public class LoginActivity2 extends Activity {
   EditText nameEdit=null,passwdEdit=null;
    Button LogoutBtn=null;
    private String name=null;
    private String password=null;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2_layout);
        getUI();

        LogoutBtn.setOnClickListener((v)->{
            name=nameEdit.getText().toString();
            password=passwdEdit.getText().toString();

            SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);
            String savedName=sharedPreferences.getString(getString(R.string.username),"");
            Log.i(this.toString(),savedName);
            if(!savedName.equals(name))
                Toast.makeText(this,"You have to login first",Toast.LENGTH_SHORT).show();
            else{
                new LogoutTask(this).execute(name,password);
            }

        });



    }
   private void getUI(){
       nameEdit=(EditText)findViewById(R.id.login2_name_edit);
       passwdEdit=(EditText)findViewById(R.id.login2_passwd_edit);
       LogoutBtn=(Button)findViewById(R.id.logout_btn);


   }

    public void login(View view){
         name=nameEdit.getText().toString();
         password=passwdEdit.getText().toString();
        new LoginTask(this).execute(name,password);

    }




}

