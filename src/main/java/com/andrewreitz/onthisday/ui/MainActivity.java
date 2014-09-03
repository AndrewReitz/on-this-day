package com.andrewreitz.onthisday.ui;

import android.os.Bundle;
import android.view.ViewGroup;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.OnThisDayApp;
import com.andrewreitz.onthisday.ui.misc.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

  @Inject AppContainer appContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OnThisDayApp app = OnThisDayApp.get(this);
    ViewGroup container = appContainer.get(this, app);

    getLayoutInflater().inflate(R.layout.activity_main, container);
  }
}
