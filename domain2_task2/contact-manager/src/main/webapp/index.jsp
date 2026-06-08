<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .hero {
            text-align: center;
            padding: 5rem 2rem;
        }
        .hero-icon {
            font-size: 4rem;
            display: block;
            margin-bottom: 1.2rem;
        }
        .hero h1 {
            font-size: 2.2rem;
            font-weight: 800;
            margin-bottom: .7rem;
        }
        .hero p {
            color: var(--text-muted);
            font-size: 1.05rem;
            max-width: 460px;
            margin: 0 auto 2.2rem;
        }
        .hero-cta {
            display: inline-flex;
            align-items: center;
            gap: .6rem;
            background: var(--primary);
            color: #fff;
            padding: .85rem 2rem;
            border-radius: 99px;
            font-weight: 700;
            font-size: 1rem;
            text-decoration: none;
            box-shadow: 0 4px 14px rgba(79,70,229,.35);
            transition: background .2s, transform .2s;
        }
        .hero-cta:hover {
            background: var(--primary-hover);
            transform: translateY(-2px);
            text-decoration: none;
            color: #fff;
        }
    </style>
</head>
<body>

<nav class="navbar">
    <div class="nav-brand">
        <span class="brand-icon">👤</span>
        <span class="brand-text">Personal Portal</span>
    </div>
    <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/" class="active">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/contacts">Contacts</a></li>
    </ul>
</nav>

<main class="container">
    <div class="hero">
        <span class="hero-icon">📇</span>
        <h1>Your Contact Manager</h1>
        <p>
            A clean, fast way to keep track of the people that matter.
            Add contacts, search instantly, and manage everything from one place.
        </p>
        <a href="${pageContext.request.contextPath}/contacts" class="hero-cta">
            Open Contacts →
        </a>
    </div>
</main>

</body>
</html>
