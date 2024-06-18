package models;

import javafx.event.ActionEvent;

public class Questions{


    private String nom;
    private int nbrquest;
    private int  idquiz;
    private String question;
    private String op1;
    private String op2;
    private String op3;

    private String answer;
    private int idquest;

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    private int iduser;
    public Questions(int nbrquest, String nom) {
        this.nbrquest=nbrquest;
        this.nom=nom;
    }

    public int getIdquiz() {
        return idquiz;
    }



    public void setIdquiz(int idquiz) {
        this.idquiz = idquiz;
    }




    @Override
    public String toString() {
        return "Questions{" +

                " nomquiz='" + nom+ '\'' +
                ", nbques=" + nbrquest +
                ", question='" + question + '\'' +
                ", op1='" + op1 + '\'' +
                ", op2='" + op2 + '\'' +
                ", op3='" + op3 + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public Questions(String nomquiz, int nbques, int idquiz, String question, String op1, String op2, String op3, String answer) {
        this.nom = nomquiz;
        this.nbrquest = nbques;
        this.idquiz = idquiz;
        this.question = question;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.answer = answer;
        /// this.idquest = idquest;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbrquest(int nbrquest) {
        this.nbrquest = nbrquest;
    }

    public int getNbrquest() {
        return nbrquest;
    }

    public String getNom() {
        return nom;
    }

/*public Questions (String o1 , String o2 , String o3,String a,String q, int qj)
{
    this.op1=o1;
    this.op2=o2;
    this.op3=o3;
    this.answer=a;
    this.question=q;
    this.idquiz=qj;

}*/
public Questions(){}

    /*public Questions(String opp1, String opp2, String opp3, String ann, String qu,int j) {
        this.op1=opp1;
        this.op2=opp2;
        this.op3=opp3;
         this.answer=ann;
        this.question=qu;
        this.idquiz=j;
    }*/



    public String getAnswer() {
        return answer;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getOp3() {
        return op3;
    }


    public String getQuestion() {
        return question;
    }

    public int getIdquest() {
        return idquest;
    }

    public void setIdquest(int idquest) {
        this.idquest = idquest;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }


public Questions (String o1 ,String o2 ,String o3,String a,String q,int idquiz)
    {
        this.op1=o1;
        this.op2=o2;
        this.op3=o3;
        this.answer=a;
        this.question=q;
        this.idquiz=idquiz;
        }


    public void ajout(ActionEvent actionEvent) {
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}


