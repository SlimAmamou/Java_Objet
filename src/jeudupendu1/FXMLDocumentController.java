/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudupendu1;

import java.time.Duration;
import java.time.Instant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Slim
 */
public class FXMLDocumentController  {
    @FXML
    private Canvas DessinJeu;
    
    @FXML
    private Label chronos;   
    
    @FXML
    private TextField NomJoueur;

    @FXML
    private Label AfficheScore;

    @FXML
    private Label AfficheMot;

    @FXML
    private Label AfficheInst;
    
    
    // Vriables compteurs , commende de jeu(bool) , mots et lettres choisies.
    private boolean jeu;
    String selectedLetters  ;
    String MotJeu;
    String AffMotCache ;
    int pointage = 0 ;
    int winpoints;
    int loosepoints;
    Instant debut;
   
    
      //  DÉBUTER PARTIE    
    @FXML
    void startGame(ActionEvent event) throws StringIndexOutOfBoundsException{
        // instant de départ.
        debut = Instant.now();
        
        
        // Netoyage canevas avant l'entame de chaque partie
        GraphicsContext gc = DessinJeu.getGraphicsContext2D();
        gc.clearRect(0, 0, 350, 400);      
        
        // Liste des caractères choisie par jeu vidé à chaque début.
        selectedLetters = "-" ;
        
        // Initialisation compteurs de jeu.
        winpoints = 0;
        loosepoints = 0;
        
        // Liste de mots à trouver
        String [] Mots = {"Camionette", "Printemps", "Toucher", "Tableau", "Lune", "Enfant", "Magicien", "Bonbon", "Chocolat", "Livre"};
        
        // Index mot à trouver
        int indice =(int) (Math.random()*10);
        
        // Mot à trouver
        MotJeu = Mots[indice];
       
        // Initiatisation jeu à chaque début de partie
       try{
           String NomDuJoueur = NomJoueur.getText();
           if ("".equals(NomDuJoueur)) throw new StringIndexOutOfBoundsException();    // Afficher la potence.
            Dessin pendu = new Dessin(gc, 0);
            pendu.dessiner();
            chronos.setText("60");                                                      // Affichage chrono
            
           AfficheInst.setText("choisissez une lettre");                               // Affichage Message de jeu
           AfficheScore.setText(String.valueOf(pointage));                // Affichage Pointage Général
           
           AffichageCaché Motcache = new AffichageCaché(MotJeu);          // Objet mot à deviner en mode caché
           AffMotCache = Motcache.Cacher();
           AfficheMot.setText(AffMotCache);
           jeu = true;                                                    // permettre le demarrage du jeu
       }
        catch(StringIndexOutOfBoundsException e){ // Prevention ENTRÉE NOM
            Alert nom = new Alert(Alert.AlertType.ERROR,"Il est impératif d'entrer un nom pour jouer", ButtonType.OK);
            nom.show();
       }
    }
    
