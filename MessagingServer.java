import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class MessagingServer implements Runnable{
	DatagramSocket socket;
	ArrayList<Peer> peers;
	Peer currentUser;
	
	public MessagingServer(ArrayList<Peer> peers, Peer currentUser){
		this.peers = peers;
		this.currentUser = currentUser;
	}
	
	public void run(){
		try{
			socket = new DatagramSocket();
			
			while(true){
				Random rand = new Random();
				Thread.sleep(rand.nextInt()*2000+1000);
				if(P2PTwitter.currentUser.getStatus().length() > 0){
					send(socket);
				}
			}
		}catch(Exception e){
			
		}
	}
	
	public void send(DatagramSocket clientSocket){
		try{
			String message = currentUser.getStatus().replace(":", "\\:");
			
			for(Peer p : peers){
				byte[] data = new byte[1024];
				InetAddress ip = InetAddress.getByName(p.getIp());
				
				String msg = currentUser.getUnikey() + ":" + message;
				data = message.getBytes("ISO-8859-1");
				
				//Change later, they must specify port to send.
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, 7014);
				clientSocket.send(packet);
			}
		} catch (Exception e) {
			
		}
	}
}
