package com.example.mrdreamer.iknow.Social.DataSource;

import com.example.mrdreamer.iknow.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by stack on 8/24/16.
 */
public class Utils {
    public static User JsonObjectToUser(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        int isLoginInt = jsonObject.getInt("login");
//            boolean isLogin=jsonObject.getBoolean("login");
        boolean isLogin = false;
        if (isLoginInt == 1) {
            isLogin = true;
        }
        return new User(name, isLogin);
//            JSONObject jsonRootObject=new JSONObject(string);
        //           JSONArray jsonArray=jsonRootObject.optJSONArray()

    }

    public static ArrayList<User> jsonStringToUsers(String jsonString) throws JSONException {
        ArrayList<User> friends = new ArrayList<>();

        JSONObject jsonRootObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonRootObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            friends.add(Utils.JsonObjectToUser(jsonArray.getJSONObject(i)));

        }
        return friends;
    }


}




