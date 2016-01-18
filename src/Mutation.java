import Configuration.Configuration;
import Configuration.MutationEnum;

import static Configuration.MutationEnum.*;


/**
 * Created by 9364290 on 18.01.16.
 */
public class Mutation implements IMutation {

    @Override
    public IChromosome doMutation(IChromosome chromosome) {
        if (Configuration.instance.mutationType.equals(Displacement)) {
            System.out.println("Displacement");
            return doMutationDisplacement(chromosome);
        }else if(Configuration.instance.mutationType.equals(Exchange)){
            System.out.println("Exchange");
            return doMutationExchange(chromosome);
        }else if(Configuration.instance.mutationType.equals(Insertion)){
            System.out.println("Insertion");
            return doMutationInsertion(chromosome);
        }else if(Configuration.instance.mutationType.equals(Inversion)){
            System.out.println("Inversion");
            return doMutationInversion(chromosome);
        }else if(Configuration.instance.mutationType.equals(Scramble)) {
            System.out.println("Scramble");
            return doMutationScramble(chromosome);
        }

        return null;
    }

    private IChromosome doMutationDisplacement(IChromosome chromosome){
        return null;
    }

    private IChromosome doMutationExchange(IChromosome chromosome){
        return null;
    }

    private IChromosome doMutationInsertion(IChromosome chromosome){
        return null;
    }

    private IChromosome doMutationInversion(IChromosome chromosome){
        return null;
    }

    private IChromosome doMutationScramble(IChromosome chromosome){
        return null;
    }
}
