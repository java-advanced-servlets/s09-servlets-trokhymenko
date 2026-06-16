package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/edit-task")
public class UpdateTaskServlet extends HttpServlet {

    private TaskRepository taskRepository;

    @Override
    public void init() {
        taskRepository = TaskRepository.getTaskRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParameter = request.getParameter("id");

        if (idParameter != null && !idParameter.isEmpty()) {
            int id = Integer.parseInt(idParameter);
            Task task = taskRepository.read(id);

            if (task == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                request.setAttribute("message", "Task with ID '" + id + "' not found!");
                request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);

                return;
            }

            request.setAttribute("id", task.getId());
            request.setAttribute("title", task.getTitle());
            request.setAttribute("priority", task.getPriority());
            request.getRequestDispatcher("/WEB-INF/pages/edit-task.jsp").forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("message", "Task ID is missing!");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParameter = request.getParameter("id");
        String titleParameter = request.getParameter("title");
        String priorityParameter = request.getParameter("priority");

        if (idParameter != null
                && !idParameter.isEmpty()
                && titleParameter != null && !titleParameter.isEmpty()
                && priorityParameter != null && !priorityParameter.isEmpty()
        ) {
            Priority priority = Priority.valueOf(priorityParameter);
            Task task = taskRepository.read(Integer.parseInt(idParameter));

            task.setTitle(titleParameter);
            task.setPriority(priority);

            boolean updated = taskRepository.update(task);

            if (updated) {
                response.sendRedirect("/tasks-list");
            } else {
                request.setAttribute("error", "Failed to update the task. Please try again.");
                request.setAttribute("title", titleParameter);
                request.setAttribute("priority", priority);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/edit-task.jsp");
                requestDispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("error", "Title and priority must not be empty!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/edit-task.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}