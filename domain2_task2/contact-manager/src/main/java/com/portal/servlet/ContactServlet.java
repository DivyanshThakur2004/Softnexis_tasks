package com.portal.servlet;

import com.portal.model.Contact;
import com.portal.util.ContactStore;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Handles the read side of the Contact Manager:
 *
 *   GET /contacts        → display the contact list (with optional search)
 *   GET /contacts/add    → show the blank "add contact" form
 *
 * All rendering is delegated to JSP views under /WEB-INF/views/.
 */
@WebServlet(name = "ContactServlet", urlPatterns = {"/contacts", "/contacts/add"})
public class ContactServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/contacts/add".equals(path)) {
            showAddForm(request, response);
        } else {
            showContactList(request, response);
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void showContactList(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException {

        ContactStore store = ContactStore.getInstance();

        // Support search-as-you-type from the list page
        String searchQuery = request.getParameter("q");
        List<Contact> contacts = store.search(searchQuery);

        request.setAttribute("contacts",    contacts);
        request.setAttribute("totalCount",  store.count());
        request.setAttribute("searchQuery", searchQuery != null ? searchQuery : "");

        // Surface any one-time flash message left by AddContactServlet after redirect
        String flash = (String) request.getSession().getAttribute("flashMessage");
        if (flash != null) {
            request.setAttribute("flashMessage", flash);
            request.getSession().removeAttribute("flashMessage");
        }

        dispatch(request, response, "/WEB-INF/views/contact-list.jsp");
    }

    private void showAddForm(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        // If we arrived here after a failed validation the session will carry
        // the form data and errors back so we can re-populate the fields.
        transferSessionAttributeToRequest(request, "formData");
        transferSessionAttributeToRequest(request, "errors");

        dispatch(request, response, "/WEB-INF/views/contact-form.jsp");
    }

    /** Moves a session attribute to request scope (and removes it from session). */
    private void transferSessionAttributeToRequest(HttpServletRequest request, String key) {
        Object value = request.getSession().getAttribute(key);
        if (value != null) {
            request.setAttribute(key, value);
            request.getSession().removeAttribute(key);
        }
    }

    private void dispatch(HttpServletRequest request,
                          HttpServletResponse response,
                          String viewPath)
            throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
