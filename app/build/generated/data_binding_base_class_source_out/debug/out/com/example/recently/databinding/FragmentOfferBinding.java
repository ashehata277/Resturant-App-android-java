// Generated by view binder compiler. Do not edit!
package com.example.recently.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.recently.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class FragmentOfferBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final FrameLayout offerfragment;

  private FragmentOfferBinding(@NonNull FrameLayout rootView, @NonNull FrameLayout offerfragment) {
    this.rootView = rootView;
    this.offerfragment = offerfragment;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentOfferBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentOfferBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_offer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentOfferBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    FrameLayout offerfragment = (FrameLayout) rootView;

    return new FragmentOfferBinding((FrameLayout) rootView, offerfragment);
  }
}
