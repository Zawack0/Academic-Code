/**
 * Test class for the hashmap with string IDs
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * April 20th, 2022
 * COSI 21A PA3
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import student_code.*;
import org.junit.jupiter.api.Test;

class StudentHashMapTest{
	

	@Test
	void getValueTest() {
		Min_Priority_Queue.heap = new GraphNode[5];
		Min_Priority_Queue.heap[0]= new GraphNode("0fd76b04-1df7-4838-b854-e270f42a5dd6",false);
		Min_Priority_Queue.heap[1]= new GraphNode("d76ed44b-6c4f-40db-a605-a19210f64f7d",false);
		Min_Priority_Queue.heap[2]= new GraphNode("d23343d2-bc99-4145-a833-dd8e9b0dd356",false);
		Min_Priority_Queue.heap[3]= new GraphNode("fedf90fe-7e00-4155-93d6-b3d2e612f737",false);
		Min_Priority_Queue.heap[4]= new GraphNode("a5003a27-641e-49a6-bdc6-cd76ea54047a",true);
		
		HashMap hashmap = new HashMap(20);
		
		for (int i = 0; i<Min_Priority_Queue.heap.length;i++) {
			hashmap.set(Min_Priority_Queue.heap[i],i);
		}
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[1]),1);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[2]),2);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[3]),3);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[4]),4);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[0]),0);
	}

	@Test
	void CollisionTest() {
		Min_Priority_Queue.heap = new GraphNode[5];
		Min_Priority_Queue.heap[0]= new GraphNode("0fd76b04-1df7-4838-b854-e270f42a5dd6",false);
		Min_Priority_Queue.heap[1]= new GraphNode("df076b04-1df7-4838-b854-e270f42a5dd6",false);
		Min_Priority_Queue.heap[2]= new GraphNode("d23343d2-bc99-4145-a833-dd8e9b0dd356",false);
		Min_Priority_Queue.heap[3]= new GraphNode("32d343d2-bc99-4145-a833-dd8e9b0dd356",false);
		Min_Priority_Queue.heap[4]= new GraphNode("d23343d2-bc99-4145-a833-dd8e9b0dd653",true);
		
		HashMap hashmap = new HashMap(20);
		
		for (int i = 0; i<Min_Priority_Queue.heap.length;i++) {
			hashmap.set(Min_Priority_Queue.heap[i],i);
		}
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[1]),1);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[2]),2);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[3]),3);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[4]),4);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[0]),0);
		System.out.println(hashmap.size);
		assertTrue(hashmap.load_factor==0.25);
	}
	
	@Test 
	void RehashTest() {
		Min_Priority_Queue.heap = new GraphNode[8];
		Min_Priority_Queue.heap[0]= new GraphNode("0fd76b04-1df7-4838-b854-e270f42a5dd6",false);
		Min_Priority_Queue.heap[1]= new GraphNode("df076b04-1df7-4838-b854-e270f42a5dd6",false);
		Min_Priority_Queue.heap[2]= new GraphNode("d23343d2-bc99-4145-a833-dd8e9b0dd356",false);
		Min_Priority_Queue.heap[3]= new GraphNode("32d343d2-bc99-4145-a833-dd8e9b0dd356",false);
		Min_Priority_Queue.heap[4]= new GraphNode("d23343d2-bc99-4145-a833-dd8e9b0dd653",true);
		Min_Priority_Queue.heap[5]= new GraphNode("9d69e008-727a-469b-ae94-2f219fc8e05e",false);
		Min_Priority_Queue.heap[6]= new GraphNode("45d1bf93-53d3-4c5c-b377-c361184c542c",false);
		Min_Priority_Queue.heap[7]= new GraphNode("19d1b8de-f732-4419-a7ab-e44db695b2a4",false);

		
		HashMap hashmap = new HashMap(10);
		
		for (int i = 0; i<Min_Priority_Queue.heap.length;i++) {
			hashmap.set(Min_Priority_Queue.heap[i],i);
		}
		
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[1]),1);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[2]),2);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[3]),3);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[4]),4);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[0]),0);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[5]),5);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[6]),6);
		assertEquals(hashmap.getValue(Min_Priority_Queue.heap[7]),7);
		assertEquals(hashmap.size,20);
	}
}
