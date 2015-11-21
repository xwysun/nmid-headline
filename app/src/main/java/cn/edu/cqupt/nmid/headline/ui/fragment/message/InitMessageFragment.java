package cn.edu.cqupt.nmid.headline.ui.fragment.message;

import android.view.View;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.ui.adapter.MessageAdapter;
import cn.edu.cqupt.nmid.headline.ui.fragment.base.NewsFeedFragment;

/**
 * Created by xwysun on 2015/11/10.
 */
public class InitMessageFragment extends MessageFragment {
//
//    @Override void loadNewFeeds() {
//        List<Feed> feeds = new ArrayList<>();
//        feeds = new Select().from(Feed.class)
//                //.where("isLike=?", true)
//                .orderBy("idMember desc")
//                .limit(this.feed_limit)
//                .execute();
//        if (feeds == null || feeds.isEmpty()) {
//            showErrorView(View.VISIBLE);
//        } else {
//            newsBeans.addAll(feeds);
//            adapter.notifyDataSetChanged();
//        }
//        mSwipeRefreshLayout.setRefreshing(false);
//    }
}