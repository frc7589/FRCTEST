/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team12.robot;

import org.usfirst.frc.team12.util.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Servo;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class test_robot extends TimedRobot {
	/**
	 * can be either {@link XboxControllerTuned} or {@link JoystickTuned}
	 */
	private IHidRobot2 stick;
	//Base
	private WPI_VictorSPX baseLeft,baseRight;
	private DifferentialDrive base;

	//Hatch and Cargo
	private WPI_VictorSPX hatchmotor;
	private WPI_VictorSPX cargo;
	private SpeedMode spmd;//Speed record for SmartDashBoard

	private DigitalInput micro;
	private UsbCamera Camera1;
	private UsbCamera Camera2;
	private VideoSink server;

	private Servo steer;

	private boolean reseter;

	private double prevRot = 0.0; // true = neg, false = pos
	private double svoAngle;

	//private AHRS gyro;

	@Override
	public void robotInit() {
		stick = new XboxControllerTuned(0);

		baseLeft = new WPI_VictorSPX(0);
		baseRight = new WPI_VictorSPX(5);
		base = new DifferentialDrive(baseLeft, baseRight);

		hatchmotor = new WPI_VictorSPX(3); 
		cargo = new WPI_VictorSPX(2);
		spmd = SpeedMode.FAST;

		//CameraServer.getInstance().startAutomaticCapture();
		micro = new DigitalInput(9);

		//steer = new Servo(8);
		//steer.set(0.0);
		svoAngle= 0;
		micro = new DigitalInput(9);

		Camera1 = CameraServer.getInstance().startAutomaticCapture(0);
		Camera2 = CameraServer.getInstance().startAutomaticCapture(1);
		server = CameraServer.getInstance().getServer();
		
		/*
		try{//Gyro
			gyro = new AHRS(SerialPort.Port.kMXP);
		}
		catch(RuntimeException e){
			DriverStation.reportError("Error instantiating navX MXP:  " + e.getMessage(), true);
		}*/
	}

	@Override
	public void robotPeriodic() {
		spmd = stick.changeSpeed();
		SmartDashboard.putString("SpeedMode", spmd.toString());
		SmartDashboard.putBoolean("Micro", micro.get());
		//svoAngle = SmartDashboard.getNumber("ServoValue", svoAngle);
		SmartDashboard.putNumber("ServoValue", svoAngle);
		/*if(gyro != null){
			SmartDashboard.putBoolean("GyroConnection", gyro.isConnected());
			SmartDashboard.putData("Gyro", gyro);
		}*/	
	}

	@Override
	public void autonomousInit() {
	}	

	@Override
	public void autonomousPeriodic() {
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		base.tankDrive(stick.lWheel(), stick.rWheel(), false);
		hatchmotor.set(ControlMode.PercentOutput, stick.panelArm());
		double cargoSlopeSpd = stick.cargoSlope();
		
		/*int currCam = stick.Camera();
		if(currCam==1&&currCam!=preCamera){
			server.setSource(Camera1);
			preCamera = currCam;
		}
		else if(currCam==2&&currCam!=preCamera){
			server.setSource(Camera2);
			preCamera = currCam;
		}*/

	
		if(micro.get() && cargoSlopeSpd*prevRot>0.0){
			cargo.set(ControlMode.PercentOutput, 0);
		}
		else{
			cargo.set(ControlMode.PercentOutput, cargoSlopeSpd);
			//System.out.println(stick.cargoSlope());
			if (cargoSlopeSpd!=0.0 && !micro.get()) {
				prevRot = cargoSlopeSpd;
			}
		}

		//set angle offset from stick
		//System.out.println(steer.get());
		//svoAngle = Math.max(0.0 , Math.min(1.0, steer.get() + stick.steering() * 0.002 ) ); // prevent angle out of bound
		//System.out.println(angle);
		/*if (Math.abs(svoAngle-steer.get())>1.0/50)*/ steer.set(svoAngle);
	}

	@Override
	public void testInit() {
		
	}
	
	@Override
	public void testPeriodic() {
		
	}
}