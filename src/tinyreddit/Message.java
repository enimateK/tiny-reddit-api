package tinyreddit;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Message {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	String idMess;
	@Persistent
	Date date;
	@Persistent
	String sender;
	@Persistent
	String text;
	@Persistent
	long nbVotant;

	
    public String getId() {
		return idMess;
	}
	public void setId(String id) {
		this.idMess = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setDate(Date d){
		this.date = d;
	}
	public Date getDate(){
		return this.date;
	}
    public long getnbVotantPlus() {
		return nbVotant;
	}
	public void VotantPlus(long n) {
		this.nbVotant++;
	}
    public void VotantMoins() {
		this.nbVotant--;
	}
    public void setnbVotant(int n){
    	this.nbVotant = n;
    }
}

