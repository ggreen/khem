package khem.solutions.cheminformatics.exception;

import nyla.solutions.core.exception.fault.FaultException;

/**
 * @author Gregory Green
 *
 */
public class KhemException extends FaultException
{
	
	public KhemException()
	{
		this.setCode("KHEM000001");
	}

	
	public KhemException(String aID, String aMessage)
	{
		super(aID, aMessage);
	}


	public KhemException(String message, Throwable cause)
	{
		super(message, cause);
	}


	public KhemException(String message)
	{
		super(message);
	}


	public KhemException(Throwable cause, String functionName,
			String errorCategory, String errorCode, String programName)
	{
		super(cause, functionName, errorCategory, errorCode, programName);
	}


	public KhemException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -4109312246029082467L;

}
