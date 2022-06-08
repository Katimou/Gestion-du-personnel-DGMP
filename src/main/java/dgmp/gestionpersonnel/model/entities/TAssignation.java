package dgmp.gestionpersonnel.model.entities;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TAssignation 
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   assId;
	@ManyToOne
	private TAgent  assAgent;
	@ManyToOne
	private  TRole  assRole;
	private LocalDateTime  assDateDebut;
	private LocalDateTime  assDateFin;
	private LocalDateTime  assDate;
	@ManyToOne
	private TStructure assStruct;
	private boolean assCour;

}
