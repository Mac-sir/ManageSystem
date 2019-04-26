package com.manage_system.ui.manage.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.manage_system.R;
import com.manage_system.ui.personal.GuideTeacherInfoActivity;

public class StudentGuideReportActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton iv_back;
    Button guide_record_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ms_student_pd_guide_record);
        getId();
    }

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, StudentGuideReportActivity.class);
    }

    /**
     * 获取id
     */
    private void getId()
    {
        iv_back = (ImageButton)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        guide_record_edit = (Button)findViewById(R.id.guide_record_edit);
        guide_record_edit.setOnClickListener(this);
    }

    public void onClick(View v) {//直接调用不会显示v被点击效果
        switch (v.getId()) {
            case R.id.iv_back:
                onViewClicked();
                break;
            case R.id.guide_record_edit:
                Intent intent = new Intent(StudentGuideReportActivity.this,StudentGuideReportEditActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void onViewClicked() {
        finish();
    }
}