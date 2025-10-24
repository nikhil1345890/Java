
import java.sql.*;
import java.util.*;
class Student {
private int studentID;
private String name;
private String department;
private double marks;
public Student(int studentID, String name, String department, double marks) {
this.studentID = studentID;
this.name = name;
this.department = department;
this.marks = marks;
}
public int getStudentID() { return studentID; }
public String getName() { return name; }
public String getDepartment() { return department; }
public double getMarks() { return marks; }
public void setName(String name) { this.name = name; }
public void setDepartment(String department) { this.department = department; }
public void setMarks(double marks) { this.marks = marks; }
@Override
public String toString() { return studentID + "\t" + name + "\t" + department + "\t" + marks; }
}
class StudentDAO {
private static final String URL = "jdbc:mysql://bytexldb.com:5051/db_43zqse8p4";
private static final String USER = "user_43zqse8p4";
private static final String PASS = "p43zse8p4";
private Connection connect() throws SQLException { return DriverManager.getConnection(URL, USER, PASS); }
public void addStudent(Student s) {
String sql = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
ps.setInt(1, s.getStudentID());
ps.setString(2, s.getName());
ps.setString(3, s.getDepartment());
ps.setDouble(4, s.getMarks());
ps.executeUpdate();
System.out.println("✅ Student added successfully.");
} catch (SQLException e) { e.printStackTrace(); }
}
public List<Student> getAllStudents() {
List<Student> list = new ArrayList<>();
String sql = "SELECT * FROM Student";
try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
Student s = new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getDouble("Marks"));
list.add(s);
}
} catch (SQLException e) { e.printStackTrace(); }
return list;
}
public void updateStudent(Student s) {
String sql = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
ps.setString(1, s.getName());
ps.setString(2, s.getDepartment());
ps.setDouble(3, s.getMarks());
ps.setInt(4, s.getStudentID());
int rows = ps.executeUpdate();
if (rows > 0) System.out.println("✅ Student updated successfully.");
else System.out.println("❌ Student not found.");
} catch (SQLException e) { e.printStackTrace(); }
}
public void deleteStudent(int id) {
String sql = "DELETE FROM Student WHERE StudentID=?";
try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
ps.setInt(1, id);
int rows = ps.executeUpdate();
if (rows > 0) System.out.println("✅ Student deleted successfully.");
else System.out.println("❌ Student not found.");
} catch (SQLException e) { e.printStackTrace(); }
}
}
public class Main {
public static void main(String[] args) {
Scanner sc = new Scanner(System.in);
StudentDAO dao = new StudentDAO();
try { Class.forName("com.mysql.cj.jdbc.Driver"); System.out.println("✅ MySQL Driver Loaded Successfully!"); }
catch (ClassNotFoundException e) { System.out.println("❌ MySQL Driver not found!"); }
int choice;
do {
System.out.println("\n===== STUDENT MANAGEMENT MENU =====");
System.out.println("1. Add Student");
System.out.println("2. View All Students");
System.out.println("3. Update Student");
System.out.println("4. Delete Student");
System.out.println("5. Exit");
System.out.print("Enter choice: ");
choice = sc.nextInt();
sc.nextLine();
switch (choice) {
case 1:
System.out.print("Enter Student ID: ");
int id = sc.nextInt();
sc.nextLine();
System.out.print("Enter Name: ");
String name = sc.nextLine();
System.out.print("Enter Department: ");
String dept = sc.nextLine();
System.out.print("Enter Marks: ");
double marks = sc.nextDouble();
dao.addStudent(new Student(id, name, dept, marks));
break;
case 2:
List<Student> list = dao.getAllStudents();
System.out.println("\nID\tName\tDepartment\tMarks");
System.out.println("----------------------------------------");
for (Student s : list) System.out.println(s);
break;
case 3:
System.out.print("Enter Student ID to Update: ");
int uid = sc.nextInt();
sc.nextLine();
System.out.print("Enter New Name: ");
String newName = sc.nextLine();
System.out.print("Enter New Department: ");
String newDept = sc.nextLine();
System.out.print("Enter New Marks: ");
double newMarks = sc.nextDouble();
dao.updateStudent(new Student(uid, newName, newDept, newMarks));
break;
case 4:
System.out.print("Enter Student ID to Delete: ");
int did = sc.nextInt();
dao.deleteStudent(did);
break;
case 5:
System.out.println("👋 Exiting Application...");
break;
default:
System.out.println("❌ Invalid choice! Try again.");
}
} while (choice != 5);
sc.close();
}
}
