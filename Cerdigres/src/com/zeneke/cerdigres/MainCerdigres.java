package com.zeneke.cerdigres;

import com.badlogic.gdx.Game;
//import com.zeneke.cerdigres.screens.MenuSelectScreen;
import com.zeneke.cerdigres.screens.MainGameScreen;
import com.zeneke.cerdigres.screens.SplashScreen;


public class MainCerdigres extends Game {
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Assets.load();
		setScreen(new SplashScreen(this));
        //setScreen(new MainGameScreen(this,true));
	}
	
}
