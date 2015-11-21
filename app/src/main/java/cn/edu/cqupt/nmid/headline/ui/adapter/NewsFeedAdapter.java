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
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.FreshNews;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.ui.activity.DetailedActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.WebViewFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by leon on 1/19/15.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ContentViewHolder> {

  private static final String TAG = LogUtils.makeLogTag(NewsFeedAdapter.class);
  private ArrayList<FreshNews> mNewsBeans;

  public NewsFeedAdapter(ArrayList<FreshNews> newsBeans) {
    this.mNewsBeans = newsBeans;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_content, parent, false);
    ContentViewHolder viewHolder = new ContentViewHolder(view);
    viewHolder.mCardView.setCardBackgroundColor(parent.getContext()
        .getResources()
        .getColor(ThemePref.getItemBackgroundResColor(parent.getContext())));
    return viewHolder;
  }

  @Override public void onBindViewHolder(ContentViewHolder holder, int position) {

    final FreshNews newsBean = mNewsBeans.get(position);
    Log.d("mnewbeans",mNewsBeans.get(position).getNewsPid().toString());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Log.d(TAG, "title" + newsBean.getTitle() + "id" + newsBean.getIdmember());
        Intent intent = new Intent(v.getContext(), DetailedActivity.class);
        Log.e("newsBean", newsBean.getNewsPid().toString());
        intent.putExtra(WebViewFragment.PARCELABLE_KEY, newsBean);
        v.getContext().startActivity(intent);
      }
    });

    holder.title.setText(newsBean.getTitle());
    holder.time.setText(newsBean.getTime());
//    holder.excerpt.setText(newsBean.get().trim());

    //what the fuck api!
    boolean one = false;
    boolean two = false;
    boolean three = false;

    if (!one && !two && !three) {
      holder.threeBottonImages.setVisibility(View.GONE);
      holder.iv_single.setVisibility(View.GONE);
      return;
    }
//
//    if (one && !two && !three) {
//      holder.iv_single.setVisibility(View.VISIBLE);
//      holder.threeBottonImages.setVisibility(View.GONE);
//      Picasso.with(holder.iv_single.getContext()).load(newsBean.getImage1()).into(holder.iv_single);
//      return;
//    }
//
//    if (one && two) {
//      holder.threeBottonImages.setVisibility(View.VISIBLE);
//      holder.iv_single.setVisibility(View.GONE);
//      Picasso.with(holder.iv_all_1.getContext()).load(newsBean.getImage1()).into(holder.iv_all_1);
//      Picasso.with(holder.iv_all_2.getContext()).load(newsBean.getImage2()).into(holder.iv_all_2);
//
//      if (three) {
//        //111
//        Picasso.with(holder.iv_all_3.getContext()).load(newsBean.getImage3()).into(holder.iv_all_3);
//      } else {
//        //110
//        holder.iv_all_3.setVisibility(View.GONE);
//      }
//      return;
//    }
  }

  @Override public int getItemCount() {
    return mNewsBeans.size();
  }

  public static class ContentViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_card_holder) CardView mCardView;
    @InjectView(R.id.item_title) TextView title;
    @InjectView(R.id.item_time) TextView time;
    @InjectView(R.id.item_excerpt) TextView excerpt;
    @InjectView(R.id.item_image_single) ImageView iv_single;
    @InjectView(R.id.item_image_botton_1) ImageView iv_all_1;
    @InjectView(R.id.item_image_botton_2) ImageView iv_all_2;
    @InjectView(R.id.item_image_botton_3) ImageView iv_all_3;
    @InjectView(R.id.item_three_images_layout) LinearLayout threeBottonImages;

    public ContentViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}

