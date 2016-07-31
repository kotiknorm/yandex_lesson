package background.mblz.yandex.com.yandexlesson.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import background.mblz.yandex.com.yandexlesson.R;
import background.mblz.yandex.com.yandexlesson.YandexApplication;
import background.mblz.yandex.com.yandexlesson.handler.CriticalSectionsManager;
import background.mblz.yandex.com.yandexlesson.model.Genre;
import background.mblz.yandex.com.yandexlesson.provider.GenreProvider;
import background.mblz.yandex.com.yandexlesson.recycler.RecyclerViewAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {


    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_content, container, false);
        final RecyclerView mRecyclerView = (RecyclerView) inflate.findViewById(R.id.genreList);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        GenreProvider genreProvider = ((YandexApplication)getContext().getApplicationContext()).getGenreProvider();
        genreProvider.getGenres(new Callback<Genre[]>() {
            @Override
            public void success(Genre[] genres, Response response) {
                RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(genres);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            CriticalSectionsManager.getHandler().stopSection(1);
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (Math.abs(dy) > 3) {
                            CriticalSectionsManager.getHandler().startSection(1);
                        } else {
                            CriticalSectionsManager.getHandler().stopSection(1);
                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        return inflate;
    }

}
