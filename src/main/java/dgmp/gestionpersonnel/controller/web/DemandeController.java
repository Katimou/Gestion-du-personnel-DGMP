package dgmp.gestionpersonnel.controller.web;
import java.util.List;

import dgmp.gestionpersonnel.controller.repositories.DemandeRepository;
import dgmp.gestionpersonnel.controller.repositories.TraitementRepository;
import dgmp.gestionpersonnel.controller.services.ITraitementService;
import dgmp.gestionpersonnel.controller.validator.exception.AppException;
import dgmp.gestionpersonnel.security.services.ISecurityContextService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dgmp.gestionpersonnel.controller.repositories.TypeRepository;
import dgmp.gestionpersonnel.controller.services.IDemandeAServices;
import dgmp.gestionpersonnel.model.entities.TDemande;
import dgmp.gestionpersonnel.model.entities.TType;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/demandes")
public class DemandeController
{
	private final IDemandeAServices demandeAServices;
	private final DemandeRepository dmeRep;
	private final TypeRepository typeRep;
	private final ISecurityContextService scs;
	private final ITraitementService traitementService;
	private final TraitementRepository traiRep;
	
	@GetMapping(path = "/GoToFormDemande")
	public String goToFormDmde(Model model) {
	List<TType> typeDmdeAbs=typeRep.findSousType(typeRep.findByTypNom("DEMANDE_ABSENCE").getTypId());
	typeDmdeAbs.removeIf(typ->typ.getTypNom().equalsIgnoreCase("DEMANDE_SORTIE_TERRITOIRE"));
	 model.addAttribute("typeDmdeAbs", typeDmdeAbs);
	 model.addAttribute("demande", new TDemande());
		return "DemandeA/absence";
	}
	@GetMapping(path = "/GoToFormDemandeSortie")
	public String goToFormDemandeSortie(Model model) {

	TType typeDmdeAbs=typeRep.findByTypNom("DEMANDE_SORTIE_TERRITOIRE");
	model.addAttribute("typeDmdeAbs", typeDmdeAbs);
	model.addAttribute("demande", new TDemande());

		return "DemandeA/sortieDuTerritoire";
	}

	@PostMapping(path = "/save")
    public String saveDemandeA(@ModelAttribute TDemande demande, Model model) {
	  	TDemande dme = demandeAServices.saveDemande(demande);
	  return "redirect:/demandes/GoToDmeWorkflow?DmeId";
}
	@PostMapping(path = "/saveDemandeSortie")
	public String saveSortie(@ModelAttribute TDemande demande, Model model) {
		TDemande dme = demandeAServices.saveDemande(demande);
		return "redirect:/demandes/GoToDmeWorkflow?DmeId";
	}
   @GetMapping(path = "/GoToDmeWorkflow")
   public String GoToDmeWorkflow() {
	  
	   return "DemandeA/demandeWorkflow";

}
   @GetMapping(path = "/goToListeDemande")
	public String GoToSuccess(Model model) {
	   List<TDemande> demande= demandeAServices.findDemande();
		model.addAttribute("demandes", demande);
		return "DemandeA/listeDemande";
	}

	@GetMapping(path = "/goToMesDemandes")
	public String goToMesDemandes(Model model) {
		List<TDemande> demandes= dmeRep.findByDemandeur(scs.getAuthUsername());
		model.addAttribute("demandes", demandes);
		return "DemandeA/listeDemande";
	}

   @GetMapping(path = "/ListeDemande")
	public String GoToListeAbscence() {
		return "redirect:/demandes/goToListeDemande";
	}


	@GetMapping(path = "/traitements/soumission")
	public String soumettreDemande(Model model, @RequestParam(defaultValue = "0") long dmeId) {
		TDemande demande = dmeRep.findById(dmeId).orElseThrow(()->new AppException("Demande inxistante"));
		if(typeRep.isSousType("DEMANDE_ABSENCE", demande.getDmeType().getTypNom()))
		   traitementService.soumettreDemandeAbsence(demande.getDmeId());
		else if(typeRep.isSousType("DEMANDE_ACTES", demande.getDmeType().getTypNom()))
			traitementService.soumettreDemandeAct(demande.getDmeId());

		return "redirect:/demandes/goToListeDemande";
	}

	@GetMapping(path = "/DemandeAdressees")
	@PreAuthorize("hasAnyAuthority('RH', 'RESPONSABLE', 'SECRETAIRE')")
	public String traiteur(Model model) {
		List<TDemande> demandes= dmeRep.getListDmeSentToStrDer(scs.getCurrentAss().getAssStruct().getStrId()) ;
		demandes.forEach(d->{d.setTraitements(traiRep.findByDemande(d.getDmeId()));});
		model.addAttribute("demandes",demandes);
		demandes.forEach(System.out::println);

		return "DemandeA/demandesAdressees";
	}
	@PostMapping(path = "/soumissionNiveauSup")
	public String soumissionNiveauSup(Model model, @RequestParam(defaultValue = "0") long dmeId) {
		TDemande demande = dmeRep.findById(dmeId).orElseThrow(()->new AppException("Demande inxistante"));
		if(typeRep.isSousType("DEMANDE_ABSENCE", demande.getDmeType().getTypNom())) {
			traitementService.viserDemandeAbsence(dmeId, true, demande.getDmeMotif());
		}
		else if(typeRep.isSousType("DEMANDE_ACTES", demande.getDmeType().getTypNom()))
			traitementService.soumettreDemandeAct(demande.getDmeId());
		List<TDemande> demandes= dmeRep.getListDmeSentToStrDer(scs.getCurrentAss().getAssStruct().getStrId());
		model.addAttribute("demandes",demandes);
		demandes.forEach(System.out::println);

		return "redirect:/demandes/DemandeAdressees";
	}
	public String getBootstrapClass(TDemande dme)
	{
		switch (dme.getDmeEtat())
		{
			case INITIE : return "initie";
			case SOUMIS: return "soumis";
			case EN_COURS_DE_TRAITEMENT: return "encours";
			case REFUSE: return "refuse";
			case VALIDE: return "valide";
			default: return "";
		}
	}
}
