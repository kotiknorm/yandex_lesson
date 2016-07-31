package background.mblz.yandex.com.yandexlesson.model;

import android.support.annotation.NonNull;

import java.net.URL;

/**
 * Created by Andrey on 27.07.2016.
 */
public class Genre {
    private final String name;
    private final URL[] links;

    public Genre(@NonNull String name, @NonNull URL[] links) {
        this.name = name;
        this.links = links;
    }

    public String getName() { return name; }
    public URL[] getLinks() { return links; }
}
