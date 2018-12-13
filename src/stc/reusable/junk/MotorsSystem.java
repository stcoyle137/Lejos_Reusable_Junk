package stc.reusable.junk;

import stc.reusable.junk.Motors;

class MotorsSystem{
	private Motors m1;
	private Motors m2;
	
	public MotorsSystem(Motors motor1, Motors motor2){
		m1 = motor1;
		m2 = motor2;
		m1.setUpSyncMotor(m2);
	}
	
	public void startSyncMotorsDegrees(double percentOfMotor1, int degreesOfMotor1, double percentOfMotor2, int degreesOfMotor2) {
		m1.startingSync();
		m1.startMotorDegrees(percentOfMotor1, degreesOfMotor1);
		m2.startMotorDegrees(percentOfMotor2, degreesOfMotor2);
		m1.stoppingSync();
		m1.waitForFinish();
		m2.waitForFinish();
	}
	
	public void startSyncMotors(double percentOfMotor1, double percentOfMotor2) {
		m1.startingSync();
		m1.startMotor(percentOfMotor1);
		m2.startMotor(percentOfMotor2);
		m1.stoppingSync();
	}
	
	public void stopSyncMotors() {
		m1.startingSync();
		m1.stopMotor();
		m2.stopMotor();
		m1.stoppingSync();
	}
	
	public double getMotor1Encoder() {
		return m1.getEncoderValue(false);
	}
	
	public double getMotor2Encoder() {
		return m2.getEncoderValue(false);
	}
	
	public double getMotorsEncodersAverage() {
		return (m1.getEncoderValue(false) + m2.getEncoderValue(false))/2;
	}
	public void resetEncoders() {
		m1.resetEncoder();
		m2.resetEncoder();
	}
}