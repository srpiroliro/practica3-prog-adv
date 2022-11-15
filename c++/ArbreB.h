#include <string>
#include <iostream>

using namespace std;

class ArbreB{
    struct NodeA{
        string content;

        // TODO
        ArbreB* yes=nullptr;
        ArbreB* no=nullptr;

        NodeA(){} // default constructor
        NodeA(string content){
            this->content=content; yes=nullptr; no=nullptr;
        }
        NodeA(string content, ArbreB* a1, ArbreB* a2){
            this->content=content; yes=a1 ;no=a2;
        }
    };
    NodeA root[2];
    

    public:
        ArbreB(){}
        ArbreB(ArbreB *a1, ArbreB *a2, string content){
            root[1].content=content;
            root[1].yes=a1; root[1].no=a2; 
            root[0]=root[1];
        }
        ArbreB(string filename){
            root[1]=loadFromFile(filename); 
            root[0]=root[1];
        }


        bool isEmpty(){
            return root[1].content=="";
        }
        void rewind(){root[1]=root[0];}
        bool atAnswer(){
            return (isEmpty())?NULL:root[1].yes==nullptr && root[1].no==nullptr;
        }
        void moveToYes(){
            // if(!isEmpty()) root[1]=(root[1].yes==null)?null:(*root[1]).yes.root[1]; // CHECK
        }
        void moveToNo(){
            // if(!isEmpty()) root[1]=(root[1].no==null)?null:(*root[1]).no.root[1]; // CHECK
        }
        string getContents(){
            if(!isEmpty()){
                return root[1].content;
            }
        }
        void improve(string question,string answer){
            root[1].no=new ArbreB(nullptr,nullptr,root[1].content);
            root[1].content=question;
            root[1].yes=new ArbreB(nullptr,nullptr,answer);
        }
        void save(string filename){
            // open file
            // preorderWrite(bufferedReader)
        }
    private:
        NodeA loadFromFile(string filename){
            // BufferedReader reader;
            NodeA *m=nullptr;
            try{
                // reader=new BufferedReader(new FileReader(filename));
                // m=build(reader);
                // reader.close();
            }catch(exception& e){cerr << e.what() << '\n';}
            
            return *m;
        }
        NodeA build(...){
            NodeA *node=nullptr; 
            string l=""; //b.readLine();
		
            if(l!=""){ 
                node=new NodeA(l);
                if(isQ(l)){
                    // NodeA r1=build(b), r2=build(b);
                    // node->yes=new ArbreB(r1.yes, r1.no, r1.contents);
                    // node->no=new ArbreB(r2.yes, r2.no, r2.contents);
                }
            }
            return *node;
        }
        void preorderWrite(...){
            if(isEmpty()) return;
            // buw.write(root[0].contents);
            ArbreB *as[2]={root[0].yes,root[0].no};

            for(ArbreB *subarbre: as){
                if(root[0].yes!=nullptr){
                    // buw.newLine(); 
                    // subarbre->preorderWrite(buw);
                }
            }
        }
        bool isQ(string str){return str.back()=="?">0;}

        
};