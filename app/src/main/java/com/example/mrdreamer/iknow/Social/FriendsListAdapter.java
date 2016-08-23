package com.example.mrdreamer.iknow.Social;

import android.app.ListFragment;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mrdreamer.iknow.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by stack on 8/22/16.
 */

public class FriendsListAdapter extends ArrayAdapter<User>{
    private ArrayList<User> users;
    private Context context;
    public FriendsListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public FriendsListAdapter(Context context, int itemLayoutResoureId, ArrayList<User>friends){
        super(context,itemLayoutResoureId,friends);
        this.context=context;
        this.users=new ArrayList<>();
        users.addAll(friends);
    }


    private class ViewHolder{
        TextView name;
        TextView isLogin;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder=null;
         Log.i("ConvertView",String.valueOf(position));
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.userinfo_list_config,null);
            holder=new ViewHolder();
            holder.name=(TextView)convertView.findViewById(R.id.userinfo_list_config_name);
            holder.isLogin=(TextView)convertView.findViewById(R.id.userinfo_list_config_islogin);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        User user=users.get(position);
        holder.name.setText(user.getName());
        holder.isLogin.setText(user.getIsLogIn());
        return convertView;
    }


}
