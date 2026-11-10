package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modal.Salary;
import util.DBConnection;

public class SalaryDAO 
{
	public boolean saveSalary(Salary salary) {

        String sql = "INSERT INTO salary "
                   + "(employee_id, salary_month, working_days, "
                   + "present_days, absent_days, leave_days, "
                   + "basic_salary, deductions, net_salary) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, salary.getEmployeeId());
            ps.setString(2, salary.getSalaryMonth());
            ps.setInt(3, salary.getWorkingDays());
            ps.setInt(4, salary.getPresentDays());
            ps.setInt(5, salary.getAbsentDays());
            ps.setInt(6, salary.getLeaveDays());
            ps.setDouble(7, salary.getBasicSalary());
            ps.setDouble(8, salary.getDeductions());
            ps.setDouble(9, salary.getNetSalary());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
	
	public List<Salary> getSalaryByEmployeeId(int employeeId) {

	    List<Salary> salaryList = new ArrayList<>();

	    String sql = "SELECT * FROM salary "
	               + "WHERE employee_id = ? "
	               + "ORDER BY salary_month";

	    try {

	        Connection cn = DBConnection.getConnection();

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setInt(1, employeeId);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Salary salary = new Salary();

	            salary.setSalaryId(rs.getInt("salary_id"));
	            salary.setEmployeeId(rs.getInt("employee_id"));
	            salary.setSalaryMonth(rs.getString("salary_month"));
	            salary.setWorkingDays(rs.getInt("working_days"));
	            salary.setPresentDays(rs.getInt("present_days"));
	            salary.setAbsentDays(rs.getInt("absent_days"));
	            salary.setLeaveDays(rs.getInt("leave_days"));
	            salary.setBasicSalary(rs.getDouble("basic_salary"));
	            salary.setDeductions(rs.getDouble("deductions"));
	            salary.setNetSalary(rs.getDouble("net_salary"));

	            salaryList.add(salary);
	        }

	    } catch (SQLException e) {

	        e.printStackTrace();
	    }

	    return salaryList;
	}
	
	public boolean salaryExists(int employeeId, String salaryMonth) {

	    String sql = "SELECT COUNT(*) FROM salary "
	               + "WHERE employee_id = ? "
	               + "AND salary_month = ?";

	    try {

	        Connection cn = DBConnection.getConnection();

	        PreparedStatement ps =
	                cn.prepareStatement(sql);

	        ps.setInt(1, employeeId);
	        ps.setString(2, salaryMonth);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }

	    } catch (SQLException e) {

	        e.printStackTrace();
	    }

	    return false;
	}
}
