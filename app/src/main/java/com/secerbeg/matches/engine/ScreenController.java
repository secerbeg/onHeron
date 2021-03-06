package com.secerbeg.matches.engine;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.secerbeg.matches.R;
import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.events.ui.ResetBackgroundEvent;
import com.secerbeg.matches.fragments.DifficultySelectFragment;
import com.secerbeg.matches.fragments.GameFragment;
import com.secerbeg.matches.fragments.MenuFragment;
import com.secerbeg.matches.fragments.WeekSelectFragment;
import com.secerbeg.matches.fragments.WeekdaySelectFragment;
import com.secerbeg.matches.week.Week;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mirza Secerbegovic
 */
public class ScreenController
{

	private static ScreenController mInstance = null;
	private static List<Screen> openedScreens = new ArrayList<Screen>();
	private FragmentManager mFragmentManager;

	/**
	 *
	 */
	private ScreenController()
	{
	}

	/**
	 *
	 * @return
	 */
	public static ScreenController getInstance()
	{
		if (mInstance == null)
		{
			mInstance = new ScreenController();
		}
		return mInstance;
	}

	/**
	 *
	 */
	public static enum Screen
	{
		MENU,
		GAME,
		DIFFICULTY,
		WEEK_SELECT,
		WEEKDAY_SELECT
	}

	/**
	 *
	 * @return
	 */
	public static Screen getLastScreen()
	{
		return openedScreens.get(openedScreens.size() - 1);
	}

	/**
	 *
	 * @param screen
	 */
	public void openScreen(Screen screen)
	{
		mFragmentManager = Shared.activity.getSupportFragmentManager();
		
		if (screen == Screen.GAME && openedScreens.get(openedScreens.size() - 1) == Screen.GAME)
		{
			openedScreens.remove(openedScreens.size() - 1);
		} else if (screen == Screen.DIFFICULTY && openedScreens.get(openedScreens.size() - 1) == Screen.GAME)
		{
			openedScreens.remove(openedScreens.size() - 1);
			openedScreens.remove(openedScreens.size() - 1);
		}

		Fragment fragment = getFragment(screen);
		FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, fragment);
		fragmentTransaction.commit();

		openedScreens.add(screen);
	}

	/**
	 *
	 * @return
	 */
	public boolean onBack()
	{
		if (openedScreens.size() > 0)
		{
			Screen screenToRemove = openedScreens.get(openedScreens.size() - 1);
			openedScreens.remove(openedScreens.size() - 1);
			if (openedScreens.size() == 0)
			{
				return true;
			}
			Screen screen = openedScreens.get(openedScreens.size() - 1);
			openedScreens.remove(openedScreens.size() - 1);
			openScreen(screen);
			if ((screen == Screen.WEEKDAY_SELECT || screen == Screen.MENU) &&
					(screenToRemove == Screen.DIFFICULTY || screenToRemove == Screen.GAME))
			{
				Shared.eventBus.notify(new ResetBackgroundEvent());
			}
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param screen
	 * @return
	 */
	private Fragment getFragment(Screen screen)
	{
		Fragment fragment = null;
		switch (screen)
		{
			case MENU:
				fragment = new MenuFragment();
				break;
			case DIFFICULTY:
				fragment = new DifficultySelectFragment();
				break;
			case GAME:
				fragment =  new GameFragment();
				break;
			case WEEKDAY_SELECT:
				fragment =  new WeekdaySelectFragment();
				break;
			case WEEK_SELECT:
				fragment = new WeekSelectFragment();
			default:
				break;
		}
		return fragment;
	}
}
