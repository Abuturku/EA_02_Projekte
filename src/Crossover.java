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
        int numberOfInvalidPos = 0;
        int[] invalidCOPosition = new int[148];
        int numberOfHealthyChildren = 0;

        do{
            int randomSplit;
            if (numberOfInvalidPos == 0) {
                randomSplit = Configuration.instance.randomGenerator.nextInt(1, 148);
            }
            else if(numberOfInvalidPos==148){
                throw new IllegalStateException( "These parents can't have valid childs... sorry." );
            }
            else {
                boolean tryAgain = false;
                do {
                    randomSplit = Configuration.instance.randomGenerator.nextInt(1, 148);
                    for (int i = 0; i < numberOfInvalidPos; i++) {
                        tryAgain = (invalidCOPosition[i] == randomSplit);
                    }
                } while (tryAgain);
            }
            //here is an untested Splitting Number!
            //breed 1st child
            IChromosome child = new Chromosome( parent1.getChromosome().substring(0,randomSplit).concat(
                                                parent2.getChromosome().substring(randomSplit,149)      )
                                              );
            if (child.isValid()){
                children[numberOfHealthyChildren]=child;
                numberOfHealthyChildren++;
            }
            //breed 2nd child
            IChromosome child = new Chromosome( parent2.getChromosome().substring(0,randomSplit).concat(
                                                parent1.getChromosome().substring(randomSplit,149)      )
                                              );
            if (numberOfHealthyChildren < 2){
                if (child.isValid()){
                    children[numberOfHealthyChildren]=child;
                    numberOfHealthyChildren++;
                }
            }

        }while (children[1]!=null);

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
