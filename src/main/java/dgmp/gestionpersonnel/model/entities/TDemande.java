package dgmp.gestionpersonnel.model.entities;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

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
	private String dmeLieuDepart;
	@ManyToOne
	private TStructure dmeDestination;
	private int dmeNbreJours;
	@Enumerated(EnumType.STRING)
	private EtatDemande dmeEtat;
	@ManyToOne
	private  TTraitement traitement;

	@Transient
	private List<TTraitement> traitements;

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




	
	
	
	

