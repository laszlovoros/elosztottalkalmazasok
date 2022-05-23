/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tanardiak;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author dell
 */
public class TanarDiakGUI {
    private AdatSzervizSzolgaltato adatModell=new AdatKapcsolat();
    public static boolean RIGHT_TO_LEFT = false;
    
    private JPanel displayPanel=new JPanel();
    private JPanel editorPanel=new JPanel();
    private JPanel IOPanel=new JPanel();
    private JPanel buttonPanel=new JPanel();
    private JTextArea display=new JTextArea(20,100);
    private JTextField nameField=new JTextField(30);
    private JTextField IDField=new JTextField(30);
    private JTextField subjectField=new JTextField(30);
    private JTextField avgField=new JTextField(30);
    private JTextField classField=new JTextField(30);
    private JList <Ember>emberListaDisplay=new JList(adatModell.getEmberLista());
    private String[] emberTipusok= adatModell.getEmberTipusok();
    private JComboBox<String> emberTipus=new JComboBox<>(emberTipusok);
    private JButton commitButton=new JButton("Hozzáad/másol");
    private JButton modifyButton=new JButton("Módosít");
    private JButton deleteButton=new JButton("Töröl");
    private JButton newButton=new JButton("Új");
    private JButton loadButton=new JButton("Top3 betöltése");
    private JButton saveButton=new JButton("Top3 mentése");
    private JButton top3Button=new JButton("Top 3");
    private JButton clearButton=new JButton("Display törlése");
    private Diak[] top3=null;
    
     
    
    
        public void buildTheGUI(Container pane) {
        pane.setLayout(new BorderLayout());
        JLabel cim=new JLabel("Tanárok és diákok", SwingConstants.CENTER);
        cim.setFont(new Font("Arial", Font.BOLD, 20));
        pane.add(BorderLayout.PAGE_START,cim);
        pane.add(BorderLayout.PAGE_END,displayPanel);
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Diák"),"Ádám","ID4","","2.A","4.0");
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Diák"),"Béla","ID6","","2.A","2.5");
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Tanár"),"Juli néni","ID2","Ének-zene",null,null);
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Tanár"),"Kriszti néni","ID5","Közgazdaságtan","","");
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Tanár"),"Laci bácsi","ID1","kémia-fizika",null,null);
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Diák"),"Máté","ID3","","2.A","4.1");
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Diák"),"Zsófi","ID7","","1.A","4.1");
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Diák"),"Fanni","ID8","","1.A","4.9");
        adatModell.addNewEmber(adatModell.getEmberTipusIndex("Diák"),"Luca","ID9","","2.A","3.5");
        emberListaDisplay.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                fillEditorFields();
            }
        });
        emberListaDisplay.setBorder(BorderFactory.createEtchedBorder());
        //listerPanel.add(emberListaDisplay);
        emberListaDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listerPanel=new JScrollPane();
        listerPanel.setViewportView(emberListaDisplay);
        emberListaDisplay.setLayoutOrientation(JList.VERTICAL);
        pane.add(BorderLayout.CENTER,listerPanel);
        pane.add(BorderLayout.LINE_END,editorPanel);//listerPanel.setLayout(new GridLayout(1,1));
        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.PAGE_AXIS));
        JLabel emberTipusLabel=new JLabel("Pozíció:");
        emberTipusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(emberTipusLabel);
        editorPanel.add(emberTipus);
        emberTipus.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        emberTipus.setSelectedIndex(0);
        classField.setEnabled(false);
        avgField.setEnabled(false);
        emberTipus.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
             if (emberTipus.getSelectedIndex()==adatModell.getEmberTipusIndex("Tanár")){
                 classField.setEnabled(false);
                 avgField.setEnabled(false);
                 subjectField.setEnabled(true);
                 deleteButton.setEnabled(true);
                 modifyButton.setEnabled(true);
                 top3Button.setEnabled(false);
             }
             else if (emberTipus.getSelectedIndex()==adatModell.getEmberTipusIndex("Diák")) {
                 classField.setEnabled(true);
                 avgField.setEnabled(true);
                 subjectField.setEnabled(false);
                 deleteButton.setEnabled(true);
                 modifyButton.setEnabled(true);
                 top3Button.setEnabled(true);
             }
             else modifyButton.setEnabled(false);
            }
        });
        
        JLabel nevLabel=new JLabel("Név:");
        nevLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(nevLabel);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(nameField);
        JLabel IDLabel=new JLabel("Azonosító:");
        IDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(IDLabel);
        IDField.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(IDField);
        JLabel subjectLabel=new JLabel("Tantárgy (csak tanároknál):");
        subjectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(subjectLabel);
        subjectField.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(subjectField);
        JLabel classLabel=new JLabel("Osztály (csak diákoknál):");
        classLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(classLabel);
        classField.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(classField);
        JLabel avgLabel=new JLabel("Tanulmányi átlag (csak diákoknál):");
        avgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(avgLabel);
        avgField.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(avgField);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editorPanel.add(buttonPanel);
        buttonPanel.add(commitButton);
        commitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                log(adatModell.addNewEmber(emberTipus.getSelectedIndex(),nameField.getText(),
                        IDField.getText(),subjectField.getText(),classField.getText(),
                        avgField.getText()));
                emberListaDisplay.clearSelection();
                emberListaDisplay.updateUI();
                modifyButton.setEnabled(false);
                emberTipus.setEnabled(true);
            }
        });
        buttonPanel.add(modifyButton);
        modifyButton.setEnabled(false);
        modifyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (emberListaDisplay.getSelectedIndex()<0){
                    log("Módosításhoz ki kell legyen jelölve egy módosítandó elem!");
                    return;
                }
                Ember ember=emberListaDisplay.getSelectedValue();
                if (adatModell.getEmberTipus(ember)!=emberTipus.getSelectedIndex()){
                    emberTipus.setSelectedIndex(adatModell.getEmberTipus(ember));
                    log("Nice try! De már létező ember típusát nem változtathatod meg!");
                }
                log(adatModell.modifyEmber(IDField.getText(),nameField.getText(),
                        subjectField.getText(),classField.getText(),
                        avgField.getText()));
                //emberListaDisplay.setSelectedIndex(adatModell.listaMeret());
                emberListaDisplay.updateUI();
                //for (int i=0;i<adatModell.getEmberLista().size();i++)log(adatModell.getEmberLista().get(i).toString());
            }
        });
        buttonPanel.add(newButton); 
        newButton.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                emberListaDisplay.clearSelection();
                clearEditorFields();
                log("Adatbeviteli mezők alaphelyzetbe állítva.");
                emberListaDisplay.updateUI();
            }
        });
        buttonPanel.add(deleteButton);
        deleteButton.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if (emberListaDisplay.getSelectedIndex()<0){
                    log("Törléshez jelöljön ki egy törlendő elemet!");
                    return;
                }
                log(adatModell.deleteByAzonosito(emberListaDisplay.getSelectedValue().getAzonosito()));
                emberListaDisplay.updateUI();
                fillEditorFields();
            }
        });
        deleteButton.setEnabled(false);
        modifyButton.setEnabled(false);
        top3Button.setEnabled(false);
        top3Button.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                top3=adatModell.top3Diak(classField.getText());
                for (int i=0;i<3;i++) if (top3[i]!=null) log((i+1)+". helyezett: "+top3[i]);
                saveButton.setEnabled(true);
            }
        });
        buttonPanel.add(top3Button);
        display.setBorder(BorderFactory.createEtchedBorder());
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(BorderLayout.PAGE_START,new JLabel("Konzol",SwingConstants.LEFT));
        JScrollPane displayScrollPanel=new JScrollPane();
        displayScrollPanel.setViewportView(display);
        displayPanel.add(BorderLayout.CENTER,displayScrollPanel);
        displayPanel.add(BorderLayout.PAGE_END,IOPanel);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        IOPanel.setLayout(new FlowLayout());
        IOPanel.add(loadButton);
        loadButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                loadTop3();
                emberListaDisplay.updateUI();
                fillEditorFields();
            }
        });
        IOPanel.add(saveButton);
        IOPanel.add(clearButton);
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                log(saveTop3());
            }
        });
        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                display.setText("");
            }
        });
        File feladatFajl=new File(System.getProperty("user.dir")+"/feladatleiras.txt");
        if (feladatFajl.exists()){
            BufferedReader br=null;
            try {
            br = new BufferedReader(new InputStreamReader(
            new FileInputStream(feladatFajl), "UTF-8"));
                String line = br.readLine();
                while (line != null) {
                    log(line);
                    line = br.readLine();
                    }
                br.close();
                } 
            catch(Exception e) {
                    
                    } 
        }
        
    }
    private void loadTop3(){
        Diak[] diakok=new Diak[3];
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
        jfc.setFileFilter(new FileNameExtensionFilter("top3 file","top3"));
        int returnValue = jfc.showOpenDialog(null);
	if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            diakok=adatModell.loadTop3(selectedFile);
            for (int i=0;i<3;i++) if (diakok[i]!=null)log((i+1)+". helyezett: "+diakok[i]);
            log("Adatbetöltés vége.");
            return;
        }
        log("Betöltés elvetve.");
        
    }    
    private String saveTop3(){
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
        jfc.setSelectedFile(new File(classField.getText().replace('.','_')+".top3"));
        jfc.setFileFilter(new FileNameExtensionFilter("top3 file","top3"));
        int returnValue = jfc.showSaveDialog(null);
	if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = jfc.getSelectedFile();
	return adatModell.saveTop3(classField.getText(),selectedFile);
        }
        return "Mentés elvetve.";
    }   
    private void clearEditorFields(){
                emberTipus.setSelectedIndex(0);
                nameField.setText("");
                IDField.setText("");
                subjectField.setText("");
                classField.setText("");
                avgField.setText("");
                deleteButton.setEnabled(false);
                modifyButton.setEnabled(false);
                top3Button.setEnabled(false);
                emberTipus.setEnabled(true);
                IDField.setEnabled(true);
                top3=null;
                saveButton.setEnabled(false);
    }
        
    private void log(String msg){
        display.append(msg+"\n");
    }   

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private void fillEditorFields(){
        saveButton.setEnabled(false);
        try{
            if (emberListaDisplay.getSelectedIndex()==-1){
                clearEditorFields();
                return;
            }
        }
        catch (Exception e){
            clearEditorFields();
            return;
        }
        try{
        Ember peldany=emberListaDisplay.getSelectedValue();
        if (peldany==null) return;
        if (peldany instanceof Tanar){
            emberTipus.setSelectedIndex(adatModell.getEmberTipusIndex("Tanár"));
            nameField.setText(peldany.getNev());
            IDField.setText(peldany.getAzonosito());
            subjectField.setText(((Tanar) peldany).getTargy());
            classField.setText("");
            avgField.setText("");
            emberTipus.setEnabled(false);
            subjectField.setEnabled(false);
            IDField.setEnabled(false);
            return;
        }
        if (peldany instanceof Diak){
            emberTipus.setSelectedIndex(adatModell.getEmberTipusIndex("Diák"));
            nameField.setText(peldany.getNev());
            IDField.setText(peldany.getAzonosito());
            subjectField.setText("");
            classField.setText(((Diak) peldany).getOsztaly());
            avgField.setText(""+((Diak) peldany).getAtlag());
            emberTipus.setEnabled(false);
            IDField.setEnabled(false);
        }
        }
        catch(Exception e){
            clearEditorFields();
        }
    }
    private static void createAndShowGUI() {
       
        //Create and set up the window.
        TanarDiakGUI gui=new TanarDiakGUI();
        JFrame frame = new JFrame("Tanárok és diákok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        gui.buildTheGUI(frame.getContentPane());
        //Use the content pane's default BorderLayout. No need for
        //setLayout(new BorderLayout());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
   
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        System.out.println("Program indul...");
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
