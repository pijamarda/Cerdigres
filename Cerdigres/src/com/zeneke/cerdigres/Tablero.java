package com.zeneke.cerdigres;

/*
 * La clase tablero define el tablero concreto de juego donde se iran colocando las fichas
 * y define ademas las fichas disponibles para colocar
 */
public class Tablero {
	//esta variable indica el tamaño del tablero, habra que valorar si colocarlo en una clase mas global
	public static final int MAXFICHAS = 4;
	//variable que nos da el numero de fichas
	public static final int NUMFICHAS = MAXFICHAS * MAXFICHAS;
	//Variable que guarda todas las fichas disponibles y no disponibles
	public Ficha[] fichas;
	//Variable donde vamos enmarcando las fichas
	public Ficha[][] grid;
	
	public Tablero()
	{
		fichas = new Ficha[NUMFICHAS];
		grid = new Ficha[MAXFICHAS][MAXFICHAS];
        /*
         * El FOR a continuacion:
         * Este for genera todas las fichas disponibles que iremos colocando en el propio tablero
         * La idea de la generacion es resumidamente la siguiente, asignamos a cada indice la siguiente estructura
         * 0 - TTTT
         * 1 - TTTF
         * 2 - TTFT
         * 3 - TTFF
         * etc..., es un proceso muy manual donde la asignacion es casi-manual pero no totalmente
         */
		for(int i=0; i<NUMFICHAS; i++)			
		{
            // el valor 1 va cambiando TFTFTFTFT
			boolean valor1;
			if (i % 2 == 0) valor1 = true; else valor1 = false;
            // el valor 2 va cambiando TTFFTTFFTTFF
			boolean valor2;
			if ((i == 0) || (i == 1) || (i == 4) || (i == 5) || (i == 8) || (i == 9) || (i == 12) || (i == 13))
				valor2 = true;
			else valor2 = false;
            //el valor 3 va cambiando 4 Ts 4 Fs
			boolean valor3;
			if (i<4 || (i>7 && i<12)) valor3 = true; else valor3 = false;
            // el valor va cambiando 8 Ts 8 Fs
			boolean valor4;
			if (i<8) valor4 = false; else valor4 = true; 
			fichas[i] = new Ficha(valor4, valor3, valor2, valor1);
		}
	}

    // este metodo para la ejecucion por consola muestras las fichas que tenemos aun disponibles
    // cuando no esta disponible las marca como hueco vacio, hay que recordar que la opcion por pantalla
    // es +1 la opcion del Array (que empieza en 0)
	public void print_fichas_disponibles_consola()
	{
		//fichas[5] = null;
		//fichas[14] = null;
		for (int i=0; i<NUMFICHAS; i++)
			if (fichas[i] != null)
			{
				int opcion = i+1;
				System.out.print(opcion + ". ");
                // llamo al print por consola de Ficha
				fichas[i].print_ficha_consola();
				System.out.println("");
			}
			//else System.out.println("Vacio");
	}

    //colocarFicha saca una ficha de la lista de fichas disponibles (marcando a NULL el Array) y la pone
    //en el hueco del tablero correspondiente
    //devuelvo False en el caso de que la ficha elegida sea erronea, o el hueco no este vacio
	public boolean colocarFicha(int ficha_origen, int coorx, int coory)
	{
		boolean resultado = false;
		int opcion = ficha_origen ;
        //tenemos que chequear que la opcion elegida no es vacia, y que la posicion donde se quiere introducir
        // SI lo esta
		if ((fichas[opcion] != null) && (grid[coorx][coory] == null))
		{
			grid[coorx][coory] = fichas[opcion];
			fichas[opcion] = null;
			resultado = true;
		}
		return resultado;
	}

