public class Peer{
		
		private String ip;
		private String pseudo;
		private String unikey;
		private int port;
		private String status;
		private long lastActive;
		private int seqNum;
		
		public Peer(String ip, String pseudo, String unikey, int port){
			this.ip = ip;
			this.pseudo = pseudo;
			this.unikey = unikey;
			this.port = port;
		}
		
		public void setSeqNum(int seqNum){
			this.seqNum = seqNum;
		}
		
		public int getSeqNum(){
			return seqNum;
		}
		
		public void setPort(int port){
			this.port = port;
		}
		
		public int getPort(){
			return this.port;
		}
		
		public void setStatus(String status){
			this.status = status;
		}
		
		public String getStatus() {
			return status;
		}
		
		public void setIp(String ip) {
			this.ip = ip;
		}
		
		public String getIp() {
			return ip;
		}
		
		public void setPseudo(String pseudo) {
			this.pseudo = pseudo;
		}
		
		public String getPseudo() {
			return pseudo;
		}
		
		public String getUnikey() {
			return unikey;
		}
		
		public void setUnikey(String unikey) {
			this.unikey = unikey;
		}
		
		public void setLastActive(long lastActive) {
			this.lastActive = lastActive;
		}
		
		public long getLastActive() {
			return lastActive;
		}
}