package background.mblz.yandex.com.yandexlesson.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 25.07.2016.
 */

public class ExampleCollageLoader implements CollageLoader {
    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public void loadCollage(List<String> urls, ImageView imageView) {
        loadCollage(urls, new ImageViewImageTarget(imageView));
    }

    @Override
    public void loadCollage(List<String> urls, ImageTarget imageTarget) {
        loadCollage(urls, imageTarget, null);
    }

    @Override
    public void loadCollage(List<String> urls, ImageView imageView, CollageStrategy collageStrategy) {
        loadCollage(urls, new ImageViewImageTarget(imageView), collageStrategy);
    }

    @Override
    public void loadCollage(List<String> urls, final ImageTarget imageTarget, CollageStrategy collageStrategy) {
        if (collageStrategy == null) collageStrategy = new ExampleCollageStrategy(600, 400);
        Observable.just(urls)
                .flatMap(loadBitmaps())
                .map(createCollage(collageStrategy))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(processResult(imageTarget));
    }

    @NonNull
    private Subscriber<Bitmap> processResult(final ImageTarget imageTarget) {
        return new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                imageTarget.onLoadBitmap(bitmap);
            }
        };
    }

    @NonNull
    private Func1<List<Bitmap>, Bitmap> createCollage(final CollageStrategy finalCollageStrategy) {
        return new Func1<List<Bitmap>, Bitmap>() {
            @Override
            public Bitmap call(List<Bitmap> bitmaps) {
                return finalCollageStrategy.create(bitmaps);
            }
        };
    }

    @NonNull
    private Func1<List<String>, Observable<List<Bitmap>>> loadBitmaps() {
        return new Func1<List<String>, Observable<List<Bitmap>>>() {
            @Override
            public Observable<List<Bitmap>> call(List<String> strings) {
                return Observable.from(strings)
                        .flatMap(new Func1<String, Observable<Bitmap>>() {
                            @Override
                            public Observable<Bitmap> call(String s) {
                                return loadOneBitmap(s);
                            }
                        })
                        .reduce(new ArrayList<Bitmap>(strings.size()), new Func2<List<Bitmap>, Bitmap, List<Bitmap>>() {
                            @Override
                            public List<Bitmap> call(List<Bitmap> bitmaps, Bitmap bitmap) {
                                bitmaps.add(bitmap);
                                return bitmaps;
                            }
                        });
            }
        };
    }

    //разкоментировать чтобы увидеть интересные логи
    //@RxLogObservable
    private Observable<Bitmap> loadOneBitmap(String bitmapUrl) {
        return Observable.just(bitmapUrl)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        try {
                            return BitmapFactory.decodeStream(okHttpClient
                                    .newCall(new Request.Builder().url(s).build()).execute().body().byteStream());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }



    private static class ImageViewImageTarget implements ImageTarget{
        private final WeakReference<ImageView> wrImageView;

        private ImageViewImageTarget(ImageView wrImageView) {
            this.wrImageView = new WeakReference<>(wrImageView);
        }

        @Override
        public void onLoadBitmap(Bitmap bitmap) {
            ImageView imageView = wrImageView.get();
            if (imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
