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
//cimport com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class test_robot extends TimedRobot {
	private IHidRobot2 stick;	// XboxControllerTuned or JoystickTuned

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

	//private AHRS gyro;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		stick = new XboxControllerTuned(0);

		baseLeft = new WPI_VictorSPX(0);
		baseRight = new WPI_VictorSPX(5);
		base = new DifferentialDrive(baseLeft, baseRight);

		hatchmotor = new WPI_VictorSPX(3);
		cargo = new WPI_VictorSPX(2);
		spmd = SpeedMode.FAST;

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
		/*if(gyro != null){
			SmartDashboard.putBoolean("GyroConnection", gyro.isConnected());
			SmartDashboard.putData("Gyro", gyro);
		}*/	
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
	}	

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
	}

	double prevRot = 0.0; // true = neg, false = pos
	int preCamera = 0;

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		base.tankDrive(stick.lWheel(), stick.rWheel(), false);
		hatchmotor.set(ControlMode.PercentOutput, stick.panelArm());
		double cargoSlopeSpd = stick.cargoSlope();
		
		int currCam = stick.Camera();
		if(currCam==1&&currCam!=preCamera){
			server.setSource(Camera1);
			preCamera = currCam;
		}
		else if(currCam==2&&currCam!=preCamera){
			server.setSource(Camera2);
			preCamera = currCam;
		}

	
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

		
	}
	  
	  public void testInit() {
	  }
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}