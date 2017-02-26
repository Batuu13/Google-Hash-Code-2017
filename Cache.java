import java.util.ArrayList;
import java.util.HashMap;

public class Cache {
	public int ID;
	public int currentMB;
	public ArrayList<Integer> endpoints;
	public ArrayList<Integer> added;
	HashMap<Integer,Score> map = new HashMap<>();
	Cache(int defaultMB)
	{
		currentMB = 0;
		endpoints = new ArrayList<>();
		added = new ArrayList<>(); 
	}
	
	public void getVideo(Cache[] caches, Endpoint[] endpoints2, String[] videos, int maxMB) {
		
		while(map.size() > 0)
		{
			
			Score s = getMax(videos, maxMB);
			
		
		if(currentMB + Integer.parseInt(videos[s.videoID]) <= maxMB)
		{
			currentMB += Integer.parseInt(videos[s.videoID]);
			added.add(s.videoID);
		}
		map.remove(s.videoID);
			
		for(int i = 0 ; i <  endpoints.size(); i++)
		{
			/*int curLatency = 0;
			for(int k = 0 ; k < endpoints2[endpoints.get(i)].caches.size() ; k++)
			{
				if(endpoints2[endpoints.get(i)].caches.get(k).cacheID == ID)
					curLatency = endpoints2[endpoints.get(i)].caches.get(k).latency;
			}
			*/
			for(int j = 0 ; j < endpoints2[endpoints.get(i)].caches.size(); j++)
			{
				
				Link link = endpoints2[endpoints.get(i)].caches.get(j);
				
				if(endpoints2[endpoints.get(i)].videoMap.containsKey(s.videoID))
				{
					int view = endpoints2[endpoints.get(i)].videoMap.get(s.videoID);
					int score = view * (endpoints2[endpoints.get(i)].mainLatency - link.latency);
				Score otherS = caches[link.cacheID].map.get(s.videoID);
				if(otherS != null)
					otherS.score -= score;
				}
			}
		}
		}
	}

	private Score getMax(String[] videos, int maxMB)
	{
		 @SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayList<Score> list = new ArrayList(map.values());
		 
			 int max = 0 ;
			 int index = 0 ;
			for(int i = 0 ; i < list.size();i++)
			{
				if(currentMB + Integer.parseInt(videos[list.get(i).videoID]) > maxMB) // remove the video from the map if its so big that wont ever fit.
				{
					map.get(list.get(i).videoID).score = 0;
					map.remove(list.get(i).videoID);
					continue;
				}
				if(list.get(i).score  > max)
					{
					index = i;
					max = list.get(i).score ;
					}
			}
			return list.get(index);
	}
	
	
}
