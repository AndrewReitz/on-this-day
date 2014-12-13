package com.andrewreitz.onthisday.ui.show;

import com.andrewreitz.onthisday.OnThisDayModule;
import dagger.Module;

@Module(
    injects = {
        ShowListFragment.class, //
    },
    addsTo = OnThisDayModule.class //
)
public final class ShowModule {
}
