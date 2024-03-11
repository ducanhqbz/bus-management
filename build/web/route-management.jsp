<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="./view/header.jsp"%>

<main class="container py-5">
    <h2>Quản lý tuyến đường</h2>
    <c:if test="${not empty param.msg}">
        <div class="alert alert-success" role="alert">
            ${param.msg}
        </div>  
    </c:if>

    <c:if test="${not empty param.err}">
        <div class="alert alert-danger" role="alert">
            ${param.err}
        </div>
    </c:if>
    <div class="row mb-12" style="margin-bottom: 30px">
        <div class="col-md-6">
            <div class="form-group">
                <form action="route-management" method="get">
                    <label for="searchInput" class="form-label">Tìm kiếm:</label>
                    <div class="input-group" style="width: 80%">
                        <input style="width: 80%" type="text" class="form-control" name="search" placeholder="Nhập tên tuyến  đường để tìm kiếm" value="${param.search}">
                        <input type="hidden" name="page" value="${currentPage}">

                    </div>
                </form>
            </div>
        </div>

    </div>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addRouteModal">
        Thêm Tuyến đường
    </button>

    <table class="table">
        <thead>
            <tr>
                <th>Điểm đầu</th>
                <th>Điểm cuối</th>
                <th>Thời gian</th>
                <th>Trạm dừng</th>
                <th>Tần suất</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="route" items="${routes}">
                <tr>
                    <td>${route.startPoint} </td>
                    <td>${route.endPoint} </td>
                    <td>${route.startTime} - ${route.endTime}</td>
                    <td>
                        <strong style="color: red"> ${route.startPoint}</strong>  ⮕ 
                        <c:forEach var="s" items="${route.routePassesStation}">
                            ${s.stationName} ⮕ 
                        </c:forEach>
                        <strong style="color: red">${route.endPoint}</strong> 
                    </td>
                    <td>${route.frequency}</td>

                    <td style="width: 140px">
                        <div style="display: inline-block;">
                            <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#editRouteModal${route.id}">
                                Sửa
                            </button>
                            <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#stationsModal${route.id}">
                                Xem
                            </button>
                        </div>
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

<!-- Add Station Modal -->

<div class="modal fade" id="addStation" tabindex="-1" aria-labelledby="addStationLable" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm trạm dừng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Form to add station -->
                <form action="${pageContext.request.contextPath}/station-management" method="post">
                    <input type="hidden" name="routeId" id="add-station-route-id">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3">
                        <label for="stationName" class="form-label">Tên trạm dừng:</label>
                        <input type="text" class="form-control" name="stationName" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Thêm</button>
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
                    <h5 class="modal-title" id="editRouteModalLabel${route.id}">Chỉnh sửa tuyến đường</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Form to edit route for the bus -->
                    <form action="${pageContext.request.contextPath}/route-management" method="post">
                        <label style="margin-bottom: 10px"for="">Điểm đầu</label><br>
                        <input class="input-group" type="hidden"" name="id" value="${route.id}">
                        <input class="input-group" type="hidden"" name="action" value="edit">
                        <input class="input-group" type="text"" name="startPoint" value="${route.startPoint}" required placeholder="Nhập điểm đầu"><br>
                        <label  style="margin-bottom: 10px">Điểm cuối</label><br>
                        <input class="input-group" type="text"" name="endPoint" value="${route.endPoint}" required placeholder="Nhập điểm cuối"><br>
                        <label  style="margin-bottom: 10px">Thời gian bắt đầu(hh:mm)</label><br>
                        <input class="input-group" type="time"" name="startTime" value="${route.startTime}" required placeholder="Thời gian bắt đầu(HH:mm)"><br>
                        <label  style="margin-bottom: 10px">Thời gian kết thúc(hh:mm)</label><br>
                        <input class="input-group" type="time"" name="endTime" value="${route.endTime}" required placeholder="Thời gian kết thúc(HH:mm)"><br>
                        <label  style="margin-bottom: 10px">Tần suất)</label><br>
                        <input class="input-group" type="text"" name="frequency" value="${route.frequency}" required placeholder="Thời gian kết thúc(HH:mm)"><br>

                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal for displaying stations -->
    <div class="modal fade" id="stationsModal${route.id}" tabindex="-1" aria-labelledby="stationsModalLabel${route.id}" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- Modal header -->
                <div class="modal-header">
                    <h5 class="modal-title" id="stationsModalLabel${route.id}">Trạm dừng cho tuyến ${route.startPoint} - ${route.endPoint}</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <!-- Modal body -->
                <div class="modal-body">
                    <ul>
                        <!-- Loop through stations of the route -->

                        <c:forEach var="s" items="${route.routePassesStation}">
                            <li style="margin-bottom: 20px; display: flex; justify-content: space-between;">
                                ${s.stationName}
                                <!-- Edit and Delete buttons inline -->
                                <span style="display: flex;">
                                    <button type="button" class="btn btn-sm btn-info btn-edit-station" data-bs-toggle="modal" data-bs-target="#editStationModal" data-route-id="${route.id}" data-station-id="${s.stationID}" data-station-name="${s.stationName}">
                                        Sửa
                                    </button>

                                    <form action="${pageContext.request.contextPath}/station-management" method="Post" onsubmit="return confirm('Bạn chắc chắn muốn xóa?')">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="stationId" value="${s.stationID}">
                                        <input type="hidden" name="routeId" value="${route.id}">
                                        <button type="submit" class="btn btn-sm btn-danger">Xóa</button>
                                    </form>
                                </span>
                            </li>
                        </c:forEach>

                    </ul>
                </div>
                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addStation" data-route-id-add="${route.id}">
                    Thêm trạm dừng
                </button>
            </div>
        </div>
    </div>

</c:forEach>

<!-- Edit Station Modal -->
<div class="modal fade" id="editStationModal" tabindex="-1" aria-labelledby="editStationModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editStationModalLabel">Chỉnh sửa thông tin trạm dừng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/station-management" method="post">
                    <div class="mb-3">
                        <input type="hidden" id="edit-station-id" name="stationId">
                        <label for="edit-station-name" class="form-label">Tên trạm dừng:</label>
                        <input type="hidden" id="edit-route-id" name="routeId">
                        <input type="hidden"  name="action" value="edit">
                        <input type="text" class="form-control" id="edit-station-name" name="stationName" required>
                    </div>
                    <!-- Add more fields as needed -->
                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Get all the Edit buttons for stations
        const editStationButtons = document.querySelectorAll('.btn-edit-station');
        // Loop through each Edit button for stations
        editStationButtons.forEach(function (button) {
            // Add click event listener to each Edit button for stations
            button.addEventListener('click', function () {
                // Get the station name and ID from the button's data attributes
                const stationId = button.getAttribute('data-station-id');
                const stationName = button.getAttribute('data-station-name');
                // Populate the form fields in the edit modal with the station values
                document.querySelector('#edit-station-id').value = stationId;
                document.querySelector('#edit-station-name').value = stationName;
                document.querySelector('#edit-route-id').value = button.getAttribute('data-route-id');
            });
        });
    });
    document.addEventListener('DOMContentLoaded', function () {
        const addStationModal = document.getElementById('addStation');
        addStationModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const routeId = button.getAttribute('data-route-id-add');
            console.log("routerId for add " + routeId);
            const routeIdInput = addStationModal.querySelector('#add-station-route-id');
            console.log("addStationModal:", addStationModal);
            console.log("routeIdInput:", routeIdInput);
            routeIdInput.value = routeId;
        });
    });
</script>

<%@ include file="./view/footer.jsp"%>
