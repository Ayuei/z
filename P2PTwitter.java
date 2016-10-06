import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

public class P2PTwitter {
	
	static ArrayList<Peer> peers;
	static Peer currentUser;
	
	public static void main(String[] args){
		if(args[0].length() > 0){
			checkParticipant(args[0]);
			
			Thread client = new Thread(new Client(peers, currentUser));
			Thread server = new Thread(new Server(peers, currentUser));
			Thread messaging = new Thread(new MessagingServer(peers, currentUser));
			
			client.start();
			server.start();
			messaging.start();
		}
	}
	
	public static void checkParticipant(String inputUnikey){
		try{
			peers = new ArrayList<Peer>();
			
			Properties p = new Properties();
			FileReader file = new FileReader("participants.properties");
			p.load(file);
			
			String[] peerList = p.getProperty("participants").split(",");
			boolean auth = false;
			for(int i = 0; i < peerList.length; i++){
		    	String ip = p.getProperty(peerList[i] +".ip");
		    	//check properties file
			    String pseudonym = p.getProperty(peerList[i] +".pseudo");
			    String unikey = p.getProperty(peerList[i] +".unikey");
			    int port = Integer.parseInt(p.getProperty(peerList[i] +".port"));

			    if(unikey.compareTo(inputUnikey) == 0){
			    	currentUser = new Peer(ip, pseudonym, unikey, port);
			    	auth = true;
			    }else{
			    	peers.add(new Peer(ip, pseudonym, unikey, port));
			    }
			}
		   
		   if(!auth){
		    System.exit(1);
		   }
			//Garbage collection
			file.close();
			p.clear();
		}catch(Exception e){
			
		}
	}
}
