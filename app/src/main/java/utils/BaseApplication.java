package utils;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.ycgis.pclient.CrashHandler;

/**
 * Created by lightkin on 17-6-29.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        utils.OfflineMapCopy.copyFilesByassets(this, "files", this.getBaseContext().getExternalFilesDir("/").getPath());
        //SDKInitializer.initialize(this);
        SDKInitializer.initialize(getApplicationContext());
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        mInstance = this;
    }

    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        if (mInstance == null) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }


}
