package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.FreshNews;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.GetInfo;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.WebViewFragment;

/**
 * Useful @Link:http://developer.android.com/training/animation/crossfade.html
 */
public class DetailedActivity extends BaseFragmentActivity {
  private GetInfo getInfo;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public Fragment getFragment() {
    return WebViewFragment.newInstance(getIntent().getExtras());
  }
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_teacher, menu);
    if (getIntent().getStringExtra("mode").equals(WebViewFragment.MODE_TEACHER)) {
     getInfo=getIntent().getParcelableExtra(WebViewFragment.PARCELABLE_KEY);
    }else
    {
      menu.getItem(0).setVisible(false);
    }
    return super.onCreateOptionsMenu(menu);
  }
  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.openBrower:
        Uri uri=Uri.parse(getInfo.getHomePage());
        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  public static void startActivity(Context context, FreshNews feed) {
    Intent intent = new Intent(context, DetailedActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(WebViewFragment.PARCELABLE_KEY, feed);
    context.startActivity(intent);
  }
}
