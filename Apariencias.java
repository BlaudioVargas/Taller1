package Taller1;

public class Apariencias {
	private Apariencia skins;
	private Apariencias next;
	
	public Apariencias(Apariencia skins) {
		this.skins=skins;
		this.next=null;
	}
	
	public void setNext(Apariencias next) {
		this.next=next;
	}
	
	public Apariencia getCharacter() {
		return this.skins;
	}
	
	public Apariencias getNext() {
		return this.next;
	}
}
