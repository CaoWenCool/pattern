package com.demo.state.blog;

/**
 * @author: Maniac Wen
 * @create: 2020/6/2
 * @update: 8:13
 * @version: V1.0
 * @detail:
 **/
public class AppContext {
    public static final UserState STATE_LOGIN = new LoginState();
    public static final UserState STATE_UNLOGIN = new UnLoginState();
    private UserState currentState = STATE_UNLOGIN;
    {
        STATE_LOGIN.setAppContext(this);
        STATE_UNLOGIN.setAppContext(this);
    }
    public void setState(UserState state){
        this.currentState = state;
        this.currentState.setAppContext(this);
    }
    public UserState getCurrentState() {
          return this.currentState;
    }
    public void favorite(){
        this.currentState.favorite();
    }
    public void comment(String comment){
        this.currentState.comment(comment);
    }
}
