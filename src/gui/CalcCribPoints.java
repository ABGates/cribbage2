package gui;

/* Class calculates the points of any cribbage hand (barring points from nobs, nibs, or other points involving the cut card)
 * @author Alexander Gates
 * @version 2.0 | November 10, 2020
 * 
 * References 
 *  	�Rules of cribbage,� Wikipedia, 22-Sep-2020. [Online]. 
 *  	 Available: https://en.wikipedia.org/wiki/Rules_of_cribbage. [Accessed: 11-Nov-2020]. 
 *  
 *  [1]	�Print all possible combinations of r elements in a given array of size n,� GeeksforGeeks, 28-Sep-2020. [Online]. 
 *  	 Available: https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/. [Accessed: 11-Nov-2020]. 		
 */

import java.util.Arrays;
import java.util.HashMap;

public class CalcCribPoints {
	
	private static int points;
	private static int points_15; 
        private static HashMap<String,Integer> accessPoints;
	
	public CalcCribPoints(Hand hand) {
		hand.sort();
                //if hand is a cribHand it cannot be a cribTurn (prevents false positive for nibs)
                if (hand.getCribH())
                    hand.setCribT(false);
                
		points=0;
		points_15=0;
                accessPoints = new HashMap<String,Integer>();
		
                //fill accessPoints in the constructor with all the points so that external classes can use those points
		int nobsP = nobs(hand);
                accessPoints.put("nob",nobsP);
		int nibsP = nibs(hand);
                accessPoints.put("nib",nibsP);
		int suitsP = suitPoints(hand);
                accessPoints.put("suit",suitsP);
		int runsP = runPoints(hand);
                accessPoints.put("run",runsP);
		int pairsP = pairsPoints(hand);
                accessPoints.put("pair",pairsP);
                Hand sumHand = new Hand();
		fifteenCombination(hand,sumHand,0,-1);
                
                accessPoints.put("fifteen", points_15);
                points+=points_15;
                accessPoints.put("total",points);
		//printTotal();
	}
	
	/*
	 * Method calculates the points from getting all the same suit 
	 */
	public static int suitPoints(Hand hand) {
		boolean fullSuit = true;
                int suitPoints = 0;
		
		//fullSuit will be false only if cards in hand are not the same suit, ignoring cut card
		for(int i = 0; i<hand.size()-1; i++) {
			if(hand.isCutCard(hand.get(i)) || hand.isCutCard(hand.get(i+1)) || hand.get(i).suit().equals(hand.get(i+1).suit()))
				continue;
			else {
				fullSuit = false;
				break;
			}
		}
		
		
		//if all same suit and not a crib Hand
		if(fullSuit && !hand.getCribH()) {
			//if cut card suit is the same as any other cards suit
			if(hand.getCut().suit().equals(hand.get(0).suit())) {
				//System.out.println("Points from matching suits: " + hand.size());
				suitPoints = hand.size();
                                points+=suitPoints;
			}
			//if cut card suit is not the same as the other suits
			else {
				//System.out.println("Points from matching suits: " + (hand.size()-1));
				suitPoints = hand.size()-1;
                                points+=suitPoints;
			}
		}
		//if all same suit and a crib Hand
		else if(fullSuit && hand.getCribH() && hand.getCut().suit().equals(hand.get(0).suit())) {
			//System.out.println("Points from matching suits: " + hand.size());
			suitPoints = hand.size();
                        points+=suitPoints;
		}
                else{
			//System.out.println("Points from flush: 0");
                }
                return suitPoints;
        }


