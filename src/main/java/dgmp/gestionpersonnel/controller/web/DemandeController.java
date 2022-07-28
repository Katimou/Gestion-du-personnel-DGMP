package dgmp.gestionpersonnel.controller.web;
import java.io.IOException;
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
	@GetMapping(path = "/FormDemandeDoc")
	public String GoToFormDemandeDoc(Model model) {
		List<TType> typeDmdeDocs=typeRep.findSousType(typeRep.findByTypNom("DEMANDE_ACTES").getTypId());
		model.addAttribute("typeDmdeDocs", typeDmdeDocs);
		model.addAttribute("demande", new TDemande());
		return "DemandeA/dmeDocForm";
	}
	@PostMapping(path = "/save")
    public String saveDemandeA(@ModelAttribute TDemande demande, Model model)throws IOException {
	  	TDemande dme = demandeAServices.saveDemande(demande);
		return "redirect:/demandes/GoToDmeWorkflow?dmeId=" + dme.getDmeId();
	}

	@PostMapping(path = "/saveDmeDocs")
	public String saveDmeDocs(@ModelAttribute TDemande demande, Model model) {
		TDemande dme = demandeAServices.saveDemande(demande);
		model.addAttribute("demande",dme);
		return "redirect:/demandes/GoToDmeWorkflow?dmeId=" + dme.getDmeId();
	}
	@PostMapping(path = "/saveDemandeSortie")
	public String saveSortie(@ModelAttribute TDemande demande, Model model) {
		TDemande dme = demandeAServices.saveDemande(demande);
		return "redirect:/demandes/GoToDmeWorkflow?dmeId=" + dme.getDmeId();
	}
   @GetMapping(path = "/GoToDmeWorkflow")
   public String GoToDmeWorkflow(@RequestParam(name = "dmeId", defaultValue = "") Long dmeId, Model model) {
	  TDemande dme= dmeRep.findById(dmeId).orElse(null);
	  model.addAttribute("demande",dme);
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
	public String DemandeAdresees(Model model) {
		List<TDemande> demandes= dmeRep.getListDmeSentToStrDer(scs.getCurrentAss().getAssStruct().getStrId()) ;
		model.addAttribute("demandes",demandes);
		return "DemandeA/demandesAdressees";
	}
	@PostMapping(path = "/soumissionNiveauSup")
	@PreAuthorize("hasAnyAuthority('RH', 'RESPONSABLE', 'SECRETAIRE')")
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
	@GetMapping(path = "/editer")
	public String editerDemande(@RequestParam(defaultValue = "0") long dmeId) {
		TDemande dme = demandeAServices.etiderAbsence(dmeId);
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

	public String getStyleCSS(TDemande dme)
	{
		switch (dme.getTraitement().getTraiStrDestination().getStrNiveau())
		{
			case 4: return "active";
			case 3: return "active";
			case 2: return "active";
			default:return "";
		}
	}
}
