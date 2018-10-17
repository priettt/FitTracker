package com.pps.globant.fittracker.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;

import com.pps.globant.fittracker.FirstAppScreenActivity;
import com.pps.globant.fittracker.LoginLocallyActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.SignUpModel;
import com.pps.globant.fittracker.mvp.view.SignUpView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.MailFormater;
import com.pps.globant.fittracker.utils.MailSender;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpPresenter {

    public static final String EXTRA_MESSAGE = "com.pps.globant.fittracker.USERID";
    public static final String DATE_PATTERN = "dd/mm/yyyy";
    private static final boolean COMPLETE = true;
    private SignUpView view;
    private SignUpModel model;
    private String userId;

    public SignUpPresenter(SignUpView view, SignUpModel model, String userId) {
        this.view = view;
        this.model = model;
        this.userId = userId;
        setTypeOfView(userId);
    }

    private void setTypeOfView(String userId) {
        if (userId == null) {
            view.setCompleteLoginView();
        } else {
            view.setLoginSocial();
            model.getUserFromDbById(Integer.parseInt(userId));
        }
    }

    @Subscribe
    public void onFetchingUserFromDataBaseCompleted(UsersRepository.FetchingUserFromDataBaseCompleted event) {
        if (event.user == null) {
            return;
        }
        model.setUser(event.user);
        String firstName = event.user.getFirstName();
        String lastName = event.user.getLastName();
        String email = event.user.getEmail();
        Date birthday = event.user.getBirthday();
        if (firstName != null) view.setFirstName(firstName);
        if (lastName != null) view.setLastName(lastName);
        if (email != null) view.setEmail(email);
        if (birthday != null) view.setBirthday(new SimpleDateFormat(DATE_PATTERN).format(birthday));
    }

    public void register() {
        BusProvider.register(this);
    }

    public void unregister() {
        BusProvider.unregister(this);
    }

    @Subscribe
    public void onSignUpEventComplete(SignUpView.SignUpEventComplete event) {

        if (!validate(event)) {
            view.popUp(R.string.sign_up_failed);
            return;
        }
        try {
            model.setUser(User.getUser(event.firstName, event.lastName, event.email, event.username, event.password,
                    new SimpleDateFormat(DATE_PATTERN).parse(event.birthday)));
        } catch (ParseException e) {
            // an exception is not posible because we use a pick up dialog to ensure a right format
        }
        model.insertUserToDB();//done async
        if (event.sendEmail) sendMail(model.getUser());
        startTheAppFirstScreen();
    }

    @Subscribe
    public void onSignUpEventSocial(SignUpView.SignUpEventSocial event) {

        if (!validateSocial(event)) {
            view.popUp(R.string.sign_up_failed);
            return;
        }
        User user = model.getUser();
        user.setFirstName(event.firstName);
        user.setLastName(event.lastName);
        user.setEmail(event.email);
        try {
            user.setBirthday(new SimpleDateFormat(view.getContext().getResources().getString(R.string.date_format)).parse
                    (event.birthday));
        } catch (ParseException e) {
            //not posible
        }
        user.setRegisterComplete(COMPLETE);
        model.updateUserToDB();//done async
        if (event.sendEmail) sendMail(model.getUser());
        startTheAppFirstScreen();
    }

    private void sendMail(final User user) {
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                String body = MailFormater.format(user);
                Resources res = view.getContext().getResources();
                try {
                    MailSender sender = new MailSender(res.getString(R.string.app_email_account), res.getString(R
                            .string.app_email_password), res.getString(R.string.app_email_host));
                    sender.sendMail(res.getString(R.string.mail_subject),
                            body,
                            res.getString(R.string.app_email_account),
                            user.getEmail());
                } catch (Exception e) {
                    view.popUp(R.string.email_cant_be_send);
                }
            }
        });
        sender.start();
    }

    private boolean validateSocial(SignUpView.SignUpEventSocial event) {
        boolean valid = true;
        view.cleanErrors();
        if (event.firstName.isEmpty() || event.firstName.length() < 3) {
            view.setFirstNameError(R.string.first_name_error);
            valid = false;
        }

        if (event.lastName.isEmpty() || event.lastName.length() < 3) {
            view.setLastNameError(R.string.last_name_error);
            valid = false;
        }

        if (event.email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(event.email).matches()) {
            view.setEmailError(R.string.email_error);
            valid = false;
        }

        if (event.birthday.isEmpty()) {
            view.setBirthdayError(R.string.birthday_error);
            valid = false;
        }
        return valid;
    }

    @Subscribe
    public void onSignUpEvent(SignUpView.SignUpEvent event) {
        view.showSingUpProgressDialog();
        if (userId == null) view.singUpComplete();
        else view.singUpSocial();
    }

    @Subscribe
    public void onLoginLinkPressedEvent(SignUpView.LoginLinkPressedEvent event) {
        startLoginScreen();
    }

    private void startLoginScreen() {
        Activity activity = view.getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, LoginLocallyActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void startTheAppFirstScreen() {
        Activity activity = view.getActivity();
        if (activity == null)return;
        Intent intent = new Intent(activity, FirstAppScreenActivity.class);
        long userId = model.getUser().getId();
        intent.putExtra(EXTRA_MESSAGE, String.valueOf(userId));
        activity.startActivity(intent);
        activity.finish();
    }

    @Subscribe
    public void onDateSetEvent(SignUpView.DatePickerFragment.DateSetEvent event) {
        String date = String.format(view.getContext().getString(R.string.date_format), event.day, event.month, event.year);
        view.setBirthday(date);
    }

    private boolean validate(SignUpView.SignUpEventComplete event) {
        boolean valid = true;
        view.cleanErrors();
        if (!model.validateFirstName(event.firstName)) {
            view.setFirstNameError(R.string.first_name_error);
            valid = false;
        }

        if (!model.validateLastName(event.lastName)) {
            view.setLastNameError(R.string.last_name_error);
            valid = false;
        }

        if (!model.validateEmail(event.email)) {
            view.setEmailError(R.string.email_error);
            valid = false;
        }

        if (!model.validateUserName(event.username)) {
            view.setUsernameError(R.string.username_error);
            valid = false;
        }

        if (!model.validatePassword(event.password)) {
            view.setPasswordError(R.string.password_error);
            valid = false;
        }

        if (!model.validateReEnterPassword(event.password, event.reEnterPassword)) {
            view.setReEnterPasswordError(R.string.re_enter_password_error);
            valid = false;
        }

        if (!model.validateBirthday(event.birthday)) {
            view.setBirthdayError(R.string.birthday_error);
            valid = false;
        }
        return valid;
    }

}

