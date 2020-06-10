package com.demo.state.blog;

/**
 * @author: Maniac Wen
 * @create: 2020/6/2
 * @update: 8:08
 * @version: V1.0
 * @detail:
 **/
public abstract class UserState {
    protected AppContext appContext;
    public AppContext getAppContext() {
        return appContext;
    }
    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }
    public abstract void favorite();
    public abstract void comment(String comment);
}
