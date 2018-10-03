package com.pps.globant.fittracker.mvp.model.DataBase;

import android.os.AsyncTask;

import com.squareup.otto.Bus;

import java.util.List;


public class UsersRepository {
    private UserDao userDao;
    private static Bus bus;

    public UsersRepository(UserDao userDao, Bus bus) {
        this.userDao = userDao;
        this.bus = bus;
    }

    public void insert(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    public void get(String name) {
        new GetAsyncTask(userDao).execute(name);
    }

    public void delete(User user) {
        new DeleteAsyncTask(userDao).execute(user);
    }

    private class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao asyncTaskDao;

        InsertAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao asyncTaskDao;

        DeleteAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }

        protected void onPostExecute(Void v) {
            bus.post(new DeletingUserFromDataBaseCompleted());
        }
    }

    private class GetAsyncTask extends AsyncTask<String, Void, List<User>> {

        private UserDao asyncTaskDao;

        GetAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<User> doInBackground(final String... params) {
            return asyncTaskDao.findByName(params[0]);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            bus.post(new FetchingUserFromDataBaseCompleted(!users.isEmpty()?users.get(0):null));
        }
    }

    public class FetchingUserFromDataBaseCompleted {

        public final User user;

        public FetchingUserFromDataBaseCompleted(User user) {
            this.user = user;
        }
    }

    public class DeletingUserFromDataBaseCompleted {
    }

}
