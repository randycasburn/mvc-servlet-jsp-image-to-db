<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/jsp/partials/header.jsp">
    <c:param name="title" value="Things"/>
</c:import>

<h1>Add/Update Thing</h1>

<form:form class="form" action="thing" method="POST" modelAttribute="thingToUpdate" enctype="multipart/form-data">
    <input type="hidden" id="id" name="id" value="${thingToUpdate.id}">
    <div class="row justify-content-center">
        <label class="col-sm-2 col-form-label" for="name">Name:</label>
        <div class="col-sm-3">
            <form:input class="form-control" id="name" name="name" value="${thingToUpdate.name}" path="name"/>
            <span class="text-danger">${error}</span>
        </div>
    </div>
    <br>
    <div class="row justify-content-center">
        <label class="col-sm-2 col-form-label" for="avatarContainer">Avatar:</label>
        <c:if test="${not empty thingToUpdate.avatar}">
            <img class="col-sm-3" width="50" height="50" src="thing/image?id=${thingToUpdate.id}"/>
        </c:if>
        <div class="col-sm-3">
            <input type="file" class="form-control" id="avatarContainer" name="avatarContainer" value=""/>
            <span class="text-danger">${error}</span>
        </div>
        <br>
        <br>
        <div class="row justify-content-center">
            <input class="col-sm-1 btn btn-primary" type="submit">
        </div>
    </div>
</form:form>
</body>
</html>
