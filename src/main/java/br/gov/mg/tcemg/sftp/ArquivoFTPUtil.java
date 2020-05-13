package br.gov.mg.tcemg.sftp;

import java.io.File;
import java.nio.file.Files;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

public class ArquivoFTPUtil {

	public static void main(String[] args) {

		String arquivo = "ArquivoFTPUtil";
		ArquivoFTPUtil.upLoadAquivo("vaticano.tce.mg.gov.br", "homolog", "1q2w3e4r5t6y", "homolog_envio/testedaniel.txt", arquivo.getBytes());
		ArquivoFTPUtil.downloadAquivo("vaticano.tce.mg.gov.br", "homolog", "1q2w3e4r5t6y", "homolog_envio/testedaniel.txt", "C:\\ambiente\\Documentos\\copia.txt");
		ArquivoFTPUtil.deleteAquivo("vaticano.tce.mg.gov.br", "homolog", "1q2w3e4r5t6y", "homolog_envio/testedaniel.txt");
	}

	public ArquivoFTPUtil() {
		super();
	}

	public static final boolean upLoadAquivo(String serverAddress, String userId, String password, String remoteDirectory, byte[] arquivo) {

		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			// Initializes the file manager
			manager.init();

			// Setup our SFTP configuration
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000000);

			// Create the SFTP URI using the host name, userid, password, remote
			// path and file name
			String sftpUri = "sftp://" + userId + ":" + password + "@" + serverAddress + "/" + remoteDirectory;

			// Create local file object
			File temp = new File("temp");
			Files.write(temp.toPath(), arquivo);
			FileObject localFile = manager.resolveFile(temp.getAbsolutePath());

			// Create remote file object
			FileObject remoteFile = manager.resolveFile(sftpUri, opts);

			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
			System.out.println("File upload successful");

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			manager.close();
		}

		return true;
	}

	public static final boolean downloadAquivo(String serverAddress, String userId, String password, String remoteDirectory, String localDirectory) {

		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {

			// Initializes the file manager
			manager.init();

			// Setup our SFTP configuration
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

			// Create the SFTP URI using the host name, userid, password, remote
			// path and file name
			String sftpUri = "sftp://" + userId + ":" + password + "@" + serverAddress + "/" + remoteDirectory;

			// Create local file object
			String filepath = localDirectory;
			File file = new File(filepath);
			FileObject localFile = manager.resolveFile(file.getAbsolutePath());

			// Create remote file object
			FileObject remoteFile = manager.resolveFile(sftpUri, opts);

			// Copy local file to sftp server
			localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
			System.out.println("File download successful");

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			manager.close();
		}

		return true;
	}

	public static final boolean deleteAquivo(String serverAddress, String userId, String password, String remoteDirectory) {

		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {

			// Initializes the file manager
			manager.init();

			// Setup our SFTP configuration
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

			// Create the SFTP URI using the host name, userid, password, remote
			// path and file name
			String sftpUri = "sftp://" + userId + ":" + password + "@" + serverAddress + "/" + remoteDirectory;

			// Create remote file object
			FileObject remoteFile = manager.resolveFile(sftpUri, opts);

			// Check if the file exists
			if (remoteFile.exists()) {
				remoteFile.delete();
				System.out.println("File delete successful");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			manager.close();
		}

		return true;
	}
}
