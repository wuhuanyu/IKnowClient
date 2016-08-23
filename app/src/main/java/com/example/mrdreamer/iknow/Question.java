package com.example.mrdreamer.iknow;

/**
 * Created by mrdreamer on 06/07/16.
 */
public class Question {

    private String ans_a,ans_b,ans_c,ans_d;
    private String question;
    private String ans_right;
    private int rightIndex;
    public int getRightIndex(){return rightIndex;}

    public Question(String content,String ans_a,String ans_b,String ans_c,String ans_d,int right_index){
        this.question=content;
        this.ans_a=ans_a;
        this.ans_b=ans_b;
        this.ans_c=ans_c;
        this.ans_d=ans_d;
        this.rightIndex=right_index;
    }



    public void setAns(String a,String b,String c,String d)
    {
        ans_a=a;ans_b=b;ans_c=c;ans_d=d;
    }
    public void setAns_right(int i)
    {  if(i<1||i>4) return ;

        switch(i){
            case 1:ans_right=ans_a;
            case 2:ans_right=ans_b;
            case 3:ans_right=ans_c;
            case 4:ans_right=ans_d;
        }
        rightIndex=i;
    }
    public void setQuestion(String q)
    {this.question=q;}
    public String  getQuestion(){return question;}
    public String getAns_a(){return ans_a;}
    public String getAns_b(){return ans_b;}
    public String getAns_c(){return ans_c;}
    public String getAns_d(){return ans_d;}


    public static Question getQuestionAndAnswer(){


        String ans_a="1948";
        String ans_b="1949";
        String ans_c="1950";
        String ans_d="1951";
        String question_content="The republic of China is proclaimed to be founded in which year?";

        return new Question(question_content,ans_a,ans_b,ans_c,ans_d,2);
    }
    public static boolean isAnswerRight(String ans,int index){
            return ans.equals(AnswerMatcher.getAns(index));

    }



    public enum AnswerMatcher{
        A("A",1),B("B",2),C("C",3),D("D",4);
        private String ans;
        private int index;
        private AnswerMatcher(String ans,int index){
            this.ans=ans;
            this.index=index;

        }

        public static String getAns(int index){
            for(AnswerMatcher answerMatcher:AnswerMatcher.values()){
                if(answerMatcher.index==index  ){
                    return answerMatcher.ans;
                }
            }
            return null;
        }
    }





}
