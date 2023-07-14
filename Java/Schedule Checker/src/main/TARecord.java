/**
 * Connor Zawacki
 * connorzawacki@braneis.edu
 * 5/1/21
 * PA#7
 * 
 * TARecord keeps track of everything to do with a TA, including their name, Job starts and IDs, fraud lines, and whether or not they reported as a batch
 */
package main;
import java.util.*;
public class TARecord {
	String name;
	HashMap<Integer, Integer> Job; //Hashmap is a fairly efficient way of storing and accessing job start times and end ID, 2 important values for comparison
	ArrayList<Integer> Started; // helps keep track of unfinished jobs and used for job completion
	int Batch_Line; 
	Boolean Batch;
	ArrayList<Integer> bad_lines; // variables for batch and bad lines are important for the format of desired output


	/*
	 * Constructor, makes all the data fields new empty values
	 */
	public TARecord (String name) {
		this.name = name;
		Started = new ArrayList<Integer>();
		bad_lines = new ArrayList<Integer>();
		Job = new HashMap<Integer, Integer>();
		Batch = false;
	}
	
	/**
	 * Gets the job ID number given its original start time
	 * Getter methods were mostly made before I was informed that our methods and fields could be public, although some are still used
	 * @param Start the line the job was started on
	 * @returns the job ID
	 */
	public int getID(int Start) {
		return (int) Job.get(Start);
	}
	
	/**
	 * Adds a Job to the Job map
	 * @param Start starting line of job
	 * @param ID value of job
	 */
	public void addJob(int Start, int ID) {
		Job.put(Start, ID);
	}
	
	/**
	 * adds an unstarted job to the job map
	 * @param ID value of unstarted job
	 */
	public void addJob(int ID) {
		Job.put(null, ID);
	}
	
	/**
	 * adds a job to the started arraylist
	 * @param start line of the job
	 */
	public void StartJob(int start) {
		Started.add(start);
	}
	
	/**
	 * finishes a job with the given ID
	 * finishes jobs in the order they were recived
	 * @param ID
	 */
	public void FinishJob(int ID) {
		if (Started.size() == 0) {
			addJob(ID);
		}
		else  {
			addJob(Started.get(0), ID);
			Started.remove(0); // unfortunetly, this has runtime n, where n is the number of started jobs. Fortunately, this should be very low, usually 1 or 2, making it ok
		}
	}
	
	/**
	 * Getter methods were mostly made before I was informed that our methods and fields could be public, although some are still used
	 * this one returns the Job map for use in comparisons
	 * @returns the record of job starts and IDs
	 */
	public HashMap<Integer, Integer> GetData(){
		return Job;
	}
	
	/**
	 * configures the applicable variables identifying the TA as one that reported as a batch
	 * the batch is on the given line parameter
	 * @param line
	 */
	public void BatchData(int line) {
		Batch = true;
		Batch_Line = line;
	}
	
	/**
	 * Getter methods were mostly made before I was informed that our methods and fields could be public, although some are still used
	 * this one just returns the line of a reported batch
	 * @return
	 */
	public int getBatchData() {
		return Batch_Line;
	}
	
	/**
	 * Getter methods were mostly made before I was informed that our methods and fields could be public, although some are still used
	 * this one is important for output, as it returns the line(s) where the infractions occurred
	 * @return
	 */
	public ArrayList<Integer> getBadLine() {
		return bad_lines;
	}
	
	/**
	 * Getter methods were mostly made before I was informed that our methods and fields could be public, although some are still used
	 * this one is just an identifier if the TA in question reported a job in a batch
	 * @return
	 */
	public Boolean IsBatch() {
		return Batch;
	}
	
	/**
	 * This is important for checking if the TA is already in the arraylist of TAs used in .contains
	 * (I think anyway. It is a comparison based on name, which doesn't damage the function of the main, so even if unnecessary its harmless)
	 * @param other is the other TA
	 * @return
	 */
	public boolean equals(Object other) {
		if (other instanceof TARecord) {
			return (name.equals(((TARecord) other).name));
		}
		if (other instanceof String) {
			return (name.equals((String)other));
		}
		return false;
	}
	
	/**
	 * The logic of comparing TAs is somewhat split between TARecord and TAChecker.
	 * I found it easier if the bulk of the actual comparison happened in the record class, where the checker class could just worry about identifying and labeling fraud
	 * @param other is the other TARecord that the current is being compared to
	 * @returns an int value representing the number of errors between the 2 TAs. had to be capable of expressing both presence/absence AND number of errors
	 */
	public int Compare(TARecord other) {
		int bad = 0; // the return value
		HashMap<Integer, Integer> other_data = other.GetData();
		Object[] keys =  Job.keySet().toArray();
		Object[] other_keys = other.Job.keySet().toArray(); 
		for (int i = 0; i<keys.length; i++) {
			for (int j = 0; j < other_keys.length; j++) {
				if(keys[i] != null && other_keys[j] != null) {
					if((int)keys[i]>(int)other_keys[j] && Job.get((int)keys[i])< other_data.get((int)other_keys[j])) { // the logic tests for the instance where one TA starts a job after another but finishes before them.
						bad_lines.add((int)keys[i]);
						bad++;
					}
				}
			}
		}
		return bad;
	}
	
	/**
	 * not needed for the project, mostly used for debugging purposes
	 * returns string representation of the TA
	 */
	public String toString() {
		return(name + " : " + Job.toString());
	}
}
	
	


	


