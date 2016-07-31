package background.mblz.yandex.com.yandexlesson.provider;

import android.support.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import background.mblz.yandex.com.yandexlesson.model.Genre;
import background.mblz.yandex.com.yandexlesson.model.SingerInfo;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by Andrey on 28.07.2016.
 */
public class GenreProvider {
    @NonNull ISingerInfoProvider singerInfoProvider;
    public GenreProvider(@NonNull ISingerInfoProvider singerInfoProvider) {
        this.singerInfoProvider = singerInfoProvider;
    }

    public void getGenres(@NonNull final Callback<Genre[]> callback) {

        singerInfoProvider.getAllSingers(new Callback<SingerInfo[]>() {
            @Override
            public void success(SingerInfo[] singerInfos, Response response) {
                Genre[] genres = null;
                HashMap<String, List<URL>> urls = new HashMap<String, List<URL>>();
                for (SingerInfo si : singerInfos) {
                    for (String genreName : si.getGenres()) {
                        if (!urls.containsKey(genreName)) {
                            urls.put(genreName, new ArrayList<URL>());
                        }
                        try {
                            urls.get(genreName).add(new URL(si.getSmall()));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                genres = new Genre[urls.size()];
                int index = 0;
                for (String genre : urls.keySet()) {
                    URL[] array = new URL[urls.get(genre).size()];
                    int urlIndex = 0;
                    for (URL url : urls.get(genre)) {
                        array[urlIndex] = url;
                        ++urlIndex;
                    }
                    genres[index] = new Genre(genre, array);
                    ++index;
                }
                callback.success(genres, new Response("", 200, "o", new ArrayList<Header>(), null));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(null);
            }
        });
    }
}
