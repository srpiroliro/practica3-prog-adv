#include <string>
#include <iostream>

using namespace std;

class ArbreB:
    class NodeA{
        string content;
        ArbreB yes, no;

        void visualitzar(bool b){
            bool answ=yes==NULL&&no==NULL;
            if(answ==b) cout << content << '\n';
            if(!answ){
                yes.visualitzar(b);
                no.visualitzar(b);
            }
        };
        int getDepth(){
            int left=0, right=0;
            if(yes==NULL && no==NULL) return 0;

			if(yes!=NULL) right=yes.root[0].getDepth();
			if(no!=NULL)  left=no.root[0].getDepth();

			return 1+((left>right)?left:right);
        };
        int getAnimals(){
			int c=0;
			if(yes==NULL && no==NULL) return 1;
			for (ArbreB arbre:{yes,no}) {
				if(arbre!=NULL) c+=arbre.root[0].getAnimals();
			}
			return c;
		}
    };

    NodeA root[];

    public:
        ArbreB(){root=NULL;}
        ArbreB(ArbreB a1, ArbreB a2, String content){
	    root=NodeA[2];
	    root[1]=NodeA(a1,a2,content); root[0]=root[1];
        }
        ArbreB(String filename){
            root=NodeA[2];
            root[1]=loadFromFile(filename); root[0]=root[1];
        }



