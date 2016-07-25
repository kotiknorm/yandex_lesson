package background.mblz.yandex.com.yandexlesson.view;

import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import background.mblz.yandex.com.yandexlesson.R;
import background.mblz.yandex.com.yandexlesson.loader.CollageLoaderManager;
import background.mblz.yandex.com.yandexlesson.loader.CollageStrategy;
import background.mblz.yandex.com.yandexlesson.loader.ExampleCollageStrategy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView collageHolder = (ImageView) findViewById(R.id.collageHolder);
        CollageStrategy collageStrategy = new ExampleCollageStrategy(600, 400);
        CollageLoaderManager.getLoader().loadCollage(generateUrls(), collageHolder, collageStrategy);
        collageHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollageStrategy collageStrategy = new ExampleCollageStrategy(600, 400);
                CollageLoaderManager.getLoader().loadCollage(generateUrls(), (ImageView) v, collageStrategy);
            }
        });
    }

    private List<String> generateUrls(){
        Random random = new Random();
        int i = random.nextInt(30) + 5;
        List<String> result = new ArrayList<>(i);
        for (int j = 0; j < i; j++) {
            result.add(createUrl(random.nextInt(400) + 200, random.nextInt(200) + 200, random.nextInt()));
        }
        return result;
    }

    //Так было проще=)
    private String createUrl(int w, int h, int color){
        return "http://placehold.it/" + Integer.toString(w) + "x" + Integer.toString(h)
                + "/" +  String.format("%06X", (0xFFFFFF & color))
                + "/000000"
                + "?txt=" + Integer.toString(w) + "x" + Integer.toString(h);

    }
}
