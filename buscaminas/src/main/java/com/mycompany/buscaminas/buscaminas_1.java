/*
	Programa: Buscaminas Java
	Autor: Borja
	Web: http://todojava.awardspace.com/
	Version: 1.0
	
	Descripci�n: El m�tico juego del buscaminas.
	
	Dificultad: Media
*/

//	Clase Principal

package com.mycompany.buscaminas;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class buscaminas extends JFrame implements ActionListener {
	//	Atributos
	JButton botones [][];
	int matrizMinas [][];
    //	Cajas de texto
    JTextField txtMinas=new JTextField(3);
    JTextField txtTiempo=new JTextField(3);
    //	Etiquetas
    JLabel lMinas=new JLabel("Minas restantes:");
    JLabel lTiempo=new JLabel("Tiempo transcurrido:");
    
	//	Imagenes de minas
	ImageIcon imagenesMinas []=new ImageIcon [12];
	//	Dimensi�n
	int dim=10;
	int totalMinas=20;
	int casillas=dim*dim-totalMinas;
	
	//	Clase del tiempo
	Tiempo tp;
	
	buscaminas (){
		botones=new JButton [dim][dim];
		matrizMinas=new int [dim][dim];
		//	Cargar Im�genes
		for(int i=0;i<12;i++)
			imagenesMinas[i]=new ImageIcon(i+".jpg");
                
		//	Panel Superior
		JPanel panelSup=new JPanel();
		panelSup.add(lMinas);
		panelSup.add(txtMinas);
		panelSup.add(lTiempo);
		panelSup.add(txtTiempo);
		add(panelSup,"North");
		txtMinas.setEditable(false);
		txtTiempo.setEditable(false);
		txtMinas.setText(Integer.toString(casillas));
		
		//	Panel de los botones
		JPanel panelMedio=new JPanel(new GridLayout(dim,dim));
		//	Crear y colocar botones
		for(int i=0;i<dim;i++)
			for(int j=0;j<dim;j++)
				{
					//	Crear boton
					botones [i][j]=new JButton();
					//	Colocar en el panel
					panelMedio.add(botones[i][j]);
					//	Action Listener
					botones[i][j].addActionListener(this);
				}
		this.add(panelMedio,"Center");	
		colocarMinas(totalMinas);
        //	Propiedades de la ventana
        
        //	Comenzar Tiempo
        tp= new Tiempo(this);
        tp.start();
        
	    setTitle("Buscaminas v 1.0                    http://todojava.awardspace.com");	
	    setResizable(false);
	    setSize(600,600);
		setVisible(true);
	}
	void colocarMinas(int minas)
	{
		System.out.println("Colocando Minas... \n");
		for(int i=0;i<minas;i++)
		{
		//	Coordenadas
		int x,y=0;
		double x1,y1=0;
		
		/*	Leyenda de matrizMinas
		 *	1 Existe Mina
		 *	0 No existe Mina
		 */
			//Colocar mina aleatoria
			do
			{
                        //	Generar posiciones aleatorias
                         x1=Math.random()*dim;
		 	 y1=Math.random()*dim;
		 	 x=(int)x1;
		 	 y=(int)y1;	
			}
			while(matrizMinas[x][y]!=0);
			matrizMinas[x][y]=1; //	Poner mina
		}
      //	Visualizar Tablero de minas.
	  for(int i=0;i<dim;i++)
	  {
	  	System.out.println("");
	  	for(int j=0;j<dim;j++)
	  		System.out.print(" "+matrizMinas[i][j]);
	  }

	}
	
	public static void main(String []args){
		new buscaminas();
	}
	
	public void actionPerformed(ActionEvent ae) {
    for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
            if (ae.getSource() == botones[i][j] && botones[i][j].getIcon() == null && botones[i][j].getBackground() != Color.WHITE) {
                botones[i][j].setBackground(Color.WHITE);
                if (matrizMinas[i][j] == 1) {
                    // Llama al efecto de explosión al encontrar una mina
                    explosionEffect(i, j);
                } else {
                    pulsarVacio(i, j);
                }
            }
        }
    }
}
	void pulsarVacio(int i, int j)
	{
		//	Al pulsar en una zona vaci�
		casillas--;
        txtMinas.setText(Integer.toString(casillas));
        botones[i][j].setText(Integer.toString(minasCerca(i,j))); //Cuantas Minas cerca
        if(casillas==0)
        			ganar();
	}
	
	void volverEmpezar()
	{
		//	Volver al estado inicial
		for(int i=0;i<dim;i++)
			for(int j=0;j<dim;j++)
			{
				matrizMinas[i][j]=0;
				botones[i][j].setText("");
				botones[i][j].setBackground(null);
				botones[i][j].setIcon(null);
			}
		colocarMinas(totalMinas);
		casillas=dim*dim-totalMinas;
		txtMinas.setText(Integer.toString(casillas));
		tp= new Tiempo(this);
        tp.start();
	}
	
	void ganar()
	{
		//	Al ganar la partida
		tp.stop(); //	parar el tiempo
		JOptionPane.showMessageDialog(this,"Has ganado. Tu tiempo es de: "+txtTiempo.getText());
		volverEmpezar();	
	}
	
	void boom()
	{
	//	Al perder la partida
	tp.stop(); //	parar el tiempo
	for(int i=0;i<dim;i++)
		for(int j=0;j<dim;j++)
		{
		if(matrizMinas[i][j]==1)
			{
			//	Imagen aleatoria de las minas
			double y1=Math.random()*12;
			int y=(int)y1;	
			botones[i][j].setIcon(imagenesMinas[y]);
			}
		}
		JOptionPane.showMessageDialog(this,"Boom!!! Has perdido.");
		volverEmpezar();
	}
	
	int minasCerca(int x, int y) {
            int[][] direcciones = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},          {0, 1},
                {1, -1}, {1, 0}, {1, 1}
            };
            int minas = 0;
            for (int[] dir : direcciones) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                if (nx >= 0 && nx < dim && ny >= 0 && ny < dim && matrizMinas[nx][ny] == 1) {
                    minas++;
                }
            }
            return minas;
        }
        
        
  void explosionEffect(int x, int y) {
    Timer timer = new Timer(100, new ActionListener() {
        int step = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (matrizMinas[i][j] == 1) {
                        // Alternar colores para simular explosión
                        if (step % 2 == 0) {
                            botones[i][j].setBackground(Color.RED); // Destello rojo
                        } else {
                            botones[i][j].setBackground(Color.ORANGE); // Destello naranja
                        }
                    }
                }
            }

            // Terminar animación después de algunos pasos
            if (step > 5) {
                ((Timer) e.getSource()).stop();
                for (int i = 0; i < dim; i++) {
                    for (int j = 0; j < dim; j++) {
                        if (matrizMinas[i][j] == 1) {
                            botones[i][j].setIcon(new ImageIcon("explosion.jpg")); // Imagen de explosión
                        }
                    }
                }
                boom(); // Finalizar el juego
            }
            step++;
        }
    });
    timer.start();
}


}