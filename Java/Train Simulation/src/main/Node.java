/**
 * Very simplistic Node class for use in DoubleLinkedList
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;

public class Node<T> {
	public T Data;
	public Node<T> Next;
	public Node<T> Prev;
	public int local; /**just a small add on that is useful for comparing station location efficiently. NOTE: did not end up using this, but it could theoretically be useful so I left it in/*
	
	/**
	 * Constructor that stores data but no pointers to other nodes
	 * @param data is the element to be stored
	 * runs in constant time
	 */
	public Node(T data) {
		this.Data = data;
		this.Next = null;
		this.Prev = null;
	}
	
	/**
	 * Allows for overriding of previous data for any particular node.
	 * Much like the constructor only modifies the data field
	 * @param data is the new element to be stored
	 * runs in constant time
	 */
	public void setData(T data) {
		this.Data = data;
		
	}
	
	/**
	 * sets the value location value to a given integer
	 * @param L is the number representing the node's relative place in the sequence
	 */
	public void setLocal(int L) {
		local = L;
	}
	/**
	 * updates the "Next" pointer to a given node
	 * @param next is the subsequent node to be pointed to 
	 * runs in constant time
	 */
	public void setNext(Node<T> next) {
		this.Next = next;
	}
	
	/**
	 * updates the "Prev" pointer to a given node
	 * @param prev is the previous node to be pointed to 
	 * runs in constant time
	 */
	public void setPrev(Node<T> prev) {
		this.Prev = prev;
	}
	
	/**
	 * a retriever method for the Next field
	 * @return the pointer to the next node
	 * runs in constant time
	 */
	public Node<T> getNext() {
		return Next;
	}
	
	/**
	 * a retriever method for the Prev field
	 * @return the pointer to the previous node
	 * runs in constant time
	 */
	public Node<T> getPrev() {
		return Prev;
	}
	
	/**
	 * a retriever method for the data stored in the node
	 * @return the data stored in this node
	 * runs in constant time
	 */
	public T getData() {
		return Data;
	}

	@Override
	/**
	 * toString on a node should return the string representation of the element it stores
	 * @return a string representation of the data field
	 * running time is equal to the running time of toString call on stored data field (likely constant time)
	 */
	public String toString() {
		return Data.toString();
	}
}
