package com.example.mrdreamer.iknow.Social.AddUser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DialerFilter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrdreamer.iknow.GetQuestion.Contract;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.DataSource.Utils;
import com.example.mrdreamer.iknow.Social.User;

/**
 * Created by stack on 9/9/16.
 */
public class AddFriendDialog extends DialogFragment {
    String nameClicked;
    public void setNameClicked(String name){
        this.nameClicked=name;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.add_friend_dialog,null);
        TextView textView=(TextView)view.findViewById(R.id.add_friend_dialog_receiver_name_textview);
        // textView.setText(getArguments().getString("clickedName"));
        String name=getArguments().getString("clickedName");
        if(name!=null){
            textView.setText(name);
        }

        User user= User.getUser(getActivity());
        builder.setView(view).setPositiveButton(
                "SendRequest", (dialog, which) -> {
                   // if(user!=null)
                    if(!(user==null? false:user.getIsLoginBoolean())){
                        Toast.makeText(getActivity(),"You have to Login first",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        AddUserEngine.SendRequest(user.getName(), name, new AddUserEngine.GetResultCallBack() {
                            @Override
                            public void onResultFetched(int resultCode) {
                                String info=null;
                                switch(resultCode){
                                    case 1:info="Send request successfully";break;
                                    case 2:info="You have already sent request before";break;
                                    case 3:info="Send request successfully";break;
                                    case 4:info="You are already friends";break;
                                    case 5:info="Send request successfully";break;
                                    case 6:info="You are friends now!";break;
                                    case 7:info="You have been blocked";break;
                                    case 8:info="You are already friends";break;
                                    case 9:info="Send request succesfully";break;
                                    case -1:info="You cannot send request to yourself";
                                    default:break;
                                }
                                Toast.makeText(getActivity(),info,Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onNoResultFetched(String info) {
                            }
                        });
                    }
                }
        ).setNegativeButton("Cancel", (dialog, which) -> {
            AddFriendDialog.this.getDialog().cancel();
        });
        return builder.create();
    }


    public static  AddFriendDialog newInstance(String name){
        AddFriendDialog dialog=new AddFriendDialog();
        Bundle bundle=new Bundle();
        bundle.putString("clickedName",name);
        //  bundle.putString("");
        dialog.setArguments(bundle);
        return dialog;
    }

}


