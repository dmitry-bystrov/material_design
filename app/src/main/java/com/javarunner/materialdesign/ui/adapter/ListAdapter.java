package com.javarunner.materialdesign.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.presentation.presenter.ListPresenter;

import io.reactivex.subjects.PublishSubject;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private ListPresenter presenter;
    private PublishSubject<ListEvent> events;

    public ListAdapter(ListPresenter presenter) {
        this.presenter = presenter;
        this.events = PublishSubject.create();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false), events);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        presenter.onBindListItem(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public PublishSubject<ListEvent> getEvents() {
        return events;
    }
}
