package projet;
/**
 * @author Adrien Merignac
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Eleve extends JFrame implements ActionListener {
/**
 * var1 et var2 étant le Nom d'utilisateur et le Mot de passe
 * text1 la zone de texte pour var1, text2 la zone de texte pour var2
 * b1 étant le boutton permettant la connexion à l'interface GestionEleve
 */
	private JLabel var1 = new JLabel ("Nom d'utilisateur :");
	private JLabel var2 = new JLabel ("Mot de passe");
	private JTextField text1 = new JTextField ();
	private JTextField text2 = new JTextField ();
	private JButton b1 = new JButton ("Se Connecter");

/**
 * Constructeur de l'interface de connexion
 * Création de la fenetre et mise en place des éléments
 */
	Eleve(){

		setVisible(true);		
		setTitle("Connexion");
		setSize(400, 200);
		setLocationRelativeTo(null);

		var1.setBounds(20, 20, 150, 20);
		var2.setBounds(20, 60, 150, 20);
		text1.setBounds(200, 20, 150, 20);
		text2.setBounds(200, 60, 150, 20);
		b1.setBounds(200, 100, 150, 20);

		getContentPane().add(var1);
		getContentPane().add(var2);
		getContentPane().add(text1);
		getContentPane().add(text2);
		getContentPane().add(b1);

		b1.addActionListener(this); 

		this.setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	static int nbrEssai=0;

/**
 * @param event
 *  étant l'évènement réalisé lorsque l'utilisateur insère un Nom d'utiliseur et un Mot de passe, puis appuie sur le bouton Se Connecter
 */
	public void actionPerformed ( ActionEvent event) {

		String uname=text1.getText();
		String pad=text2.getText();

		if(uname.equals("adrien") && pad.equals("adrien"))
		{
			text1.setText(null);
			text2.setText(null);

			GestionEleve ige= new GestionEleve();
			GestionEleve.main(null);
			this.dispose();
			
		}
		else {
			JOptionPane jop= new JOptionPane();
			JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe erroné(s)","Erreur de connexion", JOptionPane.ERROR_MESSAGE, null);
			text1.setText(null);
			text2.setText(null);

			nbrEssai++;

			if(nbrEssai>=3) {
				this.dispose();
			}
		}
	}
}
