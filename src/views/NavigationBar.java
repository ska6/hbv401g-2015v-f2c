package views;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import views.*;
import tests.*;
import models.*;


public class NavigationBar extends JTabbedPane implements ChangeListener {

	private static final long serialVersionUID = 1L;
	private MyRosterView myRosterView;
	private MarketView marketView;
	private StandingsView standingsView;
	private StatisticsView statisticsView;
	private ScheduleView scheduleView;
	

	public NavigationBar(Game game) {
		myRosterView = new MyRosterView(game);
		addTab("My Roster", myRosterView);
		marketView = new MarketView(game);
		addTab("Market", marketView);
		standingsView = new StandingsView(game);
		addTab("Standings",standingsView);
		statisticsView = new StatisticsView(game);
		addTab("Statistics", statisticsView);
		scheduleView = new ScheduleView(game);
		addTab("Schedule", scheduleView);

		addChangeListener(this);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		myRosterView.refresh();
	}
	
	public MyRosterView getMyRosterView() {
		return myRosterView;
	}

	public void setMyRosterView(MyRosterView myRosterView) {
		this.myRosterView = myRosterView;
	}

	public MarketView getMarketView() {
		return marketView;
	}

	public void setMarketView(MarketView marketView) {
		this.marketView = marketView;
	}

	public StandingsView getStandingsView() {
		return standingsView;
	}

	public void setStandingsView(StandingsView standingsView) {
		this.standingsView = standingsView;
	}

	public StatisticsView getStatisticsView() {
		return statisticsView;
	}

	public void setStatisticsView(StatisticsView statisticsView) {
		this.statisticsView = statisticsView;
	}

	public ScheduleView getScheduleView() {
		return scheduleView;
	}

	public void setScheduleView(ScheduleView scheduleView) {
		this.scheduleView = scheduleView;
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