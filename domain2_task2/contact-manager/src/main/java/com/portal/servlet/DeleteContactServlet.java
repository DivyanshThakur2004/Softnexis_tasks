package com.portal.servlet;

import com.portal.util.ContactStore;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Handles deletion of a contact.
 *
 * Expects a POST with a hidden "id" parameter (consistent with REST-ish
 * HTML forms which only support GET and POST).
 *
 * Flow: delete → flash → PRG back to /contacts
 */
@WebServlet(name = "DeleteContactServlet", urlPatterns = {"/contacts/delete"})
public class DeleteContactServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id != null && !id.trim().isEmpty()) {
            boolean removed = ContactStore.getInstance().deleteById(id.trim());
            if (removed) {
                request.getSession().setAttribute("flashMessage",
                        "Contact removed successfully.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/contacts");
    }
}
