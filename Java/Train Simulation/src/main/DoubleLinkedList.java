/**
 * Non-circular doubly linked list class
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;

public class DoubleLinkedList<T> {
	public int size;
	public Node<T> head;
	public Node<T> tail;
	public Node<T> pointer;
	

	/**
	 * constructor for an empty linked list
	 * runs in constant time
	 */
	public DoubleLinkedList() {
		size = 0;
		head=null;
		tail=null;
		pointer=null;
	}
	
	/**
	 * retriever method for the "head" field
	 * @returns the first node in the linked list
	 * runs in constant time
	 */
	public Node<T> getFirst() {
		return head;
	}
	
	/**
	 * retriever method for the "tail" field
	 * @returns the last node in the linked list
	 * runs in constant time
	 */
	public Node<T> getLast() {
		return tail;
	}
	
	/**
	 * adds an element to the end of the list, and makes it the tail. Also increments size
	 * @param element is the data to be stored in the new node at the end of the list
	 * runs in constant time
	 */
	public void insert(T element) {
		if (size==0) {
			head = new Node<T>(element);
			tail = head;
			size = 1;
			head.setLocal(size);
		}
		else {
			this.tail.setNext(new Node<T>(element));
			Node<T> temp = tail;
			tail = tail.getNext();
			tail.setPrev(temp);
			size++;
			tail.setLocal(size);
		}
	}
	
	/**
	 * deletes the first instance of the node with the given key for data
	 * @param key is the data of the node to be deleted
	 * @returns the element deleted, or null if it is not contained in the list
	 * runs in linear time (O(n) where n is the size of the list)
	 */
	public T delete(T key) {
		pointer = head;
		while (pointer.getData()!=key){
			pointer = pointer.getNext();
			if (pointer==null) {
				return null;
			}
		}
		if (pointer.equals(head)) {
			pointer.getNext().setPrev(null);
			head = pointer.getNext();
			size--;
			return pointer.getData();
		}
		if (pointer.equals(tail)) {
			pointer.getPrev().setNext(null);
			tail = pointer.getPrev();
			size--;
			return pointer.getData();
		}
		pointer.getPrev().setNext(pointer.getNext());
		pointer.getNext().setPrev(pointer.getPrev());
		size--;
		return pointer.getData();
	}
	
	/**
	 * returns the first element in the list that matches the key, or null if the element is not in the list
	 * @param key is the element being searched for
	 * @returns the first instance of the key in the list
	 * runs in linear time (O(n) where n is the size of the list)
	 */
	public T get(T key) {
		pointer = head;
		while (pointer.getData().equals(key)!=true&&pointer!=null){
			pointer = pointer.getNext();
		}
		if (pointer==null) {
			return null;
		}
		return pointer.getData();
	}
	
	/**
	 * retriever method for the "size" field
	 * @returns the number of elements in the linked list
	 * runs in constant time
	 */
	public int size() {
		return size;
	}
	
	@Override
	/**
	 * returns a string representation of all elements in the list, or a notification that the list is empty
	 * runs in linear(O(n)) time assuming the toString call on each node is constant.
	 * if the toString call on each node is not constant (if each node contained a linked list for example,) then runtime is n*x where n is size
	 * of this linked list, and x is the running time of toString on any individual node
	 */
	public String toString() {
		if (size==0) {
			return("The linked list is empty");
		}
		String dummy = "The elements in the list are as follows... \n";
		pointer = head;
		while (pointer!=null) {
			dummy += pointer.toString() + "\n";
			pointer=pointer.getNext();
		}
		return dummy;
	}
}
