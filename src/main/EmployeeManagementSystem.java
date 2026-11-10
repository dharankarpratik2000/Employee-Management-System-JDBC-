package main;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import dao.EmployeeDAO;
import dao.LeaveDAO;
import dao.SalaryDAO;
import modal.Employee;
import dao.AdminDAO;
import dao.AttendanceDAO;
import dao.DepartmentDAO;
import modal.Attendance;
import modal.Department;
import modal.LeaveRecord;
import modal.Salary;
import util.DateUtil;

public class EmployeeManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        LeaveDAO leaveDAO = new LeaveDAO();
        SalaryDAO salaryDAO = new SalaryDAO();
        AdminDAO adminDAO = new AdminDAO();

        System.out.println("========================================");
        System.out.println("          ADMIN LOGIN");
        System.out.println("========================================");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        boolean loginResult =
                adminDAO.login(username, password);

        if (!loginResult) {

            System.out.println("Invalid Username or Password!");
            sc.close();
            return;
        }

        System.out.println();
        System.out.println("Login Successful!");

        int choice;

        do {

            System.out.println();
            System.out.println("========================================");
            System.out.println("       EMPLOYEE MANAGEMENT SYSTEM");
            System.out.println("========================================");

            System.out.println("1. Add Employee");
            System.out.println("2. View Employee");
            System.out.println("3. View All Employees");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Search Employee");
            System.out.println("7. View Departments");
            System.out.println("8. Mark Attendance");
            System.out.println("9. View Attendance");
            System.out.println("10. Monthly Attendance Report");
            System.out.println("11. Apply Leave");
            System.out.println("12. View Leave");
            System.out.println("13. View Pending Leaves");
            System.out.println("14. Approve / Reject Leave");
            System.out.println("15. Calculate Salary");
            System.out.println("16. Salary Report");
            System.out.println("17. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine(); // clear buffer

                    System.out.print("Enter Employee Name: ");
                    String name = sc.nextLine();
                    
                    if (name.trim().isEmpty()) {

                        System.out.println(
                                "Employee name cannot be empty.");

                        break;
                    }
                    
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    
                    if (!email.matches(
                            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                        System.out.println("Invalid email address!");

                        break;
                    }
                    
                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();
                    
                    if (!phone.matches("\\d{10}")) {

                        System.out.println(
                                "Phone number must contain exactly 10 digits.");

                        break;
                    }
                    
                    System.out.print("Enter Designation: ");
                    String designation = sc.nextLine();

                    System.out.print("Enter Joining Date (YYYY-MM-DD): ");

                    Date joiningDate;

                    try {

                        joiningDate = Date.valueOf(sc.nextLine());

                    } catch (IllegalArgumentException e) {

                        System.out.println(
                                "Invalid date format! Use YYYY-MM-DD.");

                        break;
                    }

                    System.out.print("Enter Basic Salary: ");
                    double basicSalary = sc.nextDouble();
                    
                    if (basicSalary <= 0) {
                        System.out.println("Salary must be greater than 0!");
                        break;
                    }

                    System.out.println();
                    System.out.println("Available Departments:");

                    List<Department> departmentList =
                            departmentDAO.getAllDepartments();

                    for (Department dept : departmentList) {

                        System.out.println(
                                dept.getDepartmentId() +
                                ". " +
                                dept.getDepartmentName());
                    }

                    System.out.print("Enter Department ID: ");
                    int departmentId = sc.nextInt();
                    
                    Department selectedDepartment =
                            departmentDAO.getDepartmentById(departmentId);

                    if (selectedDepartment == null) {
                        System.out.println("Invalid Department ID!");
                        break;
                    }

                    Employee employee = new Employee();

                    employee.setName(name);
                    employee.setEmail(email);
                    employee.setPhone(phone);
                    employee.setDesignation(designation);
                    employee.setJoiningDate(joiningDate);
                    employee.setBasicSalary(basicSalary);
                    employee.setDepartmentId(departmentId);

                    boolean result = employeeDAO.addEmployee(employee);

                    if (result) {
                        System.out.println("Employee Added Successfully!");
                    } else {
                        System.out.println("Failed to Add Employee!");
                    }

                    break;

                case 2:

                	System.out.print("Enter Employee ID: ");
                    int employeeId = sc.nextInt();

                    Employee emp = employeeDAO.getEmployeeById(employeeId);

                    if (emp != null) {

                        System.out.println();
                        System.out.println("========== EMPLOYEE DETAILS ==========");
                        System.out.println("Employee ID   : " + emp.getEmployeeId());
                        System.out.println("Name          : " + emp.getName());
                        System.out.println("Email         : " + emp.getEmail());
                        System.out.println("Phone         : " + emp.getPhone());
                        System.out.println("Designation   : " + emp.getDesignation());
                        System.out.println("Joining Date  : " + emp.getJoiningDate());
                        System.out.println("Basic Salary  : " + emp.getBasicSalary());
                        Department department =
                                departmentDAO.getDepartmentById(emp.getDepartmentId());

                        if (department != null) {
                            System.out.println(
                                    "Department    : " +
                                    department.getDepartmentName());
                        } else {
                            System.out.println("Department    : Not Found");
                        }

                    } else {

                        System.out.println("Employee Not Found!");
                    }

                    break;

                    case 3:
                       List<Employee> employeeList = employeeDAO.getAllEmployees();
                        if (employeeList.isEmpty()) {
                            System.out.println("No Employees Found!");
                        } else {
                            System.out.println();
                            System.out.println("========== ALL EMPLOYEES ==========");
                            for (Employee emp1 : employeeList) {
                                System.out.println("-------------------------------");
                                System.out.println("Employee ID   : " + emp1.getEmployeeId());
                                System.out.println("Name          : " + emp1.getName());
                                System.out.println("Email         : " + emp1.getEmail());
                                System.out.println("Phone         : " + emp1.getPhone());
                                System.out.println("Designation   : " + emp1.getDesignation());
                                System.out.println("Joining Date  : " + emp1.getJoiningDate());
                                System.out.println("Basic Salary  : " + emp1.getBasicSalary());
                                Department department =
                                        departmentDAO.getDepartmentById(emp1.getDepartmentId());

                                if (department != null) {
                                    System.out.println(
                                            "Department    : " +
                                            department.getDepartmentName());
                                } else {
                                    System.out.println("Department    : Not Found");
                                }
                            }
                        }

                    break;

                case 4:

                	System.out.print("Enter Employee ID to Update: ");
                    int updateId = sc.nextInt();

                    sc.nextLine();

                    Employee existingEmployee = employeeDAO.getEmployeeById(updateId);

                    if (existingEmployee == null) {

                        System.out.println("Employee Not Found!");

                    } else {

                        System.out.println("Employee Found: " + existingEmployee.getName());

                        //System.out.print("Enter New Name: ");
                        //String newName = sc.nextLine();

                        //System.out.print("Enter New Email: ");
                        //String newEmail = sc.nextLine();

                        System.out.print("Enter New Phone: ");
                        String newPhone = sc.nextLine();

                        System.out.print("Enter New Designation: ");
                        String newDesignation = sc.nextLine();

                        //System.out.print("Enter New Joining Date (YYYY-MM-DD): ");
                        //Date newJoiningDate = Date.valueOf(sc.nextLine());

                        System.out.print("Enter New Basic Salary: ");
                        double newBasicSalary = sc.nextDouble();

                        System.out.print("Enter New Department ID: ");
                        int newDepartmentId = sc.nextInt();

                        //existingEmployee.setName(newName);
                        //existingEmployee.setEmail(newEmail);
                        existingEmployee.setPhone(newPhone);
                        existingEmployee.setDesignation(newDesignation);
                        //existingEmployee.setJoiningDate(newJoiningDate);
                        existingEmployee.setBasicSalary(newBasicSalary);
                        existingEmployee.setDepartmentId(newDepartmentId);

                        boolean updateResult =
                                employeeDAO.updateEmployee(existingEmployee);

                        if (updateResult) {

                            System.out.println("Employee Updated Successfully!");

                        } else {

                            System.out.println("Failed to Update Employee!");
                        }
                    }

                    break;

                case 5:

                	System.out.print("Enter Employee ID to Delete: ");
                    int deleteId = sc.nextInt();

                    Employee deleteEmployee =
                            employeeDAO.getEmployeeById(deleteId);

                    if (deleteEmployee == null) {

                        System.out.println("Employee Not Found!");

                    } else {

                        System.out.println(
                                "Employee Found: " + deleteEmployee.getName());

                        System.out.print(
                                "Are you sure you want to delete? (yes/no): ");

                        sc.nextLine();
                        String confirm = sc.nextLine();

                        if (confirm.equalsIgnoreCase("yes")) {

                            boolean deleteResult =
                                    employeeDAO.deleteEmployee(deleteId);

                            if (deleteResult) {

                                System.out.println(
                                        "Employee Deleted Successfully!");

                            } else {

                                System.out.println(
                                        "Failed to Delete Employee!");
                            }

                        } else {

                            System.out.println("Delete Cancelled.");
                        }
                    }

                    break;

                case 6:

                	sc.nextLine();

                    System.out.print("Enter Employee Name to Search: ");
                    String searchName = sc.nextLine();
                    
                    if (searchName.trim().isEmpty()) {

                        System.out.println(
                                "Employee name cannot be empty.");

                        break;
                    }
                    
                    List<Employee> searchResults =
                            employeeDAO.searchEmployeesByName(searchName);

                    if (searchResults.isEmpty()) {

                        System.out.println("No Employee Found!");

                    } else {

                        System.out.println();
                        System.out.println("========== SEARCH RESULTS ==========");

                        for (Employee emp1 : searchResults) {

                            System.out.println("-------------------------------");
                            System.out.println("Employee ID   : " + emp1.getEmployeeId());
                            System.out.println("Name          : " + emp1.getName());
                            System.out.println("Email         : " + emp1.getEmail());
                            System.out.println("Phone         : " + emp1.getPhone());
                            System.out.println("Designation   : " + emp1.getDesignation());
                            System.out.println("Joining Date  : " + emp1.getJoiningDate());
                            System.out.println("Basic Salary  : " + emp1.getBasicSalary());
                            System.out.println("Department ID : " + emp1.getDepartmentId());
                        }
                    }

                    break;

                case 7:

                	List<Department> departments =  departmentDAO.getAllDepartments();

            System.out.println();
            System.out.println("========== DEPARTMENTS ==========");

            for (Department dept : departments) {

                System.out.println(
                        dept.getDepartmentId()
                        + " - "
                        + dept.getDepartmentName());
            }

            break;
            
                case 8:

                	System.out.print("Enter Employee ID: ");
                    int attendanceEmployeeId = sc.nextInt();

                    // Check whether employee exists
                    Employee attendanceEmployee =
                            employeeDAO.getEmployeeById(attendanceEmployeeId);

                    if (attendanceEmployee == null) {

                        System.out.println("Employee Not Found!");

                        break;
                    }

                    sc.nextLine();

                    System.out.print("Enter Attendance Date (YYYY-MM-DD): ");
                    Date attendanceDate = Date.valueOf(sc.nextLine());
                    
                    if (attendanceDAO.attendanceExists(
                            attendanceEmployeeId,
                            attendanceDate)) {

                        System.out.println(
                                "Attendance already exists for this date.");

                        break;
                    }

                    String status;

                    // Check whether the date is Sunday
                    if (attendanceDate.toLocalDate().getDayOfWeek()
                            == java.time.DayOfWeek.SUNDAY) {

                        status = "HOLIDAY";

                        System.out.println(
                                "Sunday detected. Attendance marked as HOLIDAY.");

                    } else {

                        System.out.println("1. PRESENT");
                        System.out.println("2. ABSENT");

                        System.out.print("Enter Attendance Status: ");
                        int attendanceChoice = sc.nextInt();

                        if (attendanceChoice == 1) {

                            status = "PRESENT";

                        } else if (attendanceChoice == 2) {

                            status = "ABSENT";

                        } else {

                            System.out.println("Invalid Attendance Choice!");
                            break;
                        }
                    }

                    Attendance attendance = new Attendance(attendanceEmployeeId, attendanceDate, status);
                    boolean attendanceResult = attendanceDAO.markAttendance(attendance);

                    if (attendanceResult) {

                        System.out.println("Attendance Marked Successfully!");

                    } else {

                        System.out.println("Failed to Mark Attendance!");
                    }

                    break;
        		
                case 9:

                    System.out.print("Enter Employee ID: ");
                    int viewAttendanceEmployeeId = sc.nextInt();

                    Employee attendanceEmp =
                            employeeDAO.getEmployeeById(viewAttendanceEmployeeId);

                    if (attendanceEmp == null) {

                        System.out.println("Employee Not Found!");
                        break;
                    }

                    List<Attendance> attendanceList =
                            attendanceDAO.getAttendanceByEmployeeId(
                                    viewAttendanceEmployeeId);

                    if (attendanceList.isEmpty()) {

                        System.out.println("No Attendance Records Found!");

                    } else {

                        System.out.println();
                        System.out.println("========== ATTENDANCE ==========");
                        System.out.println("Employee: " + attendanceEmp.getName());

                        for (Attendance att : attendanceList) {

                            System.out.println(
                                    att.getAttendanceDate()
                                    + "  ->  "
                                    + att.getStatus());
                        }
                    }

                    break;
                    
                case 10:

                    System.out.print("Enter Employee ID: ");
                    int reportEmployeeId = sc.nextInt();

                    Employee reportEmployee =
                            employeeDAO.getEmployeeById(reportEmployeeId);

                    if (reportEmployee == null) {

                        System.out.println("Employee Not Found!");
                        break;
                    }

                    System.out.print("Enter Month (1-12): ");
                    int month = sc.nextInt();

                    System.out.print("Enter Year: ");
                    int year = sc.nextInt();

                    List<Attendance> monthlyList =
                            attendanceDAO.getMonthlyAttendance(
                                    reportEmployeeId, month, year);

                    if (monthlyList.isEmpty()) {

                        System.out.println("No Attendance Records Found!");

                    } else {

                        int present = 0;
                        int absent = 0;
                        int holiday = 0;
                        int leave = 0;

                        System.out.println();
                        System.out.println("======================================");
                        System.out.println("       MONTHLY ATTENDANCE REPORT");
                        System.out.println("======================================");

                        System.out.println(
                                "Employee : " + reportEmployee.getName());

                        System.out.println(
                                "Month    : " + month + "/" + year);

                        System.out.println();

                        for (Attendance att : monthlyList) {

                            System.out.println(
                                    att.getAttendanceDate()
                                    + "  ->  "
                                    + att.getStatus());

                            if (att.getStatus().equalsIgnoreCase("PRESENT")) {

                                present++;

                            } else if (att.getStatus().equalsIgnoreCase("ABSENT")) {

                                absent++;

                            } else if (att.getStatus().equalsIgnoreCase("HOLIDAY")) {

                                holiday++;

                            } else if (att.getStatus().equalsIgnoreCase("LEAVE")) {

                                leave++;
                            }
                        }

                        System.out.println();
                        System.out.println("----------- SUMMARY -----------");
                        System.out.println("Present : " + present);
                        System.out.println("Absent  : " + absent);
                        System.out.println("Leave   : " + leave);
                        System.out.println("Holiday : " + holiday);
                    }

                    break;
                  
                case 11:

                    System.out.print("Enter Employee ID: ");
                    int leaveEmployeeId = sc.nextInt();

                    Employee leaveEmployee =
                            employeeDAO.getEmployeeById(leaveEmployeeId);

                    if (leaveEmployee == null) {

                        System.out.println("Employee Not Found!");
                        break;
                    }

                    sc.nextLine();

                    System.out.print("Enter From Date (YYYY-MM-DD): ");
                    Date fromDate = Date.valueOf(sc.nextLine());

                    System.out.print("Enter To Date (YYYY-MM-DD): ");
                    Date toDate = Date.valueOf(sc.nextLine());
                    
                    if (toDate.before(fromDate)) {

                        System.out.println(
                                "To Date cannot be before From Date.");

                        break;
                    }

                    System.out.print("Enter Leave Type: ");
                    String leaveType = sc.nextLine();

                    System.out.print("Enter Reason: ");
                    String reason = sc.nextLine();

                    LeaveRecord leave = new LeaveRecord();

                    leave.setEmployeeId(leaveEmployeeId);
                    leave.setFromDate(fromDate);
                    leave.setToDate(toDate);
                    leave.setLeaveType(leaveType);
                    leave.setReason(reason);
                    leave.setStatus("PENDING");

                    boolean leaveResult = leaveDAO.applyLeave(leave);

                    if (leaveResult) {

                        System.out.println("Leave Applied Successfully!");
                        System.out.println("Leave Status: PENDING");

                    } else {

                        System.out.println("Failed to Apply Leave!");
                    }

                    break;
                  
                case 12:

                    System.out.print("Enter Employee ID: ");
                    int viewLeaveEmployeeId = sc.nextInt();

                    Employee leaveEmp =
                            employeeDAO.getEmployeeById(viewLeaveEmployeeId);

                    if (leaveEmp == null) {

                        System.out.println("Employee Not Found!");
                        break;
                    }

                    List<LeaveRecord> leaveList =
                            leaveDAO.getLeavesByEmployeeId(viewLeaveEmployeeId);

                    if (leaveList.isEmpty()) {

                        System.out.println("No Leave Records Found!");

                    } else {

                        System.out.println();
                        System.out.println("========== LEAVE DETAILS ==========");

                        System.out.println("Employee : " + leaveEmp.getName());

                        for (LeaveRecord leave1 : leaveList) {

                            System.out.println("-------------------------------");
                            System.out.println("Leave ID   : " + leave1.getLeaveId());
                            System.out.println("From Date  : " + leave1.getFromDate());
                            System.out.println("To Date    : " + leave1.getToDate());
                            System.out.println("Leave Type : " + leave1.getLeaveType());
                            System.out.println("Reason     : " + leave1.getReason());
                            System.out.println("Status     : " + leave1.getStatus());
                        }
                    }

                    break;
                    
                case 13:

                    List<LeaveRecord> pendingLeaves =
                            leaveDAO.getPendingLeaves();

                    if (pendingLeaves.isEmpty()) {

                        System.out.println("No Pending Leave Requests!");

                    } else {

                        System.out.println();
                        System.out.println("========== PENDING LEAVES ==========");

                        for (LeaveRecord leave1 : pendingLeaves) {

                            System.out.println("-------------------------------");
                            System.out.println("Leave ID     : " + leave1.getLeaveId());
                            System.out.println("Employee ID  : " + leave1.getEmployeeId());
                            System.out.println("From Date    : " + leave1.getFromDate());
                            System.out.println("To Date      : " + leave1.getToDate());
                            System.out.println("Leave Type   : " + leave1.getLeaveType());
                            System.out.println("Reason       : " + leave1.getReason());
                            System.out.println("Status       : " + leave1.getStatus());
                        }
                    }

                    break;
                    
                case 14:

                    System.out.print("Enter Leave ID: ");
                    int leaveId = sc.nextInt();

                    System.out.println("1. APPROVE");
                    System.out.println("2. REJECT");

                    System.out.print("Enter your choice: ");
                    int leaveChoice = sc.nextInt();

                    if (leaveChoice == 1) {

                        boolean result1 = leaveDAO.approveLeave(leaveId);

                        if (result1) {

                            System.out.println("Leave Approved Successfully!");
                            System.out.println(
                                    "Attendance updated for approved leave.");

                        } else {

                            System.out.println(
                                    "Leave approval failed!");
                        }

                    } else if (leaveChoice == 2) {

                        boolean result1 = leaveDAO.rejectLeave(leaveId);

                        if (result1) {

                            System.out.println("Leave Rejected Successfully!");

                        } else {

                            System.out.println(
                                    "Leave rejection failed!");
                        }

                    } else {

                        System.out.println("Invalid Choice!");
                    }

                    break;
                    
                case 15:

                	System.out.print("Enter Employee ID: ");
                    int salaryEmployeeId = sc.nextInt();

                    Employee salaryEmployee =
                            employeeDAO.getEmployeeById(salaryEmployeeId);

                    if (salaryEmployee == null) {

                        System.out.println("Employee Not Found!");
                        break;
                    }

                    System.out.print("Enter Month (1-12): ");
                    int salaryMonthNumber = sc.nextInt();

                    // POINT 1: Validate month
                    if (salaryMonthNumber < 1 || salaryMonthNumber > 12) {

                        System.out.println("Invalid month!");
                        break;
                    }

                    System.out.print("Enter Year: ");
                    int salaryYear = sc.nextInt();

                    // POINT 2: Validate year
                    if (salaryYear < 2000 || salaryYear > 2100) {

                        System.out.println("Invalid year!");
                        break;
                    }

                    List<Attendance> salaryAttendance =
                            attendanceDAO.getMonthlyAttendance(
                                    salaryEmployeeId,
                                    salaryMonthNumber,
                                    salaryYear);

                    // POINT 4: Check whether attendance exists
                    if (salaryAttendance.isEmpty()) {

                        System.out.println(
                                "No attendance records found for this month.");

                        break;
                    }

                    int presentDays = 0;
                    int absentDays = 0;
                    int leaveDays = 0;
                    int holidayDays = 0;

                    for (Attendance att : salaryAttendance) {

                        if (att.getStatus().equalsIgnoreCase("PRESENT")) {

                            presentDays++;

                        } else if (att.getStatus().equalsIgnoreCase("ABSENT")) {

                            absentDays++;

                        } else if (att.getStatus().equalsIgnoreCase("LEAVE")) {

                            leaveDays++;

                        } else if (att.getStatus().equalsIgnoreCase("HOLIDAY")) {

                            holidayDays++;
                        }
                    }

                    // Calculate actual working days
                    int workingDays =
                            DateUtil.getWorkingDays(
                                    salaryMonthNumber,
                                    salaryYear);

                    // POINT 3: Attendance completeness validation
                    if (presentDays + absentDays + leaveDays != workingDays) {

                        System.out.println(
                                "Attendance is incomplete for this month.");

                        break;
                    }

                    // Salary calculation
                    double basicSalary1 =
                            salaryEmployee.getBasicSalary();

                    double dailySalary = 0;

                    if (workingDays > 0) {

                        dailySalary =
                                basicSalary1 / workingDays;
                    }

                    double deductions =
                            absentDays * dailySalary;

                    double netSalary =
                            basicSalary1 - deductions;

                    String salaryMonth =
                            salaryMonthNumber + "/" + salaryYear;
                    
                    if (salaryDAO.salaryExists(
                            salaryEmployeeId,
                            salaryMonth)) {

                        System.out.println(
                                "Salary already generated for this month.");

                        break;
                    }

                    Salary salary = new Salary();

                    salary.setEmployeeId(salaryEmployeeId);
                    salary.setSalaryMonth(salaryMonth);
                    salary.setWorkingDays(workingDays);
                    salary.setPresentDays(presentDays);
                    salary.setAbsentDays(absentDays);
                    salary.setLeaveDays(leaveDays);
                    salary.setBasicSalary(basicSalary1);
                    salary.setDeductions(deductions);
                    salary.setNetSalary(netSalary);

                    boolean salaryResult =
                            salaryDAO.saveSalary(salary);

                    if (salaryResult) {

                        System.out.println();
                        System.out.println("========== SALARY DETAILS ==========");

                        System.out.println("Employee      : "
                                + salaryEmployee.getName());

                        System.out.println("Month         : "
                                + salaryMonth);

                        System.out.println("Working Days  : "
                                + workingDays);

                        System.out.println("Present Days  : "
                                + presentDays);

                        System.out.println("Absent Days   : "
                                + absentDays);

                        System.out.println("Leave Days    : "
                                + leaveDays);

                        System.out.println("Holiday Days  : "
                                + holidayDays);

                        System.out.println("Basic Salary  : "
                                + basicSalary1);

                        System.out.println("Deductions    : "
                                + deductions);

                        System.out.println("Net Salary    : "
                                + netSalary);

                        System.out.println("Salary Saved Successfully!");

                    } else {

                        System.out.println("Failed to Calculate Salary!");
                    }

                    break;
                    
                case 16:

                    System.out.print("Enter Employee ID: ");
                    int reportSalaryEmployeeId = sc.nextInt();

                    Employee salaryReportEmployee =
                            employeeDAO.getEmployeeById(reportSalaryEmployeeId);

                    if (salaryReportEmployee == null) {

                        System.out.println("Employee Not Found!");
                        break;
                    }

                    List<Salary> salaryList =
                            salaryDAO.getSalaryByEmployeeId(
                                    reportSalaryEmployeeId);

                    if (salaryList.isEmpty()) {

                        System.out.println("No Salary Records Found!");

                    } else {

                        System.out.println();
                        System.out.println("==============================================");
                        System.out.println("              SALARY REPORT");
                        System.out.println("==============================================");

                        System.out.println(
                                "Employee ID : "
                                + salaryReportEmployee.getEmployeeId());

                        System.out.println(
                                "Employee Name : "
                                + salaryReportEmployee.getName());

                        for (Salary salary1 : salaryList) {

                            System.out.println();
                            System.out.println("----------------------------------------------");

                            System.out.println(
                                    "Salary ID     : " + salary1.getSalaryId());

                            System.out.println(
                                    "Salary Month  : " + salary1.getSalaryMonth());

                            System.out.println(
                                    "Working Days  : " + salary1.getWorkingDays());

                            System.out.println(
                                    "Present Days  : " + salary1.getPresentDays());

                            System.out.println(
                                    "Absent Days   : " + salary1.getAbsentDays());

                            System.out.println(
                                    "Leave Days    : " + salary1.getLeaveDays());

                            System.out.println(
                                    "Basic Salary  : " + salary1.getBasicSalary());

                            System.out.println(
                                    "Deductions    : " + salary1.getDeductions());

                            System.out.println(
                                    "Net Salary    : " + salary1.getNetSalary());
                        }
                    }

                    break;
                    
                default:

                    System.out.println("Thank you for using Employee Management System!");
            }

        } while (choice != 17);

        sc.close();
    }
}
