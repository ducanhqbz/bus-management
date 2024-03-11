<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="./view/header.jsp"%>

<main class="container py-5">

    <h2>Quản lý xe bus</h2>
    <c:if test="${not empty param.msg}">
        <div class="alert alert-success" role="alert">
            ${param.msg}
        </div>
    </c:if>

    <!-- Check if there is an error message -->
    <c:if test="${not empty param.err}">
        <div class="alert alert-danger" role="alert">
            ${param.err}
        </div>
    </c:if>
    <div class="row mb-12" style="margin-bottom: 30px">
        <div class="col-md-6">
            <div class="form-group">
                <form action="bus-management" method="get">
                    <label for="searchInput" class="form-label">Tìm kiếm:</label>
                    <div class="input-group" style="width: 80%">
                        <input style="width: 80%" type="text" class="form-control" name="search" placeholder="Nhập tên bus hoặc tên tuyến  đường để tìm kiếm" value="${param.search}">
                        <input type="hidden" name="page" value="${currentPage}">

                    </div>
                </form>
            </div>
        </div>

    </div>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addBusModal">
        Thêm Bus
    </button>
    
    <table class="table">
        <thead>
            <tr>
                <th>Tuyến</th>
                <th>Bus</th>
                <th>Giá</th>
                <th>Thời gian</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="route" items="${routes}">
                <tr>
                    <td>${route.startPoint} - ${route.endPoint}</td>
                    <td>${route.name}</td>
                    <td>${route.price}</td>
                    <td>${route.startTime} - ${route.endTime}</td>
                    <td>
                        <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#editRouteModal${route.id}">
                            Sửa
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <nav class="mt-3" aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <!-- Disable the "Previous" link if on the first page -->
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="?search=${param.search}&amp;page=${currentPage - 1}">Previous</a>
                </li>
            </c:if>

            <!-- Display the page numbers as links -->
            <c:forEach var="pageNum" begin="1" end="${totalPages}">
                <li class="page-item <c:if test='${pageNum == currentPage}'>active</c:if>">
                    <a class="page-link" href="?search=${param.search}&amp;page=${pageNum}">${pageNum}</a>
                </li>
            </c:forEach>

            <!-- Disable the "Next" link if on the last page -->
            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="?search=${param.search}&amp;page=${currentPage + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</main>

<!-- Add Bus Modal, Add Route Modal, Add Bus Route Info Modal -->
<!-- Add Bus Modal -->
<div class="modal fade" id="addBusModal" tabindex="-1" aria-labelledby="addBusModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addBusModalLabel">Tạo Bus</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/bus-management" method="post">
                    <label style="margin-bottom: 10px"for="busName">Tên</label><br>
                    <input class="input-group" type="text" id="busName" name="busName" required placeholder="Nhập tên bus"><br>
                    <label  style="margin-bottom: 10px"  for="busName">Giá</label><br>
                    <input class="input-group"  type="number" id="price" name="price"placeholder="Nhập giá" required><br>
                    <label  style="margin-bottom: 10px"  for="busName">Tuyến đường</label><br>
                    <div class="input-group" >
                        <select style="width: 80%" class="form-control" name="routeID" required="">
                            <option value="">Chọn tuyến đường</option>
                            <c:forEach items="${listR}" var="c">
                                <option  value="${c.id}">${c.startPoint}- ${ c.endPoint}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="hidden"  name="action" value="add"><br><br>

                    <button type="submit" class="btn btn-primary">Add Bus</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Add Route Modal -->
<div class="modal fade" id="addRouteModal" tabindex="-1" aria-labelledby="addRouteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addRouteModalLabel">Add New Route</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="add-route" method="post">
                    <label for="startPoint">Start Point:</label>
                    <input type="text" id="startPoint" name="startPoint" required><br><br>
                    <label for="endPoint">End Point:</label>
                    <input type="text" id="endPoint" name="endPoint" required><br><br>
                    <!-- Add more fields for route details as needed -->
                    <button type="submit" class="btn btn-primary">Add Route</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Add Bus Route Info Modal -->
<div class="modal fade" id="addBusRouteInfoModal" tabindex="-1" aria-labelledby="addBusRouteInfoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addBusRouteInfoModalLabel">Add Bus Route Information</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="add-bus-route-info" method="post">
                    <label for="busId">Bus ID:</label>
                    <input type="text" id="busId" name="busId" required><br><br>
                    <label for="routeId">Route ID:</label>
                    <input type="text" id="routeId" name="routeId" required><br><br>
                    <!-- Add more fields for bus route information as needed -->
                    <button type="submit" class="btn btn-primary">Add Bus Route Info</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Edit Route Modals -->
<c:forEach var="route" items="${routes}">
    <div class="modal fade" id="editRouteModal${route.id}" tabindex="-1" aria-labelledby="editRouteModalLabel${route.id}" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editRouteModalLabel${route.id}">Chỉnh sửa tuyến đường cho bus ${route.name}</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Form to edit route for the bus -->
                    <form action="${pageContext.request.contextPath}/bus-management" method="post">
                        <label style="margin-bottom: 10px"for="busName">Tên</label><br>
                        <input class="input-group" type="hidden"" name="id" value="${route.busID}">
                        <input class="input-group" type="hidden"" name="action" value="edit">
                        <input class="input-group" type="text"" name="busName" value="${route.name}" required placeholder="Nhập tên bus"><br>
                        <label  style="margin-bottom: 10px">Giá</label><br>
                        <input class="input-group"  type="number"  value="${route.price}" name="price" placeholder="Nhập giá" required><br>
                        <label  style="margin-bottom: 10px" >Tuyến đường</label><br>
                        <select style="width: 80%" class="form-control" name="routeID" required="">
                            <option value="">Chọn tuyến đường</option>
                            <c:forEach items="${listR}" var="c">
                                <option value="${c.id}" <c:if test="${c.id eq route.id}">selected</c:if>>${c.startPoint}- ${c.endPoint}</option>
                            </c:forEach>
                        </select>
                </div>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </form>
            </div>
        </div>
    </div>
</div>
</c:forEach>

<%@ include file="./view/footer.jsp"%>
