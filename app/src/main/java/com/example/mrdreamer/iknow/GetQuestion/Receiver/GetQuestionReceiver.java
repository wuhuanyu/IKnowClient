package com.example.mrdreamer.iknow.GetQuestion.Receiver;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.Button;

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
   private Handler handler;
    private static final int  TARGET_CODE=1;
    public static final String ACTION_1="com.tencent.android.tpush.action.PUSH_MESSAGE",
    ACTION_2="com.tencent.android.tpush.action.FEEDBACK";
    public GetQuestionReceiver(Handler h){
        this.handler=h;
      //  this.handler=handler;

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
           if(!jsonObject.isNull("target_code")) {
               int target=jsonObject.getInt("target_code");

               if(target!=TARGET_CODE)return;
             //  Log.i()
           }
            String content=jsonObject.getString("content");
            String ans_a=jsonObject.getString("ans_a");
            String ans_b=jsonObject.getString("ans_b");
            String ans_c=jsonObject.getString("ans_c");
            String ans_d=jsonObject.getString("ans_d");
            int rightIndex=jsonObject.getInt("right_index");
            Message message=new Message();
            Bundle bundle=new Bundle();

            bundle.putString("content",content);
            bundle.putString("ans_a",ans_a);
            bundle.putString("ans_b",ans_b);
            bundle.putString("ans_c",ans_c);
            bundle.putString("ans_d",ans_d);
            bundle.putInt("right_index",rightIndex);
            message.setData(bundle);
            handler.dispatchMessage(message);

            Log.i("GetQuestionReceiver",customObject);

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
