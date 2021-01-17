package gui;

import java.util.ArrayList;
import java.util.Collections;


public class Hand {

	private boolean cribT; //cribTurn
	private boolean cribH; //cribHand
	private Card cut;
	private ArrayList<Card> hand;
	
	public Hand() {
		this.hand = new ArrayList<Card>();
		cribT = false;
		cribH = false;
		cut = null;
	}
	
	public Hand(boolean cribT, Card cut, boolean cribH) {
		this.hand = new ArrayList<Card>();
		this.cribT = cribT;
		this.cribH = cribH;
		this.cut = cut;
	}

	public int size() {return hand.size();}

	public Card get(int i) {return hand.get(i);}

	public void add(int i, Card c) {hand.add(i,c);}
	
	public void add(Card c) {hand.add(c);}
	
	public boolean getCribT() {return cribT;}
	public void setCribT(boolean cribT) {this.cribT=cribT;}
	
	public boolean getCribH() {return cribH;}
	public void setCribH(boolean cribH) {this.cribH=cribH;}
	
	public Card getCut() {return cut;}
	public void setCut(Card c) {this.cut = c;}
	
	public ArrayList<Card> getHandArray(){return hand;}

	public void sort() {Collections.sort(hand);}
        
        public void clear() {hand.clear();}
	
	public String toString() {
		String ret = "[";
		for(Card c: hand) {
			if(isCutCard(c))
				ret+="cc:";
			ret += c.toString();
			ret += ", ";
		}
		ret+="]";
		return ret;
	}

	public boolean isCutCard(Card cut) {
		if(cut.equals(this.cut))
			return true;
		else
			return false;
	}

	
	
	
	
	
	
	

}
