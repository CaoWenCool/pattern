package com.demo.chain.datacheck;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.util.StringUtils;

/**
 * @author: Maniac Wen
 * @create: 2020/5/23
 * @update: 7:30
 * @version: V1.0
 * @detail:
 **/
public class UserService {
    public void login(String name,String pwd){
//       Handler emptyHandler = new ValidateEmptyHandler();
//       Handler existsHandler = new ValidateExistsHandler();
//       Handler authHandler = new AuthHandler();
//       emptyHandler.next(existsHandler);
//       existsHandler.next(authHandler);
//       emptyHandler.doHandler(new User(name,pwd));
        Handler.Builder builder = new Handler.Builder();
        builder.addHandler(new ValidateEmptyHandler())
                .addHandler(new ValidateExistsHandler())
                .addHandler(new AuthHandler());
        builder.build().doHandler(new User(name,pwd));
    }
}
