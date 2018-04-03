import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.*;

public class Server extends JFrame { // JFrame გავუკეთე მშობელ კლასად იმიტომ რომ გუი დავუწერო.

	private JTextField userText; // ვაცხადებ  ვიზუალს სადაც მომხმარებელმა უნდა შეიტანოს გასაგზავნი ტექსტი (მესიჯი)
	private JTextArea chatWindow; // სერვერის და მომხმარებლის მიმოწერის ფანჯარა;
	private ObjectOutputStream output; // ვაცხადებ გამავალ სტრიმს დასაკავშირებლად;
	private ObjectInputStream input; // ვაცხადებ შემომავალ  სტრიმს დასაკავშირებლად;
	private ServerSocket server;  // ვაცხადებ სერვერ სოკეტს;
	private Socket connection;	// ვაცხადებ სოკეტს
	

	// კონსტრუქტორი
	public Server(){
		super("სერვერი");//აპლიკაციის  სახელი;
		userText = new JTextField(); // ვქმნით ტექსტის შესაყვან ობიექტს;
		userText.setEditable(false); // თუ შემოსული (კავშირზე) არ არის მომხმარებელი ტექსტის დაწერის უფლება არ გვექნება
		userText.addActionListener(
				new ActionListener(){ 
					public void actionPerformed(ActionEvent event){ // როცა  მომხმარებელი შეიყვანს ტექსტს და დააჭერს ენთერს იმწუთას შესრულდება ეს მეთოდი
						sendMessage(event.getActionCommand()); // წერილის გაგზავნა
						userText.setText(""); // ცარიელი იმიტომ არის რო მესიჯს რო გავაგზავნი ჩემს ჩასაწერში ხო არაფერი არ ჩანს. საერთო ჩათის ფანჯარაში აისახება
					}
				}
		);
		add(userText,BorderLayout.NORTH); // დავამატოდ ეკრანზე 
		chatWindow=new JTextArea(); // ჩათის ფანჯარა
		add(new JScrollPane(chatWindow)); // სქროლ ბარი ჩათის ფანჯარაზე
		setSize(300,150); //ფანჯარის ზომა
		setVisible(true);		
	}
	public void startRunning(){
		try{
			server=new ServerSocket(6789,100);// სერვერის ობიექტის შექმნა  
			while(true){
				try{
					// კავშირი ვინმესთან 
					whaitForConnection();	 // თუ ვინმეს ვეჩათავები დამოცადოს;
					setupStreams();			// ვისთანაც ვჩათაობ შეაერთოს ჩემი და მისი pc;
					whileChatting();		//ირი კომპი როცა შეერტებულია მუდმივად რომ აგზავნონ შეტყობინებები;
				}catch(EOFException eofexc){
					showMessage("\n კავშირის დასასრული  !"); // კავშირის დასასრული
				}finally{
					closeCrap();  // ყველა შემთხვევაში დაიხუროს  
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
			
		}
	}
	//  კავშირზე გასვლის ლოდინი
	private void whaitForConnection() throws IOException{ 
		showMessage("ველოდებით კავშირის დამყარებას ...\n");
		connection=server.accept(); // კავშირზე თანხმობა; იტრიალებს ციკლში სანამ კავშირი არ დაადასტურდება // როცა დამყარდება კავშირი მხოლოდ მაშინ შეიქმნება სოკეტი
		showMessage("მყარდება კავშირი მომხმარებელთან:"+connection.getInetAddress().getHostName());
	}
	//   შემომავალი და გამავალი ნაკდები

	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush(); // ბუფერიდან ეგრევე ვყრით 
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n კავშირი დამყარებულია! \n");
	}
	

	//ჩათის მიმდინარეობა
	private void whileChatting() throws IOException{
		String message="კავშირი დამყარებულია !";
		sendMessage(message);
		ableToType(true);// შეგვიძლია თუ არა ტექსტის წერა...
		do{
			// კავშირის მომენტი
			try{
				message=(String) input.readObject(); // დაგვყავს სტრინაგად  მესიჯი :) 
				showMessage("\n"+message);
			}catch(ClassNotFoundException CNFExc){
				showMessage("\n რაღაც რიგზე ვერ არის ! კლიენტი მიუღებელ ობიექტს გვიგზავნის !");
				
			}
		}while(!message.equals("კლიენტი - END")); // როცა გვინდა კავშირი დასრულდეს END-ს ვწერთ :) 
	}

	// პროგრამის დახურვა  სთრიმის და სოკეტის დახურვა გამოსვლის შემდეგ;
	private void closeCrap(){
		showMessage("\n კავშირის დასასრული ...\n");
		ableToType(false);
		try{
			output.close(); // სტრიმს ხურავს
			input.close(); // სტრიმს ხურავს
			connection.close(); //სოკეტს დახურავს
			
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// გასაგზავნი  შეტყობინება  (შეტყობინების გაგზავნა)
	private void sendMessage(String message){// sendMessage და showMasige -განსხვავდება სენდ ჩატშია შოუ არა :) 
		try{
			output.writeObject(" სერვერი - "+ message);
			output.flush();
			showMessage("\n სერვერი - "+ message); // ეკრანზე რო ვნახოთ გაგზავნილი  მესიჯი
		}catch(IOException ioExc){
			chatWindow.append("\n ERROR");
		}
	}

	// ჩეთის აფდეითი
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(text);
					}
				}
				);
	}

	// თუ კავშირი არის მაშინ აქ მესიჯის დაწერის უფლება თუ არა არა
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);  // თუ კავშირია მაშინ გაეხსნება ფანჯარა თუ არა არ გაეხსნება  true or folse tof
					}
				}
			);
		
	}
	
}
