package networking;

//Rose Lin 
//Mr. Radulovic
//June 18th, 2019
//Culminating Project

public interface ConnectionListener {
	/**
	 * newConnection()
	 * This method will be automatically called by the Server to signal that a
	 * new Client has connected.
	 */
	public void newConnection(String name);
	
	/**
	 * lostConnection(String name)
	 * This method will be automatically called by the Server to signal that a
	 * Client connection has been closed.
	 * @param name - name of Client that closed the connection
	 */
	public void lostConnection(String name);
	
	/**
	 * messageToServer(String text)
	 * This method is automatically called by a Client when sending a message
	 * to the Server.
	 * @param text - message to send to Server
	 */
	public void messageToServer(String text);
}
