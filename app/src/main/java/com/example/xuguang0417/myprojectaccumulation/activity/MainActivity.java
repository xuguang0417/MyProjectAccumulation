package com.example.xuguang0417.myprojectaccumulation.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xuguang0417.myprojectaccumulation.R;
import com.example.xuguang0417.myprojectaccumulation.adapter.MainAdapter;
import com.example.xuguang0417.myprojectaccumulation.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    private List<String> mList;
    private MainAdapter adapter;
    private Intent intent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();

        leftLayout.setVisibility(View.GONE);
        titleText.setText("吸顶效果");

        mList = new ArrayList<String>();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initData() {
        super.initData();

        mList.add("粘性头部");

        adapter = new MainAdapter(this, mList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        super.setListener();

        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, CeilingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                intent = null;
            }
        });
    }
}
