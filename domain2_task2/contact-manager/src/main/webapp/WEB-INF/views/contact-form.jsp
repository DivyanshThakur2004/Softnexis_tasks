<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Contact – Personal Portal</title>
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

<!-- ── Page content ───────────────────────────────────────────────────────── -->
<main class="container container--narrow">

    <!-- Breadcrumb -->
    <nav class="breadcrumb" aria-label="Breadcrumb">
        <a href="${pageContext.request.contextPath}/contacts">← Back to Contacts</a>
    </nav>

    <div class="form-card">
        <div class="form-card__header">
            <div class="form-card__icon">➕</div>
            <div>
                <h1 class="form-card__title">Add New Contact</h1>
                <p class="form-card__subtitle">Fill in the details below. Name and Email are required.</p>
            </div>
        </div>

        <%-- Global validation error banner (shown when ANY field has an error) --%>
        <c:if test="${not empty errors}">
            <div class="alert alert-error" role="alert">
                <span class="alert-icon">⚠️</span>
                Please fix the highlighted fields and try again.
            </div>
        </c:if>

        <%-- POST goes to /contacts/new; AddContactServlet handles it --%>
        <form
            method="post"
            action="${pageContext.request.contextPath}/contacts/new"
            id="contactForm"
            novalidate
        >

            <!-- ── Name ─────────────────────────────────────────────────── -->
            <div class="form-group <c:if test='${not empty errors.name}'>has-error</c:if>">
                <label for="name" class="form-label">
                    Full Name <span class="required">*</span>
                </label>
                <input
                    type="text"
                    id="name"
                    name="name"
                    class="form-input"
                    placeholder="e.g. Jane Doe"
                    value="<c:out value='${formData.name}'/>"
                    maxlength="50"
                    autocomplete="name"
                    autofocus
                >
                <c:if test="${not empty errors.name}">
                    <span class="form-error" id="nameError">
                        <c:out value="${errors.name}"/>
                    </span>
                </c:if>
                <span class="form-hint" id="nameHint">Between 2 and 50 characters</span>
            </div>

            <!-- ── Email ─────────────────────────────────────────────────── -->
            <div class="form-group <c:if test='${not empty errors.email}'>has-error</c:if>">
                <label for="email" class="form-label">
                    Email Address <span class="required">*</span>
                </label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    class="form-input"
                    placeholder="e.g. jane@example.com"
                    value="<c:out value='${formData.email}'/>"
                    maxlength="100"
                    autocomplete="email"
                >
                <c:if test="${not empty errors.email}">
                    <span class="form-error" id="emailError">
                        <c:out value="${errors.email}"/>
                    </span>
                </c:if>
            </div>

            <!-- ── Phone ─────────────────────────────────────────────────── -->
            <div class="form-group <c:if test='${not empty errors.phone}'>has-error</c:if>">
                <label for="phone" class="form-label">
                    Phone Number
                    <span class="optional">(optional)</span>
                </label>
                <input
                    type="tel"
                    id="phone"
                    name="phone"
                    class="form-input"
                    placeholder="e.g. 9876543210"
                    value="<c:out value='${formData.phone}'/>"
                    maxlength="15"
                    autocomplete="tel"
                >
                <c:if test="${not empty errors.phone}">
                    <span class="form-error" id="phoneError">
                        <c:out value="${errors.phone}"/>
                    </span>
                </c:if>
                <span class="form-hint">10-digit number if provided</span>
            </div>

            <!-- ── Actions ───────────────────────────────────────────────── -->
            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/contacts"
                   class="btn btn-secondary">
                    Cancel
                </a>
                <button type="submit" class="btn btn-primary" id="submitBtn">
                    <span class="btn-text">Save Contact</span>
                    <span class="btn-spinner" id="spinner" hidden>⏳</span>
                </button>
            </div>

        </form>
    </div>
</main>

<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script>
    // ── Client-side validation (mirrors server rules) ───────────────────────
    const form       = document.getElementById('contactForm');
    const submitBtn  = document.getElementById('submitBtn');
    const spinner    = document.getElementById('spinner');

    // Focus the first field that has a server-side error
    const firstError = document.querySelector('.has-error .form-input');
    if (firstError) firstError.focus();

    // Live validation helpers
    function showError(inputEl, message) {
        const group = inputEl.closest('.form-group');
        group.classList.add('has-error');
        let errSpan = group.querySelector('.form-error');
        if (!errSpan) {
            errSpan = document.createElement('span');
            errSpan.className = 'form-error';
            inputEl.insertAdjacentElement('afterend', errSpan);
        }
        errSpan.textContent = message;
    }

    function clearError(inputEl) {
        const group = inputEl.closest('.form-group');
        group.classList.remove('has-error');
        const errSpan = group.querySelector('.form-error');
        if (errSpan) errSpan.textContent = '';
    }

    // Inline validation on blur
    document.getElementById('name').addEventListener('blur', function () {
        const v = this.value.trim();
        if (!v)                        showError(this, 'Please enter valid name');
        else if (v.length < 2 || v.length > 50) showError(this, 'Name must be between 2 and 50 characters');
        else                           clearError(this);
    });

    document.getElementById('email').addEventListener('blur', function () {
        const v = this.value.trim();
        const re = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        if (!v || !re.test(v)) showError(this, 'Invalid email address');
        else                   clearError(this);
    });

    document.getElementById('phone').addEventListener('blur', function () {
        const v = this.value.replace(/[\s\-().+]/g, '');
        if (v && !/^\d{10}$/.test(v)) showError(this, 'Use 10-digit format');
        else                          clearError(this);
    });

    // Show loading spinner & disable button on valid submit
    form.addEventListener('submit', function (e) {
        // Run a quick pass before showing spinner
        const hasErrors = document.querySelectorAll('.has-error').length > 0;
        if (!hasErrors) {
            submitBtn.disabled = true;
            document.querySelector('.btn-text').textContent = 'Saving…';
            spinner.hidden = false;
        }
    });
</script>
</body>
</html>
