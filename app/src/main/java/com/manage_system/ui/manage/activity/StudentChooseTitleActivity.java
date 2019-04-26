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

public class StudentChooseTitleActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton iv_back;
    Button ct_is_choose;
    EditText ct_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ms_student_ct_item);
        getId();
    }

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, StudentChooseTitleActivity.class);
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

    public void onClick(View v) {//直接调用不会显示v被点击效果
        switch (v.getId()) {
            case R.id.iv_back:
                onViewClicked();
                break;
            case R.id.ct_is_choose:
                showDialog();
                break;
            case R.id.ct_teacher:
                Intent intent = new Intent(StudentChooseTitleActivity.this,GuideTeacherInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(StudentChooseTitleActivity.this, R.style.MyDialog);
        //设置它的ContentView
        dialog.setContentView(R.layout.alert_dialog);
        ((TextView)dialog.findViewById(R.id.tvAlertDialogTitle)).setText("选择课题");
        ((TextView)dialog.findViewById(R.id.tvAlertDialogMessage)).setText("确认选择该课题？");
        dialog.show();

        Button confirm = (Button)dialog.findViewById(R.id.btnAlertDialogPositive);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(StudentChooseTitleActivity.this, "选择成功", Toast.LENGTH_SHORT).show();
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
}