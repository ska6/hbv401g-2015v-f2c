package tests;

import org.junit.*;
import static org.junit.Assert.*;

import models.*;
import java.util.*;

public class LegalPurchaseTest {
	
	private static Game testGame;
	
	@Before
	public void setUp() {
		testGame = BasicEntities.generateGame();
	}
	
	@After
	public void tearDown() {
		testGame = null;
	}

	@Test
	public void legalPurchaseTest() {
		List<MockPlayer> in = new ArrayList<>();
		List<MockPlayer> out = new ArrayList<>();
		// No actual purchase
		assertEquals(true, User.isBuyLegal(testGame.getUsers().get(0), in, out));
		// Actual legal purchase
		in = testGame.getTeams().get(0).getPlayersByPosition(PlayerPosition.DEFENCE).subList(0, 3);
		out = testGame.getUsers().get(0).getRoster().getPlayersByPosition(PlayerPosition.DEFENCE).subList(0, 3);
		assertEquals(true, User.isBuyLegal(testGame.getUsers().get(0), in, out));
		// Incorrect amount of a position
		in = testGame.getTeams().get(0).getPlayersByPosition(PlayerPosition.GOAL).subList(0, 3);
		out = testGame.getUsers().get(0).getRoster().getPlayersByPosition(PlayerPosition.DEFENCE).subList(0, 3);
		assertEquals(false, User.isBuyLegal(testGame.getUsers().get(0), in, out));
		// Too expensive players
		in = new ArrayList<>();
		in.add(testGame.getTeams().get(0).getPlayers().get(testGame.getTeams().get(0).getPlayers().size()-1));
		out = testGame.getUsers().get(0).getRoster().getPlayers().subList(6, 7);
		assertEquals(false, User.isBuyLegal(testGame.getUsers().get(0), in, out));
		// Too large lists
		in = testGame.getTeams().get(0).getPlayersByPosition(PlayerPosition.DEFENCE).subList(0, 4);
		out = testGame.getUsers().get(0).getRoster().getPlayersByPosition(PlayerPosition.DEFENCE).subList(0, 4);
		assertEquals(false, User.isBuyLegal(testGame.getUsers().get(0), in, out));
		// Lists not of equal length
		in = testGame.getTeams().get(0).getPlayersByPosition(PlayerPosition.DEFENCE).subList(0, 2);
		out = testGame.getUsers().get(0).getRoster().getPlayersByPosition(PlayerPosition.DEFENCE).subList(0, 3);
		assertEquals(false, User.isBuyLegal(testGame.getUsers().get(0), in, out));
	}
	
	
}
	