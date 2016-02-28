package it.GeneticSolver.junit.Entities;

import static org.junit.Assert.*;

import org.junit.Test;

import it.GeneticSolver.Entities.Individual;

public class IndividualTest {

	@Test
	public void testSetGene() {
		int [] testArr = {0,0,0,0};
		Individual ind = new Individual(4, testArr);
		ind.setGene(2, 3);
		assertEquals("Gene settato correttamente ", 3, ind.getGene(2));
		ind.setGene(2, -1);
		assertEquals("Gene settato correttamente con lower bound ", 0, ind.getGene(2));
		ind.setGene(2, 15);
		assertEquals("Gene settato correttamente con upper bound ", 3, ind.getGene(2));
		ind.setGene(-1, 2);
		assertEquals("Gene settato correttamente con index <0 ", 2, ind.getGene(0));
	}

	@Test
	public void testInitGenes() {
		boolean myResult =  true;
		Individual ind = new Individual(4, false);
		for(int i = 0; i< 4; i++)
		{
			if(ind.findGeneId(i) == -1)
				myResult = false;
		}
		assertTrue(myResult);
	}
	
	@Test
	public void testSwapGenesByInsertion() {
		int [] testArr = {0,1,2,3};
		Individual ind = new Individual(4, testArr);
		ind.swapGenesByInsertion(2, 3); // in posizione 2 metto 3
		assertEquals("Gene nuovo corretto ", 3, ind.getGene(2));
		assertEquals("Gene vecchio corretto ", 2, ind.getGene(3));
	}

	@Test
	public void testFindGeneId() {
		int [] testArr = {0,1,2,3};
		Individual ind = new Individual(4, testArr);
		assertEquals("Gene 2 trovato ", 2, ind.findGeneId(2));
	}

}
