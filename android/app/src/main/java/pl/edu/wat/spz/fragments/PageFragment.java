package pl.edu.wat.spz.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import pl.edu.wat.spz.R;
import pl.edu.wat.spz.activities.NavigationActivity;
import pl.edu.wat.spz.entity.Page;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Getter
public abstract class PageFragment<T> extends Fragment {

    private final int size = 9;
    private Context fragmentContext;
    private List<T> list;
    private RecyclerView.Adapter adapter;
    private int page;
    private boolean enableLoading, isLoading;

    private int pastVisibleItems, visibleItemCount, totalItemCount, viewThreshold;

    protected abstract Call<Page<T>> makePageCall(int page, int size);

    protected abstract RecyclerView.Adapter generateAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentContext = getContext();
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        adapter = generateAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (enableLoading) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + viewThreshold)) {
                        fetchData();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        page = 0;
        pastVisibleItems = visibleItemCount = totalItemCount = viewThreshold = 0;
        enableLoading = isLoading = true;
        list.clear();
        adapter.notifyDataSetChanged();
        fetchData();
    }

    private void toggleLoading(boolean loading) {
        if (fragmentContext instanceof NavigationActivity) {
            NavigationActivity activity = (NavigationActivity) fragmentContext;
            activity.getProgressBar().setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

    private void fetchData() {
        if (!enableLoading) {
            return;
        }

        isLoading = true;
        Call<Page<T>> pageCall = makePageCall(page, size);
        page++;
        toggleLoading(true);
        pageCall.enqueue(new Callback<Page<T>>() {
            @Override
            public void onResponse(Call<Page<T>> call, Response<Page<T>> response) {

                Page<T> body = response.body();
                if (body != null) {
                    getList().addAll(body.getContent());
                    adapter.notifyDataSetChanged();

                    if (body.isLast()) {
                        enableLoading = false;
                        Toast.makeText(fragmentContext, "Koniec wyników", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(fragmentContext, "Załadowano dane", Toast.LENGTH_SHORT).show();
                    }
                }

                isLoading = false;
                toggleLoading(false);
            }

            @Override
            public void onFailure(Call<Page<T>> call, Throwable t) {
                Toast.makeText(fragmentContext, "Błąd połączenia z internetem", Toast.LENGTH_SHORT).show();
                toggleLoading(false);
            }
        });

    }

}