    @FXML
    void SelectLetter(ActionEvent event) throws Exception{
        // Instant courant
        Instant maintenant = Instant.now();
            
        // Durée 
        Duration durée = Duration.between(debut, maintenant);
        long tempRestant = 60 - durée.getSeconds(); 
        String chr = String.valueOf(tempRestant);
        if(tempRestant>=0)chronos.setText(chr);
            
        // Temps limite
            
        else{
            chronos.setText("00");
            jeu = false;
            AfficheInst.setText("Temps Écoulé"); 
            
        }
        
        boolean lettre = true;
        while(jeu && lettre){ 
            //verif (boolean) lettres sélectionnées            
            boolean validLetter = true;
            
            
            // Récuperation lettre selectionnée.
            Button monBouton = (Button)event.getSource();
            String SelectedLetter;
            SelectedLetter = monBouton.getText();
            
            // verification si ls lettre n'a pas déjà été sélectionnée.
            for(int i = 0; i< selectedLetters.length(); i++){
                char comp = selectedLetters.charAt(i);
                String playedletter = String.valueOf(comp);
                if(SelectedLetter.equals(playedletter)){
                    validLetter = false;
                }
            }
            try{
                if(!validLetter) throw new Exception();  // blocage lettre déjà séléctionnée.
                selectedLetters+=SelectedLetter;
                
                int longueurMot = MotJeu.length();
                boolean NonTrouve = true;
                
                // Verification si la lettre sélectionnée appartient au mot à chercher.
                // Inplémentation du mot à afficher ainsi qu:à tout les compteurs de points.                
                for(int i = 0; i < longueurMot; i++){
                    char vrf =MotJeu.charAt(i);
                    String verifLetter = String.valueOf(vrf);
                    if(SelectedLetter.equalsIgnoreCase(verifLetter)){
                        AffMotCache = AffMotCache.substring(0, i) + verifLetter + AffMotCache.substring(i+1);
                        NonTrouve = false;
                        pointage += 1;
                        winpoints +=1;
                        AfficheInst.setText("Excellent choix");                               // Affichage resultat de la lettre si ok

                    }
                }
                if(NonTrouve){
                    loosepoints ++;                                                 // Cas où la lettre choisie ne se trouve pas dans le mot.
                    AfficheInst.setText("Mauvais choix");                               // Affichage resultat de la lettre si non correspondante

                }  
                
                   // MOT TROUVÉ
                   
                if (winpoints == longueurMot){
                    jeu = false;
                    pointage += 5;
                    AfficheInst.setText("Mot trouvé");
                }
                
                    // bonhomme PENDU  (échec)
                    
                if (loosepoints == 6){
                    jeu = false;
                    AfficheInst.setText("PENDU");
                    
                }

                AfficheMot.setText(AffMotCache);                   // Affichage lettres trouvées
                AfficheScore.setText(String.valueOf(pointage));    // Affichage Score
                
                
            }
            catch(Exception e){ // Prevention lettre sélectionnée.
                Alert nom = new Alert(Alert.AlertType.INFORMATION,"vous avez déjà joué la lettre : " + SelectedLetter, ButtonType.OK);
                nom.show();
            }
            lettre = false ;            // Arrêter la verification jusqu'à la prochaine séléction de lettre
            
            //  Affichage de la pondaison.
            GraphicsContext gc = DessinJeu.getGraphicsContext2D();
            Dessin pendu = new Dessin(gc, loosepoints);
            pendu.dessiner();
        
        } 
    }
    
    // Quitter Partie ==> Re-initialiser score, affichage et nom ==> Netoyage canvas..
     @FXML
    void QuitterPartie(ActionEvent event) {
        pointage = 0;
        jeu = false;
        chronos.setText("--");
        AfficheInst.setText("Entrez votre nom pour recommencer");
        AfficheMot.setText("----");
        AfficheScore.setText("00");
        GraphicsContext gc = DessinJeu.getGraphicsContext2D();
        gc.clearRect(0, 0, 350, 400);
        NomJoueur.clear();
        NomJoueur.requestFocus();
    }
    
    @FXML
    void GetInstructions(ActionEvent event) {
        String Apropos = "\nEntrez votre nom pour pouvoir commencer.\n"
                + "- Selectionnez une lettre.\n"
                + "- Si la lettre choisie appartient à notre mot \n vous aurez 1 point et vous verrez la lettre \n apparaitre.\n"
                + "- Si la lettre choisie n'appartient pas à notre \n mot le bonhomme sera un peut plus\n pendu.\n"
                + "- Pour gagner il faut deviner le mot en moins \n de six coups perdants et moin de 1mn.\n"
                + "- Vous obtiendrez 5 points par mot trouvé.\n"
                + "- À chaque sélection de lettre vous verrez\n"
                + " s'afficher le temps testant en haut."
                + "- Suivez les instruction en bas pour une \n meilleure fluiditée de jeu.";
        
        GraphicsContext gc = DessinJeu.getGraphicsContext2D();
        gc.clearRect(0, 0, 350, 400);
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.strokeText(Apropos, 50, 50);
        
    }
}