import javax.swing.JFrame;
import javax.swing.JFrame.*;
public class ClientTest {

	public static void main(String[] args) {
		Client client;
		client=new Client("127.0.0.1");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.startRunning(); // ჩათის დახურვის შემდეგ კლიენტი მზად არის ახალ სერვერთან ან იგივესთან კავშირისთვის
	}

}
