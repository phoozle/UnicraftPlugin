import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import co.unicraft.UnicraftPlayer;

public class UnicraftPlayerTest {
	
	UnicraftPlayer unicraft_player;
	String server = "http://localhost:3000";
	
	@Before
	public void createMinecraftPlayer() {
		unicraft_player = new UnicraftPlayer("phoozle", "cromj008@mymail.unisa.edu.au", null);
	}
	
	@Test
	public void testCreate() {
		int responseCode = unicraft_player.create(server);
		assertEquals(201, responseCode);
	}

	@Test
	public void testIsActive() {
		boolean active = unicraft_player.isActive(server);
		assertTrue(active);
	}
}
