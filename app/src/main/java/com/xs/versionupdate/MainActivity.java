package com.xs.versionupdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xs.apkupdate.ApkUpdateParamSet;
import com.xs.apkupdate.UpdateService;
import com.xs.versionupdate.updateUtil.UpdateDialog;


public class MainActivity extends AppCompatActivity {

    private TextView mTvValue ;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (UpdateService.BROADCAST_UPDATE_PROCESS_ERROR.equals(action)) {
                mTvValue.setText("error");
            } else if (UpdateService.BROADCAST_UPDATE_PROCESS_SUCCESS.equals(action)) {
                final int process = intent.getIntExtra(UpdateService.BROADCAST_UPDATE_PROCESS_VALUE,0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvValue.setText(""+process);
                    }
                });
            } else if (UpdateService.BROADCAST_UPDATE_PROCESS_FINISH.equals(action)) {
                mTvValue.setText("下载完成");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvValue = (TextView) findViewById(R.id.tv_value);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UpdateService.BROADCAST_UPDATE_PROCESS_SUCCESS);
        intentFilter.addAction(UpdateService.BROADCAST_UPDATE_PROCESS_ERROR);
        intentFilter.addAction(UpdateService.BROADCAST_UPDATE_PROCESS_FINISH);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void update(View view) {
        UpdateDialog.getDialog(new UpdateDialog.OnClickUpdate() {
            @Override
            public void sure() {
                ApkUpdateParamSet apkUpdateParamSet = new ApkUpdateParamSet();
                apkUpdateParamSet.setApkUrl("http://kmzyf.km1818.com/file/photos/download/1487386399961.apk");
                apkUpdateParamSet.setLocalPath(Environment.getExternalStorageDirectory()+"/"+getString(R.string.app_name)+".apk");
                apkUpdateParamSet.setNotifyEndMessage("下载ok");
                apkUpdateParamSet.setNotifyIngMessage("下载中啦啦啦啦");
                apkUpdateParamSet.setNotifyTitle("apk更新title");
//                apkUpdateParamSet.setEnableBroadcast(true);
                apkUpdateParamSet.setEnableNotifycation(true);
                Intent intent = new Intent(MainActivity.this, UpdateService.class);
                intent.putExtra(ApkUpdateParamSet.NAME,apkUpdateParamSet);
                startService(intent);
            }
        }).show(getSupportFragmentManager(),MainActivity.class.getName());
    }

}
