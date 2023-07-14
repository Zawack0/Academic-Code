/**
 * Implementation of a min priority que via a min heap
 * that is paired with a heap for quick accessing of indexes that aren't the min
 * value
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * April 20th, 2022
 * COSI 21A PA3
 */
package student_code;


public class Min_Priority_Queue {

	public static GraphNode[] heap;
	private final int default_space = 51;
	public int element_index;
	public HashMap hash_helper;
	

	/**
	 * Default constructor for the min priority queue, using default space as the initial
	 * amount of space in the heap
	 * 
	 * Worth noting that the "head" of our heap has index of 1 despite the 0 based indexing
	 * 
	 * runs in constant time
	 */
	public Min_Priority_Queue() {
		heap = new GraphNode[default_space];
		element_index = 1;
		hash_helper = new HashMap();
	}
	
	/**
	 * A constructor for the min priority queue that allows user to specify a starting size
	 * @param User_space is the initial size of the heap array
	 * 
	 * runs in constant time
	 */
	public Min_Priority_Queue(int User_space) {
		heap = new GraphNode[User_space];
		element_index = 1;
		hash_helper = new HashMap();
	}
	
	/**
	 * Inserts a new element into the priority queue according to its priority
	 * by rebalancing
	 * @param g is the new element to be added into the priority queue
	 * worst case is when we have to rebalance the hash map and heap, (O(N)) 
	 * but if we don't have to, then we run according to heap up, 
	 * which should be log(n) where n is the number of element in the heap
	 */
	public void insert(GraphNode g) {
		heap[element_index]=g;
		hash_helper.set(g, element_index);
		rebalance(g);
		element_index++;
		if (hash_helper.table.length>heap.length) {
			reHeap();
		}
		
	}
	
	/**
	 * delete the min value in the heap (aka highest priority)
	 * Somewhat superfluous, but a more name succinct than pullHighestPriorityELement
	 * see pullHighestPriorityElement() for more details
	 */
	public void deletemin() {
		pullHighestPriorityElement();
	}
	
	/**
	 * Returns and removes the min element from the queue (ie the highest priority one)
	 * then rebalances by heapifying down a low priority element from the root
	 * @returns the highest priority element in the queue (ie the min value)
	 * 
	 * Runs in log(n) time due to heapdown
	 */
	public GraphNode pullHighestPriorityElement() {
		GraphNode temp = heap[1];
		heap[1] = heap[element_index-1];
		heap[element_index] = temp;
		heap[element_index]=null;
		element_index--;
		hash_helper.deleteKey(temp);
		hash_helper.set(heap[1], 1);
		rebalance(heap[1]);
		return temp;
	}
	
	/**
	 * Rebalances the heap at node g by calling heapify
	 * @param g is the node where the current imbalance "stems" from
	 * 
	 * runs in log(n) time
	 */
	public void rebalance(GraphNode g) {
		heapify(g);
	}
	
	/**
	 * returns whether or not the min priority queue contains elements
	 * @returns true if no elements are stored in the priority queue
	 * runs in constant time
	 */
	public boolean isEmpty() {
		if (element_index==1) {
			return true;
		}
		return false;
	}
	
	/**
	 * restores min heap properties at node g. In this implementation,
	 * heapify is only ever called on a node when it is either the "root" or "leaf"
	 * of the heap. The case of the problem node being somewhere else is covered
	 * manually in update priority by calling heap up
	 * @param g is the node at which balance must be restored
	 * 
	 * runs in log(n) time in either case
	 */
	public void heapify(GraphNode g) {
		if (hash_helper.getValue(g)==element_index) {
			heapUp(element_index);
		}
		
		if (hash_helper.getValue(g)==1) {
			heapDown(1);
		}
	}
	
	/**
	 * percolates a node up from its index to its parents index, swapping
	 * their positions until min heap properties are restored.
	 * @param i is the index of the node that needs to be moved upwards
	 * 
	 * runs in O(logn) time
	 */
	public void heapUp(int i){
		while (i>1 && heap[i].priority<heap[PARENT(i)].priority) {
			GraphNode temp = heap[i];
			heap[i] = heap[PARENT(i)];
			heap[PARENT(i)] = temp;
			hash_helper.set(heap[i], i);
			i = PARENT(i);
		}
	}
	
	/**
	 * percolates a node down from the root to its lowest chil's index, swapping
	 * their position until min heap properties are restored
	 * @param i is the index of the node that needs to be moved downwards
	 * 
	 * runs in O(logn) time
	 */
	public void heapDown(int i){
		int L = LEFT(i);
		int R = RIGHT(i);
		int smallest;
		if (L<=element_index-1 && heap[L].priority<heap[i].priority) {
			smallest = L;
		}
		else {
			smallest=i;
		}
		if (R<=element_index-1&& heap[R].priority<heap[smallest].priority) {
			smallest=R;
		}
		if (smallest!=i) {
			GraphNode temp = heap[i];
			heap[i] = heap[smallest];
			heap[smallest] = temp;
			hash_helper.set(heap[i], i);
			hash_helper.set(heap[smallest], smallest);
			heapDown(smallest);
		}
	}
	/**
	 * given an index, returns the index of that node's "right child" in the heap
	 * @param i is the given index to find the child's index
	 * @returns the index of the ith node's right child
	 * runs in constant time
	 */
	public int RIGHT(int i) {
		return 2*i + 1;
	}
	
	/**
	 * given an index, returns the index of that node's "left child" in the heap
	 * @param i is the given index to find the child's index
	 * @returns the index of the ith node's left child
	 * runs in constant time
	 */
	public int LEFT(int i) {
		return 2*i;
	}
	
	/**
	 * given an index, returns the index of that node's "parent" in the heap
	 * @param i is the given index to find the parent's index of
	 * @returns the index of the ith node's parent
	 * runs in constant time
	 */
	public int PARENT(int i) {
		if (i%2==0) {
			return i/2;
		}
		else {
			return (i-1)/2;
		}
	}
	
	/**
	 * decreases the value associated with the given node to the new priority
	 * this is a min priority queue, so we should NOT be increasing the value
	 * then re establises heap properties by heaping up
	 * @param g is the node to be updated
	 * @param new_prio is the lower value that the node's priority will change to
	 */
	public void update_prio(GraphNode g, int new_prio) {
		if (g.priority<new_prio) {
			return; /**should not happen*/
		}
		g.priority=new_prio;
		
		heapUp(hash_helper.getValue(g)); //we could instead call rebalance and include a check for this scenario in heapify, but this is simpler
	}
	
	
	/**
	 * In rare cases, we could run out of space in our heap, but resizing
	 * our heap is expensive and should be avoided if possible
	 * 
	 * runs in O(n) time
	 */
	public void reHeap() {
		GraphNode[] Bigger = new GraphNode[heap.length*2-1];
		GraphNode[] old = heap.clone();
		heap = Bigger;
		for (int i = 0; i<old.length; i++) {
			if (old[i]!=null) {
				Bigger[i]=old[i];
			}
		}
	}
	
	/**
	 * just a method for testing if a node is already in the MPQ or not
	 * @param g is the graph node in question
	 * @returns true if the graph node is present, false if not
	 * 
	 * runs in time identical to search method of hashmap implementation 
	 */
	public boolean contains(GraphNode g) {
		if (hash_helper.search(g)==-1) {
			return false;
		}
		return true;
	}
}
