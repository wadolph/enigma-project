package test.java;

import static org.junit.Assert.*;
import main.java.enigma.EnigmaMachine;

import org.junit.Test;

/**
 * Initial tests of the EnigmaMachine back-end. Used relatively early in
 * the development cycle. 
 * 
 * @author Team Enigma
 * @version 0.9
 */
public class EnigmaTest {

	String pb1 = "ABCEFIUQJXOPHMRWDTYZ";   //AB CE DT FI HM JX OP QU RW YZ
	String pb2 = "AOBRCQDUEPFHGWINJKLM";   //AO BR CQ DU EP FH GW IN JK LM
	String pb3 = "ARBQCIDKEFGOHNJVLZMU";   //AR BQ CI DK EF GO HN JV LZ MU
	
	@Test
	public void testThreeRotor(){
		int[] threeRotor1 = {0,1,2};   //I,II,III
		int[] threeRotor2 = {1,2,3};   //II,III,IV
		int[] threeRotor3 = {4,6,2};   //V,VII,III
		int reflectorB    = 0;
		int reflectorC    = 1;
		char[] threeRing1 = {'A','A','A'}; //1,1,1
		char[] threeRing2 = {'E','X','Z'}; //5,24,26
		char[] threeRing3 = {'F','Q','E'}; //6,17,5
		char[] threeInit1 = {'J','K','Q'};
		char[] threeInit2 = {'H','Q','Z'};
		char[] threeInit3 = {'K','U','Y'};
		EnigmaMachine three1 = new EnigmaMachine(threeRotor1,reflectorB,threeRing1,threeInit1);
		EnigmaMachine three2 = new EnigmaMachine(threeRotor2,reflectorC,threeRing2,threeInit2,pb1);
		EnigmaMachine three3 = new EnigmaMachine(threeRotor3,reflectorB,threeRing3,threeInit3,pb2);
		
		String test1     = "AAAAABBBBBCCCCCDDDDD";
		String test2     = "HELLOTHISISATESTSTRING";
		String test3     = "TESTINGTESTINGTESTING";
		String expected1 = "YZYZNVKXGMKBSISSEPBU";
		String expected2 = "DSIRPUDXVQYGBVGEAAOKQT";
		String expected3 = "FKIFTOHOJBYWWPWFFAEBW"; 
		String result1   = three1.encryptString(test1);
		String result2   = three2.encryptString(test2);
		String result3   = three3.encryptString(test3);
		
		assertEquals("three1",expected1,result1);
		assertEquals("three2",expected2,result2);
		assertEquals("three3",expected3,result3);
		three1.reset();
		three2.reset();
		three3.reset();
		assertEquals("three1Decrypt",test1,three1.encryptString(expected1));
		assertEquals("three2Decrypt",test2,three2.encryptString(expected2));
		assertEquals("three3Decrypt",test3,three3.encryptString(expected3));
	}
	
	@Test
	public void testFourRotor(){
		int reflectorBThin= 2;
		int reflectorCThin= 3;
		int[] fourRotor1  = {8,0,1,2}; //Beta,I,II,III
		char[] fourRing1  = {'A','B','C','D'}; 
		char[] fourInit1  = {'A','A','A','A'};
		EnigmaMachine four1 = new EnigmaMachine(fourRotor1,reflectorBThin,fourRing1,fourInit1);
		
		int[] fourRotor2  = {9,4,2,6}; //Gamma,V,III,VII
		char[] fourRing2  = {'Z','X','Y','U'};
		char[] fourInit2  = {'Z','Y','X','W'}; 
		EnigmaMachine four2 = new EnigmaMachine(fourRotor2,reflectorCThin,fourRing2,fourInit2,pb2);
		
		int[] fourRotor3  = {9,3,7,5}; //Gamma,IV,VIII,VI
		char[] fourRing3  = {'G','M','N','P'}; 
		char[] fourInit3  = {'I','U','F','S'}; 
		EnigmaMachine four3 = new EnigmaMachine(fourRotor3,reflectorBThin,fourRing3,fourInit3,pb3);
		
		String test1     = "TESTINGFOURROTORSNOW";
		String test2     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String test3     = "OLDMCDONALDHADAFARMEIEIO";
		String expected1 = "RXOHSDFSYSKIGXQJCZMX"; 
		String expected2 = "KDJFTBAWLZGNLYYEIGPFSBYBKF";
		String expected3 = "ZJSTAKSMYAVNYVLKKHHVBZEH";
		String result1   = four1.encryptString(test1);
		String result2   = four2.encryptString(test2);
		String result3   = four3.encryptString(test3);
		
		assertEquals("four1",expected1,result1);
		assertEquals("four2",expected2,result2);
		assertEquals("four3",expected3,result3);
		four1.reset();
		four2.reset();
		four3.reset();
		assertEquals("four1Decrypt",test1,four1.encryptString(expected1));
		assertEquals("four2Decrypt",test2,four2.encryptString(expected2));
		assertEquals("four3Decrypt",test3,four3.encryptString(expected3));
	}
}
