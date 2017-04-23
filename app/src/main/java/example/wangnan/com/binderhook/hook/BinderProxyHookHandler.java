package example.wangnan.com.binderhook.hook;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import example.wangnan.com.binderhook.constant.Constants;

/**本类通过Hook queryLocalInterface用来造一个虚假的IBinder，asInterface返回
 * Created by wangnan on 2017/4/22.
 */

public class BinderProxyHookHandler implements InvocationHandler {

    IBinder mBase;

    Class mIWifiManagerClass;

    BinderProxyHookHandler(IBinder base) {
        try {
            mBase = base;
            //将要hook的Binder的本地代理类型
            mIWifiManagerClass = Class.forName("android.net.wifi.IWifiManager");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Log.d(Constants.TAG, "BinderProxyHookHandler: method name = " + method.getName());

        if (method.getName().equals("queryLocalInterface")) {
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[] {this.mIWifiManagerClass},
                    new WifiManagerHookHandler(mBase));
        }

        return method.invoke(mBase, args);
    }
}
