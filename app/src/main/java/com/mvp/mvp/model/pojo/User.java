package com.mvp.mvp.model.pojo;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public class User {

    @Id
    public String id_review;

    @NotNull
    public String username_user;
    public String fullname_user;
    public String activity;

    public boolean is_checked;

}
