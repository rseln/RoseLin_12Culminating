package drawing;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServerConnection  {
	private String serverName = "Server1";
	private Socket socket;
	private DataOutputStream dataOutStream;
	private DataInputStream dataInStream;
	
	private Server server;	// server that this socket belongs to
	
	private String clientName;	// name of person we are connected to
	boolean closeConnection;
	
	public ServerConnection(final Socket socket)
	{
		this.socket = socket;
	}
	
	private void go()
	{
		closeConnection = false;
		
		OutputStream outStream;	
		InputStream inStream;
		try {
			outStream = socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			
			inStream = socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			write(serverName);
		
		Thread sender = new Thread(new Runnable() {  // not used...except to send client name to server
            	@Override
            	public void run() {
            		String msg;
            			
            		 //Now send some data to client from the console...
        			Scanner scan = new Scanner(System.in);
        			
            		while(!closeConnection)
            		{
            			// initially, send our name to client
                		write(serverName);
            			while(scan.hasNextLine())
        			{
        				String str = scan.nextLine();
        				if(str.endsWith("QUIT")) {
        					write(str);
        					closeConnection = true;
        					break;
        				}
        				write(str);
        			}
          		}
           	}	
           });
            sender.start();
            
            Thread receiver = new Thread(new Runnable() {
            	@Override
            	public void run() {
            		String msg;
            		// initially, get name of client
            		clientName = read();
            		server.newConnection(clientName);  // notify listeners
            		
            		while(!closeConnection)
            		{
            			if(socket.isClosed())
            			{
            				closeConnection = true;
            				break;
            			}
            			
            			msg = read();
            			System.out.println(clientName + "> " + msg);
            			
            			if(msg.endsWith("QUIT"))
            			{	
            				write("Bye.");
            				closeConnection = true;
            				
            				server.lostConnection(clientName);
            				
            			}else
            			{
            				server.messageToServer(clientName + "> " + msg + "\n");
            			}
            		}
            		close();
            	}	
            });
            receiver.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setServer(Server s)
	{
		server = s;
		go();
	}
	
	public void write(String text)
	{
		try {
			dataOutStream.writeUTF(text);
		} catch (IOException e) {
			e.printStackTrace();
			closeConnection = true;
		}
	}
	
	public String read()
	{
		String str = "";
		try {
			str = dataInStream.readUTF();
		} catch (IOException e) {
			//e.printStackTrace();
			closeConnection = true;
			close();
		}
		
		return str;
	}
	
	public void close()
	{
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String name) {
		this.clientName = name;
	}

}
