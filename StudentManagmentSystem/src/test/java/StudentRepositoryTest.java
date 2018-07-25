import org.junit.Test;
import repository.impl.StudentRepositoryImpl;

public class StudentRepositoryTest {

    StudentRepositoryImpl studentDAO=new StudentRepositoryImpl();

    @Test
    public void getStudents(){
        System.out.println(studentDAO.getAllStudents());
    }
}
