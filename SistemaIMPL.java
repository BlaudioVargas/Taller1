package Taller1;

interface SistemaIMPL {

	public boolean load();
	public boolean loadPersonaje();
	public boolean loadCuentas();
	public boolean loadEstadisticas();
	
	public void addUser(String name);
	
	public boolean addNewCharacter();
	public boolean addNewSkin();
	public boolean banUser();
	public Usuarios getUsers();
	
	public boolean save();
	public boolean savePersonaje();
	public boolean saveCuentas();
	public boolean saveEstadisticas();
	
	//** nuevos
	public Personajes getPersonajes();
}
