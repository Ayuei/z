import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;


public class Client implements Runnable{
	
	ArrayList<Peer> peersList;
	Peer currentUser;
	
	public Client(ArrayList<Peer> peers, Peer currentUser){
		this.peersList = peers;
		this.currentUser = currentUser;
	}
	
	@Override
	public void run() {
		DatagramSocket clientSocket;
		try{
			
			BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
			clientSocket = new DatagramSocket();
			currentUser.setPort(clientSocket.getLocalPort());
			while(true){
				
				System.out.print("Status: ");
				String data = bin.readLine();
				
				if(!(data.length() > 0)){
					System.err.println("Status is empty. Retry");
				}else if(data.length() > 140){
					System.err.println("Status too long, 140 characters max. Retry.");
				}else{
					currentUser.setStatus(data);
					send(clientSocket);
					printMessageQueue();
				}
			}
		}catch(Exception e){
			
		}
	}
	
	public void send(DatagramSocket clientSocket){
		try{
			String message = currentUser.getStatus().replace(":", "\\:");
			
			for(Peer p : peersList){
				byte[] data = new byte[1024];
				InetAddress ip = InetAddress.getByName(p.getIp());
				int seqNum = currentUser.getSeqNum()+1;
				currentUser.setSeqNum(seqNum);
				String msg = currentUser.getUnikey() + ":" + message + ":" +seqNum;
				data = message.getBytes("ISO-8859-1");
				
				//Change later, they must specify port to send.
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, 7014);
				clientSocket.send(packet);
			}
		} catch (Exception e) {
			
		}
	}
	//TODO: Seqnum.
	public void printMessageQueue(){
		System.out.println("### P2P tweets ###");
		System.out.println("# "+ currentUser.getPseudo()+" (myself)" + " : " +currentUser.getStatus());
		
		for(Peer p: peersList){
			//check spelling;
			if(p.getStatus() == null){
				System.out.println("# ["+p.getPseudo()+" ("+p.getUnikey()+") : not yet initialised]");
			}else if(System.currentTimeMillis() - p.getLastActive() < 10000){
				
				System.out.println("# "+p.getPseudo()+" (" + p.getUnikey()+ ") : "+p.getStatus());
			}else if(System.currentTimeMillis() - p.getLastActive() < 20000){
				System.out.println("# ["+p.getPseudo()+" ("+p.getUnikey()+") : idle]");
			}else{
				//ignore
			}
		}
	}
}
