package Taller1;

public class Inventario {
	private Personaje character;
	private Apariencias skins;
	private Inventario next;
	
	public Inventario(Personaje character, Apariencias skins) {
		this.character=character;
		this.skins=skins;
		this.next=null;
	}
	
	public void setSkins(Apariencias skins) {
		this.skins=skins;
	}
	
	public void setNext(Inventario next) {
		this.next=next;
	}
	
	public Personaje getCharater() {
		return this.character;
	}
	
	public Apariencias getSkins() {
		return this.skins;
	}
	
	public Inventario getNext() {
		return this.next;
	}
	
}
