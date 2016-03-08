package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.FreshNews;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Image;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.TechList;
import cn.edu.cqupt.nmid.headline.ui.activity.DetailedActivity;
import cn.edu.cqupt.nmid.headline.ui.activity.WebUtilActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.WebViewFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;

/**
 * Created by xwysun on 2016/1/3.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private static final String TAG = LogUtils.makeLogTag(NewsFeedAdapter.class);
    private ArrayList<TechList> mNewsBeans;

    public ProjectAdapter(ArrayList<TechList> newsBeans) {
        this.mNewsBeans = newsBeans;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_project, parent, false);
       ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemCardHolder.setCardBackgroundColor(parent.getContext()
                .getResources()
                .getColor(ThemePref.getItemBackgroundResColor(parent.getContext())));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final TechList newsBean = mNewsBeans.get(position);
        Log.d("mnewbeans", mNewsBeans.get(position).getId().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "title" + newsBean.getTitle() + "id" + newsBean.getIdmember());
                Intent intent = new Intent(v.getContext(), WebUtilActivity.class);
                Bundle data=new Bundle();
                data.putSerializable("URL",newsBean.getUrl());
                intent.putExtras(data);
                v.getContext().startActivity(intent);
            }
        });

        holder.projectName.setText(newsBean.getTitle().trim());
        holder.projectCreater.setText("团队："+newsBean.getAuthor().trim());
        holder.projectInfo.setText("简介："+newsBean.getSimpleDescribe().trim());
        Picasso.with(holder.projectImg.getContext())
                .load(newsBean.getCover())
                .resize(250,250).into(holder.projectImg);

    }

    @Override
    public int getItemCount() {
        return mNewsBeans.size();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'adapter_project.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.project_img)
        ImageView projectImg;
        @InjectView(R.id.project_name)
        TextView projectName;
        @InjectView(R.id.project_creater)
        TextView projectCreater;
        @InjectView(R.id.project_info)
        TextView projectInfo;
        @InjectView(R.id.item_card_holder)
        CardView itemCardHolder;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

