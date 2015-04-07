package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import models.*;


public class MakeRosterPanel extends JPanel implements ActionListener {
	
	private String user;
	private String rosterName;
	private List<MockPlayer> playersList = new ArrayList<>();
	private List<MockPlayer> finalRoster = new ArrayList<>();
	private DefaultListModel<MockPlayer> rosterModel = new DefaultListModel<>();
	private JList<MockPlayer> roster = new JList<MockPlayer>(rosterModel);
	private JList<MockPlayer> players = new JList<MockPlayer>();
	private int financialStatus;
	
	

	public MakeRosterPanel() {
		financialStatus = Game.STARTING_CASH;
		setLayout(null);
		JButton selectBtn = new JButton("Select player");
		selectBtn.addActionListener(this);
		JButton deselectBtn = new JButton("Deselect player");
		deselectBtn.addActionListener(this);
		JTextField userName = new JTextField("nafn notanda");
		userName.addActionListener(new TextFieldListener(user));
		JTextField rstrName = new JTextField("nafn liðs");
		rstrName.addActionListener(new TextFieldListener(rosterName));
		Box nameBox = Box.createVerticalBox();
		nameBox.setBounds(50, 50, 150, 60);
		add(nameBox);
		nameBox.add(userName);
		nameBox.add(rstrName);
		
		Box rosterBox = Box.createHorizontalBox();
		rosterBox.setBounds(50,150,550,300);
		add(rosterBox);
		
		
		Box rosterBoxL = Box.createVerticalBox();
		Box rosterBoxR = Box.createVerticalBox();
		
		
		rosterBox.add(rosterBoxL);
		rosterBox.add(rosterBoxR);

		//test stuff
		MockTeam team1 = new MockTeam("Liverpool");
		for(int i=0; i<20; i++) {
			team1.addPlayer(new MockPlayer("LiverpoolPlayer" + i, "Liverpool", Roster.positions[i%4], 100*(i%6 + 1)));
		}
		MockTeam team2 = new MockTeam("Manchester United");
		for(int i=0; i<20; i++) {
			team2.addPlayer(new MockPlayer("UnitedPlayer" + i, "Manchester United", Roster.positions[(i+1)%4], 100*(i%7 + 1)));
		}

		playersList = team1.getPlayers();
		
		players = new JList<MockPlayer>(playersList.toArray(new MockPlayer[playersList.size()]));
		
		JScrollPane rosterScroll = new JScrollPane();
		JScrollPane playersScroll = new JScrollPane();
		
		rosterScroll.getViewport().add(roster);
		playersScroll.getViewport().add(players);
		playersScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		players.setCellRenderer(new PlayerCellRender());
		players.setVisibleRowCount(15);
		roster.setCellRenderer(new PlayerCellRender());
		roster.setVisibleRowCount(15);
		
		players.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		roster.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		

		rosterBoxL.add(rosterScroll);
		rosterBoxL.add(deselectBtn);
		deselectBtn.setAlignmentX(CENTER_ALIGNMENT);
		rosterBoxR.add(playersScroll);
		rosterBoxR.add(selectBtn);
		selectBtn.setAlignmentX(CENTER_ALIGNMENT);
		
		

		
	} 
	
	
	public static class PlayerCellRender extends JPanel implements ListCellRenderer{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JLabel left, right;
	
		PlayerCellRender() {
			GridLayout layout = new GridLayout(1, 2);
			layout.setHgap(10);
			setLayout(layout);
			left = new JLabel();
			right = new JLabel();
			left.setOpaque(true);
			right.setOpaque(true);
			add(left);
			add(right);
		}
		
		public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus){ 
			String leftData = ((MockPlayer)value).getName();
			String rightData = ((MockPlayer)value).getPrice().toString();
			left.setText(leftData);
			right.setText(rightData);
			right.setHorizontalTextPosition(JLabel.RIGHT);
			if(isSelected){
				left.setBackground(list.getSelectionBackground());
				left.setForeground(list.getSelectionForeground());
				right.setBackground(list.getSelectionBackground());
				right.setForeground(list.getSelectionForeground());
			} else {
				left.setBackground(list.getBackground());
				left.setForeground(list.getForeground());
				right.setBackground(list.getBackground());
				right.setForeground(list.getForeground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			return this;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton)e.getSource();
			if(btn.getText().equals("Select player")) {
				ArrayList<MockPlayer> rosterList = Collections.list(rosterModel.elements());
				if(Roster.isPartlyLegal(rosterList, players.getSelectedValue(), financialStatus) && !rosterList.contains(players.getSelectedValue())) {
					rosterModel.addElement(players.getSelectedValue());
					financialStatus -= players.getSelectedValue().getPrice();
					sortRoster();
				}
			} else {
				financialStatus += roster.getSelectedValue().getPrice();
				rosterModel.removeElement(roster.getSelectedValue());
			}
		}
	}
	
	public static class TextFieldListener implements ActionListener {
		private String text;
		
		public TextFieldListener(String text) {
			this.text = text;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JTextField) {
				JTextField field = (JTextField)e.getSource();
				text = field.getText();
			}
		}
	}
	
	public void sortRoster() {
		List<MockPlayer> rosterList = new ArrayList<MockPlayer>(Collections.list(rosterModel.elements()));
		rosterModel.removeAllElements();
		for(PlayerPosition pos : Roster.positions) {
			for(MockPlayer player : rosterList) {
				if(player.getPosition() == pos)
					rosterModel.addElement(player);
			}
		}
	}
	
	public int getFinancialStatus() {
		return financialStatus;
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(800,800);
		JPanel panel = new MakeRosterPanel();
		frame.add(panel);
		frame.setVisible(true);
		frame.setContentPane(panel);
		
	}
}
	

