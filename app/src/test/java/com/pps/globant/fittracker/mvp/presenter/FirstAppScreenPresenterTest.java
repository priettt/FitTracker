package com.pps.globant.fittracker.mvp.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FirstAppScreenPresenterTest {

    @Mock
    private AvatarsPresenter avatarsPresenter;
    @Mock
    private RunTrackerPresenter runTrackerPresenter;
    @Mock
    private StepCounterPresenter stepCounterPresenter;

    private FirstAppScreenPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter=new FirstAppScreenPresenter(runTrackerPresenter,avatarsPresenter,stepCounterPresenter);
    }

    @Test
    public void AccesLocationPermissionGranted() {
        presenter.AccesLocationPermissionGranted();
        Mockito.verify(runTrackerPresenter).accesLocationPermisionGranted();
        Mockito.verifyNoMoreInteractions(runTrackerPresenter);
    }
}
