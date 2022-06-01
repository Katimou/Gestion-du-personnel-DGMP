package dgmp.gestionpersonnel.controller.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import dgmp.gestionpersonnel.controller.validator.exception.AppException;

@Component
public class FilesManager implements IFilesManager
{
	@Override
	public void uploadFile(MultipartFile file, String destinationPath) throws RuntimeException
	{
		long fileSize = file.getSize();
		List<String> authorizedTypes = ArchivageConstants.DOCUMENT_AUTHORIZED_TYPE;

		if (fileSize > ArchivageConstants.UPLOAD_MAX_SIZE)
		{
			throw new AppException("Taille de fichier > " + (ArchivageConstants.UPLOAD_MAX_SIZE / 1000) + " Mo");
		}

		// Si l'extension du fichier n'est pas contenu dans la liste des types
		// authorisés
		if (!authorizedTypes.stream().anyMatch(type -> type.equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename()))))
		{
			System.out.println("type fichier = " + FilenameUtils.getExtension(file.getOriginalFilename()));
			throw new AppException("Type de fichier non pris en charge");
		}
		try
		{
			Files.write(Paths.get(destinationPath+"."+FilenameUtils.getExtension(file.getOriginalFilename())), file.getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new AppException("Erreur de chargement du fichier");
		}
	}

	@Override
	public byte[] downloadFile(String filePAth)
	{
		File file = new File(filePAth);
		Path path = Paths.get(file.toURI());
		try
		{
			return Files.readAllBytes(path);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new AppException("Erreur de chargement du fichier");
		}
	}
}
