package dgmp.gestionpersonnel.model.entities;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter @AllArgsConstructor  @NoArgsConstructor @ToString @Builder

public class TAgent {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   agtId;
//	@NotNull
//	@Size(min=3, max=30)
	private String agtNom;
//	@NotNull
//	@Size(min=3, max=30)
	private String agtPrenom;
//	@NotNull
//	@Size(min=3, max=30)
    private String agtEmail;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date agtDateNaiss;
//	private LocalDateTime agtDateNaiss;
	private String agtGrade;
	private String    agtTel;
	private String   agtGenre;
	private String agtAdresse;
	@ManyToOne 
	private TStructure agtStructure;
	private String agtMatricule;
	private String agtSituationMatri;
	private String agtFonction;
	private String agtUsername;
	private String agtPasswword;
	@ManyToOne
	private TType agtTypeMvt;
	@ManyToOne
	private TStructure structureAffecte;
	@Transient
	private String agtRePasswword;
	private boolean agtActive;
	private String agtNationnalite;
	private String agtService;
	private String agtPhotoPath;
	@Transient
	private MultipartFile agtPhotoFile;

    public TAgent(Long agtId) {
    	this.agtId=agtId;
    }

    public String display()
	{
		return agtMatricule == null ? String.format("%s %s", agtNom, agtPrenom) :String.format("%s %s (%s)", agtNom, agtPrenom, agtMatricule);
	}

	public boolean isResponsable()
	{
		if(this.agtStructure.getStrRespo()==null) return false;
		return this.agtStructure.getStrRespo().getAgtId().equals(this.agtId);
	}
}




	
	
	
	

