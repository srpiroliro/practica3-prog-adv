

package Joc;

import Keyboard.*;

import java.util.HashMap;
import java.util.Map;
import Estructura.ArbreB;

public class Joc{

	static Map<String, String> msgs=new HashMap<String, String>();
	public static void main(String[] args){
		String answer;
		createMessages();
		
		System.out.println(msgs.get("welcome")+"\n"+msgs.get("spacer")+"\n"+msgs.get("load-file?"));
		ArbreB arbre=(answer.toLowerCase().equals("si"))?load_file():generate_tree();

		do{
			arbre.rewind();
			System.out.println(msgs.get("spacer")+"\n"+msgs.get("play"));
			jugar(arbre);
			System.out.print(msgs.get("play-again"));
			answer=Keyboard.readString().toLowerCase();
		}while(answer.equals("si"));

		save_tree(arbre);

		System.out.println(msgs.get("goodbye"));
	}

	private static ArbreB generate_tree(){
		System.out.println(msgs.get("no-file-loaded")+"\n"+msgs.get("start-tree-intro")+"\n"+msgs.get("spacer"));

		System.out.print(msgs.get("get-question"));
		String pregunta=capitalize(Keyboard.readString());
		if(!pregunta.endsWith("?")) pregunta+="?";

		System.out.print(msgs.get("affirmative-answer"));
		String affirmative=removeQ(Keyboard.readString().toUpperCase());
		
		System.out.print(msgs.get("negative-answer"));
		String negative=removeQ(Keyboard.readString().toUpperCase());

		return new ArbreB(
			new ArbreB(null,null,affirmative),
			new ArbreB(null,null,negative),
			pregunta
		);
	}

	private static ArbreB load_file(){
		ArbreB a;
		System.out.print(msgs.get("filename"));
		try{
			String answer=Keyboard.readString(); // CHECK what if 404?
			a=new ArbreB(answer);
		}catch (Exception e){
			System.out.println("Error: "+e.getMessage());
			a=new ArbreB();
		}
		return a;
	}

	private static void save_tree(ArbreB arbre){
		System.out.print(msgs.get("save-tree?"));
		
		String answer=Keyboard.readString();
		if(answer.toLowerCase().equals("si")){
			System.out.print(msgs.get("filename"));
			try{arbre.save(System.console().readLine());}
			catch(Exception e){
				System.out.println("Error: "+e.getMessage());
			}
		}
	}

	private static void jugar(ArbreB a){
		String answer;
		System.out.print("\u001B[31m"+a.getContents()+" "+"\u001B[32m");

		answer=Keyboard.readString();
		if(answer.toLowerCase().equals("si")) a.moveToYes();
		else a.moveToNo();
		
		if (a.atAnswer()){
			System.out.print(msgs.get("i-know-it")+a.getContents()+"?\u001B[32m");
			
			answer=Keyboard.readString();
			if(answer.toLowerCase().equals("si")) System.out.println(msgs.get("i-won"));
			else{
				String[] helper_data=get_helper();
				a.improve(helper_data[0],helper_data[1]);
			}
		} 
		else jugar(a);
	}

	static String[] get_helper(){
		System.out.print(msgs.get("what-animal"));
		String animal=removeQ(Keyboard.readString().toUpperCase());

		System.out.print(msgs.get("helper-question"));
		String question=capitalize(Keyboard.readString());
		if(!question.endsWith("?")) question+="?";

		return new String[]{question, animal};
	}

	static void createMessages(){
		msgs.put("welcome", "\u001B[31m"+"\nBenvingut al joc dels animals");
		msgs.put("spacer","----------------------------");
		msgs.put("load-file?","\nVols carregar un fitxer [si/no]? "+"\u001B[32m");
		msgs.put("filename","\u001B[31m"+"Nom del fitxer: "+"\u001B[32m");
		msgs.put("no-file-loaded","\u001B[31m"+"\nNo s'ha carregat cap fitxer");
		msgs.put("start-tree-intro","Comencem amb un arbre buit\nPer començar cal intoduir una primera pregunta amb dues respostes");
		msgs.put("get-question","\nIndica la pregunta de l'arrel de l'arbre: "+"\u001B[32m");
		msgs.put("affirmative-answer","\u001B[31m"+"Indica la resposta afirmativa: "+"\u001B[32m");
		msgs.put("negative-answer","\u001B[31m"+"Indica la resposta negativa: "+"\u001B[32m");
		msgs.put("play","\n\nJUGUEM!!!!\n\n");
		msgs.put("play-again","\u001B[31m"+"\nVols jugar una altra vegada [si/no]? "+"\u001B[32m");
		msgs.put("save-tree?","\u001B[31m"+"\nVols guardar l'arbre en un fitxer [si/no]? "+"\u001B[32m");
		msgs.put("goodbye","\u001B[31m"+"\n\nADEU!!!!\n\n");
		msgs.put("i-know-it","\u001B[31m"+"Em sembla que ja ho se!!!. Podria ser un/a ");
		msgs.put("i-won","\u001B[31m"+"Molt bé,he guanyat!");
		msgs.put("what-animal","\u001B[31m"+"\nOhhh,no ho sabia. Quin és el teu animal? "+"\u001B[32m");
		msgs.put("helper-question","Diguem una pregunta que correspon a aquest animal: ");
	}

	static String capitalize(String in){return in.substring(0,1).toUpperCase()+in.substring(1).toLowerCase();}
	static String removeQ(String in){return (in.endsWith("?"))?in.substring(0,in.length()-1):in;}
}
