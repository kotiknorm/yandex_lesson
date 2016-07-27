package background.mblz.yandex.com.yandexlesson;

import android.app.Application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import background.mblz.yandex.com.yandexlesson.handler.CriticalSectionsManager;
import background.mblz.yandex.com.yandexlesson.loader.CollageLoaderManager;
import background.mblz.yandex.com.yandexlesson.model.Genre;
import background.mblz.yandex.com.yandexlesson.model.SingerInfo;
import background.mblz.yandex.com.yandexlesson.provider.ISingerInfoProvider;
import background.mblz.yandex.com.yandexlesson.provider.ProviderCreator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class YandexApplication extends Application {

    public Genre[] getGenres() {
        final Genre[][] genres = {null};
        singerInfoProvider.getAllSingers(new Callback<SingerInfo[]>() {
            @Override
            public void success(SingerInfo[] singerInfos, Response response) {
                HashMap<String, List<URL>> urls = new HashMap<String, List<URL>>();
                for (SingerInfo si : singerInfos) {
                    for (String genreName : si.getGenres()) {
                        if (urls.containsKey(genreName)) {
                            try {
                                urls.get(genreName).add(new URL(si.getSmall()));
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                genres[0] = new Genre[urls.size()];
                int index = 0;
                for (String genre : urls.keySet()) {
                    URL[] array = new URL[urls.get(genre).size()];
                    int urlIndex = 0;
                    for (URL url : urls.get(genre)) {
                        array[urlIndex] = url;
                        ++urlIndex;
                    }
                    genres[0][index] = new Genre(genre, array);
                    ++index;
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return genres[0];
    }

    public ISingerInfoProvider singerInfoProvider = ProviderCreator.createSingerInfoProvider();

    @Override
    public void onCreate() {
        super.onCreate();

        Genre[] genres = getGenres();

        CriticalSectionsManager.init(null); // add implementation
        CollageLoaderManager.init(null); // add implementation
    }
}
