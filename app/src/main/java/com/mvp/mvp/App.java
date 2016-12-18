package com.mvp.mvp;

import android.app.Application;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/**
 * Created by TEAM on 12/18/2016.
 * That's it
 */

public class App extends Application {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private AbstractDaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();



    }

    public AbstractDaoSession getDaoSession() {
        return daoSession;
    }
}
