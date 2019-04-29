package com.manage_system.ui.index.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manage_system.R;
import com.manage_system.ui.index.adapter.AnnouncementAdapter;
import com.manage_system.ui.manage.adapter.ChooseTitleAdapter;

public class AnnouncementFragment extends Fragment {

    public static Fragment newInstance() {
        AnnouncementFragment fragment = new AnnouncementFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AnnouncementAdapter());
        return rootView;
    }
}
