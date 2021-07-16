package com.xwtxumm.xummnftwalletcreator;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "index", value = "/")
public class Index extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        if (httpSession.isNew()) {
            //Create session if it doesn't exist
            httpSession = request.getSession();
            xumm x = new xumm();

            httpSession.setAttribute("xumm", x);
            String result = x.signIn(Facade.signIn);
            System.out.println(result);
            String URL = (String) x.getLoginURL(result);
            response.sendRedirect(URL);

        }else{
            xumm x = (xumm)httpSession.getAttribute("xumm");
        }


        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "test" + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}