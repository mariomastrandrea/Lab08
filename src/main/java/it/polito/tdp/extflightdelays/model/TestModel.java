package it.polito.tdp.extflightdelays.model;

import java.util.Scanner;

public class TestModel 
{
	public static void main(String[] args) 
	{
		System.out.println("Testing RegExp\n");
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		while(!input.equals("end"))
		{
			input = scanner.nextLine();
			if(input.matches("(0)+(.)*"))
				System.out.println(true);
			else
				System.out.println(false);
			
			System.out.println();
		}
		scanner.close();
		System.out.println("Bye");
	}
}
