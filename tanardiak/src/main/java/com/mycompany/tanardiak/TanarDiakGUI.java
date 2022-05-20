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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author dell
 */
public class TanarDiakGUI {
    private AdatSzervizSzolgaltato adatModell=new AdatKapcsolat();
    public static boolean RIGHT_TO_LEFT = false;
    private JPanel listerPanel=new JPanel();
    private JPanel displayPanel=new JPanel();
    private JPanel editorPanel=new JPanel();
    private JPanel IOPanel=new JPanel();
    private JPanel buttonPanel=new JPanel();
    private JPanel upperPanel=new JPanel();
    private JTextArea display=new JTextArea(20,100);
    private JTextField nameField=new JTextField(30);
    private JTextField IDField=new JTextField(30);
    private JTextField subjectField=new JTextField(30);
    private JTextField avgField=new JTextField(30);
    private JTextField classField=new JTextField(30);
    //private Vector <Ember> emberLista=new Vector<>();
    private JList <Ember>emberListaDisplay=new JList(adatModell.getEmberLista());
    private String[] emberTipusok= adatModell.getEmberTipusok();
    private JComboBox<String> emberTipus=new JComboBox<>(emberTipusok);
    private JButton commit=new JButton("Hozzáad/másol");
    private JButton modify=new JButton("Módosít");
    private JButton delete=new JButton("Töröl");
    private JButton load=new JButton("Betöltés");
    private JButton save=new JButton("Mentés");
     
    
    
        public void buildTheGUI(Container pane) {
        pane.setLayout(new BorderLayout());
        JLabel cim=new JLabel("Tanárok és diákok", SwingConstants.CENTER);
        cim.setFont(new Font("Arial", Font.BOLD, 20));
        pane.add(BorderLayout.PAGE_START,cim);
        pane.add(BorderLayout.PAGE_END,displayPanel);
        pane.add(BorderLayout.CENTER,listerPanel);
        pane.add(BorderLayout.LINE_END,editorPanel);
        adatModell.addNewEmber(getEmberTipusIndex("Tanár"),"Vörös Laci","ID1","kémia-fizika",null,null);
        adatModell.addNewEmber(getEmberTipusIndex("Tanár"),"Julcsi","ID2","Ének-zene",null,null);
        adatModell.addNewEmber(getEmberTipusIndex("Diák"),"Máté","ID3","","2.A","4.1");
        adatModell.addNewEmber(getEmberTipusIndex("Diák"),"Ádám","ID4","","1.A","4.0");
        adatModell.addNewEmber(getEmberTipusIndex("Tanár"),"Kriszti","ID5","Közgazdaságtan","","");
        listerPanel.setLayout(new GridLayout(1,1));
        emberListaDisplay.setBorder(BorderFactory.createEtchedBorder());
        //listerPanel.add(BorderLayout.PAGE_START,new JLabel("Tanulók és tanárok",SwingConstants.LEFT));
        listerPanel.add(emberListaDisplay);
        emberListaDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        emberListaDisplay.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                fillEditorFields();
            }
        });
        
        
        //listPane.add(Box.createRigidArea(new Dimension(0,5)));
        //listPane.add(listScroller);
        listerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.PAGE_AXIS));
        editorPanel.add(new JLabel("Pozíció:", SwingConstants.LEFT));
        editorPanel.add(emberTipus);
        editorPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        emberTipus.setSelectedIndex(0);
        classField.setEnabled(false);
        avgField.setEnabled(false);
        emberTipus.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
             if (emberTipus.getSelectedIndex()==0){
                 classField.setEnabled(false);
                 avgField.setEnabled(false);
                 subjectField.setEnabled(true);
             }
             else {
                 classField.setEnabled(true);
                 avgField.setEnabled(true);
                 subjectField.setEnabled(false);
             }
            }
        });
        editorPanel.add(new JLabel("Név:",SwingConstants.LEFT));
        editorPanel.add(nameField);
        editorPanel.add(new JLabel("Azonosító:",SwingConstants.LEFT));
        editorPanel.add(IDField);
        editorPanel.add(new JLabel("Tantárgy (csak tanároknál):",SwingConstants.LEFT));
        editorPanel.add(subjectField);
        editorPanel.add(new JLabel("Osztály (csak diákoknál):",SwingConstants.LEFT));
        editorPanel.add(classField);
        editorPanel.add(new JLabel("Tanulmányi átlag (csak diákoknál):",SwingConstants.LEFT));
        editorPanel.add(avgField);
        editorPanel.add(buttonPanel);
        buttonPanel.add(commit);
        commit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                adatModell.addNewEmber(emberTipus.getSelectedIndex(),nameField.getText(),
                        IDField.getText(),subjectField.getText(),classField.getText(),
                        avgField.getText());
            }
        });
        buttonPanel.add(modify);
        buttonPanel.add(delete);
        delete.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                adatModell.deleteByAzonosito(IDField.getText());
                emberListaDisplay.updateUI();
                fillEditorFields();
            }
        });
        display.setBorder(BorderFactory.createEtchedBorder());
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(BorderLayout.PAGE_START,new JLabel("Konzol",SwingConstants.LEFT));
        displayPanel.add(BorderLayout.CENTER,display);
        displayPanel.add(BorderLayout.PAGE_END,IOPanel);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        IOPanel.setLayout(new FlowLayout());
        IOPanel.add(load);
        IOPanel.add(save);
    }
        
    private int getEmberTipusIndex(String emberTipus){
        for (int i=0;i<emberTipusok.length;i++) if (emberTipusok[i].equals(emberTipus))return i;
        return -1;
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
        Ember peldany=emberListaDisplay.getSelectedValue();
        if (peldany==null) return;
        if (peldany instanceof Tanar){
            emberTipus.setSelectedIndex(getEmberTipusIndex("Tanár"));
            nameField.setText(peldany.getNev());
            IDField.setText(peldany.getAzonosito());
            subjectField.setText(((Tanar) peldany).getTargy());
            classField.setText("");
            avgField.setText("");
            return;
        }
        if (peldany instanceof Diak){
            emberTipus.setSelectedIndex(getEmberTipusIndex("Diák"));
            nameField.setText(peldany.getNev());
            IDField.setText(peldany.getAzonosito());
            subjectField.setText("");
            classField.setText(((Diak) peldany).getOsztaly());
            avgField.setText(""+((Diak) peldany).getAtlag());
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
       
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
