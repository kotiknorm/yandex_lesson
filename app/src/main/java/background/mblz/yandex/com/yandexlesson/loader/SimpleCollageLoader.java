package background.mblz.yandex.com.yandexlesson.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import background.mblz.yandex.com.yandexlesson.handler.CriticalSectionsManager;
import background.mblz.yandex.com.yandexlesson.handler.Task;


/**
 * Created by Andrey on 28.07.2016.
 */
public class SimpleCollageLoader implements CollageLoader {

    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private ExecutorService managerService = Executors.newFixedThreadPool(4);
    private final ConcurrentHashMap<WeakReference<ImageView>, AsyncTask<URL, Void, Bitmap> > proccessingTasks = new ConcurrentHashMap<>();

    @Override
    public void loadCollage(List<String> urls, ImageView imageView) {

    }

    @Override
    public void loadCollage(List<String> urls, ImageTarget imageTarget) {

    }



    @Override
    public void loadCollage(final List<URL> urls, final WeakReference<ImageView> imageView, final CollageStrategy collageStrategy) {
        AsyncTask<URL, Void, Bitmap> task = new AsyncTask<URL, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(URL... params) {

                final ConcurrentHashMap<URL, Bitmap> store = new ConcurrentHashMap<>();
                ArrayList<Future> results = new ArrayList<>();
                for (final URL url : params) {
                    if (this.isCancelled()) return null;
                    results.add(executorService.submit(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap bitmap = getBitmapFromURL(url);
                                    store.put(url, bitmap);
                                }
                            }
                    ));
                }
                if (this.isCancelled()) return null;
                for (Future f : results) {
                    try {
                        f.get();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                        return null;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                final ArrayList<Bitmap> bitmaps = new ArrayList<>(store.values());
                final Bitmap bitmap = collageStrategy.create(bitmaps);
                return bitmap;


            }

            @Override
            protected void onPostExecute(final Bitmap bitmap) {
                CriticalSectionsManager.getHandler().postLowPriorityTask(new Task() {
                    @Override
                    public void run() {
                        try {
                            if (!isCancelled()) {
                                imageView.get().setImageBitmap(bitmap);
                            }
                        }
                        catch (NullPointerException e) {

                        }
                    }
                });

            }
        };


        synchronized (imageView) {
            URL[] urlsArray = urls.toArray(new URL[0]);
            AsyncTask<URL, Void, Bitmap> lastRunningTask = proccessingTasks.get(imageView);
            proccessingTasks.put(imageView, task);
            Log.i("MINE_MESSAGE", "Task count " + proccessingTasks.size());
            if (lastRunningTask != null) {
                Log.i("MINE_MESSAGE", "canceled: " + String.valueOf(lastRunningTask.cancel(true)) + " stopped imageView: " + imageView.hashCode());
            }
            task.execute(urlsArray);
        }
    }
    public static Bitmap getBitmapFromURL(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void loadCollage(List<String> urls, ImageTarget imageTarget, CollageStrategy collageStrategy) {

    }
}
