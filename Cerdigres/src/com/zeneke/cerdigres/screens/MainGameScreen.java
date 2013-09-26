package com.zeneke.cerdigres.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zeneke.cerdigres.Assets;
import com.zeneke.cerdigres.MainCerdigres;
import com.zeneke.cerdigres.Player;
import com.zeneke.cerdigres.Tablero;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;


public class MainGameScreen implements Screen
{
	
	public static final int NUMFICHAS = 16;
    public static final int TAMTABLERO = 4;
    public static final int ALTO_TABLAS = 50;
    public static final int ANCHO_TABLAS = 50;
    public static final int ALTO_FICHAS = 50;
    public static final int ANCHO_FICHAS = 50;
    public static final int TIEMPO_PAUSITA = 500;

    MainCerdigres game;
	Stage stage;
    ImageButton[][] fichasTablero;
    ImageButton[] eleccionFichas;
	TextButton startGameButton;
	TextButton optionsButton;
	TextButton exitButton;
    ImageButton colocarButton;
    Label labelTurnoJugador;
    Tablero tablero;
    Player[] jugadores;
    int turno;
    int noTurno;
    boolean esEmpate = false;
    int fichaElegida;
    boolean tablero_bloqueado=true;
    boolean fichas_bloqueado=false;

    boolean esHumano;

    Table mainTable;
    Table tablaTablero;
    Table tablaFichas;
	
