<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fn"  uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contacts – Personal Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- ── Navigation ─────────────────────────────────────────────────────────── -->
<nav class="navbar">
    <div class="nav-brand">
        <span class="brand-icon">👤</span>
        <span class="brand-text">Personal Portal</span>
    </div>
    <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/contacts" class="active">Contacts</a></li>
    </ul>
</nav>

<!-- ── Page header ────────────────────────────────────────────────────────── -->
<main class="container">
    <div class="page-header">
        <div>
            <h1 class="page-title">Contacts</h1>
            <p class="page-subtitle">
                ${totalCount} contact<c:if test="${totalCount != 1}">s</c:if> on file
            </p>
        </div>
    </div>

    <!-- ── Flash message (success / info) ──────────────────────────────────── -->
    <c:if test="${not empty flashMessage}">
        <div class="alert alert-success" id="flashAlert">
            <span class="alert-icon">✅</span>
            <c:out value="${flashMessage}"/>
            <button class="alert-close" onclick="this.parentElement.remove()">×</button>
        </div>
    </c:if>

    <!-- ── Search bar ───────────────────────────────────────────────────────── -->
    <div class="toolbar">
        <form method="get" action="${pageContext.request.contextPath}/contacts"
              class="search-form" id="searchForm">
            <div class="search-wrapper">
                <span class="search-icon">🔍</span>
                <input
                    type="text"
                    name="q"
                    id="searchInput"
                    class="search-input"
                    placeholder="Search by name, email or phone…"
                    value="<c:out value='${searchQuery}'/>"
                    autocomplete="off"
                >
                <c:if test="${not empty searchQuery}">
                    <a href="${pageContext.request.contextPath}/contacts"
                       class="search-clear" title="Clear search">×</a>
                </c:if>
            </div>
        </form>
    </div>

    <!-- ── Contact table ────────────────────────────────────────────────────── -->
    <div class="card">
        <c:choose>
            <c:when test="${empty contacts}">
                <div class="empty-state">
                    <div class="empty-icon">📭</div>
                    <c:choose>
                        <c:when test="${not empty searchQuery}">
                            <h3>No results for "<c:out value='${searchQuery}'/>"</h3>
                            <p>Try a different search term or
                               <a href="${pageContext.request.contextPath}/contacts">view all contacts</a>.
                            </p>
                        </c:when>
                        <c:otherwise>
                            <h3>No contacts yet</h3>
                            <p>Add your first contact using the button below.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-wrapper">
                    <table class="contacts-table" id="contactsTable">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Added</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="contact" items="${contacts}" varStatus="loop">
                                <tr class="contact-row" data-id="${contact.id}">
                                    <td class="col-num">${loop.index + 1}</td>
                                    <td class="col-name">
                                        <div class="contact-avatar">
                                            ${fn:substring(contact.name, 0, 1)}
                                        </div>
                                        <c:out value="${contact.name}"/>
                                    </td>
                                    <td class="col-email">
                                        <a href="mailto:<c:out value='${contact.email}'/>">
                                            <c:out value="${contact.email}"/>
                                        </a>
                                    </td>
                                    <td class="col-phone">
                                        <c:choose>
                                            <c:when test="${not empty contact.phone}">
                                                <c:out value="${contact.phone}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">—</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="col-date">
                                        <c:out value="${contact.createdAt}"/>
                                    </td>
                                    <td class="col-action">
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/contacts/delete"
                                              class="delete-form"
                                              onsubmit="return confirmDelete('<c:out value='${contact.name}'/>')">
                                            <input type="hidden" name="id" value="${contact.id}">
                                            <button type="submit" class="btn-delete" title="Delete contact">
                                                🗑
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Simple pagination info -->
                <div class="table-footer">
                    Showing ${fn:length(contacts)}
                    <c:if test="${not empty searchQuery}">
                        result<c:if test="${fn:length(contacts) != 1}">s</c:if>
                        for "<c:out value='${searchQuery}'/>"
                    </c:if>
                    <c:if test="${empty searchQuery}">
                        of ${totalCount} contact<c:if test="${totalCount != 1}">s</c:if>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>
    </div><!-- /.card -->
</main>

<!-- ── Floating "Add New Contact" button ──────────────────────────────────── -->
<a href="${pageContext.request.contextPath}/contacts/add"
   class="fab" title="Add new contact" aria-label="Add new contact">
    <span class="fab-icon">+</span>
    <span class="fab-label">New Contact</span>
</a>

<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script>
    // Auto-dismiss the flash alert after 4 seconds
    const flash = document.getElementById('flashAlert');
    if (flash) {
        setTimeout(() => flash.classList.add('fade-out'), 4000);
        setTimeout(() => flash.remove(),                  4500);
    }

    // Search-as-you-type: debounce the form submission
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        let debounceTimer;
        searchInput.addEventListener('input', () => {
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(() => {
                document.getElementById('searchForm').submit();
            }, 350);
        });
    }
</script>
</body>
</html>
