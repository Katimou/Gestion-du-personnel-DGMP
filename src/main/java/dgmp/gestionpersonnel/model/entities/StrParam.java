package dgmp.gestionpersonnel.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StrParam
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strParamId;
    private String strRole;
    @ManyToOne
    private TStructure structure;
}
