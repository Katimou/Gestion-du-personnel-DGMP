package dgmp.gestionpersonnel.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor  @ToString
public class TRole{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long RleId;
	private String RleNom;

}
