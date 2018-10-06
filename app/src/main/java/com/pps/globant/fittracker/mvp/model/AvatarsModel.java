package com.pps.globant.fittracker.mvp.model;

import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.service.AvatarService;
import com.squareup.otto.Bus;

public class AvatarsModel {
    private AvatarService service;
    private Bus bus;
    private User activeUser;
    private UsersRepository usersRepository;

    public AvatarsModel(AvatarService service, Bus bus, UsersRepository usersRepository) {
        this.service = service;
        this.bus = bus;
        this.usersRepository = usersRepository;
    }

    public void getAvatarsList() {
        service.getAvatarsList();
    }

    public User getUser() {
        return activeUser;
    }

    public void setUser(User user) {
        activeUser = user;
    }

    //ROOM DATABASE

    public void getUserFromDbById() {
        usersRepository.getById(activeUser.getId());
    }

    public void getUserFromDbById(long id) {
        usersRepository.getById(id);
    }

    public void updateUserToDB() {
        usersRepository.update(activeUser);
    }

    public void setThumbnail(Thumbnail thumbnail) {
        activeUser.setAvatarThumbnail(thumbnail);
        updateUserToDB();
    }
}
