package background.mblz.yandex.com.yandexlesson;

import android.app.Application;

import background.mblz.yandex.com.yandexlesson.handler.CriticalSectionsManager;
import background.mblz.yandex.com.yandexlesson.loader.CollageLoaderManager;
import background.mblz.yandex.com.yandexlesson.loader.SimpleCollageLoader;
import background.mblz.yandex.com.yandexlesson.provider.GenreProvider;
import background.mblz.yandex.com.yandexlesson.provider.ISingerInfoProvider;
import background.mblz.yandex.com.yandexlesson.provider.ProviderCreator;

public class YandexApplication extends Application {

    private ISingerInfoProvider singerInfoProvider = ProviderCreator.createSingerInfoProvider();
    private GenreProvider genreProvider = new GenreProvider(singerInfoProvider);

    public GenreProvider getGenreProvider() {
        return genreProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CriticalSectionsManager.init(null); // add implementation
        CollageLoaderManager.init(new SimpleCollageLoader()); // add implementation
    }
}
