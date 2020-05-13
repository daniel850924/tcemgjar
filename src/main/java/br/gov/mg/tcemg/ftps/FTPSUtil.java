package br.gov.mg.tcemg.ftps;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;

/**
 * Classe de client para conexao com servidores que utilizem o protocolo
 * <b>SSL</b> para comunicacao com criptografia explicita.
 * 
 * @author rafael.lima
 *
 */
public final class FTPSUtil {

	private static final String PROTOCOLO_SSL = "SSL";

	public static final boolean uploadArquivo(String server, String username, String password, String remoteDirectory, byte[] arrayFile) {

		FTPSClient ftps = new FTPSClient(PROTOCOLO_SSL);

		/*
		 * Descomentar a linha de codigo abaixo quando quiser exibir as
		 * mensagens de comunicacao com o servidor FTP.
		 */
		// ftps.addProtocolCommandListener(new PrintCommandListener(new
		// PrintWriter(System.out)));

		try {

			ftps.connect(server);

			if (!ftps.login(username, password)) {

				ftps.logout();

				return false;

			}

			ftps.setFileType(FTP.CARRIAGE_CONTROL_TEXT_FORMAT);

			/*
			 * Utilizando o modo passivo.
			 */
			ftps.enterLocalPassiveMode();

			InputStream inputStream = new ByteArrayInputStream(arrayFile);

			ftps.storeFile(remoteDirectory, inputStream);

			inputStream.close();

			ftps.logout();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (ftps.isConnected()) {

				try {

					ftps.disconnect();

				} catch (IOException ioex) {

					ioex.printStackTrace();

				}

			}

		}

		return false;

	}

	public static final byte[] downloadArquivo(String server, String username, String password, String remoteDirectory) {

		FTPSClient ftps = new FTPSClient(PROTOCOLO_SSL);

		/*
		 * Descomentar a linha de codigo abaixo quando quiser exibir as
		 * mensagens de comunicacao com o servidor FTP.
		 */
//		ftps.addProtocolCommandListener(new PrintCommandListener(new
//		PrintWriter(System.out)));

		try {

			ftps.connect(server);

			if (!ftps.login(username, password)) {

				ftps.logout();
				
				System.out.println("Não foi possível efetuar login no servidor.");

				return null;

			}
			  
	        //Indica que não será utilizado buffer de proteção (requisito para acessar o canal privado)  
			ftps.execPBSZ(0);
			
			//Indica que o canal de comunicação será privado
			ftps.execPROT("P");
			
			//Indica a utilização do modo passivo
			ftps.enterLocalPassiveMode();
			
			//Define o tipo de arquivo que será recuperado
			ftps.setFileType(FTP.BINARY_FILE_TYPE);
			
			InputStream inputStream = ftps.retrieveFileStream(remoteDirectory);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int nextByte = inputStream.read();

			while (nextByte != -1) {

				baos.write(nextByte);
				nextByte = inputStream.read();

			}
			
			boolean success = ftps.completePendingCommand();
			
			if(success){
				System.out.println("Download realizado com sucesso.");
			}else{
				System.out.println("Download não realizado.");
			}
			
			inputStream.close();
			baos.close();

			ftps.logout();

			return baos.toByteArray();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (ftps.isConnected()) {

				try {

					ftps.disconnect();

				} catch (IOException ioex) {

					ioex.printStackTrace();

				}

			}

		}

		return null;

	}

}