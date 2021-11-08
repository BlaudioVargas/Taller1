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
		private static void menuUser(SistemaIMPL system, Usuario user) {
		boolean valido = true;
		while(valido) {
			@SuppressWarnings("resource")
			var sc= new Scanner(System.in);
			
			System.out.println("1)Compra de skins");
			System.out.println("2)Compra de personajes");
			System.out.println("3)Mostrar las skins disponibles");
			System.out.println("4)Mostrar el inventario");
			System.out.println("5)recargar RP");
			System.out.println("6)Mostrar datos de la cuenta");
			System.out.println("0)Salir");
			String respuesta = sc.nextLine();
			if(respuesta.equals("1")) {
				buySkin(system,user);
			}
			else if(respuesta.equals("2")) {
				buyCharacter(system,user);		
			}
			else if(respuesta.equals("3")) {
				showSkinNotOwn(system,user);
			}
			else if(respuesta.equals("4")) {
				showInventory(user);
			}
			else if(respuesta.equals("5")) {
				rechargeRP(user);
			}
			else if(respuesta.equals("6")) {
				infoUser(user);
			}
			
			else if(respuesta.equals("0")) {
				valido = false;
				System.out.println("Saliendo de menú usario");
			}
			else {
				System.out.println("ERROR RESPUESTA NO VALIDA");
			}
		}
	}
	
	private static boolean buySkin(SistemaIMPL system, Usuario user) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Indique el nombre del campeón al que le desea comprar su Skin");
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
						user.setLVL(1);
						aux.setBalance(costo);
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
		if(user.getTotal()<150) {
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
					user.setLVL(1);
					user.setTotal(1);
					aux.setBalance(975);
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
		}
		else {
			System.out.println("ERROR TIENE EL LIMITE DE 150 CAMPEONES");
		}
		return false;
	}

	private static boolean showSkinNotOwn(SistemaIMPL system, Usuario user) {
		System.out.println("Skins por campeon no obtenidas");
		Personajes indexCharacter = system.getPersonajes();
		int contador =1;
		while(indexCharacter!=null) {
			Inventario indexPosecion = getInventario(user, indexCharacter.getCharacter().getName());
			System.out.println(contador+") Campeon :"+indexCharacter.getCharacter().getName());
			Apariencias indexSkins = indexCharacter.getCharacter().getSkins();
			int cont =1;
			while(indexSkins!=null) {
				boolean existe =false;
				if(indexPosecion!=null) {
					existe =chekExistenceSkin(indexSkins.getSkins().getName(), indexPosecion.getSkins());
				}
				if(!existe) {
					System.out.println("---"+cont+"> Skin: "+indexSkins.getSkins().getName()+"; Rareza"+indexSkins.getSkins().getRarity()+"; Costo ="+getCosto(indexSkins.getSkins().getRarity()));
				}
				indexSkins=indexSkins.getNext();
				cont++;
			}
			contador++;
			indexCharacter=indexCharacter.getNext();
		}
		return true;
	}

	private static boolean showInventory( Usuario user) {
		System.out.println("Inventario del usuario : "+user.getName());
		Inventario indexPosecion = user.getInventario();
		int contador =1;
		while(indexPosecion!=null) {
			System.out.println(contador+") Campeon :"+indexPosecion.getCharater().getName());
			Apariencias indexSkins = indexPosecion.getSkins();
			int cont =1;
			while(indexSkins!=null) {
				System.out.println("---"+cont+"> Skin: "+indexSkins.getSkins().getName()+"; Rareza"+indexSkins.getSkins().getRarity());
				cont++;
			}
			contador++;
			indexPosecion=indexPosecion.getNext();
		}
		return true;
	}

	private static boolean rechargeRP( Usuario user) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Recargar saldo a la cuenta: "+user.getName()+"/ Balance actual: "+user.getBalance());
		System.out.println("Inique el saldo a ingresar");
		int fondos = sc.nextInt();
		if (fondos>0) {
			user.setBalance(fondos);
			System.out.println("Operacion realizada con exito balance actual: "+user.getBalance());
			return true;
		}
		System.out.println("ERROR INGRESO NO VALIDO");
		return false;
	}

	private static boolean infoUser( Usuario user) {
		String name =user.getName();
		String nickName= user.getAlias();
		String password= user.getPassword();
		int saldo = user.getBalance();
		int lvl = user.getLVL();
		String passwordCensurado=censurarPassword(password);
		System.out.println("Nombre Usuario: "+name);
		System.out.println("Apodo: "+nickName);
		System.out.println("Contraseña: "+passwordCensurado);
		System.out.println("Saldo: "+saldo);
		System.out.println("Nivel: "+lvl);
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Desea cambiar el password S/N");
		String respuesta =sc.nextLine();
		if(respuesta.equals("s")|respuesta.equals("S")){
			System.out.println("Indique el password actual");
			respuesta =sc.nextLine();
			if(respuesta.equals(password)) {
				System.out.println("Indique el nuevo password");
				respuesta =sc.nextLine();
				System.out.println("Indique  nuevamente el nuevo password");
				String respuesta2 =sc.nextLine();
				if(respuesta.equals(respuesta2)) {
					user.setPassword(respuesta);
					return true;
				}
				else{
					System.out.println("ERROR CONFIRMACION DE PASWORD INVALIDO");
				}
			}
			else {
				System.out.println("ERROR PASWORD INVALIDO");
			}
		}
		return false;
	}
				///////////////////////////////////////////////////////////////////////----
	private static void menuAdmin(SistemaIMPL system) {
		boolean valido = true;
		while(valido) {
			@SuppressWarnings("resource")
			var sc= new Scanner(System.in);
			
			System.out.println("1)Recaudación total por cada rol");
			System.out.println("2)Recaudación total por cada región");
			System.out.println("3)Recaudación total por personaje");
			System.out.println("4) Desplegar la cantidad de personajes por cada rol existente");
			System.out.println("5)Agregar un personaje al juego");
			System.out.println("6)Agregar una skin");
			System.out.println("0)Salir");
			String respuesta = sc.nextLine();
			/*if(respuesta.equals("1")) {
				buySkin(system,user);
			}
			else if(respuesta.equals("2")) {
				buyCharacter(system);		
			}
			else if(respuesta.equals("3")) {
				showSkinNotOwn(system);
			}
			else if(respuesta.equals("4")) {
				showInventory(user);
			}*/
			else if(respuesta.equals("5")) {
				addNewCharacter(system,name);
				
			}
			else if(respuesta.equals("6")) {
				addNewSkin(system,name);
			}
			/*else if(respuesta.equals("7")) {
				banUser(system);
			}
			else if(respuesta.equals("8")) {
				---(user);
			}*/
			else if(respuesta.equals("0")) {
				valido = false;
				System.out.println("Saliendo de menu admin");
			}
			else {
				System.out.println("ERROR RESPUESTA NO VALIDA");
			}
		}
	}
		
		///////////////////////////////////////////////////////////////////////----
		
	private static boolean addNewCharacter(SistemaIMPL system,String name) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Nombre de personaje a agregar: "+name);
		boolean continuar=true;
		String clase ="SUP";
		while(continuar) {
			String clasess="SUP/ADC/TOP/MID/JG";
			String[] clases = clasess.split("/");
			System.out.println("Indique su rol : SUP / ADC / TOP / MID / JG");
			clase =sc.nextLine();
			if(Arrays.asList(clases).contains(clase)) {
				continuar = false;
			}
			else {
				System.out.println("ERROR ROL NO RECONOCIDO");
			}
		}
		Personaje addNewCharacter= new Personaje(name, clase, 0, skinstemp);
		//Personaje addNewCharacter= new Personajes(name, clase, skinstemp);
		Personajes characterstemp = new Personajes(addNewCharacter);
		characterstemp.setNext(characters);
		this.characters=characterstemp;

	}
		
	private static boolean addNewSkin( SistemaIMPL system,String name,String nameskin ) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Nombre del personaje al que se le desea agregar la skin: "+ name);
		System.out.println("Nombre del la skin: "+ nameskin);
		boolean continuar=true;
		String rarityskin ="M";
		while(continuar) {
			String rarityskins="M/D/L/E/N";
			String[] clases = rarityskins.split("/");
			System.out.println("Indique la calidad de skin : M / D / L / E / N");
			rarityskin =sc.nextLine();
			if(Arrays.asList(clases).contains(rarityskin)) {
				continuar = false;
			}
			else {
				System.out.println("ERROR CALIDAD NO RECONOCIDA");
			}
			
		}
		Apariencia addNewSkin = new Apariencia(nameskin, rarityskin);
		Apariencias skinstemp = new Apariencias(addNewSkin);
		skinstemp.setNext();
		this.=skinstemp;
	}

		///////////////////////////////////////////////////////////////////////----
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
	
	private static String censurarPassword(String password) {
		String passwordCensurado="";
		String[] partes = password.split("");
		int tamano = partes.length;
		if (tamano>3) {
			int censurar = tamano-3;
			for(int j = 0;j<censurar;j++) {
				passwordCensurado=passwordCensurado+"*";
			}
		}
		passwordCensurado=passwordCensurado+partes[tamano-3]+partes[tamano-2]+partes[tamano-1];
		return passwordCensurado;
	}
}
