package dgmp.gestionpersonnel.controller.web;
import dgmp.gestionpersonnel.controller.repositories.AgentRepository;
import dgmp.gestionpersonnel.controller.repositories.MouvementRepository;
import dgmp.gestionpersonnel.controller.repositories.StructureRepository;
import dgmp.gestionpersonnel.controller.services.IMouvementService;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TMouvement;
import dgmp.gestionpersonnel.model.entities.TStructure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/mouvements")
@Slf4j
public class MouvementController {
    public  final IMouvementService mvtServices;
    public final AgentRepository agtRep;
    public  final StructureRepository strRep;
    public  final MouvementRepository mvtRep;



    @GetMapping(path = "/GoToaffecter")
    public String GoToaffecter(@RequestParam(name = "idAgent", defaultValue = "") Long id, Model model) {
        TAgent agtAffecte= agtRep.findById(id).orElse(null);
        List<TStructure> structures= strRep.findAll();
        TMouvement mvt= new TMouvement();
        mvt.setMvtAgent(agtAffecte);
        mvt.setMvtStructureOrigine(agtAffecte.getAgtStructure());
        model.addAttribute("mvt",mvt);
        model.addAttribute("structures",structures);
        return "agents/affecter";
    }

    @PostMapping(path = "/saveAffectation")
    public String affecter(TMouvement mvt,Model model) { ;
        TAgent agt=mvt.getMvtAgent();
        mvtServices.Affecter(mvt,agt.getAgtId());
        return "redirect:/mouvements/AffectationSuccess?mvtId=" + mvt.getMvtId();
    }

    @GetMapping(path = "/AffectationSuccess")
    public String AffectationSuccess(@RequestParam(name = "mvtId", defaultValue = "") Long id,Model model) { ;
        model.addAttribute("mouvement",mvtRep.findById(id).orElse(null));
        return "mouvements/AffectationSuccess";
    }




}


