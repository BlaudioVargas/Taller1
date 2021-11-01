package Taller1;

public class Personajes {
	private Personaje character;
	private Personajes next;
	
	public Personajes(Personaje character) {
		this.character=character;
		this.next=null;
	}
	
	public void setNext(Personajes next) {
		this.next=next;
	}
	
	public Personaje getCharacter() {
		return this.character;
	}
	
	public Personajes getNext() {
		return this.next;
	}
	
}
