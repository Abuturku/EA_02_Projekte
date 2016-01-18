import Configuration.Configuration;

/**
 * Created by Linda on 18.01.2016.
 */

public class Chromosome implements IChromosome {

    private int budget = 1500;
    private int length = 150;
    private String chromosomeString;

    public Chromosome(String chromosomeString){
        this.chromosomeString = chromosomeString;
        generateRandomChromosome();
    }

    @Override
    public IChromosome generateRandomChromosome() {
        boolean isValid = false;

        while(!isValid){
            this.chromosomeString = getRandomChromosomeString();
            System.out.println("Set new Chromosome: "+ this.chromosomeString);
            isValid = isValid();
        }
        return this;
    }

    private String getRandomChromosomeString(){
        char[] characters = new char[length];
        for(int index = 0; index < characters.length; index++){
            characters[index]= (char) Configuration.instance.randomGenerator.nextInt(0,1);
        }


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

        if(tempCost >= this.budget){
            System.out.println("Chromosome ist not Valid");
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

}
