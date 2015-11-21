package cn.edu.cqupt.nmid.headline.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.message.CreateMessageFragment;

public class NoticeActivity extends BaseFragmentActivity {
    @Override
    public Fragment getFragment() {
        return CreateMessageFragment.newInstance(getIntent().getExtras());
    }
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
