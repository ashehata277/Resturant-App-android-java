// Generated by view binder compiler. Do not edit!
package com.example.recently.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.recently.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RegistrationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView Register;

  @NonNull
  public final CardView card;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final EditText mailregistration;

  @NonNull
  public final EditText password2registration;

  @NonNull
  public final EditText passwordregistration;

  @NonNull
  public final EditText phonenumber;

  @NonNull
  public final ImageView userimage;

  @NonNull
  public final EditText usernameregitration;

  private RegistrationBinding(@NonNull ConstraintLayout rootView, @NonNull TextView Register,
      @NonNull CardView card, @NonNull CardView cardView, @NonNull EditText mailregistration,
      @NonNull EditText password2registration, @NonNull EditText passwordregistration,
      @NonNull EditText phonenumber, @NonNull ImageView userimage,
      @NonNull EditText usernameregitration) {
    this.rootView = rootView;
    this.Register = Register;
    this.card = card;
    this.cardView = cardView;
    this.mailregistration = mailregistration;
    this.password2registration = password2registration;
    this.passwordregistration = passwordregistration;
    this.phonenumber = phonenumber;
    this.userimage = userimage;
    this.usernameregitration = usernameregitration;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RegistrationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RegistrationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.registration, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RegistrationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Register;
      TextView Register = rootView.findViewById(id);
      if (Register == null) {
        break missingId;
      }

      id = R.id.card;
      CardView card = rootView.findViewById(id);
      if (card == null) {
        break missingId;
      }

      id = R.id.cardView;
      CardView cardView = rootView.findViewById(id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.mailregistration;
      EditText mailregistration = rootView.findViewById(id);
      if (mailregistration == null) {
        break missingId;
      }

      id = R.id.password2registration;
      EditText password2registration = rootView.findViewById(id);
      if (password2registration == null) {
        break missingId;
      }

      id = R.id.passwordregistration;
      EditText passwordregistration = rootView.findViewById(id);
      if (passwordregistration == null) {
        break missingId;
      }

      id = R.id.phonenumber;
      EditText phonenumber = rootView.findViewById(id);
      if (phonenumber == null) {
        break missingId;
      }

      id = R.id.userimage;
      ImageView userimage = rootView.findViewById(id);
      if (userimage == null) {
        break missingId;
      }

      id = R.id.usernameregitration;
      EditText usernameregitration = rootView.findViewById(id);
      if (usernameregitration == null) {
        break missingId;
      }

      return new RegistrationBinding((ConstraintLayout) rootView, Register, card, cardView,
          mailregistration, password2registration, passwordregistration, phonenumber, userimage,
          usernameregitration);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
