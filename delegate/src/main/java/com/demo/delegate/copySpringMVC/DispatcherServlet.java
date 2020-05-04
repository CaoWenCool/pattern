package com.demo.delegate.copySpringMVC;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * @author: Maniac Wen
 * @create: 2020/5/4
 * @update: 8:37
 * @version: V1.0
 * @detail:
 **/
public class DispatcherServlet extends HttpServlet {
    private Map<String,Method> handlerMapping = new HashMap<String,Method>();
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doDispatch(req,res);
    }
    @Override
    public void init() throws ServletException {
        try {
            handlerMapping.put("/web/getMemberById.json",MemberController.class.getMethod("getMemberById",new Class[]{String.class}));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    private void doDispatch(HttpServletRequest req, HttpServletResponse res){
        String url = req.getRequestURI();
        Method method = handlerMapping.get(url);
    }
}
