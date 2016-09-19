package com.example.xuguang0417.myprojectaccumulation.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xuguang0417.myprojectaccumulation.R;
import com.example.xuguang0417.myprojectaccumulation.adapter.CeilingAdapter;
import com.example.xuguang0417.myprojectaccumulation.base.BaseActivity;
import com.example.xuguang0417.myprojectaccumulation.entity.StickyModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CeilingActivity extends BaseActivity {

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.text_sticky_header)
    TextView textStickyHeader;

    public CeilingAdapter adapter;
    public List<StickyModel> stickyModelList = new ArrayList<StickyModel>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_ceiling;
    }

    @Override
    public void initView() {
        super.initView();

        titleText.setText("粘性头部");

        assert recyclerView != null;
        assert textStickyHeader != null;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CeilingAdapter(this, setData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        super.setListener();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                /**
                 * 找到RecyclerView的item中，和RecyclerView的getTop 向下相距5个像素的那个item
                 * (尝试2、3个像素位置都找不到，所以干脆用了5个像素)，
                 * 我们根据这个item，来更新吸顶布局的内容，因为我们的StickyLayout展示的信息肯定是最上面的那个item的信息.
                 */
                View stickyInfoView = recyclerView.findChildViewUnder(
                        textStickyHeader.getMeasuredWidth() / 2, 5);
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    textStickyHeader.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                /**
                 * 找到固定在屏幕上方那个FakeStickyLayout下面一个像素位置的RecyclerView的item，
                 * 我们根据这个item来更新假的StickyLayout要translate多少距离.
                 * 并且只处理HAS_STICKY_VIEW和NONE_STICKY_VIEW这两种tag，
                 * 因为第一个item的StickyLayout虽然展示，但是一定不会引起FakeStickyLayout的滚动.
                 */
                View transInfoView = recyclerView.findChildViewUnder(
                        textStickyHeader.getMeasuredWidth() / 2, textStickyHeader.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - textStickyHeader.getMeasuredHeight();

                    /**
                     * 如果当前item需要展示StickyLayout，那么根据这个item的getTop和FakeStickyLayout的高度
                     * 相差的距离来滚动FakeStickyLayout.这里有一处需要注意，如果这个item的getTop已经小于0，
                     * 也就是滚动出了屏幕，那么我们就要把假的StickyLayout恢复原位，来覆盖住这个item对应的吸顶信息.
                     */
                    if (transViewStatus == CeilingAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            textStickyHeader.setTranslationY(dealtY);
                        } else {
                            textStickyHeader.setTranslationY(0);
                        }
                    } else if (transViewStatus == CeilingAdapter.NONE_STICKY_VIEW) {
                        // 如果当前item不需要展示StickyLayout，那么就不会引起FakeStickyLayout的滚动.
                        textStickyHeader.setTranslationY(0);
                    }
                }
            }
        });

        adapter.setOnItemClickListener(new CeilingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(CeilingActivity.this, "第" + position + "个item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnStickyItemClick(View view, int position) {
                Toast.makeText(CeilingActivity.this, "第" + position + "个头部", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.left_layout)
    public void onClick() {
        finish();
    }

    //测试数据
    public List<StickyModel> setData() {
        List<StickyModel> stickyExampleModels = new ArrayList<>();

        for (int index = 0; index < 40; index++) {
            if (index < 5) {
                stickyExampleModels.add(new StickyModel(
                        "1班学生", "姓名" + index, "性别" + index, "职业" + index));
            } else if (index < 15) {
                stickyExampleModels.add(new StickyModel(
                        "2班学生", "姓名" + index, "性别" + index, "职业" + index));
            } else if (index < 25) {
                stickyExampleModels.add(new StickyModel(
                        "3班学生", "姓名" + index, "性别" + index, "职业" + index));
            } else {
                stickyExampleModels.add(new StickyModel(
                        "4班学生", "姓名" + index, "性别" + index, "职业" + index));
            }
        }

        return stickyExampleModels;
    }
}
