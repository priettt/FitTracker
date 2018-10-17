package com.pps.globant.fittracker.utils;

import com.pps.globant.fittracker.mvp.model.DataBase.User;

import java.text.SimpleDateFormat;

import static com.pps.globant.fittracker.mvp.presenter.SignUpPresenter.DATE_PATTERN;

public class MailFormater {

    public static final String UNREGISTER_USER = "UNREGISTER USER";
    public static final String BASIC_DATA = "Datos ingresados en el registro de cuenta: \n Nombre : %1$s\n Apellido : %2$s\n Email : %3$s\n Fecha de Nacimiento : %4$s\n\n\n ";
    public static final String SOCIAL_NETWORK_DATA = "Signup realizado por medio de red social con identificador: %1$s\n ";
    public static final String MANUAL_SIGNUP_DATA = "Signup realizado por registro manual \n Username: %1$s\nPassword: %2$s\n";

    public static String format(User user) {
        if (!user.isRegisterComplete()) return UNREGISTER_USER;
        String mailBody = String.format(BASIC_DATA, user.getFirstName(), user.getLastName(), user
                .getEmail(), new SimpleDateFormat(DATE_PATTERN).format(user.getBirthday()));
        if (user.getSocialNetworkId() != null)
            mailBody = mailBody.concat(String.format(SOCIAL_NETWORK_DATA, user.getSocialNetworkId()));
        else
            mailBody = mailBody.concat(String.format(MANUAL_SIGNUP_DATA, user.getUsername(), user.getPassword()));
        return mailBody;
    }
}
