package background.mblz.yandex.com.yandexlesson.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import background.mblz.yandex.com.yandexlesson.R;
import background.mblz.yandex.com.yandexlesson.loader.CollageLoader;
import background.mblz.yandex.com.yandexlesson.loader.CollageLoaderManager;
import background.mblz.yandex.com.yandexlesson.loader.ShuffleStrategy;
import background.mblz.yandex.com.yandexlesson.model.Genre;

/**
 * Created by Andrey on 28.07.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Genre[] genres;

    public RecyclerViewAdapter(Genre[] genres) {
        this.genres = genres;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Genre genre = genres[i];
        viewHolder.name.setText(genre.getName());
        viewHolder.collage.get().setImageBitmap(null);
        CollageLoader loader = CollageLoaderManager.getLoader();
        loader.loadCollage(Arrays.asList(genre.getLinks()), viewHolder.collage, new ShuffleStrategy());
    }

    @Override
    public int getItemCount() {
        return genres.length;
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private WeakReference<ImageView> collage;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.genreName);
            collage = new WeakReference<ImageView> ((ImageView) itemView.findViewById(R.id.collage));
        }
    }
}