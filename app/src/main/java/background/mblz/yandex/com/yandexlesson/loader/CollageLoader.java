package background.mblz.yandex.com.yandexlesson.loader;

import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

public interface CollageLoader {

    void loadCollage(List<String> urls, ImageView imageView);

    void loadCollage(List<String> urls, ImageTarget imageTarget);

    void loadCollage(List<URL> urls, WeakReference<ImageView> imageView, CollageStrategy collageStrategy);

    void loadCollage(List<String> urls, ImageTarget imageTarget, CollageStrategy collageStrategy);

}
