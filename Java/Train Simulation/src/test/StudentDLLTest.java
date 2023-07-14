/**
 * DoubleLinkedList test class
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import main.DoubleLinkedList;
import org.junit.jupiter.api.Test;

class StudentDLLTest {
	public DoubleLinkedList<String> DLL = new DoubleLinkedList<String>();
	@Test
	void initTest() {
		assertTrue(DLL.size==0);
		assertTrue(DLL.head==DLL.tail && DLL.head==null);
	}
	
	@Test
	void addTests() {
		DLL.insert("a");
		DLL.insert("b");
		DLL.insert("c");
		assertTrue(DLL.size==3);
		assertTrue(DLL.head.Data.equals("a"));
		assertTrue(DLL.tail.Data.equals("c"));
		assertTrue(DLL.head.Prev==null);
		assertTrue(DLL.tail.Next==null);
	}
	
	@Test
	void deleteTests() {
		DLL.insert("a");
		DLL.insert("b");
		DLL.insert("c");
		DLL.delete("c");
		assertTrue(DLL.size==2);
		assertTrue(DLL.tail.Data.equals("b"));
		DLL.delete(DLL.head.Data);
		assertTrue(DLL.size==1);
		assertTrue(DLL.tail.Data.equals(DLL.head.Data));
	}
	
	@Test
	void toStringTest() {
		assertTrue(DLL.toString().equals("The linked list is empty"));
		DLL.insert("alpha");
		DLL.insert("beta");
		DLL.insert("charlie");
		DLL.insert("delta");
		DLL.delete("beta");
		assertTrue(DLL.toString().equals("The elements in the list are as follows... \nalpha\ncharlie\ndelta\n"));
	}
	

}
