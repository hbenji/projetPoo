package be.iccbxl.poo;

import be.iccbxl.poo.ui.IUi;
import be.iccbxl.poo.ui.UiConsole;

public class Main {
	
	private static IUi ui;
	
    public Main() {
		ui = new UiConsole();
	}

	public static void main( String[] args )
    {
        //Avant lancement du programme
		System.out.println( "Bonjour et bienvenue!" );
        new Main();
        
        //Lancement du programme
        ui.run();
        
        //Après la fin du programme
        System.out.println("Merci d'avoir utilisé ce programme.");
    }
}
