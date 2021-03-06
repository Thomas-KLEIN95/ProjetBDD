package fr.miage.fsgbd;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Galli Gregory, Mopolo Moke Gabriel
 */
public class GUI extends JFrame implements ActionListener {

    TestInteger testInt = new TestInteger();
    BTreePlus<Integer> bInt;
    CSVSearchStats CSVStats;
    private JButton buttonClean, buttonRemove, buttonLoad, buttonSave, buttonAddMany, buttonAddItem, buttonRefresh, buttonLoadCSV, buttonSearchSeq, buttonSearchIndex, buttonSearchMany;
    private JTextField txtNbreItem, txtNbreSpecificItem, txtU, txtFile, removeSpecific, txtFileCSV, txtId, txtSearchMany;
    private final JTree tree = new JTree();

    public GUI() {
        super();
        build();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonLoad || e.getSource() == buttonClean || e.getSource() == buttonSave || e.getSource() == buttonRefresh || e.getSource() == buttonLoadCSV ) {
            if (e.getSource() == buttonLoad) {
                BDeserializer<Integer> load = new BDeserializer<Integer>();
                bInt = load.getArbre(txtFile.getText());
                if (bInt == null)
                    System.out.println("Echec du chargement.");

            } else if (e.getSource() == buttonClean) {
                if (Integer.parseInt(txtU.getText()) < 2)
                    System.out.println("Impossible de créer un arbre dont le nombre de clés est inférieur à 2.");
                else
                    bInt = new BTreePlus<Integer>(Integer.parseInt(txtU.getText()), testInt);
            } else if (e.getSource() == buttonSave) {
                BSerializer<Integer> save = new BSerializer<Integer>(bInt, txtFile.getText());
            } else if (e.getSource() == buttonRefresh) {
                tree.updateUI();
            } else if (e.getSource() == buttonLoadCSV) {
                CSVFileLoader fichierCSV = new CSVFileLoader(); //creation d'un fichier CSV
                bInt = fichierCSV.loadCSV(txtFileCSV.getText(),Integer.parseInt(txtU.getText()),testInt);
                CSVStats = new CSVSearchStats(bInt);
            }
        } else {
            if (bInt == null)
                bInt = new BTreePlus<Integer>(Integer.parseInt(txtU.getText()), testInt);

            if (e.getSource() == buttonAddMany) {
                for (int i = 0; i < Integer.parseInt(txtNbreItem.getText()); i++) {
                    int valeur = (int) (Math.random() * 10 * Integer.parseInt(txtNbreItem.getText()));
                    boolean done = bInt.addValeur(valeur);

					/*
					  On pourrait forcer l'ajout mais on risque alors de tomber dans une boucle infinie sans "règle" faisant sens pour en sortir

					while (!done)
					{
						valeur =(int) (Math.random() * 10 * Integer.parseInt(txtNbreItem.getText()));
						done = bInt.addValeur(valeur);
					}
					 */
                }

            } else if (e.getSource() == buttonAddItem) {
                if (!bInt.addValeur(Integer.parseInt(txtNbreSpecificItem.getText())))
                    System.out.println("Tentative d'ajout d'une valeur existante : " + txtNbreSpecificItem.getText());
                txtNbreSpecificItem.setText(
                        String.valueOf(
                                Integer.parseInt(txtNbreSpecificItem.getText()) + 2
                        )
                );

            } else if (e.getSource() == buttonRemove) {
                bInt.removeValeur(Integer.parseInt(removeSpecific.getText()));
            } else if(e.getSource() == buttonSearchMany || e.getSource() == buttonSearchSeq || e.getSource() == buttonSearchIndex) {
                CSVStats = new CSVSearchStats(bInt);
                if (e.getSource() == buttonSearchMany) {
                    CSVStats.searchMany(Integer.parseInt(txtSearchMany.getText()));
                } else if (e.getSource() == buttonSearchSeq) {
                    // recherche sequentielle
                    CSVStats.seqSearch(Integer.parseInt(txtId.getText()));
                } else if (e.getSource() == buttonSearchIndex) {
                    // recherche par index
                    CSVStats.indexSearch(Integer.parseInt(txtId.getText()));
                }
            }
        }

        tree.setModel(new DefaultTreeModel(bInt.bArbreToJTree()));
        for (int i = 0; i < tree.getRowCount(); i++)
            tree.expandRow(i);

