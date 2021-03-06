/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends Subsystem {
  public static final double K = 217.16376556;
  public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  public static NetworkTableEntry tv = table.getEntry("tv"); // any valid targets? (0 for no target, 1 for target) 
  public static NetworkTableEntry tx = table.getEntry("tx"); // horizontal offset from crosshair to target (-27 to 27 degrees)
  public static NetworkTableEntry ta = table.getEntry("ta"); // target area (0% to 100%)
  public static NetworkTableEntry ts = table.getEntry("ts"); // skew or rotation (-90 to 0 degrees)*/
  public static NetworkTableEntry ty = table.getEntry("ty"); // y angle of the limelight to the target

  public Limelight(){
    setLiveStream(0);
  }
  
  public static boolean objectSighted(){
    if (tv.getDouble(0.0) == 1.0){
      return true;
    }
      return false;
  }  
  
  public static double getTx(){
    if(objectSighted()){
      return tx.getDouble(0.0);
    }
    return 0;
  }

  public static void setLiveStream(int mode){
    // mode = 0 sets limelight to vision processor mode
    // mode = 1 sets limelight to driver camera (increases exposure and disables vision processing)
    //table.getEntry("camMode").setNumber(mode); 

    // can just connect another USB camera to the limelight through the USB port
    //table.getEntry("stream").setNumber(0); // Standard Side-by-side streaming 
  }

  public static void setLEDMode(int mode){
    table.getEntry("ledMode").setNumber(mode);
    // mode = 0	use the LED Mode set in the current pipeline
    //mode = 1	force off
    // mode = 2	force blink
    //mode = 3	force on
  }

  public static double getArea(){
    return ta.getDouble(0.0);
  }

  public static double getDistance(){
   /* double h1 = 31.5;
    double h2 = 57;
    double a1 = 0;
    double a2 = ty.getDouble(0.0)*(180/Math.PI);
    return (h2-h1)/Math.tan(a1+a2);
    */
    return K/Math.sqrt(getArea()) + 4;
  }

/*
  public double getTs(){
    if(objectSighted())
      return ts.getDouble(0.0);
    else
      return 0;
  }
  */

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}