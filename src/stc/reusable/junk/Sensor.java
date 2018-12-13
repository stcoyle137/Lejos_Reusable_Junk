package stc.reusable.junk;

import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

import lejos.hardware.BrickFinder;

class Sensor implements SensorInter{
	SensorInter sensor;
	public Sensor(char type, int port) throws ParmeterNotInRange {
		switch(type) {
		case 't':
			sensor = new TouchSensor(port);
			break;
		case 'c':
			sensor = new ColorSensor(port);
			break;
		case 'g':
			sensor = new GyroSensor(port);
			break;
		default:
			throw new ParmeterNotInRange("The Sensor type is not one of the following: t, l, or g");
		}
	}

	public double getValue() {
		return sensor.getValue();
	}
	
	public String getPort() {
		return sensor.getPort();
	}
	
	public void reset() {
		sensor.reset();
	}
}

class PortForSensors{
	private lejos.hardware.port.Port port;
	private String whichPort;
	public PortForSensors(int portPlace) throws ParmeterNotInRange {
		switch (portPlace) {
			case 1:
				whichPort = "S1";
				break;
			
			case 2:
				whichPort = "S2";
				break;
			
			case 3:
				whichPort = "S3";
				break;
			
			case 4:
				whichPort = "S4";
				break;
		
			default:
				throw new ParmeterNotInRange("The port is not in range of the following: 1-4");
			}
		port = BrickFinder.getDefault().getPort(whichPort);
	}
	
	public lejos.hardware.port.Port getPort() {
		return port;
	}
	
	public String getPortString() {
		return whichPort;
	}

}

class TouchSensor implements SensorInter{
	private EV3TouchSensor touchSensor;
	private PortForSensors wPort;
	private SensorMode touch;
	private float[] sample;
	
	public TouchSensor(int port) throws ParmeterNotInRange {
		wPort = new PortForSensors(port);
		touchSensor = new EV3TouchSensor(wPort.getPort());
		touch = touchSensor.getTouchMode();
		sample = new float[touch.sampleSize()];

	}
	public double getValue() {
		touch.fetchSample(sample, 0);
		return sample[0];
	}
	
	public String getPort() {
		return wPort.getPortString();
	}
	
	public void reset() {
		
	}
}
class ColorSensor implements SensorInter {
	private EV3ColorSensor colorSensor;
	private PortForSensors wPort;
	public ColorSensor (int port) throws ParmeterNotInRange {
		wPort = new PortForSensors(port);
		colorSensor = new EV3ColorSensor(wPort.getPort());
	}
	public double getValue() {
		return colorSensor.getColorID();
	}
	
	public String getPort() {
		return wPort.getPortString();
	}
	public void reset() {
		
	}
}

class GyroSensor implements SensorInter {
	private EV3GyroSensor gyroSensor;
	private SampleProvider gyroReader;
	private PortForSensors wPort;
	
	public GyroSensor (int port) throws ParmeterNotInRange {
		wPort = new PortForSensors(port);
		gyroSensor = new EV3GyroSensor(wPort.getPort());
		gyroReader = gyroSensor.getAngleMode();
	}
	
	public double getValue() {
		float[] sample = new float[gyroReader.sampleSize()];
		gyroReader.fetchSample(sample, 0);
		return sample[0];
	}
	
	public String getPort() {
		return wPort.getPortString();
	}
	public void reset() {
		gyroSensor.reset();
	}
}

class ParmeterNotInRange extends Exception {
	private static final long serialVersionUID = 1L;

	public ParmeterNotInRange() {}

	public ParmeterNotInRange(String message) {
		super(message);
	}
}

interface SensorInter{
	public double getValue();
	public String getPort();
	public void reset();
}