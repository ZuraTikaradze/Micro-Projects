package repository;

import model.Student;

import java.util.List;

// ინტერფეისი გამოიყენება მონაცემთა ბაზასთან სამუშაოდ
//CRUD / GET BY ID
public interface StudentRepository {
    void addStudent(Student student);

    void deleteStudent(int studentId);

    void updateStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(int studentId);
}
