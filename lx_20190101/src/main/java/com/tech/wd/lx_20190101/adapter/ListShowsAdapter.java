package com.tech.wd.lx_20190101.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tech.wd.lx_20190101.R;
import com.tech.wd.lx_20190101.bean.ComputerBean;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<ComputerBean.DataBean> list;
    private Boolean mBoolean;

    public ListShowsAdapter(Context context, List<ComputerBean.DataBean> list, Boolean aBoolean) {
        mContext = context;
        this.list = list;
        mBoolean = aBoolean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mBoolean){

            View view = View.inflate(parent.getContext(), R.layout.item_linelayout, null);
            LineViewHolder holder = new LineViewHolder(view);
            return holder;

        }else {
            View view = View.inflate(parent.getContext(), R.layout.item_gridlayout, null);
            GridViewHolder holder = new GridViewHolder(view);
            return holder;
        }
    }

    public interface onItemClickListener{
        void onItemClick(int layoutPosition, String title, String price);
    }

    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (mBoolean){

            final LineViewHolder lineViewHolder = (LineViewHolder) holder;

            lineViewHolder.mTextView_title.setText(list.get(position).getTitle());
            lineViewHolder.mTextView_price.setText(list.get(position).getPrice()+"");

            String images = list.get(position).getImages();
            Pattern pattern = Pattern.compile("\\|");
            String[] img = pattern.split(images);

            Uri uri = Uri.parse(img[0]);
            lineViewHolder.mSimpleDraweeView.setImageURI(uri);
            lineViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener!=null){
                        int layoutPosition = lineViewHolder.getLayoutPosition();
                        String title = list.get(position).getTitle();
                        String price = list.get(position).getPrice()+"";

                        mOnItemClickListener.onItemClick(layoutPosition,title,price);
                    }
                }
            });

        }else {

            final GridViewHolder gridViewHolder = (GridViewHolder) holder;

            gridViewHolder.mTextView_title.setText(list.get(position).getTitle());
            gridViewHolder.mTextView_price.setText(list.get(position).getPrice()+"");

            String images = list.get(position).getImages();
            Pattern pattern = Pattern.compile("\\|");
            String[] img = pattern.split(images);

            Uri uri = Uri.parse(img[0]);
            gridViewHolder.mSimpleDraweeView.setImageURI(uri);

            gridViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener!=null){
                        int layoutPosition = gridViewHolder.getLayoutPosition();
                        String title = list.get(position).getTitle();
                        String price = list.get(position).getPrice()+"";

                        mOnItemClickListener.onItemClick(layoutPosition,title,price);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LineViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_line_img)
        SimpleDraweeView mSimpleDraweeView;

        @BindView(R.id.item_line_text_title)
        TextView mTextView_title;

        @BindView(R.id.item_line_text_price)
        TextView mTextView_price;
        public LineViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_grid_img)
        SimpleDraweeView mSimpleDraweeView;

        @BindView(R.id.item_grid_text_title)
        TextView mTextView_title;

        @BindView(R.id.item_grid_text_price)
        TextView mTextView_price;
        public GridViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
