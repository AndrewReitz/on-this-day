package com.andrewreitz.onthisday;

import com.andrewreitz.onthisday.data.DebugDataModule;
import com.andrewreitz.onthisday.ui.DebugUiModule;

import dagger.Module;

@Module(
    addsTo = OnThisDayModule.class,
    includes = {
        DebugUiModule.class,
        DebugDataModule.class
    },
    overrides = true
)
public final class DebugOnThisDayModule {
}
