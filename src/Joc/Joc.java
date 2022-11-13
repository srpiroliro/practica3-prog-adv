package Joc;

import Estructura.ArbreB;

public class Joc {

	public static void main(String[] args) {
		ArbreB a;

		System.out.println("\u001B[31m"+"\nBenvingut al joc dels animals");
		System.out.println("----------------------------");
		System.out.print("\nVols carregar un fitxer [si/no]? "+"\u001B[32m");
		if (System.console().readLine().toLowerCase().equals("si")) {
			System.out.println("\u001B[31m"+"Pue va a ser que no pq encara no he fet :)");
			System.out.print("\u001B[31m"+"Nom del fitxer: "+"\u001B[32m");
			try {
				a = new ArbreB(System.console().readLine());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				a = new ArbreB();
			}
		}else{
			System.out.println("\u001B[31m"+"\nNo s'ha carregat cap fitxer");
			System.out.println("Comencem amb un arbre buit");
			System.out.println("\nPer començar cal intoduir una primera pregunta amb dues respostes");
			System.out.println("----------------------------");

			System.out.print("\nIndica la pregunta de l'arrel de l'arbre: "+"\u001B[32m");
			String pregunta = System.console().readLine();
			pregunta = pregunta.substring(0, 1).toUpperCase() + pregunta.substring(1).toLowerCase();
			if(!pregunta.endsWith("?")) pregunta += "?";

			System.out.print("\u001B[31m"+"Indica el nom de l'animal de la resposta afirmativa: "+"\u001B[32m");
			String respostaAfirmativa = System.console().readLine().toUpperCase();
			if(respostaAfirmativa.endsWith("?")) respostaAfirmativa = respostaAfirmativa.substring(0, respostaAfirmativa.length() - 1);

			System.out.print("\u001B[31m"+"Indica el nom de l'animal de la resposta negativa: "+"\u001B[32m");
			String respostaNegativa = System.console().readLine().toUpperCase();
			if(respostaNegativa.endsWith("?")) respostaNegativa = respostaNegativa.substring(0, respostaNegativa.length() - 1);

			a = new ArbreB(new ArbreB(null, null, respostaAfirmativa), new ArbreB(null, null, respostaNegativa), pregunta);
		}
		
		do{
			System.out.println("\u001B[31m"+"----------------------------");
			System.out.println("\n\nJUGUEM!!!!\n\n");
			jugar(a);
			System.out.print("\u001B[31m"+"\nVols jugar una altra vegada [si/no]? "+"\u001B[32m");
			a.rewind();
		}while(System.console().readLine().toLowerCase().equals("si"));

		System.out.print("\u001B[31m"+"\nVols guardar l'arbre en un fitxer [si/no]? "+"\u001B[32m");
		
		if (System.console().readLine().toLowerCase().equals("si")) {
			// System.out.print("Nom del fitxer: ");
			// try{
			// 	a.save(System.console().readLine());
			// }
			// catch (Exception e) {
			// 	System.out.println("Error: " + e.getMessage());
			// }
			System.out.println("Pue va a ser que no pq encara no he fet el save :)");
		}
		System.out.println("\u001B[31m"+"\n\nADEU!!!!\n\n");
	}

	private static void jugar(ArbreB a) {
		System.out.print("\u001B[31m"+a.getContents()+" "+"\u001B[32m");

		if (System.console().readLine().toLowerCase().equals("si")) a.moveToYes();
		else a.moveToNo();
		
		if (a.atAnswer()) {
			System.out.print("\u001B[31m"+"Em sembla que ja ho se!!!. Podria ser un/a " + a.getContents() + "? "+"\u001B[32m");
			
			if (System.console().readLine().toLowerCase().equals("si")) System.out.println("\u001B[31m"+"Molt bé, he guanyat!");
			else {
				System.out.print("\u001B[31m"+"\nOhhh, no ho sabia. Quin és el teu animal? "+"\u001B[32m");
				String animal = System.console().readLine().toUpperCase();
				if(animal.endsWith("?")) animal = animal.substring(0, animal.length() - 1);

				System.out.print("\u001B[31m"+"Quina pregunta diferenciarà un/a " + animal + " d'un/a " + a.getContents() + "? "+"\u001B[32m");
				String pregunta = System.console().readLine();
				pregunta = pregunta.substring(0, 1).toUpperCase() + pregunta.substring(1).toLowerCase();
				if(!pregunta.endsWith("?")) pregunta += "?";

				a.improve(pregunta, animal);
			}
		} 
		else jugar(a);
	}

}
