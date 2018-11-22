package com.pps.globant.fittracker.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.INotificationSideChannel;

import com.pps.globant.fittracker.FirstAppScreenActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.SignInFormActivity;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.LoginLocallyModel;
import com.pps.globant.fittracker.mvp.view.LoginLocallyView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.mail.Quota;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoginLocallyPresenter.class)
public class LoginLocallyPresenterTest {
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String FIELD_NAME = "user";
    private static final Long ID = 1l;

    @Mock
    private LoginLocallyView view;
    @Mock
    private Activity activity;
    @Mock
    private LoginLocallyModel model;
    @Mock
    private LoginLocallyView.LoginEvent loginEvent;
    @Mock
    private UsersRepository.FetchingUserFromDataBaseCompleted fetchingUserFromDataBaseCompleted;
    @Mock
    private LoginLocallyView.signUpLinkPressedEvent signUpLinkPressedEvent;
    @Mock
    private User user;


    private LoginLocallyPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginLocallyPresenter(view, model);
        Mockito.when(user.getId()).thenReturn(ID);
        Mockito.when(view.getActivity()).thenReturn(activity);
        Mockito.when(model.validateUserName(USERNAME)).thenReturn(Boolean.TRUE);
        Mockito.when(model.validatePassword(PASSWORD)).thenReturn(Boolean.TRUE);
    }

    @Test
    public void onLoginEventValid() {
        Whitebox.setInternalState(loginEvent, "username", USERNAME);
        Whitebox.setInternalState(loginEvent, "password", PASSWORD);
        presenter.onLoginEvent(loginEvent);
        Mockito.verify(view).cleanErrors();
        Mockito.verify(model).getUserFromDbByUsernameAndPassword(USERNAME, PASSWORD);
        Mockito.verify(model).validatePassword(PASSWORD);
        Mockito.verify(model).validateUserName(USERNAME);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onLoginEventInvalidUsername() {
        Whitebox.setInternalState(loginEvent, "username", USERNAME);
        Whitebox.setInternalState(loginEvent, "password", PASSWORD);
        Mockito.when(model.validateUserName(USERNAME)).thenReturn(Boolean.FALSE);
        presenter.onLoginEvent(loginEvent);
        Mockito.verify(view).cleanErrors();
        Mockito.verify(view).setUsernameError(R.string.username_error);
        Mockito.verify(view).popUp(R.string.login_failed);
        Mockito.verify(model).validatePassword(PASSWORD);
        Mockito.verify(model).validateUserName(USERNAME);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onLoginEventInvalidPassword() {
        Whitebox.setInternalState(loginEvent, "username", USERNAME);
        Whitebox.setInternalState(loginEvent, "password", PASSWORD);
        Mockito.when(model.validatePassword(PASSWORD)).thenReturn(Boolean.FALSE);
        presenter.onLoginEvent(loginEvent);
        Mockito.verify(view).cleanErrors();
        Mockito.verify(view).setPasswordError(R.string.password_error);
        Mockito.verify(view).popUp(R.string.login_failed);
        Mockito.verify(model).validatePassword(PASSWORD);
        Mockito.verify(model).validateUserName(USERNAME);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onFetchingUserFromDataBaseCompletedIncorrectUsername() {
        Whitebox.setInternalState(fetchingUserFromDataBaseCompleted, FIELD_NAME, (User) null);
        presenter.onFetchingUserFromDataBaseCompleted(fetchingUserFromDataBaseCompleted);
        Mockito.verify(view).popUp(R.string.username_and_password_error);
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onFetchingUserFromDataBaseCompletedUserNotCompletedRegistered() {
        Mockito.when(user.isRegisterComplete()).thenReturn(Boolean.FALSE);
        Whitebox.setInternalState(fetchingUserFromDataBaseCompleted, FIELD_NAME, user);
        presenter.onFetchingUserFromDataBaseCompleted(fetchingUserFromDataBaseCompleted);
        Mockito.verify(view).popUp(R.string.username_and_password_error);
        Mockito.verifyNoMoreInteractions(view);
    }


    @Test
    public void onFetchingUserFromDataBaseCompletedUserCorrect() throws Exception {
        Mockito.when(user.isRegisterComplete()).thenReturn(Boolean.TRUE);
        final Intent intent = PowerMockito.mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withArguments(activity,FirstAppScreenActivity.class).thenReturn(intent);
        Whitebox.setInternalState(fetchingUserFromDataBaseCompleted, FIELD_NAME, user);
        presenter.onFetchingUserFromDataBaseCompleted(fetchingUserFromDataBaseCompleted);
        Mockito.verify(user).isRegisterComplete();
        Mockito.verify(view).getActivity();
        Mockito.verify(user).getId();
        Mockito.verify(activity).startActivity(intent);
        Mockito.verify(activity).finish();
        Mockito.verify(intent).putExtra(LoginLocallyPresenter.EXTRA_MESSAGE,String.valueOf(ID));
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(activity);
        Mockito.verifyNoMoreInteractions(user);
        Mockito.verifyNoMoreInteractions(intent);
    }

    @Test
    public void onSignUpLinkPressedEvent() throws Exception {
        final Intent intent = PowerMockito.mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withArguments(activity,SignInFormActivity.class).thenReturn(intent);
        presenter.onSignUpLinkPressedEvent(signUpLinkPressedEvent);
        Mockito.verify(view).getActivity();
        Mockito.verify(activity).startActivity(intent);
        Mockito.verify(activity).finish();
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(activity);
        Mockito.verifyNoMoreInteractions(intent);
    }

}
