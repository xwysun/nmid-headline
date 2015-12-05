package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.event.NightModeEvent;
import cn.edu.cqupt.nmid.headline.support.pref.KEYS;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.MessageGson;
import cn.edu.cqupt.nmid.headline.ui.fragment.base.FavFeedFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.ImagesFeedFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.NavigationDrawerFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.SlidingTabFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.LollipopUtils;
import cn.edu.cqupt.nmid.headline.utils.PreferenceUtils;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import cn.sharesdk.framework.statistics.a;
import retrofit.Callback;
import retrofit.Response;

import com.squareup.otto.Subscribe;

/**
 *
 */
public class HomeActivity extends AppCompatActivity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {

  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @InjectView(R.id.toolbar_holder) RelativeLayout mToolbarHolder;

  @InjectView(R.id.home_drawer_layout) DrawerLayout mDrawerLayout;
  @InjectView(R.id.home_content_layout) LinearLayout mLinearLayout;

  NavigationDrawerFragment mNavigationDrawerFragment;
  private String TAG = LogUtils.makeLogTag(HomeActivity.class);
  private String Mode;
  private String classNo;
  private String password;
  private String account;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.inject(this);
    LollipopUtils.setStatusbarColor(this, mToolbarHolder);
    trySetupToolbar();
    trySetupNavigationDrawer();
    mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    mToolbarHolder.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
  }
  public boolean hasUserInfoCache()
  {
    SharedPreferences sharedPreferences=getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
    Mode=sharedPreferences.getString("MODE", "NONE");
    password=sharedPreferences.getString("password", "NONE");
    account=sharedPreferences.getString("account","NONE");
    classNo=sharedPreferences.getString("classNo","NONE");
    Log.d("share",Mode+account+password+classNo);
    if (Mode.equals("student")&&!classNo.equals("NONE")&&!classNo.equals("NONE"))
    {
      return true;
    }else if (Mode.equals("teacher")&&!account.equals("NONE")&&!password.equals("NONE"))
    {
      return true;
    }else {
      return false;
    }
  }

  public void trySetupToolbar() {

    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void trySetupNavigationDrawer() {

    mNavigationDrawerFragment =
        (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
            R.id.navigation_drawer);

    mNavigationDrawerFragment.setUp(mDrawerLayout, mToolbar);
  }

  @Override public void onNavigationDrawerItemSelected(int position) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment = null;
    switch (position) {
      case 0:
        fragment = new SlidingTabFragment();
        break;
      case 1:
        fragment = new ImagesFeedFragment();
        break;
      case 2:
        fragment = new FavFeedFragment();
        break;
    }

    if (fragment != null) {
      fragmentManager.beginTransaction().replace(R.id.base_fragment_container, fragment).commit();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
//    if (ThemePref.isNightMode(this)) {
//      menu.getItem(1).setTitle(R.string.settings_night_mode_day);
//    } else {
//      menu.getItem(1).setTitle(R.string.settings_night_mode_night);
//    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
//      case R.id.action_night_mode:
//        boolean isNightmode = PreferenceUtils.getPrefBoolean(this, KEYS.NIGHTMODE, false);
//        PreferenceUtils.setPrefBoolean(this, KEYS.NIGHTMODE, !isNightmode);
//        //GlobalContext.getBus().post(new NightModeEvent(isNightmode));
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//        finish();
//        break;
//      case R.id.action_settings:
//        startActivity(new Intent(this, SettingsActivity.class));
//        break;
      case R.id.message:

        if(hasUserInfoCache())
        {
          startActivity(new Intent(this, MessageListActivity.class));
        }else {
          startActivity(new Intent(this, UserInfoActivity.class));
        }
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.reset(this);
  }

  @Subscribe public void onNightmode(NightModeEvent currentNightMode) {

    mToolbar.setBackgroundResource(
        ThemePref.getToolbarBackgroundResColor(currentNightMode.isNightMode));
    mToolbarHolder.setBackgroundResource(
        ThemePref.getToolbarBackgroundResColor(currentNightMode.isNightMode));
  }
}
