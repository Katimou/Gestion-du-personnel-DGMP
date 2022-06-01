package dgmp.gestionpersonnel.controller.utilities;

import java.util.Arrays;
import java.util.List;

public class ArchivageConstants
{
	public static final String UPLOADS_DIR = System.getProperty("user.home")
			+ "\\workspace\\dgmp\\gestion_personnel\\docs\\uploads";

	public static final String AGENT_UPLOADS_DIR = System.getProperty("user.home")
			+ "\\workspace\\dgmp\\gestion_personnel\\docs\\uploads\\agent";
	public static final long UPLOAD_MAX_SIZE = 1048576000;
	public static final List<String> PHOTO_AUTHORIZED_TYPE = Arrays.asList("jpeg", "jpg", "png");
	public static final List<String> DOCUMENT_AUTHORIZED_TYPE = Arrays.asList("jpeg", "jpg", "png", "pdf", "doc",
			"docx", "docs", "vnd.openxmlformats-officedocument.wordprocessingml.document");
	
	public static void main(String[] args) {
		System.out.println("user.home = "+ System.getProperty("user.home"));
		System.out.println("UPLOADS_DIR = "+ UPLOADS_DIR);
		System.out.println("AGENT_UPLOADS_DIR = "+ AGENT_UPLOADS_DIR);
	}
}
