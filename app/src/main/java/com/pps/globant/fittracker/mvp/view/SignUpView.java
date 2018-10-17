package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.utils.BusProvider;
import com.squareup.otto.Bus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.edit_first_name)
    EditText firstName;

    @BindView(R.id.edit_last_name)

    EditText lastName;

    @BindView(R.id.edit_email)
    EditText email;

    @BindView(R.id.edit_birthday)
    EditText birthday;

    @BindView(R.id.edit_username)
    EditText username;

    @BindView(R.id.edit_password)
    EditText password;

    @BindView(R.id.edit_re_enter_password)
    EditText reEnterPassword;

    @BindView(R.id.link_login)
    TextView loginLink;

    @BindView(R.id.button_signup)
    Button singUp;

    @BindView(R.id.edit_username_layout)
    TextInputLayout usernameLayout;

    @BindView(R.id.edit_password_layout)
    TextInputLayout passwordLayout;

    @BindView(R.id.edit_re_enter_password_layou)
    TextInputLayout reEnterPasswordLayout;

    @BindView(R.id.send_email)
    CheckBox sendEmail;

    public SignUpView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void singUpComplete() {
        bus.post(new SignUpEventComplete(firstName.getText().toString(), lastName
                .getText().toString(), email.getText().toString(), birthday.getText().toString(), username.getText()
                .toString(), password.getText().toString(), reEnterPassword.getText().toString(), sendEmail.isChecked()));
    }

    public void singUpSocial() {
        bus.post(new SignUpEventSocial(firstName.getText().toString(), lastName
                .getText().toString(), email.getText().toString(), birthday.getText().toString(), sendEmail.isChecked()));
    }

    @OnClick(R.id.button_signup)
    public void signUpButtonPressed() {
        bus.post(new SignUpEvent());
    }

    @OnClick(R.id.link_login)
    public void loginLinkPressed() {
        bus.post(new LoginLinkPressedEvent());
    }

    @OnClick(R.id.edit_birthday)
    public void birthdayEditPressed() {
        DatePickerFragment fragment = new DatePickerFragment();
        FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
        fragment.show(fragmentManager, getContext().getResources().getString(R.string.datePicker));
    }

    public void popUp(@StringRes int mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    public void setFirstNameError(@StringRes int error) {
        firstName.setError(getContext().getResources().getString(error));
    }

    public void setLastNameError(@StringRes int error) {
        lastName.setError(getContext().getResources().getString(error));
    }

    public void setEmailError(@StringRes int error) {
        email.setError(getContext().getResources().getString(error));
    }

    public void setUsernameError(@StringRes int error) {
        username.setError(getContext().getResources().getString(error));
    }

    public void setPasswordError(@StringRes int error) {
        password.setError(getContext().getResources().getString(error));
    }

    public void setReEnterPasswordError(@StringRes int error) {
        reEnterPassword.setError(getContext().getResources().getString(error));
    }

    public void cleanErrors() {
        firstName.setError(null);
        lastName.setError(null);
        email.setError(null);
        birthday.setError(null);
        username.setError(null);
        password.setError(null);
        reEnterPassword.setError(null);
    }

    public void showSingUpProgressDialog() {
        //TODO CREATE A TOP PROGRESS BAR WHILE SIGNING UP
    }

    public void setBirthday(String birthday) {
        this.birthday.setText(birthday);
    }

    public void setBirthdayError(@StringRes int error) {
        birthday.setError(getContext().getResources().getString(error));
    }

    public void setCompleteLoginView() {
        usernameLayout.setVisibility(View.VISIBLE);
        passwordLayout.setVisibility(View.VISIBLE);
        reEnterPasswordLayout.setVisibility(View.VISIBLE);
    }

    public void setLoginSocial() {
        usernameLayout.setVisibility(View.GONE);
        passwordLayout.setVisibility(View.GONE);
        reEnterPasswordLayout.setVisibility(View.GONE);
    }

    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private static final String DATE_PATTERN = "dd/mm/yyyy";

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            BusProvider.getInstance().post(new DateSetEvent(day, month + 1, year));
        }

        public class DateSetEvent {
            public int day;
            public int month;
            public int year;

            public DateSetEvent(int day, int month, int year) {
                this.day = day;
                this.month = month;
                this.year = year;
            }
        }
    }

    public class SignUpEventComplete {
        public String firstName;
        public String lastName;
        public String email;
        public String phoneNumber;
        public String birthday;
        public String username;
        public String password;
        public String reEnterPassword;
        public boolean sendEmail;

        public SignUpEventComplete(String firstName, String lastName, String email, String birthday, String username,
                                   String password, String reEnterPassword, boolean sendEmail) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.birthday = birthday;
            this.username = username;
            this.password = password;
            this.reEnterPassword = reEnterPassword;
            this.sendEmail = sendEmail;
        }
    }

    public class SignUpEvent {
    }

    public class SignUpEventSocial {
        public String firstName;
        public String lastName;
        public String email;
        public String phoneNumber;
        public String birthday;
        public boolean sendEmail;

        public SignUpEventSocial(String firstName, String lastName, String email, String birthday, boolean sendEmail) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.birthday = birthday;
            this.sendEmail = sendEmail;
        }
    }

    public class LoginLinkPressedEvent {
    }
}
