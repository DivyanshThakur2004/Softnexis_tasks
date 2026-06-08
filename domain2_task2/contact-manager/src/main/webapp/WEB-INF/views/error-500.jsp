<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Server Error – Personal Portal</title>
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
        <div class="empty-icon">⚠️</div>
        <h3 style="font-size:1.5rem;">500 – Something Went Wrong</h3>
        <p>An unexpected error occurred on the server. Please try again in a moment.</p>
        <br>
        <a href="${pageContext.request.contextPath}/contacts" class="btn btn-primary">
            Back to Safety
        </a>
    </div>
</main>
</body>
</html>
