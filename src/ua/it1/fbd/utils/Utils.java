package ua.it1.fbd.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Utils {

    public static ArrayList<Integer> findIndex(String word, ArrayList<String> list){
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            if(list.get(i).contains(word))
                indexes.add(i);
        }

        return indexes;
    }

    public static String htmlFormater(String text){
        if (!text.isEmpty())
       text = "<html><style>" +
                "div {" +
                "  width: 430px;" +
                "}" +
                "" +
                "div.b {" +
                "  word-wrap: break-word;" +
                "}" +
                "</style><div class='b'>"+text+"</div></html>";

        return text;
    }

    public static int getExanIndex(String title){
        int index = 0;
        switch (title){
            case "S01A":
                index = 0;
                break;
            case "S01B":
                index = 1;
                break;
            case "T2A":
                index = 2;
                break;
            case "T2B":
                index = 3;
                break;
            case "T3A":
                index = 4;
                break;
            case "T3A2":
                index = 5;
                break;
            case "T3B":
                index = 6;
                break;
            case "T4":
                index = 7;
                break;
            case "T5":
                index = 8;
                break;
        }

        return index;
    }


    public static ArrayList<Pregunta> getAll(ArrayList<Examen> list){
        ArrayList<Pregunta> all = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            all.addAll(list.get(i).preguntas);
        }

        return all;
    }

    public static String round(Double d){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        Double dd = d.doubleValue();
        return df.format(dd);
    }

}
