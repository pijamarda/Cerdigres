package com.zeneke.cerdigres.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zeneke.cerdigres.Assets;
import com.zeneke.cerdigres.MainCerdigres;

public class SplashScreen implements Screen
{

    MainCerdigres game;
	Stage stage;
	
	public SplashScreen(MainCerdigres game) {
		// TODO Auto-generated constructor stub
		this.game = game;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        //stage = new Stage();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport( width, height, true );

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Image splashImage = new Image(Assets.backgroundTexture);
		splashImage.addAction(Actions.fadeIn(2f));
        //he eliminado el fade in fade out
        //Actions.fadeOut( 0.001f ), Actions.fadeIn( 2f ),
		splashImage.addAction(Actions.run(onSplashFinishedRunnable));
		
		stage.addActor(splashImage);
	}
	
	Runnable onSplashFinishedRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
            //Descartamos el menu por ahora, llamando a la ventana de juego directamente
			game.setScreen(new MenuPrincipalScreen(game));
            //game.setScreen(new MainGameScreen(game));
		}
	};

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
