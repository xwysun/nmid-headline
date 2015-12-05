package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.content.Intent;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.GetInfo;
import cn.edu.cqupt.nmid.headline.ui.activity.DetailedActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.WebViewFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;

/**
 * Created by xwysun on 2015/12/4.
 */
public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private static final String TAG = LogUtils.makeLogTag(TeacherAdapter.class);
    private ArrayList<GetInfo> mNewsBeans;

    public TeacherAdapter(ArrayList<GetInfo> newsBeans) {
        this.mNewsBeans = newsBeans;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_teacher, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemCardHolder.setCardBackgroundColor(parent.getContext()
                .getResources()
                .getColor(ThemePref.getItemBackgroundResColor(parent.getContext())));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final GetInfo newsBean = mNewsBeans.get(position);
        final String image = newsBean.getImgUrl();
        Log.d("mnewbeans", mNewsBeans.get(position).getTeacherInfoPid().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "title" + newsBean.getTitle() + "id" + newsBean.getIdmember());
                Intent intent = new Intent(v.getContext(), DetailedActivity.class);
                Log.e("newsBean", newsBean.getTeacherInfoPid().toString());
                intent.putExtra(WebViewFragment.PARCELABLE_KEY, newsBean);
                intent.putExtra("mode", WebViewFragment.MODE_TEACHER);
                v.getContext().startActivity(intent);
            }
        });

        holder.teacherName.setText(newsBean.getName());
        //约为90：130
        Log.d(TAG,image);
        Picasso.with(holder.teacherIcon.getContext()).load(image).resize(90,130).centerInside().into(holder.teacherIcon);
    }

    @Override
    public int getItemCount() {
        return mNewsBeans.size();
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'adapter_teacher.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.teacherIcon)
        ImageView teacherIcon;
        @InjectView(R.id.teacherName)
        TextView teacherName;
        @InjectView(R.id.item_card_holder)
        CardView itemCardHolder;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
