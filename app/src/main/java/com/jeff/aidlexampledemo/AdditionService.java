package com.jeff.aidlexampledemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by 小太阳jeff on 2017/6/6.
 */

public class AdditionService extends Service {

    private static final String TAG = "AdditionService";
    private AddBinder mBinder;

    public class AddBinder extends IAddInterface.Stub {

        @Override
        public int add(int x, int y) throws RemoteException {
            return x+y;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder=new AddBinder();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: remote service destroy");
        super.onDestroy();
    }
}
