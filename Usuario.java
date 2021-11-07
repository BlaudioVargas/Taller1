package Taller1;

public class Usuario {
	private String name;
	private String password;
	private String alias;
	private int lvl;
	private int balance;
	private Inventario items;
	private String region;//////////////
	private int total;//////////////////
	
	public Usuario(String name, String password, String alias, int lvl, int balance, int total, Inventario items, String region) {
		this.name=name;
		this.password=password;
		this.alias=alias;
		this.lvl=lvl;
		this.balance=balance;
		this.items=items;
		this.region=region;
		this.total=total;////
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public void setLVL(int lvl) {
		this.lvl+=lvl;
	}
	
	public void setTotal(int total) {/////////
		this.total+=total;
	}
	
	public void setBalance(int balance) {
		this.balance+=balance;
	}
	
	public void setInventario(Inventario items) {
		Inventario temporal = this.items;
		boolean primero = true;
		while(temporal!=null) {
			if(temporal.getNext()==null) {
				primero=false;
				temporal.setNext(items);
				break;
			}
			else {
				temporal= temporal.getNext();
			}
		}
		if(primero) {
			this.items = items;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getAlias() {
		return this.alias;
	}
	
	public int getLVL() {
		return this.lvl;
	}
	
	public int getTotal() {//////
		return this.total;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public Inventario getInventario() {
		return this.items;
	}
	
	public String getRegion() {///////////
		return this.region;
	}
}
