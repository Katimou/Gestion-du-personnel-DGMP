package dgmp.gestionpersonnel.model.entities;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor  @NoArgsConstructor @ToString

public class TStructure {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long   strId;
	private String strNomStruc;
	private String strSigle;
	private String strSiteGeo;
	private int strNiveau;
	@ManyToOne
	private TType strType;
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	private TStructure strTutelleDirecte;
	
	@OneToMany(mappedBy = "strTutelleDirecte")
	private List<TStructure> strStructuresFilles;
	@OneToMany(mappedBy = "agtStructure")
	private List<TAgent> strAgents;
	@ManyToOne
	private TAgent strRespo;
	
	public String display()
	{
		return strNomStruc + " (" + strSigle +")";
	}
	
	
}




	
	
	
	

