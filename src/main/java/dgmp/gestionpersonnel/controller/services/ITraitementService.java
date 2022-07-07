package dgmp.gestionpersonnel.controller.services;

import org.springframework.web.multipart.MultipartFile;

public interface ITraitementService
{
    void soumettreDemandeAbsence(Long dmeId);
    void viserDemandeAbsence(Long dmeId, boolean accepte, String motif);
    void viserDemandeAct(Long dmeId, boolean accepte, String motif);
    void editerDemandeAbsence(Long dmeId);
    void attacherAutorisationAbsence(Long dmeId, MultipartFile autorisation);
    void soumettreDemandeAct(Long dmeId);
    void editerAct(Long dmeId);
    void attacherAct(Long dmeId, MultipartFile act);




}
