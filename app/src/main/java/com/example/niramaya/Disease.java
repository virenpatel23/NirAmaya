package com.example.niramaya;

import java.util.Comparator;

public class Disease   {

    private int coun;
    private String diseae;


    public  Disease(){
        //Empty cunstructor
    }

    public Disease(int coun, String diseae) {
        this.coun = coun;
        this.diseae = diseae;
    }

    public int getCoun() {
        return coun;
    }

    public void setCoun(int coun) {
        this.coun = coun;
    }

    public String getDiseae() {
        return diseae;
    }

    public void setDiseae(String diseae) {
        this.diseae = diseae;
    }

    public static Comparator<Disease> Countno = new Comparator<Disease>() {

        public int compare(Disease s1, Disease s2) {

            int no1 = s1.getCoun();
            int no2 = s2.getCoun();

            /*For ascending order*/
          //  return no1-no2;

            /*For descending order*/
            return no2-no1;
        }};


//    @Override
//    public String toString() {
//        return "" + diseae + "\t " + coun ;
//    }
}
