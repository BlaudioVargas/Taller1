package Taller1;

import java.io.IOException;


public class Taller1 {

	public static void main(String [] args) throws IOException {
		SistemaIMPL system = new Sistema();
		boolean exito =system.load();
		if (exito) {
			boolean continuar=true;
			while(continuar) {
				continuar = inicioSecion(system);
			}
			system.save();
		}
	}
	
	public static boolean inicioSecion(SistemaIMPL system) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("INICIO DE SECION");
		System.out.println("Indique nombre de usuario");
		System.out.println("Se finaliza con 0:");
		String seleccion = sc.nextLine();
		
		if((seleccion).equals("0")){
			System.out.println("Se a finalizado la operacion");
			return false;
		}
		else if((seleccion).equals("ADMIN")){
			menuAdmin(system);
		}
		else  if(!(Arrays.asList(system.getBanList()).contains(seleccion))){
			Usuario user = getUsuario(system, seleccion);
			if(user!=null) {
				menuUser(system, user);
			}
			else {
				newUser(system, user);
			}
		}
		else {
			System.out.println("ERROR: EL USUARIO: '"+seleccion+"' SE ENCUENTRA BLOQUEADO DEL SISTEMA");
		}
		return true;
	}

	private static void newUser(SistemaIMPL system, Usuario user) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Usuario no existe");
		System.out.println("Desea crar un nuevo Usuario");
		System.out.println("Y/N");
		String clave =sc.nextLine();
		if(((clave).equals("y")||(clave).equals("Y")))
		{
			system.addUser(name);
		}
		else if((clave).equals("n")||(clave).equals("N"))
		{
			/// NO SE HACE NADA
		}
		else
		{
			System.out.println("ERROR COMANDO NO RECONOCIDO");
		}
		System.out.println("Volviendo al principio de la operacion");
	}

	private static void menuUser(SistemaIMPL system, Usuario user) {
		
	}

	private static void menuAdmin(SistemaIMPL system) {
		
	}

	private static Usuario getUsuario(SistemaIMPL system, String seleccion) {
		Usuarios indice = system.getUsers();
		while(indice!=null) {
			if(indice.getUser().getName().equals(seleccion)) {
				return indice.getUser();
			}
			indice=indice.getNext();
		}
		return null;
	}
}
