package Taller1;


public class Sistema implements SistemaIMPL{
	public Usuarios user;
	public Personajes characters;
	public String[] banList;

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
				for(int i=2;i<tamano;i+=2) {
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
				int totalPersonajes =Integer.parseInt(partes[5]);
				int tamano = partes.length;
				int i;
				Inventario newItem = null;
				Inventario indiceItem = null;
				for(i=5;i<(tamano-1);i++) {
					String namecharacter =partes[i];
					Personaje tempCharacter=searchCharacter(namecharacter);
					i++;
					int totalskin =Integer.parseInt(partes[i]);
					Apariencias skinstemp=null;
					if(tempCharacter!=null) {
						Apariencias skinsindice=null;
						for(int j =0;j<totalskin;j++) {
							i++;
							String nameskin =partes[i];
							Apariencia tempskin=searchSkin(tempCharacter, nameskin);
							if(tempskin==null) {
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
				
				Usuario newUser= new Usuario(name, password, nickname, lvl, saldo, newItem ,region);
				if(characters==null) {
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
			System.out.println("ARCHIVO DE PERSONAJES CORRUPTO/INEXISTENTE");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addNewCharacter() {
		// TODO Auto-generated method stub
		return false;
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
		return null;
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean savePersonaje() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveCuentas() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveEstadisticas() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Personajes getPersonajes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String[] getBanList() {
		// TODO Auto-generated method stub
		return null;
	}

}