    /*
     * Este es el método más "complejo" del programa, basicamente es el chequeador de que la partida se haya terminado
     * para ello mira todas las posibles casuisticas del juego.
     * TODO: En teoria falta los modos extra de terminacion de juego que Alex ha introducido en el GDC
     */
	public boolean hayCoincidencia()
	{
		boolean hayC = false;
		boolean c1=true;
		boolean c2=true;
		boolean c3=true;
		boolean c4=true;
					
		//son todos cualidad X = true?
		//aqui comprobamos todas las filas
		for (int i=0; i<MAXFICHAS; i++)
		{
			c1=true;
			c2=true;
			c3=true;
			c4=true;
			boolean hayNull = false;
			for (int j=0; j<MAXFICHAS; j++)
			{	
						
				if (grid[i][j] != null)
				{
						c1 = c1 && grid[i][j].getC1();
						c2 = c2 && grid[i][j].getC2();
						c3 = c3 && grid[i][j].getC3();
						c4 = c4 && grid[i][j].getC4();
				}
				else hayNull = true;				
			}
			if (!hayNull && (c1 || c2 || c3 || c4)) 
			{	
				print_ctest("Salgo en true | fila",i);
				return true;
			}
		}
		
		
		//comprobamos en todas las columnas
		for (int j=0; j<MAXFICHAS; j++)
		{
			c1=true;
			c2=true;
			c3=true;
			c4=true;
			boolean hayNull = false;
			for (int i=0; i<MAXFICHAS; i++)
			{			
				if (grid[i][j] != null)
				{
						c1 = c1 && grid[i][j].getC1();
						c2 = c2 && grid[i][j].getC2();
						c3 = c3 && grid[i][j].getC3();
						c4 = c4 && grid[i][j].getC4();
				}
				else hayNull = true;		
			}
			if (!hayNull && (c1 || c2 || c3 || c4)) 
			{	
				print_ctest("Salgo en true | columna",j);
				return true;
			}
		}
		
		
		//son todos cualidad X = false?
				
		//aqui comprobamos todas las filas
		for (int i=0; i<MAXFICHAS; i++)
		{
			c1=true;
			c2=true;
			c3=true;
			c4=true;
			boolean hayNull = false;
			for (int j=0; j<MAXFICHAS; j++)
			{			
				if (grid[i][j] != null)
				{
						c1 = c1 && !grid[i][j].getC1();
						c2 = c2 && !grid[i][j].getC2();
						c3 = c3 && !grid[i][j].getC3();
						c4 = c4 && !grid[i][j].getC4();
				}
				else hayNull = true;
			}
			if (!hayNull && (c1 || c2 || c3 || c4)) 
			{	
				print_ctest("Salgo en false | fila",i);
				return true;
			}			
		}
		
		
		//comprobamos en todas las columnas
		for (int j=0; j<MAXFICHAS; j++)
		{
			c1=true;
			c2=true;
			c3=true;
			c4=true;
			boolean hayNull = false;
			for (int i=0; i<MAXFICHAS; i++)
			{			
				if (grid[i][j] != null)
				{
						c1 = c1 && !grid[i][j].getC1();
						c2 = c2 && !grid[i][j].getC2();
						c3 = c3 && !grid[i][j].getC3();
						c4 = c4 && !grid[i][j].getC4();
				}
				else hayNull = true;
			}
			if (!hayNull && (c1 || c2 || c3 || c4)) 
			{	
				print_ctest("Salgo en false | columna",j);
				return true;
			}
		}

        //Comprobacion diagonal
        //Primero para los casos True
        boolean hayNull = false;
        c1=true;
        c2=true;
        c3=true;
        c4=true;
        for (int i=0; i<MAXFICHAS; i++)
            for (int j=0; j<MAXFICHAS; j++)
                if (j==i)
                    if (grid[i][j] != null)
                    {
                        c1 = c1 && grid[i][j].getC1();
                        c2 = c2 && grid[i][j].getC2();
                        c3 = c3 && grid[i][j].getC3();
                        c4 = c4 && grid[i][j].getC4();
                    }
                    else hayNull = true;
        if (!hayNull && (c1 || c2 || c3 || c4))
        {
            print_ctest("Salgo en true | diagonal principal",0);
            return true;
        }

        //Luego para los casos False
        hayNull = false;
        c1=true;
        c2=true;
        c3=true;
        c4=true;
        for (int i=0; i<MAXFICHAS; i++)
            for (int j=0; j<MAXFICHAS; j++)
                if (j==i)
                    if (grid[i][j] != null)
                    {
                        c1 = c1 && !grid[i][j].getC1();
                        c2 = c2 && !grid[i][j].getC2();
                        c3 = c3 && !grid[i][j].getC3();
                        c4 = c4 && !grid[i][j].getC4();
                    }
                    else hayNull = true;
        if (!hayNull && (c1 || c2 || c3 || c4))
        {
            print_ctest("Salgo en false | diagonal principal",0);
            return true;
        }

        //Comprobacion diagonal invertida
        //Primero para los casos True
        hayNull = false;
        c1=true;
        c2=true;
        c3=true;
        c4=true;
        int i=0;
        int j=3;
        int contador=0;
        while (contador<MAXFICHAS)
        {
            if (grid[i][j] != null)
            {
                c1 = c1 && grid[i][j].getC1();
                c2 = c2 && grid[i][j].getC2();
                c3 = c3 && grid[i][j].getC3();
                c4 = c4 && grid[i][j].getC4();
                i++;
                j--;
            }
            else hayNull = true;
            contador++;
        }
        if (!hayNull && (c1 || c2 || c3 || c4))
        {
            print_ctest("Salgo en true | diagonal invertida",0);
            return true;
        }

        //Primero para los casos False
        hayNull = false;
        c1=true;
        c2=true;
        c3=true;
        c4=true;
        i=0;
        j=3;
        contador=0;
        while (contador<MAXFICHAS)
        {
            if (grid[i][j] != null)
            {
                c1 = c1 && !grid[i][j].getC1();
                c2 = c2 && !grid[i][j].getC2();
                c3 = c3 && !grid[i][j].getC3();
                c4 = c4 && !grid[i][j].getC4();
                i++;
                j--;
            }
            else hayNull = true;
            contador++;
        }
        if (!hayNull && (c1 || c2 || c3 || c4))
        {
            print_ctest("Salgo en false | diagonal invertida",0);
            return true;
        }
        return hayC;
	}

    //Este metodo imprime el estado actual del tablero por la consola
	public void print_grid_consola()
	{
		for(int i=0; i<MAXFICHAS; i++)
		{
			for(int j=0; j<MAXFICHAS; j++)
			{
				if (grid[i][j] == null)
					System.out.print("      ");
				else
					grid[i][j].print_ficha_short_consola();
			}
			System.out.println("");
		}
	}
	
	
	//Modo debug para la salida en formato texto, normalmente le pasamos a este metodo, el estado donde salimos y
    // el numero de fila o columna
	public void print_ctest(String texto, int i)
	{
		System.out.println(texto+"="+i);
	}

    public boolean elegirFicha(int elec)
    {
        boolean esCorrecta = false;
        if (elec >= 0 && fichas[elec] != null && elec<NUMFICHAS)
            esCorrecta = true;
        return esCorrecta;
    }

    public String textoFicha(int i, int j)
    {
        String name = Integer.toString(i)+Integer.toString(j);
        if (grid[i][j] != null)
            return grid[i][j].textoFicha();
        else return name;
    }

    public int fichasRestantes()
    {
        int contador = NUMFICHAS;
        for (int i=0; i<NUMFICHAS; i++)
            if (fichas[i]==null)
                contador--;
        System.out.println(contador);
        return contador;
    }
	
}
