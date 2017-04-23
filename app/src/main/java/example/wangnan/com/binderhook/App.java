package example.wangnan.com.binderhook;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import example.wangnan.com.binderhook.constant.Constants;

/**
 * Created by wangnan on 2017/4/16.
 */

public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(Constants.TAG, "App: attachBaseContext");
    }
}