        tree.updateUI();
    }

    private void build() {
        setTitle("Indexation - B Arbre");
        setSize(760, 760);
        setLocationRelativeTo(this);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(buildContentPane());
    }

    private JPanel buildContentPane() {
        GridBagLayout gLayGlob = new GridBagLayout();

        JPanel pane1 = new JPanel();
        pane1.setLayout(gLayGlob);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 5, 2, 0);

        JLabel labelU = new JLabel("Nombre max de clés par noeud (2m): ");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        pane1.add(labelU, c);

        txtU = new JTextField("4", 7);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 2;
        pane1.add(txtU, c);

        JLabel labelBetween = new JLabel("Nombre de clefs à ajouter:");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        pane1.add(labelBetween, c);

        txtNbreItem = new JTextField("10000", 7);
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        pane1.add(txtNbreItem, c);


        buttonAddMany = new JButton("Ajouter n éléments aléatoires à l'arbre");
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = 2;
        pane1.add(buttonAddMany, c);

        JLabel labelSpecific = new JLabel("Ajouter une valeur spécifique:");
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(labelSpecific, c);

        txtNbreSpecificItem = new JTextField("50", 7);
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(txtNbreSpecificItem, c);

        buttonAddItem = new JButton("Ajouter l'élément");
        c.gridx = 2;
        c.gridy = 3;
        c.weightx = 1;
        c.gridwidth = 2;
        pane1.add(buttonAddItem, c);

        JLabel labelRemoveSpecific = new JLabel("Retirer une valeur spécifique:");
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(labelRemoveSpecific, c);

        removeSpecific = new JTextField("54", 7);
        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(removeSpecific, c);

        buttonRemove = new JButton("Supprimer l'élément n de l'arbre");
        c.gridx = 2;
        c.gridy = 4;
        c.weightx = 1;
        c.gridwidth = 2;
        pane1.add(buttonRemove, c);

        JLabel labelFilename = new JLabel("Nom de fichier : ");
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(labelFilename, c);

        txtFile = new JTextField("arbre.abr", 7);
        c.gridx = 1;
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(txtFile, c);

        buttonSave = new JButton("Sauver l'arbre");
        c.gridx = 2;
        c.gridy = 5;
        c.weightx = 0.5;
        c.gridwidth = 1;
        pane1.add(buttonSave, c);

        buttonLoad = new JButton("Charger l'arbre");
        c.gridx = 3;
        c.gridy = 5;
        c.weightx = 0.5;
        c.gridwidth = 1;
        pane1.add(buttonLoad, c);

        JLabel labelFilenameCSV = new JLabel("Nom du fichier CSV : ");
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(labelFilenameCSV, c);

        txtFileCSV = new JTextField("steam_games.csv", 7);
        c.gridx = 1;
        c.gridy = 6;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(txtFileCSV, c);

        buttonLoadCSV = new JButton("Charger le CSV");
        c.gridx = 2;
        c.gridy = 6;
        c.weightx = 1;
        c.gridwidth = 2;
        pane1.add(buttonLoadCSV, c);

        JLabel labelRecherche = new JLabel("Recherche : ");
        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(labelRecherche, c);

        txtId = new JTextField("2", 7);
        c.gridx = 1;
        c.gridy = 7;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(txtId, c);

        buttonSearchSeq = new JButton("Recherche séquencielle");
        c.gridx = 2;
        c.gridy = 7;
        c.weightx = 0.5;
        c.gridwidth = 1;
        pane1.add(buttonSearchSeq, c);

        buttonSearchIndex = new JButton("Recherche par index");
        c.gridx = 3;
        c.gridy = 7;
        c.weightx = 0.5;
        c.gridwidth = 1;
        pane1.add(buttonSearchIndex, c);

        JLabel labelRechercheMany = new JLabel("Recherche de x valeurs : ");
        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(labelRechercheMany, c);

        txtSearchMany = new JTextField("100", 7);
        c.gridx = 1;
        c.gridy = 8;
        c.weightx = 1;
        c.gridwidth = 1;
        pane1.add(txtSearchMany, c);

        buttonSearchMany = new JButton("Rechercher x valeurs ");
        c.gridx = 2;
        c.gridy = 8;
        c.weightx = 1;
        c.gridwidth = 2;
        pane1.add(buttonSearchMany, c);

        buttonClean = new JButton("Reset");
        c.gridx = 2;
        c.gridy = 9;
        c.weightx = 1;
        c.gridwidth = 2;
        pane1.add(buttonClean, c);

        buttonRefresh = new JButton("Refresh");
        c.gridx = 2;
        c.gridy = 10;
        c.weightx = 1;
        c.gridwidth = 2;
        pane1.add(buttonRefresh, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 400;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.gridwidth = 4;   //2 columns wide
        c.gridx = 0;
        c.gridy = 11;

        JScrollPane scrollPane = new JScrollPane(tree);
        pane1.add(scrollPane, c);

        tree.setModel(new DefaultTreeModel(null));
        tree.updateUI();

        /**
         * Les écouteurs
         */
        txtNbreItem.addActionListener(this);
        buttonAddItem.addActionListener(this);
        buttonAddMany.addActionListener(this);
        buttonLoad.addActionListener(this);
        buttonSave.addActionListener(this);
        buttonRemove.addActionListener(this);
        buttonClean.addActionListener(this);
        buttonRefresh.addActionListener(this);
        buttonLoadCSV.addActionListener(this);
        buttonSearchSeq.addActionListener(this);
        buttonSearchIndex.addActionListener(this);
        buttonSearchMany.addActionListener(this);

        return pane1;
    }
}

