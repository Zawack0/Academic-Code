/**
 * Implementation of a hashmap that is paired with a heap for storage of
 * index values of each node in the heap. 
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * April 20th, 2022
 * COSI 21A PA3
 */
package student_code;


public class HashMap {
	
	public Entry[] table; /* table maps IDs to their index in the heap.*/
	private final int default_size = 50; /*a hash map needs an initial size, in absence of user provided size, default starting point is 50*/
	public int size;
	public int elements;
	public double load_factor;
	
	/**
	 * default constructor for the hash map given that user does not provide a
	 * starting size
	 * constant run time
	 */
	public HashMap() {
		table = new Entry[default_size];
		size = default_size;
		elements = 0;
	}
	
	
	/**
	 * gives the user the ability to specifiy a starting size to avoid constant
	 * rehashing for very large inputs
	 * @param size is how much space the user requests the Hashmap to originally
	 * have
	 * constant run time
	 */
	public HashMap(int size) {
		table = new Entry[size];
		this.size = size;
		elements = 0;
	}
	
	/**
	 * Creation of a good hash function should be quick, achieve a relatively
	 * even distribution, and avoid excess collisions. To do this, you must
	 * have a good idea of what sort of data you will be storing. In this case, this
	 * hashmap is responsible for storing based on 36 character long randomly 
	 * generated UUID's. 
	 * 
	 * Normally, a summation of ASCII values is a poor hashfunction because
	 * of an abnormal level of collisions, however, with 36 randomly generated
	 * characters, this is unlikely to yield keys with similar ASCII sums. That
	 * being said, I alternated here between adding and subtracting ASCII values
	 * from the total just to prevent collisions from two strings that have similar
	 * content in differing orders.
	 * 
	 * int characters were just treated as themselves, not their ASCII values
	 * 
	 * @param key
	 * @return
	 * runtime is O(L) where L is the length of the key string
	 * 
	 * incredibly difficult to create a hash function that takes into account a 
	 * mixed int and letter string that runs in O(1)
	 */
	int hashFunction (String key) {
		boolean pos = true;
		int total = 0;
		
		for (int i = 0; i<key.length();i++){
			if (pos==true) {
				total +=(int) key.charAt(i);
				pos = false;
			}
			else {
				total +=(int) key.charAt(i);
				pos = true;
			}
		}
		if (total<0) {
			total*=-1;
		}
		return total%size;
		
	}
	
/**
 * quadratic probing mechanism to find nearest open spot
 * @param init is the value of the initial hash function that didn't work out
 * @return a new int index value for the hashmap
 * runtime is O(n) where n is size of table, but the rest
 * of the data structure is build in such a way to minimize the chances of
 * such a runtime
 */
	int QuadProbe (int init) {
		int test;
		for ( int i = 0; i<size; i++) {
			test =( init + (i*i))%size;
			if (table[test]==null || table[test].ID.equals("[[REDACTED]]")) {
				return test;
			}
		}
		return -1;
	}
	
	/**
	 * very similar algorhythm to the quadratic probing one, but useful for things
	 * other than insertion, tests to see if a given key is in our hashmap by
	 * comparing the index stored to the node with that index in our heap
	 * @param key is the graph node being searched for
	 * @return an int value giving the index in the hashmap of the node's location
	 * or -1 if not found
	 * 
	 * O(n) time, but actually taking this long for a search is very unlikely
	 * (only O(n) if you have a fully deleted table or something absurd)
	 * usually O(L) where L is the length of the ID string
	 */
	public int search (GraphNode key) {
		int test;
		int init = hashFunction(key.getId());
		for ( int i = 0; i<=size; i++) {
			test =( init + (i*i))%size;
			if (table[test]==null) {
				return -1;
			}
			if (table[test].ID.equals("[[REDACTED]]")!=true && table[test].ID.equals(key.getId())) {
				return test;
			}
		}
		return -1;
	
	}

	/**
	 * Searches for a graphnode in our hash table and updates its value. If
	 * not found, inserts instead
	 * 
	 * if we want to differentiate from a "null" value and a deleted value, we have
	 * to never allow a meaningful value of "0" to enter the hashtable because in
	 * java 0 is both the default value in an array (not "null") AND java uses 0
	 * based indexing. Therefore we insert 'value+1' and use this "adjusted index"
	 * information accordingly
	 * @param key graphnode to have its index mapped
	 * @param value is the index in our heap of the graphnode
	 * technically worst case O(n) time, but usually O(L) where n is the size of the table and
	 * L is size of ID string
	 */
	public void set (GraphNode key, int value) {
		int index = search(key);
		if (index!=-1) {
			table[index].value=value;
			table[index].ID=key.getId();
		}
		else {
			index = QuadProbe(hashFunction(key.getId()));
			elements++;
			load_factor = ((double) elements)/((double)size);
			table[index]=new Entry();
			table[index].value=value;
			table[index].ID=key.getId();
			if (load_factor>0.5) {
				reHash(); //O(n) only when rehashing
			}
		}
		
	}
	
	/**
	 * searches for the value associated with a given graphnode in the hashmap
	 * @param g is the graphnode being searched for
	 * @return the value of the applicable graphnode (ie its location in the heap)
	 * returns -1 if not present
	 * O(n) time, but actually taking this long for a search is very unlikely
	 * (only O(n) if you have a fully deleted table or something absurd)
	 * usually O(L) where L is the length of the ID string
	 * 
	 */
	public int getValue(GraphNode g) {
		int index = search(g);
		if (index!=-1) {
			return table[index].value;
		}
		else {
			return -1;
		}
	}
	
/**
 * deletes a given graphnode from the hashmap, replacing it with -1
 * @param g is the graphnode to be deleted
 * @return true if an element was "deleted" and false otherwise
 * O(n) time, but actually taking this long for a search is very unlikely
 * (only O(n) if you have a fully deleted table or something absurd)
 * usually O(L) where L is the length of the ID string
 * O(n) when rehashing is needed is irrelevent because this method should never
 * call reHash
 */
	boolean deleteKey(GraphNode g) {
		int index = search(g);
		if (index!=-1){
			table[index].ID="[[REDACTED]]";
			elements--;
			load_factor = ((double) elements)/((double)size);
			if (load_factor>0.5) {
				reHash(); //O(n) only when rehashing, which shouldn't happen here
			}
			return true;
		}
		return false;
	}
	
	/**
	 * indicates whether or not the given graphnode is in the hashmap
	 * @param g is the graphnode whose key is being searched for
	 * @return true if the graphnode is in the hashmap, false if not
	 * Identical runtime to search (see above)
	 */
	public boolean hasKey(GraphNode g) {
		if (search(g)==-1) {
			return false;
		}
		return true;
	}
	
	/**
	 * In some cases, we may run out of space, and rehashing may be necessary,
	 * doubles the size of the hashtable and reconfigures position and loadfactor
	 * 
	 * 
	 * very expensive operation, O(n)
	 */
	public void reHash() {
		Entry[] Bigger = new Entry[size*2];
		size*=2;
		elements = 0;
		Entry[] old = table.clone();
		table = Bigger;
		for (int i = 0; i<old.length; i++) {
			if (old[i]!=null && old[i].ID.equals("[[REDACTED]]")==false) {
				GraphNode node = Min_Priority_Queue.heap[old[i].value];
				set(node, old[i].value);
			}
		}
	}
}
