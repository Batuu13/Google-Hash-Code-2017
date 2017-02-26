# Google-Hash-Code-2017

This the source code of our solution at Google Hash Code 2017

Extended Round: 2372746 Points. 192nd At Global. 

USAGE:
- Provide the input file and change the input file name in Main.java
- Output is in the out.txt


Algorithm's steps are following:
- 1) Calculate the scores of each video (Score = (mainLatency - localLatency) * viewCount) and put in a map in every related cache.
- 2) Select a cache to choose a video to store.
- 3) After selecting decrease score from other caches that are related to this cache (caches that are connected to endpoints of the initial cache)
- 4) After all videos are selected for that cache repeat number 2
- 5) after all caches are selected print selected videos for caches.

Cache choosing order mathers. That is why we are choosing caches in latency score order.

This can  be improved by selecting video sets in cachse in stead of selecting one by one. Best set that fits and gives the most score should be selected.



