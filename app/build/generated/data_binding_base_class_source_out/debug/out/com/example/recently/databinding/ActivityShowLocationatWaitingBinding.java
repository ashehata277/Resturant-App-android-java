// Generated by view binder compiler. Do not edit!
package com.example.recently.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.recently.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class ActivityShowLocationatWaitingBinding implements ViewBinding {
  @NonNull
  private final View rootView;

  private ActivityShowLocationatWaitingBinding(@NonNull View rootView) {
    this.rootView = rootView;
  }

  @Override
  @NonNull
  public View getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityShowLocationatWaitingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityShowLocationatWaitingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_show_locationat_waiting, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityShowLocationatWaitingBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    return new ActivityShowLocationatWaitingBinding(rootView);
  }
}
