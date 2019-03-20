package ua.it1.fbd;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ua.it1.fbd.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Inicio{


    private JPanel panelInicio;
    private JLabel mPregunta;
    private JCheckBox resp1;
    private JCheckBox resp2;
    private JCheckBox resp3;
    private JCheckBox resp4;
    private JButton btnNext;
    private JToolBar toolbar;
    private JLabel tbPreguntas;
    private JLabel tbPuntos;
    private JLabel tbCorrectas;
    private JLabel tbIncorrectas;
    private JLabel tbPen;
    private JLabel examLabel;
    private JButton btnTerminar;
    private ArrayList<Pregunta> content;
    private String appTitle = Consts.APP_TITLE, currExam = "";
    private Boolean loaded = false;
    private int currIndex = 0, correctas = 0, incorrectas = 0, total = 0, startExam = 0;
    private Double points = 0.0, pen = 0.0;
    private Color defaultClor, getDefaultClorFor, correctColor;
    private Boolean answered = false, correct = false, next = false;
    private ArrayList<String> examenes = new ArrayList<>();
    private ActionListener lr1,lr2,lr3,lr4;

    public static void main(String [] args){
        JFrame frame = new JFrame(Consts.APP_TITLE);
        frame.getContentPane().add(new Inicio().panelInicio);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().requestFocusInWindow();
        try {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("logo.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Inicio(){
        setPadding();
        defaultClor = Color.decode("#E3E3E3");
        correctColor = Color.decode("#008F7A");
        getDefaultClorFor = Color.decode("#3C3F41");
        loading(false);
        content = new ArrayList<>();
        settingExam();
        prepareData(false);

        if (loaded){
            resetChecks();
            total = content.size();
            loading(true);
            setteUpQuestions();
            tbPreguntas.setText("Pregunta " + (currIndex+1) + "/" + content.size());
            examLabel.setText(currExam);
            lr1 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(resp1.isSelected()){
                        resp1.setSelected(false);
                    }else{
                        resp1.setSelected(true);
                    }
                }
            };
             lr2 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(resp2.isSelected()){
                        resp2.setSelected(false);
                    }else{
                        resp2.setSelected(true);
                    }
                }
            };
           lr3 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(resp3.isSelected()){
                        resp3.setSelected(false);
                    }else{
                        resp3.setSelected(true);
                    }
                }
            };
            lr4 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(resp4.isSelected()){
                        resp4.setSelected(false);
                    }else{
                        resp4.setSelected(true);
                    }
                }
            };

            btnTerminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(currIndex > 0)
                        showResults();
                }
            });

            btnNext.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(currIndex < content.size()){
                        resetQuestions();
                    }else{

                    }
                }
            });
        }else{
            JOptionPane.showMessageDialog(null,
                    "Error al cargar el fichero de examen", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void setPadding(){
        Widget.setPadding(mPregunta,5,true);
        Widget.setPadding(tbPreguntas,10,false);
        Widget.setPadding(tbIncorrectas,10,false);
        Widget.setPadding(tbCorrectas,10,false);
        Widget.setPadding(tbPuntos,10,false);
        Widget.setPadding(tbPen,10,false);
        Widget.setPadding(examLabel,10,false);
    }

    private void resetChecks(){
        resp1.setSelected(false);
        resp2.setSelected(false);
        resp3.setSelected(false);
        resp4.setSelected(false);
    }

    private void enableResps(boolean enable){
        if(!enable){
            resp1.addActionListener(lr1);
            resp2.addActionListener(lr2);
            resp3.addActionListener(lr3);
            resp4.addActionListener(lr4);
        }else{
            resp1.removeActionListener(lr1);
            resp2.removeActionListener(lr2);
            resp3.removeActionListener(lr3);
            resp4.removeActionListener(lr4);
        }

    }

    private void resetQuestions(){
        correct = false;
        answered = false;
        int selecltedCount = 0, correctCount = 0;

        if (resp1.isSelected()){
            answered = true;
            // preg 1
            selecltedCount++;
            if (content.get(currIndex).repuestas.get(0).contains("[v]")){
                // correcta
                correct = true;
                correctCount++;
            }
        }else{
            if (content.get(currIndex).repuestas.get(0).contains("[v]")){
                // correcta
                correct = false;
            }
        }
        if (resp2.isSelected()){
            answered = true;
            // preg 2
            selecltedCount++;
            if (content.get(currIndex).repuestas.get(1).contains("[v]")){
                // correcta
                correct = true;
                correctCount++;
            }
        }else{
            if (content.get(currIndex).repuestas.get(1).contains("[v]")){
                // correcta
                correct = false;
            }
        }
        if (resp3.isSelected()){
            answered = true;
            // preg 3
            selecltedCount++;
            if (content.get(currIndex).repuestas.get(2).contains("[v]")){
                // correcta
                correct = true;
                correctCount++;
            }
        }else{
            if (content.get(currIndex).repuestas.get(2).contains("[v]")){
                // correcta
                correct = false;
            }
        }
        if (resp4.isSelected()){
            answered = true;
            // preg 4
            selecltedCount++;
            if (content.get(currIndex).repuestas.get(3).contains("[v]")){
                // correcta
                correct = true;
                correctCount++;
            }
        }else{
            if (content.get(currIndex).repuestas.get(3).contains("[v]")){
                // correcta
                correct = false;
            }
        }

        if (selecltedCount != correctCount) {
            correct = false;
        }

        if(next){
            enableResps(true);
            resetBackground();
            updateAccount();
        }else{
            if (answered){
                corregir();
            }else{
                // responder
                JOptionPane.showMessageDialog(null,
                        "Debes elegir una respuesta", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateAccount(){
        if (answered){
            if (correct){
                points += 1;
                correctas++;
            }else{
                points -= 0.10;
                points = points > 0 ? points : 0;
                pen += 0.10;
                incorrectas++;
            }
            currIndex++;
            tbCorrectas.setText("Correctas " + correctas);
            tbIncorrectas.setText("Icorrectas " + incorrectas);
            tbPuntos.setText("Puntuación " + Utils.round(points) + " de " + content.size());
            tbPen.setText("Penalización " + Utils.round(pen));

            if(currIndex < content.size()){

                tbPreguntas.setText("Pregunta " + (currIndex+1) + "/" + content.size());

                next = false;
                setteUpQuestions();
                resetChecks();
            }else{
               showResults();
            }
        }else{
            // responder
            JOptionPane.showMessageDialog(null,
                    "Debes elegir una respuesta", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void settingExam(){
        prepareData(true);

        Object[] exams = examenes.toArray();
        String s = (String)JOptionPane.showInputDialog(
                null,
                "Selecciona el examen que deseas resolver",
                "Examen",
                JOptionPane.PLAIN_MESSAGE,
                null,
                exams,
                examenes.get(0));

        if ((s != null) && (s.length() > 0)) {
            examLabel.setText(s);
            startExam = Utils.getExanIndex(s);
            examLabel.setText(examenes.get(0));
        }else{
            System.exit(0);
        }
    }

    private void resetBackground(){
        resp1.setBackground(defaultClor);
        resp2.setBackground(defaultClor);
        resp3.setBackground(defaultClor);
        resp4.setBackground(defaultClor);

        resp1.setForeground(getDefaultClorFor);
        resp2.setForeground(getDefaultClorFor);
        resp3.setForeground(getDefaultClorFor);
        resp4.setForeground(getDefaultClorFor);
    }

    private void showResults(){
        JOptionPane.showMessageDialog (null,
                "Fin del cuestionario"+
                        "\nExamen: " +currExam +
                        "\nPuntuación obtenida: "
                        + Utils.round(points) + " de " + content.size() +
                        "\nRespuestas correctas: " + correctas +
                        "\nRespuestas incorrectas: " + incorrectas +
                        "\nPenalizacón: " + Utils.round(pen)+
                        "\nPreguntas respondidas: " + (correctas+incorrectas) + " de " + content.size(),
                "Test finalizado",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void corregir(){
        ArrayList<Integer> indexes = Utils.findIndex("[v]", content.get(currIndex).repuestas);
        for (int i = 0; i < indexes.size(); i++){
            switch (indexes.get(i)){
                case 0:
                    resp1.setBackground(correctColor);
                    resp1.setForeground(Color.WHITE);
                    break;
                case 1:
                    resp2.setBackground(correctColor);
                    resp2.setForeground(Color.WHITE);
                    break;
                case 2:
                    resp3.setBackground(correctColor);
                    resp3.setForeground(Color.WHITE);
                    break;
                case 3:
                    resp4.setBackground(correctColor);
                    resp4.setForeground(Color.WHITE);
                    break;
            }
        }
        enableResps(false);
        next = true;
    }

    private void setteUpQuestions(){
        serializeResps();
        mPregunta.setText(content.get(currIndex).pregunta);
        resp1.setText(Utils.htmlFormater(content.get(currIndex).repuestas.get(0).replace("[v]","")));
        resp2.setText(Utils.htmlFormater(content.get(currIndex).repuestas.get(1).replace("[v]","")));
        resp3.setText(Utils.htmlFormater(content.get(currIndex).repuestas.get(2).replace("[v]","")));
        resp4.setText(Utils.htmlFormater(content.get(currIndex).repuestas.get(3).replace("[v]","")));
        serializeResps();
    }

    private void serializeResps(){
        if(resp1.getText().isEmpty()){
            resp1.setVisible(false);
        }else {
            resp1.setVisible(true);
        }
        if(resp2.getText().isEmpty()){
            resp2.setVisible(false);
        }else {
            resp2.setVisible(true);
        }
        if(resp3.getText().isEmpty()){
            resp3.setVisible(false);
        }else {
            resp3.setVisible(true);
        }
        if(resp4.getText().isEmpty()){
            resp4.setVisible(false);
        }else {
            resp4.setVisible(true);
        }
    }

    private void loading(boolean load){
        mPregunta.setVisible(load);
        resp1.setVisible(load);
        resp2.setVisible(load);
        resp3.setVisible(load);
        resp4.setVisible(load);
        btnNext.setVisible(load);
    }

    private void prepareData(boolean titsOnly) {
        JSONParser parser = new JSONParser();

        try {

            /**/
            Object obj;
            try{
                obj = parser.parse(new FileReader("preguntas.json"));
            }catch (Exception ex){
                InputStream is =  getClass().getResourceAsStream("preguntas.json");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                obj = parser.parse(br);
            }

            JSONArray jsonArray = (JSONArray) obj;
            if(titsOnly){
                for (int i = 0; i < jsonArray.size(); i++){
                    JSONObject examen = (JSONObject) jsonArray.get(i);
                    examenes.add(examen.get("titulo").toString());
                }
            }else{
                JSONObject examen = (JSONObject) jsonArray.get(startExam);
                JSONArray preguntas = (JSONArray) examen.get("preguntas");
                currExam = examen.get("titulo").toString();

                for (int i = 0; i < preguntas.size(); i++){
                    JSONObject prs = (JSONObject) preguntas.get(i);
                    String pregunta = prs.get("pregunta").toString();
                    pregunta = Utils.htmlFormater(pregunta);
                    JSONArray resps = (JSONArray) prs.get("respuestas");
                    Pregunta p = new Pregunta(pregunta);
                    ArrayList<String> respuestas = new ArrayList<>();

                    for (int j = 0; j < resps.size(); j++){
                        respuestas.add(resps.get(j).toString());
                    }

                    Collections.shuffle(respuestas);
                    p.setRepuestas(respuestas);
                    content.add(p);
                }

                loaded = true;
            }
        } catch (Exception e) {
        }
    }

}
