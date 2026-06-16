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
<div class="delete-confirmation-container">
  <form action="${pageContext.request.contextPath}/delete-task" method="post">
    <input type="hidden" value="<%= request.getAttribute("id") %>" name="id"/>
    <h2 class="title">Are you sure you want to delete this task?</h2>
    <div>
      <p>Title: <%= request.getAttribute("title") %></p>
      <p>Priority: <%= request.getAttribute("priority") %></p>
    </div>
    <div class="actions">
      <button type="submit" class="btn btn-danger">Delete</button>
      <a href="${pageContext.request.contextPath}/tasks-list" class="btn">Cancel</a>
    </div>
  </form>
</div>
</body>
</html>
