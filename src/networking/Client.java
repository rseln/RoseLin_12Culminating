package networking;
import java.io.DataInputStream; 
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import networking.Server;

public class Client {
	private String name;
	private String serverAddr;	// server address
	private int port;
	private Socket socket;
	private DataOutputStream dataOutStream;
	private DataInputStream dataInStream;
	private Server server;	// server that we are connected to
	
	private String serverName;	// name of person we are connected to
	
	boolean closeConnection;
	
	
	public Client(Server s)
	{
		server = s;
		serverAddr = s.getServerAddr();
		port = s.getPort();					// default port
		closeConnection = false;
	}
	
	public Client()
	{
		try {
			serverAddr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Client class Constructor generated ERROR while obtaining IP address of local host");
		}
		port = 2000;					// default port
		closeConnection = false;
	}
	
	public boolean connect()
	{
		OutputStream outStream;	
		InputStream inStream;
		try {
			socket = new Socket(serverAddr, port);
			outStream = socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			
			inStream = socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			// initially, send our name to server
			write(name);
			
			System.out.println("Start socket.");
			
			Thread sender = new Thread(new Runnable() {
            	@Override
            	public void run() {
            		String msg;
            		// initially, send our name to server
            		write(name);
            		
            		// Now send some data to server from the console...
        			Scanner scan = new Scanner(System.in);
        			
            		while(!closeConnection)
            		{
            			while(scan.hasNextLine())
	        			{
	        				String str = scan.nextLine();
	        				if(str.equals("QUIT")) {
	        					write(str);
	        					closeConnection = true;
	        					//close();
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
            		
            		// initially, get name of server            		
            		System.out.println("Start receiver");

            		closeConnection = socket.isClosed();
            		while(!closeConnection)
            		{
            			msg = read();
            			System.out.println(serverName + "> " + msg.trim());
            			if(msg.equals("Bye"))
            			{	
            				//write("Bye.");
            				closeConnection = true;
            				close();
            			}
            		}
            	}	
            });
            receiver.start();
            System.out.println("Done");
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void write(String text)
	{
		try {
			dataOutStream.writeUTF(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String read()
	{
		String str = "";
		try {
			if(!closeConnection)
				str = dataInStream.readUTF();
		} catch (IOException e) {
			//e.printStackTrace();
			closeConnection = true;
		}
		
		return str;
	}
	
	public void close()
	{
		try {
			closeConnection = true;
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getPort() {
		return socket.getPort();
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String clientName) {
		this.name = clientName;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}
	
	public void setServer(Server s)
	{
		server = s;
	}
	
}

