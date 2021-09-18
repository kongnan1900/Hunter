package com.hunter.library.point;

import android.util.Log;

/**
 * Created by quinn on 11/09/2018
 */
public interface IPointHandler {

    IPointHandler DEFAULT = new IPointHandler() {

        private static final String TAG = "Default-IPointHandler";

        @Override
        public void pointMethod(String method) {
//            Log.i(TAG, "method: " + method);
        }
    };

    void pointMethod(String method);
}
