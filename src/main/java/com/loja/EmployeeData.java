package com.loja;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeData {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public EmployeeData(String jdbcURL, String jdbcUsername, String jdbcPassword ) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public void conectar() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    public void desconectar() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public void inserir(Employee Employee) throws SQLException {
        conectar();
        String query = "INSERT INTO Employees (nome, role) VALUES (?, ?)";
        PreparedStatement preparedStatement = jdbcConnection.prepareStatement(query);
        preparedStatement.setString(1, Employee.getName());
        preparedStatement.setString(2, Employee.getRole());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        desconectar();
    }

    public Employee buscarPorId(int id) throws SQLException {
        conectar();
        String query = "SELECT * FROM Employees WHERE id = ?";
        PreparedStatement preparedStatement = jdbcConnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;
        if (resultSet.next()) {
            employee = new Employee(id, query, query);
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("nome"));
            employee.setRole(resultSet.getString("role"));
        }

        resultSet.close();
        preparedStatement.close();
        desconectar();

        return employee;
    }

    public List<Employee> listar() throws SQLException {
        conectar();
        String query = "SELECT * FROM Employees";
        PreparedStatement preparedStatement = jdbcConnection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            Employee employee = new Employee(0, query, query);
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("nome"));
            employee.setRole(resultSet.getString("role"));
            employees.add(employee);
        }

        resultSet.close();
        preparedStatement.close();
        desconectar();

        return employees;
    }

    public void atualizar(Employee employee) throws SQLException {
        conectar();
        String query = "UPDATE Employees SET nome = ?, role = ? WHERE id = ?";
        PreparedStatement preparedStatement = jdbcConnection.prepareStatement(query);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getRole());
        preparedStatement.setFloat(3, employee.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        desconectar();
    }

    public void excluir(int id) throws SQLException {
        conectar();
        String query = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement preparedStatement = jdbcConnection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        desconectar();
    }

}
