package dgmp.gestionpersonnel.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor  @ToString
public class TdocumentAdmin {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   doctId;
	private String docNom;
	private String docNbreCopies;
	@ManyToOne
	private TType  docType;


}
