package cn.edu.cqupt.nmid.headline.ui.fragment.message;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.event.NightModeEvent;
import cn.edu.cqupt.nmid.headline.support.pref.HttpPref;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.MessageGson;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.RecMsg;
import cn.edu.cqupt.nmid.headline.ui.adapter.MessageAdapter;
import cn.edu.cqupt.nmid.headline.ui.adapter.NewsFeedAdapter;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import retrofit.Callback;
import retrofit.Response;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.LOGD;
import static cn.edu.cqupt.nmid.headline.utils.LogUtils.makeLogTag;

/**
 * Created by xwysun on 2015/11/10.
 */
public class MessageFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";
    private static final String ARG_CATEGORY = "slug";
    private static final String ARG_FAV = "favorite";
    private int OFFSET=1;
    //1为5条，2为10条，3为15条

    String TAG = makeLogTag(MessageFragment.class);
    /**
     * Injected Vies
     */
    @InjectView(R.id.feed_recyclerview)
    RecyclerView mRecyclerview;
    @InjectView(R.id.feed_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.feed_floating_actionButton)
    FloatingActionButton mFloatingActionButton;

    @InjectView(R.id.loading_tips)
    TextView mTvloading_tips;
    @InjectView(R.id.loading_refresh)
    Button mBtnloading_refresh;
    /**
     * Data
     */
    LinearLayoutManager mLayoutManager;
    ArrayList<RecMsg> newsBeans = new ArrayList<>();
    MessageAdapter adapter;
    int msg_id;
    private String title;
    protected int msg_limit = 15;
    private int feed_category = HeadlineService.CATE_ALUMNUS;
    private boolean isLoadingMore = false;
    private String classNo;
    private String Mode;
    private String account;
    private String password;

    public static MessageFragment newInstance(String title, int type) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_CATEGORY, type);
        fragment.setArguments(args);
        return fragment;
    }

    public static MessageFragment newFavInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_FAV, true);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.feed_floating_actionButton) void feed_multiple_actions() {
        loadNewFeeds();
    }

    @OnClick(R.id.loading_refresh) void loading_refresh() {
        loadNewFeeds();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        getArgsAndPrefs();
    }

    private void getArgsAndPrefs() {
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            feed_category = getArguments().getInt(ARG_CATEGORY);
        } else {
            Log.e(TAG, "getArguments == null!");
        }
        msg_limit = HttpPref.getQueryFeedsLimit(getActivity());
    }

    @Override public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                       Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Mode=sharedPreferences.getString("MODE", "NONE");
        password=sharedPreferences.getString("password", "NONE");
        account=sharedPreferences.getString("account","NONE");
        classNo=sharedPreferences.getString("classNo","NONE");
        Log.d("share",Mode+account+password+classNo);
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        //set for night mode
        mRecyclerview.setBackgroundResource(ThemePref.getBackgroundResColor(getActivity()));
        mFloatingActionButton.setColorNormalResId(
                ThemePref.getToolbarBackgroundResColor(getActivity()));
        mFloatingActionButton.setColorPressedResId(ThemePref.getItemBackgroundResColor(getActivity()));

        mFloatingActionButton.setIcon(R.drawable.ic_reload_48dp);

        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        adapter = new MessageAdapter(newsBeans);
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {

                loadNewFeeds();
            }
        });
        //Endless RecyclerView
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount 表示剩下2个item自动加载
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (!isLoadingMore) {
                        loadOldNews();
                    }
                }
            }
        });
        loadDbFeeds();
    }

    void loadNewFeeds() {
        if (Mode.equals("student")&&!classNo.equals("NONE")) {
            mRecyclerview.smoothScrollToPosition(0);
            RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT_TEST)
                    .create(HeadlineService.class)
                    .receiveMessage(OFFSET, classNo).enqueue(new Callback<MessageGson>() {
                @Override
                public void onResponse(Response<MessageGson> response) {
                    dispatchSuccess(response.body(), true);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }else if (Mode.equals("teacher")&&!account.equals("NONE")&&!password.equals("NONE"))
        {
            mRecyclerview.smoothScrollToPosition(0);
            RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT_TEST)
                    .create(HeadlineService.class)
                    .receiveTeacherMessage(OFFSET,account,password).enqueue(new Callback<MessageGson>() {
                @Override
                public void onResponse(Response<MessageGson> response) {
                    dispatchSuccess(response.body(), true);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
            
        }else {
            Toast.makeText(getActivity(),"内部错误，请清除缓存后重试",Toast.LENGTH_SHORT);
        }
    }

    //private void cacheToDb(List<Feed> feeds) {
    //  //如果你同时只操作一个数据库的话，就用被注释掉的方法，反之用下面的
    //  //try {
    //  //  for (Feed feed : feeds) {
    //  //    feed.save();
    //  //  }
    //  //  ActiveAndroid.setTransactionSuccessful();
    //  //} finally {
    //  //  ActiveAndroid.endTransaction();
    //  //}
    //  for (Feed feed : feeds) {
    //    feed.save();
    //  }
    //}

    void loadOldNews() {
        isLoadingMore = true;
        msg_id = newsBeans.get(newsBeans.size()/5+1).getMessagePid();
        RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT_TEST)
                .create(HeadlineService.class)
                .receiveMessage(msg_id,classNo).enqueue(new Callback<MessageGson>() {
            @Override
            public void onResponse(Response<MessageGson> response) {
                dispatchSuccess(response.body(), true);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void dispatchSuccess(MessageGson headJson, boolean isClear) {

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        showErrorView(View.GONE);

        if (headJson.getCode() == 500) {
            Log.e(TAG, "STATUS_ERR , use LogLevel.FULL for more!");
            return;
        }
        if (headJson.getCode() == 200) {
            if (newsBeans.isEmpty()) {
                Log.d(TAG, "newsBeans.isEmpty()");
                newsBeans.addAll(headJson.getRecMsg());
                adapter.notifyDataSetChanged();
                //cacheToDb(newsBeans);
                return;
            }
//            if (newsBeans.get(0).getMessagePid() == headJson.getRecMsg().get(0).getMessagePid()) {
//                Log.d(TAG, "Same data, Ignore cacheToDb");
//                return;
//            }
            if (isClear) {
                newsBeans.clear();
            }
            newsBeans.addAll(headJson.getRecMsg());
            adapter.notifyDataSetChanged();
        }
    }

    private void dispatchError() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        newsBeans.clear();
        adapter.notifyDataSetChanged();
        isLoadingMore = false;
        showErrorView(View.VISIBLE);
    }

    void loadDbFeeds() {
        //List<Feed> feeds;
        //feeds = new Select().from(Feed.class)
        //    .where("category = ?", Integer.valueOf(this.feed_category))
        //    .orderBy("idMember desc")
        //    .limit(this.feed_limit)
        //    .execute();
        //if (feeds.isEmpty()) {
        //  //TODO
        //  loadNewFeeds();
        //} else {
        //  newsBeans.addAll(feeds);
        //  adapter.notifyDataSetChanged();
        //  mSwipeRefreshLayout.setRefreshing(false);
        //}
        loadNewFeeds();
    }

    void showErrorView(int isVisible) {
        if (mBtnloading_refresh != null && mTvloading_tips != null) {
            mBtnloading_refresh.setVisibility(isVisible);
            mTvloading_tips.setVisibility(isVisible);
        }
    }

    @Subscribe
    public void onNightmode(NightModeEvent event) {
        Log.d(TAG, "isNightMode" + event.isNightMode);
        //if (mRecyclerview == null) {
        //  Log.d(TAG, "onNightmode mRecyclerview is null!");
        //}
        //mFloatingActionButton.setColorNormalResId(ThemePref.getBackgroundResColor(event.isNightMode));
        //mFloatingActionButton.setColorPressedResId(
        //    ThemePref.getItemBackgroundResColor(event.isNightMode));
        onDetach();
        onAttach(getActivity());
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        GlobalContext.getBus().register(this);
    }

    @Override public void onDetach() {
        super.onDetach();
        GlobalContext.getBus().unregister(this);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        LOGD(TAG, "onDestroyView");
        ButterKnife.reset(this);
    }
}

