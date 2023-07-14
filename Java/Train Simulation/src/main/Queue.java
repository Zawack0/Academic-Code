/**
 * First in first out circular queue class
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;

import java.util.NoSuchElementException;
public class Queue<T> {

	public T[] q;
	public int head;
	public int tail;
	public int numEntries;
	
	@SuppressWarnings("unchecked")
	/**
	 * constructor for an empty queue with a given capacity
	 * @param capacity is the largest number of entries possible in the constructed queue
	 * runs in constant time
	 */
	public Queue(int capacity) {
		this.q = (T[]) new Object[capacity];
		head = 0;
		tail = 0;
		numEntries=0;
	}
	
	/**
	 * inserts an element at the end of the queue and updates tail and numENtries appropriately
	 * @param element is the element to be included next in the queue
	 * runs in constant time
	 */
	public void enqueue(T element) {
		q[tail]=element;
		if (tail==q.length) {
			tail=0;
		}
		else {
			tail%=q.length;
			tail++;
			tail%=q.length;
		}
		numEntries++;
	}
	
	/**
	 * updates head and numEntries variables to exclude the current head of the queue. 
	 * notably does not get rid of the value in the array, just ignores it for further operation
	 * throws a NoSuchElementException in the case where the queue is empty
	 * runs in constant time
	 */
	public void dequeue() { 
		if (head==tail&&this.isFull()!=true) {
			throw new NoSuchElementException(); /** will only be executed if the queue is empty*/
		}
		if (head==q.length) {
			head=0;
		}
		else {
			head%=q.length;
			head++;
		}
		numEntries--;
	}
	
	/**
	 * retrieves and returns the value of the element first in the queue
	 * @returns the value at the head of the queue
	 * throws a NoSuchElementException in the case where the queue is empty
	 * runs in constant time
	 */
	public T front() {
		if (head==tail) {
			throw new NoSuchElementException(); /** will only be executed if the queue is empty*/
		}
		return(q[head]);
	}
	
	/**
	 * a retriever function for numEntries, which keeps track of the number of elements stored in the queue
	 * @returns number of elements stored in the queue
	 * runs in constant time
	 */
	public int size() {
		return numEntries;
	}
	
	@Override
	/**
	 * returns a string representation of all elements stored in the queue
	 * runs in linear time, (O(n)) or larger in scenarios where the queue is storing objects whose to string is longer than constant time
	 * (in that case, running time would be n*x where n is size of the queue and x is running time of toString call on one of the stored objects)
	 */
	public String toString() {
		if (numEntries==0) {
			return("The queue is empty");
		}
		String dummy = "The elements in the queue are as follows... \n";
		for (int i=0; i<numEntries; i++) {
			dummy += q[(head+i)%q.length].toString() + "\n";
		}
		return dummy;
	}
	
	/**
	 * tests whether or not the queue has any space for additional values
	 * @returns a boolean value representing whether or not the queue is full
	 * runs in constant time
	 */
	public boolean isFull() {
		if(this.size()==this.q.length) {
			return true;
		}
		else {
			return false;
		}
	}
}
