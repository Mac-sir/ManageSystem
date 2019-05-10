package com.manage_system.ui.manage.activity.teacher;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage_system.R;
import com.manage_system.net.ApiConstants;
import com.manage_system.ui.personal.GuideStudentInfoActivity;
import com.manage_system.utils.DownloadUtil;
import com.manage_system.utils.OkManager;
import com.manage_system.utils.OpenFileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class TeacherScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private String id = null;
    private String task_fileId = null;
    private Context mContext;
    private String fileName = null;

    @BindView(R.id.stu_name_id)
    EditText stu_name_id;
    @BindView(R.id.pName)
    EditText pName;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.scoreWorth)
    EditText scoreWorth;
    @BindView(R.id.scoreTotal)
    EditText scoreTotal;
    @BindView(R.id.scoreReport)
    EditText scoreReport;
    @BindView(R.id.scoreProcess)
    EditText scoreProcess;
    @BindView(R.id.scoreDefence)
    EditText scoreDefence;
    private static String TAG = "选题界面";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ms_teacher_reply_plan_score);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        SharedPreferences sp=getSharedPreferences("processData", MODE_PRIVATE);
        JSONObject obj = JSON.parseObject(sp.getString("showDefStudents" , ""));
        Log.w(TAG,obj.toString());
        JSONArray array = new JSONArray(obj.getJSONArray("studentList"));
        Log.w(TAG,array.toString());
        Log.w(TAG,"position==="+intent.getStringExtra("position"));

        JSONObject object = array.getJSONObject(Integer.parseInt(intent.getStringExtra("position")));
        JSONObject defScore = object.getJSONObject("defScore");

        Log.e(TAG,defScore.toString());
        stu_name_id.setText(object.getString("name")+"（"+object.getString("identifier")+"）");
        pName.setText(object.getString("pName"));
        comment.setText(defScore.getString("comment"));
        scoreWorth.setText(defScore.getString("scoreWorth"));
        scoreTotal.setText(defScore.getString("scoreTotal"));
        scoreReport.setText(defScore.getString("scoreReport"));
        scoreProcess.setText(defScore.getString("scoreProcess"));
        scoreDefence.setText(defScore.getString("scoreDefence"));
    }

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, TeacherScoreActivity.class);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View v) {//直接调用不会显示v被点击效果
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

}