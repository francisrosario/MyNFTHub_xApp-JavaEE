package com.xwtxumm.xummnftwalletcreator;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;


import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

public class doAuthorization extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession();
        if (httpSession.isNew()) {
            httpSession = request.getSession();
            xumm x = new xumm();
            httpSession.setAttribute("xumm", x);

            //Detect If Smartphone / Personal computer
            UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
            ReadableUserAgent agent = parser.parse(request.getHeader("User-Agent"));
            x.setDeviceType(agent.getDeviceCategory().getCategory().getName());

            x.processAuthorization();
            response.sendRedirect(x.getLoginURL_Redirect());
        }else{
            xumm x = (xumm)httpSession.getAttribute("xumm");
            x.checkAuthorization();
            RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(request, response);
        }

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "test" + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }

}
