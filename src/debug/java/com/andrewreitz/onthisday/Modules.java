package com.andrewreitz.onthisday;

final class Modules {
  static Object[] list(OnThisDayApp app) {
    return new Object[]{
        new OnThisDayModule(app),
        new DebugOnThisDayModule()
    };
  }

  private Modules() {
    // No instances.
  }
}
