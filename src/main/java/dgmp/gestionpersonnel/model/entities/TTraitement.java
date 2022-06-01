package dgmp.gestionpersonnel.model.entities;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor  @NoArgsConstructor @ToString
public class TTraitement
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   traiId;
	private LocalDateTime  traiDate;
	@ManyToOne
	private TStructure traiStrDestination;
	@ManyToOne
	private TAgent traiAgtDestination;
	@ManyToOne
	private TAgent traiAgtTraiteur;
	private String traiMotifRefus;
	@ManyToOne
	private TDemande traiDemande;
	private boolean traiStatutDem;
}




	
	
	
	

