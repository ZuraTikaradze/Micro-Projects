package controller;

import model.Student;
import repository.StudentRepository;
import repository.impl.StudentRepositoryImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/StudentController")
public class StudentController extends HttpServlet {

    private StudentRepository studentRepository;

    public StudentController() {
        studentRepository = new StudentRepositoryImpl();
    }


    public static final String lIST_STUDENT = "/listStudent.jsp";
    public static final String INSERT_OR_EDIT = "/student.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");
        int studentId;

        switch (action) {
            case "delete":
                System.out.println("hello");
                forward = lIST_STUDENT;
                studentId = Integer.parseInt(req.getParameter("studentId"));//StudentController?action=edit&studentId=1   URL დან შემოსული id ია
                studentRepository.deleteStudent(studentId);
                req.setAttribute("students", studentRepository.getAllStudents());
                break;
            case "edit":
                System.out.println("edit");
                forward = INSERT_OR_EDIT;
                studentId = Integer.parseInt(req.getParameter("studentId"));
                Student student = studentRepository.getStudentById(studentId);
                req.setAttribute("student", student);
                break;
            case "insert":
                System.out.println("insert");
                forward = INSERT_OR_EDIT;
                break;
            default:
                System.out.println("all studetn");
                forward = lIST_STUDENT;
                req.setAttribute("students", studentRepository.getAllStudents());
                break;
        }
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();
        student.setName(req.getParameter("Name"));
        student.setSurname(req.getParameter("Surname"));
//        student.setAge(Integer.parseInt(req.getParameter("Age")));
        String studentId = req.getParameter("Id");

        if (studentId == null || studentId.isEmpty())
            studentRepository.addStudent(student);
        else {
            student.setId(Integer.parseInt(studentId));
            studentRepository.updateStudent(student);
        }
        RequestDispatcher view = req.getRequestDispatcher(lIST_STUDENT);
        req.setAttribute("students", studentRepository.getAllStudents());
        view.forward(req, resp);
    }

}
