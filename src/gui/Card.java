package gui;
/*
 * Class that creates card objects based on suit, name, & value
 * Adds functionality to get values for counting points in crib
 * @author Alexander Gates
 * @date Nov.06.2019
 */

public class Card implements Comparable<Card>{
	
	private int value;      //value for counting points (ie Queen = 10)
	private int faceValue;  //value of the card (ie Queen = 12)
	private String suit;
	private String name;
	
	public Card(int value, int faceValue, String name){
		this.value = value;
		this.faceValue = faceValue;
		this.name = name;
		
		//randomly assigns suit
		int r = (int) (Math.random() * 4);
		String[] suits = {"Diamonds", "Clubs", "Spades","Hearts"};
		suit = suits[r];
	}
	
	public Card(int value, int faceValue, String name, String suit) {
		this.value = value;
		this.faceValue = faceValue;
		this.name = name;
		this.suit = suit;
	}
	
	//getters
	public int value(){return value;}
	public int faceValue(){return faceValue;}
	public String name(){return name;}
	public String suit(){return suit;}
	
	//setters
	public void setValue(int value) {this.value=value;}
	public void setfaceValue(int faceValue) {this.faceValue=faceValue;}
	public void setName(String name) {this.name=name;}
	public void setSuit(String suit) {this.suit=suit;}
	
	public String toString(){return name + " of " + suit;}


	/*override of equals method, if cards have the same face value they are equal
	 * @param other is comparison card
	 * @return boolean true/false if equal
	 */
	public boolean equals(Card other) {
		if(other.name().equals(this.name()) && other.faceValue == this.faceValue && other.suit.equals(this.suit))
			return true;
		else return false;
	}

	@Override
	public int compareTo(Card c) {
		return this.faceValue- c.faceValue;
	}
}
