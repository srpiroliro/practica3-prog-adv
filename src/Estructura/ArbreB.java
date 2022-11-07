package Estructura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;


public class ArbreB {
	private class NodeA {
		String contents;
		ArbreB yes, no;	
	
		NodeA(String contents) {
			//Constructor 1. Inicialitza als atributys yes i no a null
			this(contents, null, null);
		}
		NodeA(String pregunta, ArbreB a1, ArbreB a2) {
			//Constructor 2. Crea el node i l'inicialitza amb els par�metres
			this.contents = pregunta;
			this.yes = a1;
			this.no = a2;
		}
	}
	// Atributs: Taula de 2 posicions
	// Dues referències a NodeA, una que sempre s’ha de mantenir a l’arrel de l’arbre i una segona al node actual en l’avançament de joc. Ambdues referències estan englobades en una taula de dues posicions:
	private NodeA[] root;

	/* CONSTRUCTORS */
	public ArbreB(ArbreB a1, ArbreB a2, String pregunta) {
		//Constructor 1. Crea un arbre amb una pregunta i dos respostes
		root = new NodeA[2];
		root[1] = new NodeA(pregunta, a1, a2);
		root[0] = root[1];
	}
	public ArbreB() {
		//Constructor 2. Crea un arbre buit
		root = new NodeA[2];
		root[1] = new NodeA(getContents());
		root[0] = root[1];
	}	
	public ArbreB(String filename) throws Exception{
		//Constructor 3. Crea l'arbre amb el contingut donat en un fitxer
		//El par�metre indica el nom del fitxer
		// TODO: Implementar
	}

	/* PUBLIC METHODS */
	public boolean isEmpty() {return root[1] == null;} //? Ns si es correcte
	public void rewind() {root[1]=root[0];}
	/* True if the current node is an answer (a leaf) */
	public boolean atAnswer() {return root[1].yes == null && root[1].no == null;}
	/* move current to yes-descendant of itself */
	public void moveToYes() {root[1] = root[1].yes.root[0];} //! controlar nullexceptions
	/* move current to no-descendant of itself */
	public void moveToNo() { root[1] = root[1].no.root[0];} //! controlar nullexceptions
	/* get the contents of the current node */
	public String getContents() {return root[1].contents;}
	 /* Substituir la informació del node actual
	 per la pregunta donada pel jugador. Previament crear el node que serà el
	 seu fill dret, resposta no encertada, amb la informació del node actual.
	 */
	public void improve(String question, String answer) {
		root[1].no = new ArbreB(null, null, root[1].contents); 
		root[1].contents = question;
		root[1].yes = new ArbreB(null, null, answer);
	}
	private void preorderWrite(BufferedWriter buw) throws Exception {
		//Imprescindible que la implementaci� sigui recursiva
	}
	/* Saves contents of tree in a text file */
	public void save(String filename) throws Exception {
		BufferedWriter buw = null;
		try {
			buw = new BufferedWriter(new FileWriter(filename));
			this.preorderWrite(buw);
			buw.close();

		} catch (IOException e) {
			System.err.println("saveToTextFile failed: " + e);
			System.exit(0);
		}
	}
	private NodeA loadFromFile(String filename){
		//Imprescindible implementaci� recursiva
		return null;
	}
	public void visualitzarAnimals() {
		return;
		// Visualitzar a pantalla el nom dels animals que conté l’arbre
		/* Following the guidelines indicated in the statement of practice */
		/* COMPLETE */
	}
	public int quantsAnimals() {
		return 0;
		// Comptabilitza el nombre d’animals que conté l’arbre
		/* Following the guidelines indicated in the statement of practice */
		/* COMPLETE */
	}
	public int alsada() {
		return 0;
		/* COMPLETE */
		// Imprescindible invocar a un m�tode la classe NodeA
		// Calcula i retorna l’alçada de l’arbre. Recordeu que aquesta ve donada per la 
		// longitud del camí que va des de l’arrel de l’arbre a la fulla més llunyana:
	}
	public void visualitzarPreguntes() {
		/* COMPLETE */
		// Visualitzar a pantalla les preguntes que conté l’arbre
		//! Imprescindible invocar a un m�tode la classe NodeA
		ArbreB aux = this;
		visualitzarPreguntesR(aux);
	}
	private void visualitzarPreguntesR(ArbreB aux) {
		if(!aux.atAnswer()){
			System.out.print(aux.getContents());
			visualitzarPreguntesR(aux.root[1].yes);
			visualitzarPreguntesR(aux.root[1].no);
		}
	}
}
