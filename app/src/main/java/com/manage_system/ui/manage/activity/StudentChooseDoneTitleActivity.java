package com.manage_system.ui.manage.activity;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage_system.R;
import com.manage_system.net.ApiConstants;
import com.manage_system.ui.manage.Manage;
import com.manage_system.ui.personal.GuideTeacherInfoActivity;
import com.manage_system.utils.DateUtil;
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

public class StudentChooseDoneTitleActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton iv_back;
    Button ct_is_choose;
    EditText ct_teacher;
    EditText ct_topic,ct_type,ct_resource,ct_number;
    private String id = null;
    private String task_fileId = null;
    private String annex_fileId = null;
    private Context mContext;
    private String fileName = null;
    @BindView(R.id.ct_profession)
    EditText ct_profession;
    @BindView(R.id.ct_time)
    EditText ct_time;
    @BindView(R.id.ct_task)
    EditText ct_task;
    @BindView(R.id.ct_annex)
    EditText ct_annex;
    @BindView(R.id.ct_detail)
    EditText ct_detail;
    private String TAG = "已选题界面";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ms_student_ct_detail);
        ButterKnife.bind(this);
        getId();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.w(TAG,id);

        ct_topic = (EditText) findViewById(R.id.ct_topic);
        ct_type = (EditText)findViewById(R.id.ct_type);
        ct_resource = (EditText)findViewById(R.id.ct_resource);
        ct_number = (EditText)findViewById(R.id.ct_number);
        ct_is_choose.setText("取　消");

        SharedPreferences sp=getSharedPreferences("chooseTitle", MODE_PRIVATE);
        Log.w(TAG,sp.getString("obj" , ""));
        JSONObject obj = JSON.parseObject(sp.getString("obj" , ""));
        JSONArray array = new JSONArray(obj.getJSONObject("data").getJSONArray("projects"));
        for (int i = 0; i < Integer.parseInt(obj.getJSONObject("data").getString("count")); i++) {
            JSONObject object = array.getJSONObject(i);
            JSONObject project = object.getJSONObject("project");
            JSONObject teacher = object.getJSONObject("teacher");
            if(project.getString("id").equals(id)){
                fileName = project.getString("title");
                ct_topic.setText(project.getString("title"));
                ct_type.setText(project.getString("genre"));
                ct_resource.setText(project.getString("source"));
                ct_number.setText(project.getString("rest"));
                ct_teacher.setText(teacher.getString("name"));
                ct_profession.setText(project.getString("major"));
                ct_time.setText(DateUtil.getDateFormat(object.getString("setDate")));
                ct_detail.setText(project.getString("briefIntro"));
                task_fileId = project.getJSONObject("taskBook").getString("fileId");
                annex_fileId = project.getString("fileId");
                if(task_fileId.equals("0")){
                    ct_task.setEnabled(false);
                    ct_task.setText("暂无任务书");
                }else{
                    ct_task.setText(Html.fromHtml("<u>"+project.getString("title")+".任务书"+"</u>"));
                }
                if(annex_fileId.equals("0")){
                    ct_annex.setEnabled(false);
                    ct_annex.setText("暂无附件");
                }else{
                    ct_annex.setText(Html.fromHtml("<u>"+project.getString("title")+".附件"+"</u>"));
                }
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("teacher",teacher.toString());
                editor.commit();
            }
        }

    }

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, StudentChooseDoneTitleActivity.class);
    }

    /**
     * 获取id
     */
    private void getId()
    {
        iv_back = (ImageButton)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ct_is_choose = (Button)findViewById(R.id.ct_is_choose);
        ct_is_choose.setOnClickListener(this);
        ct_teacher = (EditText)findViewById(R.id.ct_teacher);
        ct_teacher.setOnClickListener(this);
    }

    @OnClick({R.id.ct_task,R.id.ct_annex})
    public void onClick(View v) {//直接调用不会显示v被点击效果
        switch (v.getId()) {
            case R.id.iv_back:
                onViewClicked();
                break;
            case R.id.ct_is_choose:
                showDialog();
                break;
            case R.id.ct_teacher:
                Intent intent = new Intent(StudentChooseDoneTitleActivity.this,GuideTeacherInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.ct_task:
                downLoad("下载文件","确认下载任务书？",task_fileId);
                break;
            case R.id.ct_annex:
                downLoad("下载文件","确认下载附件？",annex_fileId);
                break;
            default:
                break;
        }
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(StudentChooseDoneTitleActivity.this, R.style.MyDialog);
        //设置它的ContentView
        dialog.setContentView(R.layout.alert_dialog);
        ((TextView)dialog.findViewById(R.id.tvAlertDialogTitle)).setText("取消课题");
        ((TextView)dialog.findViewById(R.id.tvAlertDialogMessage)).setText("是否取消选择？");
        dialog.show();

        Button confirm = (Button)dialog.findViewById(R.id.btnAlertDialogPositive);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                OkManager manager = OkManager.getInstance();
                Map<String, String> map = new HashMap<String, String>();
                map.put("pid", id);
                Log.w(TAG,id+"取消");
                manager.post(ApiConstants.studentApi+"/cancelProject", map,new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure: ",e);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        final JSONObject obj = JSON.parseObject(responseBody);
                        Log.e(TAG,obj.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(obj.get("statusCode").equals(100)) {
                                    Toast.makeText(StudentChooseDoneTitleActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(StudentChooseDoneTitleActivity.this,StudentChooseDoneTitleMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Manage.getData();
                                }else{
                                    Toast.makeText(StudentChooseDoneTitleActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

            }
        });
        Button cancel = (Button) dialog.findViewById(R.id.btnAlertDialogNegative);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }

    public void downLoad(String title,String message,final String fileId){
        final Dialog dialog = new Dialog(StudentChooseDoneTitleActivity.this, R.style.MyDialog);
        //设置它的ContentView
        dialog.setContentView(R.layout.alert_dialog);
        ((TextView)dialog.findViewById(R.id.tvAlertDialogTitle)).setText(title);
        ((TextView)dialog.findViewById(R.id.tvAlertDialogMessage)).setText(message);
        dialog.show();

        Button confirm = (Button)dialog.findViewById(R.id.btnAlertDialogPositive);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Map<String, String> map = new HashMap<String, String>();
                map.put("fileId",fileId);
                Log.w(TAG,fileId+"嘻嘻");
                //文件在手机内存存储的路径
                final String saveurl="/download/";
                Log.w(TAG,"路径1："+saveurl);
                //配置progressDialog
                final ProgressDialog dialog= new ProgressDialog(StudentChooseDoneTitleActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                dialog.setTitle("正在下载中");
                dialog.setMessage("请稍后...");
                dialog.setProgress(0);
                dialog.setMax(100);
                dialog.show();
                Log.w(TAG,ApiConstants.commonApi + "/downloadFile"+"?fileId="+fileId);
                //启动下载方法
                DownloadUtil.get().download(StudentChooseDoneTitleActivity.this, ApiConstants.commonApi + "/downloadFile"+"?fileId="+fileId,saveurl,fileName,  new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getNotificationManager().notify(1,getNotification("文件下载成功，点击进行查看",-1));
                                Toast.makeText(StudentChooseDoneTitleActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                                    return;
                                }

                                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/download/"+fileName+".doc");
                                Log.w(TAG,"路径2："+file);
                                try {
                                    Log.w(TAG,"打开");
                                    OpenFileUtils.openFile(mContext, file);
                                } catch (Exception e) {
                                    Log.w(TAG,"无打开方式");
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onDownloading(int progress) {

                        dialog.setProgress(progress);
                        getNotificationManager().notify(1,getNotification("下载中...",progress));
                    }

                    @Override
                    public void onDownloadFailed() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getNotificationManager().notify(1,getNotification("下载失败",-1));
                                Toast.makeText(StudentChooseDoneTitleActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                });

            }
        });
        Button cancel = (Button) dialog.findViewById(R.id.btnAlertDialogNegative);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }

    public void onViewClicked() {
        finish();
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    // 显示对话框；
    private Notification getNotification(String title, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(StudentChooseDoneTitleActivity.this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        if (progress > 0) {
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }

}