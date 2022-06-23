package dgmp.gestionpersonnel.controller.services;

import java.util.List;

import dgmp.gestionpersonnel.model.entities.TStructure;

public interface IStructureService 
{
	public TStructure createStructure(TStructure structure);
	public TStructure updateStructure(Long strId, TStructure structure);
	public TStructure getStrChildrenTree(Long strId);
	public List<TStructure> getAllStructureFilles(Long strId);


}
