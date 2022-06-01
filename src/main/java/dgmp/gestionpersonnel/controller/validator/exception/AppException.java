package dgmp.gestionpersonnel.controller.validator.exception;

public class AppException extends RuntimeException
{
	public String ErrorMsg;

	public AppException(String errorMsg) 
	{
		super(errorMsg);
		ErrorMsg = errorMsg;
	}
	

}
