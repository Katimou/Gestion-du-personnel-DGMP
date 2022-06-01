package dgmp.gestionpersonnel;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import dgmp.gestionpersonnel.controller.repositories.TAgentRepository;
import dgmp.gestionpersonnel.controller.repositories.TTypeRepository;
import dgmp.gestionpersonnel.controller.repositories.TTypeStructureParamRepository;
import dgmp.gestionpersonnel.controller.services.IStructureService;
import dgmp.gestionpersonnel.controller.utilities.ArchivageConstants;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TType;
import dgmp.gestionpersonnel.model.entities.TTypeStructureParam;
@SpringBootApplication
public class GestionPersonnelApplication {
	public static void main(String[] args) {
		SpringApplication.run(GestionPersonnelApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(TAgentRepository agentRep, PasswordEncoder passwordEncoder,
			TTypeRepository typeRep, TTypeStructureParamRepository tspRep, IStructureService strService)
	{
		
		return (args)->
		{
			TAgent agent = TAgent.builder()
					.agtUsername("kataye")
					.agtPasswword(passwordEncoder.encode("samie"))
					.agtActive(true)
					.agtTel(56780084)
			    	.agtAdresse("Riviera")
					.agtDateNaiss(new Date())
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
			
			strService.getAllStructureFilles(3L).forEach(s->System.out.println(s.display()));
		
		};
	}
	
	@Bean
	CommandLineRunner  createSystemDirectories() 
	{
		return(args)->
		{
			File uploadDir= new File(ArchivageConstants.UPLOADS_DIR);
			File agtUploadDir= new File(ArchivageConstants.AGENT_UPLOADS_DIR);
			if(!uploadDir.exists()) uploadDir.mkdirs();
			if(!agtUploadDir.exists()) agtUploadDir.mkdirs(); 
			
		};
	}
}
