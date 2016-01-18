import Configuration.Configuration;
import com.sun.org.apache.bcel.internal.generic.POP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Application {
    public static final IProject[] PROJECTS = new Project[150];
    public final int populationAmount = 100;


    public static void main(String[] args) {

        Application obj = new Application();



    }

    public Population generatePopulation(){
        Population population = new Population(new Chromosome[Configuration.instance.populationSize]);
        for (int i = 0; i < Configuration.instance.populationSize; i++) {
            population.getPopulation()[i] = new Chromosome();
        }

        return population;
    }

    public Application() {

        String csvFile = "././data/projectData.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int index =0;
            while ((line = br.readLine()) != null) {

                // use semicolon as separator
                line = line.substring(1,line.length()-1);
                String[] project = line.split(cvsSplitBy);

                    IProject tempProject= new Project(Integer.parseInt(project[0]), Integer.parseInt(project[1]), Integer.parseInt(project[2]));
                    System.out.println(tempProject.toString());

                    PROJECTS[index] = tempProject;
                    index++;
            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");


        System.out.println("Start");

        IPopulation population = generatePopulation();
        IPopulation evolvedPopulation = population.evolve();

    }
}


