<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="./view/header.jsp"%>

<main class="container py-5">

    <h2>Quản lý tài khoản</h2>
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
                <form action="user-management" method="get">
                    <label for="searchInput" class="form-label">Tìm kiếm:</label>
                    <div class="input-group" style="width: 80%">
                        <input style="width: 80%" type="text" class="form-control" name="search" placeholder="Nhập tên hoặc email để tìm kiếm" value="${param.search}">
                        <input type="hidden" name="page" value="${currentPage}">

                    </div>
                </form>
            </div>
        </div>

    </div>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addBusModal">
        Thêm tài khoản mới
    </button>

    <table class="table">
        <thead>
            <tr>
                <th>Email</th>
                <th>Tên</th>
                <th>Vai trò</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.email}</td>
                    <td>${user.name}</td>
                    <c:choose>
                        <c:when test="${user.role eq 'admin'}">
                            <td style="color: red">Quản trị viên</td>
                        </c:when>
                        <c:otherwise>
                            <td>Nhân viên</td>
                        </c:otherwise>
                    </c:choose>

                    <td style="display: inline-flex">
                        <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#editRouteModal${user.id}">
                            Sửa
                        </button>
                        <c:if test="${account.id != user.id}">
                            <form action="${pageContext.request.contextPath}/user-management" method="POST" onsubmit="return confirm('Bạn chắc chắn muốn xóa tài khoản này?')">
                                <input type="hidden" name="id" value="${user.id}">
                                <input type="hidden" name="action" value="delete">
                                <button type="submit" class="btn btn-danger">
                                    Xóa
                                </button>
                            </form>
                        </c:if>
                    </td>


                </tr>

            <div class="modal fade" id="editRouteModal${user.id}" tabindex="-1" aria-labelledby="editRouteModalLabel${user.id}" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editRouteModalLabel${route.id}">Chỉnh sửa tuyến đường cho bus ${route.name}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Form to edit route for the bus -->
                            <<form action="${pageContext.request.contextPath}/user-management" method="post">
                                <label style="margin-bottom: 10px"for="">Email</label><br>
                                <input class="input-group" type="email" value="${user.email}" name="email" required placeholder="Nhập email"><br>
                                <label  style="margin-bottom: 10px" >Tên</label><br>
                                <input class="input-group"  type="text" value="${user.name}" name="name"placeholder="Nhập tên" required><br>
                                <label  style="margin-bottom: 10px">Vai trò</label><br>
                                <div class="input-group" >
                                    <select style="width: 80%" class="form-control" name="role" required="">
                                        <option value="">Chọn vai trò</option>
                                        <c:choose>
                                            <c:when test="${user.role eq 'admin'}">
                                                <option value="admin" selected>Quản trị viên</option>
                                                <option value="employee">Nhân viên</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="admin">Quản trị viên</option>
                                                <option value="employee" selected>Nhân viên</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>

                                </div>
                                <input type="hidden"  name="id" value="${user.id}"><br><br>
                                <input type="hidden"  name="action" value="edit"><br><br>

                                <button type="submit" class="btn btn-primary">Cập nhật</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
            </tbody>
    </table>
    <nav class="mt-3" aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
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


<div class="modal fade" id="addBusModal" tabindex="-1" aria-labelledby="addBusModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addBusModalLabel">Tạo tài khoản mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/user-management" method="post">
                    <label style="margin-bottom: 10px"for="">Email</label><br>
                    <input class="input-group" type="email" name="email" required placeholder="Nhập email"><br>
                    <label  style="margin-bottom: 10px" >Tên</label><br>
                    <input class="input-group"  type="text" name="name"placeholder="Nhập tên" required><br>
                    <label  style="margin-bottom: 10px"  >Mật khẩu</label><br>
                    <input class="input-group"  type="password"  name="psw"placeholder="Nhập mật khẩu" required><br>
                    <label  style="margin-bottom: 10px"  >Nhập lại mật khẩu</label><br>
                    <input class="input-group"  type="password"  name="psw2"placeholder="Nhập lại mật khẩu" required><br>
                    <label  style="margin-bottom: 10px">Vai trò</label><br>
                    <div class="input-group" >
                        <select style="width: 80%" class="form-control" name="role" required="">
                            <option value="">Chọn vai trò</option>
                            <option  value="admin">Quản trị viên</option>
                            <option  value="employee">Nhân viên</option>
                        </select>
                    </div>
                    <input type="hidden"  name="action" value="add"><br><br>

                    <button type="submit" class="btn btn-primary">Thêm</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="./view/footer.jsp"%>
