package org.usfirst.frc.team12.util;

import edu.wpi.first.wpilibj.XboxController;

public class XboxControllerTuned extends XboxController implements IHidRobot2 {
    private SpeedMode mode;
    public XboxControllerTuned(int id) {
        super(id);
        mode = SpeedMode.FAST;
    }
    
    public SpeedMode changeSpeed(){
        /*if(this.getBumperPressed(Hand.kLeft)){
            if(mode == SpeedMode.FAST) mode = SpeedMode.MED;
            else if(mode == SpeedMode.MED) mode = SpeedMode.SLOW;
        }
        if(this.getBumperPressed(Hand.kRight)){
            if(mode == SpeedMode.SLOW) mode = SpeedMode.MED;
            else if(mode == SpeedMode.MED) mode = SpeedMode.FAST;
        }*/
        if(this.getBumperPressed(Hand.kLeft)){
            if(mode == SpeedMode.FAST) mode = SpeedMode.SLOW;
        }
        if(this.getBumperPressed(Hand.kRight)){
            if(mode == SpeedMode.SLOW) mode = SpeedMode.FAST;
        }
        return mode;
    }

    public double lWheel() {
        double left = -this.getY(Hand.kLeft);

        if(mode == SpeedMode.FAST){
            left = -this.getY(Hand.kLeft);
        }
        else if(mode == SpeedMode.MED){
            left = -this.getY(Hand.kLeft)*0.8;
        }
        else if(mode == SpeedMode.SLOW){
            left = -this.getY(Hand.kLeft)*0.6;
        }

		if(Math.abs(left) >= 0.1) {
			return left;
        }
        else {
            return 0.0;
        }
    }

    public double rWheel() {
        double right = -this.getY(Hand.kRight);
        
        if(mode == SpeedMode.FAST){
            right = -this.getY(Hand.kRight);
        }
        else if(mode == SpeedMode.MED){
            right = -this.getY(Hand.kRight)*0.8;
        }
        else if(mode == SpeedMode.SLOW){
            right = -this.getY(Hand.kRight)*0.6;
        }

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
    public int steering(){
        if(this.getStartButton()){
            return 1;
        }
        else if(this.getBackButton()){
            return -1;
        }
        else{
            return 0;
        }
    }

    public int Camera(){
        boolean val1 = this.getYButton();
        boolean val2 = this.getXButton();
        if(val1){
            return 1;
        }
        if(val2){
            return 2;
        }
        return 0;
    }
    
}
