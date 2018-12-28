package org.usfirst.frc.team12.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class RobotMap {
	public static Joystick stick;
	public static Compressor comp;
	public static DoubleSolenoid sol; 
	
	private static final int PCM_ID = 48;
	
	public static void init() {
		stick = new Joystick(0);
		comp = new Compressor(PCM_ID);
		sol = new DoubleSolenoid(PCM_ID, 6, 7 );
	}
}
