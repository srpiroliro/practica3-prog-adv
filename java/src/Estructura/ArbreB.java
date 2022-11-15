package Estructura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ArbreB {
	private class NodeA {
		String contents;
		ArbreB yes,no;

		NodeA(String pregunta){this(pregunta,null,null);}
		NodeA(String pregunta,ArbreB a1,ArbreB a2){
			this.contents=pregunta;
			this.yes=a1; this.no=a2;
		}

		private void visualitzar(boolean b){
			boolean answ=yes==null && no==null;
			if(answ==b) System.out.println(contents);
			if(!answ){
				yes.root[0].visualitzar(b);
				no.root[0].visualitzar(b);
			}
		}
		private int getDepth(){
			int left=0,right=0;
			if(yes!=null) right=yes.root[0].getDepth();
			if(no!=null)  left=no.root[0].getDepth();
			return 1+Math.max(left,right);
		}
		private int getAnimals(){
			int c=0;
			if(yes==null && no==null) return 1;
			for (ArbreB arbre:new ArbreB[]{yes,no}) {
				if(arbre!=null)
					c+=arbre.root[0].getAnimals();
			}
			return c;
		}
	}

	private NodeA[] root;

	/* CONSTRUCTORS */
	public ArbreB(){root=null;}
	public ArbreB(ArbreB a1,ArbreB a2,String pregunta){
		root=new NodeA[2];
		root[1]=new NodeA(pregunta,a1,a2); root[0]=root[1];
	}
	public ArbreB(String filename) throws Exception{
		File f=new File(filename);
		if(f.isFile()) { 
			root=new NodeA[2];
			root[1]=loadFromFile(filename); root[0]=root[1];
		} else throw new Exception("NO ES UN FITXER O ERROR 404.");
	}

	/* PUBLIC METHODS */
	public boolean isEmpty(){return (root==null)?true:root[1]==null;}
	public void rewind(){root[1]=root[0];}
	public boolean atAnswer(){return (isEmpty())?null:root[1].yes==null && root[1].no==null;}
	public void moveToYes(){
		if(!isEmpty()) 
			root[1]=(root[1].yes==null)?null:root[1].yes.root[1];
	}
	public void moveToNo(){
		if(!isEmpty()) 
			root[1]=(root[1].no==null)?null:root[1].no.root[1];
	}
	public String getContents(){return (isEmpty())?null:root[1].contents;}
	public void improve(String question,String answer){
		root[1].yes=new ArbreB(null,null,answer);
		root[1].contents=question;
		root[1].no=new ArbreB(null,null,root[1].contents);
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
	public int quantsAnimals(){return (isEmpty())?0:root[0].getAnimals();}
	public int alsada(){return (isEmpty())?0:root[0].getDepth()-1;}
	public void visualitzarPreguntes(){if(!isEmpty()) root[0].visualitzar(false);}
	public void visualitzarAnimals(){if(!isEmpty()) root[0].visualitzar(true);}

	/* PRIVATE METHODS */
	private void preorderWrite(BufferedWriter buw) throws Exception{
		if(isEmpty()) return;
		buw.write(root[0].contents);
		for(ArbreB subarbre:new ArbreB[]{root[0].yes,root[0].no}){
			if(root[0].yes!=null){
				buw.newLine(); subarbre.preorderWrite(buw);
			}
		}
	}
	private NodeA loadFromFile(String filename){
		BufferedReader reader;
		NodeA m=null;
		try{
			reader=new BufferedReader(new FileReader(filename));
			m=build(reader);
			reader.close();
		}catch(IOException e){
			System.err.println("loadFromFile failed: " + e);
			System.exit(0);
		}
		return m;
	}
	private NodeA build(BufferedReader b) throws IOException{
		NodeA node=null; String l=b.readLine();
		
		if(l!=null){
			node=new NodeA(l);
			if(isQ(l)){
				NodeA r1=build(b), r2=build(b);
				node.yes=new ArbreB(r1.yes, r1.no, r1.contents);
				node.no=new ArbreB(r2.yes, r2.no, r2.contents);
			}
		}
		return node;
	}
	private boolean isQ(String str){return str.indexOf("?")>0;}
}
