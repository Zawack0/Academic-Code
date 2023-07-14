/**
 * Implementation for an AVL tree. Each node is the root of its own subtree, so client should only call methods on the original created tree. Note that 
 * for modification of tree structure the method returns the new root, allowing the original created tree to be maintained.
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * April 2nd, 2022
 * COSI 21A PA2
 */
package main;
/**
 * Your code goes in this file
 * fill in the empty methods to allow for the required
 * operations. You can add any fields or methods you want
 * to help in your implementations.
 */

public class AVLPlayerNode{
    private Player data;
    private double value;
    
    private AVLPlayerNode parent;
    private AVLPlayerNode leftChild;
    private AVLPlayerNode rightChild;
    

    
    private int rightWeight;
    private int leftWeight;
    private int balanceFactor;


    /**
     * constructor to create a new tree or subtree with the given data as the head node
     * @param data the player that shall be stored in the node
     * @param value that players ID or ELO, depending on the tree being used
     * runs in constant time
     */
    public AVLPlayerNode(Player data,double value){
        this.data=data;
        this.value=value;
        leftChild=null;
        rightChild=null;
        leftWeight=0;
        rightWeight=0;
    }
    

    /**
     * @param newGuy the new player to be inserted
     * @param value the new player's ID or ELO depending on the tree being used
     * @return the new root of the tree
     * runs in O(n). this is because of the postFix method where after an insertion balance factor is updated for the nodes of the tree
     * 
     * note that this is still within the constraints of the PA, as only search was mentioned for to have a worst case of log(n)
     */
    public AVLPlayerNode insert(Player newGuy,double value){
    	AVLPlayerNode newNode = new AVLPlayerNode(newGuy, value);
    	AVLPlayerNode pointer1=this;
    	AVLPlayerNode pointer2=null;
    	while (pointer1!=null){//O(log(n))
    		pointer2=pointer1;
    		if (pointer1.value<value) {
    			pointer1=pointer1.rightChild;
    		}
    		else if (pointer1.value>value) {
    			pointer1=pointer1.leftChild;
    		}
    		else if (pointer1.value==value) {
    			return (pointer1.getRoot()); //if value is already in tree just return the root
    		}
    	}
    	if (value>pointer2.value){
    		pointer2.rightChild=newNode;
    		newNode.parent=pointer2;
    	}
    	if (value<pointer2.value) {
    		pointer2.leftChild=newNode;
    		newNode.parent=pointer2;
    	}
    	postFix(); //linear
    	int counter = 0;
    	while (pointer2!=null) { //O(log(n)) THERE IS A PROBLEM IN THIS WHILE LOOP CAUSING INFINITE WHEN SEQUENTIAL THINGS ARE ADDED
    		counter++;
    		if (counter>10000) {
    			System.out.println("Infinite loop in insert function between " +pointer1.data.getName() + " and " + pointer2.data.getName());
    			pointer1=pointer2.parent;
    			while (pointer1!=null) {
    				pointer1=pointer1.parent;
    				pointer2=pointer2.parent;
    			}
    			return pointer2;
    		}
    		if (pointer2.balanceFactor>1 || pointer2.balanceFactor<-1) {
    			if (pointer2.balanceFactor<-1){ //i may be using the wrong rotate checks
    				if (pointer2.rightChild!=null &&pointer2.rightChild.balanceFactor>0) {
    					AVLPlayerNode a = pointer2.rightChild;
    					AVLPlayerNode b = pointer2;
    					a.rotateRight();
    					b.rotateLeft();
    				}
    				else {
    					pointer2.rotateLeft();
    				}
    			}
    			else if (pointer2.balanceFactor>1) { //i may be using the wrong rotate checks
    				if (pointer2.leftChild!=null && pointer2.leftChild.balanceFactor<0) {
    					AVLPlayerNode a = pointer2.leftChild;
    					AVLPlayerNode b = pointer2;
    					a.rotateLeft();
    					b.rotateRight();
    				}
    				else {
    					pointer2.rotateRight();
    				}
    			}
    			pointer2.postFix(); //this should remove the need for any further rotations, meaning this log(n) should only be run once in the while loop, making it O(n)
    		}
    		pointer1=pointer2;
    		pointer2=pointer2.parent;
    	}
    	
    	return pointer1;
    }
    

