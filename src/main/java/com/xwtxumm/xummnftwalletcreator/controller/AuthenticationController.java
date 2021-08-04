package com.xwtxumm.xummnftwalletcreator.controller;

import com.xwtxumm.xummnftwalletcreator.impl.Action;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import java.io.*;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AuthenticationController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null || Objects.requireNonNull(httpSession).getAttribute("xumm") == null) {
            httpSession = request.getSession();
            httpSession.setMaxInactiveInterval(180);
            Action x = new Action();
            httpSession.setAttribute("xumm", x);

            //Set Primary URL for XUMM APP/WEB Redirect.
            if(request.getLocalName().contains("localhost")){
                x.setURL(request.getRequestURL().toString());
            }else{
                x.setURL("https://"+request.getRemoteHost());
            }

            //Detect If Smartphone / Personal computer
            UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
            ReadableUserAgent agent = parser.parse(request.getHeader("User-Agent"));
            x.setDeviceType(agent.getDeviceCategory().getCategory().getName());

            //Get xApp OTT and other user info.
            StringBuffer requestURL = request.getRequestURL();
            requestURL.append("?load=true").append(request.getQueryString());

            x.processAuthentication();
            response.sendRedirect(x.getSignInURL());
        }else{
            Action x = (Action)httpSession.getAttribute("xumm");
            x.checkAuthentication();
            RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(request, response);
        }
    }
}
