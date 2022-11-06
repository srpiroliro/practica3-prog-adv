package Joc;

import Estructura.ArbreB;

public class Joc {

	public static void main(String[] args) {
		ArbreB a;

		System.out.println("\nBenvingut al joc dels animals");
		System.out.println("----------------------------");
		System.out.print("\nVols carregar un fitxer [si/no]? ");
		if (System.console().readLine().toLowerCase().equals("si")) {
			System.out.print("Nom del fitxer: ");
			String filename = System.console().readLine();
			try {
				a = new ArbreB(filename);
				// TODO
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				a = new ArbreB();
			}
		} else {
			System.out.println("\nNo s'ha carregat cap fitxer");
			System.out.println("Comencem amb un arbre buit");
			System.out.println("\nPer començar cal intoduir una primera pregunta amb dues respostes");
			System.out.println("----------------------------");

			System.out.print("\nIndica la pregunta de l'arrel de l'arbre: ");
			String pregunta = System.console().readLine();
			pregunta = pregunta.substring(0, 1).toUpperCase() + pregunta.substring(1).toLowerCase();
			if(!pregunta.endsWith("?")) pregunta += "?";

			System.out.print("Indica el nom de l'animal de la resposta afirmativa: ");
			String respostaAfirmativa = System.console().readLine().toUpperCase();
			if(respostaAfirmativa.endsWith("?")) respostaAfirmativa = respostaAfirmativa.substring(0, respostaAfirmativa.length() - 1);

			System.out.print("Indica el nom de l'animal de la resposta negativa: ");
			String respostaNegativa = System.console().readLine().toUpperCase();
			if(respostaNegativa.endsWith("?")) respostaNegativa = respostaNegativa.substring(0, respostaNegativa.length() - 1);

			a = new ArbreB(new ArbreB(null, null, respostaAfirmativa), new ArbreB(null, null, respostaNegativa), pregunta);
		}
		System.out.println("\n\nJUGUEM!!!!\n\n");
		System.out.print(a.getContents()+" ");
		if (System.console().readLine().toLowerCase().equals("si")) {
			a.moveToYes();
			if(a.atAnswer()){
				System.out.println("Em sembla que ja ho se!!!. Podria ser un "+a.getContents()+"?");
			}
		} else {
			a.moveToNo();
			if(a.atAnswer()){
				System.out.println("Em sembla que ja ho se!!!. Podria ser un "+a.getContents()+"?");
			}
		}

	}

}
