package gov.mg.tce.api.rest.util;

import gov.mg.tce.api.rest.exception.BusinessException;

import javax.ws.rs.core.Response;


public class RetornoRestUtil {
	
	private RetornoRestUtil(){
		
	}
	
	public static Response criarResponse(ExecuteOperation executeOperation) throws Exception{
		
		Response response = null;
		
		try{
			
			response = criarResponseValido(executeOperation.executarAcao());
			
		}catch(BusinessException b){
			b.printStackTrace();
			response = criarResponseInvalido(b);
		}catch(Exception e){
			e.printStackTrace();
			response = criarResponseInvalido(e);
		}
		
		return response;
		
	}
	
	private static Response criarResponseInvalido(BusinessException businessException){
		
		return Response.status(Response.Status.BAD_REQUEST).entity(businessException.getMensagemErro()).build();
	}
	
	private static Response criarResponseInvalido(Exception exception) throws Exception{
		
		return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
	
	private static Response criarResponseValido(Object retorno){
		
		return Response.status(Response.Status.OK).entity(retorno).build();
	}
	
	
	
	public static abstract class ExecuteOperation {
		public abstract Object executarAcao() throws Exception;
	}

}
