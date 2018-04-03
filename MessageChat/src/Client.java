
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Client extends JFrame {
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message="";
	private String serverIP;
	private Socket connection;
	
	public Client(String host){
		super("მომხმარებელი"); // აპლიკაციის სახელი;
		serverIP=host;
		userText=new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
			);
		add(userText,BorderLayout.NORTH);   // ვიზუალის ეკრანზე დამატება
		chatWindow=new JTextArea();
		add(new JScrollPane(chatWindow),BorderLayout.CENTER);
		setSize(300,150);
		setVisible(true);
	}
	//   სერვერთან კავშირის დამყარება
	public void startRunning(){
		try{
			connectToServer(); // სერვერთან კავშირი
			setupStreams(); // სტრიმების გახსნა
			whileChatting(); // ჩეთის მიმდინარეობა
		}catch(EOFException eofExc){
			showMessage("\n კავშირი არ არის!");
		}catch(IOException  ioExc){
			ioExc.printStackTrace();
		}finally{
			closeCrap();
		}
	}
	// სერვერთან კავშირის მეთოდი
	private void connectToServer()throws IOException{
		showMessage("ველოდებით კავშირის დამყარებას...\n");
		connection =new Socket(InetAddress.getByName(serverIP),6789);// სერვერთან დასაკავშირებლად გვჭირდება სერვერის იპ მისამართი და პორტი რაზეც გაეშვება ჩვენი აპლიკაცია
		showMessage("კავშირზეა:" + connection.getInetAddress().getHostName());
	}
	// სტრიმების გახსნა
	private void setupStreams() throws IOException{
		output=new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input=new ObjectInputStream(connection.getInputStream());
		showMessage("\n სტრიმი მზადაა ...\n");
	}
	// ჩეთის მიმდინარეობისთვის ციკლი .
	private void whileChatting()throws IOException{
		ableToType(true);// ჩათის მიმდინარეობისას უნდა შემეძლოს ტექსტის შეტანა ....
		do{
			try{
				message=(String) input.readObject(); // სტრიმში გაშვება
				showMessage("\n"+message); // მომხმარებელმა რო ნახოს რა დაწერა 
			}catch(ClassNotFoundException cnfExc){
				showMessage("\n ობიექტის ტიპი გაურკვეველია");
			}
			
		}while(!message.equals("სერვერი-END"));
	}
	// სტრიმების და სოკეტების დახურვა
	private void closeCrap(){
		showMessage("\n კავშირის დასასრული...");
		ableToType(false);// თუ შერვერი არ არის დაქოქილი ვერ  ჩავწერ ტექსტს
		try{
			output.close(); // გამავალის დახურვა
			input.close(); // შემომავალის დახურვა
			connection.close(); // კავშირის დახურვა
		}catch(IOException ioExc){
			ioExc.printStackTrace();
		}
	}
	// მესიჯის გაგზავნა სერვერისთვის
	private void sendMessage(String message){
		try{
			output.writeObject("კლიენტი - " + message);
			output.flush();
			showMessage("\n კლიენტი - " + message);
		}catch(IOException ioExc){
			chatWindow.append("\n რაღაც აფერხებს სერვერისთვის შეტყოინების გაგზავნას !");
		}
	}
	// მესიჯი ეკრანზე და შესაძლებელია თუ არა ჩათაობა
	private void showMessage(final String m){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(m);
					}
				}
		);
	}
	// მზად არის მომხმარებელი საჩათაოდ
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);
					}
				}
		);
	}
	
}
