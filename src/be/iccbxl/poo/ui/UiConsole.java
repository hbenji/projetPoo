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
		
		//var temp;
		int cpt = 0;
		int pos = 0;
		
		do {
			//Afficher un menu
				System.out.println("1 - Créer un membre et le sauver dans un fichier xml et csv\n" 
				   +"2 - Créer un livre et le sauver dans un fichier xml et csv\n"
				   +"3 - Emprunter un livre\n"
				   +"4 - Charger un fichier de membres (XML) (inutile vu qu'il y a un chargement des membres avant une action)\n"
				   +"5 - Modifier un membre\n"
				   +"6 - Supprimer un membre\n"
				   +"7 - Charger un fichier de livres (XML) (inutile vu qu'il y a un chargement des livres avant une action)\n"
				   +"8 - Modifier un livre\n"
				   +"9 - Supprimer un livre\n"
				   +"10 - Afficher les livres\n"
				   +"11 - Affichier les membres\n"
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
					
					cpt = this.books.size();
					
					//Sélectionner un livre
										
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
					//Charger un fichier de membres (XML)
					metier.getMembres();	//xml
					message = "Chargement des membres du fichier OK.";
					break;
				case 5 : 
					//Modifier un membre + sauvegarde xml/csv
					
					//Afficher les membres
					//Récupérer la liste des membres
					membres = metier.getMembres();
					this.printMembers();				
					
					cpt = this.membres.size();
					
					//Sélectionner un membre
					do {
						System.out.print("Veuillez choisir le membre à modifier:");
						pos = s.nextInt();
					} while (pos<0 || pos>cpt);
					
					Person personUpdate = this.membres.get(pos-1);	
					//proposer à l'utilisateur les champs à modifier ...
					personUpdate.setName("Testupdatenom");
					
					metier.update(personUpdate);
					message = "Modification de l'utilisateur OK.";
					break;
				case 6:
					//Suppression d'un membre
					//Afficher les membres
					//Récupérer la liste des membres
					membres = metier.getMembres();
					this.printMembers();				
					
					cpt = this.membres.size();
					
					//Sélectionner un membre
					do {
						System.out.print("Veuillez choisir le membre à modifier:");
						pos = s.nextInt();
					} while (pos<0 || pos>cpt);
					
					Person personDelete = this.membres.get(pos-1);	
										
					metier.unregister(personDelete);
					
					message = "Suppression du membre OK.";
					break;
				case 7 : 
					//Charger un fichier de livres (XML)
					metier.getBooks();
					message = "Chargement des livres du fichier OK.";
					break;
				case 8 : 
					//Modifier un livre + sauvegarde xml/csv
					
					//Récupérer la liste des livres
					books = metier.getBooks();
					//Afficher les livres
					this.printBooks();
					
					cpt = this.books.size();
					
					//Sélectionner un livre à modifier
										
					do {
						System.out.print("Veuillez choisir le livre à modifier:");
						pos = s.nextInt();
					} while (pos<0 || pos>cpt);
					
					Book bookUpdate = this.books.get(pos-1);
					
					//Proposer à l'utilisateur de modifier les champs du livre
					bookUpdate.setAuthor("testUpdateAuthor");
					
					metier.update(bookUpdate);
					
					message = "Modification du livre OK.";
					break;
				case 9 : 
					//Suppression d'un livre + sauvegarde xml/csv
					
					//Récupérer la liste des livres
					books = metier.getBooks();
					//Afficher les livres
					this.printBooks();
					
					cpt = this.books.size();
					
					//Sélectionner un livre à supprimer
										
					do {
						System.out.print("Veuillez choisir le livre à supprimer:");
						pos = s.nextInt();
					} while (pos<0 || pos>cpt);
					
					Book bookDelete = this.books.get(pos-1);
										
					metier.unregister(bookDelete);
					
					message = "Suppression du livre OK.";
					break;
				case 10:
					//afficher les livres
					this.books = metier.getBooks();
					this.printBooks();
					break;
				case 11:
					//afficher les membres
					this.membres = metier.getMembres();
					this.printMembers();
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
