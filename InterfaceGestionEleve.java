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
	 * var1, var2, var3, var4 correspondent respectivement au Numero Eleve, Nom Eleve, Pr�nom Elve et le Sexe de l'Eleve
	 * text1, text2, text3 correspondent respectivement a la zone de saisie de Num�ro Elve, Nom Eleve et Pr�nom Eleve
	 * box correspond � la liste de Sexe Masculin ou Feminin pr�d�fini dans la var4
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
	 * Cr�ation de la fenetre et mise en place des �l�ments
	 * Affichage des �l�ments
	 * b1 et b2 sont pr�d�fini comme �tant impossible � cliquer
	 * Ajout de Listener sur les diff�rents �l�ments permettant de r�aliser des �v�nements lors de la siasie de l'utilisateur
	 */
	InterfaceGestionEleve() {

		setVisible(true);		
		setTitle("Gestion �l�ve");
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
	 * @param event correspond � l'�v�nement r�alis� lorsque l'utilisateur clique sur l'un des bouttons
	 */
	public void actionPerformed ( ActionEvent event) {

		/**
		 * @param event correspond � l'�v�nement du Boutton Effacer
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
		 * @param event correspond � l'�v�nement du Boutton Ajouter
		 * Il y a d'abord le test du sexe de l'�l�ve
		 * Il va y avoir la lecture du fichier que l'on veut modifier pour lire les donn�es existantes, et voir si l'ajout de l'�l�ve est possible.
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
						JOptionPane.showMessageDialog(null, "Num�ro d'�l�ve d�ja existant","Erreur de saisie", JOptionPane.ERROR_MESSAGE);
					}
				}

				br.close();

				if(!trouver) {
					bw = new BufferedWriter (new FileWriter ("eleve.txt", true));

					bw.write(ligne);
					bw.newLine();
					bw.close();
					JOptionPane jop= new JOptionPane();
					JOptionPane.showMessageDialog(null, "Eleve ajout� avec succ�s"," saisie r�ussie", JOptionPane.INFORMATION_MESSAGE);
				}
			}

			catch (FileNotFoundException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @param event correspond � l'�v�nement du boutton Chercher
		 * Il va y avoir la lecture des donn�es mot par mot et v�rifi� si les conditions sont remplis.
		 * Dans ce cas Le Nom, Pr�nom et le sexe de l'�l�ve s'afficheront dans le formulaire de Gestion des �l�ves
		 * Sinon Un message d'erreur nous signal que l'�l�ve n'existe pas
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
					JOptionPane.showMessageDialog(null, "Eleve non trouv�","Erreur de recherche", JOptionPane.ERROR_MESSAGE);
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
	 * Appel� lorsqu'un �l�ment a �t� mis en place.
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
	 * Appel� lorsqu'un �l�ment a �t� utilis�.
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
	 * Appel� lorsqu'un �l�ment a �t� utilis�.
 	 * Cet �v�nement se produit lorsqu'une touche est suivie d'une version de cl�.
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