package com.example.xuguang0417.myprojectaccumulation.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xuguang0417.myprojectaccumulation.R;
import com.example.xuguang0417.myprojectaccumulation.entity.StickyModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuguang on 2016/9/19.
 */
public class CeilingAdapter extends RecyclerView.Adapter {

    public static final int FIRST_STICKY_VIEW = 1;  //第一个粘性头部   RecyclerView 的第一个item，肯定是展示StickyLayout的.
    public static final int HAS_STICKY_VIEW = 2;    //有粘性头部     RecyclerView 除了第一个item以外，要展示StickyLayout的.
    public static final int NONE_STICKY_VIEW = 3;   //没有粘性头部    RecyclerView 的不展示StickyLayout的item.

    private Context context;
    private List<StickyModel> stickyModelList;

    public CeilingAdapter(Context context, List<StickyModel> stickyModelList) {
        this.context = context;
        this.stickyModelList = stickyModelList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void OnStickyItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            //改变背景颜色  以作区分
            if (position % 2 == 0) {
                itemViewHolder.realContentWrapper.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_color_1));
            } else {
                itemViewHolder.realContentWrapper.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_color_2));
            }

            itemViewHolder.textName.setText(stickyModelList.get(position).getName());
            itemViewHolder.textGender.setText(stickyModelList.get(position).getSex());
            itemViewHolder.textProfession.setText(stickyModelList.get(position).getProfession());

            if (position == 0) {
                itemViewHolder.textStickyHeader.setVisibility(View.VISIBLE);
                itemViewHolder.textStickyHeader.setText(stickyModelList.get(position).getSticky());

                //第一个item的吸顶信息肯定是展示的，并且标记tag为FIRST_STICKY_VIEW
                itemViewHolder.itemView.setTag(FIRST_STICKY_VIEW);
            } else {
                //之后的item都会和前一个item要展示的吸顶信息进行比较，不相同就展示，并且标记tag为HAS_STICKY_VIEW
                if (!TextUtils.equals(stickyModelList.get(position).getSticky(),
                        stickyModelList.get(position - 1).sticky)) {
                    itemViewHolder.textStickyHeader.setVisibility(View.VISIBLE);
                    itemViewHolder.textStickyHeader.setText(stickyModelList.get(position).getSticky());
                    itemViewHolder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    // 相同就不展示，并且标记tag为NONE_STICKY_VIEW
                    itemViewHolder.textStickyHeader.setVisibility(View.GONE);
                    itemViewHolder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }
            // ContentDescription 用来记录并获取要吸顶展示的信息
            itemViewHolder.itemView.setContentDescription(stickyModelList.get(position).getSticky());

            itemViewHolder.textStickyHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnStickyItemClick(view, position);
                }
            });

            itemViewHolder.realContentWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stickyModelList == null ? 0 : stickyModelList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView textName;
        @BindView(R.id.tv_gender)
        TextView textGender;
        @BindView(R.id.tv_profession)
        TextView textProfession;
        @BindView(R.id.rl_content_wrapper)
        RelativeLayout realContentWrapper;          //吸顶下部的部分
        @BindView(R.id.text_sticky_header)
        TextView textStickyHeader;                  //吸顶显示

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
