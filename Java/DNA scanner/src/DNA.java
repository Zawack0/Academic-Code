/**
 * Connor Zawacki
 * connorzawacki@braneis.edu
 * 3/3/21
 * PA#3
 * Program receives file input about DNA sequence in a specific format, and outputs information about said sequence in a user inputed output file
 * Throws file not found exception. Shouldn't be an issue unless user input is incorrect as to the input and output pathways
 * also of note is ecoli.txt, which is empty as it is not mentioned what we need to put in it.
 */
import java.util.*;
import java.io.*;

public class DNA {
	public static final int min_codon = 5;
	public static final int valid_percent = 30;
	public static final int unique_nucleotide = 4;
	public static final int nucleotides_in_codon = 3;
	public static final double adenine_mass = 135.128;
	public static final double cytosine_mass = 111.103;
	public static final double guanine_mass = 151.128;
	public static final double thymine_mass = 125.107;
	
	/**
	 * main initializes arrays and other needed variables, loops through each line of the input file, calling  methods, then finally calling output method with all needed data 
	 * @throws FileNotFoundException shouldn't be an issue unless user fails to input correct input and output paths
	 */
	public static void main(String[] args) throws FileNotFoundException  {
		Scanner console = new Scanner(System.in);
		System.out.println("This program reports information about DNA");
		System.out.println("nucleotide sequences that may encode proteins.");
		System.out.print("Input file name? ");
		String input_filename = console.next();
		System.out.print("Output file name? ");
		String output_filename = console.next();
		System.out.println();
		Scanner input = new Scanner(new File(input_filename));
		PrintStream output = new PrintStream(new File(output_filename));
		/** the above only collects and initiates the file input and output*/
		while (input.hasNextLine()) {
			int[] distribution = new int[unique_nucleotide]; /** we have to initialize both the arrays in the while loop so we can reset their values */
			double[] masses = new double[unique_nucleotide];
			String name = input.nextLine();
			String sequence = input.nextLine();
			double total_mass = nuc_counts(sequence, distribution);
			nuc_masses(distribution, total_mass, masses);
			total_mass = Math.round(total_mass*10);
			total_mass /= 10; /** I wanted the un-rounded total mass in the nuc_masses function, but total mass should be rounded before file_out is called */
			String[] codons = codon_list(sequence);
			Boolean isprotein = protein_test(codons, masses);
			file_out(name, sequence, distribution, masses, total_mass, codons, isprotein, output);
		}
		console.close();
	}
	
