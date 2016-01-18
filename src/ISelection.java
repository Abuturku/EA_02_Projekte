import Configuration.SelectionEnum;

/**
 * Created by Linda on 18.01.2016.
 */
public interface ISelection {

    public Chromosome[] topTenPercentOfPopulation(IPopulation population);

    public IChromosome[] getParents(IPopulation population, SelectionEnum selectionType);
}
