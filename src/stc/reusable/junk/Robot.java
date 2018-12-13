package stc.reusable.junk;

/**
 * If you are using these files you should only be declaring this class for any objects
 */
public class Robot {

	//These are the all the diffferent parts of a robot could
	private Sensor[] sensors = new Sensor[4];
	private Motors[] motors = new Motors[4];
	private MotorsSystem motorSystem;

	/**
	 * @param sensors is a string with length of 4 Used charators for sensors
	 * 'e' for a empty port
	 * 't' for a touch sensor
	 * 'c' for a color sensor
	 * 'g' for a gyro sensor
	 * @param Used charators for motors
	 * 'e' for a empty port
	 * 'm' for a medium motor
	 * 'l' for a large motor
	 * 
	 * This is constructor that is manuel for the robot 
	 * Eventually a automatic constructor will be used
	 */
	public Robot(String sensor, String motor) {
		try {
			//This is checcking that the length of the input sensor string is at least 4
			while(sensor.length() < 4) {
				//Assumes that the empty spots are 'e'
				sensor += "e";
			}
			
			//This is checcking that the length of the input motor string is at least 4
			while(motor.length() < 4) {
				//Assumes that the empty spots are 'e'
				motor += "e";
			}
			//Setting up a way to run through all the of the strings and convert them to a list of Sensors and Motors
			int counter = 0;
			while (counter < 4) {
				//Checking char at the current count
				if (sensor.charAt(counter) != 'e') {
					//Create a Sensor in the Array of sensors
					sensors[counter] = new Sensor(sensor.charAt(counter), counter + 1);
				}
				//Checking char at the current count
				if (motor.charAt(counter) != 'e') {
					//Create a Motors in the Array of motors
					motors[counter] = new Motors(motor.charAt(counter), changeToLetterPort(counter + 1));
				}
				//Increase counter for next run through
				counter ++;
			}
		}
		catch (ParmeterNotInRange e){
			//This is a exception that is throw and created in the Sensor file
			//This is a worse case scencio
			System.out.println(e);
			System.out.println("Class " + this.getClass() + " is have a parmeter problem it will not work");
			motors = null;
			sensors = null;
		}
	}
	
	public void runMotorDegrees(char port, int degrees, double speed) {
		 motors[changeToNumberPort(port)].startMotorDegrees(speed, degrees);
	}
	
	public void turnOnMotor(char port, double speed) {
		 motors[changeToNumberPort(port)].startMotor(speed);
	}
	
	public void stopMotor(char port) {
		motors[changeToNumberPort(port)].stopMotor();
	}
	
	public double getValueForSensor(int port) {
		return sensors[port - 1].getValue();
	}
	
	public void setUpMotorSystem(char portOfMotor1, char portOfMotor2) {
		if(motorSystem == null) {	
			motorSystem = new MotorsSystem(motors[changeToNumberPort(portOfMotor1)], motors[changeToNumberPort(portOfMotor2)]);
		}
	}
	
	public void runSyncMotor(double powerOfMotor1, double powerOfMotor2) {
		if(motorSystem != null) {
			motorSystem.startSyncMotors(powerOfMotor1, powerOfMotor2);
		}
	}
	public void runSyncMotorDegrees(double powerOfMotor1, int degreesOfMotor1, double powerOfMotor2, int degreesOfMotor2) {
		if(motorSystem != null) {
			motorSystem.startSyncMotorsDegrees(powerOfMotor1, degreesOfMotor1, powerOfMotor2, degreesOfMotor2);
		}
	}
	public void stopSyncMotor() {
		if(motorSystem != null) {
			motorSystem.stopSyncMotors();
		}
	}
	public void resetSensor(int port) {
		sensors[port - 1].reset();		
	}
	
	public double getAverageEncoderSyncMotors() {
		return motorSystem.getMotorsEncodersAverage();
	}
	
	public void resetEncoders() {
		motorSystem.resetEncoders();
	}
	
	public Motors useOtherMethodsOfMotor(char port) {
		/**
		 * This is a temporay method that will be edit out of exist soon
		 */
		return motors[changeToNumberPort(port)];
	}
	
	private static char changeToLetterPort(int port) throws ParmeterNotInRange {
		switch(port) {
		case 1:
			return 'a';
		case 2:
			return 'b';
		case 3:
			return 'c';
		case 4:
			return 'd';
		default:
			throw new ParmeterNotInRange("Some how you port is not 1 to 4 in the letter changing state");
		}
	}
	
	private static int changeToNumberPort(char port) {
		switch(port) {
		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		default:
			return 0;
		}
	}
}