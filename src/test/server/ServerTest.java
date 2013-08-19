package test.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.Server;

public class ServerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Server server = new Server();
		server.run();
	}

}