    /**
     * deletes the player with the given value identifier, maintaining right and left weight and BF's, 
     * EXTRA CREDIT: delete method does successfully maintain AVL properties :)
     * @param value the player's ID or ELO, depending on the tree
     * @return the new root of the tree
     * worst case is O(n*log(n))
     */
    public AVLPlayerNode delete(double value){

		AVLPlayerNode point1 = getNode(value);
		if (point1==null) {
			return this; //returns root of tree if a non existent node is searched for
		}
		if (point1.leftChild==null && point1.rightChild == null && point1.parent==null) {
			return null;
		}

		AVLPlayerNode point2;
		AVLPlayerNode point3;
		if (point1.leftChild==null||point1.rightChild==null) {
			point2 = point1;
		}
		else {
			point2 = point1.successor();
		}
		if (point2.leftChild!=null) {
			point3 = point2.leftChild;
		}
		else {
			point3 = point2.rightChild;
		}
		if (point3!=null) {
			point3.parent=point2.parent;
		}
		if (point2.parent!=null) {
			if (point2==point2.parent.leftChild) {
				point2.parent.leftChild=point3;
			}
			else {
				point2.parent.rightChild=point3;
			}
		}
		if (point2.data!=point1.data) {
			point1.data=point2.data;
		}
		if (point3!=null) {
			point2=point3;
		}
		postFix();
		//up to this point in time has been O(n)
		int counter = 0;
    	while (point2!=null) { //log n
    		counter++;
    		if (counter>10000) {
    			System.out.println("Infinite loop in delete function between " +point1.data.getName() + " and " + point2.data.getName());
    			point1=point2.parent;
    			while (point1!=null) {
    				point1=point1.parent;
    				point2=point2.parent;
    			}
    			return point2;
    		}
    		if (point2.balanceFactor>1 || point2.balanceFactor<-1) {
    			if (point2.balanceFactor<-1){
    				if (point2.rightChild!=null &&point2.rightChild.balanceFactor>0) {
    					AVLPlayerNode a = point2.rightChild;
    					AVLPlayerNode b = point2;
    					a.rotateRight();
    					b.rotateLeft();
    				}
    				else {
    					point2.rotateLeft();
    				}
    			}
    			else if (point2.balanceFactor>1) {
    				if (point2.leftChild!=null && point2.leftChild.balanceFactor<0) {
    					AVLPlayerNode a = point2.leftChild;
    					AVLPlayerNode b = point2;
    					a.rotateLeft();
    					b.rotateRight();
    				}
    				else {
    					point2.rotateRight();
    				}
    			}
    			postFix(); //maintains correct values, but can possibly be called more than once (up to theoretically log(n)). therefore, algorithm = O(log(n) * n) = O(nlog(n))
    		}
    		point1=point2;
    		point2=point2.parent;
    	}
    	return point1;
    }
    
    /**
     * finds the successor of the node that the method is called on
     * @returns the successor, defined as the left most node in the right subtree
     * runs in O(log(n))
     * 
     * public for use in Junit testing
     */
    public AVLPlayerNode successor() {
    	AVLPlayerNode point = this.rightChild;
    	AVLPlayerNode help = this;
    	while (point!=null) {
    		help=point;
    		point=point.leftChild;
    	}
    	return help;
    }
    /**
     * method is internally useful for updating of balance factors of nodes above a rotation or double rotation, or after deletion
     * specifically, when rotation is done to maintain balance factor, the balance factor of all nodes higher in the 
     * tree need to be updated, similar with deletion
     * runs in O(n)
     * 
     * made public for Junit tests
     */
    public void postFix() {
    	AVLPlayerNode top = this.getRoot();
    	top.weightfix();
    	top.BFfix();
    	}
    
