	package tinyreddit;

	import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

	@SuppressWarnings("serial")
	public class TinyRedditServlet extends HttpServlet {
		public void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
			/*String user1 = "u1";
			
			resp.setContentType("text/plain");		
			
			Random r=new Random();
			resp.getWriter().println("creating friends");
			

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			
			int max = 10;
			for (int i = 0; i < max; i++) {
				Entity m = new Entity("Message", "m" + i);
				
				m.setProperty("text", "Hello m" +i);
				m.setProperty("Sender", "u"+r.nextInt(100));
				

			Entity messIndex = new Entity("MessageIndex", "mIndex" + i, m.getKey() );
				ArrayList<String> Votplus = new ArrayList<String>();
				ArrayList<String> Votmoins = new ArrayList<String>();
				
				for (int j = 0; j < 10; j++) {
					String user = "u"+r.nextInt(5);
					Boolean PlusOuMoins = false;
					
					if (!PlusOuMoins){
						if (Votplus.contains(user)){
							Votplus.remove(user);
						}
						if (!Votmoins.contains(user)){
							Votmoins.add(user);
						}
						PlusOuMoins = true;
					}else{	
						if (Votmoins.contains(user)){
							Votmoins.remove(user);
						}
						if (!Votplus.contains(user)){
							Votplus.add(user);
						}
						PlusOuMoins = false;
					}
				}
				messIndex.setProperty("votplus", Votplus);
				messIndex.setProperty("votplus", Votmoins);	
				
				resp.getWriter().println("wrote:"+m.getKey()+","+messIndex.getKey());
				
				datastore.put(m);
				datastore.put(messIndex);
				
				}
			
			resp.getWriter().println("done");
/*			
			Filter propertyFilter = new FilterPredicate("Votant", FilterOperator.EQUAL, user1);
			Query q = new Query("Message").setFilter(propertyFilter);
			 PreparedQuery pq= datastore.prepare(q);
			 List<Entity> results=pq.asList(FetchOptions.Builder.withLimit(5));
				for (Entity res : results) {
					resp.getWriter().println("<li>:"+res);
				}
				
				resp.getWriter().println("finished");		
	*/
	}

}
