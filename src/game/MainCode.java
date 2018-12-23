package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
* My game called "Barrowing Dungeon".
* @author Chris Lipscombe 14876717
**/

public class MainCode 
{
	public static int gold = 0; // Wallet Gold
	public static int silver = 0; // Wallet Silver
	public static int copper = 0; // Wallet Copper
	
	public static int health = 100; // Your Health
	public static int strenght = 100; // Your Strenght
	
	public static String[] desciptions; // Room desciption
	public static int[][] roomLayout; // Room Layout	
	public static int[] roomIndex; // Room Index
	public static int[] goldInRoom; // Gold in the room
	public static boolean[] dagger; // Dagger in the room
	public static String[] items; // Items in the room
	static int numberOfRooms;
	
	public static int defaultRoom = 1;
	
	public static String OPEN = "open"; // Open command
	public static String SEARCH = "search"; // Search command
	public static String HELP = "help"; // Help command
	public static String PLAYER = "player"; // Display player stats
	public static String ATTACK = "attack"; // Attack from monster
	public static String RUN = "run"; // Run from monster
	public static String REST = "rest"; // Restore some stats
	
	/**
	* The main, this is where all my classes are called.
	* @author Chris Lipscombe 14876717
	**/
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		Random rand = new Random();
		int randomMonsterSpawn = rand.nextInt(5) + 1; // Random monster spawn
		
		// Loading in map
		LoadingMap();
		
		// Starting wallet and stats
		System.out.println("Your starting Health : " + health + "%");
		System.out.println("Your starting Strenght : " + strenght + "%");
		System.out.println("Your starting Money : Gold " + gold + ", Silver " + silver + " And Copper " + copper);
		Thread.sleep(1000); // Added effect of pause
		
