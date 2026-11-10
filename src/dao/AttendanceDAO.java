package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modal.Attendance;
import util.DBConnection;

public class AttendanceDAO 
{
	public boolean markAttendance(Attendance attendance) {

	    if (attendanceExists(
	            attendance.getEmployeeId(),
	            attendance.getAttendanceDate())) {

	        System.out.println(
	                "Attendance already exists for this date.");

	        return false;
	    }

	    String sql = "INSERT INTO attendance "
	               + "(employee_id, attendance_date, status) "
	               + "VALUES (?, ?, ?)";

	    try {

	        Connection cn = DBConnection.getConnection();

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setInt(1, attendance.getEmployeeId());
	        ps.setDate(2, attendance.getAttendanceDate());
	        ps.setString(3, attendance.getStatus());

	        int rows = ps.executeUpdate();

	        return rows > 0;

	    } catch (SQLException e) {

	        e.printStackTrace();
	        return false;
	    }
	}
	
	public List<Attendance> getAttendanceByEmployeeId(int employeeId) {

	    List<Attendance> attendanceList = new ArrayList<>();

	    String sql = "SELECT * FROM attendance "
	               + "WHERE employee_id = ? "
	               + "ORDER BY attendance_date";

	    try {

	        Connection cn = DBConnection.getConnection();

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setInt(1, employeeId);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Attendance attendance = new Attendance();

	            attendance.setAttendanceId(
	                    rs.getInt("attendance_id"));

	            attendance.setEmployeeId(
	                    rs.getInt("employee_id"));

	            attendance.setAttendanceDate(
	                    rs.getDate("attendance_date"));

	            attendance.setStatus(
	                    rs.getString("status"));

	            attendanceList.add(attendance);
	        }

	    } catch (SQLException e) {

	        e.printStackTrace();
	    }

	    return attendanceList;
	}
	
	public List<Attendance> getMonthlyAttendance(
	        int employeeId, int month, int year) {

	    List<Attendance> attendanceList = new ArrayList<>();

	    String sql = "SELECT * FROM attendance "
	               + "WHERE employee_id = ? "
	               + "AND MONTH(attendance_date) = ? "
	               + "AND YEAR(attendance_date) = ? "
	               + "ORDER BY attendance_date";

	    try {

	        Connection cn = DBConnection.getConnection();

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setInt(1, employeeId);
	        ps.setInt(2, month);
	        ps.setInt(3, year);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Attendance attendance = new Attendance();

	            attendance.setAttendanceId(
	                    rs.getInt("attendance_id"));

	            attendance.setEmployeeId(
	                    rs.getInt("employee_id"));

	            attendance.setAttendanceDate(
	                    rs.getDate("attendance_date"));

	            attendance.setStatus(
	                    rs.getString("status"));

	            attendanceList.add(attendance);
	        }

	    } catch (SQLException e) {

	        e.printStackTrace();
	    }

	    return attendanceList;
	}
	
	public boolean markApprovedLeaveAttendance(
	        int employeeId,
	        Date fromDate,
	        Date toDate) {

	    LocalDate currentDate = fromDate.toLocalDate();
	    LocalDate endDate = toDate.toLocalDate();

	    try {

	        Connection cn = DBConnection.getConnection();

	        String sql = "INSERT INTO attendance "
	                   + "(employee_id, attendance_date, status) "
	                   + "VALUES (?, ?, ?)";

	        PreparedStatement ps =
	                cn.prepareStatement(sql);

	        while (!currentDate.isAfter(endDate)) {

	            Date currentSqlDate =
	                    Date.valueOf(currentDate);

	            // Skip if attendance already exists
	            if (!attendanceExists(
	                    employeeId,
	                    currentSqlDate)) {

	                String status;

	                if (currentDate.getDayOfWeek()
	                        == DayOfWeek.SUNDAY) {

	                    status = "HOLIDAY";

	                } else {

	                    status = "LEAVE";
	                }

	                ps.setInt(1, employeeId);
	                ps.setDate(2, currentSqlDate);
	                ps.setString(3, status);

	                ps.executeUpdate();
	            }

	            currentDate = currentDate.plusDays(1);
	        }

	        return true;

	    } catch (SQLException e) {

	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean attendanceExists(int employeeId, Date attendanceDate) {

	    String sql = "SELECT COUNT(*) FROM attendance "
	               + "WHERE employee_id = ? "
	               + "AND attendance_date = ?";

	    try {

	        Connection cn = DBConnection.getConnection();

	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.setInt(1, employeeId);
	        ps.setDate(2, attendanceDate);

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
