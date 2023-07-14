/**
 * Min Priority Queue test class
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * April 20th, 2022
 * COSI 21A PA2
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import student_code.*;
import org.junit.jupiter.api.Test;

class StudentPQTest{
	GraphNode Atown = new GraphNode("0fd76b04-1df7-4838-b854-e270f42a5dd6",false);
	GraphNode Btown = new GraphNode("df076b04-1df7-4838-b854-e270f42a5dd6",false);
	GraphNode Ctown = new GraphNode("d23343d2-bc99-4145-a833-dd8e9b0dd356",false);
	GraphNode Dtown = new GraphNode("32d343d2-bc99-4145-a833-dd8e9b0dd356",false);
	GraphNode Etown = new GraphNode("d23343d2-bc99-4145-a833-dd8e9b0dd653",true);
	GraphNode Ftown = new GraphNode("9d69e008-727a-469b-ae94-2f219fc8e05e",false);
	GraphNode Gtown = new GraphNode("45d1bf93-53d3-4c5c-b377-c361184c542c",false);
	GraphNode Htown = new GraphNode("19d1b8de-f732-4419-a7ab-e44db695b2a4",false);
	GraphNode Ztown = new GraphNode("6d99e008-727a-469b-ae94-2f219fc8e05e",false);
	
	@Test
	void defaultname() {
		Atown.priority=1;
		Btown.priority=2;
		Ctown.priority=3;
		Dtown.priority=4; 
		Etown.priority=5;
		Ftown.priority=6;
		Gtown.priority=7;
		Htown.priority=8;
		Ztown.priority=9;
		
		
		Min_Priority_Queue Q = new Min_Priority_Queue();
		Q.insert(Atown);
		Q.insert(Ztown);
		Q.insert(Btown);
		Q.insert(Htown);
		Q.insert(Ctown);
		Q.insert(Gtown);
		Q.insert(Dtown);
		Q.insert(Etown);
		Q.insert(Ftown);
		
		/**
		 * traced the insertions on paper, the following should be the structure of
		 * the "tree"
		 */
		assertTrue(Q.heap[1].priority==1);
		assertTrue(Q.heap[2].priority==3);
		assertTrue(Q.heap[3].priority==2);
		assertTrue(Q.heap[4].priority==5);
		assertTrue(Q.heap[5].priority==8);
		assertTrue(Q.heap[6].priority==7);
		assertTrue(Q.heap[7].priority==4);
		assertTrue(Q.heap[8].priority==9);
		assertTrue(Q.heap[9].priority==6);
		
		assertTrue(Q.pullHighestPriorityElement().equals(Atown));
		assertTrue(Q.pullHighestPriorityElement().equals(Btown));
		assertTrue(Q.pullHighestPriorityElement().equals(Ctown));
		assertTrue(Q.pullHighestPriorityElement().equals(Dtown));
		assertTrue(Q.pullHighestPriorityElement().equals(Etown));
		assertTrue(Q.pullHighestPriorityElement().equals(Ftown));
		assertTrue(Q.pullHighestPriorityElement().equals(Gtown));
		assertTrue(Q.pullHighestPriorityElement().equals(Htown));
		assertTrue(Q.pullHighestPriorityElement().equals(Ztown));
		
		assertTrue(Q.isEmpty());
	}
	
	@Test
	void defaultname2() {
		Atown.priority=1;
		Btown.priority=2;
		Ctown.priority=3;
		Dtown.priority=5; 
		Etown.priority=5;
		Ftown.priority=5;
		Gtown.priority=7;
		Htown.priority=8;
		Ztown.priority=9;
		
		Min_Priority_Queue Q = new Min_Priority_Queue();
		
		Q.insert(Atown);
		Q.insert(Ztown);
		Q.insert(Btown);
		Q.insert(Htown);
		Q.insert(Ctown);
		Q.insert(Gtown);
		Q.insert(Dtown);
		Q.insert(Etown);
		Q.insert(Ftown);
		
		Q.pullHighestPriorityElement();
		Q.pullHighestPriorityElement();
		Q.pullHighestPriorityElement();
		
		assertTrue(Q.pullHighestPriorityElement().priority==5);
		assertTrue(Q.pullHighestPriorityElement().priority==5);
		Q.update_prio(Ztown, 0);
		assertTrue(Q.pullHighestPriorityElement().equals(Ztown));
		assertTrue(Q.pullHighestPriorityElement().priority==5);
		assertTrue(Q.pullHighestPriorityElement().priority==7);
		Q.pullHighestPriorityElement();
		
		assertTrue(Q.isEmpty());
	}
}
