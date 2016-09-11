package com.example.mrdreamer.iknow.Social.Friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;

import com.example.mrdreamer.iknow.R;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stack on 9/11/16.
 */
public class PKInvitationReceiver extends XGPushBaseReceiver {

    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        if(context==null||xgPushTextMessage==null)return;
        String customContent=xgPushTextMessage.getCustomContent();

        String sender=null;
        String room=null;
       try{

           JSONObject jsonObject=new JSONObject(customContent);

           if(!jsonObject.isNull("sender")){

           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(Context)

                .setSmallIcon(R.mipmap.ic_launcher)
                .setContent();

    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        if(context==null||xgPushClickedResult==null) return;
        if(xgPushClickedResult.getActionType()==XGPushClickedResult.NOTIFACTION_CLICKED_TYPE){

        }
        String customContent=xgPushClickedResult.getCustomContent();
        if(customContent!=null&&customContent.length()!=0){
            try{
                String senderName=null,room=null ;
                JSONObject jsonObject=new JSONObject(customContent);
                if(!jsonObject.isNull("sender")){
                     senderName=jsonObject.getString("sender");
                }
                if(!jsonObject.isNull("room")){
                    room=jsonObject.getString("room");
                }
                Intent intent=new Intent();
                intent.setAction("PK");
                Bundle bundle=new Bundle();
                if(room!=null&&senderName!=null){

                    bundle.putString("sender",senderName);
                    bundle.putString("room",room);
                }
                intent.putExtras(bundle);
                context.startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
