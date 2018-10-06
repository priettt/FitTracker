package com.pps.globant.fittracker.mvp.model.DataBase;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.squareup.otto.Bus;

import java.util.List;

public class UsersRepository {
    private static Bus bus;
    private UserDao userDao;

    public UsersRepository(UserDao userDao, Bus bus) {
        this.userDao = userDao;
        this.bus = bus;
    }

    public void insert(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateAsyncTask(userDao).execute(user);
    }

    public void getById(long id) {
        new GetByIdAsyncTask(userDao).execute(id);
    }

    public void getByUsername(String username) {
        new GetByUsernameAsyncTask(userDao).execute(username);
    }

    public void getBySocialNetworkId(String socialNetworkId) {
        new GetBySocialNetworkIdAsyncTask(userDao).execute(socialNetworkId);
    }

    public void delete(User user) {
        new DeleteAsyncTask(userDao).execute(user);
    }

    public void getByUsernameAndPassword(String username, String password) {
        new GetByUsernameAndPasswordAsyncTask(userDao).execute(username, password);

    }

    public void clearDatabase() {
        new ClearDatabaseAsyncTask(userDao).execute();
    }

    private class InsertAsyncTask extends AsyncTask<User, Void, Long> {

        private UserDao asyncTaskDao;

        InsertAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final User... params) {
            return asyncTaskDao.insert(params[0]);
        }

        @Override
        protected void onPostExecute(Long l) {
            bus.post(new InsertUserIntoDataBaseCompleted(l));
        }
    }

    private class UpdateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao asyncTaskDao;

        UpdateAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            asyncTaskDao.update(params[0]);
            return null;
        }

        protected void onPostExecute(Void v) {
            bus.post(new UpdatingUserFromDataBaseCompleted());
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

    private class GetByIdAsyncTask extends AsyncTask<Long, Void, List<User>> {

        private UserDao asyncTaskDao;

        GetByIdAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<User> doInBackground(final Long... params) {
            return asyncTaskDao.findById(params[0]);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            bus.post(new FetchingUserFromDataBaseCompleted(!users.isEmpty() ? users.get(0) : null));
        }
    }

    private class GetByUsernameAsyncTask extends AsyncTask<String, Void, List<User>> {

        private UserDao asyncTaskDao;

        GetByUsernameAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<User> doInBackground(final String... params) {
            return asyncTaskDao.findByUsername(params[0]);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            bus.post(new FetchingUserFromDataBaseCompleted(!users.isEmpty() ? users.get(0) : null));
        }
    }

    private class GetByUsernameAndPasswordAsyncTask extends AsyncTask<String, Void, List<User>> {

        private UserDao asyncTaskDao;

        GetByUsernameAndPasswordAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<User> doInBackground(final String... params) {
            return asyncTaskDao.findByUsernameAndPassword(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            bus.post(new FetchingUserFromDataBaseCompleted(!users.isEmpty() ? users.get(0) : null));
        }
    }

    private class GetBySocialNetworkIdAsyncTask extends AsyncTask<String, Void, List<User>> {

        private UserDao asyncTaskDao;

        GetBySocialNetworkIdAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<User> doInBackground(final String... params) {
            return asyncTaskDao.findBySocialNetworkId(params[0]);
        }

        @Override
        protected void onPostExecute(List<User> users) {
            bus.post(new FetchingUserFromDataBaseCompleted(!users.isEmpty() ? users.get(0) : null));
        }
    }

    public class FetchingUserFromDataBaseCompleted {

        public final @Nullable User user;

        public FetchingUserFromDataBaseCompleted(@Nullable User user) {
            this.user = user;
        }
    }

    public class DeletingUserFromDataBaseCompleted {
    }

    public class InsertUserIntoDataBaseCompleted {
        public long id;

        public InsertUserIntoDataBaseCompleted(long id) {
            this.id = id;
        }
    }

    public class UpdatingUserFromDataBaseCompleted {
    }

    private class ClearDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao asyncTaskDao;

        ClearDatabaseAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            UserRoomDataBase.emptyDatabase();
            return null;
        }
    }
}
