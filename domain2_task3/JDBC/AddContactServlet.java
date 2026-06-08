@WebServlet("/addContact")
public class AddContactServlet
        extends HttpServlet {

    private ContactDAO contactDAO;

    @Override
    public void init() {

        contactDAO =
                new ContactDAOImpl(
                        DBUtil.getDataSource());
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Contact contact =
                    new Contact(
                            request.getParameter("name"),
                            request.getParameter("email"),
                            request.getParameter("phone"));

            contactDAO.addContact(contact);

            response.sendRedirect("contacts");

        } catch (SQLException e) {

            if ("23505".equals(
                    e.getSQLState())) {

                request.setAttribute(
                        "error",
                        "Email already exists");
            }

            request.getRequestDispatcher(
                    "/contact-form.jsp")
                    .forward(request,
                            response);
        }
    }
}