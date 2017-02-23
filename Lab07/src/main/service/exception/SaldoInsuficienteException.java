package main.service.exception;

/**
 * Exceção para saldo insuficiente.
 * Created by rerissondcsm on 14/02/17.
 */
public class SaldoInsuficienteException extends Exception {
    /**
     * {@inheritDoc}
     */
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
