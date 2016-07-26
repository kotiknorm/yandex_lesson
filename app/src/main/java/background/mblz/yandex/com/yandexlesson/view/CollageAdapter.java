package background.mblz.yandex.com.yandexlesson.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import background.mblz.yandex.com.yandexlesson.R;
import background.mblz.yandex.com.yandexlesson.loader.CollageLoaderManager;
import background.mblz.yandex.com.yandexlesson.loader.ExampleCollageLoader;

/**
 * Created by Александр on 26.07.2016.
 */

public class CollageAdapter extends RecyclerView.Adapter<CollageAdapter.Item> {

    private List<List<String>> data = Collections.emptyList();
    private ClickListener clickListener = new ClickListener();
    Random random = new Random();

    public CollageAdapter() {
        super();
        data = generateData();
    }

    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        holder.onBind(data.get(position));
    }

    public void replaceData(List<List<String>> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private List<List<String>> generateData(){
        int i = random.nextInt(50) + 10;
        List<List<String>> newData = new ArrayList<>(i);
        for (int j = 0; j < i; j++)
            newData.add(generateUrls());
        return newData;
    }

    private List<String> generateUrls(){
        int i = random.nextInt(4) + 2;
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

    protected class Item extends RecyclerView.ViewHolder{

        private CardView cvHolder;
        private TextView tvForLinks;
        private ImageView ivForCollage;
        private ExampleCollageLoader.ImageViewImageTarget ivit;

        public Item(View itemView) {
            super(itemView);
            cvHolder = (CardView) itemView.findViewById(R.id.cv_holder);
            tvForLinks = (TextView) itemView.findViewById(R.id.textHolder);
            ivForCollage = (ImageView) itemView.findViewById(R.id.collageHolder);
        }

        public void onBind(List<String> data){
            if(ivit != null) ivit.cancel();
            cvHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick();
                }
            });
            tvForLinks.setText("Number links: " + data.size());
            ivit = new ExampleCollageLoader.ImageViewImageTarget(ivForCollage);
            CollageLoaderManager.getLoader().loadCollage(data, ivit);
        }
    }

    private interface OnClickListener{
        void onClick();
    }

    private class ClickListener implements OnClickListener{
        @Override
        public void onClick() {
            data = generateData();
            notifyDataSetChanged();
        }
    }
}
