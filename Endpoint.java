import java.util.ArrayList;
import java.util.HashMap;

public class Endpoint {

	public ArrayList<Video> requests;
	public HashMap<Integer,Integer> videoMap = new HashMap<>();
	public ArrayList<Link> caches;
	int mainLatency;
	public Endpoint(int l, int cacheCount)
	{
		mainLatency = l;
		caches = new ArrayList<>();
		requests = new ArrayList<>();
	}
	public void updateCaches(Cache[] globalC) {

		
			
			for(int i = 0 ; i < caches.size() ; i++)
			{
				Link link = caches.get(i);
				for(int k = 0 ; k < requests.size() ; k++)
				{
					 Video temp = requests.get(k);
					 Score s = globalC[link.cacheID].map.get((temp.videoID));
					 if(s == null)
					 {	 
						 s = new Score();
						 s.videoID = temp.videoID;
						 globalC[link.cacheID].map.put(temp.videoID,s);
					 }
					 s.score += temp.viewCount * (mainLatency - link.latency);
					
				}
				
			}
			
		
		
	}
	
}
