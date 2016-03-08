package cn.edu.cqupt.nmid.headline.ui.activity;

import android.support.v4.app.Fragment;

import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.WebUtilFragment;

/**
 * Created by xwysun on 2016/1/3.
 */
public class WebUtilActivity extends BaseFragmentActivity {
    @Override
    public Fragment getFragment() {
        return new WebUtilFragment();
    }
}
