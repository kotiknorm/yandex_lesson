package background.mblz.yandex.com.yandexlesson.loader;

import android.graphics.Bitmap;

import java.util.List;

public interface CollageStrategy {

    Bitmap create(List<Bitmap> bitmaps);
}
