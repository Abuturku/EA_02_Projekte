import Configuration.Configuration;
import Configuration.CrossoverEnum;

/**
 * Created by Team02 on 18.01.2016.
 */
public class Crossover implements ICrossover {

    private Enum crossType = Configuration.instance.crossoverType;

    public IChromosome[] doCrossover(IChromosome parent1, IChromosome parent2){
        IChromosome[] children = new IChromosome[2];
        if (crossType.equals(CrossoverEnum.OnePoint)){
            children = onePointCO(parent1, parent2);
        }
        if (crossType.equals(CrossoverEnum.TwoPoint)){
            children = twoPointCO(parent1, parent2);
        }
        if (crossType.equals(CrossoverEnum.KPoint)){
            children = kPointCO(parent1, parent2);
        }
        if (crossType.equals(CrossoverEnum.Uniform)){
            children = uniformCO(parent1, parent2);
        }
        return children;
    }

    private IChromosome[] onePointCO(IChromosome parent1, IChromosome parent2){
        IChromosome[] children = new IChromosome[2];

        return children;
    }

    private IChromosome[] twoPointCO(IChromosome parent1, IChromosome parent2){
        IChromosome[] children = new IChromosome[2];

        return children;
    }

    private IChromosome[] kPointCO(IChromosome parent1, IChromosome parent2){
        int k = Configuration.instance.kForCrossOver;
        IChromosome[] children = new IChromosome[2];

        return children;
    }

    private IChromosome[] uniformCO(IChromosome parent1, IChromosome parent2){
        IChromosome[] children = new IChromosome[2];

        return children;
    }

}
