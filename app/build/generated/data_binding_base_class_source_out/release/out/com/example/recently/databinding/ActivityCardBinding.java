// Generated by view binder compiler. Do not edit!
package com.example.recently.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.recently.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCardBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ListView cardList;

  @NonNull
  public final TextView clear;

  @NonNull
  public final LinearLayout linearLayout5;

  @NonNull
  public final TextView send;

  private ActivityCardBinding(@NonNull ConstraintLayout rootView, @NonNull ListView cardList,
      @NonNull TextView clear, @NonNull LinearLayout linearLayout5, @NonNull TextView send) {
    this.rootView = rootView;
    this.cardList = cardList;
    this.clear = clear;
    this.linearLayout5 = linearLayout5;
    this.send = send;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_card, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardList;
      ListView cardList = rootView.findViewById(id);
      if (cardList == null) {
        break missingId;
      }

      id = R.id.clear;
      TextView clear = rootView.findViewById(id);
      if (clear == null) {
        break missingId;
      }

      id = R.id.linearLayout5;
      LinearLayout linearLayout5 = rootView.findViewById(id);
      if (linearLayout5 == null) {
        break missingId;
      }

      id = R.id.send;
      TextView send = rootView.findViewById(id);
      if (send == null) {
        break missingId;
      }

      return new ActivityCardBinding((ConstraintLayout) rootView, cardList, clear, linearLayout5,
          send);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
