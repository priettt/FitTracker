package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.ImageDialog;
import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.mvp.model.ImageDialogModel;
import com.pps.globant.fittracker.mvp.view.ImageDialogView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ImageDialogPresenterTest
{
    public static final String URL_FULL_RESOLUTION = "UrlFullResolution";
    @Mock
    private ImageDialogView view;
    @Mock
    private ImageDialogModel model;
    @Mock
    private ImageDialog.OnAcceptClickListener onAcceptClickListener;
    @Mock
    private ImageDialogView.AcceptClickPressedEvent acceptClickPressedEvent;
    @Mock
    private ImageDialogView.onCancelClickPressedEvent onCancelClickPressedEvent ;
    @Mock
    private Thumbnail thumbnail;

    private ImageDialogPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(model.getUrlFullResolution()).thenReturn(URL_FULL_RESOLUTION);
        Mockito.when(model.getThumbnail()).thenReturn(thumbnail);
        presenter=new ImageDialogPresenter(view,model,onAcceptClickListener);
        Mockito.verify(view).display(URL_FULL_RESOLUTION);
        Mockito.verify(model).getUrlFullResolution();
    }

    @Test
    public void onAcceptClickPressedEvent() {
        presenter.onAcceptClickPressedEvent(acceptClickPressedEvent);
        Mockito.verify(onAcceptClickListener).onAcceptAvatar(thumbnail);
        Mockito.verify(view).dismiss();
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(onAcceptClickListener);
    }

    @Test
    public void onCancelClickPressedEvent() {
        presenter.onCancelClickPressedEvent(onCancelClickPressedEvent);
        Mockito.verify(view).dismiss();
        Mockito.verifyNoMoreInteractions(view);
    }
}
