package com.quinn.hunter.timing;

import android.app.Application;
import android.util.Log;

import com.hunter.library.point.IPointHandler;
import com.hunter.library.point.PointManager;
import com.hunter.library.timing.BlockManager;
import com.hunter.library.timing.IBlockHandler;
import com.hunter.library.timing.impl.StacktraceBlockHandler;

/**
 * Created by quinn on 14/09/2018
 */
public class App extends Application {

    private IBlockHandler customBlockManager = new StacktraceBlockHandler(50);
    private IPointHandler customPointManager = new IPointHandler() {
        @Override
        public void pointMethod(String s) {
            Log.e("sdfkjsdlkfdsf", "method:" + s);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        BlockManager.installBlockManager(customBlockManager);
        PointManager.installPointManager(customPointManager);
    }

    public IBlockHandler getCustomBlockManager(){
        return customBlockManager;
    }

}
