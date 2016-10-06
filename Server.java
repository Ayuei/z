import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class Server implements Runnable{
	
	ArrayList<Peer> peers;
	Peer currentUser;
	
	public Server(ArrayList<Peer> peers, Peer currentUser){
		this.peers = peers;
		this.currentUser = currentUser;
	}
	
	@Override
	public void run() {
		DatagramSocket serverSocket;
		try{
			serverSocket = new DatagramSocket(7014);
			
			while(true){
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				serverSocket.receive(packet);
				//Unmarshalling.
				String text = packet.getData().toString();
				//TODO: Check if this works
				if(text == null || text.length() < 1 || text.indexOf(":") < 1){
					continue;
				}
				//Finding the seqnum and the status.
				int index = text.indexOf(":");
				int index2 = text.lastIndexOf(":");
				int seqNum = 0;
				String unikey, status;
				boolean flag = false;
				if(index != index2){
					//Seqnum
					unikey = text.substring(0, index);
					status = text.substring(index+1, text.length());
					seqNum = Integer.parseInt(text.substring(index2+1, text.length()).trim());
					flag = true;
				}else{
					//No seqnum
					unikey = text.substring(0, index);
					status = text.substring(index+1, text.length());
				}
				status = status.replace("\\:", ":");
				//Check if seqnum is larger, also check if logical clock increments upon receiving.
				//Should we handle we logical clock portion here or during message print?
				//Seems to be easier if we do it here.
				//Seems like we have to do it here if messages are not to be lost.
				for (Peer p : peers){
					if(unikey.equals(p.getUnikey())){
						if(flag == true){
							if(p.getSeqNum() < seqNum){
								p.setStatus(status);
								p.setLastActive(System.currentTimeMillis());
								p.setSeqNum(seqNum);
							}
						}else{
							p.setStatus(status);
							p.setLastActive(System.currentTimeMillis());
						}
						break;
					}
				}
			}
		}catch(Exception e){
			
		}
	}
	
}
