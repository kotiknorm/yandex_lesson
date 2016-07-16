package background.mblz.yandex.com.yandexlesson;

import android.app.Application;

import background.mblz.yandex.com.yandexlesson.handler.CriticalSectionsManager;
import background.mblz.yandex.com.yandexlesson.loader.CollageLoaderManager;

public class YandexApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CriticalSectionsManager.init(null); // add implementation
        CollageLoaderManager.init(null); // add implementation
    }
}
