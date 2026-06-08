# 📇 Contact Manager Module

**Task 2 – Servlet-Based CRUD Application**  
*Part of the Java Full Stack Web Development course project*

---

## What is this?

This is the second building block of the Personal Portal project. Task 1 gave us a static site; this module brings it to life by adding a fully dynamic **Contact Manager** built with Java Servlets, JSP, and JSTL — the classic Java EE web stack.

You can add contacts through a web form, see them listed in a searchable table, and delete them — all without touching a database (that comes in Task 3). Everything lives in memory for now, which keeps the focus squarely on the Servlet/JSP workflow.

---

## What's built

| Feature | Details |
|---|---|
| **Contact list** | Responsive table showing name, email, phone, and date added |
| **Add contact form** | Clean form with real-time client-side validation + server-side backup |
| **Search** | Live search-as-you-type filtering by name, email, or phone |
| **Delete** | One-click removal with a confirmation prompt |
| **Flash messages** | Success toasts that auto-dismiss after 4 seconds |
| **Error pages** | Custom 404 and 500 pages instead of Tomcat's ugly defaults |
| **XSS protection** | All output goes through `<c:out>` / sanitisation before rendering |
| **PRG pattern** | POST → Redirect → GET prevents duplicate submissions on refresh |

---

## Project structure

```
contact-manager/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/portal/
    │   │   ├── model/
    │   │   │   └── Contact.java              ← JavaBean (id, name, email, phone, createdAt)
    │   │   ├── servlet/
    │   │   │   ├── ContactServlet.java        ← GET /contacts  &  GET /contacts/add
    │   │   │   ├── AddContactServlet.java     ← POST /contacts/new
    │   │   │   └── DeleteContactServlet.java  ← POST /contacts/delete
    │   │   └── util/
    │   │       ├── ValidationUtil.java        ← All validation logic (+ sanitisation)
    │   │       └── ContactStore.java          ← In-memory thread-safe contact store
    │   └── webapp/
    │       ├── index.jsp                      ← Landing page
    │       ├── css/style.css                  ← All styles in one clean file
    │       ├── js/app.js                      ← Confirm-delete, toast, search debounce
    │       └── WEB-INF/
    │           ├── web.xml
    │           └── views/
    │               ├── contact-list.jsp       ← Main contacts page
    │               ├── contact-form.jsp       ← Add contact form
    │               ├── error-404.jsp
    │               └── error-500.jsp
    └── test/
        └── java/com/portal/util/
            └── ValidationUtilTest.java        ← JUnit 5 tests for every validation rule
```

---

## How to run it

### What you need

- **Java 17** or newer
- **Maven 3.8+**
- **Apache Tomcat 10.x** (the project uses Jakarta EE 5 / `jakarta.*` packages, so Tomcat 10 is required — Tomcat 9 won't work)

### Option A — Deploy to Tomcat manually

```bash
# 1. Clone / download the project
cd contact-manager

# 2. Build the WAR
mvn clean package

# 3. Copy the WAR to Tomcat's webapps folder
cp target/contact-manager.war /path/to/tomcat/webapps/

# 4. Start Tomcat
/path/to/tomcat/bin/startup.sh    # macOS / Linux
/path/to/tomcat/bin/startup.bat   # Windows
```

Then open: **http://localhost:8080/contact-manager**

### Option B — Embedded Tomcat via Maven

```bash
mvn tomcat10:run
```

Then open: **http://localhost:8080/portal**

---

## URL map

| URL | Method | What happens |
|---|---|---|
| `/portal/` | GET | Home / landing page |
| `/portal/contacts` | GET | Contact list (supports `?q=search`) |
| `/portal/contacts/add` | GET | Show add-contact form |
| `/portal/contacts/new` | POST | Process form submission |
| `/portal/contacts/delete` | POST | Delete a contact by ID |

---

## Validation rules

The same rules are enforced on both client (JavaScript) and server (Java) — the server check always wins.

| Field | Rule | Error shown |
|---|---|---|
| Name | Required, 2–50 characters | "Please enter valid name" |
| Email | Required, must match `user@domain.tld` format | "Invalid email address" |
| Phone | Optional — if provided, must be exactly 10 digits (formatting chars stripped first) | "Use 10-digit format" |

If validation fails, the form is re-displayed with the original values preserved and errors highlighted in red beneath each field. The first invalid field gets focused automatically.

---

## Running the tests

```bash
mvn test
```

The test suite (`ValidationUtilTest.java`) covers:

- Happy-path valid inputs
- Boundary conditions (exactly 2 chars, exactly 50 chars, exactly 10 digits)
- All invalid name cases (null, blank, too short, too long)
- Valid and invalid email formats (parametrised)
- Phone with/without formatting characters
- XSS sanitisation
- All-fields-invalid giving three errors at once

---

## Design decisions worth noting

**Why POST-Redirect-GET?**  
Without it, pressing F5 after adding a contact would re-submit the form and add a duplicate. The servlet redirects to `/contacts` after a successful save, so the browser's last request becomes a plain GET.

**Why a singleton `ContactStore` instead of the HTTP session?**  
Storing the contact list in the session means each browser tab gets its own isolated list — not great for a shared portal. A singleton lives for the lifetime of the server process and is shared across all sessions. Task 3 will replace it with a database.

**Why validate on both client and server?**  
Client-side validation gives instant feedback without a round trip. Server-side validation is the actual safety net — it cannot be bypassed by disabling JavaScript or crafting a raw HTTP request.

**`<c:out>` everywhere**  
Every piece of user-supplied data is rendered through `<c:out value="..."/>`, which escapes HTML entities. This prevents stored XSS where a malicious name like `<script>alert(1)</script>` would otherwise execute in other users' browsers.

---

## What's coming in Task 3

- Swap `ContactStore` for a real JDBC/JPA database layer
- Persistent storage across server restarts
- Edit (update) contact functionality
- Pagination for large contact lists

---

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Web framework | Jakarta Servlet 5.0 + JSP 3.1 |
| Templating | JSTL 3.0 (Jakarta Tags) |
| Build tool | Maven 3.8 |
| Server | Apache Tomcat 10.x |
| Testing | JUnit 5 (Jupiter) |
| Front-end | Vanilla CSS + JS (no frameworks) |

---

*Built as part of the Java Full Stack Web Development course — Task 2.*
