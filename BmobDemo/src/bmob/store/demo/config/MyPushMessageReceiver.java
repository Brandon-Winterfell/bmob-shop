package bmob.store.demo.config;

import org.json.JSONException;
import org.json.JSONObject;

import bmob.store.demo.utlis.ToastFactory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.push.PushConstants;

public class MyPushMessageReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
        	//����json
        	try {
				JSONObject getJsonObject = new JSONObject(intent.getStringExtra("msg"));
				String content = getJsonObject.optString("bmobpushText");
				//��ʾ֪ͨ��
				ToastFactory.showNotNotification(context, "",content);
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
    }
}