package com.pps.globant.fittracker.mvp.model;

import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.service.AvatarService;
import com.squareup.otto.Bus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import javax.xml.transform.sax.TemplatesHandler;

import static org.junit.Assert.assertEquals;

public class    AvatarsModelTest {
    private static final long ID = 1;

    @Mock
    AvatarService avatarService;
    @Mock
    Bus bus;
    @Mock
    User activeUser;
    @Mock
    Thumbnail thumbnail;
    @Mock
    UsersRepository usersRepository;

    AvatarsModel avatarsModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        avatarsModel = new AvatarsModel(avatarService, bus, usersRepository);
        Mockito.when(activeUser.getId()).thenReturn(ID);
    }

    @Test
    public void getAvatarList() {
        avatarsModel.getAvatarsList();
        Mockito.verify(avatarService).getAvatarsList();
        Mockito.verifyNoMoreInteractions(avatarService);
    }

    @Test
    public void getUserSetUser() {
        avatarsModel.setUser(activeUser);
        User response = avatarsModel.getUser();
        assertEquals(response, activeUser);
    }

    @Test
    public void getUserFromDbById() {
        avatarsModel.getUserFromDbById(ID);
        avatarsModel.setUser(activeUser);
        avatarsModel.getUserFromDbById();
        Mockito.verify(usersRepository, Mockito.times(2)).getById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void updateUserToDB() {
        avatarsModel.setUser(activeUser);
        avatarsModel.updateUserToDB();
        Mockito.verify(usersRepository).update(activeUser);
        Mockito.verifyNoMoreInteractions(usersRepository);
    }

    @Test
    public void setThumbnail() {
        avatarsModel.setUser(activeUser);
        avatarsModel.setThumbnail(thumbnail);
        Mockito.verify(activeUser).setAvatarThumbnail(thumbnail);
        Mockito.verify(usersRepository).update(activeUser);
        Mockito.verifyNoMoreInteractions(usersRepository);
        Mockito.verifyNoMoreInteractions(activeUser);
    }
}
