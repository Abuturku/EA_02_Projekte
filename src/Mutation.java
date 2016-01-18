import Configuration.Configuration;
import Configuration.MutationEnum;

import static Configuration.MutationEnum.*;


/**
 * Created by 9364290 on 18.01.16.
 */
public class Mutation implements IMutation {

    @Override
    public IChromosome doMutation(IChromosome chromosome) {

        if(Configuration.instance.randomGenerator.nextFloat() <= Configuration.instance.mutationRatio){
            if (Configuration.instance.mutationType.equals(Displacement)) {
                System.out.println("Mutation: Displacement");
                return doMutationDisplacement(chromosome);
            }else if(Configuration.instance.mutationType.equals(Exchange)){
                System.out.println("Mutation: Exchange");
                return doMutationExchange(chromosome);
            }else if(Configuration.instance.mutationType.equals(Insertion)){
                System.out.println("Mutation: Insertion");
                return doMutationInsertion(chromosome);
            }else if(Configuration.instance.mutationType.equals(Inversion)){
                System.out.println("Mutation: Inversion");
                return doMutationInversion(chromosome);
            }else if(Configuration.instance.mutationType.equals(Scramble)) {
                System.out.println("Mutation: Scramble");
                return doMutationScramble(chromosome);
            }

            System.out.println("Incorrect ENUM Type");
            return chromosome;
        }else{
            return chromosome;
        }
    }

    private IChromosome doMutationDisplacement(IChromosome chromosome){
        return null;
    }

    private IChromosome doMutationExchange(IChromosome chromosome){
        return null;
    }

    private IChromosome doMutationInsertion(IChromosome chromosome){
        StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
        int index = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());

        return null;

    }

    private IChromosome doMutationInversion(IChromosome chromosome){
        StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
        int start = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());
        int end = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());

        if(start>end){
            int temp = start;
            start = end;
            end = temp;
        }

        System.out.println("Reverse chromosome: " + chromosomeString.toString());

        StringBuilder toTurn = new StringBuilder(chromosomeString.substring(start,end));
        toTurn.reverse();
        chromosomeString.replace(start,end,toTurn.toString());

        System.out.println("New chromosome: " + chromosomeString.toString());

        IChromosome newChromosome = new Chromosome(chromosomeString.toString());
        if(newChromosome.isValid()){
            return newChromosome;
        }else{
            return null;
        }
    }

    private IChromosome doMutationScramble(IChromosome chromosome){
        return null;
    }
}
