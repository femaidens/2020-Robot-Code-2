/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Climber;


public class MoveClimb extends Command{

  public MoveClimb(){
    requires(Robot.climber);
	}

	protected void initialize(){
    System.out.println("Ready");
	}

	protected void execute() {
		if(!Climber.limitSwitchB.get()){
			//SmartDashboard.putString("Ready?", Climb.ready());
		} 
    Climber.move();
    System.out.println("Forward");
   // Climb.climbSol.set(Value.kForward);

  }

  protected boolean isFinished(){
	  return true;
  }

  protected void end(){
  }
	
  protected void interrupted(){
    System.out.println("reverse");
   // Climb.climbSol.set(Value.kReverse);
  }
}