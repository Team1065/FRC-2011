/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MooseBot2011 extends SimpleRobot {


        SensorBase sBase;
        Jaguar frontLeft  = new Jaguar(1);
        Jaguar frontRight = new Jaguar(2);
        Jaguar backLeft   = new Jaguar(3);
        Jaguar backRight  = new Jaguar(4);
        Victor arm1       = new Victor(5);
        Victor arm2       = new Victor(6);
        Victor deploy     = new Victor(7);
        Solenoid sClaw    = new Solenoid(1);
        Solenoid elbow    = new Solenoid(2);
        Solenoid alignR   = new Solenoid(3);
        Solenoid miniR    = new Solenoid(4);


        Compressor airComp    = new Compressor(1,1);
        DigitalInput maxLim   = new DigitalInput(2);
        DigitalInput minLim   = new DigitalInput(3);
        DigitalInput cSwitch1 = new DigitalInput(4);
        DigitalInput cSwitch2 = new DigitalInput(5);
        DigitalInput retract  = new DigitalInput(6);
        DigitalInput extend   = new DigitalInput(7);
        DigitalInput pole     = new DigitalInput(8);
        DigitalInput base     = new DigitalInput(9);
        DigitalInput lineL    = new DigitalInput(10);
        DigitalInput lineC    = new DigitalInput(11);
        DigitalInput lineR    = new DigitalInput(12);
        DigitalInput slider   = new DigitalInput(13);
        DigitalInput auto     = new DigitalInput(14);
        AnalogChannel accelX  = new AnalogChannel(1);
        AnalogChannel accelY  = new AnalogChannel(2);
        AnalogChannel gyro    = new AnalogChannel(3);
        AnalogChannel ultraF  = new AnalogChannel(4);
        AnalogChannel ultraB  = new AnalogChannel(5);
        AnalogChannel pot     = new AnalogChannel(6);

        Joystick leftStick = new Joystick(1);
        Joystick rightStick = new Joystick(2);
        Joystick armStick = new Joystick(3);
        DriverStationEnhancedIO ds;

        Drive mooseDrive = new Drive(frontLeft, frontRight, backLeft, backRight);
        Arm mooseArm= new Arm(arm1,arm2,pot,minLim,maxLim);
        Claw mooseClaw=new Claw(sClaw,cSwitch1,cSwitch2);
        SonicAssist sAssist= new SonicAssist(ultraF, ultraB, mooseDrive);
        Timer time = new Timer();

        double leftY;
        double rightY;



    public MooseBot2011(){
    getWatchdog().setEnabled(true);
    airComp.start();
    ds =DriverStation.getInstance().getEnhancedIO();
    }

    public void autonomous() {
       getWatchdog().setEnabled(false);          //Disable Watchdog
       if(isAutonomous())
       {
           airComp.start();
           time.reset();
           time.start();
           mooseClaw.open();
           while(isAutonomous() && time.get() < 2)
           {
               sAssist.senseBack(32,3,.7);
               mooseArm.position(3.45, .7, .1);
               elbow.set(true);
           }
           while(isAutonomous() && time.get() < 8)
           {
               sAssist.senseBack(32,3,.2);
               mooseArm.position(3.65, .7, .1);
           }
           mooseClaw.close();
           mooseDrive.tank(0,0);
           while(isAutonomous() && time.get() <10)
           {
               mooseDrive.tank(.3,-.3);
           }
           mooseDrive.tank(0, 0);
       }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {


        airComp.start();
        getWatchdog().setEnabled(true);
        getWatchdog().setExpiration(1.5);
        SensorBase.setDefaultSolenoidModule(2);


        while (true && isOperatorControl() && isEnabled()){
            
            try{
                getWatchdog().feed();
                leftY = leftStick.getY();
                rightY = rightStick.getY();

 /*****************************Drive**********************************/
               /* if(rightStick.getTop()){
                    mooseDrive.mecanum(rightStick.getX(), -rightStick.getY());
                    }
                else{
                    mooseDrive.tank(-leftY, rightY);
                    }*/
 /****************************Sonic Assist****************************/
                if(leftStick.getTrigger())
                {
                    getWatchdog().feed();
                    if(leftStick.getY()>.3)
                    {
                        sAssist.senseBack(35,3,.5);
                        
                    }
                    else if(leftStick.getY()<-.3)
                    {
                        sAssist.senseFront(35,3,.5);
                       
                    }
                    else
                    {
                        mooseDrive.tank(0, 0);
                    }
                }
                else{
                    getWatchdog().feed();
                    if(rightStick.getTop()){
                    mooseDrive.mecanum(rightStick.getX(), -rightStick.getY());
                    }
                    else{
                    mooseDrive.tank(-leftY, rightY);
                    }
                }
 /*****************Arm Implementation********************************/
                if(armStick.getTrigger()==false){
                    if(ds.getAnalogIn(1)<.8){
                        mooseArm.position(.45, .5, .1);
                        }
                    else if(ds.getAnalogIn(1)>=1.0 && ds.getAnalogIn(1)<=1.3){
                        mooseArm.position(1.35,.5, .1);
                        }
                    else if(ds.getAnalogIn(1)>=1.7&& ds.getAnalogIn(1)<=1.9){
                        mooseArm.position(1.6, .5, .1);
                        }
                    else if(ds.getAnalogIn(1)>=2.1&& ds.getAnalogIn(1)<=2.3){
                        mooseArm.position(1.95, .5, .1);
                        }
                    else if(ds.getAnalogIn(1)>=2.85&& ds.getAnalogIn(1)<=3.0){
                        mooseArm.position(2.9, .5, .1);
                        }
                    else if(ds.getAnalogIn(1)>=3.2){
                        mooseArm.position(3.45, .5, .1);
                        }
                    }
                else{
                  mooseArm.manual(armStick.getY(), .7);
                    }

  /************************Claw*************************************/
            if(ds.getDigital(4) == true){
                mooseClaw.open();
                }
            else if(ds.getDigital(5)==true){
               mooseClaw.close();
                }
            else{
               mooseClaw.sense();
                }

 /************************Elbow*************************************/
            if(ds.getDigital(1) == true){
                elbow.set(true);
            }
            else{
                elbow.set(false);
            }


            }
  /********************Driver Station Exception Catch******************/

            catch (EnhancedIOException ex){
                ex.printStackTrace();
                }
            }
        airComp.stop();
        }
    }


