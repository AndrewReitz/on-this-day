package com.andrewreitz.onthisday.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewreitz.onthisday.ui.misc.BaseFragment;

public final class HomeFragment extends BaseFragment {

  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
