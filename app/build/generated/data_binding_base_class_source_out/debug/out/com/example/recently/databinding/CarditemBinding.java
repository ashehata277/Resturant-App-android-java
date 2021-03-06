// Generated by view binder compiler. Do not edit!
package com.example.recently.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public final class CarditemBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView DeleteImage;

  @NonNull
  public final EditText Notes;

  @NonNull
  public final TextView des;

  @NonNull
  public final LinearLayout linearLayout3;

  @NonNull
  public final LinearLayout linearLayout4;

  @NonNull
  public final TextView name;

  @NonNull
  public final EditText number;

  @NonNull
  public final TextView piceprice;

  @NonNull
  public final TextView totalprice;

  private CarditemBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView DeleteImage,
      @NonNull EditText Notes, @NonNull TextView des, @NonNull LinearLayout linearLayout3,
      @NonNull LinearLayout linearLayout4, @NonNull TextView name, @NonNull EditText number,
      @NonNull TextView piceprice, @NonNull TextView totalprice) {
    this.rootView = rootView;
    this.DeleteImage = DeleteImage;
    this.Notes = Notes;
    this.des = des;
    this.linearLayout3 = linearLayout3;
    this.linearLayout4 = linearLayout4;
    this.name = name;
    this.number = number;
    this.piceprice = piceprice;
    this.totalprice = totalprice;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CarditemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CarditemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.carditem, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CarditemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.DeleteImage;
      ImageView DeleteImage = rootView.findViewById(id);
      if (DeleteImage == null) {
        break missingId;
      }

      id = R.id.Notes;
      EditText Notes = rootView.findViewById(id);
      if (Notes == null) {
        break missingId;
      }

      id = R.id.des;
      TextView des = rootView.findViewById(id);
      if (des == null) {
        break missingId;
      }

      id = R.id.linearLayout3;
      LinearLayout linearLayout3 = rootView.findViewById(id);
      if (linearLayout3 == null) {
        break missingId;
      }

      id = R.id.linearLayout4;
      LinearLayout linearLayout4 = rootView.findViewById(id);
      if (linearLayout4 == null) {
        break missingId;
      }

      id = R.id.name;
      TextView name = rootView.findViewById(id);
      if (name == null) {
        break missingId;
      }

      id = R.id.number;
      EditText number = rootView.findViewById(id);
      if (number == null) {
        break missingId;
      }

      id = R.id.piceprice;
      TextView piceprice = rootView.findViewById(id);
      if (piceprice == null) {
        break missingId;
      }

      id = R.id.totalprice;
      TextView totalprice = rootView.findViewById(id);
      if (totalprice == null) {
        break missingId;
      }

      return new CarditemBinding((ConstraintLayout) rootView, DeleteImage, Notes, des,
          linearLayout3, linearLayout4, name, number, piceprice, totalprice);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
