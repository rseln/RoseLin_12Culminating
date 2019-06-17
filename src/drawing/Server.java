package drawing;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private static String serverName = "Server1";
	private String serverAddr;	// server address
	private int port;			// listening port
	private ServerSocket serverSocket;

	boolean closeConnection;
	List<ConnectionListener> listeners;
	
	private List<ServerConnection> connectionList;	// list of client-server connections for this Server
	
	public static void main(String[] args) 
	{
		Server server = new Server("localhost", 2000);
		
	}
	
	public Server(String serverName, int port)
	{
		this.serverName = serverName;
		this.serverAddr = serverName;
		this.port = port;
		
		connectionList = new ArrayList<ServerConnection>();
		listeners = new ArrayList<ConnectionListener>();
		
		boolean listening = true;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
	}
	
	public synchronized void addConnectionListener(ConnectionListener toAdd) 
	{
	        listeners.add(toAdd);
	}
	
	public synchronized void newConnection(String clientName) {
        // Notify everybody that may be interested.
        for (ConnectionListener cl : listeners)
            cl.newConnection(clientName);
    }
	
	public synchronized void lostConnection(String name) {
		
		// Notify everybody that may be interested.
        for (ConnectionListener cl : listeners)
            cl.lostConnection(name);
        removeConnectionList(name);
	}
	
	public synchronized void messageToServer(String name) {
		// Notify everybody that may be interested.
        for (ConnectionListener cl : listeners)
            cl.messageToServer(name);
	}
	
	public void WaitForConnections()
	{
		boolean listening = true;
		while (listening) {
			ServerConnection s;
			try {
				s = new ServerConnection(serverSocket.accept());
				s.setServer(this);
				connectionList.add(s);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getLastClientName()
	{
		return getClientName(connectionList.size()-1);
	}
	
	public String getClientName(int index)
	{
		if(index < connectionList.size())
			return(connectionList.get(index).getClientName());
		else
			return "Client not found.";
	}
	
	public ServerConnection getConnectionList(int i)
	{
		return connectionList.get(i);
	}
	
	/**
	 * Returns the ServerConnection with the specified client name, or null if not found
	 * @param clientName
	 * @return ServerConnection with the specified client name
	 */
	public ServerConnection getConnectionList(String clientName)
	{
		for(ServerConnection sc : connectionList)
		{
			if(sc.getClientName().equals(clientName))
				return sc;
		}
		return null;
	}
	
	/**
	 * Removes a ServerConnection with the specified client name.
	 * @param clientName
	 */
	public void removeConnectionList(String clientName)
	{
		int i=0;
		for(ServerConnection sc : connectionList)
		{
			if(sc.getClientName().equals(clientName))
			{
				connectionList.remove(i);
				return;
			}
			i++;
		}
	}
	
	/**
	 * Removes a ConnectionListener with the specified client name
         * @param clientName
	 */
	public synchronized void removeListener(int i)
	{
		if(listeners.size() > 0)
			listeners.remove(i);
	}
	
	public int numConnections()
	{
		return connectionList.size();
	}
	
	public ServerConnection getLastConnection()
	{
		if(connectionList.size() > 0)
			return connectionList.get(connectionList.size()-1);
		else
			return null;
	}
	
	public List<ServerConnection> getConnectionList()
	{
		return connectionList;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String name) {
		serverName = name;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}

