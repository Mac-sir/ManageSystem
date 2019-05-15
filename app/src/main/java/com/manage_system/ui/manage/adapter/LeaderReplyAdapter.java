package com.manage_system.ui.manage.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.manage_system.R;
import com.manage_system.ui.manage.activity.manager.ManagerGroupStudentMainActivity;
import com.manage_system.ui.manage.activity.manager.ManagerReplyTypeActivity;
import com.manage_system.ui.manage.activity.manager.ManagerScoreListMainActivity;
import com.manage_system.ui.manage.activity.manager.ManagerScoreTotalMainActivity;
import com.manage_system.ui.manage.activity.student.StudentReplyGradeActivity;
import com.manage_system.ui.manage.activity.student.StudentReplyPlanActivity;
import com.manage_system.ui.manage.activity.teacher.TeacherCheckDataMainActivity;
import com.manage_system.ui.manage.activity.teacher.TeacherGradeMainActivity;
import com.manage_system.ui.manage.activity.teacher.TeacherGtCommentMainActivity;
import com.manage_system.ui.manage.activity.teacher.TeacherReplyPlanMainActivity;

import static android.content.ContentValues.TAG;

public class LeaderReplyAdapter extends RecyclerView.Adapter<LeaderReplyAdapter.AuthorViewHolder> {

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.ms_manager_reply, parent, false);
        AuthorViewHolder viewHolder = new AuthorViewHolder(childView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {
        // 答辩分组
        holder.m_reply_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ManagerReplyTypeActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        // 计算答辩总成绩
        holder.m_reply_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ManagerScoreListMainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        holder.m_reply_teacher_group.setVisibility(View.GONE);

        // 学生答辩分组统计报表
        holder.m_reply_student_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ManagerGroupStudentMainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        // 成绩汇总统计报表
        holder.m_reply_total_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ManagerScoreTotalMainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.m_reply_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),TeacherGradeMainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class AuthorViewHolder extends RecyclerView.ViewHolder {

        LinearLayout m_reply_group;
        LinearLayout m_reply_grade;
        LinearLayout m_reply_teacher_group;
        LinearLayout m_reply_student_group;
        LinearLayout m_reply_total_grade;
        LinearLayout m_reply_data;

        public AuthorViewHolder(View itemView) {
            super(itemView);
            m_reply_group = (LinearLayout) itemView.findViewById(R.id.m_reply_group);
            m_reply_grade = (LinearLayout) itemView.findViewById(R.id.m_reply_grade);
            m_reply_teacher_group = (LinearLayout) itemView.findViewById(R.id.m_reply_teacher_group);
            m_reply_student_group = (LinearLayout) itemView.findViewById(R.id.m_reply_student_group);
            m_reply_total_grade = (LinearLayout) itemView.findViewById(R.id.m_reply_total_grade);
            m_reply_data = (LinearLayout) itemView.findViewById(R.id.m_reply_data);
        }
    }
}
