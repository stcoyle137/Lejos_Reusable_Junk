package stc.reusable.junk;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;

class Motors{
	//Defining ports to use outside of class
	public static final char A = 'a';
	public static final char B = 'b';
	public static final char C = 'c';
	public static final char D = 'd';
	public static final char Large = 'l';
	public static final char Medium = 'm';
	//Defining Class variable that will be use only inside the object  
	private RegulatedMotor motor;
	private Port whichPort;
	private final int maxLargeSpeed = 835;
	private final int maxMediumSpeed = 1238;
	private int maxSpeed;
	private char sizeOfMotor;
	private boolean isSyncing = false;
	private boolean hasSyncStarted = false;
	
	public Motors( char size, char port) {
		/**
		 * The port is represent by the Lower case letters a b c d but there are const variable in motor declared above to use
		 * The size with l and m and with m as small motor and l as large but there are const variable in motor declared above to use
		 * This constructor is to create a large or medium motor with any port input
		 */
		
		//Simply set a class variable to size 
		sizeOfMotor = size;
		
		//The Switch will set the class variable whichPort to the inputed port 
		switch (port){
		case A:
			whichPort = MotorPort.A;
			break;
			
		case B:
			whichPort = MotorPort.B;	
			break;
			
		case C:
			whichPort = MotorPort.C;
			break;
			
		case D:
			whichPort = MotorPort.D;
			break;
		
		//Default for port will be a and then it will tell user Port that was given is wrong
		default :
			System.out.println("Given Port does not exist: " + port);
			System.out.println("Giving default port of A");
			whichPort = MotorPort.A;
			break;
		}
		
		//Using Size input to see which Size of Motor we need to use
		if(sizeOfMotor == Large) {
			//This is if Large Motor was inputed
			//MaxSpeed was found on Lego store site and looking at specs of motors
			motor = new EV3LargeRegulatedMotor(whichPort);
			maxSpeed = maxLargeSpeed;
		}
		else if (sizeOfMotor == Medium) {
			//This is if Medium Motor was inputed
			//MaxSpeed was found on Lego store site and looking at specs of motors
			motor = new EV3MediumRegulatedMotor(whichPort);
			maxSpeed = maxMediumSpeed;
		}
		else {
			//This is the default if size inputed doesn't match the two sizes
			//It will also set the speed to Large and Size to Large
			System.out.println("Given Size does not exist: " + size);
			System.out.println("Giving a default value of Large");
			motor = new EV3LargeRegulatedMotor(whichPort);
			maxSpeed = maxLargeSpeed;
		}
	}
	
	public void startMotor(double percentOfSpeed) {
		/**
		 * This method will turn on motor for a set speed with out a stop
		 * Percent must be in the range 1 <-> -1
		 */
		//The if else statement is to make sure the percent of speed is not high that 100%
		if (Math.abs(percentOfSpeed) <= 1) {
			//Setting speed to a percent of max speed
			//Also Turn the motor ON
			int speed = (int)(maxSpeed * percentOfSpeed);
			motor.setSpeed(speed);
			if(percentOfSpeed == Math.abs(percentOfSpeed)) {
				motor.forward();
			}
			else {
				motor.backward();
			}
		}
		else {
			//Since the percent cannot be high that 100 %, So motor will not be turn on
			System.out.println("Input percent is out of 1 <-> -1 Range");
			System.out.println("Cannont Run faster than 100 percent( or 1)");
			System.out.println("Motors will not be turn on");
		}
	}
	
	public void startMotorDegrees(double percentOfSpeed, int degreesToTurn) {
		/**
		 * This method will use two input speed and degrees to run the motor for a certain degrees
		 * It will tell if the motor is going forward or backwards using percentOfSpeed not degreesToTurn
		 */
		
		//This if else statement is to make sure the percent of speed is not high that 100% and not lower than 100%
		//The percent will tell the direction
		if (percentOfSpeed <= 1 && percentOfSpeed >= -1) {
			
			//Setting speed to a percent of max speed
			// The Math.abs will make sure speed is not negitive
			int speed = (int)(maxSpeed * Math.abs(percentOfSpeed));
			
			//Setting Speed of motor
			motor.setSpeed(speed);
			
			//Tell if percentOfSpeed is postive or negitive
			if (percentOfSpeed == Math.abs(percentOfSpeed)) {
				
				//Make sure the degrees are postive, this statement is turn on motor
				motor.rotate(Math.abs(degreesToTurn), true);
			}
			
			else {
			
				//Make sure the degrees are negtive, this statement is turn on motor
				motor.rotate(-Math.abs(degreesToTurn), true);
			}
			motor.waitComplete();
		}
		else {
			
			//Since the percent cannot be high that 100 %, So motor will not be turn on
			System.out.println("Input percent is out of 1 <-> 0 Range");
			System.out.println("Cannont Run faster than 100 percent( or 1)");
			System.out.println("Motors will not be turn on");
		}
	}
	
	public void stopMotor() {
		/**
		 * This Method will stop the motor
		 */
		
		//Turns off motor
		motor.stop();
	}
	
	public void resetEncoder() {
		/**
		 * This method will reset the encoder of the motor
		 */
		
		//This will reset the encoder value
		motor.resetTachoCount();
	}
	
