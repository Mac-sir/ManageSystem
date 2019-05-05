package com.manage_system.ui.manage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage_system.R;
import com.manage_system.net.ApiConstants;
import com.manage_system.ui.manage.adapter.MyAdapter;
import com.manage_system.utils.OkManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class StudentReplyPlanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton iv_back;
    private String TAG = "答辩安排";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ms_student_reply_plan);
        getId();
        initData();
    }

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, StudentReplyPlanActivity.class);
    }

    /**
     * 获取id
     */
    private void getId()
    {
        iv_back = (ImageButton)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    public void initData() {
        OkManager manager = OkManager.getInstance();
        Map<String, String> map = new HashMap<String, String>();
        manager.post(ApiConstants.studentApi + "/showDefence", map,new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ",e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                Log.e(TAG,responseBody);
                final JSONObject obj = JSON.parseObject(responseBody);
                final String msg = obj.getString("msg");
                Log.e(TAG,responseBody);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(obj.get("statusCode").equals(100)){

                        }else{
                            Toast.makeText(StudentReplyPlanActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void onClick(View v) {//直接调用不会显示v被点击效果
        switch (v.getId()) {
            case R.id.iv_back:
                onViewClicked();
                break;
            default:
                break;
        }
    }

    public void onViewClicked() {
        finish();
    }
}