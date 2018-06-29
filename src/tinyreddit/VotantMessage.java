package tinyreddit;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class VotantMessage {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	String idVotMess;
	@Persistent
	Message message;
	
	@Persistent
	List<String> votplus;
	@Persistent
	List<String> votmoins;	

	
	public List getvotplus() {
		return votplus;
	}
	public void addvotplus(String id) {
		if (votmoins.contains(id)){
			removevotmoins(id);
		}
		if (!votplus.contains(id)){
			votplus.add(id);
		}
	}
	public void removevotplus(String id){
		votplus.remove(new String(id));
	}
	public List getvotmoins() {
		return votmoins;
	}
	public void addvotmoins(String id) {
		if (votplus.contains(id)){
			removevotplus(id);
		}
		if (!votmoins.contains(id)){
			votmoins.add(id);
		}
	}
	public void removevotmoins(String id){
		votmoins.remove(new String(id));
	}

}
