package dgmp.gestionpersonnel.model.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor  @NoArgsConstructor @ToString

public class TAbsence {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   absId;
	@ManyToOne
	private TType 	absType;
	
}
