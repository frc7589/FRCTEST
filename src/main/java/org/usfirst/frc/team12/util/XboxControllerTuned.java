package org.usfirst.frc.team12.util;

import edu.wpi.first.wpilibj.XboxController;

public class XboxControllerTuned extends XboxController implements IHidRobot2 {
    public XboxControllerTuned(int id) {
        super(id);
    }

    public double lWheel() {
		double left = -this.getY(Hand.kLeft);
		if(Math.abs(left) >= 0.1) {
			return left;
        }
        else {
            return 0.0;
        }
    }

    public double rWheel() {
        double right = -this.getY(Hand.kRight);
        if(Math.abs(right) >= 0.1) {
			return right;
        }
        else {
            return 0.0;
        }
    }

    public double panelArm() {
        double speed = 0.3;
		double value2 = this.getTriggerAxis(Hand.kLeft);       //hatch(snowblower)
		double value1 = this.getTriggerAxis(Hand.kRight);       //hatch(snowblower)
        if (value2>0.1) {
            return value2*speed;
        }
        else if (value1>0.1) {
            return value1*-speed;
        }
        else {
            return 0.0;
        }
    }

    public double cargoSlope() {
        double speed = 0.8;
        boolean val1 = this.getAButton();
        boolean val2 = this.getBButton();
        if (val1) {
            return -speed;
        }
        else if (val2) {
            return speed;
        }
        else {
            return 0.0;
        }
    }
}