	public MainGameScreen(MainCerdigres game, boolean vsPlayer)
    {
		this.game = game;
        fichasTablero = new ImageButton[TAMTABLERO][TAMTABLERO];
        eleccionFichas = new ImageButton[NUMFICHAS];

        esHumano = vsPlayer;

        jugadores = new Player[2];
        tablero = new Tablero();
        //En algun momento habra que definir aqui si el jugador es CPU o no
        //true indica que los 2 son humanos
        //TODO: Ojo que ahora mismo los nombre se asignan de manera fija, es decir, nombre1 siempre va a ser el jugador 1
        jugadores[0] = new Player("Alex", true);
        if (esHumano) jugadores[1] = new Player("Fer", true);
        else jugadores[1] = new Player("CPU", false);
        Random randomGen = new Random();
        //definimos aleatoriamiente si empieza el jugador 1 o 2
        //turno = randomGen.nextInt(2);
        turno = 0;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
//		Table.drawDebug(stage);
        //Aqui es la CPU quien toma la decision de colocar ficha
        if(!jugadores[turno].esHumano())
        {
            mueveCPU();
            pausita();
        }

	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show()
    {
		// TODO Auto-generated method stub
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		tablaTablero = new Table(Assets.skin);
        tablaFichas = new Table(Assets.skin);
		
		startGameButton = new TextButton("New Game", Assets.skin);
		optionsButton = new TextButton("Options", Assets.skin);
		exitButton = new TextButton("Exit", Assets.skin);

        ImageButton.ImageButtonStyle estiloColocar = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        colocarButton = new ImageButton(estiloColocar);

		Image backImage = new Image(Assets.backgroundTexture);
		
		startGameButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
                mueveCPU();
				return true;
			}
			
		});

        exitButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                // TODO Auto-generated method stub
                Gdx.app.exit();

                return true;
            }

        });

        for (int i=0; i<TAMTABLERO; i++)
            for (int j=0; j<TAMTABLERO; j++)
            {
                String name = Integer.toString(i)+Integer.toString(j);
                ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
                //style.imageUp = new TextureRegionDrawable(Assets.botonPirata);
                //style.imageDown = new TextureRegionDrawable(Assets.botonPirataDown);
                fichasTablero[i][j] = new ImageButton(style);
                fichasTablero[i][j].setName(name);
                fichasTablero[i][j].addListener( new InputListener()
                {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y,int pointer, int button)
                    {
                        if (!tablero_bloqueado)
                        {
                            String tmp = event.getListenerActor().getName();
                            String sx = tmp.substring(0,1);
                            String sy = tmp.substring(1,2);
                            int xint = Integer.parseInt(sx);
                            int yint = Integer.parseInt(sy);
                            //

                            //
                            if (tablero.colocarFicha(fichaElegida,xint,yint))
                            {
                                lockTablero();
                                tablero_bloqueado=true;
                                unLockFichas();
                                fichas_bloqueado=false;
                                ImageButton boton = (ImageButton)event.getListenerActor();
                                ImageButton.ImageButtonStyle style = seleccionarStyle(xint, yint);
                                boton.setStyle(style);
                                if (tablero.hayCoincidencia())
                                {
                                    System.out.println("Fin de la partida: "+ jugadores[turno]);
                                    labelTurnoJugador.setText("HA GANADO: "+jugadores[turno].getNombre());
                                    //System.exit(0);
                                    lockFichas();
                                    fichas_bloqueado=true;
                                    lockTablero();
                                    tablero_bloqueado=true;
                                    finTableros();

                                }
                                else
                                {
                                    labelTurnoJugador.setText(jugadores[turno].getNombre() + " Elije ficha");
                                    ImageButton.ImageButtonStyle estiloColocar = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
                                    colocarButton.setStyle(estiloColocar);
                                }
                            }
                        }

                        return true;
                    }

                });
            }

        for (int i=0; i<NUMFICHAS; i++)
        {

            //eleccionFichas[i] = new TextButton(Integer.toString(i),Assets.skin);

            //eleccionFichas[i] = new Button(new Image(Assets.botonPirata),Assets.skin);
            //eleccionFichas[i].setBackground(Assets.botonPirata);

            eleccionFichas[i] = seleccionarImagen(i);
            eleccionFichas[i].setName(Integer.toString(i));
            eleccionFichas[i].addListener(new InputListener()
            {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,int pointer, int button)
                {

                    if (!fichas_bloqueado)
                    {
                        int tmp = Integer.parseInt(event.getListenerActor().getName());
                        //event.getListenerActor().setName("pulsado");
                        if (tablero.elegirFicha(tmp))
                        {
                            fichaElegida = tmp;
                            lockFichas();
                            fichas_bloqueado=true;
                            unLockTablero();
                            tablero_bloqueado=false;
                            event.getListenerActor().setColor(Color.DARK_GRAY);
                            cambiarTurno();
                            ImageButton.ImageButtonStyle style = eleccionFichas[tmp].getStyle();
                            colocarButton.setStyle(style);
                        }
                    }
                    return true;
                }

            });
        }

        //mainTable = new Table();
        //mainTable.setFillParent(true);


		tablaTablero.setFillParent(true);
