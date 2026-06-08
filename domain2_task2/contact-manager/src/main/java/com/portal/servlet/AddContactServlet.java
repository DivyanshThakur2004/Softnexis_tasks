package com.portal.servlet;

import com.portal.model.Contact;
import com.portal.util.ContactStore;
import com.portal.util.ValidationUtil;
import com.portal.util.ValidationUtil.ValidationResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles POST /contacts – processes the "Add Contact" form submission.
 *
 * Flow:
 *   1. Read & sanitise raw form parameters
 *   2. Run server-side validation
 *   3a. On failure  → stash errors + original values in session, redirect back to form
 *   3b. On success  → persist contact, stash success flash in session, redirect to list
 *
 * Using POST-Redirect-GET (PRG) pattern prevents duplicate submissions on F5.
 */
@WebServlet(name = "AddContactServlet", urlPatterns = {"/contacts/new"})
public class AddContactServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ── 1. Read raw values ──────────────────────────────────────────────
        String rawName  = request.getParameter("name");
        String rawEmail = request.getParameter("email");
        String rawPhone = request.getParameter("phone");

        // ── 2. Validate ─────────────────────────────────────────────────────
        ValidationResult result = ValidationUtil.validateContact(rawName, rawEmail, rawPhone);

        HttpSession session = request.getSession();

        if (!result.isValid()) {
            // Stash errors and original input so the form can be re-populated
            session.setAttribute("errors", result.getErrors());

            Map<String, String> formData = new HashMap<>();
            formData.put("name",  rawName  != null ? rawName  : "");
            formData.put("email", rawEmail != null ? rawEmail : "");
            formData.put("phone", rawPhone != null ? rawPhone : "");
            session.setAttribute("formData", formData);

            // PRG – redirect back to the form
            response.sendRedirect(request.getContextPath() + "/contacts/add");
            return;
        }

        // ── 3. Sanitise & persist ───────────────────────────────────────────
        Contact contact = new Contact(
                ValidationUtil.sanitise(rawName),
                ValidationUtil.sanitise(rawEmail),
                ValidationUtil.sanitise(rawPhone)
        );

        ContactStore.getInstance().add(contact);

        // ── 4. Flash success message and redirect to list ───────────────────
        session.setAttribute("flashMessage",
                "Contact "" + contact.getName() + "" was added successfully!");

        response.sendRedirect(request.getContextPath() + "/contacts");
    }

    /**
     * Guard against direct GET requests to /contacts/new.
     * Redirect to the form instead of showing a blank/broken page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/contacts/add");
    }
}
