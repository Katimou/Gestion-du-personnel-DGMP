package dgmp.gestionpersonnel.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor  @ToString
public class TMouvement{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long mvtId;
	@ManyToOne
	private TStructure mvtStructureOrigine;
	@ManyToOne
	private TStructure mvtStructureDes;
	@ManyToOne
	private TType  docType;


}
