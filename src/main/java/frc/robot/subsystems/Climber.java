/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.MoveClimb;

public class Climber extends Subsystem {
  /**
  * Creates a new Climber.
  */
  
  public static CANSparkMax climbMotor = new CANSparkMax(RobotMap.climbMotorPort, MotorType.kBrushless);
  
  public static DigitalInput limitSwitchB = new DigitalInput(RobotMap.limitPortB); //bottom
  public static DigitalInput limitSwitchT = new DigitalInput(RobotMap.limitPortT); //top
  public static DoubleSolenoid climbSol = new DoubleSolenoid(RobotMap.solPortF, RobotMap.solPortR);
  
  public Climber() {
  }
  
  public static void move(){
    double value = OI.joyPort2.getRawAxis(0);
    if(value>0 && !limitSwitchT.get()){
      climbSol.set(Value.kReverse);
      climbMotor.set(value);
    }else if(value<0 && !limitSwitchB.get()){
      climbSol.set(Value.kForward);
      climbMotor.set(value);
    }else{
      climbMotor.set(0.0);
    }
  }
  
  @Override
  protected void initDefaultCommand() {
    //  setDefaultCommand(new MoveClimb());
  }
}