public @WebServlet("/contacts")
public class ContactServlet
        extends HttpServlet {

    private ContactDAO contactDAO;

    @Override
    public void init() {

        contactDAO =
                new ContactDAOImpl(
                        DBUtil.getDataSource());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String search =
                    request.getParameter("search");

            List<Contact> contacts;

            if (search != null &&
                    !search.isBlank()) {

                contacts =
                        contactDAO.searchContacts(search);

            } else {

                contacts =
                        contactDAO.getAllContacts();
            }

            request.setAttribute(
                    "contacts",
                    contacts);

            request.getRequestDispatcher(
                    "/contact-list.jsp")
                    .forward(request,
                            response);

        } catch (Exception e) {

            throw new ServletException(e);
        }
    }
} {
    
}
