package com.example.mrdreamer.iknow.GetQuestion.Receiver;

import android.content.Context;
import android.os.Handler;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mrdreamer on 16-9-15.
 */
public class GetQuestionReceiver extends XGPushBaseReceiver {
   private final Handler handler;

    public GetQuestionReceiver(Handler handler){
        this.handler=handler;

    }
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
        JSONObject jsonObject=null;
        String customObject=xgPushTextMessage.getCustomContent();
        try{
             jsonObject=new JSONObject(customObject);
            if(jsonObject==null)
                throw new JSONException("Data Error");
            String content=jsonObject.getString("content");
            String ans_a=jsonObject.getString("ans_a");
            String ans_b=jsonObject.getString("ans_b");
            String ans_c=jsonObject.getString("ans_c");
            String ans_d=jsonObject.getString("ans_d");
            int rightIndex=jsonObject.getInt("right_index");





        } catch (JSONException e) {
            e.printStackTrace();
        }
       // if(jsonObject==null)return;
       // if(!jsonObject.isNull())




    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