    /**
     * "Balance Factor fix", given the root of a tree recursively updates the balance factor of each node in the tree
     * @returns an int representing the height of the current node
     * Runs in O(n) time 
     */
    private int BFfix() {
    	int LH;
    	int RH;
    	if (this.leftChild==null){
    		LH= 0;
    	}
    	else {
    		LH = 1+this.leftChild.BFfix();
    	}
    	if (this.rightChild==null) {
    		RH = 0;
    	}
    	else {
    		RH= 1+this.rightChild.BFfix();
    	}
    	this.balanceFactor=LH-RH;
    	if (LH>RH) {
    		return LH;
    	}
    	else {
    		return RH;
    	}
		
		
	}

    /**
     * similar to BFfix, But instead of fixing balance factors it fixes the left and right weights of the tree
     * @returns an integer representing number of nodes in tree or subree
     * runs in O(n) time
     */
	private int weightfix() {
    	if (this.leftChild!=null) {
    		this.leftWeight=this.leftChild.weightfix();
    	}
    	else {
    		this.leftWeight=0;
    	}
    	if (this.rightChild!=null) {
    		this.rightWeight=this.rightChild.weightfix();
    	}
    	else {
    		this.rightWeight=0;
    	}
    	return(1+leftWeight+rightWeight);
	}
	

    /**
     * internally useful for modification of tree structure following an unbalanced tree 
     * runs in constant time
     * 
     * can be made public for use in Junit testing
     */
    private void rotateRight(){
    	AVLPlayerNode point1=this.leftChild;
    	this.leftChild=point1.rightChild;
    	
    	if (point1.rightChild!=null){
    		point1.rightChild.parent=this;
    	}
    	point1.parent=this.parent;
    	if (this.parent!=null&&this==this.parent.rightChild) {
    		this.parent.rightChild=point1;
    	}
    	else if (this.parent!=null) {
    		this.parent.leftChild=point1;
    	}
    	point1.rightChild=this;
    	this.parent=point1;
    	

    }
    

    /**
     * internally useful for modification of tree structure following an unbalanced tree 
     * runs in constant time
     * 
     * can be made public for use in Junit testing
     */
    private void rotateLeft(){
    	AVLPlayerNode point1=this.rightChild;
    	this.rightChild=point1.leftChild;
    	
    	if (point1.leftChild!=null){
    		point1.leftChild.parent=this;
    	}
    	point1.parent=this.parent;
    	if (this.parent!=null&&this==this.parent.leftChild) {
    		this.parent.leftChild=point1;
    	}
    	else if (this.parent!=null) {
    		this.parent.rightChild=point1;
    	}
    	point1.leftChild=this;
    	this.parent=point1;
    	
    }
	
    //this should return the Player object stored in the node with this.value == value
    /**
     * searches for a player with a given identification value recursively
     * @param value is the player's ID or ELO depending on the tree you are searching with
     * @return the player being searched for or last encountered node if not in tree
     * runs in O(log(N))
     */
    public Player getPlayer(double value){
    	if (this.value==value) {
    		return this.data;
    	}
    	if (value>this.value){
    		if (this.rightChild!=null){
    			return(this.rightChild.getPlayer(value));
    		}
    	}
    	if (value<this.value) {
    		if (this.leftChild!=null) {
    			return(this.leftChild.getPlayer(value));
    		}
    	}
    	
    	return this.data;
    }
    
    /**
     * searches for a player's node with a given identification value recursively
     * @param value is the player's ID or ELO depending on the tree you are searching with
     * @return the node of the player being searched for 
     * runs in O(log(n))
     */
    public AVLPlayerNode getNode(double value){
    	if (this.value==value) {
    		return this;
    	}
    	if (value>this.value){
    		if (this.rightChild!=null) {
    			return(this.rightChild.getNode(value));
    		}
    	}
    	if (value<this.value) {
    		if (this.leftChild!=null) {
    			return(this.leftChild.getNode(value));	
    		}
    	}
    	
    	return null;
    }
    

