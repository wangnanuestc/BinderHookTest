package example.wangnan.com.binderhook.hook;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import example.wangnan.com.binderhook.constant.Constants;

/**外面传来一个IBinder对象，我们需要先获取其binder代理对象，然后对相应方法进行hook
 * 对android.net.wifi.IWiFiManager的setWifiEnabled静态方法进行hook
 * Created by wangnan on 2017/4/16.
 */

public class WifiManagerHookHandler implements InvocationHandler {

    private static final String TAG = "WifiManagerHookHandler";


    Object mIWifiManager;   //Binder的代理对象，asInterface返回的结果对象，存起来

    public WifiManagerHookHandler(IBinder base) {
        try {

            Class stubClass = Class.forName("android.net.wifi.IWifiManager$Stub");   //先获取class对象
            Method queryLocalInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            queryLocalInterfaceMethod.setAccessible(true);
            mIWifiManager = queryLocalInterfaceMethod.invoke(null, base);   //Stub类的静态方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("setWifiEnabled".equals(method.getName())) {
            Log.d(TAG, "always return true");
            return true;
        }

        Log.d(Constants.TAG, "WifiManagerHookHandler: method name is " + method.getName());

        return method.invoke(mIWifiManager, args);  //使用client端对象进行执行
    }
}
