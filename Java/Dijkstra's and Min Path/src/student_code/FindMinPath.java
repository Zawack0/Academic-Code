/**
 * The main method used to run a specialized version of Dijkstra's algorithm to nativity through
 * our graph to get to our destination. 
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * April 20th, 2022
 * COSI 21A PA3
 */
package student_code;
import java.io.*; //only used here for outputting answers as a file


public class FindMinPath {

	/**
	 * Dijkstra's algorithm essentially, followed by some logic allowing us to output our solution to a new txt file
	 * runs in O(VlogV+E)
	 */
	public static void main(String[] args) {
		Min_Priority_Queue Q = new Min_Priority_Queue();
		GraphWrapper gw = new GraphWrapper(true);
		GraphNode home = gw.getHome();
		GraphNode neighbor;
		home.priority=0;
		Q.insert(home);
		
		GraphNode fin = null;
		boolean flag = true;
		while (Q.isEmpty()==false && flag==true) {
			GraphNode curr = Q.pullHighestPriorityElement();
			if (curr.isGoalNode()) {
				fin = curr;
				flag = false;
			}
			else {
				if (curr.hasNorth()) {
					neighbor = curr.getNorth();
					int x = curr.priority+curr.getNorthWeight();
					if (Q.contains(neighbor)==false) {
						neighbor.priority=x;
						neighbor.previousDirection="North";
						neighbor.previousNode=curr;
						Q.insert(neighbor);
					}
					else {
						if(neighbor.priority>x) {
							Q.update_prio(neighbor, x);
							neighbor.previousDirection="North";
							neighbor.previousNode=curr;
						}
					}
				}
				if (curr.hasEast()) {
					neighbor = curr.getEast();
					int x = curr.priority+curr.getEastWeight();
					if (Q.contains(neighbor)==false) {
						neighbor.priority=x;
						neighbor.previousDirection="East";
						neighbor.previousNode=curr;
						Q.insert(neighbor);
					}
					else {
						if(neighbor.priority>x) {
							Q.update_prio(neighbor, x);
							neighbor.previousDirection="East";
							neighbor.previousNode=curr;
						}
					}
				}
				if (curr.hasSouth()) {
					neighbor = curr.getSouth();
					int x = curr.priority+curr.getSouthWeight();
					if (Q.contains(neighbor)==false) {
						neighbor.priority=x;
						neighbor.previousDirection="South";
						neighbor.previousNode=curr;
						Q.insert(neighbor);
					}
					else {
						if(neighbor.priority>x) {
							Q.update_prio(neighbor, x);
							neighbor.previousDirection="South";
							neighbor.previousNode=curr;
						}
					}
				}
				if (curr.hasWest()) {
					neighbor = curr.getWest();
					int x = curr.priority+curr.getWestWeight();
					if (Q.contains(neighbor)==false) {
						neighbor.priority=x;
						neighbor.previousDirection="West";
						neighbor.previousNode=curr;
						Q.insert(neighbor);
					}
					else {
						if(neighbor.priority>x) {
							Q.update_prio(neighbor, x);
							neighbor.previousDirection="West";
							neighbor.previousNode=curr;
						}
					}
				}
			}
		}
		String Solution = "";
		if (fin==null) {
			Solution = "There is no path";
		}
		else {
			int counter = 0;
			GraphNode last = fin;
			while (last.equals(home)!=true) {
				counter++;
				last = last.previousNode;
			}
			String[] answer = new String[counter];
			for (int i = counter-1; i>=0; i--) {
				answer[i] = fin.previousDirection;
				fin = fin.previousNode;
			}
			for (int i = 0; i<answer.length; i++) {
				Solution+=answer[i];
				if (i<answer.length-1) {
					Solution+="\n";
				}
			}
		}
		File output = new File("Solution.txt");
		try {
			FileWriter write = new FileWriter("Solution.txt");
			write.write(Solution);
			write.close();
		} catch (IOException e) {
			System.out.println("Error, I don't know how to output to a file apparently");
		}
	}
}
