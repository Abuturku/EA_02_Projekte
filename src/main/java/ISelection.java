import Configuration.SelectionEnum;

/**
 * Created by Linda on 18.01.2016.
 */
public interface ISelection {

    public IChromosome[] getParents(IPopulation population, SelectionEnum selectionType);
}
