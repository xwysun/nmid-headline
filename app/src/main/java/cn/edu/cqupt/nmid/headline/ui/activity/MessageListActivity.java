package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.message.InitMessageFragment;

public class MessageListActivity extends BaseFragmentActivity {


    @Override
    public Fragment getFragment() {
        return new InitMessageFragment();
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        SharedPreferences sharedPreferences=getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String Mode=sharedPreferences.getString("MODE","NONE");
        if (Mode.equals("student"))
        {
            menu.getItem(0).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message_send:
                Intent intent =new Intent(getApplicationContext(),NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.message_setting:
                Intent intent1=new Intent(getApplicationContext(),UserInfoActivity.class);
                startActivity(intent1);
                break;
        }
            return super.onOptionsItemSelected(item);
        }

}
