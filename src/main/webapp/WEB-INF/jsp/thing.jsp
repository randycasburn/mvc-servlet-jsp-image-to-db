<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/jsp/partials/header.jsp">
    <c:param name="title" value="Things"/>
</c:import>
<div class="row text-center">
    <h1>Things</h1>
    <br>
    <a href="thing?id=&action=add">
        <button>Add Thing</button>
    </a>
    <br>
</div>
<div class="row justify-content-center">
    <div class="col-sm-4">
        <table class="table">
            <thead class="table-light">
            <th>Actions</th>
            <th>Thing ID</th>
            <th>Name</th>
            <th>Avatar</th>
            </thead>
            <tbody>
            <c:forEach var="thing" items="${things}">
                <tr>
                    <td>
                        <a href="thing?id=${thing.id}&action=update"><button>Update</button></a>
                        &nbsp;&nbsp;
                        <a href="thing?id=${thing.id}&action=delete"><button>Delete</button></a>
                    </td>
                    <td>${thing.id}</td>
                    <td>${thing.name}</td>
                    <c:url var="thingImg" value="/thing/image?id=${thing.id}"/>
                    <td><img height="50" width="50" src="${thingImg}" alt="None"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
