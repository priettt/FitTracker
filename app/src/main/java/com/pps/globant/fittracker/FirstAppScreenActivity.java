package com.pps.globant.fittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import com.pps.globant.fittracker.mvp.model.AvatarsModel;
import com.pps.globant.fittracker.mvp.model.DataBase.UserRoomDataBase;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.presenter.AvatarsPresenter;
import com.pps.globant.fittracker.mvp.presenter.FirstAppScreenPresenter;
import com.pps.globant.fittracker.mvp.presenter.LoginPresenter;
import com.pps.globant.fittracker.mvp.view.AvatarsView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.Constants;
import com.pps.globant.fittracker.utils.ServiceUtils;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstAppScreenActivity extends AppCompatActivity {

    @BindView(R.id.card_avatars)
    CardView avatarsCard;
    private FirstAppScreenPresenter presenter;
    //private AvatarsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_app_screen);
        ButterKnife.bind(this);
        // presenter=new FirstScreenPresenter();
        Intent intent = getIntent();
        String userId = intent.getStringExtra(Constants.EXTRA_MESSAGE);
        Bus bus = BusProvider.getInstance();
        presenter = new FirstAppScreenPresenter(new AvatarsPresenter(new AvatarsModel(ServiceUtils.getAvatarService(), bus,
                new UsersRepository(UserRoomDataBase.getDatabase(this).userDao(), bus)), new AvatarsView
                (avatarsCard, this,
                        bus), userId));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregister();
    }
}
