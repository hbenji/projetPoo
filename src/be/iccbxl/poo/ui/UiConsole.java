package be.iccbxl.poo.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import be.iccbxl.poo.entities.Book;
import be.iccbxl.poo.entities.Person;
import be.iccbxl.poo.metier.IMetier;
import be.iccbxl.poo.metier.Metier;

public class UiConsole implements IUi {

	private IMetier metier;
	
	private Scanner s;

	private List<Person> membres;
	private List<Book> books;
	
	private String message;

	public UiConsole() {
		s = new Scanner(System.in);
		metier = new Metier();
		membres = new ArrayList<Person>();
		books = new ArrayList<Book>();
		message = "";
	}

	public void run() {
		int cmd = 0;
		
		do {
			//Afficher un menu
				System.out.println("1 - Créer un membre\n" 
				   +"2 - Créer un livre\n"
				   +"3 - Emprunter un livre\n"
				   +"4 - Charger un fichier de membres (CSV)\n"
				   +"5 - Charger un fichier de livres (CSV)\n"
				   +"6 - Sauvegarder le fichier des livres en (CSV)\n"
				   +"7 - Sauvegarder le fichier des membres en CSV\n"
				   +"8 - Charger un fichier de membres (XML)\n"
				   +"9 - Sauvegarder le fichier des membres en XML\n"
				   +"10 - Charger un fichier de livres (XML)\n"
				   +"11 - Sauvegarder le fichier des livres en XML\n"
				   +"12 - Afficher les membres\n"
				   +"13 - Affichier les livres\n"
				   +"0 - Quitter");
			
			//Lire le choix de l'utilisateur
			cmd = s.nextInt();
			s.nextLine();
			
			//Traiter la commande de l'utilisateur						
			switch(cmd) {
				case 1 :
					//inscription membre
					System.out.print("Veuillez entrer le nom:");
					String nom = s.nextLine();
					UUID id = UUID.randomUUID();
										
					metier.register(new Person(id, nom));					
					message = "Inscription terminée.";
						
					break;
				case 2 : 
					//ajout livre
					System.out.print("Veuillez entrer le titre:");
					String titre = s.nextLine();
					
					System.out.print("Veuillez entrer l'auteur:");
					String auteur = s.nextLine();
					
					short nbPages;
					
					do {
						System.out.print("Veuillez entrer le nombre de pages:");
					
						try {
							nbPages = s.nextShort();
							s.nextLine();
							
							if(nbPages<=0) {
								System.out.println("Valeur incorrecte!");
							}
						} catch (InputMismatchException e) {
							s.nextLine();
							nbPages = 0;
							System.out.println("Valeur incorrecte!");
						}
					} while(nbPages<=0);
					
					System.out.print("Veuillez entrer la langue:");
					String langue = s.nextLine();
					
					UUID idBook = UUID.randomUUID();
					Book book = new Book(idBook,titre, auteur, nbPages, langue);
					
					metier.register(book);
					
					message = "Ajout livre OK.";

					break;
				case 3 : 
					//Emprunt livre
					
					//Récupérer la liste des livres
					books = metier.getBooks();
					//Afficher les livres
					this.printBooks();
					
					int cpt = this.books.size();
					
					//Sélectionner un livre
					int pos;
					
					do {
						System.out.print("Veuillez choisir le livre à emprunter:");
						pos = s.nextInt();
					} while (pos<0 || pos>cpt);
					
					Book selectedBook = this.books.get(pos-1);

					System.out.println("Emprunt du livre : " + selectedBook.getTitle());					
					
					//Afficher les membres
					//Récupérer la liste des membres
					membres = metier.getMembres();
					this.printMembers();				
					
					cpt = this.membres.size();
					
					//Sélectionner un membre
					do {
						System.out.print("Veuillez choisir le membre qui emprunte:");
						pos = s.nextInt();
					} while (pos<0 || pos>cpt);
										
					Person borrower = this.membres.get(pos-1);
					
					message = "Emprunté par : " + borrower.getName();
					
					borrower.borrows(selectedBook);
					
					break;
				case 4 :
					//Charger un fichier de membres
					
					metier.getMembres();	//csv
					
					message = "Chargement des membres du fichier ... OK.";
					break;
				case 5 : 
					//Charger un fichier de livres
					
					metier.getBooks();	//csv
					message = "Chargement des livres du fichier ... OK.";
					break;
				case 6 : 
					//sauvegarder les livres (un livre)
					
					Book n = null;
					metier.register(n);	//csv
					message = "Sauvegarde des livres dans le fichier ... OK.";
					break;
				case 7 : 
					//sauvegarder les membres (un membre)
										
					Person p = null;
					metier.register(p);	//csv
					message = "Sauvegarde des membres dans le fichier ... OK.";
					break;
				case 8 :
					//Charger un fichier de membres (XML)
					metier.getMembres();	//xml
					message = "Chargement des membres du fichier ... OK.";
					break;
				case 9 : 
					//Sauvegarde les membres dans un fichier xml (XML)\n
					Person p2 = null;
					metier.register(p2);	//xml
					message = "Sauvegarde des membres dans le fichier ... OK.";
					break;
				case 10 : 
					//Charger un fichier de livres (XML)\n
					metier.getBooks();
					message = "Chargement des livres du fichier ... OK.";
					break;
				case 11 : 
					//Sauvegarde les livres dans un fichier xml (XML)
					Book n2 = null;
					metier.register(n2);	//xml
					message = "Sauvegarde des livres dans le fichier ... OK.";
					break;
				case 12 : 
					//afficher les membres
					metier.getMembres();
					this.printMembers();
					break;
				case 13:
					//afficher les livres
					metier.getBooks();
					this.printBooks();
					break;
				default:
					message = "Erreur !!!";
					break;
			}
			
			System.out.println(message);
			
		} while(cmd!=0);
		
	}

	private void printMembers() {
		Iterator<Person> it = membres.iterator();
		int i = 1;
		while(it.hasNext()) {
			Person p = it.next();			
			System.out.println(i+") "+"Nom: "+p.getName() + "\tInscrit le: " + p.getRegistrationDate());
		}
	}
	
	private void printBooks() {
		Iterator<Book> it = books.iterator();
		int i = 1;
		while(it.hasNext()) {
			Book b = it.next();			
			System.out.println(i+") "+b.toString());
		}
	}
}
