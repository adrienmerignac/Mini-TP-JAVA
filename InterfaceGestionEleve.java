package projet;
/**
 * @author Adrien Merignac
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.StringTokenizer;

public class InterfaceGestionEleve extends JFrame implements KeyListener, ActionListener {


	/**
	 * var1, var2, var3, var4 correspondent respectivement au Numero Eleve, Nom Eleve, Prénom Elve et le Sexe de l'Eleve
	 * text1, text2, text3 correspondent respectivement a la zone de saisie de Numéro Elve, Nom Eleve et Prénom Eleve
	 * box correspond à la liste de Sexe Masculin ou Feminin prédéfini dans la var4
	 * b1, b2, b3 correspondent respectivement au Boutton Chercher, Ajouter et Effacer un Eleve 
	 */
	private JLabel var1 = new JLabel ("Numero :");
	private JLabel var2 = new JLabel ("Nom :");
	private JLabel var3 = new JLabel ("Prenom :");
	private JLabel var4 = new JLabel ("Sexe :");
	private JTextField text1 = new JTextField ();
	private JTextField text2 = new JTextField ();
	private JTextField text3 = new JTextField ();
	private JComboBox box= new JComboBox();
	private JButton b1 = new JButton ("Chercher");
	private JButton b2 = new JButton ("Ajouter");
	private JButton b3= new JButton ("Effacer");


	
	/**
	 * Constructeur de l'interface de Gestion des Eleves
	 * Création de la fenetre et mise en place des éléments
	 * Affichage des éléments
	 * b1 et b2 sont prédéfini comme étant impossible à cliquer
	 * Ajout de Listener sur les différents éléments permettant de réaliser des évènements lors de la siasie de l'utilisateur
	 */
	InterfaceGestionEleve() {

		setVisible(true);		
		setTitle("Gestion élève");
		setSize(400, 300);
		setLocationRelativeTo(null);

		var1.setBounds(20, 20, 150, 20);
		var2.setBounds(20, 50, 150, 20);
		var3.setBounds(20, 80, 150, 20);
		var4.setBounds(20, 110, 150, 20);
		text1.setBounds(100,20, 150, 20);
		text2.setBounds(100, 50, 150, 20);
		text3.setBounds(100, 80, 150, 20);
		box.setBounds(100, 110, 150, 20);
		box.addItem("masculin");
		box.addItem("feminin");

		b1.setBounds(20, 160, 150, 20);
		b2.setBounds(200, 160, 150, 20);
		b3.setBounds(20, 200, 150, 20);

		getContentPane().add(var1);
		getContentPane().add(var2);
		getContentPane().add(var3);
		getContentPane().add(var4);
		getContentPane().add(text1);
		getContentPane().add(text2);
		getContentPane().add(text3);
		getContentPane().add(box);
		getContentPane().add(b1);
		getContentPane().add(b2);
		getContentPane().add(b3);

		b1.setEnabled(false);
		b2.setEnabled(false);
		text1.addKeyListener(this);
		text2.addKeyListener(this);
		text3.addKeyListener(this);
		b1.addActionListener(this); 
		b2.addActionListener(this); 
		b3.addActionListener(this); 

		this.setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}		

	/**
	 * @param event correspond à l'évènement réalisé lorsque l'utilisateur clique sur l'un des bouttons
	 */
	public void actionPerformed ( ActionEvent event) {

		/**
		 * @param event correspond à l'évènement du Boutton Effacer
		 * Lorsque l'utilisateur clique sur ce boutton, les zones de textes se vident, et les bouttons redeviennent non cliquables
		 */
		if(event.getSource()==b3) {
			text1.setText(null);
			text2.setText(null);
			text3.setText(null);
			b1.setEnabled(false);
			b2.setEnabled(false);
		}	

		/**
		 * @param event correspond à l'évènement du Boutton Ajouter
		 * Il y a d'abord le test du sexe de l'élève
		 * Il va y avoir la lecture du fichier que l'on veut modifier pour lire les données existantes, et voir si l'ajout de l'élève est possible.
		 */
		if(event.getSource()==b2) {

			BufferedReader br = null;
			String ligne = null;
			String ligne1;
			BufferedWriter bw = null;
			int indice= box.getSelectedIndex();
			String sexe;


			if(indice==0) {
				sexe= "masculin";
			}
			else {
				sexe="feminin";
			}

			ligne= text1.getText()+" "+text2.getText()+" "+text3.getText()+" "+sexe;

			boolean trouver= false;

			try {

				br = new BufferedReader ( new FileReader ("eleve.txt"));


				while (((ligne1=br.readLine()) != null)&& (!trouver)) {

					StringTokenizer st= new StringTokenizer(ligne1);
					String n= st.nextToken();

					if(n.compareTo(text1.getText())==0) {
						trouver= true;
						JOptionPane jop= new JOptionPane();
						JOptionPane.showMessageDialog(null, "Numéro d'élève déja existant","Erreur de saisie", JOptionPane.ERROR_MESSAGE);
					}
				}

				br.close();

				if(!trouver) {
					bw = new BufferedWriter (new FileWriter ("eleve.txt", true));

					bw.write(ligne);
					bw.newLine();
					bw.close();
					JOptionPane jop= new JOptionPane();
					JOptionPane.showMessageDialog(null, "Eleve ajouté avec succès"," saisie réussie", JOptionPane.INFORMATION_MESSAGE);
				}
			}

			catch (FileNotFoundException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @param event correspond à l'évènement du boutton Chercher
		 * Il va y avoir la lecture des données mot par mot et vérifié si les conditions sont remplis.
		 * Dans ce cas Le Nom, Prénom et le sexe de l'élève s'afficheront dans le formulaire de Gestion des élèves
		 * Sinon Un message d'erreur nous signal que l'élève n'existe pas
		 */
		if(event.getSource()==b1) {

			BufferedReader br = null;
			String ligne1;

			boolean trouver= false;

			try {

				br = new BufferedReader ( new FileReader ("eleve.txt"));

				String n= null;
				String n1= null;
				String n2= null;
				String n3= null;

				while (((ligne1=br.readLine()) != null)&& (!trouver)) {

					StringTokenizer st= new StringTokenizer(ligne1);
					n= st.nextToken();
					n1= st.nextToken();
					n2= st.nextToken();
					n3= st.nextToken();

					if(n.compareTo(text1.getText())==0) {
						trouver = true;
					}
				}				

				if (trouver) {

					text2.setText(n1);
					text3.setText(n2);

					if(n3.compareTo("masculin")==0) {

						box.setSelectedIndex(0);
					}
					else {

						box.setSelectedIndex(1);
					}
				}
				else {
					JOptionPane jop= new JOptionPane();
					JOptionPane.showMessageDialog(null, "Eleve non trouvé","Erreur de recherche", JOptionPane.ERROR_MESSAGE);
				}
			}

			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param ke
	 * Appelé lorsqu'un élément a été mis en place.
	 */
	public void keyReleased( KeyEvent ke)
	{
		if(text1.getText().isEmpty()) {
			b2.setEnabled(false);
			b1.setEnabled(false);
		}
		else {
			b1.setEnabled(true);
			if(text2.getText().isEmpty() || text3.getText().isEmpty()) {
				b2.setEnabled(false);
			}
			else {
				b2.setEnabled(true);
			}
		}
	}

	/**
	 * @param kp
	 * Appelé lorsqu'un élément a été utilisé.
	 */
	public void keyPressed( KeyEvent kp)
	{
		if(!text1.getText().isEmpty()) {
			b2.setEnabled(false);
			b1.setEnabled(false);
		}
		else {
			b1.setEnabled(true);
			if(text2.getText().isEmpty() || text3.getText().isEmpty()) {
				b2.setEnabled(false);
			}
			else {
				b2.setEnabled(true);
			}
		}
	}

	/**
	 * Appelé lorsqu'un élément a été utilisé.
 	 * Cet événement se produit lorsqu'une touche est suivie d'une version de clé.
	 */
	public void keyTyped( KeyEvent kt)
	{
		if(!text1.getText().isEmpty()) {
			b2.setEnabled(false);
			b1.setEnabled(false);
		}
		else {
			b1.setEnabled(true);
			if(text2.getText().isEmpty() || text3.getText().isEmpty()) {
				b2.setEnabled(false);
			}
			else {
				b2.setEnabled(true);
			}
		}
	}	
}