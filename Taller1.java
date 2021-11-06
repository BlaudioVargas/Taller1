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
				System.out.println("Indique la clave de Usario:");
				String clave = sc.nextLine();
				if(user.getPassword().equals(clave)) {
					menuUser(system, user);
				}
				else {
					System.out.println("ERROR CLAVE INVALIDA");
				}
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
	
	private static boolean buySkin(SistemaIMPL system, Usuario user) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Indique el nombre del campeon al que le desea comprar su Skin");
		String campeon =sc.nextLine();
		Personaje aux = system.searchCharacter(campeon);
		Inventario temp = getInventario(user, campeon);
		if(temp!=null) {
			Apariencias indice = aux.getSkins();
			System.out.println("Campeon :"+aux.getName());
			int contador =1;
			while(indice!=null) {
				int costo = getCosto(indice.getSkins().getRarity());
				boolean existe = chekExistenceSkin(indice.getSkins().getName(),temp.getSkins());
				if(costo>0 && !existe) {
					System.out.println("Skin "+contador+": "+indice.getSkins().getName()+"- Valor :"+costo);
					contador++;
				}
				indice=indice.getNext();
			}
			System.out.println("Indique el nombre de la Skin que desea comprar");
			String buySkin =sc.nextLine();
			boolean existe = chekExistenceSkin(buySkin,temp.getSkins());
			if(!existe) {
				Apariencia tempskin=system.searchSkin(aux, buySkin);
				if(tempskin!=null) {
					int costo = getCosto(tempskin.getRarity());
					if(costo>=user.getBalance()) {
						Apariencias newSkin = new Apariencias (tempskin);
						newSkin.setNext(temp.getSkins());
						temp.setSkins(newSkin);
						user.setBalance(-costo);
						System.out.println("Se compro con exito la Skin: "+tempskin.getName()+" ("+tempskin.getRarity()+") del personaje "+aux.getName()+" por un valor de :"+costo+" RPs");
					}
					else {
						System.out.println("ERROR EL SALDO DE SU CUENTA ES INSUFICIENTE POR :"+(costo-user.getBalance())+"RPs");
					}
				}
				else {
					System.out.println("ERROR SKIN LA SKIN DESEADA NO EXISTE");
				}
			}
			else {
				System.out.println("ERROR YA POSEE ESA SKIN");
			}
		}
		else {
			if(aux!=null) {
				System.out.println("ERROR NO TIENE POSECION DEL CAMPEON");
			}
			else {
				System.out.println("ERROR NO EXISTE EL CAMPEON");
			}
		}
		return false;
	}

	

	private static boolean buyCharacter(SistemaIMPL system, Usuario user) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		Personajes indice = system.getPersonajes();
		System.out.println("Listado de campeones:");
		int contador =1;
		while(indice!=null) {
			System.out.println(contador+") "+indice.getCharacter().getName()+" Costo: 975 RPs");
			contador++;
			indice=indice.getNext();
		}
		
		System.out.println("Indique el nombre del campeon que desea comprar");
		String campeon =sc.nextLine();
		Personaje aux = system.searchCharacter(campeon);
		Inventario temp = getInventario(user, campeon);
		if(temp==null && aux!=null) {
			if(user.getBalance()>=975) {
				Inventario newCharacter = new Inventario(aux,null);
				newCharacter.setNext(temp);
				user.setInventario(newCharacter);
				user.setBalance(-975);
				System.out.println("Se compro con exito al personaje: "+aux.getName()+" por un valor de : 975 RPs");
				return true;
			}
			else {
				System.out.println("ERROR EL SALDO DE SU CUENTA ES INSUFICIENTE POR :"+(975-user.getBalance())+"RPs");
			}
		}
		else {
			if(aux!=null) {
				System.out.println("ERROR YA TIENE POSECION DEL CAMPEON");
			}
			else {
				System.out.println("ERROR NO EXISTE EL CAMPEON");
			}
		}
		return false;
	}

	private static boolean showSkinNotOwn(SistemaIMPL system, Usuario user) {
		return false;
	}

	private static boolean showInventory(SistemaIMPL system, Usuario user) {
		return false;
	}

	private static boolean rechargeRP(SistemaIMPL system, Usuario user) {
		return false;
	}

	private static boolean infoUser(SistemaIMPL system, Usuario user) {
		return false;
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
	
	private static Inventario getInventario(Usuario user, String campeon) {
		Inventario indice = user.getInventario();
		while(indice!=null) {
			if(indice.getCharater().getName().equals(campeon)) {
				return indice;
			}
			indice=indice.getNext();
		}
		return null;
	}
	
	private static int getCosto(String rarity) {
		if(rarity.equals("N")) {
			return 975;
		}
		else if(rarity.equals("E")) {
			return 1350;
		}
		else if(rarity.equals("L")) {
			return 1820;
		}
		else if(rarity.equals("D")) {
			return 2750;
		}
		else if(rarity.equals("M")) {
			return 3250;
		}
		return 0;
	}
	
	private static boolean chekExistenceSkin(String name, Apariencias skins) {
		while(skins!=null) {
			if(skins.getSkins().getName().equals(name)) {
				return true;
			}
			skins=skins.getNext();
		}
		return false;
	}
}
