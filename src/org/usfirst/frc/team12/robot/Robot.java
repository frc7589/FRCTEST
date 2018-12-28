/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team12.robot;

import org.usfirst.frc.team12.util.RangeMapper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private Joystick stick;
	
	private WPI_VictorSPX baseLeft1;
	private WPI_VictorSPX baseLeft2;
	private WPI_VictorSPX baseRight1;
	private WPI_VictorSPX baseRight2;
	private VictorSPX catcher;
	private Encoder motorEnc;
	private WPI_VictorSPX arm1;
	private WPI_VictorSPX arm2;
	private Timer timer;
	
	private SpeedControllerGroup baseLeft,baseRight,armGrp;
	private DifferentialDrive base;
	private DigitalInput reseter;
	private Compressor comp;
	private Solenoid sol;
	
	
	//Motor Fix Constant(Not Used)
	private final int catcherSpot = 1;
			
		// Autonomous Constant
	private final double firstStop = 3.0;
	private final double firstDrvSpd = 0.5;
	private final double firstDrvRot = 0.0;
	private final double armDownTime = 0.5;
	private final int comp_id = 48;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		stick = new Joystick(0);
		
		//motor = new VictorSPX(5);
		baseLeft1 =  new WPI_VictorSPX(0);
		baseLeft2 =  new WPI_VictorSPX(2);
		baseRight1 =  new WPI_VictorSPX(1);
		baseRight2 =  new WPI_VictorSPX(3);
		
		baseRight = new SpeedControllerGroup(baseRight1, baseRight2);
		baseLeft = new SpeedControllerGroup(baseLeft1, baseLeft2);
		base = new DifferentialDrive(baseLeft, baseRight);
		
		catcher = new VictorSPX(5);
		motorEnc = new Encoder( 8, 9, true, CounterBase.EncodingType.k4X); // I don't know why but reverse true ==> motor same direct
		
		arm1 = new WPI_VictorSPX(4);
		arm2 = new WPI_VictorSPX(7);
		armGrp = new SpeedControllerGroup(arm1, arm2);
		CameraServer.getInstance().startAutomaticCapture();
		reseter = new DigitalInput(7);
		timer = new Timer();
		comp = new Compressor(comp_id);
		sol = new Solenoid(0);

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
	/*@Override
	public void autonomousInit() {
		timer.start();
	}*/

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		System.out.printf("Now : %f\n", timer.get());	
		
		if( timer.get() < firstStop) {  //Wait for stop at spot
			base.arcadeDrive(firstDrvSpd, firstDrvRot); //Drive before arrive

			catcher.set(ControlMode.PercentOutput, 0.0);
		}
		else { //Arrive Spot!
			base.arcadeDrive(0.0, 0.0); // Stop Machine
			
			//Timer.delay(1.0);
			
			//Release Flag for spot
			if(motorEnc.get()<= catcherSpot+1){
				catcher.set(ControlMode.PercentOutput,0.8);
				//System.out.printf("1:%d\n", motorEnc.get());
			}
			else {
				catcher.set(ControlMode.PercentOutput, 0.0);
				
			}
			if(timer.get() > firstStop+1 && timer.get() < firstStop+2 ) {
				base.arcadeDrive(-0.4, 0.0);
			}
			else {
				base.arcadeDrive(0.0, 0.0);
			}
			
		}
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	
	
	@Override
	public void teleopPeriodic() {
		/*System.out.printf("Stick (%f, %f ,%f ,%d)\n", stick.getX(), stick.getY(),stick.getZ(),motorEnc.get());
		//armGrp.set(stick.getZ()*(-0.5));

		rotate = new RangeMapper(-0.35, 0.35).mapRange(stick.getX());		
		//Set forward speed while stick push 
		speedct = new RangeMapper(0.5,-0.5).mapRange(stick.getY()) ;
		
		if (Math.abs(stick.getX()) > 0.1 || Math.abs(stick.getY()) > 0.1) {
			
			//Calculate move speed and move
			System.out.printf("RUN => Speed : %f , Rotate : %f \n", speedct,rotate);
			base.arcadeDrive(speedct, rotate, false);
			
		}*/	

		/*if(Math.abs(stick.getZ()) > 0.1) {
			armGrp.set(stick.getZ()*(-0.4));
		}
		else {
			armGrp.set(0.0);	
		}
		
		
		//System.out.printf("Z : %f\n", stick.getZ());
		System.out.printf("motor_get : %d rester_get : (%b)\n", motorEnc.get(), reseter.get());
		
		if(stick.getRawButton(7)) { //lower button
			catcher.set(ControlMode.PercentOutput, 0.8);
		}
		else if(stick.getRawButton(5)) {//upper button
			catcher.set(ControlMode.PercentOutput, -0.8);
		}
		else {
			catcher.set(ControlMode.PercentOutput, 0.0);
		}*/
  	}
	private double speedct;
	private double rotate;
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		rotate = new RangeMapper(-0.5,0.5).mapRange(stick.getX());
		speedct = new RangeMapper(0.5,-0.5).mapRange(stick.getY()) ;
		if(stick.getY()>0.1||stick.getX()>0.1) {
			base.tankDrive(speedct+rotate, speedct-rotate);
		}
	}	
}
