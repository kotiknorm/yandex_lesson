package background.mblz.yandex.com.yandexlesson.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import background.mblz.yandex.com.yandexlesson.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        ContentFragment contentFragment = (ContentFragment) supportFragmentManager.findFragmentById(R.id.content_layout);
        if (contentFragment == null) {
            contentFragment = new ContentFragment();
            supportFragmentManager.beginTransaction().replace(R.id.content_layout, contentFragment).commit();
        }
    }
}
