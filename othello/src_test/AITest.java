import static org.junit.Assert.*;

import org.junit.Test;

public class AITest {

	@Test
	public void test() {
		// This is just an example test to show JUnit working. It won't be useful
		// for your real implementation.
		AI ai = new AI();
		GameState state = new GameState();
		state.setPlayer(1);
		state.setBoard(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0},
				                   {0, 0, 0, 0, 0, 0, 0, 0},
				                   {0, 0, 0, 0, 0, 0, 0, 0},
				                   {0, 0, 0, 1, 2, 0, 0, 0},
				                   {0, 0, 0, 2, 1, 0, 0, 0},
				                   {0, 0, 0, 0, 0, 0, 0, 0},
				                   {0, 0, 0, 0, 0, 0, 0, 0},
				                   {0, 0, 0, 0, 0, 0, 0, 0}});
		
		// Our first canned move is [2,4]
		assertArrayEquals(new int[]{5, 4}, ai.computeMove(state));

		// Our second canned move is [3, 5]
		//assertArrayEquals(new int[]{3, 5}, ai.computeMove(state));
	}
}
