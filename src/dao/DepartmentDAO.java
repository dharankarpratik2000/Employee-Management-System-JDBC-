package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modal.Department;
import util.DBConnection;

public class DepartmentDAO 
{
	public List<Department> getAllDepartments() {

        List<Department> departmentList = new ArrayList<>();

        String sql = "SELECT * FROM department";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Department department = new Department();

                department.setDepartmentId(
                        rs.getInt("department_id"));

                department.setDepartmentName(
                        rs.getString("department_name"));

                departmentList.add(department);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return departmentList;
    }
	
	public Department getDepartmentById(int departmentId) {

        String sql = "SELECT * FROM department WHERE department_id = ?";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, departmentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Department department = new Department();

                department.setDepartmentId(
                        rs.getInt("department_id"));

                department.setDepartmentName(
                        rs.getString("department_name"));

                return department;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
}
