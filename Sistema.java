package Taller1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Sistema implements SistemaIMPL{
	
	public Usuarios user;
	public Personajes characters;
	public String[] banList;
	
	public Sistema() {
		user = null;
		characters = null;
		banList = new String[999];
	}

	@Override
	public boolean load() {
		boolean valido = loadPersonaje();
		if (valido) {
			valido=loadCuentas();
			if (valido) {
				valido=loadEstadisticas();
				if (valido) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean loadPersonaje() {
		try {
			File archivo = new File ("src/Taller1/Personajes.txt"); 
			FileReader text = new FileReader (archivo); 
			BufferedReader reader = new BufferedReader(text); 
			Personajes temp=characters;
			String linea;
			String[] partes;
			while((linea = reader.readLine())!=null){
				partes = linea.split(",");
				String name =partes[0];
				String clase =partes[1];
				//----------------------------
				int tamano = partes.length;
				Apariencias skinstemp=null;
				Apariencias skinsindice=null;
				for(int i=3;i<tamano;i+=2) {
					String nameskin =partes[i];
					String rarityskin =partes[i+1];
					Apariencia newskin = new Apariencia(nameskin, rarityskin);
					if(skinstemp==null) {
						skinstemp = new Apariencias(newskin);
						skinsindice=skinstemp;
					}
					else {
						skinsindice.setNext(new Apariencias(newskin));
						skinsindice=skinsindice.getNext();
					}
				}
				Personaje newcaharcter= new Personaje(name, clase, 0, skinstemp);
				if(characters==null) {
					characters = new Personajes(newcaharcter);
					temp=characters;
				}
				else {
					temp.setNext(new Personajes(newcaharcter));
					temp = temp.getNext();
				}
				//---------------------------
			 }
			 reader.close();
			 return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO DE PERSONAJES CORRUPTO/INEXISTENTE");
			return false;
		}
	}

	@Override
	public boolean loadCuentas() {
		try {
			File archivo = new File ("src/Taller1/Cuentas.txt"); 
			FileReader text = new FileReader (archivo); 
			BufferedReader reader = new BufferedReader(text); 
			Usuarios temp=user;
			String linea;
			String[] partes;
			while((linea = reader.readLine())!=null){
				partes = linea.split(",");
				String name =partes[0];
				String password =partes[1];
				String nickname =partes[2];
				int lvl =Integer.parseInt(partes[3]);
				int saldo =Integer.parseInt(partes[4]);
				//----------------------------
				int tamano = partes.length;
				int i;
				Inventario newItem = null;
				Inventario indiceItem = null;
				int totalCharacter=0;
				for(i=6;i<(tamano-1);i++) {
					String namecharacter =partes[i];
					Personaje tempCharacter=searchCharacter(namecharacter);
					i++;
					int totalskin =Integer.parseInt(partes[i]);
					Apariencias skinstemp=null;
					Apariencias skinsindice=null;
					if(tempCharacter!=null) {
						for(int j =0;j<totalskin;j++) {
							i++;
							String nameskin =partes[i];
							Apariencia tempskin=searchSkin(tempCharacter, nameskin);
							if(tempskin!=null) {
								if(skinstemp==null) {
									skinstemp = new Apariencias(tempskin);
									skinsindice=skinstemp;
								}
								else {
									skinsindice.setNext(new Apariencias(tempskin));
									skinsindice=skinsindice.getNext();
								}
							}
						}
						totalCharacter++;
					}
					else {
						i+=totalskin;
					}
					if(newItem==null) {
						newItem = new Inventario(tempCharacter, skinstemp);
						indiceItem=newItem;
					}
					else {
						indiceItem.setNext(new Inventario(tempCharacter, skinstemp));
						indiceItem = indiceItem.getNext();
					}
				}
				String region =partes[i];
				//---------------------------
				
				Usuario newUser= new Usuario(name, password, nickname, lvl, saldo, totalCharacter, newItem ,region);
				if(user==null) {
					user = new Usuarios(newUser);
					temp=user;
				}
				else {
					temp.setNext(new Usuarios(newUser));
					temp = temp.getNext();
				}
			 }
			 reader.close();
			 return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO DE CLIENTES CORRUPTO/INEXISTENTE");
			return false;
		}
	}

	@Override
	public boolean loadEstadisticas() {
		try {
			File archivo = new File ("src/Taller1/Estadísticas.txt"); 
			FileReader text = new FileReader (archivo); 
			BufferedReader reader = new BufferedReader(text); 
			String linea;
			String[] partes;
			while((linea = reader.readLine())!=null){
				partes = linea.split(",");
				String name =partes[0];
				int saldo =Integer.parseInt(partes[1]);
				Personaje tempCharacter=searchCharacter(name);
				if(tempCharacter!=null) {
					tempCharacter.setBalance(saldo);
				}
			 }
			 reader.close();
			 return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO DE ESTADISTICAS CORRUPTO/INEXISTENTE");
			return false;
		}
	}
//////////////////////////////////////////////////////////////////--------
	public Apariencia searchSkin(Personaje tempCharacter, String nameskin) {
		// TODO Auto-generated method stub
		Apariencias temp = tempCharacter.getSkins();
		while(temp!=null) {
			if(temp.getSkins().getName().equals(nameskin)) {
				return temp.getSkins();
			}
			temp=temp.getNext();
		}
		return null;
	}

	public Personaje searchCharacter(String namecharacter) {
		// TODO Auto-generated method stub
		Personajes temp=characters;
		while(temp!=null) {
			if(temp.getCharacter().getName().equals(namecharacter)) {
				return temp.getCharacter();
			}
			temp=temp.getNext();
		}
		return null;
	}
//////////////////////////////////////////////////////////////////--------	
	@Override
	public void addUser(String name) {
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("Usuario a crear: "+name);
		System.out.println("Indique la contraseña");
		String clave =sc.nextLine();
		System.out.println("Indique su apodo");
		String nickmane =sc.nextLine();
		boolean continuar=true;
		String region ="LAS";
		while(continuar) {
			String regionn="LAS/LAN/EUW/KR/NA/RU";
			String[] regiones = regionn.split("/");
			System.out.println("Indique su region : LAS / LAN / EUW / KR / NA / RU");
			region =sc.nextLine();
			if(Arrays.asList(regiones).contains(region)) {
				continuar = false;
			}
			else {
				System.out.println("ERROR REGION NO RECONOCIDA");
			}
		}
		
		Usuario newUser= new Usuario(name, clave, nickmane, 0, 0, 0, null ,region);
		Usuarios usertemp = new Usuarios(newUser);
		usertemp.setNext(user);
		this.user=usertemp;
	}

	@Override
	public boolean addNewCharacter() {
		try {
			@SuppressWarnings("resource")
			var sc= new Scanner(System.in);
			System.out.println("Indique el nombre del nuevo personaje a agregar: ");
			String name = sc.nextLine();
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
			System.out.println("Indique el nombre de la Skin del nuevo personaje a agregar: ");
			String Skin = sc.nextLine();
			continuar=true;
			String rareza ="N";
			while(continuar) {
				String clasess="N/E/L/D/M";
				String[] clases = clasess.split("/");
				System.out.println("Indique su rol : N / E / L / D / M");
				clase =sc.nextLine();
				if(Arrays.asList(clases).contains(clase)) {
					continuar = false;
				}
				else {
					System.out.println("ERROR RAREZA NO RECONOCIDO");
				}
			}
			Apariencia firstSkin = new Apariencia(Skin, rareza);
			Apariencias item = new Apariencias(firstSkin);
			Personaje addNewCharacter= new Personaje(name, clase, 0, item);
			Personajes characterstemp = new Personajes(addNewCharacter);
			characterstemp.setNext(characters);
			this.characters=characterstemp;
			return true;
		}
		catch (Exception e) {
			System.out.println("PERSOAJE NO VALIDO");
			return false;
		}
	}

	@Override
	public boolean addNewSkin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean banUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Usuarios getUsers() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public boolean save() {
		savePersonaje();
		saveEstadisticas();
		saveCuentas();
		return true;
	}

	@Override
	public boolean savePersonaje() {
		try {
			File archivo = new File ("src/Taller1/Personajes.txt"); 
			FileWriter text = new FileWriter (archivo); 
			BufferedWriter wr = new BufferedWriter(text);
			Personajes indiceCharacter = characters;
			while(indiceCharacter!=null) {
				String name =indiceCharacter.getCharacter().getName();
				String rol =indiceCharacter.getCharacter().getRol();
				int total =0;
				String skins="";
				Apariencias indiceSkins=indiceCharacter.getCharacter().getSkins();
				while(indiceSkins!=null){
					String nameSkin=indiceSkins.getSkins().getName();
					String rarezaSkin=indiceSkins.getSkins().getRarity();
					skins=skins+","+nameSkin+","+rarezaSkin;
					total++;
					indiceSkins=indiceSkins.getNext();
				}
				wr.write(name+","+rol+","+total+skins+"\n");
				indiceCharacter=indiceCharacter.getNext();
			}
			wr.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO NO GUARDADO");
			return false;
		}
	}

	@Override
	public boolean saveCuentas() {
		try {
			File archivo = new File ("src/Taller1/Cuentas2.txt"); 
			FileWriter text = new FileWriter (archivo); 
			BufferedWriter wr = new BufferedWriter(text);
			Usuarios indiceUsers = user;
			while(indiceUsers!=null) {
				String name =indiceUsers.getUser().getName();
				String password =indiceUsers.getUser().getPassword();
				String nick =indiceUsers.getUser().getAlias();
				int LVL =indiceUsers.getUser().getLVL();
				int RPs =indiceUsers.getUser().getBalance();
				int total =indiceUsers.getUser().getTotal();
				String listCharcater="";
				String region = indiceUsers.getUser().getRegion();
				Inventario indiceItem = indiceUsers.getUser().getInventario();
				while(indiceItem!=null){
					String namecharacter=indiceItem.getCharater().getName();
					listCharcater=listCharcater+","+namecharacter;
					Apariencias indiceSkins=indiceItem.getSkins();
					int contador =0;
					String listaSkins="";
					while(indiceSkins!=null){
						String nameSkin=indiceSkins.getSkins().getName();
						listaSkins=listaSkins+","+nameSkin;
						indiceSkins=indiceSkins.getNext();
						contador++;
					}
					listCharcater=listCharcater+","+contador+listaSkins;
					indiceItem=indiceItem.getNext();
				}
				wr.write(name+","+password+","+nick+","+LVL+","+RPs+","+total+listCharcater+","+region+"\n");
				indiceUsers=indiceUsers.getNext();
			}
			wr.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO NO GUARDADO");
			return false;
		}
	}

	@Override
	public boolean saveEstadisticas() {
		try {
			File archivo = new File ("src/Taller1/Estadísticas.txt"); 
			FileWriter text = new FileWriter (archivo); 
			BufferedWriter wr = new BufferedWriter(text);
			Personajes indiceCharacter = characters;
			while(indiceCharacter!=null) {
				String name =indiceCharacter.getCharacter().getName();
				int balance =indiceCharacter.getCharacter().getBalance();
				wr.write(name+","+balance+"\n");
				indiceCharacter=indiceCharacter.getNext();
			}
			wr.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO NO GUARDADO");
			return false;
		}
	}

	@Override
	public Personajes getPersonajes() {
		return characters;
	}

	@Override
	public String[] getBanList() {
		return banList;
	}


}
