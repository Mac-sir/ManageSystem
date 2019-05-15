package com.manage_system.ui.manage.activity.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.manage_system.R;
import com.manage_system.ui.manage.activity.teacher.TeacherReplyMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerReplyTypeActivity extends Activity {

	@BindView(R.id.iv_back)
	ImageButton iv_back;
	@BindView(R.id.teacher)
	TextView teacher;
	@BindView(R.id.student)
	TextView student;
	@BindView(R.id.top_title)
	TextView top_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ms_teacher_reply_plan);
		ButterKnife.bind(this);
		top_title.setText("答辩分组");
		teacher.setText("答辩教师");
		student.setText("指导教师");
	}

	@OnClick({R.id.iv_back,R.id.teacher_info,R.id.student_info})
	public void onClick(View v) {//直接调用不会显示v被点击效果
		Intent intent;
		switch (v.getId()) {
			case R.id.teacher_info:
				intent = new Intent(ManagerReplyTypeActivity.this,ManagerReplyGroupMainActivity.class);
				intent.putExtra("reply_group","teacher_reply");
				startActivity(intent);
				break;
			case R.id.student_info:
				intent = new Intent(ManagerReplyTypeActivity.this,ManagerReplyGroupMainActivity.class);
				intent.putExtra("reply_group","teacher_guide");
				startActivity(intent);
				break;
			case R.id.iv_back:
				finish();
				break;
			default:
				break;
		}
	}
}
