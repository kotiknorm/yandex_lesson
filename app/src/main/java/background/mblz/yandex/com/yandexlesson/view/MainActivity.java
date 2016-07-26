package background.mblz.yandex.com.yandexlesson.view;

import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    RecyclerView rvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvData = (RecyclerView) findViewById(R.id.rv);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setHasFixedSize(true);
        rvData.setAdapter(new CollageAdapter());

    }


}
