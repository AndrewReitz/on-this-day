package com.andrewreitz.onthisday.ui.eventbus;

/** Event bus event to change the tool bar's title. */
public final class ToolBarTitleEvent {
  public final String title;

  public ToolBarTitleEvent(String title) {
    this.title = title;
  }
}