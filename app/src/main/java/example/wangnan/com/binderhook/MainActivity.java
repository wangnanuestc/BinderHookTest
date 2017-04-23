package example.wangnan.com.binderhook;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import example.wangnan.com.binderhook.constant.Constants;
import example.wangnan.com.binderhook.hook.Hooker;

public class MainActivity extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(Constants.TAG, "MainActivity: onCreate");

        Hooker.doHook();

        //这里需要注意一下，先简单获取一下系统服务，保证在本进程内有一个IWifiManager对象，否则可能遇到崩溃
        //原因是在通过IWifiManager获生成WifiManager对象的过程中，除了
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = wifiManager.setWifiEnabled(true);
                Log.d(Constants.TAG, "result = " + result);
                List<ScanResult> scanResults = wifiManager.getScanResults();
                for (ScanResult scanResult : scanResults) {
                    Log.d(Constants.TAG, "ScanResult = " + scanResult.toString());
                }
            }
        });

    }



}
