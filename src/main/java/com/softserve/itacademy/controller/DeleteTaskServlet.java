package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-task")
public class DeleteTaskServlet extends HttpServlet {

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
            request.setAttribute("priority", task.getPriority().name());
            request.getRequestDispatcher("/WEB-INF/pages/delete-task.jsp").forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("message", "Task ID is missing!");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParameter = request.getParameter("id");

        if (idParameter != null) {
            int id = Integer.parseInt(idParameter);
            Task task = taskRepository.read(id);
            boolean deleted = taskRepository.delete(id);

            if (deleted) {
                response.sendRedirect("/tasks-list");
            } else {
                request.setAttribute("error", "Failed to delete the task. Please try again.");
                request.setAttribute("id", task.getId());
                request.setAttribute("title", task.getTitle());
                request.setAttribute("priority", task.getPriority().name());
                request.getRequestDispatcher("/WEB-INF/pages/delete-task.jsp").forward(request, response);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("message", "Task ID is missing!");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}