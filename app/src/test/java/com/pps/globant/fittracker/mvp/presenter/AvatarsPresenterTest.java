package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.adapters.AvatarsAdapter;
import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.mvp.events.GetAvatarsSuccessEvent;
import com.pps.globant.fittracker.mvp.model.AvatarsModel;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.view.AvatarsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

public class AvatarsPresenterTest {

    public static final String USER_ID = "1";
    public static final String NAME = "name";
    public static final int WANTED_NUMBER_OF_INVOCATIONS2 = 2;
    public static final int WANTED_NUMBER_OF_INVOCATIONS1 = 1;
    public static final String USER = "user";

    @Mock
    private AvatarsModel model;
    @Mock
    private AvatarsView view;
    @Mock
    private User user;
    @Mock
    private Thumbnail thumbnail;
    @Mock
    private GetAvatarsSuccessEvent getAvatarsSuccessEvent;
    @Mock
    private AvatarsView.LessDetailsPressedEvent lessDetailsPressedEvent;
    @Mock
    private UsersRepository.FetchingUserFromDataBaseCompleted fetchingUserFromDataBaseCompleted;
    @Mock
    private AvatarsView.ChangeAvatarPressedEvent changeAvatarPressedEvent;



    private String firstName;
    private AvatarsPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new AvatarsPresenter(model, view, USER_ID);
        Mockito.when(user.getAvatarThumbnail()).thenReturn(thumbnail);
        firstName = NAME;
        Mockito.when(user.getFirstName()).thenReturn(firstName);
        Mockito.when(model.getUser()).thenReturn(user);
        Mockito.verify(model).getUserFromDbById(Long.parseLong(USER_ID));
    }

    @Test
    public void onAcceptAvatar() {
        presenter.onAcceptAvatar(thumbnail);
        Mockito.verify(model).setThumbnail(thumbnail);
        Mockito.verify(model).getUser();
        Mockito.verify(user).getAvatarThumbnail();
        Mockito.verify(view).setAvatar(thumbnail);
        Mockito.verify(view).collapseCard();
        Mockito.verifyNoMoreInteractions(user);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(thumbnail);
    }

    @Test
    public void onAvatarsSuccessEvent() {
        presenter.onAvatarsSuccessEvent(getAvatarsSuccessEvent);
        Mockito.verify(view).setAdapter(Matchers.<AvatarsAdapter>any());
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onLessDetailsPressedEvent() {
        presenter.onLessDetailsPressedEvent(lessDetailsPressedEvent);
        Mockito.verify(view).collapseCard();
        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void onFetchingUserFromDataBaseCompletedWithoutThumbnail() {
        Whitebox.setInternalState(fetchingUserFromDataBaseCompleted, USER, user);
        Mockito.when(user.getAvatarThumbnail()).thenReturn(null);
        presenter.onFetchingUserFromDataBaseCompleted(fetchingUserFromDataBaseCompleted);
        Mockito.verify(model).setUser(user);
        Mockito.verify(view).setFirstName(firstName);
        Mockito.verify(view).setNullAvatar();
        Mockito.verify(user).getAvatarThumbnail();
        Mockito.verify(user).getFirstName();
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(user);

    }

    @Test
    public void onFetchingUserFromDataBaseCompletedWithThumbnail() {
        Whitebox.setInternalState(fetchingUserFromDataBaseCompleted, USER, user);
        presenter.onFetchingUserFromDataBaseCompleted(fetchingUserFromDataBaseCompleted);
        Mockito.verify(model).setUser(user);
        Mockito.verify(view).setFirstName(firstName);
        Mockito.verify(view).setAvatar(thumbnail);
        Mockito.verify(user,Mockito.times(WANTED_NUMBER_OF_INVOCATIONS2)).getAvatarThumbnail();
        Mockito.verify(user,Mockito.times(WANTED_NUMBER_OF_INVOCATIONS1)).getFirstName();
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(user);
    }

    @Test
    public void onChangeAvatarPressedEvent() {
        presenter.onChangeAvatarPressedEvent(changeAvatarPressedEvent);
        Mockito.verify(view).expandCard();
        Mockito.verify(model).getAvatarsList();
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
    }

}
