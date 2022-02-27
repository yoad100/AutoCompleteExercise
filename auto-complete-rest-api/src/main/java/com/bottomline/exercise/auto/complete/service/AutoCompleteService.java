package com.bottomline.exercise.auto.complete.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bottomline.exercise.auto.complete.data.AutoCompleteRepository;

@Service
public class AutoCompleteService {
	private TrieSuggestions trieDS;
	private final AutoCompleteRepository autoCompleteRepo;
	Map<String,List<String>> cache;
	
	@Autowired
	public AutoCompleteService(AutoCompleteRepository acr) {
		this.autoCompleteRepo = acr;
	}
	
	public List<String> autoCompleteNames(String prefix){
		if(trieDS == null) {
			this.trieDS = new TrieSuggestions(this.getNames());
		}
		return this.trieDS.suggest(prefix);
	}
	
	/*I've come to the conclusion that Trie algorithm is not O(K) even with cache 
	 * so i tried to figure a simple solution where the performance will be O(1)
	 * im aware about the complexity of that function on the first run (O(N**2)) 
 	 * And there can be a large number of words.
 	 * -this solution is case sensitive-
	 */
	public List<String> autoCompleteWithoutAlgorithm(String prefix){
		if(this.cache == null) {
			this.cache = new HashMap<String,List<String>>();
			//break the words from the data base 
			//and put every prefix as a key and the relevant words as value
			for(String name : this.getNames()) {
				String subName = "";
				for(char c:name.toCharArray()) {
					subName += c;
					if(cache.get(subName) == null) {
						List<String> list = new ArrayList<String>();
						list.add(name);
						this.cache.put(subName,list);
					}
					else {
						List<String> list = cache.get(subName);
						list.add(name);
						cache.put(subName, list);
					}
				}
			}
		}
		
		return this.cache.get(prefix);		
	}
	
	public  List<String> getNames(){
		List<String> res = null;
		try {
			res = 
				this.autoCompleteRepo.findAll().stream()
				.map(boy->boy.getName()).collect(Collectors.toList());
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
 
	}
}
