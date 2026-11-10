package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modal.Employee;
import util.DBConnection;

public class EmployeeDAO 
{
	 public boolean addEmployee(Employee employee) {
		 
		 //Insert Query
	        String inssql = "INSERT INTO employee "
	                   + "(name, email, phone, designation, joining_date, basic_salary, department_id) "
	                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	        try {

	            Connection cn = DBConnection.getConnection();

	            PreparedStatement ps = cn.prepareStatement(inssql);

	            ps.setString(1, employee.getName());
	            ps.setString(2, employee.getEmail());
	            ps.setString(3, employee.getPhone());
	            ps.setString(4, employee.getDesignation());
	            ps.setDate(5, employee.getJoiningDate());
	            ps.setDouble(6, employee.getBasicSalary());
	            ps.setInt(7, employee.getDepartmentId());

	            int rows = ps.executeUpdate();

	            if (rows > 0) {
	                return true;
	            }

	        } catch (SQLException e) {

	            e.printStackTrace();
	        }

	        return false;
	 }
	 
	 //View Query
	 public Employee getEmployeeById(int employeeId) {

		    String sql = "SELECT * FROM employee WHERE employee_id = ?";

		    try {

		        Connection cn = DBConnection.getConnection();

		        PreparedStatement ps = cn.prepareStatement(sql);

		        ps.setInt(1, employeeId);

		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {

		            Employee employee = new Employee();

		            employee.setEmployeeId(rs.getInt("employee_id"));
		            employee.setName(rs.getString("name"));
		            employee.setEmail(rs.getString("email"));
		            employee.setPhone(rs.getString("phone"));
		            employee.setDesignation(rs.getString("designation"));
		            employee.setJoiningDate(rs.getDate("joining_date"));
		            employee.setBasicSalary(rs.getDouble("basic_salary"));
		            employee.setDepartmentId(rs.getInt("department_id"));

		            return employee;
		        }

		    } catch (SQLException e) {

		        e.printStackTrace();
		    }

		    return null;
		}
	 
	 //View allEmployee
	 public List<Employee> getAllEmployees() {

		    List<Employee> employeeList = new ArrayList<>();

		    String sql = "SELECT * FROM employee";

		    try {

		        Connection cn = DBConnection.getConnection();

		        PreparedStatement ps = cn.prepareStatement(sql);

		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {

		            Employee employee = new Employee();

		            employee.setEmployeeId(rs.getInt("employee_id"));
		            employee.setName(rs.getString("name"));
		            employee.setEmail(rs.getString("email"));
		            employee.setPhone(rs.getString("phone"));
		            employee.setDesignation(rs.getString("designation"));
		            employee.setJoiningDate(rs.getDate("joining_date"));
		            employee.setBasicSalary(rs.getDouble("basic_salary"));
		            employee.setDepartmentId(rs.getInt("department_id"));

		            employeeList.add(employee);
		        }

		    } catch (SQLException e) {

		        e.printStackTrace();
		    }

		    return employeeList;
	 }
	 
	 //Update Query
	 public boolean updateEmployee(Employee employee) {

		    String sql = "UPDATE employee SET "
		               + "name = ?, "
		               + "email = ?, "
		               + "phone = ?, "
		               + "designation = ?, "
		               + "joining_date = ?, "
		               + "basic_salary = ?, "
		               + "department_id = ? "
		               + "WHERE employee_id = ?";

		    try {

		        Connection cn = DBConnection.getConnection();

		        PreparedStatement ps = cn.prepareStatement(sql);

		        ps.setString(1, employee.getName());
		        ps.setString(2, employee.getEmail());
		        ps.setString(3, employee.getPhone());
		        ps.setString(4, employee.getDesignation());
		        ps.setDate(5, employee.getJoiningDate());
		        ps.setDouble(6, employee.getBasicSalary());
		        ps.setInt(7, employee.getDepartmentId());
		        ps.setInt(8, employee.getEmployeeId());

		        int rows = ps.executeUpdate();

		        return rows > 0;

		    } catch (SQLException e) {

		        e.printStackTrace();
		        return false;
		    }
		}
	 
	 //Delete Employee
	 
	 public boolean deleteEmployee(int employeeId) {

		    String sql = "DELETE FROM employee WHERE employee_id = ?";

		    try {

		        Connection cn = DBConnection.getConnection();

		        PreparedStatement ps = cn.prepareStatement(sql);

		        ps.setInt(1, employeeId);

		        int rows = ps.executeUpdate();

		        return rows > 0;

		    } catch (SQLException e) {

		        e.printStackTrace();
		        return false;
		    }
		}
	 
	 //Search Employee by Name
	 
	 public List<Employee> searchEmployeesByName(String name) {

		    List<Employee> employeeList = new ArrayList<>();

		    String sql = "SELECT * FROM employee WHERE name LIKE ?";

		    try {

		        Connection cn = DBConnection.getConnection();

		        PreparedStatement ps = cn.prepareStatement(sql);

		        ps.setString(1, "%" + name + "%");

		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {

		            Employee employee = new Employee();

		            employee.setEmployeeId(rs.getInt("employee_id"));
		            employee.setName(rs.getString("name"));
		            employee.setEmail(rs.getString("email"));
		            employee.setPhone(rs.getString("phone"));
		            employee.setDesignation(rs.getString("designation"));
		            employee.setJoiningDate(rs.getDate("joining_date"));
		            employee.setBasicSalary(rs.getDouble("basic_salary"));
		            employee.setDepartmentId(rs.getInt("department_id"));

		            employeeList.add(employee);
		        }

		    } catch (SQLException e) {

		        e.printStackTrace();
		    }

		    return employeeList;
		}
}
