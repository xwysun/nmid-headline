package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.RecMsg;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;

/**
 * Created by xwysun on 2015/11/10.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final String TAG = LogUtils.makeLogTag(MessageAdapter.class);
    private ArrayList<RecMsg> mNewsBeans;

    public MessageAdapter(ArrayList<RecMsg> newsBeans) {
        this.mNewsBeans = newsBeans;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_messagelist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemCardHolder.setCardBackgroundColor(parent.getContext()
                .getResources()
                .getColor(ThemePref.getItemBackgroundResColor(parent.getContext())));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final RecMsg newsBean = mNewsBeans.get(position);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                //Log.d(TAG, "title" + newsBean.getTitle() + "id" + newsBean.getIdmember());
//                Intent intent = new Intent(v.getContext(), DetailedActivity.class);
//                intent.putExtra(WebViewFragment.PARCELABLE_KEY, newsBean);
//                v.getContext().startActivity(intent);
//            }
//        });
        holder.itemEndtime.setText(newsBean.getEndTime());
        holder.itemStarttime.setText(newsBean.getStartTime());
        holder.messagelistItemContent.setText(newsBean.getContent());
        holder.messagelistItemTitle.setText(newsBean.getTitle());
        holder.messageUsername.setText(newsBean.getName());
    }

    @Override
    public int getItemCount() {
        return mNewsBeans.size();
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'adapter_messagelist.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.messagelist_item_title)
        TextView messagelistItemTitle;
        @InjectView(R.id.messagelist_item_content)
        TextView messagelistItemContent;
        @InjectView(R.id.item_starttime)
        TextView itemStarttime;
        @InjectView(R.id.item_endtime)
        TextView itemEndtime;
        @InjectView(R.id.message_username)
        TextView messageUsername;
        @InjectView(R.id.item_card_holder)
        CardView itemCardHolder;

       public ViewHolder(View view) {
           super(view);
           ButterKnife.inject(this, view);
        }
    }
}

