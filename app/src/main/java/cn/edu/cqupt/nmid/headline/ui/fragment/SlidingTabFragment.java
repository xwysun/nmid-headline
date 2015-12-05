package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.event.NightModeEvent;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.TeacherList;
import cn.edu.cqupt.nmid.headline.ui.fragment.base.NewsFeedFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.base.TeacherListFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import com.astuetz.PagerSlidingTabStrip;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

/**
 * Created by leon on 1/16/15.
 */
public class SlidingTabFragment extends Fragment {

  static final String TAG = LogUtils.makeLogTag(SlidingTabFragment.class);

  @InjectView(R.id.slidingtab) PagerSlidingTabStrip mTabLayout;

  @InjectView(R.id.viewpager) ViewPager mViewPager;
  @InjectView(R.id.holder) LinearLayout mHolder;

  ArrayList<Fragment> fragments = new ArrayList<>();
  PagerAdapter mPagerAdapter;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.d(TAG, "onViewCreated");
    fragments.add(NewsFeedFragment.newInstance("学院经纬", HeadlineService.TYPE_JINGWEI));
    fragments.add(NewsFeedFragment.newInstance("新闻快讯", HeadlineService.TYPE_NEWS));
    fragments.add(TeacherListFragment.newInstance("教师名录"));
//    fragments.add(NewsFeedFragment.newInstance("通信校友", HeadlineService.CATE_ALUMNUS));
    Log.d(TAG, "setViewPager");
    mTabLayout.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(getActivity()));

    mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), fragments);
    //mViewPager.setOffscreenPageLimit(fragments.size());
    mViewPager.setAdapter(mPagerAdapter);

    mTabLayout.setViewPager(mViewPager);
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
    Log.d(TAG, "onDestroyView");
    ButterKnife.reset(this);
  }

  @Subscribe public void onNightmode(NightModeEvent event) {
    mTabLayout.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(event.isNightMode));
  }

  public static class PagerAdapter extends FragmentStatePagerAdapter {

    private String TAG = LogUtils.makeLogTag(PagerAdapter.class);

    private ArrayList<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
      super(fm);
      this.fragments = fragments;
    }

    @Override public Fragment getItem(int pos) {
      return fragments.get(pos);
    }

    @Override public int getCount() {
      return fragments.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return fragments.get(position).getArguments().getString("title");
    }


  }
}
