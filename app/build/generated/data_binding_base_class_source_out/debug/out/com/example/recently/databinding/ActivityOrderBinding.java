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

public final class ActivityOrderBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ListView Dynamic;

  @NonNull
  public final LinearLayout linearLayout15;

  @NonNull
  public final LinearLayout linearLayout7;

  @NonNull
  public final TextView orderitemname2;

  @NonNull
  public final TextView orderitemnote2;

  @NonNull
  public final TextView orderitemnumber2;

  @NonNull
  public final TextView orderitemtotal2;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textView8;

  @NonNull
  public final TextView textView9;

  private ActivityOrderBinding(@NonNull ConstraintLayout rootView, @NonNull ListView Dynamic,
      @NonNull LinearLayout linearLayout15, @NonNull LinearLayout linearLayout7,
      @NonNull TextView orderitemname2, @NonNull TextView orderitemnote2,
      @NonNull TextView orderitemnumber2, @NonNull TextView orderitemtotal2,
      @NonNull TextView textView4, @NonNull TextView textView6, @NonNull TextView textView7,
      @NonNull TextView textView8, @NonNull TextView textView9) {
    this.rootView = rootView;
    this.Dynamic = Dynamic;
    this.linearLayout15 = linearLayout15;
    this.linearLayout7 = linearLayout7;
    this.orderitemname2 = orderitemname2;
    this.orderitemnote2 = orderitemnote2;
    this.orderitemnumber2 = orderitemnumber2;
    this.orderitemtotal2 = orderitemtotal2;
    this.textView4 = textView4;
    this.textView6 = textView6;
    this.textView7 = textView7;
    this.textView8 = textView8;
    this.textView9 = textView9;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOrderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOrderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_order, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOrderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id._dynamic;
      ListView Dynamic = rootView.findViewById(id);
      if (Dynamic == null) {
        break missingId;
      }

      id = R.id.linearLayout15;
      LinearLayout linearLayout15 = rootView.findViewById(id);
      if (linearLayout15 == null) {
        break missingId;
      }

      id = R.id.linearLayout7;
      LinearLayout linearLayout7 = rootView.findViewById(id);
      if (linearLayout7 == null) {
        break missingId;
      }

      id = R.id.orderitemname2;
      TextView orderitemname2 = rootView.findViewById(id);
      if (orderitemname2 == null) {
        break missingId;
      }

      id = R.id.orderitemnote2;
      TextView orderitemnote2 = rootView.findViewById(id);
      if (orderitemnote2 == null) {
        break missingId;
      }

      id = R.id.orderitemnumber2;
      TextView orderitemnumber2 = rootView.findViewById(id);
      if (orderitemnumber2 == null) {
        break missingId;
      }

      id = R.id.orderitemtotal2;
      TextView orderitemtotal2 = rootView.findViewById(id);
      if (orderitemtotal2 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = rootView.findViewById(id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = rootView.findViewById(id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = rootView.findViewById(id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = rootView.findViewById(id);
      if (textView8 == null) {
        break missingId;
      }

      id = R.id.textView9;
      TextView textView9 = rootView.findViewById(id);
      if (textView9 == null) {
        break missingId;
      }

      return new ActivityOrderBinding((ConstraintLayout) rootView, Dynamic, linearLayout15,
          linearLayout7, orderitemname2, orderitemnote2, orderitemnumber2, orderitemtotal2,
          textView4, textView6, textView7, textView8, textView9);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}