	public double getEncoderValue(boolean printValue) {
		/**
		 * This Method is use to return the value of the Motor encoder
		 * The printValue variable is to see if the method should print the value of the encoder on the screen
		 */
		
		//This if statement will tell in to print encoder value
		if(printValue) {
		
			//Prints encoder value
			System.out.println(motor.getTachoCount());
		}
		
		//Will return the encoder value
		return motor.getTachoCount();
	}
	
	public int getMaxSpeed(boolean printValue) {
		/**
		 * This method will return the MaxSpeed of the instance of the Motor
		 * The printValue variable is to see if the method should print the value of the encoder on the screen
		 */
		
		//This if statement will tell in to print the maxSpeed
		if(printValue) {
			
			//Prints the MaxSpeed
			System.out.println(maxSpeed);
		}
		
		// Will return the max Speed
		return maxSpeed;
	}
	
	private EV3LargeRegulatedMotor castMotorsToEV3LargeRegulatedMotors() {
		/**
		 * This method is the cast the motor to Large motor and return the casted value
		 * This method exist because I didn't want to extend to EV3LargeRegulatedMotors in this class
		 * Be careful in the use of this method it could create an exception or error if used worng (might use a catch block to prevent this eventually)
		 * This is a class method and cannot be used outside of this class
		 */
		//This statement cast the Motor to Large motor and returns value
		return (EV3LargeRegulatedMotor) motor;
	}
	
	private EV3MediumRegulatedMotor castMotorsToEV3MediumRegulatedMotors() {
		/**
		 * This method is the cast the motor to medium motor and return the casted value
		 * This method exist because I didn't want to extend to EV3MediumRegulatedMotors in this class
		 * Be careful in the use of this method it could create an exception or error if used worng (might use a catch block to prevent this eventually) 
		 * This is a class method and cannot be used outside of this class
		 */
		
		//This statement cast the Motor to Medium motor and returns value
		return (EV3MediumRegulatedMotor) motor;
	}
	
	private void changeSyncingState(boolean newState) {
		/** 
		 * This method is to change the class variable isSyncing to tell if motor are syncing
		 * no one outside of the class 
		 */
		isSyncing = newState;
	}
	
	public boolean returnSyncingState() {
		/**
		 * This method is to return the Sync State
		 */
		return this.isSyncing;
	}
	
	private void changeStateOfStartingSync(boolean SyncStartingState) {
		/*
		 * This method will change the state of the class variable SyncStartingState
		 * It is a class method and will not be used outside of this class
		 */
		hasSyncStarted = SyncStartingState;
	}
	
	public boolean returnStateOfStartingSync() {
		return hasSyncStarted;
	}
	
	public void setUpSyncMotor(Motors m2) {
		/**
		 * This Method will set up the sync of two motors 
		 * One motor must be input to the method 
		 * This should be the only palce the the casting method above should be used(so far)
		 * The two motor that are being sync have to have to same size
		 */
		
		//This if statement will tell if motor and m2 have to same size
		//it is used to prevent errors
		if (this.sizeOfMotor == m2.sizeOfMotor) {
			
			//This if statement will tell if a med or large motor is used for motor
			if (motor.getClass() == EV3MediumRegulatedMotor.class) {
			
				//Setting up sync between motor and m2
				motor.synchronizeWith(new RegulatedMotor[] {m2.castMotorsToEV3MediumRegulatedMotors()});
			}
			
			//The only other thing that could happen is that large motor and large m2
			else {
				//Setting up sync between motor and m2
				motor.synchronizeWith(new RegulatedMotor[] {m2.castMotorsToEV3LargeRegulatedMotors()});
			}
			//Will change class variable to tell that Syncing state is true
			this.changeSyncingState(true);
		}
		//This is to tell user that the motor and m2 are not the same size and therefor cannot be sync
		else {
			System.out.println("Motors that are try to be sync are not the same size");
			System.out.println("Sync will not be set up");
		}
	}//
	
	public void startingSync() {
		/**
		 * This will start the sync between two motors 
		 * It will only start if a Sync had been set up
		 */
		
		//This if statement will tell if sync has been set up 
		if(isSyncing && !hasSyncStarted) {
			
			//Starting the sync
			motor.startSynchronization();
			//Setting the class variable hasSyncStarted to True
			this.changeStateOfStartingSync(true);
		}
		//If the sync hasn't been set up this will tell user that
		//It will NOT start the Sync
		else {
			System.out.println("The Sync between motors have not been set up");
			System.out.println("The sync will not start");
		}
	}
	
	
	public void stoppingSync() {
		/**
		 * This method will stop the Sync
		 * If the Sync is not set up or started it will print this to the user
		 */
		//This if statement will tell if sync has started
		if(isSyncing && hasSyncStarted) {
			//Ending the sync
			motor.endSynchronization();
			//Change state of class variable to start sync process again
			this.changeStateOfStartingSync(false);
		}
		//If the sync hasn't been set up this will tell user that
		//It will NOT start the Sync
		else {
			System.out.println("The Sync between motors have not been set up or started");
			System.out.println("There is no sync to be stop");
		}

	}
	
	public void waitForFinish() {
		/** 
		 * This method will wait for the motor to finish
		 * It will mostly used in Syncing motors
		 */
		
		//Waiting for the motor to be complete
		motor.waitComplete();
	}
}
