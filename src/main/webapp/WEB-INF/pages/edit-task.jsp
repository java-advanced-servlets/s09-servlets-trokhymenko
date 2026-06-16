<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page import="com.softserve.itacademy.model.Priority" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Edit existing Task</title>
    <style>
        <%@include file="../styles/main.css"%>
    </style>
</head>
<body>
<%@include file="header.html" %>
<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-danger">
    <%= request.getAttribute("error") %>
</div>
<% } %>

<form action="${pageContext.request.contextPath}/edit-task" method="post">
    <input type="hidden" value="<%= request.getAttribute("id") %>" name="id">
    <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" class="form-control"
               value="<%= request.getAttribute("title") != null ? request.getAttribute("title") : "" %>"
               placeholder="Enter task title" required>
    </div>

    <div class="form-group">
        <label for="priority">Priority:</label>
        <select id="priority" name="priority" class="form-select" required>
            <option value="" disabled <%= request.getAttribute("priority") == null ? "selected" : "" %>>Select priority</option>
            <%
                Priority selectedPriority = (Priority) request.getAttribute("priority");
                for (Priority priority : Priority.values()) {
                    boolean isSelected = selectedPriority != null && selectedPriority.equals(priority);
            %>
            <option value="<%= priority %>" <%= isSelected ? "selected" : "" %>><%= priority %></option>
            <% } %>
        </select>
    </div>

    <button type="submit" class="btn btn-success">Update Task</button>
    <a href="${pageContext.request.contextPath}/tasks-list" class="btn">Cancel</a>
</form>
</body>
</html>