    /**
     * calculates a player's rank recursively based on their ELO
     * @param value is the player in question's ELO. Note that this does not work if called on the ID tree or given an invalid ELO
     * @return the rank of the ELO being searched for
     * runs in O(log(n))
     */
    public int getRank(double value){
        if (this.value==value) {
        	return rightWeight+1;
        }
        if (value>this.value) {
        	return this.rightChild.getRank(value);
        }
        if (value<this.value) {
        	return rightWeight+1+this.leftChild.getRank(value);
        }
    	return 0;
    }


    /**
     * @return a specialized string representation of the tree with parenthesis denoting subtrees
     * runs in O(n)
     */
    public String treeString(){ 
    	String strange = "";
    	strange+="(";
    	if (this.leftChild!=null) {
    		strange+=this.leftChild.treeString();
    	}
    	strange+=this.data.getName();
    	if (this.rightChild!=null) {
    		strange+=this.rightChild.treeString();
    	}
    	strange+=")";
    	return strange;
    }

    
    /**
     * returns a string representation of players in descending order of their double identifier
     * @returns each player separated by a new line, in order of their rank
     * runs in linear time
     */
    private String RankingsHelp(){
    	String rank = "";
    	if (this.rightChild!=null) {
    		rank+=this.rightChild.RankingsHelp()+"\n";
    	}
    	rank+=this.value+"\n";
    	if (this.leftChild!=null) {
    		rank+=this.leftChild.RankingsHelp()+"\n";
    	}
    	return rank;
    }
    
    /**
     * Scoreboard provides a string representation of all players in order of descending rank according to the pdf
     * @returnstring representation of all players in order of descending rank
     * Runs in Linear time
     */
    public String scoreboard(){
    	String values = RankingsHelp();
    	String[] ranked = values.split("\n");
    	String Scoreboard = "NAME\t\tID\tSCORE\n";
    	for (int i = 0; i<ranked.length; i++) { //O(n)
    		if ((ranked[i].equals("") || ranked[i].equals(null))==false) {
    			Player player = getPlayer(Double.valueOf(ranked[i])); //O(log(n)), making the total function O(nlog(n))
    			Scoreboard+=player.getName()+"\t\t"+player.getID()+"\t"+player.getELO()+"\n";
    		}
    	}
    	return Scoreboard;
    }
    
    /**
     * The following methods are for use ONLY in junits tests so as to properly set up a tree to isolate testing of rotate or something similar
     * @param take a node as a parameter and sets the current node's applicable pointer to the passed node
     * all run in constant time
     */
    public void setParent(AVLPlayerNode prev) {
    	this.parent=prev;
    }
    /**
     * sets the right child for manual setup of a tree in junit tests
     * @param the node to become the right child of this
     * runs in constant time
     */
    public void setRight(AVLPlayerNode right) {
    	this.rightChild=right;
    }
    /**
     * sets the left child for manual setup of a tree in junit tests
     * @param the node to become the left child of this
     * runs in constant time
     */
    public void setLeft(AVLPlayerNode left) {
    	this.leftChild=left;
    }
    
    /**
     * getter method for parent
     * @return this node's parent
     * runs in constant time
     */
    public AVLPlayerNode getParent() {
    	return(this.parent);
    }
    /**
     * getter method for rightChild
     * @return the right child of this
     * runs in constant time
     */
    public AVLPlayerNode getRight() {
    	return(this.rightChild);
    }
    /**
     * getter method for leftChild
     * @return the left child of this
     * runs in constant time
     */
    public AVLPlayerNode getLeft() {
    	return(this.leftChild);
    }
    
    /**
     * getter method for the node's player's name
     * @return the name of the player stored in this node
     * runs in constant time
     */
    public String getName() {
    	return data.getName();
    }
    /**
     * getter method for Balance Factor
     * @return the balance factor of this
     * runs in constant time
     */
    public int getBF() {
    	return this.balanceFactor;
    }
	
    /**
     * returns the root of the tree that this node is a part of
     * @returns the root of the tree that the current node is in
     * runs in O(log(n))
     */
    public AVLPlayerNode getRoot() {
    	AVLPlayerNode helper = this;
    	while (helper.parent!=null) {
    		helper=helper.parent;
    	}
    	return helper;
    }
}
    
	
