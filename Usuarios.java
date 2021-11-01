package Taller1;

public class Usuarios {
	private Usuario user;
	private Usuarios next;
	
	public Usuarios(Usuario user) {
		this.user=user;
		this.next=null;
	}
	
	public void setNext(Usuarios next) {
		this.next=next;
	}
	
	public Usuario getUser() {
		return this.user;
	}
	
	public Usuarios getNext() {
		return this.next;
	}
	
}
