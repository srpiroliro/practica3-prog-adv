package Estructura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import java.util.ArrayList;
import java.util.List;


public class ArbreB {
	private class NodeA {
		String contents;
		ArbreB yes,no;	
	
		NodeA(String pregunta){this(pregunta,null,null);}
		NodeA(String pregunta,ArbreB a1,ArbreB a2){
			this.contents=pregunta;
			this.yes=a1; this.no=a2;
		}
	}
	
	private NodeA[] root; // root[0]==prev, root[1]==next

	/* CONSTRUCTORS */
	public ArbreB(ArbreB a1,ArbreB a2,String pregunta){
		root=new NodeA[2];
		root[1]=new NodeA(pregunta,a1,a2);
		root[0]=root[1];
	}
	public ArbreB(){
		root=new NodeA[2];
		// root[1]=new NodeA(); //getContents()); // CHECK
		// root[0]=root[1]; // CHECK
	}	
	public ArbreB(String filename) throws Exception{
		root=new NodeA[2];
		root[1]=loadFromFile(filename);
		root[0]=root[1];
	}

	/* PUBLIC METHODS */
	public boolean isEmpty(){return root[1]==null;} // CHECK
	public void rewind(){root[1]=root[0];}
	public boolean atAnswer(){return root[1].yes==null && root[1].no==null;}
	public void moveToYes(){
		root[1]=(root[1].yes==null)?null:root[1].yes.root[1]; // CHECK nullpointer
	} 
	public void moveToNo(){
		root[1]=(root[1].no==null)?null:root[1].no.root[1]; // CHECK nullpointer
	}
	public String getContents(){return root[1].contents;}
	public void improve(String question,String answer){
		root[1].no=new ArbreB(root[1].yes,root[1].no,root[1].contents); // CHECK
		root[1].contents=question;
		root[1].yes=new ArbreB(null,null,answer);
	}
	private void preorderWrite(BufferedWriter buw) throws Exception{
		if(root==null) return;
		buw.write(root[0].contents);
		for(ArbreB subarbre:new ArbreB[]{root[0].yes,root[0].no}){
			if(root[0].yes!=null){
				buw.newLine(); subarbre.preorderWrite(buw);
			}
		}
	}

	public void save(String filename) throws Exception{
		BufferedWriter buw=null;
		try{
			buw=new BufferedWriter(new FileWriter(filename));
			preorderWrite(buw);
			buw.close();
		}catch(IOException e){
			System.err.println("saveToTextFile failed: " + e);
			System.exit(0);
		}
	}

	private NodeA loadFromFile(String filename){
		List<String> lines=get_file_lines(filename);
		return new NodeA(
			lines.get(0),
			build(lines, 1),
			build(lines, 2)
		);
	}
	private List<String> get_file_lines(String filename){
		BufferedReader reader; 
		List<String> lines=new ArrayList<>();
		try{
			reader=new BufferedReader(new FileReader(filename));
			String line=reader.readLine();
			while(line!=null){
				lines.add(line);
				line=reader.readLine();
			}
			reader.close();
		}catch(IOException e){
			System.err.println("loadFromFile failed: " + e);
			System.exit(0);
		}
		return lines;
	}
	private ArbreB build(List<String> lines, int i){
		if(lines.size()<i) return null; // CHECK: necessary?

		ArbreB y=null,n=null;
		String content=lines.get(i);

		if(lines.size()>i+2){
			if(isQ(content)){
				y=build(lines,i+1); n=build(lines,i+2);
				for(int x=0;x<2;x++){lines.remove(i+1);}
			}
		}
		return new ArbreB(y,n,content);
	}

	private boolean isQ(String str){return str.indexOf("?")>0;}

	public int quantsAnimals(){
		return 0;
		// Comptabilitza el nombre d’animals que conté l’arbre
		/* Following the guidelines indicated in the statement of practice */
		/* COMPLETE */
	}
	public int alsada(){
		return 0;
		/* COMPLETE */
		// Imprescindible invocar a un m�tode la classe NodeA
		// Calcula i retorna l’alçada de l’arbre. Recordeu que aquesta ve donada per la 
		// longitud del camí que va des de l’arrel de l’arbre a la fulla més llunyana:
	}
	public void visualitzarPreguntes(){visualitzarR(this,false);}
	public void visualitzarAnimals(){visualitzarR(this,true);}

	private void visualitzarR(ArbreB aux, boolean b){ // CHECK: legal?
		if(aux==null) return;

		boolean answ=aux.atAnswer();
		if(answ==b) System.out.println(aux.getContents());
		if(!answ){
			visualitzarR(aux.root[1].yes, b);
			visualitzarR(aux.root[1].no, b);
		}
	}

}
