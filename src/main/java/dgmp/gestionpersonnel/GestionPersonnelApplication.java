package dgmp.gestionpersonnel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import dgmp.gestionpersonnel.controller.repositories.*;
import dgmp.gestionpersonnel.controller.services.EmailServiceImpl;
import dgmp.gestionpersonnel.controller.services.JasperReportServicePdf;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import dgmp.gestionpersonnel.controller.services.IStructureService;
import dgmp.gestionpersonnel.controller.utilities.ArchivageConstants;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;

@Controller
@SpringBootApplication
public class GestionPersonnelApplication {

	@Autowired
	private JasperRepository repository;
	@Autowired
	private JasperReportServicePdf service;
	@Autowired

	@GetMapping("/getEmployees")
	public List<TAgent> getEmployees() {

		return repository.findAll();
	}


	@GetMapping("/report/{format}")
	public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
		service.exportReport(format);
		return "/agents/jasperFont";
	}
	@Bean
	CommandLineRunner start(AgentRepository agentRep, PasswordEncoder passwordEncoder,
							TypeRepository typeRep, TypeStructureParamRepository tspRep, IStructureService strService) {

		return (args) ->
		{
			TAgent agent = TAgent.builder()
					.agtUsername("kataye")
					.agtPasswword(passwordEncoder.encode("samie"))
					.agtActive(true)
					.agtTel("56780084")
					.agtAdresse("Riviera")
					.agtEmail("nomakatimou3@gmail.com")
					.agtFonction("DGA")
					.agtGrade("A5")
					.agtNationnalite("Ivoirienne")
					.agtNom("Halidou")
					.agtPrenom("Katimou")
					.agtSituationMatri("Célibataire")
					.build();
//		           agentRep.save(agent); 
			
			/*typeRep.save(new TType(null,"DG","TYP_STR"));
			typeRep.save(new TType(null,"DC","TYP_STR"));
			typeRep.save(new TType(null,"DR","TYP_STR"));
			typeRep.save(new TType(null,"SD","TYP_STR"));
			typeRep.save(new TType(null,"SCE","TYP_STR"));
			typeRep.save(new TType(null,"CAB","TYP_STR"));
			typeRep.save(new TType(null,"SEC","TYP_STR"));
			
			TType DG = typeRep.findById(1L).get();
			TType DC = typeRep.findById(3L).get();
			TType DR = typeRep.findById(4L).get();
			TType SD = typeRep.findById(5L).get();
			TType SCE = typeRep.findById(6L).get();
			TType SEC = typeRep.findById(8L).get();
			TType CAB = typeRep.findById(7L).get();
			
			Arrays.asList(DC, DR, SD, SCE, SEC, CAB).forEach(type->tspRep.save(new TTypeStructureParam(null, DG, type)));
			Arrays.asList(SD, SCE, SEC).forEach(type->tspRep.save(new TTypeStructureParam(null, DC, type)));
			Arrays.asList(SCE, SEC).forEach(type->tspRep.save(new TTypeStructureParam(null, DR, type)));
			Arrays.asList(SCE, SEC).forEach(type->tspRep.save(new TTypeStructureParam(null, SD, type)));
			Arrays.asList(SEC).forEach(type->tspRep.save(new TTypeStructureParam(null, SCE, type)));	*/

			TType DG = typeRep.findById(1L).get();
			TType DC = typeRep.findById(3L).get();
			TType DR = typeRep.findById(4L).get();
			TType SD = typeRep.findById(5L).get();
			TType SCE = typeRep.findById(6L).get();
			TType SEC = typeRep.findById(8L).get();
			TType CAB = typeRep.findById(7L).get();

			typeRep.findSousType(DC.getTypId()).forEach(System.out::println);

			strService.getAllStructureFilles(3L).forEach(s -> System.out.println(s.display()));


		};
	}

	public static void main(String[] args) {
		SpringApplication.run(GestionPersonnelApplication.class, args);
	}
	@Bean
	CommandLineRunner createSystemDirectories() {
		return (args) ->
		{
			File uploadDir = new File(ArchivageConstants.UPLOADS_DIR);
			File agtUploadDir = new File(ArchivageConstants.AGENT_UPLOADS_DIR);
			if (!uploadDir.exists()) uploadDir.mkdirs();
			if (!agtUploadDir.exists()) agtUploadDir.mkdirs();

		};

	}
}