	/**
	 * 
	 * @param output_filename is the place where all the output goes
	 * @param name is the DNA region's given name
	 * @param sequence is the unedited nucleotide sequence
	 * @param distribution is the array describing how much of each nucleotide is present
	 * @param masses is the array describing how much of each nucleotide contributes to percent of mass
	 * @param total_mass is the entire mass of the sequence
	 * @param codons essentially the sequence string split into groups of 3 nucleotides
	 * @param isprotein bool value keeping track of the sequence's protein status
	 * @throws FileNotFoundException shouldn't be an issue as long as user inputs correct output file. I input src//output.txt for testing
	 */
	public static void file_out(String name, String sequence, int[] distribution, double[] masses, double total_mass, String[] codons, Boolean isprotein, PrintStream output) throws FileNotFoundException {
		output.println("Region Name: " + name);
		output.println("Nucleotides: " + sequence.toUpperCase());
		output.println("Nuc. Counts: " + Arrays.toString(distribution));
		output.println("Total Mass%: " + Arrays.toString(masses) + " of " + total_mass);
		output.println("Codons List: " + Arrays.toString(codons));
		if (isprotein==true) {
			output.println("Is Protein?: YES" );
		}
		else {
			output.println("Is Protein?: NO" );
		}
		output.println();
	}
	/**
	 * @param sequence is the unedited nucleotide sequence
	 * @param distriubution array to be modified with applicable numbers
	 * @return returns total mass for use in masses function
	 * NOTE: distribution array goes in alphabetical order, as in {A, C, G, T}
	 */
	public static double nuc_counts(String sequence, int[] distribution) {
		double total = 0;
		for (int i = 0; i<sequence.length(); i++) { /** series of if statements increments the applicable nucleotide count in distribution, and adds applicable mass to total*/
			if (sequence.toUpperCase().charAt(i) == 'A') {
				distribution[0]++;
				total += adenine_mass;
			}
			else if (sequence.toUpperCase().charAt(i) == 'C') {
				distribution[1]++;
				total += cytosine_mass;
			}
			else if (sequence.toUpperCase().charAt(i) == 'G') {
				distribution[2]++;
				total += guanine_mass;
			}
			else if (sequence.toUpperCase().charAt(i) == 'T') {
				distribution[3]++;
				total += thymine_mass;
			}
			else if (sequence.charAt(i) == '-' ) {
				total += 100;
			}
		}
		return total;
	}
	/**
	 * 
	 * @param sequence is the unmodified nucleotide sequence
	 * @return returns an array of strings representing 3 nucleotide sub-sequences known as codons
	 * First removes all dashes, then splices the string into sequences of 3 characters each
	 */
	public static String[] codon_list(String sequence) {
		for (int i = 0; i<sequence.length(); i++) { /** gets rid of all the junk*/
			if (sequence.charAt(i) == '-') {
				sequence = sequence.substring(0,i) + sequence.substring(i+1, sequence.length());
				i-=1; /** this is necessary for sequences with many '-' in a row */
			}
		}
		int all_codons = sequence.length()/nucleotides_in_codon;
		String[] codons = new String[all_codons];
		int temp = 0; /** we actually want to increment 2 variables every time we go through the loop*/
		for (int i = 0; i<sequence.length(); i+=nucleotides_in_codon) {
			codons[temp] = Character.toString(sequence.charAt(i)) + Character.toString(sequence.charAt(i+1))+ Character.toString(sequence.charAt(i+2)); /** not sure why I had to use character.tostring here, but it kept on making them ints if I didn't */
			codons[temp] = codons[temp].toUpperCase();
			temp++; /** i keeps track of where we are in the nucleotide sequence, temp keeps track of where we are in the codon sequence*/
		}
		return codons;
	}
	/**
	 * @param distribution the total count of each nucleotide
	 * @param total_mass is the grand total of the gene's mass
	 * @param masses is the array describing the percent that each nucleotide contribute to the mass
	 * All this method does is make the masses array ready for output
	 */
	public static void nuc_masses(int[] distribution, double total_mass, double[]masses) {
		masses[0] = Math.round((distribution[0]*adenine_mass)*100 / total_mass*10);
		masses[1] = Math.round((distribution[1]*cytosine_mass)*100 / total_mass*10);
		masses[2] = Math.round((distribution[2]*guanine_mass)*100 / total_mass*10);
		masses[3] = Math.round((distribution[3]*thymine_mass)*100 / total_mass*10);
		/** the above LOOKS like it should be for looped through, but honestly, defining a whole new method to equate a specific
		 *  index of distribution with its mass or using if statements in the for loop would be just as inefficient, so this actually
		 *  isn't really repetition that could be easily solved in a more succinct manner. Incidentally was also approved by a TA
		 */
		for(int i = 0; i<unique_nucleotide; i++) { /** I attempted to include the /10 statement above, which is used for rounding, but for some reason it wouldn't work if used in the same line as math.round */
			masses[i]/=10;
		}
	}
	/**
	 * @param codons the complete list of codons comprised of 3 nucleic acids each
	 * @param masses the array representing mass percents of each nuclic acid in alphabetical order
	 * @return returns whether or not the codon sequence is one coding for a protein
	 */
	public static Boolean protein_test(String[] codons, double[] masses) {
		Boolean protein = true;
		if (!codons[0].equals("ATG")) {
			protein = false;
		}
		if (!codons[codons.length-1].equals("TAA") && !codons[codons.length-1].equals("TAG") && !codons[codons.length-1].equals("TGA")) {
			protein = false;
		}
		if (codons.length<5) {
			protein = false;
		}
		if (masses[1] + masses[2] < valid_percent) {
			protein = false;
		}
		return protein;
	}

}
