package com.zeneke.cerdigres;

import com.badlogic.gdx.Game;
//import com.zeneke.cerdigres.screens.MenuScreen;
import com.zeneke.cerdigres.screens.SplashScreen;


public class MainCerdigres extends Game {
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Assets.load();
		setScreen(new SplashScreen(this));
	}
	
}
