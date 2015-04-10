package views;

import javax.swing.*;
import java.awt.*;
import views.*;
import tests.*;
import models.*;


public class NavigationBar extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	

	public NavigationBar(Game game) {
		JPanel MyRosterView = new MakeRosterPanel();
		addTab("My Roster",MyRosterView);
		JPanel StandingsView = new StandingsView(game);
		addTab("Standings",StandingsView);
	}
	
	public static void main(String[] args){
		Game game = BasicEntities.generateGame();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(900,700);
		JTabbedPane panel = new NavigationBar(game);
		frame.add(panel);
		frame.setVisible(true);
		frame.setContentPane(panel);
	}

}