package org.usfirst.frc.team12.util;

/**
 * Interface for intergration of HID 
**/
public interface IHidRobot2 {
    /**
     * @return left side wheel speed from 0.0 to 1.0
    **/
    public double lWheel();

    /**
     * @return right side wheel speed from 0.0 to 1.0
    **/
    public double rWheel();

    /**
     * @return hatch catching motor speed
    **/
    public double panelArm();

    /**
     * @return cargo slope controlling motor speed
    **/
    public double cargoSlope();

    /**
     * change current speed mode
     * only have effect on  {@link XboxControllerTuned }
     * @return the changed speed mode
    **/
    public SpeedMode changeSpeed();
<<<<<<< HEAD

    /**
     * @return <ul>
     * <li> 0  - no pressed
     * <li> 1  - increase servo angle
     * <li> -1 - decrease servo angle
     * </ul>
     */
    public int steering();
=======
    public int Camera();
>>>>>>> 705acdb6b99aa8e189bae5c3e366b99f53faba3d
}