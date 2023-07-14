/**
 * AVL tree test class
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * April 2nd, 2022
 * COSI 21A PA2
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import main.*;
import org.junit.jupiter.api.Test;

class StudentAVLTest {
	public Player abeP = new Player("Abe", 1, 3000);
	public Player bobP = new Player("Bob", 2, 4000);
	public Player charlesP = new Player("Charles", 3, 5000);
	public Player danP = new Player("Dan", 4, 6000);
	public Player eddP = new Player("Edd", 5, 7000);
	public AVLPlayerNode ELOabe = new AVLPlayerNode(abeP, abeP.getELO());
	public AVLPlayerNode ELObob = new AVLPlayerNode(bobP, bobP.getELO());
	public AVLPlayerNode ELOcharles = new AVLPlayerNode(charlesP, charlesP.getELO());
	public AVLPlayerNode ELOdan = new AVLPlayerNode(danP, danP.getELO());
	public AVLPlayerNode ELOedd = new AVLPlayerNode(eddP, eddP.getELO());
	
	public Player noobP = new Player("noob", 0, 100);
	public Player proP = new Player("pro", 10, 10000);
	public Player newguyP = new Player("newguy", 6, 8000);
	/*
	 * didn't end up using these
	public AVLPlayerNode ELOnoob = new AVLPlayerNode(noobP, noobP.getELO());
	public AVLPlayerNode ELOpro = new AVLPlayerNode(proP, proP.getELO());
	public AVLPlayerNode ELOnewguy = new AVLPlayerNode(newguyP, newguyP.getELO());
	*/
	
	
	// the following rotate tests can be used to individually test the rotate methods, however they need access to the rotate methods. Rotate methods
	// can be made temporarily public for use of testing, but should be private for final submission, just my attempt at a more complete test file
	/*
	
	@Test
	void RotateTest1() {
		ELObob.setLeft(ELOabe);
		ELObob.setParent(null);
		ELObob.setRight(ELOedd);
		
		ELOabe.setLeft(null);
		ELOabe.setParent(ELObob);
		ELOabe.setRight(null);
		
		ELOedd.setLeft(ELOdan);
		ELOedd.setParent(ELObob);
		ELOedd.setRight(null);
		
		ELOdan.setLeft(ELOcharles);
		ELOdan.setParent(ELOedd);
		ELOdan.setRight(null);
		
		ELOcharles.setLeft(null);
		ELOcharles.setParent(ELOdan);
		ELOcharles.setRight(null);
		
		ELOedd.rotateRight();
		assertTrue(ELObob.getRight().equals(ELOdan));
		assertTrue(ELOdan.getRight().equals(ELOedd));
		assertTrue(ELOdan.getLeft().equals(ELOcharles));
		assertTrue(ELOdan.getParent().equals(ELObob));
	}
	
	@Test
	void RotateTest2() {
		ELObob.setLeft(null);
		ELObob.setParent(ELOabe);
		ELObob.setRight(ELOcharles);
		
		ELOabe.setLeft(null);
		ELOabe.setParent(ELOdan);
		ELOabe.setRight(ELObob);
		
		ELOedd.setLeft(null);
		ELOedd.setParent(ELOdan);
		ELOedd.setRight(null);
		
		ELOdan.setLeft(ELOabe);
		ELOdan.setParent(null);
		ELOdan.setRight(ELOedd);
		
		ELOcharles.setLeft(null);
		ELOcharles.setParent(ELObob);
		ELOcharles.setRight(null);
		
		ELOabe.rotateLeft();
		assertTrue(ELOdan.getLeft().equals(ELObob));
		assertTrue(ELObob.getLeft().equals(ELOabe));
		assertTrue(ELOabe.getLeft()==null);
		assertTrue(ELOabe.getRight()==null);
		assertTrue(ELObob.getRight().equals(ELOcharles));
		assertTrue(ELOcharles.getRight()==null);
		assertTrue(ELOcharles.getRight()==null);
	}
	
	@Test
	void DoubleRotateTest() {
		ELObob.setLeft(null);
		ELObob.setParent(ELOabe);
		ELObob.setRight(null);
		
		ELOabe.setLeft(null);
		ELOabe.setParent(ELOcharles);
		ELOabe.setRight(ELObob);
		
		ELOedd.setLeft(null);
		ELOedd.setParent(ELOdan);
		ELOedd.setRight(null);
		
		ELOdan.setLeft(ELOcharles);
		ELOdan.setParent(null);
		ELOdan.setRight(ELOedd);
		
		ELOcharles.setLeft(ELOabe);
		ELOcharles.setParent(ELOdan);
		ELOcharles.setRight(null);
		
		ELOabe.rotateLeft();
		ELOcharles.rotateRight();
		
		assertTrue(ELOdan.getLeft().equals(ELObob));
		assertTrue(ELObob.getLeft().equals(ELOabe));
		assertTrue(ELObob.getRight().equals(ELOcharles));
		assertTrue(ELOcharles.getRight()==null);
		assertTrue(ELOcharles.getLeft()==null);	
		assertTrue(ELOabe.getRight()==null);
		assertTrue(ELOabe.getLeft()==null);	
		}
	*/
	@Test
	void postFixTest() {
		ELObob.setLeft(ELOabe);
		ELObob.setParent(null);
		ELObob.setRight(ELOedd);
		
		ELOabe.setLeft(null);
		ELOabe.setParent(ELObob);
		ELOabe.setRight(null);
		
		ELOedd.setLeft(ELOdan);
		ELOedd.setParent(ELObob);
		ELOedd.setRight(null);
		
		ELOdan.setLeft(ELOcharles);
		ELOdan.setParent(ELOedd);
		ELOdan.setRight(null);
		
		ELOcharles.setLeft(null);
		ELOcharles.setParent(ELOdan);
		ELOcharles.setRight(null);
		
		ELObob.postFix();
		
		assertTrue(ELObob.getBF()==-2);
		assertTrue(ELOabe.getBF()==0);
		assertTrue(ELOedd.getBF()==2);
		assertTrue(ELOdan.getBF()==1);
		assertTrue(ELOcharles.getBF()==0);
	}
	
	@Test
	void insertTest1() {
		ELObob.setLeft(ELOabe);
		ELObob.setParent(null);
		ELObob.setRight(ELOedd);
		
		ELOabe.setLeft(null);
		ELOabe.setParent(ELObob);
		ELOabe.setRight(null);
		
		ELOedd.setLeft(ELOdan);
		ELOedd.setParent(ELObob);
		ELOedd.setRight(ELOcharles);
		
		ELOdan.setLeft(null);
		ELOdan.setParent(ELOedd);
		ELOdan.setRight(null);
		
		ELOcharles.setLeft(null);
		ELOcharles.setParent(ELOedd);
		ELOcharles.setRight(null);
		
		ELObob.insert(noobP, noobP.getELO());
		
		assertTrue(ELOabe.getBF()==1);
		assertTrue(ELOabe.getLeft().getName().equals("noob"));
	}
	
	@Test
	void insertTest2() {
		ELObob.setParent(null);
		ELObob.setLeft(null);
		ELObob.setRight(null);
		AVLPlayerNode Tree = ELObob; //4k elo
		Tree = Tree.insert(abeP, abeP.getELO()); //3k elo
		Tree = Tree.insert(danP, danP.getELO()); //6k elo
		Tree = Tree.insert(charlesP, charlesP.getELO()); //5k elo
		Tree = Tree.insert(proP, proP.getELO()); //10 k elo
		Tree = Tree.insert(eddP, eddP.getELO()); // 7k elo
		Tree = Tree.insert(newguyP, newguyP.getELO()); //8k elo
		
		Tree = Tree.insert(newguyP, newguyP.getELO()); //test the case of duplicate inserts
		Tree = Tree.insert(eddP, eddP.getELO()); //test the case of duplicate inserts
		
		Tree = Tree.insert(noobP, noobP.getELO()); // 100 elo
		assertTrue(Tree.getName().equals(ELObob.getRoot().getName()));
		//below is what the tree structure should be theoretically. This would have involved multiple rotations
		//           Dan
		//        /         \
		//      Bob         newguy
		//     /\           /   \
		// (abe)(charles) (edd)  (pro)
		//  /
		// noob
		assertTrue(Tree.getLeft().getLeft().getLeft().getName().equals("noob")); //multiple .lefts are stylistically crap but for junits they get the job done if we know what the tree should look like
		assertTrue(Tree.getName().equals("Dan"));
		assertTrue(Tree.getBF()==1);
		assertTrue(Tree.getRight().getBF()==0);
		}
	
	/**
	 * the following tests multiple methods in tandum, more than just delete
	 * even tests the successor method at the end
	 */
	@Test
	void DeleteFunctionalTest() {
		ELObob.setParent(null);
		ELObob.setLeft(null);
		ELObob.setRight(null);
		AVLPlayerNode Tree = ELObob; //4k elo
		Tree = Tree.insert(abeP, abeP.getELO()); //3k elo
		Tree = Tree.insert(danP, danP.getELO()); //6k elo
		Tree = Tree.insert(charlesP, charlesP.getELO()); //5k elo
		Tree = Tree.insert(proP, proP.getELO()); //10 k elo
		Tree = Tree.insert(eddP, eddP.getELO()); // 7k elo

		Tree = Tree.insert(newguyP, newguyP.getELO()); //8k elo
		
		Tree = Tree.insert(noobP, noobP.getELO()); // 100 elo
		
		Tree = Tree.delete(6000); // delete dan
		Tree = Tree.delete(5000); // delete charles
		
		// tree should NOW look like the following
		//           edd
		//        /         \
		//      abe         newguy
		//     /\                \
		// (noob)(bob)         (pro)
		// 
		

		assertTrue(Tree.getBF()==0);
		assertTrue(Tree.getName().equals("Edd"));
		assertTrue(Tree.getRight().getBF()==-1);
		assertTrue(Tree.getLeft().getRight().getName().equals("Bob"));
		
		//we also need to make sure adding works here, adding a player with an elo between noob and abe should be a good final functionality test, because it should make us
		//do one final double rotate
		
		Player IfYouSeeThisIHopeYouHaveAWonderfulDay = new Player("Frank",69420,500);
		Tree.insert(IfYouSeeThisIHopeYouHaveAWonderfulDay, IfYouSeeThisIHopeYouHaveAWonderfulDay.getELO());
		
		// tree should NOW look like the following
		//           edd
		//        /         \
		//      Frank         newguy
		//     /\                \
		// (noob)(abe)         (pro)
		//         \
		//          (bob)
		
		assertTrue(Tree.getLeft().successor().getName().equals("Bob"));
		assertTrue(Tree.getBF()==1);
		assertTrue(Tree.getRight().getBF()==-1);
	}
	
	@Test
	void StringTest() {
		ELObob.setParent(null);
		ELObob.setLeft(null);
		ELObob.setRight(null);
		AVLPlayerNode Tree = ELObob; //4k elo
		Tree = Tree.insert(abeP, abeP.getELO()); //3k elo
		Tree = Tree.insert(danP, danP.getELO()); //6k elo
		Tree = Tree.insert(charlesP, charlesP.getELO()); //5k elo
		Tree = Tree.insert(proP, proP.getELO()); //10 k elo
		Tree = Tree.insert(eddP, eddP.getELO()); // 7k elo
		Tree = Tree.insert(newguyP, newguyP.getELO()); //8k elo
		
		Tree = Tree.insert(abeP, abeP.getELO());
		Tree = Tree.insert(danP, danP.getELO());
		Tree = Tree.insert(charlesP, charlesP.getELO()); // some duplicate inserts just to make sure they aren't impacting the tree in any way
		
		
		Tree = Tree.insert(noobP, noobP.getELO()); // 100 elo
		assertTrue(Tree.treeString().equals("((((noob)Abe)Bob(Charles))Dan((Edd)newguy(pro)))"));
		String CorrectLeaderboard ="NAME\t\tID\tSCORE\npro\t\t10\t10000.0\nnewguy\t\t6\t8000.0\nEdd\t\t5\t7000.0\nDan\t\t4\t6000.0\nCharles\t\t3\t5000.0\nBob\t\t2\t4000.0\nAbe\t\t1\t3000.0\nnoob\t\t0\t100.0\n";
		assertEquals(Tree.scoreboard(),CorrectLeaderboard);
	}
	
	@Test
	void GetRankTest() {
		ELObob.setParent(null);
		ELObob.setLeft(null);
		ELObob.setRight(null);
		AVLPlayerNode Tree = ELObob; //rank 6
		Tree = Tree.insert(abeP, abeP.getELO()); //rank 7
		Tree = Tree.insert(danP, danP.getELO()); ////rank 4
		Tree = Tree.insert(charlesP, charlesP.getELO()); //rank 5
		Tree = Tree.insert(proP, proP.getELO()); //rank 1
		Tree = Tree.insert(eddP, eddP.getELO()); // rank 3
		Tree = Tree.insert(newguyP, newguyP.getELO()); //rank 2
		Tree = Tree.insert(noobP, noobP.getELO()); // rank 8
		assertEquals(6,Tree.getRank(bobP.getELO()));
		assertEquals(7,Tree.getRank(abeP.getELO()));
		assertEquals(4,Tree.getRank(danP.getELO()));
		assertEquals(5,Tree.getRank(charlesP.getELO()));
		assertEquals(1,Tree.getRank(proP.getELO()));
		assertEquals(3,Tree.getRank(eddP.getELO()));
		assertEquals(2,Tree.getRank(newguyP.getELO()));
		assertEquals(8,Tree.getRank(noobP.getELO()));
		
	}
	
	@Test
	void GetNodeandPlayerTest() {
		AVLPlayerNode Tree = ELObob;
		Tree = Tree.insert(abeP, abeP.getELO()); 
		Tree = Tree.insert(danP, danP.getELO()); 
		Tree = Tree.insert(charlesP, charlesP.getELO());
		Tree = Tree.insert(proP, proP.getELO()); 
		Tree = Tree.insert(eddP, eddP.getELO()); 
		Tree = Tree.insert(newguyP, newguyP.getELO());
		Tree = Tree.insert(noobP, noobP.getELO());
		
		AVLPlayerNode target = Tree.getNode(eddP.getELO());
		Player bullseye = Tree.getPlayer(eddP.getELO());
		assertEquals(bullseye.getName(),target.getName());
		target = Tree.getNode(danP.getELO());
		bullseye = Tree.getPlayer(danP.getELO());
		assertEquals(bullseye.getName(),target.getName());
	}
	

		
	
}
