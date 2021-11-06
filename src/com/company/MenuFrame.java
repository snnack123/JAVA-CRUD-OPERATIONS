package com.company;

import menu_functionalities.IMenuInterface;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MenuFrame implements IMenuInterface {

    private JButton readButton;
    private JButton updateButton;
    private JButton createButton;
    private JButton deleteButton;
    private JPanel panel;
    private JTextArea viewTextArea;
    private JTextField txtDelete;
    private JTextField txtNume;
    private JTextField txtCantitate;
    private JTextField txtMarime;
    private JTextField txtCuloare;
    private JTextField txtPret;
    private JLabel lblError;
    private JTextField txtNumeUpdate;
    private JTextField txtCantitateUpdate;
    private JTextField txtPretUpdate;
    private JComboBox cmbMarimeUpdate;
    private JComboBox cmbCuloareUpdate;
    private JComboBox cmbMarimeCreate;
    private JComboBox cmbCuloareCreate;
    private JLabel IdUpdateField;
    private JLabel NameUpdateLabel;
    private JLabel CantitateUpdateLabel;
    private JLabel MarimeUpdateLabel;
    private JLabel CuloareUpdateLabel;
    private JLabel PretUpdateLabel;
    private JComboBox cmbDelete;
    private JComboBox cmbUpdate;
    private JButton btnNrTotal;
    private JTextField txtNrTotal;
    private JLabel lblError1;
    private JButton generareMarimeButton;
    private JButton generareCuloareButton;
    private JLabel lblGenerareMarime;
    private JLabel lblGenerareCuloare;
    private JButton btnCantitateTotala;
    private JTextField txtCantitateTotala;
    private JButton btnValoareTotala;
    private JTextField txtValoareTotala;
    private JLabel lblNrTotal;

    public MenuFrame() {
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readContent();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent();
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createContent();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContent();
            }
        });
        btnNrTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkNumber();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        generareMarimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generareMarime();
            }
        });
        generareCuloareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generareCuloare();
            }
        });
        btnCantitateTotala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cantitateTotala();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnValoareTotala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    valoareTotala();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void readContent() {
        viewTextArea.setText("");
        cmbDelete.removeAllItems();
        cmbUpdate.removeAllItems();
        viewTextArea.setEditable(false);
        lblError.setText("");
        lblError1.setText("");
        txtNrTotal.setText("");
        txtNumeUpdate.setText("");
        txtCantitateUpdate.setText("");
        txtPretUpdate.setText("");
        txtCantitateTotala.setText("");
        txtValoareTotala.setText("");
        File f = new File("db.txt");
        try {
            Scanner sc = new Scanner(f);
            String tv = "";

            while (sc.hasNextLine()) {
                tv = sc.nextLine();
                viewTextArea.append(tv + "\n");
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        try {
            Scanner scn = new Scanner(f);
            String line = "";
            String val = "";

            while (scn.hasNextLine()) {
                line = scn.nextLine();

                StringTokenizer stk = new StringTokenizer(line, ":");
                for (int i = 0; stk.hasMoreTokens(); i++) {
                    if (stk.nextToken().equals("Id")) {
                        int add = Integer.parseInt(stk.nextToken());
                        cmbDelete.addItem(add);
                        cmbUpdate.addItem(add);
                    }
                }
            }
            scn.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createContent() throws IOException {

        File f = new File("db.txt");
        LinkedList<Integer> idList = new LinkedList<Integer>();
        try {
            Scanner sc = new Scanner(f);
            ArrayList al = new ArrayList();
            String line = "";
            String val = "";

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                StringTokenizer stk = new StringTokenizer(line, ":");
                for (int i = 0; stk.hasMoreTokens(); i++) {
                    if (stk.nextToken().equals("Id")) {
                        idList.add(Integer.parseInt(stk.nextToken()));
                    }
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int id = idList.get(idList.size()-1);
        int checkNull = 0;
        int checkCantitate = 0;
        int checkPret = 0;
        int checkLungime = 0;

        if (txtNume.getText().equals("") || txtCantitate.getText().equals("") || txtPret.getText().equals("")) {
            checkNull = 0;
            lblError.setText("Toate campurile sunt obligatorii");
            lblError.setForeground(Color.RED);
        } else {
            checkNull = 1;
            lblError.setText("");
        }
        if (checkNull == 1) {
            if (txtNume.getText().length()<=3) {
                lblError.setText("Numele trebuie sa contina cel putin 3 caractere");
                lblError.setForeground(Color.red);
                checkLungime = 0;
            } else {
                checkLungime = 1;
                lblError.setText("");
            }
        }
        try {
            checkCantitate = Integer.parseInt(txtCantitate.getText());
        } catch (NumberFormatException e) {
            checkCantitate = 0;
            lblError.setText("Cantitatea trebuie sa fie un numar!");
            lblError.setForeground(Color.RED);
        }
        try {
            checkPret = Integer.parseInt(txtPret.getText());
        } catch (NumberFormatException e) {
            checkPret = 0;
            lblError.setText("Pretul trebuie sa fie un numar!");
            lblError.setForeground(Color.RED);
        }
        if (checkNull == 1 && checkCantitate != 0 && checkPret != 0 && checkLungime ==1) {
            FileWriter writer = new FileWriter("db.txt", true);
            id++;
            String nou = "\n" + "\n" + "Id:" + Integer.toString(id++) + "\n" + "Nume:" + txtNume.getText().toString() + "\n" + "Cantitate:" +
                    txtCantitate.getText().toString() + "\n" + "Marime:" + cmbMarimeCreate.getSelectedItem().toString()
                    + "\n" + "Culoare:" + cmbCuloareCreate.getSelectedItem().toString() + "\n" + "Pret:" + txtPret.getText().toString();
            writer.write(nou);
            writer.close();
            lblError.setText("Ai adaugat un nou produs cu succes!");
            lblError.setForeground(Color.green);
            txtNume.setText("");
            txtCantitate.setText("");
            txtPret.setText("");
        }
    }

    @Override
    public void updateContent() {

        File f = new File("db.txt");
        Scanner sc;
        String line;
        int val = 0;
        int id;
        String nume;
        int qty;
        int pret;
        String marime;
        String culoare;
        ArrayList ul = new ArrayList();
        int checkNull = 0;
        int checkCantitate;
        int checkPret;
        int checkLungime = 0;
        if(cmbUpdate.getSelectedItem() == null){
            lblError.setText("Trebuie sa alegi un id pentru a il actualiza");
            lblError.setForeground(Color.RED);
        }
        else {
            lblError.setText("");
            if (txtNumeUpdate.getText().equals("") || txtCantitateUpdate.getText().equals("") || txtPretUpdate.getText().equals("")) {
                checkNull = 0;
                lblError.setText("Toate campurile sunt obligatorii");
                lblError.setForeground(Color.RED);
            } else {
                checkNull = 1;
                lblError.setText("");
            }
            if (checkNull == 1) {
                if(txtNumeUpdate.getText().length() <= 3){
                    lblError.setText("Numele trebuie sa contina minim 3 caractere");
                    lblError.setForeground(Color.RED);
                    checkLungime = 0;
                }
                else {
                    checkLungime = 1;
                    lblError.setText("");
                }
            }
            }
            try {
                checkCantitate = Integer.parseInt(txtCantitateUpdate.getText());
            } catch (NumberFormatException e) {
                checkCantitate = 0;
                lblError.setText("Cantitatea trebuie sa fie un numar!");
                lblError.setForeground(Color.RED);
            }
            try {
                checkPret = Integer.parseInt(txtPretUpdate.getText());
            } catch (NumberFormatException e) {
                checkPret = 0;
                lblError.setText("Pretul trebuie sa fie un numar!");
                lblError.setForeground(Color.RED);
            }

            if (checkNull == 1 && checkCantitate != 0 && checkPret != 0 && checkLungime==1) {
                try {

                    sc = new Scanner(f);
                    id = Integer.parseInt(cmbUpdate.getSelectedItem().toString());
                    nume = txtNumeUpdate.getText().toString();
                    qty = Integer.parseInt(txtCantitateUpdate.getText());
                    marime = cmbMarimeUpdate.getSelectedItem().toString();
                    culoare = cmbCuloareUpdate.getSelectedItem().toString();
                    pret = Integer.parseInt(txtPretUpdate.getText());

                    while (sc.hasNextLine()) {
                        line = sc.nextLine();
                        StringTokenizer tk = new StringTokenizer(line, ":");

                        for (int i = 0; tk.hasMoreElements(); i++) {
                            String s = tk.nextToken();
                            if (IdUpdateField.getText().toString().equals(s)) {
                                val = Integer.parseInt(tk.nextToken().toString());
                            }

                            if (val == id) {
                                if (NameUpdateLabel.getText().equals(s)) {
                                    String name = tk.nextToken();
                                    if (nume.equals(name)) {

                                    } else {
                                        line = "Nume:" + nume;
                                    }
                                }

                                if (CantitateUpdateLabel.getText().equals(s)) {
                                    String quantity = tk.nextToken();

                                    if (Integer.toString(qty).equals(quantity)) {

                                    } else {
                                        line = "Cantitate:" + qty;
                                    }
                                }

                                if (MarimeUpdateLabel.getText().equals(s)) {
                                    String size = tk.nextToken();

                                    if (size.equals(marime)) {

                                    } else {
                                        line = "Marime:" + marime;
                                    }
                                }

                                if (CuloareUpdateLabel.getText().equals(s)) {
                                    String color = tk.nextToken();

                                    if (color.equals(culoare)) {

                                    } else {
                                        line = "Culoare:" + culoare;
                                    }
                                }

                                if (PretUpdateLabel.getText().equals(s)) {
                                    String price = tk.nextToken();

                                    if (Integer.toString(pret).equals(price)) {

                                    } else {
                                        line = "Pret:" + pret;
                                    }
                                }

                            }
                        }

                        ul.add(line);
                        System.out.println(line);

                    }

                    sc.close();

                    FileWriter fw = new FileWriter("db.txt");

                    for (int j = 0; j < ul.size(); j++) {
                        fw.write(ul.get(j).toString());
                        fw.write("\n");
                    }
                    fw.close();

                } catch (NumberFormatException e) {
                    System.out.println(e);
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    lblError.setText("Ai facut update cu succes!");
                    lblError.setForeground(Color.green);
                }
            }
        }

    @Override
    public void deleteContent() {

        if(cmbUpdate.getSelectedItem() == null){
            lblError.setText("Trebuie sa alegi un id pentru a il sterge");
            lblError.setForeground(Color.RED);
        } else {
            File f = new File("db.txt");
            try {
                Scanner sc = new Scanner(f);
                ArrayList al = new ArrayList();
                String line = "";
                String vtxt = cmbDelete.getSelectedItem().toString();
                System.out.println(vtxt);
                String val = "";

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    StringTokenizer stk = new StringTokenizer(line, ":");
                    for (int i = 0; stk.hasMoreTokens(); i++) {
                        if (stk.nextToken().equals("Id")) {
                            val = stk.nextToken();

                            if (val.equals(vtxt)) {
                                sc.nextLine();
                                sc.nextLine();
                                sc.nextLine();
                                sc.nextLine();
                                sc.nextLine();

                            }
                        }
                    }

                    if (val.startsWith(vtxt))
                        continue;
                    al.add(line);
                }

                sc.close();

                FileWriter fw = new FileWriter("db.txt");
                for (int j = 0; j < al.size(); j++) {
                    fw.write(al.get(j).toString());
                    fw.write("\n");
                }
                fw.close();
                lblError.setText("Ai sters produsul cu succes!");
                lblError.setForeground(Color.green);

            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkNumber() throws IOException {
        if( viewTextArea.getText().equals("") ){
            lblError1.setText("Trebuie intai sa citesti datele din fisier");
            lblError1.setForeground(Color.RED);
        } else {
            File f = new File("db.txt");
            FileWriter writer = new FileWriter("numar_inregistrari.txt");
            try {
                Scanner scn = new Scanner(f);
                String line = "";
                String val = "";
                int nrInregistrari = 0;

                while (scn.hasNextLine()) {
                    line = scn.nextLine();

                    StringTokenizer stk = new StringTokenizer(line, ":");
                    for (int i = 0; stk.hasMoreTokens(); i++) {
                        if (stk.nextToken().equals("Id")) {
                            nrInregistrari++;
                        }
                    }
                }
                scn.close();
                String text = "Numarul total de inregistrari din baza de date este: " +String.valueOf(nrInregistrari);
                txtNrTotal.setText(text);
                writer.write(text);
                writer.close();

            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                e.printStackTrace();
            }

            lblError1.setText("Ai aflat cu succes numarul de inregistrari!");
            lblError1.setForeground(Color.GREEN);
        }
    }

    public void cantitateTotala() throws IOException {
        if( viewTextArea.getText().equals("") ){
            lblError1.setText("Trebuie intai sa citesti datele din fisier");
            lblError1.setForeground(Color.RED);
        } else{
        File f = new File("db.txt");
        FileWriter writer = new FileWriter("cantitate_totala.txt");
        int suma = 0;
        try {
            Scanner sc = new Scanner(f);
            ArrayList al = new ArrayList();
            String line = "";
            String val = "";

            while (sc.hasNextLine()) {
                line = sc.nextLine();

                StringTokenizer stk = new StringTokenizer(line, ":");
                for (int i = 0; stk.hasMoreTokens(); i++) {
                    if (stk.nextToken().equals("Cantitate")) {
                        suma = suma + Integer.parseInt(stk.nextToken());
                    }
                }
            }
            sc.close();
            String text = "Cantitate toala de produse din baza de date este: " +String.valueOf(suma);
            txtCantitateTotala.setText(text);
            writer.write(text);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
            lblError1.setText("Ai aflat cu succes cantitate totala de produse!");
            lblError1.setForeground(Color.GREEN);
        }
    }

    public void valoareTotala() throws IOException {
        if( viewTextArea.getText().equals("") ){
            lblError1.setText("Trebuie intai sa citesti datele din fisier");
            lblError1.setForeground(Color.RED);
        } else{
            File f = new File("db.txt");
            FileWriter writer = new FileWriter("valoarea_totala.txt");
            int cantitate = 0;
            int pret = 0;
            int valoareTotala = 0;
            try {
                Scanner sc = new Scanner(f);
                ArrayList al = new ArrayList();
                String line = "";
                String val = "";

                while (sc.hasNextLine()) {
                    line = sc.nextLine();

                    StringTokenizer stk = new StringTokenizer(line, ":");
                    for (int i = 0; stk.hasMoreTokens(); i++) {
                         String currentLine = stk.nextToken();
                        if (currentLine.equals("Cantitate")) {
                            cantitate = cantitate + Integer.parseInt(stk.nextToken());
                        } else if (currentLine.equals("Pret")){
                            pret = pret + Integer.parseInt(stk.nextToken());
                        }
                    }
                }
                sc.close();
                valoareTotala = cantitate * pret;
                String text = "Valoarea toala a produselor din baza de date este: " +String.valueOf(valoareTotala) + " lei";
                txtValoareTotala.setText(text);
                writer.write(text);
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
            lblError1.setText("Ai aflat cu succes valoarea totala a produselor!");
            lblError1.setForeground(Color.GREEN);
        }
    }

    public void generareMarime() {
        String[] marime= new String[]{"XS", "S", "M", "L", "XL", "XXL"};
        int randomNum = 0 + (int)(Math.random() * 5);
        lblGenerareMarime.setText("Marime: " + marime[randomNum]);
    }

    public void generareCuloare()
    {
        String[] marime= new String[]{"Negru", "Alb", "Gri", "Rosu", "Albastru", "Galben", "Maro", "Violet", "Verde", "Portocaliu", "Roz"};
        int randomNum = 0 + (int)(Math.random() * 11);
        lblGenerareCuloare.setText("Marime: " + marime[randomNum]);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("On-Line Shop"); //creez un obiect de tip Frame
        frame.setContentPane(new MenuFrame().panel); //Sa afiseze tot ce am declarat in interfata
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //butonul de inchis aplicatia
        frame.pack(); //sa il micsoreze
        frame.setVisible(true); //il faci vizibil
    }
}


