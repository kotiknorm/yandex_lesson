package background.mblz.yandex.com.yandexlesson.loader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Random;


/**
 * Created by Andrey on 28.07.2016.
 */
public class ShuffleStrategy implements CollageStrategy {
    @Override
    public Bitmap create(final List<Bitmap> bitmaps) {

        final int count = ((int) Math.sqrt((double) bitmaps.size()) + 1);
        final int width = bitmaps.get(0).getWidth();
        final int height = bitmaps.get(0).getHeight();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmaps.get(0), count * width, count * height, false);
        Bitmap copy = scaledBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(copy);
        Random random = new Random();
        HashSet<Pair<Integer, Integer>> existCoords = new HashSet<>();
        for (Bitmap bitmap : bitmaps) {
            Pair<Integer, Integer> coords = GetCoords(existCoords, random, count);
            canvas.drawBitmap(bitmap, width * coords.first, height * coords.second, null);
        }

        return Bitmap.createScaledBitmap(copy, 400, 400, false);

    }

    private Pair<Integer, Integer> GetCoords(HashSet<Pair<Integer, Integer>> existCoords, Random random, int count) {
        Pair<Integer, Integer> pair = new Pair<>(random.nextInt(count), random.nextInt(count));
        while (existCoords.contains(pair)) {
            pair = new Pair<>(random.nextInt(count), random.nextInt(count));
        }
        existCoords.add(pair);
        return pair;
    }
}
