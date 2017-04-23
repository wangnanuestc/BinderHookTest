package example.wangnan.com.binderhook.hook;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import example.wangnan.com.binderhook.constant.Constants;

/**
 * Created by wangnan on 2017/4/16.
 */

public class Hooker {

    static Object sCache;

    public static void doHook() {

        try {
            //制造一个假的
            Class serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManagerClass.getDeclaredMethod("getService", String.class);
            IBinder rawBinder = (IBinder)getServiceMethod.invoke(null, Context.WIFI_SERVICE);

            IBinder fakeIBinder = (IBinder) Proxy.newProxyInstance(serviceManagerClass.getClassLoader(),
                    new Class<?>[] { IBinder.class },
                    new BinderProxyHookHandler(rawBinder));

            //替代掉
//            sCache = FieldUtils.readField(sServiceManagerClass, "sCache");
            Field cacheField = serviceManagerClass.getDeclaredField("sCache");
            cacheField.setAccessible(true);
            sCache = (Map) cacheField.get(null);

            if (sCache instanceof Map) {
                ((Map) sCache).put(Context.WIFI_SERVICE, fakeIBinder);

                Log.d(Constants.TAG, "insert the fake binder success");
            } else {
                Log.d(Constants.TAG, "The sCache is not a map instance");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
