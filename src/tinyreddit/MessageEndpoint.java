
package tinyreddit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

//_ah/api/explorer

@Api(name = "messageendpoint", namespace = @ApiNamespace(ownerDomain = "mycompany.com", ownerName = "mycompany.com", packagePath="services"))
public class MessageEndpoint {

  @ApiMethod(name = "CreerMessage")
	  public Entity CreerMessage(@Named("userId") String userId, @Named("texte") String texte) {
		  DatastoreService ds = DatastoreServiceFactory.getDatastoreService(); 
		  Random r = new Random();
		  int rand = r.nextInt();
		  Entity m = new Entity("Message", "message" + rand);
		  
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	      Calendar cal = Calendar.getInstance();
	      m.setProperty("date", dateFormat.format(cal.getTime()));
		  m.setProperty("text", texte);
		  m.setProperty("sender", userId);
		  m.setProperty("idMess", "message" + rand);
		  m.setProperty("nbVotant", 0);
		  ds.put(m);
		  
		  Entity Votantm = new Entity("VotantMessage", "message" + rand + "vot", m.getKey());
		  ds.put(Votantm);
		  return m;
		 }

  @ApiMethod(name="VoterPour")
  	public Entity VoterPlus(@Named("cle") String cle, @Named("userId") String userId) throws EntityNotFoundException{
	  
	  Entity message = null;

	  DatastoreService ds = DatastoreServiceFactory.getDatastoreService(); 
	  
	  String idmessage = cle + "vot";	  
	 Key keyMess = KeyFactory.createKey("Message", cle);
	 message = ds.get(keyMess);
	 
	  Key keyVotant = KeyFactory.createKey(message.getKey(),"VotantMessage",idmessage);
	  Entity VotantMessage = ds.get(keyVotant);
	  
	  long nbVot = (long)message.getProperty("nbVotant");
	  
	  List<String>VotantPlus = new ArrayList<String>();
	  if (VotantMessage.getProperty("votplus") == null){		  
		  VotantMessage.setProperty("votplus", VotantPlus);  
	  }else{
		 VotantPlus = (List<String>)VotantMessage.getProperty("votplus");
	  }
	  
	  List<String>VotantMoins = new ArrayList<String>();
	  if (VotantMessage.getProperty("votmoins") == null){		  
		  VotantMessage.setProperty("votmoins", VotantMoins);  
	  }else{
		 VotantMoins = (List<String>)VotantMessage.getProperty("votmoins");
	  }
	  
	  if(!VotantMoins.contains(userId)){
		 if(!VotantPlus.contains(userId)){
			 VotantPlus.add(userId);
			 message.setProperty("nbVotant", nbVot + 1);
		 }
	  }else{
		  VotantMoins.remove(userId);
		  message.setProperty("nbVotant", nbVot + 1);
	  }
	  
	  ds.put(message);
	  ds.put(VotantMessage);
	  return message;
  }
  
  @ApiMethod(name="MesVote")
  public Collection<Entity> MesVote(@Named("user") String user){
	  List<Entity> res = null;
	  DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	  com.google.appengine.api.datastore.Query Query = null;
	  PreparedQuery PQuery = null;
	  Filter FiltrePlus = null;
	  Filter FiltreMoins = null;
	  
	  FiltrePlus = new FilterPredicate("votplus", FilterOperator.EQUAL, user);
	  FiltreMoins = new FilterPredicate("votmoins", FilterOperator.EQUAL, user);
	  CompositeFilter F = CompositeFilterOperator.or(FiltrePlus, FiltreMoins);
	  Query = new com.google.appengine.api.datastore.Query("VotantMessage").setFilter(F);
	  Query.setKeysOnly();
	  PQuery = ds.prepare(Query);			
	  res=PQuery.asList(FetchOptions.Builder.withLimit(200));
	  
	  List<Key>Mescles = new ArrayList<Key>();
	  for(Entity r : res){
		  Mescles.add(r.getParent());
	  }
	  
	  Map<Key, Entity> resultat = ds.get(Mescles);
	  return resultat.values();
  }
  
  @ApiMethod(name="VoterContre")
	public Entity VoterContre(@Named("cle") String cle, @Named("userId") String userId) throws EntityNotFoundException{
	  Entity message = null;

	  DatastoreService ds = DatastoreServiceFactory.getDatastoreService(); 
	  
	  String idmessage = cle + "vot";	  
	 Key keyMess = KeyFactory.createKey("Message", cle);
	 message = ds.get(keyMess);
	 
	  Key keyVotant = KeyFactory.createKey(message.getKey(),"VotantMessage",idmessage);
	  Entity VotantMessage = ds.get(keyVotant);
	  
	  long nbVot = (long)message.getProperty("nbVotant");
	  
	  List<String>VotantPlus = new ArrayList<String>();
	  if (VotantMessage.getProperty("votplus") == null){		  
		  VotantMessage.setProperty("votplus", VotantPlus);  
	  }else{
		 VotantPlus = (List<String>)VotantMessage.getProperty("votplus");
	  }
	  List<String>VotantMoins = new ArrayList<String>();
	  if (VotantMessage.getProperty("votmoins") == null){		  
		  VotantMessage.setProperty("votmoins", VotantMoins);  
	  }else{
		 VotantMoins = (List<String>)VotantMessage.getProperty("votmoins");
	  }
	  
	  if(!VotantPlus.contains(userId)){
		 if(!VotantMoins.contains(userId)){
			 VotantMoins.add(userId);
			  message.setProperty("nbVotant", nbVot - 1);

		 }
	  }else{
		  VotantPlus.remove(userId);
		  message.setProperty("nbVotant", nbVot - 1);

	  }
	  ds.put(message);
	  ds.put(VotantMessage);
	  return message;
  }
	
  @ApiMethod(name = "Tendance")
  public List<Entity> Tendance() {
	  List<Entity> res = null;
	  DatastoreService ds = DatastoreServiceFactory.getDatastoreService(); 
	  com.google.appengine.api.datastore.Query Query = null;
	  PreparedQuery PQuery = null;
	  
	  Query = new com.google.appengine.api.datastore.Query("Message").addSort("nbVotant", SortDirection.DESCENDING);
	  PQuery = ds.prepare(Query);			
	  res= PQuery.asList(FetchOptions.Builder.withLimit(200));
    	return res;
  }
  
  @ApiMethod(name = "MesMessages")
  public List<Entity> MesMessage(@Named("user") String user) {
	  List<Entity> res = null;
	  DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	  com.google.appengine.api.datastore.Query Query = null;
	  PreparedQuery PQuery = null;
	  Filter Filtre = null;
			    
	  Filtre = new FilterPredicate("sender", FilterOperator.EQUAL, user);
	  Query = new com.google.appengine.api.datastore.Query("Message").setFilter(Filtre);
	  PQuery = ds.prepare(Query);			
	  res=PQuery.asList(FetchOptions.Builder.withLimit(200));
	  
	  return res;
  }
  
}
