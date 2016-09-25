package com.example.mrdreamer.iknow.Social;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.User;

import java.util.ArrayList;

/**
 * Created by stack on 8/27/16.
 */
public class RequestInfoAdapter extends ArrayAdapter<User> {
    private ArrayList<User> users;
    private Context context;
    public RequestInfoAdapter(Context context, int resource) {
        super(context, resource);
    }

    public RequestInfoAdapter(Context context,int itemLayoutResourceId,ArrayList<User> users){
        super(context,itemLayoutResourceId,users);
        this.context=context;
        this.users=new ArrayList<>();
        this.users.addAll(users);

    }
    private class ViewHolder{
        TextView name;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder=null;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.request_info_config,null);
            viewHolder=new ViewHolder();
            viewHolder.name=(TextView)convertView.findViewById(R.id.request_info_config_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        User user=users.get(position);
        viewHolder.name.setText(user.getName());
        return convertView;

    }

}
