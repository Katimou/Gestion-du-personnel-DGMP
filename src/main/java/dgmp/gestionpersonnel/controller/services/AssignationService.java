package dgmp.gestionpersonnel.controller.services;

import dgmp.gestionpersonnel.controller.repositories.*;
import dgmp.gestionpersonnel.controller.validator.exception.AppException;
import dgmp.gestionpersonnel.model.entities.TAgent;
import dgmp.gestionpersonnel.model.entities.TAssignation;
import dgmp.gestionpersonnel.model.entities.TRole;
import dgmp.gestionpersonnel.model.entities.TStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AssignationService implements IAssService {
    final AgentRepository agtRep;
    final StructureRepository strRep;
    final RoleRepository rleRep;
    final AssignationRepository assRep;
    final StrParamRepository strParam;
    public void createAss(Long agtId, Long rleId, Long strId) {
        if(!agtRep.existsById(agtId)) throw new AppException("cet Agent n'existe pas avec cet Id");
        if(!strRep.existsById(strId)) throw new AppException("cette structure n'existe pas avec cet Id");
        if(!rleRep.existsById(rleId)) throw new AppException("ce rôle  n'existe pas avec cet Id");
        if(assRep.existsByAssAgent_AgtIdAndAssRole_RleIdAndAssStruct_StrId(agtId,rleId,strId))
        {
            TAssignation ass= assRep.findByAgtStrRle(agtId,strId,rleId);
            ass.setAssCour(true);
            ass.setAssDateFin(LocalDateTime.now().plusYears(1));
            assRep.save(ass);
            return;
       }
            TAssignation assignation = new TAssignation();
            assignation.setAssDateDebut(LocalDateTime.now());
            assignation.setAssDate(LocalDateTime.now());
            assignation.setAssDateFin(LocalDateTime.now().plusYears(1));
            assignation.setAssAgent(new TAgent(agtId));
            assignation.setAssStruct(new TStructure(strId));
            assignation.setAssRole(new TRole(rleId));
            assRep.save(assignation);
    }
}
