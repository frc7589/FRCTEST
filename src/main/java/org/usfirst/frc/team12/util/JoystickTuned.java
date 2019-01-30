package org.usfirst.frc.team12.util;

import edu.wpi.first.wpilibj.Joystick;
/*
public class JoystickTuned extends Joystick implements IHidRobot2 {
    public JoystickTuned(int id) {
        super(id);
    }

    public void findButton() {
        for (int i=0; i<10; i++) {
            System.out.printf("%d:%s ", i, this.getRawButton(i)?"On":"Off");
        }
        System.out.print("\n");
    }

    public SpeedMode changeSpeed(){
        //method not supported
        return SpeedMode.MED;
    }

    public double lWheel() {
        double y = this.getY();
        double x = this.getX();
        double portionL = (x+1.0)/2.0;
        double maxSpd = Math.max(portionL, 1-portionL);
        if (Math.abs(y)>=0.1) {
            return -y*portionL/maxSpd;
        }
        else if (Math.abs(x)>=0.1) {
            return x;
        }
        else {
            return 0.0;
        }
    }
    public double rWheel() {
        double y = this.getY();
        double x = this.getX();
        double portionL = (x+1.0)/2.0;
        double maxSpd = Math.max(portionL, 1-portionL);
        if (Math.abs(y)>=0.1) {
            return -y*(1-portionL)/maxSpd;
        }
        else if (Math.abs(x)>=0.1) {
            return -x;
        }
        else {
            return 0.0;
        }
    }
    public double panelArm() {
        boolean buttU = this.getRawButton(5);
        boolean buttD = this.getRawButton(6);
        if (buttU) {
            return -0.3;
        }
        else if (buttD) {
            return 0.3;
        }
        else {
            return 0.0;
        }
    }
    public double cargoSlope() {
        double z = this.getZ();
        if (Math.abs(z)>0.1) {
            return -z*0.8;
        }
        else {
            return 0.0;
        }
    }

}
*/