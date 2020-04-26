package be.iccbxl.poo;

import be.iccbxl.poo.ui.IUi;
import be.iccbxl.poo.ui.UiConsole;

public class App {
	
	private static IUi ui;
	
    public App() {
		ui = new UiConsole();
	}

	public static void main( String[] args )
    {
        //Avant lancement du programme
		System.out.println( "Lancement de l'application." );
        
		new App();
        
        //Lancement du programme
        ui.run();
        
        //Après la fin du programme
        System.out.println("Fin du programme.");
    }
}
