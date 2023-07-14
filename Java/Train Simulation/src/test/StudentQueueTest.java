/**
 * Queue test class
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Queue;

class StudentQueueTest {
	public Queue<String> Q;
	@Test
	void initTest() {
		Q = new Queue<String>(10);
		assertTrue(Q.size()==0);
		assertTrue(Q.head==Q.tail);
	}
	
	@Test
	void TestCapacity() {
		Q = new Queue<String>(10);
		while(Q.isFull()==false) {
			Q.enqueue("place holder");
		}
		assertTrue(Q.size()==10&&Q.isFull());
	}
	
	@Test
	void TestDequeueEnqueue() {
		Q = new Queue<String>(10);
		while(Q.isFull()==false) {
			Q.enqueue("place holder");
		}
		while(Q.size()>5) {
			System.out.println(Q.size());
			Q.dequeue();
		}
		assertTrue(Q.size()==5);
		assertTrue(Q.head==5);
		while(Q.isFull()==false) {
			System.out.println(Q.size());
			Q.enqueue("place holder");
		}
		assertTrue(Q.tail==Q.head);
	}
	

	

}
