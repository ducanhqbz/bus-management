<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Hola Bus</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <style>
            body {
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            main {
                flex: 1;
            }
        </style>
    </head>

    <body class="bg-light d-flex flex-column">
        <header>
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg bg-body">
                <div class="container-fluid">
                    <button
                        data-mdb-collapse-init
                        class="navbar-toggler"
                        type="button"
                        data-mdb-target="#navbarExample01"
                        aria-controls="navbarExample01"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                        >
                        <i class="fas fa-bars"></i>
                    </button>

                    <!-- Logo -->
                    <a class="navbar-brand" href="home">
                        <img src="data:image/jpeg;base64,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+FjSkp+sHUICAgICAgICAgICAgICAgoRlALQTlBRzGPBa9ocD1BGURpCy2hpW/Ep4m/NYB7kNIRy6cPdN3KqlqZ6JzJpnl8jopC3c49Sta5r18Sytw+O06zDXP4U6cJO0Vjf+uSrdzdTtMXoj4U6baQX/C39xmITuLnaYvTfWvR+n7Xg0lsg3/LeNx+lZ2yWt5lrXFSviGfeJGQ2qtfIdrWwPJP6pVaxrMLW2iUZ4RM26Jp39PKTzOGfnkBa5/zZcP8AgmqxbiAgICAgICAgICAgICAgIPDy7o0j1oIfdtYXaxzOZdNM1UkAPKqon+VjI9JG3LfXj61rXHFvEsLZZr5hgRcX9OPOJIa9h7cxMIHscr9tkV7rGzI+KWln9amob407vqVe3ud1i9rn8Z2k+24yD9mk/dToXT3OP2tScU9JM/n0rvCBw94ToXT3FGO7i1p1ziylhuFU7sEUIP2sp29/0juaeN1uXiPXS8rZpG71GRyc6F+PoafepjDH3aEdxP1WWtq2661mz4FUULLPbZHfjnPbtcW+BO4nuwArx0se+uss562XaY0h0ez26C022nt9KCIYIwxuepx2lc1pm06y661isaQzlCwgICAgICAgICAgICAgICCmEDAQWJ6KkqM/CKWCXPXykYd71MTMeETWJ8ww3acsTjl1ltxPfSs+5Tz29q9Onp6Zp6yR/m7Pb2/NpWD6k5re08lfTJit1DD+ao6dnzYmj6lHNPs5K+mQ1oaMNAA9AUJiNDARJgDoEFcICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICD//2Q==" alt="Hola Bus" style="height: 80px; width: auto; margin-right: 5px;">
                    </a>

                    <!-- Navigation links -->
                    <div class="collapse navbar-collapse" id="navbarExample01">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <c:if test="${account.role eq 'employee'}">
                                <li class="nav-item">
                                    <a class="nav-link" href="bus-management">Quản lý xe bus</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="route-management">Quản lý tuyến</a>
                                </li>
                            </c:if>
                            <c:if test="${account.role eq 'admin'}">
                                <li class="nav-item">
                                    <a class="nav-link" href="user-management">Quản lý tài khoản</a>
                                </li>
                            </c:if>
                            <c:if test="${account != null}">
                                <li class="nav-item">
                                    <a class="nav-link" href="logout">Đăng xuất</a>
                                </li>
                            </c:if>
                            <c:if test="${account == null}">
                                <li class="nav-item">
                                    <a class="nav-link" href="login">Đăng Nhập</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- Navbar -->
        </header>

