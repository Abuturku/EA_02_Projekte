import Configuration.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Application {
	public static final IProject[] PROJECTS = new Project[150];
	private int fitnessOfFittestChromosome = 0;

	public static void main(String[] args) {
		new Application();
	}

	public Application() {
		loadProjectsFromCSV("././data/projectData.csv");
		evolvePopulation(generatePopulation());
	}
	public Application(int populationSize) {
		Configuration.POPULATION_SIZE = populationSize;
		loadProjectsFromCSV("././data/projectData.csv");
		evolvePopulation(generatePopulation());
	}

	public Population generatePopulation() {
		Population population = new Population(new Chromosome[Configuration.POPULATION_SIZE]);
		for (int i = 0; i < Configuration.POPULATION_SIZE; i++) {
			population.getPopulation()[i] = new Chromosome();
		}

		return population;
	}

	private void evolvePopulation(IPopulation population) {
		int evolved = 0;
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < Configuration.MAX_GENERATION; i++) {
			evolved++;
			population = population.evolve();
			reportFittestChromosome(evolved, population, currentTimeMillis);
		}
	}

	private int getTimeInSeconds(long pastTime) {
		return  Math.round(pastTime / 1000);
	}

	private long getPastTime(long currentTimeMillis) {
		return System.currentTimeMillis() - currentTimeMillis;
	}

	private void reportFittestChromosome(int evolved, IPopulation evolvedPopulation, long currentTimeMillis) {
		int fitnessOfFittestChromosomeFromPopulation = evolvedPopulation.getFittest().getFitness();
		if (fitnessOfFittestChromosome < fitnessOfFittestChromosomeFromPopulation) {
			fitnessOfFittestChromosome = fitnessOfFittestChromosomeFromPopulation;
			System.out.println("NEW FITNESS: " + fitnessOfFittestChromosome + " GENERATION: " + evolved + " Vergangene Zeit in s: " + getTimeInSeconds(getPastTime(currentTimeMillis)));
		} else if (fitnessOfFittestChromosome == fitnessOfFittestChromosomeFromPopulation) {
			// System.out.println("");
		} else {
			System.out.println(
					"Schlechter als zuvor: " + fitnessOfFittestChromosomeFromPopulation + " GENERATION: " + evolved);
		}
	}

	private void loadProjectsFromCSV(String csvFileAddress) {
		String csvFile = csvFileAddress;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			int index = 0;
			while ((line = br.readLine()) != null) {

				// use semicolon as separator
				line = line.substring(1, line.length() - 1);
				String[] project = line.split(cvsSplitBy);

				IProject tempProject = new Project(Integer.parseInt(project[0]), Integer.parseInt(project[1]),
						Integer.parseInt(project[2]));
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
	}

}
