package dgmp.gestionpersonnel.controller.utilities;

import org.springframework.web.multipart.MultipartFile;

public interface IFilesManager {

	void uploadFile(MultipartFile file, String destinationPath) throws RuntimeException;
	public byte[] downloadFile(String filePAth);

}
