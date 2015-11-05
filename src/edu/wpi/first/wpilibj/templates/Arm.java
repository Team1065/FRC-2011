/************************************************************/
/*****************        Arm        ************************/
/*****************  FIRST Team 1065  ************************/
/***************** Updated 2/02/2011 ************************/
/************************************************************/

/****************Package Files******************************/
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Jaguar;
import  edu.wpi.first.wpilibj.Victor;
import  edu.wpi.first.wpilibj.DigitalInput;
import  edu.wpi.first.wpilibj.AnalogChannel;
/***********************************************************/

/*********************Arm Class*****************************/
//Contains:
//Arm Constructor
//Manual Control Method
//Preset Position Method
public class Arm {
    
    Victor arm1;                //Arm Motor 1
    Victor arm2;                //Arm Motor 2
    DigitalInput maxLimit;      //Limit Switch for maximum extension
    DigitalInput minLimit;      //Limit Switch for minimum retraction
    AnalogChannel pot;          //Potentiometer
    
    //Create Arm constructor based on 2 motors, 2 limit switches, & 1 potentiometer
    public Arm (Victor v1, Victor v2, AnalogChannel p, DigitalInput min, DigitalInput max){
    arm1=v1;
    arm2=v2;
    maxLimit=max;
    minLimit=min;
    pot=p;
    }


    //Manual Control Method allows control of arm at defined speeds
    public void manual(double val, double mod){
    //If arm is between limit switches
    if((minLimit.get()== true) && (maxLimit.get() == true)){
        arm1.set((val*mod));   //Set arm motors to input value * speed modifier
        arm2.set((val*mod));
        }
    //This case should never occur, if it does immediately stop motors to prevent arm damage
    else if ((minLimit.get() == false) && (maxLimit.get()==false)){
        arm1.set(0);  //Stop arm motors
        arm2.set(0);
        }
    //If arm at minimum limit and input moves towards max switch
    //                            OR
    //If arm at maximum limit and input moves towards min switch
    else if((minLimit.get() == true&& val<0) ||(maxLimit.get()==true&&val>0)){
        arm1.set((val*mod)); //Set arm motors to input value * speed modifier
        arm2.set((val*mod));
        }
    //All other cases stop arm
    else{
        arm1.set(0);
        arm2.set(0);
        }
    }
    //Position Method allows arm movemnt at set target positions at defined speed and tolerance
    public void position(double target, double val, double tol){

        //This case should never occur, if it does immediately stop motor to stop arm damage
        if ((minLimit.get()==false) && (maxLimit.get()==false)){
            arm1.set(0);
            arm2.set(0);
            }
        //If arm is between limit switches
        else if((minLimit.get() == true) && (maxLimit.get() == true)){
            //If arm is below target
            if(pot.getVoltage()<(target-tol)){
                arm1.set(val);  //Move arm up to target
                arm2.set(val);
                }
            //If arm is above target
            else if(pot.getVoltage()>(target+tol)){
                arm1.set(-val);  //Move arm down to target
                arm2.set(-val);
                }
            //Arm is within target tolerance
            else{
                arm1.set(0); //Stop arm
                arm2.set(0);
                }
            }
        //If arm at minimum limit
        else if((minLimit.get() == false)){
            //If arm lower than target position
            if(pot.getVoltage()<(target-tol)){
                arm1.set(val);  //Move arm up to target
                arm2.set(val);
                }
            //If arm is at target position within set tolerances
            else{
                arm1.set(0);    //Stop Arm
                arm2.set(0);
                }
            }
        //If arm at maximum limit
        else if(maxLimit.get() == false){
            //If arm higher than target position
            if(pot.getVoltage()>(target+tol)){
                arm1.set(-val);  //Move arm down to target
                arm2.set(-val);
                }
            else{
                arm1.set(0);    //Stop Arm
                arm2.set(0);
                }
            }
    }
        
}

