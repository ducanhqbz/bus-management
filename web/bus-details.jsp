<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <div class="row">
        <div class="col">
            <h1>Chi tiết chuyến xe</h1>
            <hr>
            <c:if test="${not empty route}">
                <div class="card">
                    <div class="card-body">
                        <p class="card-text">Bus: <strong> ${route.name}</strong></p>
                        <p class="card-text">Giá: <strong> ${route.price} đồng</strong></p>
                        <p class="card-text">Tuyến:<strong> ${route.startPoint}</strong> đến <strong>${route.endPoint}</strong></p>
                        <p class="card-text">Bắt đầu:<strong> ${route.startTime}</strong></p>
                        <p class="card-text">Kết thúc<strong> ${route.startTime}</strong></p>
                        <p class="card-text">Tần số: <strong> ${route.frequency} phút/chuyến</strong></p>
                        <hr>
                        <h5 class="card-title">Danh sách các trạm dừng</h5>
                        <strong style="color: red"> ${route.startPoint}</strong> -
                        <c:forEach var="station" items="${station}">
                            <strong>${station.stationName}</strong> -
                        </c:forEach>
                            <strong style="color: red"> ${route.endPoint}</strong>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty route}">
                <p>No route found with the specified ID.</p>
            </c:if>
        </div>
    </div>
</main>

<%@ include file="./view/footer.jsp"%>
