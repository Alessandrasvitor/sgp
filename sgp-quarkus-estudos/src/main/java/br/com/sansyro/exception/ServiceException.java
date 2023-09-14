package br.com.sansyro.exception;

public class ServiceException extends RuntimeException {

    /**
     * Cria nova instancia da classe ServicoException
     */
    public ServiceException() {
        super();
    }

    /**
     * Cria nova instancia da classe ServicoException
     *
     * @param message Mensagem a ser enviada na exception
     */
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception ex) {
        super(ex);
    }

    /**
     * Cria nova instancia da classe ServicoException
     *
     * @param msg mensagem a ser enviada na exception
     * @param ex  Exception original a ser enviada
     */
    public ServiceException(String msg, Exception ex) {
        super(msg, ex);
    }
}
