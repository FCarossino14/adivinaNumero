package adivinaNumero;

import adivinaNumero.vistas.*;
import excepciones.NumeroFueraDeRangoException;
import static java.awt.Color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Controlador {

    private final int NUM_MAX = 100;
    private final int NUM_MIN = 1;
    private final int NUM_TURNOS = 7;
    private int numSistema;
    private int contadorTurnos;
    private IVista vista;

    public Controlador(IVista vista) {
        this.vista = vista;
    }

    public void iniciar() {
        this.vista.iniciar();
        generarNumero();
        contadorTurnos = 0;
        this.vista.mostrarTurnos(contadorTurnos);
        this.vista.addHandlerAdivinar(new HandlerAdivinar());
        this.vista.addHandlerReiniciar(new HandlerReiniciar());
        this.vista.addHandlerMostrarInfo(new HandlerMostrarInfo());

    }

    private void generarNumero() {
        Random rnd = new Random();
        numSistema = rnd.nextInt(NUM_MAX - NUM_MIN + 1) + NUM_MIN;
    }

    private void adivinar() {
        try {
            revisarInputUsuario();
            contadorTurnos++;
            if (numSistema == pasarInputANum()) {
                this.vista.adivinar("Pulse \"Reiniciar\" para jugar denuevo.");
                this.vista.mostrarMensaje("Felicitaciones! Usted ha ganado en " + contadorTurnos + " turnos.");
                this.vista.activarBotonAdivinar(false);
                this.vista.cambiarColorInfo(GREEN);
            } else if (numSistema > pasarInputANum()) {
                this.vista.adivinar("El numero que has ingresado es menor que el mio.");
                this.vista.cambiarColorInfo(RED);
            } else {
                this.vista.adivinar("El numero que has ingresado es mayor que el mio.");
                this.vista.cambiarColorInfo(RED);
            }
            this.vista.mostrarTurnos(contadorTurnos);
            revisarCantidadDeTurnos();
        } catch (NumeroFueraDeRangoException e) {
            this.vista.mostrarMensajeError(e.getMessage());
        } catch (NumberFormatException e) {
            this.vista.mostrarMensajeError(e.getMessage());
        }
    }

    private void reiniciar() {
        generarNumero();
        contadorTurnos = 0;
        this.vista.mostrarTurnos(contadorTurnos);
        this.vista.adivinar("Ingrese un número.");
        this.vista.reiniciar("Se reinicio el juego");
        this.vista.cambiarColorInfo(black);
    }

    private void revisarInputUsuario() throws NumeroFueraDeRangoException {
        if (pasarInputANum() < NUM_MIN || pasarInputANum() > NUM_MAX) {
            throw new NumeroFueraDeRangoException("Ingrese un número del 1 al 100.");
        }
    }

    private void revisarCantidadDeTurnos() {
        if (contadorTurnos >= NUM_TURNOS && numSistema != pasarInputANum()) {
            this.vista.mostrarMensaje("Lo siento, se ha quedado sin turnos.\nPulse \"Reiniciar\" para intentarlo nuevamente.\nPulsando el boton \"Info\" puede ver una ayuda que lo ayudara a ganar.");
            this.vista.activarBotonAdivinar(false);
        }
    }

    private void mostrarInfo() {
        this.vista.mostrarMensajeInfo("Usted debe adivinar un número entre 1 y 100 en un maximo de 7 intentos.\n" + "En cada intento, la máquina informa si se ha logrado adivinar el número o si fue mayor o menor al esperado.\n" + "Si el usuario no lograr adivinar en un máximo de 7 intentos, el juego termina.\n" + "\nPista: Si usted pone el numero que divide al intervalo (donde podria estar el numero) al medio, no puede perder nunca,\n ya que 2 (dividir por dos) elevado a la 7 (turnos) da 128 que es el numero maximo que se puede adivinar 100% de las veces con 7 turnos.");
    }

    private int pasarInputANum() {
        try {
            return Integer.parseInt(this.vista.getTextUsuario());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"" + this.vista.getTextUsuario() + "\" no es un número. Ingrese un número del 1 al 100.");
        }
    }

    private class HandlerAdivinar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            adivinar();
        }

    }

    private class HandlerReiniciar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            reiniciar();
        }

    }

    private class HandlerMostrarInfo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarInfo();
        }

    }

}
