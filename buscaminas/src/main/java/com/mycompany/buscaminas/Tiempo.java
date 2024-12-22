/*
	Programa: Buscaminas Java
	Autor: Borja
	Web: http://todojava.awardspace.com/
	Version: 1.0
	
	Descripci�n: El m�tico juego del buscaminas. Clase del tiempo, con ella corren los segundos 
	en la caja de texto.
	
	Dificultad: Media
	
	Para m�s informaci�n sobre Threads vete a http://todojava.awardspace.com/manuales-java.html
	desc�rgate el manual de: Threads, Programaci�n multihebra
*/

//	Clase Temporizador

package com.mycompany.buscaminas;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Tiempo extends Thread {

	//clase del buscamintas
	buscaminas bd;
	
	//Parar cron�metro
	boolean Salir=false;
	
	//Contador de segundos
	int seg=0;
	
	Tiempo (JFrame j){
		System.out.println("\n Comienza el tiempo...");
		//copia la clase del buscaminas en el objeto bd. Con esto
		//podemos referirnos a los atribustos de la clase del buscaminas y 
		//cambiar el tiempo de la caja txtTiempo
		
		bd=(buscaminas)j;
	}
	
	public void run()	//m�todo run, obligatorio en el thread
	{
		while(!Salir){
			try
			{
			//Retardar 1000 milisegutndos
			sleep(1000);
			seg++;		
			bd.txtTiempo.setText(Integer.toString(seg));
			}
			catch(InterruptedException ie)
			{
				System.out.println(ie);
			}
		}
	}
	
	public void parar(boolean b)
	{
		//m�todo para parar el cron�metro
		if(b)Salir=true;
		seg=0;
	}

}