package com.bottomline.exercise.auto.complete.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieSuggestions {
	Map<String, List> resultsCache;
	TrieNode root;

	public TrieSuggestions(List<String> words) {
		root = new TrieNode();
		//data insertion during the initialization
		for (String word : words)
			root.insert(word);
		resultsCache = new HashMap<String, List>(); 
	}
	
	public class TrieNode {
		Map<Character, TrieNode> children;
		char c;
		boolean isWord;

		public TrieNode(char c) {
			this.c = c;
			children = new HashMap<>();
		}

		public TrieNode() {
			children = new HashMap<>();
		}

		public void insert(String word) {
			//empty words are unnecessary in our case
			if (word == null || word.isEmpty())
				return;
			char firstChar = word.charAt(0);
			//check if the trie node already exist in our tree
			TrieNode child = children.get(firstChar);
			//in case the tree is missing the key(char)
			if (child == null) {
				child = new TrieNode(firstChar);
				children.put(firstChar, child);
			}
			//in case the word is longer then 1 char 
			//recursively add the word to the nodes of the tree
			// Aaron = (A)->(a)->(r)->(o)->(n)
			if (word.length() > 1)
				child.insert(word.substring(1));
			else
				child.isWord = true;
		}

	}

	public void suggestHelper(TrieNode root, List<String> list, StringBuffer curr) {
		if (root.isWord) {
			list.add(curr.toString());
		}
		
		if (root.children == null || root.children.isEmpty())
			return;
		
		//recursively traversing from our lastNode to the end of the tree building the words
		for (TrieNode child : root.children.values()) {
			suggestHelper(child, list, curr.append(child.c));
			curr.setLength(curr.length() - 1);
		}
	}

	public List<String> suggest(String prefix) {
		List<String> suggestionsList = resultsCache.get(prefix);
		if(suggestionsList != null) {
			return suggestionsList;
		}
		else
			suggestionsList = new ArrayList<>();
		TrieNode lastNode = root;
		//temp will be necessary to validate if the prefix suppose to upper-case or not
		TrieNode temp = null;
		StringBuffer curr = new StringBuffer();
		//accourding to the prefix 
		//traversing from node to node where the character fit with the prefix
		for (char c : prefix.toCharArray()) {
			temp = lastNode.children.get(c);
			if(temp == null) {
				c = Character.toUpperCase(c);
				lastNode = lastNode.children.get(c);
			}
			else
				lastNode = temp;
			if (lastNode == null)
				return suggestionsList;
			curr.append(c);
		}
		//search the Continuity of the prefix
		suggestHelper(lastNode, suggestionsList, curr);
		//update the cache for better performance next time
		resultsCache.put(prefix, new ArrayList<String>(suggestionsList));
		return suggestionsList;
	}




}
