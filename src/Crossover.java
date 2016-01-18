import Configuration.Configuration;
import Configuration.CrossoverEnum;

import java.util.Arrays;

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
            child = new Chromosome( parent2.getChromosome().substring(0,randomSplit).concat(
                                                parent1.getChromosome().substring(randomSplit,149)      )
                                              );
            if (numberOfHealthyChildren < 2){
                if (child.isValid()){
                    children[numberOfHealthyChildren]=child;
                    numberOfHealthyChildren++;
                }
            }

        } while ( numberOfHealthyChildren <2 );

        return children;
    }

    private IChromosome[] twoPointCO(IChromosome parent1, IChromosome parent2){
        IChromosome[] children = new IChromosome[2];
        int numberOfHealthyChildren = 0;

        do{
            int randomSplit1;
            int randomSplit2;
            randomSplit1 = Configuration.instance.randomGenerator.nextInt(1, 148);
            do{
                randomSplit2 = Configuration.instance.randomGenerator.nextInt(1, 148);
            } while (randomSplit1 == randomSplit2);

            int firstSplit = (randomSplit1 > randomSplit2) ? randomSplit1 : randomSplit2;
            int secondSplit = (randomSplit1 < randomSplit2) ? randomSplit1 : randomSplit2;

            //breed 1st child
            IChromosome child = new Chromosome( parent1.getChromosome().substring(0,firstSplit).concat(
                                                parent2.getChromosome().substring(firstSplit,secondSplit)).concat(
                                                parent1.getChromosome().substring(secondSplit,149))
            );
            if (child.isValid()){
                children[numberOfHealthyChildren]=child;
                numberOfHealthyChildren++;
            }
            //breed 2nd child
            child = new Chromosome( parent2.getChromosome().substring(0,firstSplit).concat(
                                    parent1.getChromosome().substring(firstSplit,secondSplit)).concat(
                                    parent2.getChromosome().substring(secondSplit,149))
            );
            if (numberOfHealthyChildren < 2){
                if (child.isValid()){
                    children[numberOfHealthyChildren]=child;
                    numberOfHealthyChildren++;
                }
            }

        } while ( numberOfHealthyChildren <2 );

        return children;
    }

    private IChromosome[] kPointCO(IChromosome parent1, IChromosome parent2){
        int k = Configuration.instance.kForCrossOver;
        if(k<3){
            throw new IllegalArgumentException( "Please use OnePoint for k=1 or TwoPoint for k=2. " +
                                                "kPoint works only for values grater than 2." );
        }

        IChromosome[] children = new IChromosome[2];
        int numberOfHealthyChildren = 0;

        do{
            int[] randomSplit = new int[k];
            randomSplit[0] = Configuration.instance.randomGenerator.nextInt(1, 148);
            for (int i=1; i<k; i++){
                boolean isNew = true;
                randomSplit[i] = Configuration.instance.randomGenerator.nextInt(1, 148);
                for (int j=1; j<i; j++){
                    isNew=randomSplit[i]==randomSplit[j];
                    if (isNew) { break; }
                }
            }

            int[] sortedSplit = new int[k+2];
            sortedSplit = randomSplit;
            sortedSplit[k]=0;
            sortedSplit[k+1]=149;
            Arrays.sort(sortedSplit);

            //breed 1st child
            String childChromosome1 = "";
            for (int i=0; i<k ; i++){
                IChromosome currentParent = (i%2==0) ? parent1 : parent2;
                childChromosome1 += currentParent.getChromosome().substring(sortedSplit[i],sortedSplit[i+1]);
            }

            IChromosome child = new Chromosome( childChromosome1 );
            if (child.isValid()){
                children[numberOfHealthyChildren]=child;
                numberOfHealthyChildren++;
            }
            //breed 2nd child
            String childChromosome2 = "";
            for (int i=0; i<k ; i++){
                IChromosome currentParent = (i%2==0) ? parent1 : parent2;
                childChromosome2 += currentParent.getChromosome().substring(sortedSplit[i],sortedSplit[i+1]);
            }

            child = new Chromosome( childChromosome2 );
            if (numberOfHealthyChildren < 2){
                if (child.isValid()){
                    children[numberOfHealthyChildren]=child;
                    numberOfHealthyChildren++;
                }
            }

        } while ( numberOfHealthyChildren <2 );

        return children;
    }

    private IChromosome[] uniformCO(IChromosome parent1, IChromosome parent2){
        IChromosome[] children = new IChromosome[2];
        float ratio = 100*Configuration.instance.mixingRation; //in Prozent
        if(ratio<=0||ratio>=100){
            throw new IllegalArgumentException( "The mixing ratio has to be a value between 0 and 1." );
        }

        char[] caParent1 = parent1.getChromosome().toCharArray();
        char[] caParent2 = parent2.getChromosome().toCharArray();
        char[] caChild1 = new char[149];
        char[] caChild2 = new char[149];

        int parent1Full = (int) (1.5*ratio);
        int parent2Full = (int) (1.5*ratio);
        int countParent1 = 0;
        int countParent2 = 0;
        char caFirstParent;
        char caSecondParent;

        int numberOfHealthyChildren = 0;
        do{
            for(int i=0 ; i < 149 ; i++){
                int randNr = Configuration.instance.randomGenerator.nextInt(0,100);

                if( countParent1<parent1Full && randNr <= ratio ){
                    caChild1[i] = caParent1[i];
                    caChild2[i] = caParent2[i];
                }
                else if( countParent2<parent2Full && randNr > ratio){
                    caChild1[i] = caParent2[i];
                    caChild2[i] = caParent1[i];
                }
            }

            IChromosome child = new Chromosome( caChild1.toString() );
            if (child.isValid()){
                children[numberOfHealthyChildren]=child;
                numberOfHealthyChildren++;
            }

            child = new Chromosome( caChild2.toString() );
            if (numberOfHealthyChildren < 2){
                if (child.isValid()){
                    children[numberOfHealthyChildren]=child;
                    numberOfHealthyChildren++;
                }
            }

        } while ( numberOfHealthyChildren <2 );

        return children;
    }

}
