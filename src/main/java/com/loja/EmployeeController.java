package com.loja;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeData employeeData;

    public void init() {
        String jdbcURL = "jdbc:mysql://localhost:3306/crud_funcionarios";
        String jdbcUsername = "(Configurar)";
        String jdbcPassword = "(Configurar)";

        employeeData = new EmployeeData(jdbcURL, jdbcUsername, jdbcPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    doGet(request, response);
}


protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
String action = request.getServletPath();

try {
switch (action) {
    case "/novo":
        exibirFormulario(request, response);
        break;
    case "/inserir":
        inserirFuncionario(request, response);
        break;
    case "/editar":
        exibirFormularioEdicao(request, response);
        break;
    case "/atualizar":
        atualizarFuncionario(request, response);
        break;
    case "/excluir":
        excluirFuncionario(request, response);
        break;
    default:
        listarFuncionarios(request, response);
        break;
}
} catch (SQLException e) {
throw new ServletException(e);
    }
}


private void listarFuncionarios(HttpServletRequest request, HttpServletResponse response)
throws SQLException, ServletException, IOException {
List<Employee> listEmployees = employeeData.listar();
request.setAttribute("listaFuncionarios", listEmployees);
request.getRequestDispatcher("lista-funcionarios.jsp").forward(request, response);
}



private void exibirFormulario(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
request.getRequestDispatcher("formulario-funcionario.jsp").forward(request, response);
}

private void inserirFuncionario(HttpServletRequest request, HttpServletResponse response)
throws SQLException, IOException {
String name = request.getParameter("name");
String role = request.getParameter("role");

Employee employee = new Employee(0, name, role);
employeeData.inserir(employee);
response.sendRedirect("list");
}

private void exibirFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
throws SQLException, ServletException, IOException {
int id = Integer.parseInt(request.getParameter("id"));
Employee employee = employeeData.buscarPorId(id);
request.setAttribute("employee", employee);
request.getRequestDispatcher("formulario-funcionario.jsp").forward(request, response);
}

private void atualizarFuncionario(HttpServletRequest request, HttpServletResponse response)
throws SQLException, IOException {
int id = Integer.parseInt(request.getParameter("id"));
String name = request.getParameter("name");
String role = request.getParameter("role");

Employee employee = new Employee(id, name, role);
employeeData.atualizar(employee);
response.sendRedirect("listar");
}

private void excluirFuncionario(HttpServletRequest request, HttpServletResponse response)
throws SQLException, IOException {
int id = Integer.parseInt(request.getParameter("id"));
employeeData.excluir(id);
response.sendRedirect("list");
}

    
}
