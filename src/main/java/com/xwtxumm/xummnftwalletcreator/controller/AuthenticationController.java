package com.xwtxumm.xummnftwalletcreator.controller;

import com.xwtxumm.xummnftwalletcreator.impl.Action;
import java.io.*;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AuthenticationController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(false);
        //IF session is null or getAttribute object is null then redo the process if not do checkAuthorization.
        if (httpSession == null || Objects.requireNonNull(httpSession).getAttribute("xumm") == null) {
            httpSession = request.getSession();
            httpSession.setMaxInactiveInterval(180);
            Action x = new Action();
            httpSession.setAttribute("xumm", x);

            //Set Primary URL for XUMM APP/WEB Redirect.
            if(request.getLocalName().contains("localhost")){
                x.setURL(request.getRequestURL().toString());
            }else{
                x.setURL("https://xls19d-xumm-dev.herokuapp.com/");
            }
            // Set Device type of Payload Generator
            x.setDeviceType(request);
            //Get xApp OTT and other user info.
            StringBuffer requestURL = request.getRequestURL();
            requestURL.append("?load=true").append(request.getQueryString());
            //Process Authentication by doing a XUMM Sign-In
            x.processAuthentication();
            //Redirect to XUMM generated signin
            response.sendRedirect(x.getSignInURL());
        }else{
            Action x = (Action)httpSession.getAttribute("xumm");
            x.checkAuthentication();
            RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(request, response);
        }
    }

}
