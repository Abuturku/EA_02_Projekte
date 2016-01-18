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
        StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
        int start = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());
        int end = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());

        if(start>end){
            int temp = start;
            start = end;
            end = temp;
        }

        System.out.println("Displace Chromosome: " + chromosomeString.toString());

        String toDisplace = chromosomeString.substring(start,end);
        chromosomeString.delete(start,end);


        int displaceIndex = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());
        chromosomeString.insert(displaceIndex, toDisplace);

        System.out.println("New Chromosome: " + chromosomeString.toString());

        IChromosome newChromosome = new Chromosome(chromosomeString.toString());
        if(newChromosome.isValid()){
            return newChromosome;
        }else{
            System.out.println("Chromosome is not Valid. Create random Chromosome");
            return chromosome.generateRandomChromosome();
        }
    }

    private IChromosome doMutationExchange(IChromosome chromosome){
        StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
        int indexOne = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());
        int indexTwo = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());

        char charOne = chromosomeString.charAt(indexOne);
        char charTwo = chromosomeString.charAt(indexTwo);

        System.out.print("Swap Character on '" + indexOne + "' and '" + indexTwo + "' on Chromosome: " + chromosomeString.toString());

        chromosomeString.deleteCharAt(indexOne);
        chromosomeString.insert(indexOne, charTwo);

        chromosomeString.deleteCharAt(indexTwo);
        chromosomeString.insert(indexOne, charOne);

        System.out.println("New Chromosome: " + chromosomeString.toString());

        IChromosome newChromosome = new Chromosome(chromosomeString.toString());
        if(newChromosome.isValid()){
            return newChromosome;
        }else{
            System.out.println("Chromosome is not Valid. Create random Chromosome");
            return chromosome.generateRandomChromosome();
        }
    }

    private IChromosome doMutationInsertion(IChromosome chromosome){
        StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
        int indexCut = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());
        int indexPaste = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());

        char cutChar = chromosomeString.charAt(indexCut);

        System.out.println("Cut char '" + cutChar + "' at '"+ indexCut + "' from Chromosome: " + chromosomeString.toString());

        chromosomeString.deleteCharAt(indexCut);
        chromosomeString.insert(indexPaste, cutChar);

        System.out.println("New Chromosome: " + chromosomeString.toString());

        IChromosome newChromosome = new Chromosome(chromosomeString.toString());
        if(newChromosome.isValid()){
            return newChromosome;
        }else{
            System.out.println("Chromosome is not Valid. Create random Chromosome");
            return chromosome.generateRandomChromosome();
        }
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
            System.out.println("Chromosome is not Valid. Create random Chromosome");
            return chromosome.generateRandomChromosome();
        }
    }

    private IChromosome doMutationScramble(IChromosome chromosome){
        StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
        int start = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());
        int end = Configuration.instance.randomGenerator.nextInt(chromosomeString.length());

        if(start>end){
            int temp = start;
            start = end;
            end = temp;
        }

        System.out.println("Mix Chromosome: " + chromosomeString.toString());

        StringBuilder toMix = new StringBuilder(chromosomeString.substring(start,end));

        for(int i = 0 ; i < toMix.length(); i++){

            int indexOne = Configuration.instance.randomGenerator.nextInt(toMix.length());
            int indexTwo = Configuration.instance.randomGenerator.nextInt(toMix.length());

            char charOne = toMix.charAt(indexOne);
            char charTwo = toMix.charAt(indexTwo);

            toMix.deleteCharAt(indexOne);
            toMix.insert(indexOne, charTwo);

            toMix.deleteCharAt(indexTwo);
            toMix.insert(indexOne, charOne);

        }

        chromosomeString.replace(start,end, toMix.toString());

        System.out.println("New chromosome: " + chromosomeString.toString());

        IChromosome newChromosome = new Chromosome(chromosomeString.toString());
        if(newChromosome.isValid()){
            return newChromosome;
        }else{
            System.out.println("Chromosome is not Valid. Create random Chromosome");
            return chromosome.generateRandomChromosome();
        }
    }
}
