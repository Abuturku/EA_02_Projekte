import Configuration.Configuration;

/**
 * Created by Linda on 18.01.2016.
 */

public class Chromosome implements IChromosome {


    private String chromosomeString;

    public Chromosome(String chromosomeString){
        this.chromosomeString = chromosomeString;
    }

    public Chromosome(){
        generateRandomChromosome();
    }

    @Override
    public IChromosome generateRandomChromosome() {
        boolean isValid = false;

        while(!isValid){
            this.chromosomeString = getRandomChromosomeString();
            //System.out.println("Set new Chromosome: "+ this.chromosomeString);
            isValid = isValid();
        }
        return this;
    }

    private String getRandomChromosomeString(){
        StringBuilder characters = new StringBuilder();
        for(int index = 0; index < Configuration.instance.numberOfProjects; index++){
            if (Configuration.instance.randomGenerator.nextInt(0,100) < 80){
                characters.append(0);
            }else{
                characters.append(1);
            }


        }
        //System.out.println(characters.toString());
        return characters.toString();
    }

    @Override
    public String getChromosome(){
        return chromosomeString;

    }

    @Override
    public boolean isValid(){
        char[] characters = chromosomeString.toCharArray();
        int tempCost = 0;
        for(int index = 0; index < characters.length; index++){
            char character = characters[index];
            if(character == '1'){
                tempCost += Application.PROJECTS[index].getCost();
            }

        }

        if(tempCost >= Configuration.instance.maxBudget){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public int getFitness(){
        char[] characters = chromosomeString.toCharArray();
        int tempFitness = 0;
        for(int index = 0; index < characters.length; index++){
            char character = characters[index];
            if(character == '1'){
                tempFitness += Application.PROJECTS[index].getFitness();
            }

        }

       return tempFitness;

    }

    @Override
    public int compareTo(IChromosome chromosome) {
        int fitnessOne = this.getFitness();
        int fitnessTwo = chromosome.getFitness();
        if(fitnessOne<fitnessTwo){
            return -1;
        }else if(fitnessOne>fitnessTwo){
            return 1;
        }else{
            return 0;
        }
    }


}
