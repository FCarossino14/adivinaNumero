package adivinaNumero;

import adivinaNumero.vistas.*;
import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {
        
        IVista vista = new VistaSwing();
        Controlador c = new Controlador(vista);
        c.iniciar();
    }

}
