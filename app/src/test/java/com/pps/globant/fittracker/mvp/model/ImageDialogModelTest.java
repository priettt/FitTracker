package com.pps.globant.fittracker.mvp.model;

import com.pps.globant.fittracker.model.avatars.Thumbnail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class ImageDialogModelTest {

    private static final String EXAMPLE_STRING = "ABC";

    @Mock
    Thumbnail thumbnail;

    ImageDialogModel imageDialogModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageDialogModel = new ImageDialogModel(thumbnail);
        Mockito.when(thumbnail.toUrlRequest(Thumbnail.FULL_DETAIL)).thenReturn(EXAMPLE_STRING);
    }

    @Test
    public void getUrlFullResolution() {
        final String response = imageDialogModel.getUrlFullResolution();
        Mockito.verify(thumbnail).toUrlRequest(Thumbnail.FULL_DETAIL);
        assertEquals(response, EXAMPLE_STRING);
        Mockito.verifyNoMoreInteractions(thumbnail);
    }

    @Test
    public void getThumbnail() {
        final Thumbnail response = imageDialogModel.getThumbnail();
        assertEquals(thumbnail, response);
    }
}
