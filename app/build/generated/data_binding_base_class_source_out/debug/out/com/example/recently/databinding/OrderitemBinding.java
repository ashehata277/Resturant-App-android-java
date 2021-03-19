// Generated by view binder compiler. Do not edit!
package com.example.recently.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.recently.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class OrderitemBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LinearLayout linearLayout10;

  @NonNull
  public final LinearLayout linearLayout8;

  @NonNull
  public final LinearLayout linearLayout9;

  @NonNull
  public final TextView orderitemname;

  @NonNull
  public final TextView orderitemnote;

  @NonNull
  public final TextView orderitemnumer;

  @NonNull
  public final TextView orderitemtotal;

  @NonNull
  public final TextView textView18;

  @NonNull
  public final TextView textView20;

  @NonNull
  public final TextView textview500;

  private OrderitemBinding(@NonNull ConstraintLayout rootView, @NonNull LinearLayout linearLayout10,
      @NonNull LinearLayout linearLayout8, @NonNull LinearLayout linearLayout9,
      @NonNull TextView orderitemname, @NonNull TextView orderitemnote,
      @NonNull TextView orderitemnumer, @NonNull TextView orderitemtotal,
      @NonNull TextView textView18, @NonNull TextView textView20, @NonNull TextView textview500) {
    this.rootView = rootView;
    this.linearLayout10 = linearLayout10;
    this.linearLayout8 = linearLayout8;
    this.linearLayout9 = linearLayout9;
    this.orderitemname = orderitemname;
    this.orderitemnote = orderitemnote;
    this.orderitemnumer = orderitemnumer;
    this.orderitemtotal = orderitemtotal;
    this.textView18 = textView18;
    this.textView20 = textView20;
    this.textview500 = textview500;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static OrderitemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static OrderitemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.orderitem, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static OrderitemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.linearLayout10;
      LinearLayout linearLayout10 = rootView.findViewById(id);
      if (linearLayout10 == null) {
        break missingId;
      }

      id = R.id.linearLayout8;
      LinearLayout linearLayout8 = rootView.findViewById(id);
      if (linearLayout8 == null) {
        break missingId;
      }

      id = R.id.linearLayout9;
      LinearLayout linearLayout9 = rootView.findViewById(id);
      if (linearLayout9 == null) {
        break missingId;
      }

      id = R.id.orderitemname;
      TextView orderitemname = rootView.findViewById(id);
      if (orderitemname == null) {
        break missingId;
      }

      id = R.id.orderitemnote;
      TextView orderitemnote = rootView.findViewById(id);
      if (orderitemnote == null) {
        break missingId;
      }

      id = R.id.orderitemnumer;
      TextView orderitemnumer = rootView.findViewById(id);
      if (orderitemnumer == null) {
        break missingId;
      }

      id = R.id.orderitemtotal;
      TextView orderitemtotal = rootView.findViewById(id);
      if (orderitemtotal == null) {
        break missingId;
      }

      id = R.id.textView18;
      TextView textView18 = rootView.findViewById(id);
      if (textView18 == null) {
        break missingId;
      }

      id = R.id.textView20;
      TextView textView20 = rootView.findViewById(id);
      if (textView20 == null) {
        break missingId;
      }

      id = R.id.textview500;
      TextView textview500 = rootView.findViewById(id);
      if (textview500 == null) {
        break missingId;
      }

      return new OrderitemBinding((ConstraintLayout) rootView, linearLayout10, linearLayout8,
          linearLayout9, orderitemname, orderitemnote, orderitemnumer, orderitemtotal, textView18,
          textView20, textview500);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
