<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <form action="home" method="post">
        <div class="input-group mb-3">
            <input type="text" class="form-control" required=""placeholder="Điểm bắt đầu" name="startStation" value="${startStation}">
            <input type="text" class="form-control" required="" placeholder="Điểm kết thúc" name="endStation" value="${endStation}">
            <button class="btn btn-outline-primary" type="submit">Search</button>
        </div>
    </form>
    <div class="row row-cols-1 row-cols-md-3 g-4" style="height: 80vh; overflow-y: auto;">
        <c:forEach var="bus" items="${routes}">
            <div class="col">
                <div class="card h-100" style="max-height:  150px">
                    <div class="card-body">
                        <strong > <a href="bus-details?id=${bus.id}" style="font-size: x-large" class="card-title">Xe Bus ${bus.name}</a></strong>
                        <p class="card-text">${bus.startPoint} - ${bus.endPoint}</p>
                        <p class="card-text">
                            <i class="bi bi-clock"></i> ${bus.startTime} -${bus.endTime}
                            <span style="float: right" class="badge bg-primary">${bus.price}</span>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>



    </div>
</main>
<%@ include file="./view/footer.jsp"%>
