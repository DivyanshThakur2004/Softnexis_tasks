<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Page Not Found – Personal Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-brand">
        <span class="brand-icon">👤</span>
        <span class="brand-text">Personal Portal</span>
    </div>
</nav>
<main class="container">
    <div class="empty-state" style="padding-top: 6rem;">
        <div class="empty-icon">🔍</div>
        <h3 style="font-size:1.5rem;">404 – Page Not Found</h3>
        <p>The page you're looking for doesn't exist or has been moved.</p>
        <br>
        <a href="${pageContext.request.contextPath}/contacts" class="btn btn-primary">
            Go to Contacts
        </a>
    </div>
</main>
</body>
</html>
