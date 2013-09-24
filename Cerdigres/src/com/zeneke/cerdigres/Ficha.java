package com.zeneke.cerdigres;
/*
 * Clase generica ficha, independientemente de las cualidades que definamos
 * se puede crear la clase de forma abstracta de manera que, ya sean formas, colores,
 * sexo piratas o zombis, es independiente de la implementacion ya que mas adelante
 * definiremos estas mismas cualidades
 */
public class Ficha {
	/*
	 * Las cualidades estan definidas por un boolean: True or False
	 * por ejemplo en el prototipo vamos a usar la siguiente
	 * cualidad1 personaje pirata = true; ninja = false
	 * cualidad2 raza blanca = true; raza negra = false
	 * cualidad3 sexo masculino = true; sexo femenino = false
	 * cualidad4 edad mayor = true; menor = false
	 */
	boolean cualidad1;
	boolean cualidad2;
	boolean cualidad3;
	boolean cualidad4;
	Ficha (boolean c4, boolean c3, boolean c2, boolean c1)
	{
		cualidad1=c1;
		cualidad2=c2;
		cualidad3=c3;
		cualidad4=c4;
	}
	
	public boolean getC1() {return cualidad1;}
	public boolean getC2() {return cualidad2;}
	public boolean getC3() {return cualidad3;}
	public boolean getC4() {return cualidad4;}
	

	//Este metodo es para depuracion y muestra la propia ficha, con sus cualidades en modo texto
	public void print_ficha_consola()
	{	
		String personaje = " ";
		String raza = " ";
		String sexo = " ";
		String edad = " ";
		if (cualidad1)
			personaje = "pirata"; //Cerdigre
		else
			personaje = "ninja"; //Lobeja
		if (cualidad2)
			raza = "white"; //Naranja
		else
			raza = "black"; //Morado
		if (cualidad3)
			sexo = "hombre";
		else
			sexo = "mujer";
		if (cualidad4)
			edad = "viejo";
		else
			edad = "joven";
		System.out.print(personaje +" "+ raza + " " + sexo + " " +edad);
	}

    //metodo de depuracion como el anterior, pero muestra las cualidades en modo abreviado
	public void print_ficha_short_consola()
	{	
		String personaje = " ";
		String raza = " ";
		String sexo = " ";
		String edad = " ";
		if (cualidad1)
			personaje = "P";
		else
			personaje = "N";
		if (cualidad2)
			raza = "W";
		else
			raza = "B";
		if (cualidad3)
			sexo = "H";
		else
			sexo = "M";
		if (cualidad4)
			edad = "V";
		else
			edad = "J";
		System.out.print(" "+personaje +""+ raza + "" + sexo + "" +edad+ " ");
	}

    public String textoFicha()
    {
        String personaje = " ";
        String raza = " ";
        String sexo = " ";
        String edad = " ";
        if (cualidad1)
            personaje = "P";
        else
            personaje = "N";
        if (cualidad2)
            raza = "W";
        else
            raza = "B";
        if (cualidad3)
            sexo = "H";
        else
            sexo = "M";
        if (cualidad4)
            edad = "V";
        else
            edad = "J";
        return " "+personaje +""+ raza + "" + sexo + "" +edad+ " ";
    }
    public boolean getCualidad1() {return cualidad1;}
    public boolean getCualidad2() {return cualidad2;}
    public boolean getCualidad3() {return cualidad3;}
    public boolean getCualidad4() {return cualidad4;}
}