//		tablaTablero.debug(); 
		//tablaTablero.add(startGameButton).width(150).height(50);
        labelTurnoJugador = new Label("Empieza eligiendo ficha: "+ jugadores[turno].getNombre(), Assets.skin);
        //labelTurnoJugador.setPosition(0,550);
        stage.addActor(labelTurnoJugador);

        colocarButton.setSize(ANCHO_FICHAS+10,ALTO_FICHAS+10);
        //colocarButton.setPosition(10,450);
        stage.addActor(colocarButton);

        exitButton.setSize(100,50);
        //exitButton.setPosition(350,550);
        stage.addActor(exitButton);

        optionsButton.setSize(100,50);
        optionsButton.setPosition(350,500);
        //stage.addActor(optionsButton);

        startGameButton.setSize(100,50);
        startGameButton.setPosition(350,450);
        //stage.addActor(startGameButton);

        //tablaTablero.add(labelTurnoJugador);
        tablaTablero.row();
        for (int i=0; i< TAMTABLERO; i++)
        {
            for (int j=0; j< TAMTABLERO; j++)
            {
                tablaTablero.add(fichasTablero[i][j]).width(ANCHO_TABLAS).height(ALTO_TABLAS);
            }
            tablaTablero.row();
        }
		//tablaTablero.add(optionsButton).width(150).height(50).padTop(10);


		//table.add(exitButton).width(150).height(50).padTop(10);
		//stage.addActor(backImage);
        tablaTablero.setPosition(0,150);
		stage.addActor(tablaTablero);

        tablaFichas.setFillParent(true);
        tablaFichas.setPosition(0,-100);
        for (int i=0; i<NUMFICHAS; i++)
        {
            tablaFichas.add(eleccionFichas[i]).width(ANCHO_FICHAS).height(ALTO_FICHAS).pad(15);
            if ((i+1)%4 == 0)
                tablaFichas.row();
        }

        stage.addActor(tablaFichas);
        lockTablero();

        //lockFichas();
	}

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
		
	}



    public void lockFichas()
    {
        for (int i=0; i<NUMFICHAS; i++)
        {
            //eleccionFichas[i].setVisible(false);
        }
    }

    public void unLockFichas()
    {
        for (int i=0; i<NUMFICHAS; i++)
        {
            //eleccionFichas[i].setVisible(true);
        }
    }

    public void lockTablero()
    {
        for (int i=0; i<TAMTABLERO; i++)
            for (int j=0; j<TAMTABLERO; j++)
            {
                //fichasTablero[i][j].setVisible(false);

            }
    }

    public void unLockTablero()
    {
        for (int i=0; i<TAMTABLERO; i++)
            for (int j=0; j<TAMTABLERO; j++)
            {
                //fichasTablero[i][j].setVisible(true);
            }
    }

    public void finTableros()
    {
        for (int i=0; i<NUMFICHAS; i++)
        {
            eleccionFichas[i].setVisible(true);

        }
        for (int i=0; i<TAMTABLERO; i++)
            for (int j=0; j<TAMTABLERO; j++)
            {
                fichasTablero[i][j].setVisible(true);
            }

    }

    public void cambiarTurno()
    {
        if (turno == 0)
        {
            turno = 1;
            noTurno = 0;
            labelTurnoJugador.setText(jugadores[turno].getNombre() + " Coloca ficha");
        }
        else
        {
            turno = 0;
            noTurno = 1;
            labelTurnoJugador.setText(jugadores[turno].getNombre() + " Coloca ficha");
        }
    }

    public ImageButton seleccionarImagen(int i)
    {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));


        style.imageDown = new TextureRegionDrawable(Assets.botonPirataDown);
        ImageButton seleccion = new ImageButton(style);
        boolean c1 = tablero.fichas[i].getC1();
        boolean c2 = tablero.fichas[i].getC2();
        boolean c3 = tablero.fichas[i].getC3();
        boolean c4 = tablero.fichas[i].getC4();
        if (c1&&c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.tttt);
        if (c1&&c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.tttf);
        if (c1&&c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.ttft);
        if (c1&&c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.ttff);
        if (c1&&!c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.tftt);
        if (c1&&!c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.tftf);
        if (c1&&!c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.tfft);
        if (c1&&!c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.tfff);

        if (!c1&&c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.fttt);
        if (!c1&&c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.fttf);
        if (!c1&&c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.ftft);
        if (!c1&&c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.ftff);
        if (!c1&&!c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.fftt);
        if (!c1&&!c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.fftf);
        if (!c1&&!c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.ffft);
        if (!c1&&!c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.ffff);


        return seleccion;
    }

    public ImageButton.ImageButtonStyle seleccionarStyle(int x,int y)
    {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));

        //style.imageDown = new TextureRegionDrawable(Assets.botonPirataDown);
        boolean c1 = tablero.grid[x][y].getC1();
        boolean c2 = tablero.grid[x][y].getC2();
        boolean c3 = tablero.grid[x][y].getC3();
        boolean c4 = tablero.grid[x][y].getC4();
        if (c1&&c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.tttt);
        if (c1&&c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.tttf);
        if (c1&&c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.ttft);
        if (c1&&c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.ttff);
        if (c1&&!c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.tftt);
        if (c1&&!c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.tftf);
        if (c1&&!c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.tfft);
        if (c1&&!c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.tfff);

        if (!c1&&c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.fttt);
        if (!c1&&c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.fttf);
        if (!c1&&c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.ftft);
        if (!c1&&c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.ftff);
        if (!c1&&!c2&&c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.fftt);
        if (!c1&&!c2&&c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.fftf);
        if (!c1&&!c2&&!c3&&c4) style.imageUp = new TextureRegionDrawable(Assets.ffft);
        if (!c1&&!c2&&!c3&&!c4) style.imageUp = new TextureRegionDrawable(Assets.ffff);

        return style;
    }

    public void debug_posiciones(float x, float y, ImageButton boton, boolean tablero)
    {
        if (tablero)
        {
            float xint = boton.getX();
            float yint = boton.getY();

            optionsButton.setText("ficha "+Float.toString(xint) + " " + Float.toString(yint));
            exitButton.setText("punt " + Float.toString(x) + " "+ Float.toString(y));

        }
    }


    public void mueveCPU()
    {
        int tmp = 0;
        while (tablero_bloqueado && tmp <NUMFICHAS)
        {
            Random randomGen = new Random();
            //La CPU escogera fichas aleatoriamente
            tmp = randomGen.nextInt(NUMFICHAS);
            //event.getListenerActor().setName("pulsado");
            debug_cpu(tmp,true);
            if (tablero.elegirFicha(tmp))
            {
                fichaElegida = tmp;
                lockFichas();
                fichas_bloqueado=true;
                unLockTablero();
                tablero_bloqueado=false;
                eleccionFichas[tmp].setColor(Color.DARK_GRAY);
                cambiarTurno();

                //ImageButton.ImageButtonStyle style = seleccionarStyle(xint, yint);
                ImageButton.ImageButtonStyle style = eleccionFichas[tmp].getStyle();
                colocarButton.setStyle(style);
            }

            if (tmp < NUMFICHAS)tmp++; else break;
        }

        int x = 0;
        int y = 0;

        //TODO: ojo que aqui hemos hardcoded el turno de la CPU para que no ponga
        //TODO: por nosotros
        while (fichas_bloqueado && turno == 1)
        {
            Random randomGen = new Random();
            //La CPU escogera fichas aleatoriamente
            x = randomGen.nextInt(TAMTABLERO);
            y = randomGen.nextInt(TAMTABLERO);
            if (tablero.colocarFicha(fichaElegida,x,y))
            {
                lockTablero();
                tablero_bloqueado=true;
                unLockFichas();
                fichas_bloqueado=false;
                ImageButton.ImageButtonStyle style = seleccionarStyle(x, y);
                fichasTablero[x][y].setStyle(style);
                if (tablero.hayCoincidencia())
                {
                    System.out.println("Fin de la partida: "+ jugadores[turno]);
                    labelTurnoJugador.setText("HA GANADO: "+jugadores[turno].getNombre());
                    //System.exit(0);
                    lockFichas();
                    fichas_bloqueado=true;
                    lockTablero();
                    tablero_bloqueado=true;
                    finTableros();
                }
                else
                {
                    labelTurnoJugador.setText(jugadores[turno].getNombre() + " Elije ficha");
                }
            }
        }
    }

    public void pausita()
    {
        try
        {
            Thread.sleep(TIEMPO_PAUSITA);//sleep for 1000 ms
        } catch (InterruptedException ie)
        {
            System.out.println("I was interrupted!");
        }
    }

    public void debug_cpu(int x,boolean ficha)
    {
        if (ficha) optionsButton.setText("ficha elegida "+Integer.toString(x) );
        else optionsButton.setText("ficha elegida "+Integer.toString(x) );
    }

}
