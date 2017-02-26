import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
class CacheScore implements Comparable<CacheScore>{
	public int cacheID;
	public int score;
	
	public int compareTo(CacheScore cache) {

		return this.score - cache.score;
	}
}


public class Main {
	
	static Cache[] caches;
	static String[] videos;
	static Endpoint[] endpoints;
	static int maxMB;
	public static void main(String[] args) {
		Charset charset = Charset.forName("US-ASCII");
		Path p = Paths.get("me_at_the_zoo.in");
		try (BufferedReader reader = Files.newBufferedReader(p, charset)) {
		    String line = null;
		    
		        String[] data = reader.readLine().split(" ");
		        videos = reader.readLine().split(" ");
		        caches = new Cache[Integer.parseInt(data[3])];
		        endpoints = new Endpoint[Integer.parseInt(data[1])];
		        maxMB = Integer.parseInt(data[4]);
		        
		        //  CRATE CACHES 		        	
		        for(int i = 0 ; i < caches.length ; i++)
		        {
		        	caches[i] = new Cache(Integer.parseInt(data[4]));
		        	caches[i].ID = i;
		        }
		        
		        for(int k = 0; k < Integer.parseInt(data[1]) ; k++)
		     {
		        	line = reader.readLine();
		        int mainLatency = Integer.parseInt(line.split(" ")[0]);
		        int cacheCount = Integer.parseInt(line.split(" ")[1]);
		        Endpoint e = new Endpoint(mainLatency, cacheCount); // CREATE ENDPOÝNT
		        
		        
		        for(int i = 0 ; i < cacheCount ; i++)
		        {
		        	String [] temp = reader.readLine().split(" ");
		        	int cacheID = Integer.parseInt(temp[0]);
		        	int latency = Integer.parseInt(temp[1]);

		        	Link link = new Link();
		        	link.cacheID = cacheID;
		        	link.latency = latency;
		        	e.caches.add(link);
		        	caches[link.cacheID].endpoints.add(k);
		        	
		        }
		        endpoints[k] = e;
		        
		     }
		     for(int i = 0 ; i < Integer.parseInt(data[2]) ; i++)
		        {
		    	 String [] temp = reader.readLine().split(" ");
		    	 int videoID = Integer.parseInt(temp[0]);
		    	 int endPointID = Integer.parseInt(temp[1]);
		    	 int viewCount = Integer.parseInt(temp[2]);
		    	 Video video = new Video(videoID,viewCount);// ADD VIDEO TO ENPOÝNT
		        endpoints[endPointID].requests.add(video);
		        endpoints[endPointID].videoMap.put(videoID, viewCount);
		        }
		     
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		InitializeScore();
		CacheScore [] temp = GetCacheList();
		GetVideos(temp);
		ListCaches();
		
	}
	
	private static CacheScore [] GetCacheList() {
		
			CacheScore [] cacheQeueu = new CacheScore[caches.length];
			
			for(int i = 0 ; i < caches.length ; i++)
			{
				int total = 0;
				ArrayList<Score> list = new ArrayList(caches[i].map.values());
				for(int k = 0 ; k < list.size() ; k++)
					
					total += list.get(k).score ;
				cacheQeueu[i] = new CacheScore();
				cacheQeueu[i].cacheID =i ;
				cacheQeueu[i].score = total;
			}
	/*		
		for(int i = 0 ; i < endpoints.length ; i++)
		{	
			for(int j = 0 ; j < endpoints[i].caches.size() ; j++)
			{
				
				
				cacheQeueu[id].score += endpoints[i].;
				
			}
		}
		*/
		Arrays.sort(cacheQeueu);
		return (cacheQeueu);
	}

	private static void ListCaches() {
		
		 Path p = Paths.get("./out.txt");
		 
		 Charset charset = Charset.forName("US-ASCII");
		 try (BufferedWriter writer = Files.newBufferedWriter(p, charset)) {
			    
			 writer.write(caches.length + "");
			 writer.newLine();
		for(int i = 0 ; i < caches.length ; i++)
		{
			 writer.write(i + " ");
			
			for(int j = 0 ; j < caches[i].added.size() ; j++)
			{
				
				
					 writer.write(caches[i].added.get(j) + " ");
			}
			 writer.newLine();
			
		}
		 } catch (IOException x) {
			    System.err.format("IOException: %s%n", x);
			}
	}
	private static void GetVideos(CacheScore [] list) {
		
		for(int i = 0; i < list.length; i++)
		{
			
			int id = list[i].cacheID;
			System.out.println("Cache ID: " + id);
			caches[id].getVideo(caches,endpoints,videos,maxMB);
			
		}
	}
	private static void InitializeScore() {
		for(int i = 0 ; i < endpoints.length ; i++)
		{
			System.out.println("Added Scores of endpoint Id: " + i);
			endpoints[i].updateCaches(caches);
			
		}
	}
	
	

}
