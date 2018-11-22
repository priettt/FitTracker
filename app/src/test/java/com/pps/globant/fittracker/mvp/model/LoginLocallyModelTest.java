package com.pps.globant.fittracker.mvp.model;

import android.text.TextUtils;

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
import org.mockito.internal.verification.Times;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class LoginLocallyModelTest {

    private static final long ANY_LONG = 99;
    private static final String ANY_STRING = "ABC";
    private static final String ANY_USERNAME = "JOHN";
    private static final String ANY_PASS = "password";
    private static final String CORRECTUSERNAME = "correctusername";
    private static final String SHORT = "Sho";
    private static final String CORRECTPASS = "123456";
    private static final String EMPTY = "";
    private static final String NULL = null;

    @Mock
    private Bus bus;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private User activeUser;

    private LoginLocallyModel model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new LoginLocallyModel(bus, usersRepository);
        Mockito.when(activeUser.getId()).thenReturn(ANY_LONG);
        Mockito.when(activeUser.getSocialNetworkId()).thenReturn(ANY_STRING);
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
        Mockito.verify(usersRepository).getById(ANY_LONG);
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
    public void getUserFromDbByUsername() {
        model.getUserFromDbByUsername(ANY_USERNAME);
        Mockito.verify(usersRepository).getByUsername(ANY_USERNAME);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void getUserFromDbByUsernameAndPassword() {
        model.getUserFromDbByUsernameAndPassword(ANY_USERNAME, ANY_PASS);
        Mockito.verify(usersRepository).getByUsernameAndPassword(ANY_USERNAME, ANY_PASS);
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
    public void validateUserName() {
        mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(Matchers.anyString())).thenReturn(false);
        PowerMockito.when(TextUtils.isEmpty(EMPTY)).thenReturn(true);
        PowerMockito.when(TextUtils.isEmpty(NULL)).thenReturn(true);
        final String correctUsername = CORRECTUSERNAME;
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
        mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(Matchers.anyString())).thenReturn(false);
        PowerMockito.when(TextUtils.isEmpty(EMPTY)).thenReturn(true);
        PowerMockito.when(TextUtils.isEmpty(NULL)).thenReturn(true);
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

}

