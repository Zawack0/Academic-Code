package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.IDLList;

public class IDLListTest {
	@Test
	public void test() {
		IDLList<Integer> testList = new IDLList<>();
		assertEquals("[]", testList.toString());
		assertEquals(0, testList.size());
		testList.append(10);
		testList.append(30);
		testList.append(42);
		testList.append(80);
		testList.append(34);
		testList.append(27);
		testList.append(31);
		assertEquals("[10, 30, 42, 80, 34, 27, 31]", testList.toString());
		assertEquals(7, testList.size());
		testList.add(5, 13);
		assertEquals("[10, 30, 42, 80, 34, 13, 27, 31]", testList.toString());
		assertEquals(8, testList.size());
		assertEquals(Integer.valueOf(10), testList.remove());
		assertEquals(Integer.valueOf(30), testList.remove());
		assertEquals("[42, 80, 34, 13, 27, 31]", testList.toString());
		assertEquals(6, testList.size());
		testList.add(30);
		testList.add(10);
		assertEquals("[10, 30, 42, 80, 34, 13, 27, 31]", testList.toString());
		assertEquals(8, testList.size());
		assertEquals(Integer.valueOf(31), testList.removeLast());
		assertEquals(Integer.valueOf(27), testList.removeLast());
		assertEquals(Integer.valueOf(13), testList.removeLast());
		assertEquals(Integer.valueOf(34), testList.removeLast());
		assertEquals(Integer.valueOf(80), testList.removeLast());
		assertEquals(Integer.valueOf(42), testList.removeLast());
		assertEquals(Integer.valueOf(30), testList.removeLast());
		assertEquals(Integer.valueOf(10), testList.removeLast());
		assertEquals("[]", testList.toString());
		assertEquals(0, testList.size());
		testList.append(10);
		testList.append(30);
		testList.append(42);
		testList.append(80);
		testList.append(34);
		testList.append(27);
		testList.append(31);
		assertEquals("[10, 30, 42, 80, 34, 27, 31]", testList.toString());
		assertEquals(7, testList.size());
		assertEquals(Integer.valueOf(27), testList.removeAt(5));
		assertEquals(Integer.valueOf(42), testList.removeAt(2));
		assertEquals(Integer.valueOf(31), testList.removeAt(4));
		assertEquals("[10, 30, 80, 34]", testList.toString());
		assertEquals(4, testList.size());
		assertEquals(Integer.valueOf(30), testList.get(1));
		assertEquals(Integer.valueOf(34), testList.get(3));
		assertEquals(Integer.valueOf(10), testList.getHead());
		assertEquals(Integer.valueOf(34), testList.getLast());
		testList.add(4, 31);
		testList.add(2, 42);
		testList.add(5, 27);
		assertEquals("[10, 30, 42, 80, 34, 27, 31]", testList.toString());
		assertEquals(7, testList.size());
		assertEquals(true, testList.remove(Integer.valueOf(27)));
		assertEquals(true, testList.remove(Integer.valueOf(42)));
		assertEquals(false, testList.remove(Integer.valueOf(67)));
		assertEquals(false, testList.remove(Integer.valueOf(-1)));
		assertEquals("[10, 30, 80, 34, 31]", testList.toString());
		assertEquals(5, testList.size());
	}
}
