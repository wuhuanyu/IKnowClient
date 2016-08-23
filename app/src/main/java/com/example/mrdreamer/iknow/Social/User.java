package com.example.mrdreamer.iknow.Social;

/**
 * Created by stack on 8/21/16.
 */
public class User {
    private String name,info;
    private boolean isLogin=false;
    public User(String name,String info,boolean isLogin){
        this.info=info;
        this.name=name;
        this.isLogin=isLogin;
    }

    public User(String name,boolean isLogin){
        this.isLogin=isLogin;
        this.info=null;
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public String getIsLogIn(){
        if(isLogin) return "Online";
        else return "Offline";
    }

    public String toString(){
        return "User: Name: "+name+" isLogin: "+getIsLogIn()+" Info: "+info+"\n";
    }
}
