package com.pps.globant.fittracker.mvp.model.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pps.globant.fittracker.model.avatars.Thumbnail;

import java.util.Date;

@Entity
public class User {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String email;
    @Nullable
    private String username;
    @Nullable
    private String password;
    @Nullable
    private Date birthday;
    @Nullable
    private String socialNetworkId;
    @Nullable

    private Thumbnail avatarThumbnail;
    @Nullable
    private boolean registerComplete;

    public User(@NonNull long id, @Nullable String firstName, @Nullable String lastName, @Nullable String email,
                @Nullable String username, @Nullable String password, @Nullable Date birthday, @Nullable String
                        socialNetworkId, @Nullable boolean registerComplete, @Nullable Thumbnail avatarThumbnail) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.socialNetworkId = socialNetworkId;
        this.registerComplete = registerComplete;
        this.avatarThumbnail = avatarThumbnail;
    }

    public static User getUser(@Nullable String firstName, @Nullable String lastName, @Nullable String email,
                               @Nullable String username, @Nullable String password, @Nullable Date birthday) {

        return new User(0, firstName, lastName, email, username, password, birthday, null, true, null);
    }

    public static User getUser(@Nullable String firstName, @Nullable String lastName, @Nullable String email,
                               @Nullable Date birthday, @Nullable String socialNetworkId) {

        return new User(0, firstName, lastName, email, null, null, birthday, socialNetworkId, false, null);
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nullable Date birthday) {
        this.birthday = birthday;
    }

    @Nullable
    public String getSocialNetworkId() {
        return socialNetworkId;
    }

    public void setSocialNetworkId(@Nullable String socialNetworkId) {
        this.socialNetworkId = socialNetworkId;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    public void setUsername(@Nullable String username) {
        this.username = username;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    @Nullable
    public boolean isRegisterComplete() {
        return registerComplete;
    }

    public void setRegisterComplete(@Nullable boolean registerComplete) {
        this.registerComplete = registerComplete;
    }

    @Nullable
    public Thumbnail getAvatarThumbnail() {
        return avatarThumbnail;
    }

    public void setAvatarThumbnail(@Nullable Thumbnail avatarThumbnail) {
        this.avatarThumbnail = avatarThumbnail;
    }
}