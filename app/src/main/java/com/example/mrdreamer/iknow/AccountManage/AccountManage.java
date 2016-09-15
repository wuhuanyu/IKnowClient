package com.example.mrdreamer.iknow.AccountManage;

import android.widget.Toast;

import com.example.mrdreamer.iknow.IKnowApplication;
import com.example.mrdreamer.iknow.Social.User;
import com.example.mrdreamer.iknow.Utils;

import java.io.IOException;

/**
 * Created by stack on 9/15/16.
 */
public class AccountManage {
    public enum Task{
        LOG_IN,
        LOG_OUT,
        SIGN_UP;
    }

    public static void performTask(Task task, User user){
        try{

        switch (task){
            case LOG_IN:
                if(user==null){
                    return;
                }
                if(user.getIsLoginBoolean()){
                    return;
                }
                new LoginTask(IKnowApplication.getAppContext()).execute(user.getName(),user.getPassword());
                break;

            case LOG_OUT:
              //  User userToLogout=User.getUser();
                User userToLogout=user;
               // if(!(userToLogout==null? false:userToLogout.getIsLoginBoolean())){
               //     //    Toast.makeText(IKnowApplication.getAppContext(),"You have Login,")
               //     return;
               // }
                new LogoutTask(IKnowApplication.getAppContext()).execute(userToLogout.getName(),userToLogout.getPassword());
                break;
            case SIGN_UP:
                new SignUpTask(IKnowApplication.getAppContext()).execute(user.getName(),user.getPassword());
                break;
        }
    }catch (Exception e){
        }
    }



}

