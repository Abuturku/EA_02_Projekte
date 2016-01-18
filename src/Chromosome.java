/**
 * Created by Linda on 18.01.2016.
 */

public class Chromosome implements IChromosome {

    private int budget = 1500;
    private int length = 150;
    private String chromosomeString;

    public Chromosome(String chromosomeString){
        this.chromosomeString = chromosomeString;
    }

    @Override
    public IChromosome generateRandomChromosome() {
        return null;
    }

    public String getChromosome(){
        return null; //TODO

    }

    public boolean isValid(){
        return true; //TODO

    }

    public int getFitness(){
        return 0; //TODO

    }

}
