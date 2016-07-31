package background.mblz.yandex.com.yandexlesson.loader;

import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

public class StubCollageLoader implements CollageLoader {

    @Override
    public void loadCollage(List<String> urls, ImageView imageView) {

    }

    @Override
    public void loadCollage(List<String> urls, ImageTarget imageTarget) {

    }

    @Override
    public void loadCollage(List<URL> urls, WeakReference<ImageView> imageView,
                            CollageStrategy collageStrategy) {

    }

    @Override
    public void loadCollage(List<String> urls, ImageTarget imageTarget,
                            CollageStrategy collageStrategy) {

    }
}
