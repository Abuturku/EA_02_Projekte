/**
 * Created by Linda on 18.01.2016.
 */
public interface IChromosome {

    public String generateRandomChromosome();

    public String getChromosome();

    public boolean isValid();

    public int getFitness();

}
