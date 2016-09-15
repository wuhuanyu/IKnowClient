package com.example.mrdreamer.iknow.AccountManage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.User;
import com.example.mrdreamer.iknow.Utils;

/**
 * Created by mrdreamer on 05/08/16.
 */
public class Account extends Activity {
   EditText nameEdit=null,passwdEdit=null;
    Button LogoutBtn=null,loginBtn=null,signUpBtn=null,testBtn=null;
    private String name=null;
    private String password=null;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getUI();
        setOnClickListener();






    }

    private void setOnClickListener(){
        LogoutBtn.setOnClickListener((v)->{
            name=nameEdit.getText().toString();
            password=passwdEdit.getText().toString();

           AccountManage.performTask(AccountManage.Task.LOG_OUT,new User(name,"",password,false));

        });

        loginBtn.setOnClickListener((v)->{

             name=nameEdit.getText().toString();
            password=passwdEdit.getText().toString();

           AccountManage.performTask(AccountManage.Task.LOG_IN,new User(name,"",password,false));
        });

        signUpBtn.setOnClickListener((v)->{
             name=nameEdit.getText().toString();
            password=passwdEdit.getText().toString();

           AccountManage.performTask(AccountManage.Task.SIGN_UP,new User(name,"",password,false));
        });


        testBtn.setOnClickListener((v)->{
            User user=User.getUser();
            if(user==null)return;
            Utils.makeToast(this,user.toString());
        });
    }


   private void getUI(){
       nameEdit=(EditText)findViewById(R.id.activity_account_name_edit);
       passwdEdit=(EditText)findViewById(R.id.activity_account_password_edit);
       LogoutBtn=(Button)findViewById(R.id.activity_account_logout_btn);
       loginBtn=(Button)findViewById(R.id.activity_account_login_btn);
       signUpBtn=(Button)findViewById(R.id.activity_account_signup_btn);
       testBtn=(Button)findViewById(R.id.activity_account_test_btn);


   }





}

