package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modal.LeaveRecord;
import util.DBConnection;

public class LeaveDAO 
{
	public boolean applyLeave(LeaveRecord leave) {
		AttendanceDAO attendanceDAO = new AttendanceDAO();

        String sql = "INSERT INTO leave_record "
                   + "(employee_id, from_date, to_date, leave_type, reason, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, leave.getEmployeeId());
            ps.setDate(2, leave.getFromDate());
            ps.setDate(3, leave.getToDate());
            ps.setString(4, leave.getLeaveType());
            ps.setString(5, leave.getReason());
            ps.setString(6, "PENDING");

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public List<LeaveRecord> getLeavesByEmployeeId(int employeeId) {

        List<LeaveRecord> leaveList = new ArrayList<>();

        String sql = "SELECT * FROM leave_record "
                   + "WHERE employee_id = ? "
                   + "ORDER BY from_date";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                LeaveRecord leave = new LeaveRecord();

                leave.setLeaveId(rs.getInt("leave_id"));
                leave.setEmployeeId(rs.getInt("employee_id"));
                leave.setFromDate(rs.getDate("from_date"));
                leave.setToDate(rs.getDate("to_date"));
                leave.setLeaveType(rs.getString("leave_type"));
                leave.setReason(rs.getString("reason"));
                leave.setStatus(rs.getString("status"));

                leaveList.add(leave);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return leaveList;
    }
    
    public List<LeaveRecord> getPendingLeaves() {

        List<LeaveRecord> leaveList = new ArrayList<>();

        String sql = "SELECT * FROM leave_record "
                   + "WHERE status = 'PENDING' "
                   + "ORDER BY from_date";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                LeaveRecord leave = new LeaveRecord();

                leave.setLeaveId(rs.getInt("leave_id"));
                leave.setEmployeeId(rs.getInt("employee_id"));
                leave.setFromDate(rs.getDate("from_date"));
                leave.setToDate(rs.getDate("to_date"));
                leave.setLeaveType(rs.getString("leave_type"));
                leave.setReason(rs.getString("reason"));
                leave.setStatus(rs.getString("status"));

                leaveList.add(leave);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return leaveList;
    }
    
    public boolean updateLeaveStatus(int leaveId, String status) {

        String sql = "UPDATE leave_record "
                   + "SET status = ? "
                   + "WHERE leave_id = ?";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, leaveId);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
    
    public boolean approveLeave(int leaveId) {

        String selectSql = "SELECT * FROM leave_record "
                         + "WHERE leave_id = ?";

        String updateSql = "UPDATE leave_record "
                         + "SET status = 'APPROVED' "
                         + "WHERE leave_id = ?";

        try {

            Connection cn = DBConnection.getConnection();

            // Step 1: Get leave details
            PreparedStatement selectPs =
                    cn.prepareStatement(selectSql);

            selectPs.setInt(1, leaveId);

            ResultSet rs = selectPs.executeQuery();

            if (!rs.next()) {
                return false;
            }

            int employeeId = rs.getInt("employee_id");
            Date fromDate = rs.getDate("from_date");
            Date toDate = rs.getDate("to_date");
            String status = rs.getString("status");

            // Prevent approving an already approved/rejected leave
            if (!status.equalsIgnoreCase("PENDING")) {
                return false;
            }

            // Step 2: Update leave status
            PreparedStatement updatePs =
                    cn.prepareStatement(updateSql);

            updatePs.setInt(1, leaveId);

            int rows = updatePs.executeUpdate();

            if (rows > 0) {

                // Step 3: Mark attendance
                AttendanceDAO attendanceDAO =
                        new AttendanceDAO();

                return attendanceDAO.markApprovedLeaveAttendance(
                        employeeId,
                        fromDate,
                        toDate
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
    
    public boolean rejectLeave(int leaveId) {

        String sql = "UPDATE leave_record "
                   + "SET status = 'REJECTED' "
                   + "WHERE leave_id = ? "
                   + "AND status = 'PENDING'";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, leaveId);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}