	/* 
	 * Method calculates points from "runs" defined as 3 or more cards increasing in value by 1 
	 */
	public static int runPoints(Hand hand) {
		
		int runPoints = 0;
		//length of any potential run through each loop
		int length = 1;
		
		//runs with multiple repeats of the same card will have several multipliers
		int[] multipliers = new int[hand.size()];
		Arrays.fill(multipliers, 1);
		int multIndex = 0;
		
		
		//iterate through hand
		for(int i = 0; i<hand.size()-1; i++) {
			//if curr card is same as next card
			if(hand.get(i).compareTo(hand.get(i+1)) == 0) { 
				multipliers[multIndex]++; 
			}
			//if curr card is one less in faceValue than next card, length of the run is one longer, and move the multiplier index for the next card
			else if(hand.get(i).compareTo(hand.get(i+1)) == -1) { 
				length++;  
				multIndex++;
				
			}
			//when the run stops
			else {
				//if there actually is a run before it got cut off
				if(length>=3) {
					//multiply length * each multiplier to get total points for this run
					for(int j : multipliers) { 
						length*=j;
					}
					runPoints+=length;
				}
					
				//reset for next loop
				length=1;
				Arrays.fill(multipliers,1);
				multIndex=0;
			}
		}
		
		//add runs that finish with max value of hand, ie: the else statement in the loop was never reached because the hand ended
		if(length>=3) {
			for(int j : multipliers) { 
				length*=j;
			}
			runPoints+=length;
		}

		points+=runPoints;
		//System.out.println("Points from runs: " + runPoints);
	
                return runPoints;
        }
	
	/*
	 * This method calculates points from pairs in cribbage
	 */
	public static int pairsPoints(Hand hand) {
		int pairsPoints = 0;
		
		for(int i =0; i<hand.size()-1; i++) {
			for(int j = i+1; j<hand.size(); j++) {
				if(hand.get(i).compareTo(hand.get(j)) == 0)
					pairsPoints+=2;
			}
		}
		
		//System.out.println("Points from Pairs: " + pairsPoints);
		points+= pairsPoints;
                return pairsPoints;
	}
	
	/*
	 * This method calculates points from nobs
	 */
	public static int nobs(Hand hand) {
		boolean exists = false;
		
                //if the cut card is a jack
                if(hand.getCut().name().equals("j") || hand.getCut().name().equals("Jack"))
                    return 0;
                
		for(Card c : hand.getHandArray()) {
			if(c.name().equals("j") || c.name().equals("Jack")) {
				if(c.suit().equals(hand.getCut().suit())) {
					//System.out.println("Points from nobs: 1");
					points+=1;
					exists = true;
				}
			}
		}
		
		if(!exists) {
			//System.out.println("Points from nobs: 0");
		}
		
                if(exists)
                    return 1;
                else return 0;
	}
	
	/*
	 * This method calculates points from nibs
	 */
	public static int nibs(Hand hand) {
		 
		 if(hand.getCribT() && hand.getCut().name().equals("j") || hand.getCribT() && hand.getCut().name().equals("Jack")) {
			// System.out.println("Points from nibs: 2");
			 points+=2;
                         return 2;
		 }
		 else {
			// System.out.println("Points from nibs: 0");
                         return 0;
		 }
	}
	

	/*
	 * This method (and its helper method) calculate points from combinations that add to 15. Combinations methodology inspired by [1]
	 */
	public static void fifteenPoints(Hand sumHand,int sumLen){
		int sum = 0;
		for (int i=0;i<=sumLen;i++) 
		   sum+= sumHand.get(i).value();
		if(sum == 15) { 
		   points_15+=2;
		}
	}
	
	/*
	 * This method creates an ArrayList of unique combinations of cards in the hand ArrayList
	 */
	public static void fifteenCombination(Hand hand, Hand sumHand,int handBeg,int sumEnd){
	    for(int i=handBeg;i<hand.size();i++){
	       sumHand.add((sumEnd+1),hand.get(i));
	       fifteenPoints(sumHand,sumEnd+1);
	       if(i<hand.size()-1)
	          fifteenCombination(hand,sumHand,i+1,sumEnd+1);
	      }
	 }
		
	/*
	 * Prints total points and 15 points due to the nature of how 15 points are calculated
	 */
	public static void printTotal() {
		System.out.println("Points from Fifteens: " + points_15);
		System.out.println("Total Points: " + (points + points_15));
	}
        
        public int getFifteen(){
            return points_15;
        }
        
        public HashMap<String,Integer> getPoints(){
            return accessPoints;
        }
}
