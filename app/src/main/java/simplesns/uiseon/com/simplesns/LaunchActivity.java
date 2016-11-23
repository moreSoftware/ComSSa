package simplesns.uiseon.com.simplesns;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;

import simplesns.uiseon.com.simplesns.manager.AutoLayout;

public class LaunchActivity extends Activity {
    private SharedPreferences pref;
    private AutoLayout autoLayout = AutoLayout.GetInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        int disWidth = this.getResources().getDisplayMetrics().widthPixels;
        int disHeight = this.getResources().getDisplayMetrics().heightPixels;

        autoLayout.init(this, disWidth, disHeight);

        View view = (View)findViewById(android.R.id.content);
        autoLayout.setView(view);

        pref = this.getSharedPreferences("simpleSns", Activity.MODE_PRIVATE);


        Thread t = new Thread() {
            public void run() {
                SystemClock.sleep(1000);
                mhandler.sendEmptyMessage(0);
            }
        };
        t.start();
    }

    Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            finish();
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);


        }
    };

}
