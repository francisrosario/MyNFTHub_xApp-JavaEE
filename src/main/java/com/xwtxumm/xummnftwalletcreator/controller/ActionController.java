package com.xwtxumm.xummnftwalletcreator.controller;

import com.xwtxumm.xummnftwalletcreator.impl.Action;
import org.apache.commons.io.IOUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "createnft.process", value = "/createnft.process")
@MultipartConfig
public class ActionController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession();
        if(httpSession.isNew()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/");
            dispatcher.forward(request, response);
        } else {
            Action x = (Action) httpSession.getAttribute("xumm");
            Part img = request.getPart("file");
            byte[] imageByte = IOUtils.toByteArray(img.getInputStream());
            String nftName = request.getParameter("nftName");
            String nftAuthor = request.getParameter("nftAuthor");
            String nftEmail = request.getParameter("nftEmail");
            String nftTwitter = request.getParameter("nftTwitter");
            String nftDescription = request.getParameter("nftDescription");

            x.createNFTWallet(imageByte, nftName, nftAuthor, nftEmail, nftTwitter, nftDescription);
            httpSession.invalidate();
            response.sendRedirect("https://github.com/francisrosario/NFTWalletCreator_xApp-JavaEE");
        }
    }
}
