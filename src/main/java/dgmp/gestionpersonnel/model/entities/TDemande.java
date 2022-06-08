package dgmp.gestionpersonnel.model.entities;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import dgmp.gestionpersonnel.model.enums.EtatDemande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data @AllArgsConstructor  @NoArgsConstructor

public class TDemande {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   dmeId;
	private Long dmeDocId;
	@ManyToOne
	private TType dmeType;
	@ManyToOne
	private TAgent dmeDemandeur;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dmeDateDebut;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dmeDateFin;
	private String dmeDate;
	private String dmeMotif;
	@ManyToOne
	private TStructure dmeDestination;
	private int dmeNbreJours;
	@Enumerated(EnumType.STRING)
	private EtatDemande dmeEtat;

	@Override
	public String toString() {
		return "TDemande{" +
				"dmeId=" + dmeId +
				", dmeDocId=" + dmeDocId +
				", dmeType=" + dmeType +
				", dmeDateDebut=" + dmeDateDebut +
				", dmeDateFi=" + dmeDateFin +
				", dmeDate='" + dmeDate + '\'' +
				", dmeMotif='" + dmeMotif + '\'' +
				", dmeDestination='" + dmeDestination + '\'' +
				", dmeNbreJours=" + dmeNbreJours +
				'}';
	}
}




	
	
	
	

