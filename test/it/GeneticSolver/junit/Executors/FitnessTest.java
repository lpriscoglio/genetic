package it.GeneticSolver.junit.Executors;

import static org.junit.Assert.*;

import org.junit.Test;

import it.GeneticSolver.Entities.Individual;
import it.GeneticSolver.Executors.Fitness;
import it.GeneticSolver.Executors.InstanceGen;

public class FitnessTest {

	@Test
	public void testGetFitness() {
		int [] myArr = {11,6,8,2,3,7,10,0,4,5,9,1};
		InstanceGen.setInstanceGen("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug12.dat", false);
		Individual indiv = new Individual(12, myArr);
		assertEquals("Fitness Calcolata", 578, Fitness.getFitness(indiv));
	}

}
