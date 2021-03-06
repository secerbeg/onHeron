package com.secerbeg.matches.events.engine;

import com.secerbeg.matches.events.AbstractEvent;
import com.secerbeg.matches.events.EventObserver;
import com.secerbeg.matches.model.GameState;

/**
 * When the 'back to menu' was pressed.
 *
 * @author Mirza Secerbegovic
 */
public class GameWonEvent extends AbstractEvent
{
	public static final String TYPE = GameWonEvent.class.getName();

	public GameState gameState;

	
	public GameWonEvent(GameState gameState)
	{
		this.gameState = gameState;
	}

	@Override
	protected void fire(EventObserver eventObserver)
	{
		eventObserver.onEvent(this);
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

}
