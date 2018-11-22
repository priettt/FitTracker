package com.pps.globant.fittracker.mvp.model;

import android.text.TextUtils;
import android.util.Patterns;

import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.squareup.otto.Bus;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class,Patterns.class})
public class SignUpModelTest {

    private static final long ANY_LONG = 99;
    private static final String ANY_STRING = "ABC";
    private static final String CORRECT = "correct1234";
    private static final String SHORT = "S";
    private static final String CORRECTPASS = "123456";
    private static final String EMPTY = "";
    private static final String NULL = null;
    private static final String CORRECT_EMAIL = "example@example.com";
    private static final String CORRECTPASS2 = "123456789";
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";

    @Mock
    private Bus bus;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private User activeUser;

    private SignUpModel model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new SignUpModel(bus, usersRepository);
        Mockito.when(activeUser.getId()).thenReturn(ANY_LONG);
        Mockito.when(activeUser.getSocialNetworkId()).thenReturn(ANY_STRING);
        mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(Matchers.anyString())).thenReturn(false);
        PowerMockito.when(TextUtils.isEmpty(EMPTY)).thenReturn(true);
        PowerMockito.when(TextUtils.isEmpty(NULL)).thenReturn(true);
    }

    @Test
    public void getUserSetUser() {
        model.setUser(activeUser);
        User response = model.getUser();
        assertEquals(response, activeUser);
    }

    @Test
    public void getUserFromDbById() {
        model.setUser(activeUser);
        model.getUserFromDbById();
        model.getUserFromDbById(ANY_LONG);
        Mockito.verify(usersRepository,Mockito.times(2)).getById(ANY_LONG);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void getUserFromDbBySocialNetworkId() {
        model.setUser(activeUser);
        model.getUserFromDbBySocialNetworkId();
        Mockito.verify(usersRepository).getBySocialNetworkId(ANY_STRING);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void insertUserToDB() {
        model.setUser(activeUser);
        model.insertUserToDB();
        Mockito.verify(usersRepository).insert(activeUser);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void updateUserToDB() {
        model.setUser(activeUser);
        model.updateUserToDB();
        Mockito.verify(usersRepository).update(activeUser);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void deleteUserFromDb() {
        model.setUser(activeUser);
        model.deleteUserFromDb();
        Mockito.verify(usersRepository).delete(activeUser);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void validateFirstName() {
        final String correct = CORRECT;
        final String wrong = SHORT;
        final String wrong2 = EMPTY;
        final String wrong3 = NULL;
        assertTrue(model.validateFirstName(correct));
        assertTrue(!model.validateFirstName(wrong));
        assertTrue(!model.validateFirstName(wrong2));
        assertTrue(!model.validateFirstName(wrong3));
        verifyStatic(Mockito.times(4));
        TextUtils.isEmpty(Matchers.anyString());
        verifyStatic();
        verifyNoMoreInteractions(TextUtils.class);
    }

    @Test
    public void validateLastName() {
        final String correct = CORRECT;
        final String wrong = SHORT;
        final String wrong2 = EMPTY;
        final String wrong3 = NULL;
        assertTrue(model.validateLastName(correct));
        assertTrue(!model.validateLastName(wrong));
        assertTrue(!model.validateLastName(wrong2));
        assertTrue(!model.validateLastName(wrong3));
        verifyStatic(Mockito.times(4));
        TextUtils.isEmpty(Matchers.anyString());
        verifyStatic();
        verifyNoMoreInteractions(TextUtils.class);
    }

    @Test
    public void validateEmail() {
        Pattern myPattern = mock(Pattern.class);
        Matcher myMatcherOk = mock(Matcher.class);
        Matcher myMatcherWrong = mock(Matcher.class);
        Whitebox.setInternalState(Patterns.class, EMAIL_ADDRESS, myPattern);
        PowerMockito.when(myPattern.matcher(CORRECT_EMAIL)).thenReturn(myMatcherOk);
        PowerMockito.when(myMatcherOk.matches()).thenReturn(Boolean.TRUE);
        assertTrue(model.validateEmail(CORRECT_EMAIL));
        assertTrue(!model.validateEmail(EMPTY));
        assertTrue(!model.validateEmail(NULL));
        verifyStatic(Mockito.times(3));
        TextUtils.isEmpty(Matchers.anyString());
        verifyStatic();
        verifyNoMoreInteractions(TextUtils.class);
    }


    @Test
    public void validateUserName() {
        final String correctUsername = CORRECT;
        final String wrongUsername = SHORT;
        final String wrongUsername2 = EMPTY;
        final String wrongUsername3 = NULL;
        assertTrue(model.validateUserName(correctUsername));
        assertTrue(!model.validateUserName(wrongUsername));
        assertTrue(!model.validateUserName(wrongUsername2));
        assertTrue(!model.validateUserName(wrongUsername3));
        verifyStatic(Mockito.times(4));
        TextUtils.isEmpty(Matchers.anyString());
        verifyStatic();
        verifyNoMoreInteractions(TextUtils.class);
    }

    @Test
    public void validatePassword() {
        final String correctPassword = CORRECTPASS;
        final String wrongPassword = SHORT;
        final String wrongPassword2 = EMPTY;
        final String wrongPassword3 = NULL;
        assertTrue(model.validatePassword(correctPassword));
        assertTrue(!model.validatePassword(wrongPassword));
        assertTrue(!model.validatePassword(wrongPassword2));
        assertTrue(!model.validatePassword(wrongPassword3));
        verifyStatic(Mockito.times(4));
        TextUtils.isEmpty(Matchers.anyString());
        verifyStatic();
        verifyNoMoreInteractions(TextUtils.class);
    }

    @Test
    public void validateReEnterPassword() {
        final String correctPassword = CORRECTPASS;
        final String correctPasswordDiff = CORRECTPASS2;
        final String wrongPassword2 = EMPTY;
        final String wrongPassword3 = NULL;
        assertTrue(model.validateReEnterPassword(correctPassword,correctPassword));
        assertTrue(!model.validateReEnterPassword(correctPassword, correctPasswordDiff));
        assertTrue(!model.validateReEnterPassword(wrongPassword2,wrongPassword2));
        assertTrue(!model.validateReEnterPassword(wrongPassword3,wrongPassword3));
        verifyStatic(Mockito.times(4));
        TextUtils.isEmpty(Matchers.anyString());
        verifyStatic();
        verifyNoMoreInteractions(TextUtils.class);
    }

    @Test
    public void validateBirthDay() {
        final String correct = CORRECT;
        final String wrong = EMPTY;
        final String wrong2 = NULL;
        assertTrue(model.validateBirthday(correct));
        assertTrue(!model.validateBirthday(wrong));
        assertTrue(!model.validateBirthday(wrong2));
        verifyStatic(Mockito.times(3));
        TextUtils.isEmpty(Matchers.anyString());
        verifyStatic();
        verifyNoMoreInteractions(TextUtils.class);
    }

}
