package Taller1;

public class Personaje {
	private String name;
	private String rol;
	private int balance;
	private Apariencias skins;
	
	public Personaje(String name, String rol, int balance, Apariencias skins) {
		this.name=name;
		this.rol=rol;
		this.balance=balance;
		this.skins=skins;
	}
	
	public boolean setBalance(int balance) {
		this.balance+=balance;
		return true;
	}
	
	public boolean addSkins(Apariencia skin) {
		Apariencias temporal = skins;
		Apariencias auxiliar = new Apariencias(skin);
		boolean primero = true;
		while(temporal!=null) {
			if(temporal.getNext()==null) {
				primero=false;
				temporal.setNext(auxiliar);
				return true;
			}
			else {
				temporal= temporal.getNext();
			}
		}
		if(primero) {
			this.skins = auxiliar;
			return true;
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getRol() {
		return this.rol;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public Apariencias getSkins() {
		return this.skins;
	}
	
	
}
