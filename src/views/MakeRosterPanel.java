package views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.event.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import tests.BasicEntities;
import models.*;


public class MakeRosterPanel extends JPanel implements ActionListener {
	
	private Game game;
	private String user;
	private String rosterName;
	JTextField userName,rstrName;
	private List<MockPlayer> playersList = new ArrayList<>();
	private DefaultListModel<MockPlayer> rosterModel = new DefaultListModel<>();
	private JList<MockPlayer> roster = new JList<MockPlayer>(rosterModel);
	private JList<MockPlayer> players = new JList<MockPlayer>();
	private int financialStatus;
	private JLabel currentFinancialStatus = new JLabel();
	

	public MakeRosterPanel(Game game) {
		this.game = game;
		financialStatus = Game.STARTING_CASH;
		setLayout(null);
		JButton selectBtn = new JButton("Select player");
		selectBtn.addActionListener(this);
		JButton deselectBtn = new JButton("Deselect player");
		deselectBtn.addActionListener(this);
		userName = new JTextField("User name");
		userName.getDocument().addDocumentListener(new TextFieldListener(user, userName));
		rstrName = new JTextField("Team name");
		rstrName.getDocument().addDocumentListener(new TextFieldListener(rosterName, rstrName));
		Box nameBox = Box.createVerticalBox();
		nameBox.setBounds(50, 50, 150, 60);
		add(nameBox);
		nameBox.add(userName);
		nameBox.add(rstrName);
		
		Box financeStatusBox = Box.createHorizontalBox();
		financeStatusBox.setBounds(350, 50, 250, 60);
		add(financeStatusBox);
		
		JLabel financeLbl = new JLabel("Financial Status: ");
		currentFinancialStatus.setText(""+financialStatus);
		financeStatusBox.add(financeLbl);
		financeStatusBox.add(currentFinancialStatus);
		
		Box rosterBox = Box.createHorizontalBox();
		rosterBox.setBounds(50,150,700,300);
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

		playersList = game.getPlayers();
		
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
	
	public String getUserName(){
		return user;
	}
	
	public void updateUserName() {
		user = userName.getText();
	}
	
	public String getRosterName(){
		return rosterName;
	}
	
	public void updateRosterName() {
		rosterName = rstrName.getText();
	}
	
	public Roster getRoster(){
		List<MockPlayer> list = new ArrayList<>();
		for(int i=0;i<rosterModel.getSize();i++ ){
			list.add(rosterModel.getElementAt(i));
		}
		Roster roster = new Roster(getRosterName());
		roster.setPlayers(list);
		return roster;
	}
	
	public void setUserName(String name){
		user=name;
	}
	
	public void setRosterName(String name){
		rosterName=name;
	}
	
	public User getUser() {
		User u = new User(user);
		Roster r = new Roster(rosterName);
		ArrayList<MockPlayer> rosterList = Collections.list(rosterModel.elements());
		r.setPlayers(rosterList);
		u.setRoster(r);
		u.setFinancialStatus(financialStatus);
		return u;
	}
	
	public void setActiveUser() {
		User u1 = game.getUsers().get(game.getActiveUser());
		User u2 = getUser();
		u1.setUserName(u2.getUserName());
		u1.setRoster(u2.getRoster());
		u1.setUserScore(new Integer(0));
		u1.setFinancialStatus(u2.getFinancialStatus());
		u1.setUserStats(new UserStats());
	}
	
	public static class PlayerCellRender extends JPanel implements ListCellRenderer{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JLabel left, middle, right;
	
		public PlayerCellRender() {
			GridLayout layout = new GridLayout(1, 2);
			layout.setHgap(10);
			setLayout(layout);
			left = new JLabel();
			Dimension d = left.getPreferredSize();
			//middle = new JLabel();
			//middle.setMaximumSize(new Dimension(10, d.height));
			right = new JLabel();
			right.setPreferredSize(new Dimension(10, d.height));
			left.setOpaque(true);
			//middle.setOpaque(true);
			right.setOpaque(true);
			add(left);
			//add(middle);
			add(right);
		}
		
		public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus){ 
			String leftData = ((MockPlayer)value).getName();
			//String middleData = ((MockPlayer)value).getPosition().name().substring(0,1);
			String rightData = ((MockPlayer)value).getPosition().name().substring(0,1)+"   "+((MockPlayer)value).getPrice().toString();
			left.setText(leftData);
			//middle.setText(middleData);
			//middle.setHorizontalTextPosition(JLabel.CENTER);
			right.setText(rightData);
			right.setHorizontalTextPosition(JLabel.RIGHT);
			if(isSelected){
				left.setBackground(list.getSelectionBackground());
				left.setForeground(list.getSelectionForeground());
				//middle.setBackground(list.getSelectionBackground());
				//middle.setForeground(list.getSelectionForeground());
				right.setBackground(list.getSelectionBackground());
				right.setForeground(list.getSelectionForeground());
			} else {
				left.setBackground(list.getBackground());
				left.setForeground(list.getForeground());
				//middle.setBackground(list.getBackground());
				//middle.setForeground(list.getForeground());
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
				if(players.getSelectedValue() instanceof MockPlayer && Roster.isPartlyLegal(rosterList, players.getSelectedValue(), financialStatus) && !rosterList.contains(players.getSelectedValue())) {
					rosterModel.addElement(players.getSelectedValue());
					financialStatus -= players.getSelectedValue().getPrice();
					sortRoster();
					currentFinancialStatus.setText(""+financialStatus);
				}
			} else {
				if(roster.getSelectedValue() instanceof MockPlayer){
					financialStatus += roster.getSelectedValue().getPrice();
					rosterModel.removeElement(roster.getSelectedValue());
					currentFinancialStatus.setText(""+financialStatus);
				}
			}
		}
	}
	
	public static class TextFieldListener implements DocumentListener {
		private String text;
		private JTextField textField;
		
		public TextFieldListener(String text, JTextField textField) {
			this.text = text;
			this.textField = textField;
		}
		
		
		@Override
	    public void insertUpdate(DocumentEvent e) {
			text = textField.getText();
	    }
	    @Override
	    public void removeUpdate(DocumentEvent e) {
	    	text = textField.getText();
	    }
	    @Override
	    public void changedUpdate(DocumentEvent e) {
	    	text = textField.getText();
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
		Game game = BasicEntities.generateGame();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(900,700);
		JPanel panel = new MakeRosterPanel(game);
		frame.add(panel);
		frame.setVisible(true);
		frame.setContentPane(panel);
		
	}
}
	

