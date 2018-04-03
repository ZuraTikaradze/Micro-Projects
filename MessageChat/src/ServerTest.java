import javax.swing.JFrame;

public class ServerTest {
		// სევერის გაშვება
	public static void main(String[] args) {
		Server server=new Server();
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		server.startRunning();
	}

}