		while(numberOfRooms > 0)
		{
			// Information about what room you in
			RoomInformation();
			
			// Spawning in monster (There is a 1 out of 5 change)
			if(randomMonsterSpawn == 1)
			{
				MonsterSpawner();
			}
				
			// Gets command on what to do some user
			CommandsUser();
			
			numberOfRooms = numberOfRooms - 1;
		}
	}
	
	/**
	* RoomInformation prints out the information proved from the game map.
	* @author Chris Lipscombe 14876717
	**/
	
	private static void RoomInformation() throws InterruptedException 
	{
		NiceFormat();
		
		System.out.println("You are in room " + roomIndex[defaultRoom - 1] + ".");
		System.out.println("");
		Thread.sleep(1000); // Added effect of pause
		System.out.println(desciptions[defaultRoom - 1] + ".");
		System.out.println("");
		Thread.sleep(1000); // Added effect of pause
	
		for(int i = 0; i < roomLayout[defaultRoom - 1].length; i++)
		{
			System.out.println("There is a room(s) with a number " + roomLayout[defaultRoom - 1][i] + " on it.");
		}
		System.out.println("");
	}
	
	/**
	* LoadingMap scans in the information from the game map and stores it in the right location.
	* @author Chris Lipscombe 14876717
	**/

	public static void LoadingMap() throws IOException, InterruptedException
	{		
		System.out.print("Loading the map, Please wait ");
		
		for(int i = 0; i < 3; i ++) // Added effect of pause
		{
			Thread.sleep(2000); 
			System.out.print(". ");
		}
		System.out.println("");
		Thread.sleep(1000); // Added effect of pause
		
		Scanner fileScanner = new Scanner(new File("input/gamemap.txt"));
		
		numberOfRooms = fileScanner.nextInt();
		roomLayout = new int[numberOfRooms][];
		desciptions = new String[5];
		roomIndex = new int[5];
		goldInRoom = new int[5];
		dagger = new boolean[5];
		items = new String[3];
		
		for(int i = 0; i < numberOfRooms; i++)
		{
			roomIndex[i] = fileScanner.nextInt(); //Line 1
			fileScanner.nextLine(); //Flush line 1
			fileScanner.nextLine(); //Line 2: @description
			desciptions[i] = fileScanner.nextLine(); //Line 3
			fileScanner.nextLine(); //Line 4: @connect
			String[] tokens = TokenizeInput(fileScanner.nextLine()); //Line 5
			roomLayout[i] = ToIntegerArray(tokens);
			fileScanner.nextLine(); //Line 6: @Items
			items = TokenizeInput(fileScanner.nextLine()); //Line 7
			goldInRoom[i] = GoldCheck(items);
			dagger[i] = DaggerCheck(items);
		}
			
		System.out.println("Map Loaded.");
		System.out.println("In this map pack there are : " + numberOfRooms + " rooms");
		System.out.println(" ");
		
		Thread.sleep(1000); // Added effect of pause
	}
	
	/**
	* GoldCheck takes information proved from the game map and randomizes the amount of gold in the rooms.
	* @author Chris Lipscombe 14876717
	**/
	
	private static int GoldCheck(String[] items) 
	{
		Random rand = new Random();
		int[] moneyInRoom;
		moneyInRoom = new int[3];
		
		for(int i = 0; items.length > i; i++)
		{
			if(items[i] == "gold")
			{
				moneyInRoom[0] = rand.nextInt(6) + 1; // Gold
				moneyInRoom[1] = rand.nextInt(89) + 1; // Silver
				moneyInRoom[2] = rand.nextInt(61) + 1; // Copper
			}
		}	
		
		gold = gold + moneyInRoom[0];
		silver = silver + moneyInRoom[1];
		copper = copper + moneyInRoom[2];
		
		MoneyFilter();
		
		return 0;
	}
	
	/**
	* ToInterArray takes the information proved from the game map and changes a string array too a integer array.
	* @author Chris Lipscombe 14876717
	**/

	private static int[] ToIntegerArray(String[] tokens) 
	{
		int[] tokenArray = new int[tokens.length];
		
		for(int i = 0; i < tokens.length; i++)
		{
			tokenArray[i] = Integer.parseInt(tokens[i]);
		}
		
		return tokenArray;
	}
	
	/**
	* DaggerChecktakes the information proved from the game map and checks what room the dagger is in.
	* @author Chris Lipscombe 14876717
	**/

	private static boolean DaggerCheck(String[] items2) 
	{
		if(items2[0] == "dagger") 
		{
			return true;	
		}
		
		else 
		{
			return false;
		}
	}

	/**
	* TokenizeInput take the information proved from the game map and splits the items and puts them into an array.
	* @author Chris Lipscombe 14876717
	**/
	
	private static String[] TokenizeInput(String nextLine) 
	{
		String[] item = nextLine.split(",");
		return item;
	}
	
	/**
	* MoneyFilter filters the players wallet so there is no over flow, as the max Silver and Copper you can have is 99.
	* @author Chris Lipscombe 14876717
	**/

	public static void MoneyFilter() 
	{
		// Money filter.
		while (copper > 99) 
		{
			silver++;
			copper = copper - 99;
		}
		
		while (silver > 99) 
		{
			gold++;
			silver = silver - 99;
		}
	}

	/**
	* MonsterSpawner creates and spawns in the monsters in any random room, so be on guard.
	* @author Chris Lipscombe 14876717
	**/
	
	private static void MonsterSpawner() throws InterruptedException 
	{
		Random randStatsOfMonster = new Random();
		Random randMonsterSpawn = new Random();
		Random randMoneyDropped = new Random();
		
		int checkingAttack = 0;
		int checkingRun = 0;
		boolean attack, run;
						
		int monsterSpawner = randMonsterSpawn.nextInt(30) + 1;
		
		if(monsterSpawner == 5)
		{
			System.out.println("OH! SNAP! Your in trouble now!");
			Thread.sleep(2000); // Added effect of pause
			System.out.println("You have just walked into Barry The Grand Magicians hideout!");
			Thread.sleep(500); // Added effect of pause
			System.out.println("You better run befo..");
			Thread.sleep(500); // Added effect of pause
			System.out.println("You feel like your being watched from the shadows..");
			Thread.sleep(500); // Added effect of pause
			System.out.println(" ");
			System.out.println("A voice speaks : Tread wisey my dear, for im Barry the one and only");
			Thread.sleep(500); // Added effect of pause
			System.out.println("The voice continues : For what way do you look travel? for whom maybe me watching");
			Thread.sleep(500); // Added effect of pause
			System.out.println("The voice continues : North lends too a Waterfall.");
			Thread.sleep(500); // Added effect of pause
			System.out.println("The voice continues : East lends too a Clearing.");
			Thread.sleep(500); // Added effect of pause
			System.out.println("The voice continues : South lends too a City.");
			Thread.sleep(500); // Added effect of pause
			System.out.println("The voice continues : And West lends too a Grave in which you will never wake up from!");
			Thread.sleep(500); // Added effect of pause
			System.out.println(" ");
			System.out.println("A gaint fire ball flys towards you!");
			Thread.sleep(500); // Added effect of pause
			
			NiceFormat();
			
			int monsterStrenght = randStatsOfMonster.nextInt(10) + 1;
			int monsterHealth = randStatsOfMonster.nextInt(50) + 1;
			int monsterMaxHit = randStatsOfMonster.nextInt(monsterStrenght * 2);
						
			System.out.println("Barry The Grand Magicians Health : " + monsterHealth);
			System.out.println("Barry The Grand Magicians Strenght : " + monsterStrenght);
			System.out.println("Barry The Grand Magicians Max Hit : " + monsterMaxHit);
			
			NiceFormat();
			
			Scanner fileScan = new Scanner(System.in);
			
			System.out.println("You can run or attack, what do you pick?");
			
			String input;
			input = fileScan.nextLine();
			String[] words = input.split(" ");
			attack = words[0].equalsIgnoreCase(ATTACK);
			run = words[0].equalsIgnoreCase(RUN);
			
			if(attack = true)
			{
				while(monsterHealth > 0)
				{
					Random randHitRateMonster = new Random();
					Random randHitRateyou = new Random();
										
					int yourHit = randHitRateyou.nextInt(strenght / 4);
					System.out.println("You hit " + yourHit + " on Barry The Grand Magician.");
					Thread.sleep(200); // Added effect of pause
					monsterHealth = monsterHealth - yourHit;
					System.out.println("Barry The Grand Magicians Health is : " + monsterHealth);
					
					Thread.sleep(200); // Added effect of pause
					System.out.println("Barry The Grand Magician throws another fire ball at you.");
					int monsterHit = randHitRateMonster.nextInt(monsterMaxHit);
					health = health - monsterHit;
					System.out.println("You got hit! BThe Grand Magicians hit did " + monsterHit + " on you.");
					Thread.sleep(200); // Added effect of pause
					System.out.println("Your health is : " + health);
				}
				
				System.out.println("You killed Barry The Grand Magician!");
			}
			
			else if(run = true)
			{
				Random randRun = new Random();
				
				int canYouRun = randRun.nextInt(strenght);
				
				if(canYouRun > 50)
				{
					System.out.println("You run from Barry The Grand Magician! but at a cost of some of your strenght."); 
					strenght = strenght - 14;
				}
				
				else
				{
					System.out.println("OH NO! You failed too run! Your focused to fight Barry The Grand Magician!");
					
					while(monsterHealth > 0)
					{
						Random randHitRateMonster = new Random();
						Random randHitRateyou = new Random();
						
						int yourHit = randHitRateyou.nextInt(strenght / 4);
						System.out.println("You hit " + yourHit + " on Barry The Grand Magician.");
						Thread.sleep(200); // Added effect of pause
						monsterHealth = monsterHealth - yourHit;
						System.out.println("Barry The Grand Magicians Health is : " + monsterHealth);
						
						Thread.sleep(200); // Added effect of pause
						System.out.println("Barry The Grand Magician throws another fire ball at you.");
						int monsterHit = randHitRateMonster.nextInt(monsterMaxHit);
						health = health - monsterHit;
						System.out.println("You got hit! BThe Grand Magicians hit did " + monsterHit + " on you.");
						Thread.sleep(200); // Added effect of pause
						System.out.println("Your health is : " + health);
					}
					
					System.out.println("You killed Barry The Grand Magician!");
				}
			}
		}
		
		else if(monsterSpawner == 22)
		{
			System.out.println("PROBELM! BIG PROBELM!");
			Thread.sleep(2000); // Added effect of pause
			System.out.println("A iron bared cage stands before you..");
			Thread.sleep(500); // Added effect of pause
			System.out.println("With a huge hole in the side of it!");
			Thread.sleep(500); // Added effect of pause
			System.out.println("Out of nowhere an Enraged Gorilla jumps down from the roof!");
			Thread.sleep(500); // Added effect of pause
			
			int monsterStrenght = randStatsOfMonster.nextInt(14) + 1;
			int monsterHealth = randStatsOfMonster.nextInt(46) + 1;
			int monsterMaxHit = randStatsOfMonster.nextInt(monsterStrenght * 2);
			
			System.out.println("The Enraged Gorillas Health : " + monsterHealth);
			System.out.println("The Enraged Gorillas Strenght : " + monsterStrenght);
			System.out.println("The Enraged Gorillas Max Hit : " + monsterMaxHit);
			
			Scanner fileScan = new Scanner(System.in);
			
			System.out.println("You can run or attack, what do you pick?");
			
			String input;
			input = fileScan.nextLine();
			String[] words = input.split(" ");
			attack = words[0].equalsIgnoreCase(ATTACK);
			run = words[0].equalsIgnoreCase(RUN);
			
			if(attack = true)
			{
				while(monsterHealth > 0)
				{
					Random randHitRateMonster = new Random();
					Random randHitRateyou = new Random();
					
					int yourHit = randHitRateyou.nextInt(strenght / 4);
					System.out.println("You hit " + yourHit + " on The Enraged Gorilla.");
					Thread.sleep(200); // Added effect of pause
					monsterHealth = monsterHealth - yourHit;
					System.out.println("The Enraged Gorillas health is : " + monsterHealth);
					
					Thread.sleep(200); // Added effect of pause
					System.out.println("The Enraged Gorilla throw his huge fist at you.");
					int monsterHit = randHitRateMonster.nextInt(monsterMaxHit);
					health = health - monsterHit;
					System.out.println("You got hit! The Enraged Gorilla hit did " + monsterHit + " on you.");
					Thread.sleep(200); // Added effect of pause
					System.out.println("Your health is : " + health);
				}
				
				System.out.println("You killed The Enraged Gorilla!");
			}
			
			else if(run = true)
			{
				Random randRun = new Random();
				
				int canYouRun = randRun.nextInt(strenght);
				
				if(canYouRun > 50)
				{
					System.out.println("You run from Barry! but at a cost of some of your strenght."); 
					strenght = strenght - 14;
				}
				
				else
				{
					System.out.println("OH NO! You failed too run! Your focused to fight Barry!");
					
					while(monsterHealth > 0)
					{
						Random randHitRateMonster = new Random();
						Random randHitRateyou = new Random();
						
						int yourHit = randHitRateyou.nextInt(strenght / 4);
						System.out.println("You hit " + yourHit + " on The Enraged Gorilla.");
						Thread.sleep(200); // Added effect of pause
						monsterHealth = monsterHealth - yourHit;
						System.out.println("The Enraged Gorillas health is : " + monsterHealth);
						
						Thread.sleep(200); // Added effect of pause
						System.out.println("The Enraged Gorilla throw his huge fist at you.");
						int monsterHit = randHitRateMonster.nextInt(monsterMaxHit);
						health = health - monsterHit;
						System.out.println("You got hit! The Enraged Gorilla hit did " + monsterHit + " on you.");
						Thread.sleep(200); // Added effect of pause
						System.out.println("Your health is : " + health);
					}
					
					System.out.println("You killed The Enraged Gorilla!");
				}
			}
		}
		
		else if(monsterSpawner == 1 || monsterSpawner == 2)
		{
			System.out.println("You feel like your in maze...");
			Thread.sleep(2000); // Added effect of pause
			System.out.print("You keep turning in rounds ");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("180 ");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("360 ");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("540 ");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("720 ");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("900 ");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.println("You became dizzy! BUT you feel a warming hand be placed on your shoulder!");
			Thread.sleep(500); // Added effect of pause
			System.out.println("You jump backwards in fair, well at least what you think is backwards..");
			Thread.sleep(500); // Added effect of pause
			System.out.println("You find standing in front of you a wise traveler, of whom does not speak but have a one of a kind blue hat..");
			Thread.sleep(500); // Added effect of pause
			System.out.println("He hands you a potion and some gold..");
			Thread.sleep(500); // Added effect of pause
			System.out.println("He waves good-bye..");
			Thread.sleep(500); // Added effect of pause
			System.out.println("You awake..");
			Thread.sleep(500); // Added effect of pause
			System.out.println("Laying on the ground with a died skeleton laying at your feet..");
			Thread.sleep(500); // Added effect of pause
			System.out.print("Wearing");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("the");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("a");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("blue");
			
			for(int i = 0; i < 3; i ++) // Added effect of pause
			{
				Thread.sleep(200); 
				System.out.print(". ");
			}
			
			Thread.sleep(500); // Added effect of pause
			System.out.print("hat");	
		}
		
		gold = randMoneyDropped.nextInt(10) + 1; // Adding Gold dropped into wallet
		silver = randMoneyDropped.nextInt(20) + 1; // Adding Silver dropped into wallet
		copper = randMoneyDropped.nextInt(99) + 1; // Adding Copper dropped into wallet

		// Filtering money
		MoneyFilter();
	}
	
	/**
	* CommandsUser creates and read commands inputted by the user.
	* @author Chris Lipscombe 14876717
	**/	
	
	public static void CommandsUser() throws InterruptedException 
	{
		Scanner fileScan = new Scanner(System.in);
		
		int checkingOpen = 0;
		int checkingSearch = 0;
		int checkingHelp = 0;
		int checkingPlayer = 0;
		int checkingRest = 0;
		
		System.out.println("What would you like too do?");
		
		String input;
		input = fileScan.nextLine();
		String[] words = input.split(" ");
		
		boolean open, search, help, player, rest;
		
		open = words[0].equalsIgnoreCase(OPEN);
		search = words[0].equalsIgnoreCase(SEARCH);
		help = words[0].equalsIgnoreCase(HELP);
		player = words[0].equalsIgnoreCase(PLAYER);
		rest = words[0].equalsIgnoreCase(REST);

		if(open == true) 
		{
			Random rand = new Random();
			int randomNumber = rand.nextInt(10) + 1;
			int annoyingWait = randomNumber + 1000;
				
			System.out.println("");
			System.out.println("Your tried too open the door.");
			Thread.sleep(annoyingWait); // Added effect of pause
				
			if(randomNumber == 9)
			{
				System.out.println("OH! NO! you failed too open the door!");
				Thread.sleep(2000); // Added effect of pause
				System.out.println("I dont know you managed to fail it");
				Thread.sleep(1000); // Added effect of pause
				System.out.println("Your strenght took a hit!");
				Thread.sleep(2000); // Added effect of pause
				strenght = strenght - 9;
				System.out.println("");
					
				 CommandsUser();
			}
				
			if(randomNumber == 5)
			{
				System.out.println("OH! NO! you failed too open the door!");
				Thread.sleep(2000); // Added effect of pause
				System.out.println("I dont know you managed to fail it");
				Thread.sleep(500); // Added effect of pause
				System.out.println("Your strenght took a hit!");
				Thread.sleep(2000); // Added effect of pause
				strenght = strenght - 5;
				System.out.println("");
					
				 CommandsUser();
			}
			
			else
			{
				System.out.println("You opened the door!");
				Thread.sleep(2000); // Added effect of pause
				defaultRoom = Integer.parseInt(words[1]);
			}
		}
			
		else if(search == true) 
		{
			System.out.println("");
			System.out.println("You started too search the room! quickly");
			Thread.sleep(2000); // Added effect of pause
			
			for(int i = 0; items.length > i; i++)
			{
				if(items[i] == "gold")
				{
					System.out.println("You found something!");
					Thread.sleep(2000); // Added effect of pause
					System.out.println("Its gold and shiney!");
					Thread.sleep(500); // Added effect of pause
					System.out.println("There is more over here!");
					Thread.sleep(500); // Added effect of pause
					System.out.println("And more! Im rich!");
					System.out.println("");
				}
				
				else if(items[i] == "dagger")
				{
					System.out.println("You found something!");
					Thread.sleep(2000); // Added effect of pause
					System.out.println("It looks dangerous, it might someone");
					Thread.sleep(500); // Added effect of pause
					System.out.println("So its perfect for my journey!");
					System.out.println("");
				}
			}	
			
			System.out.println("You searched high and low, but you found nothing.");
			Thread.sleep(2000); // Added effect of pause
			System.out.println("Maybe you need to find some glasses.");
			Thread.sleep(500); // Added effect of pause
			System.out.println("");
			
			CommandsUser();
		}
			
		else if(help == true) 
		{
			HelpMenu();
			
			CommandsUser();
		}
			
		else if(player == true) 
		{
			System.out.println("");
			System.out.println("Your Health : " + health + "%");
			System.out.println("Your Strenght : " + strenght + "%");
			System.out.println("Your Money : Gold " + gold + ", Silver " + silver + " And Copper " + copper);
				
			CommandsUser();
		}
		
		else if(rest == true) 
		{
			System.out.println("Your lay down on the ground and dream about the outside world.");
			health = health + 10;
			strenght = strenght + 20;
			System.out.println("Your awake with a raging headache and see flicking numbers and letter before your eyes come right.");
		}
			
		else 
		{
			System.out.println("");
			System.out.println("That command is unknown.");
			Thread.sleep(2000); // Added effect of pause
				
			CommandsUser();
		}
	}
	
	/**
	* NiceFormat is just a little formater that creates a line so it splits the rooms.
	* @author Chris Lipscombe 14876717
	**/	
	
	public static void NiceFormat()
	{
		System.out.println("");
		System.out.println("-----------------------------------------------------");
		System.out.println("");
	}
	
	/**
	* HelpMenu is the command help, this is what is displays.
	* @author Chris Lipscombe 14876717
	**/
	
	public static void HelpMenu()
	{
		System.out.println("");
		
		System.out.println("Command : help");
		System.out.println("        : Type \"help\" to get information on commands.");
		System.out.println("");
		
		System.out.println("Command : search");
		System.out.println("        : Type \"search\" to get search the room you are in too see what you might find.");
		System.out.println("");
		
		System.out.println("Command : open");
		System.out.println("        : Type \"open\" followed by a space and the number on the door to get open the door.");
		System.out.println("");
		
		System.out.println("Command : player");
		System.out.println("        : Type \"player\" to display your players health, strenght and money.");
		
		System.out.println("Command : rest");
		System.out.println("        : Type \"rest\" to restore some strenght and alittle health.");
	}
}