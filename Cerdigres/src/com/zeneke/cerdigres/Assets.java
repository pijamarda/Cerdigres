package com.zeneke.cerdigres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


//Referenca za Tile Sprajtove http://www.netthreads.co.uk/2012/01/31/libgdx-example-of-using-scene2d-actions-and-event-handling/

public class Assets {
	public static TextureRegion platform;
	public static TextureRegion fallingManSplash;
	public static Animation fallingManAnim;
	public static Texture backgroundTexture;
	public static Skin skin;
    public static TextureRegion botonPirata;
    public static TextureRegion botonPirataDown;
    public static TextureRegion tttt;
    public static TextureRegion tttf;
    public static TextureRegion ttft;
    public static TextureRegion ttff;
    public static TextureRegion tftt;
    public static TextureRegion tftf;
    public static TextureRegion tfft;
    public static TextureRegion tfff;

    public static TextureRegion fttt;
    public static TextureRegion fttf;
    public static TextureRegion ftft;
    public static TextureRegion ftff;
    public static TextureRegion fftt;
    public static TextureRegion fftf;
    public static TextureRegion ffft;
    public static TextureRegion ffff;

	
	public static void load () {
		
		TextureAtlas textureAtlas = new TextureAtlas("data/PigTest.pack");
		fallingManAnim = new Animation(0.2f, textureAtlas.findRegion("falling1"), textureAtlas.findRegion("falling2"));
		platform = textureAtlas.findRegion("platform");
		fallingManSplash = textureAtlas.findRegion("rip");
		backgroundTexture = new Texture(Gdx.files.internal("data/back.jpg"));
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        //skin = new Skin();
        //skin.add("default", new Texture(Gdx.files.internal("data/boton_pirata.png")));
        Texture texture1 = new Texture(Gdx.files.internal("images/boton_pirata.png"));
        botonPirata = new TextureRegion(texture1);
        botonPirataDown = new TextureRegion(texture1);
        botonPirataDown.flip(true,true);
        //botonPirata = new Skin(Gdx.files.internal("data/botonskin.json"));
        Texture text1 = new Texture(Gdx.files.internal("images/tttt.png"));
        tttt = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/tttf.png"));
        tttf = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/ttft.png"));
        ttft = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/ttff.png"));
        ttff = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/tftt.png"));
        tftt = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/tftf.png"));
        tftf = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/tfft.png"));
        tfft = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/tfff.png"));
        tfff = new TextureRegion(text1);

        text1 = new Texture(Gdx.files.internal("images/fttt.png"));
        fttt = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/fttf.png"));
        fttf = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/ftft.png"));
        ftft = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/ftff.png"));
        ftff = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/fftt.png"));
        fftt = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/fftf.png"));
        fftf = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/ffft.png"));
        ffft = new TextureRegion(text1);
        text1 = new Texture(Gdx.files.internal("images/ffff.png"));
        ffff = new TextureRegion(text1);

		
	}
}
