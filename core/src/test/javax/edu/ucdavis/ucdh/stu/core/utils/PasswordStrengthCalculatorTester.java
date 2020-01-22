package edu.ucdavis.ucdh.stu.core.utils;

import junit.framework.TestCase;

import org.junit.Test;

public class PasswordStrengthCalculatorTester extends TestCase {

	/**
	 * Sets up the test fixture. 
	 * (Called before every test case method.) 
	 */ 
	protected void setUp() throws Exception { 
		super.setUp();
	} 

	/**
	 * Tears down the test fixture. 
	 * (Called after every test case method.) 
	 */ 
	protected void tearDown() throws Exception { 
		super.tearDown(); 
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */ 
	@Test
	public void testPasswordStrengthCalculator1() {
		// "", "", ""
		int strength = PasswordStrengthCalculator.passwordStrength("", "", "");
		int expected = 1;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */
	@Test
	public void testPasswordStrengthCalculator2() {
		// "username", "", ""
		int strength = PasswordStrengthCalculator.passwordStrength("username", "", "");
		int expected = 1;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */
	@Test
	public void testPasswordStrengthCalculator3() {
		// "username", "pwd", ""
		int strength = PasswordStrengthCalculator.passwordStrength("username", "pwd", "");
		int expected = 1;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */
	@Test
	public void testPasswordStrengthCalculator4() {
		// "username", "username", ""
		int strength = PasswordStrengthCalculator.passwordStrength("username", "username", "");
		int expected = 2;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */ 
	@Test
	public void testPasswordStrengthCalculator5() {
		// "username", "password", ""
		int strength = PasswordStrengthCalculator.passwordStrength("username", "password", "");
		int expected = 2;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */ 
	@Test
	public void testPasswordStrengthCalculator6() {
		// "username", "password", "password"
		int strength = PasswordStrengthCalculator.passwordStrength("username", "password", "password");
		int expected = 2;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */ 
	@Test
	public void testPasswordStrengthCalculator7() {
		// "username", "P@s$w0rd", ""
		int strength = PasswordStrengthCalculator.passwordStrength("username", "P@s$w0rd", "");
		int expected = 3;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */ 
	@Test
	public void testPasswordStrengthCalculator8() {
		// "username", "P@s$w0rd", "password"
		int strength = PasswordStrengthCalculator.passwordStrength("username", "P@s$w0rd", "password");
		int expected = 5;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 

	/**
	 * Tests the PasswordStrengthCalculator 
	 */ 
	@Test
	public void testPasswordStrengthCalculator9() {
		// "username", "P@s$w0rdP@s$w0rd", ""
		int strength = PasswordStrengthCalculator.passwordStrength("username", "P@s$w0rdP@s$w0rd", "");
		int expected = 4;
		assertTrue("Password strength of " + strength + " does not match expected result of " + expected + ".", strength == expected);
	} 
}
