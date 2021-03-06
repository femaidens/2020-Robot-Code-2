/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class AlignTurret_PID extends Command {
  private static final double KP = 0.1;
	private static final double KI = 1e-3;
	private static final double KD = 0.0;

  public static double speed;

  private static double min_error = 0.1;
  private static double min_command = 0.0;
  static double current_error = 0.0; 
  static double previous_error = 0.0; 
  static double integral = 0.0;
  static double derivative = 0.0;
  static double adjust = 0.0;
  static double time = 0.1;

  public AlignTurret_PID(double s){
    speed = s;
  }


  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Robot.limelight.setLiveStream(0);
    Robot.limelight.setLEDMode(3);
  }

  // cCalled repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {     
    if(!Robot.limelight.objectSighted()){
      System.out.println("No object");
      if(Robot.shooter.outLimit()){
        speed *= -1;
      }
      Shooter.spinTurret(speed);
    }
    else{
      System.out.println("Object Sighted");
      System.out.println(Robot.shooter.turretHall.getPosition());

      previous_error = current_error;
      current_error = Robot.limelight.getTx();
      integral = (current_error+previous_error)/2*(time);
      derivative = (current_error-previous_error)/time;
      adjust = (KP*current_error + KI*integral + KD*derivative) * -0.1;
        
      /*if (current_error > min_error){
        adjust += min_command;
      }
      else if (current_error < -min_error){
        adjust -= min_command;
      }*/
        
      try {
        Thread.sleep((long)(time*1000));
      }
      catch(InterruptedException e){
      }
        
      System.out.println("Adjust: " + adjust);
      /*double total = speed + adjust;
      if((int)Shooter.turretHall.getPosition() == -45 && speed + adjust < 0){
        total *= -1;
      }else if((int)Shooter.turretHall.getPosition() == 45 && speed + adjust > 0){
        total *= -1;
      }*/
      
     // if(Shooter.limitTurret.get() == false) {
     //   Shooter.spinTurret(-(speed));
     // }
      //if(Shooter.outLimit()){
     //   Shooter.spinTurret(-(speed));
     // }
      Shooter.spinTurret(speed + adjust);
    }


    //System.out.println("Adjust: " + adjust);
    //Shooter.spinTurret(speed+adjust);
    
   //System.out.println(Robot.shooter.turretHall.getPosition());
   /* if(Robot.shooter.outLimit()){
      speed *= -1;
    }
    Shooter.spinTurret(speed+adjust);*/
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Shooter.spinTurret(0.0);
    //Limelight.setLEDMode(1);
  }


  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}