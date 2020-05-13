package br.gov.mg.tcemg.exception;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "br.gov.mg.tcemg.exception.TceFault")
public class TceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TceFault tceFault;

	public TceException(){
		super();
	}

	
	public TceException(String message, TceFault tceFault, Throwable cause) {
        super(message, cause);
        this.tceFault = tceFault;
    }

    public TceException(String message, TceFault tceFault) {
        super(message);
        this.tceFault = tceFault;
    }
    
    public TceFault getFaultInfo() {
        return tceFault;
    }
}
