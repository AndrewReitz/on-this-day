package com.andrewreitz.onthisday.ui.musiclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.model.Data;
import com.andrewreitz.onthisday.ui.misc.BindableAdapter;

import java.util.Collections;
import java.util.List;

public class MusicListAdapter extends BindableAdapter<Data> {
  private List<Data> data = Collections.emptyList();

  public MusicListAdapter(Context context) {
    super(context);
  }

  public void updateData(List<Data> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  @Override public int getCount() {
    return data.size();
  }

  @Override public Data getItem(int position) {
    return data.get(position);
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View newView(LayoutInflater inflater, int position, ViewGroup container) {
    return inflater.inflate(R.layout.view_music_item, container, false);
  }

  @Override public void bindView(Data item, int position, View view) {
    ((MusicItemView) view).bindTo(item);
  }
}
