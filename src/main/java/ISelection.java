import Configuration.SelectionEnum;

public interface ISelection {

	public IChromosome[] getParents(IPopulation population, SelectionEnum selectionType);
}
