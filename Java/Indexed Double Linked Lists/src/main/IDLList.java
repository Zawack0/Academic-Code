/**
 * Connor Zawacki
 * connorzawacki@braneis.edu
 * 4/20/21
 * PA#7
 * 
 * In an attempt to gain the strengths of both double linked lists and array lists, this class was created for fast accessing while
 * maintaining double linked list properties
 * Unfortunately, it sorta combines their weaknesses too, so its not actually that practical
 */
package main;
import java.util.ArrayList;
public class IDLList<E> {
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;
	
	/**
	 * Similar to default DLL implementation
	 *
	 * @param <E> we use genetics so that we can store any type of reference data.
	 */
	private static class Node<E>{
		E data;
		Node<E> next;
		Node<E> prev;
		
		Node (E elem){
			data = elem;
		}
		
		Node (E elem, Node<E> _prev, Node<E> _next){
			data = elem;
			next = _next;
			prev = _prev;
			
		}
		
		/**
		 * this is important for the IDLL to string to function properly
		 */
		public String toString() {
			return(data.toString());
		}
	}
	
	/**
	 * constructor starts with a 0 size and creating an empty array list of nodes
	 */
	public IDLList() {
		size = 0;
		indices = new ArrayList<Node<E>>();
	}
	
	/**
	 * 
	 * @param index the place it should be added to
	 * @param elem new element to be added
	 */
	public boolean add(int index, E elem) {
//		if (indices.contains(elem)){ This is here if we don't want to allow 2 of the same value, commented out by default
//			return false;
//		}
		if (size == 0) { // special case the list is empty at the time of calling
			Node <E> ele = new Node<E>(elem);
			ele.next = null;
			ele.prev = null;
			head = ele;
			tail = ele;
			indices.add(ele);
		}
		else if (index==0) {// special case if new element is to be the head
			Node <E> ele = new Node<E>(elem);
			ele.next = head;
			head.prev = ele;
			head = ele;
			ele.prev = null;
			indices.add(0, ele);
		}
		else if (index==indices.size() && index!=0) { // special case if new element is to be the tail
			Node <E> ele = new Node<E>(elem);
			ele.prev = tail;
			tail.next = ele;
			tail = ele;
			ele.next = null;
			indices.add(ele);
		}
		else { // all cases not handled by above
			Node <E>pre = indices.get(index-1);
			Node <E>post = indices.get(index);
			Node <E>ele = new Node <E>(elem, pre, post);
			pre.next = ele;
			post.prev = ele;
			indices.add(index, ele);
		
		}
		size++;
		return true;
	}
	
	/**
	 * because of our specificity above, we can just call the other add function
	 * @param elem new element to be added
	 */
	public boolean add(E elem) {
		return(add(0, elem));
	}
	
	/**
	 * because of our specificity above, we can just call the other add function
	 * @param elem new element to be added
	 */
	public boolean append(E elem) {
		return(add(indices.size(), elem));
		
	}
	
	/**
	 * the get value returns the element stored, not the node
	 * a single line return statement for this function seems sketch, but I confirmed it with a TA
	 * @param index is the index of the element desired
	 * @return the element at the given index
	 */
	public E get (int index){
		return(indices.get(index).data);
	}
	
	/**
	 * similar to above, only we can just use the head
	 * @return the head element
	 */
	public E getHead() {
		return(head.data);
	}
	
	/**
	 * similar to above, only we can just use the tail
	 * @return the tail element
	 */
	public E getLast() {
		return(tail.data);
	}
	
	/**
	 * just a getter for the IDLL's current size
	 * @return int representing number of nodes
	 */
	public int size() {
		return(size);
	}
	
	
	/**
	 * similar to how we did add, we make an all inclusive removeAt function that gets a value and node out of their respective storage
	 * @param index of value removed
	 * @returns the removed element
	 */
	public E removeAt(int index) {
		Node <E>value;
		if (size<1) { // can't remove if nothings there
			return null;
		}
		else if (size==1) { // if only one element, the IDLL becomes empty
			head = null;
			tail = null;
			value = indices.remove(0);
		}
		else if (index==0) { // special case for removing head
			indices.get(1).prev = null;
			head = indices.get(1);
			value = indices.remove(index);
		}
		else if (index==indices.size()-1) { // special case for removing tail
			indices.get(index-1).next = null;
			tail = indices.get(index-1);
			value = indices.remove(index);
		}
		else { // procedure for all other cases
			indices.get(index-1).next = indices.get(index+1);
			indices.get(index+1).prev = indices.get(index-1);
			value = indices.remove(index);
		}
		size--;
		return (E) value.data;
	}
	
	/**
	 * removes the head by calling removeAt(0)
	 * returns element removed
	 */
	public E remove() {
		return(removeAt(0));
	}
	
	/**
	 * removes the tail by calling removeAt(the last element)
	 * @returns element removed
	 */
	public E removeLast() {
		return(removeAt(size-1));
	}
	
	/**
	 * Removes the first occurrence of the element provided
	 * removing by element is a little more tricky
	 * Unfortunately have to loop through manually, because indices is an array of nodes, not values, so we can't just use the Array list remove by element
	 * @param elem is the element to be removed
	 * @returns false if value is missing in list, true if it was removed
	 */
	public boolean remove(E elem) {
		for (int i = 0; i<indices.size(); i++) {
			if (elem.equals(get(i))) {
				removeAt(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * textual representation of IDLL
	 */
	public String toString() {
		return(indices.toString());
	}
}
