package com.zeneke.cerdigres;

public class Player {
	String nombre;
    // false - Nos indicaria CPU
    // true - Nos indicaria Humano
	boolean humano;
	
	public Player(String name, boolean type)
	{
		nombre = name;
        humano = type;
	}

    public String getNombre() {return nombre;}
    public boolean esHumano() {return humano;}

}
