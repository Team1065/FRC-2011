/************************************************************/
/*****************    SonicAssist    ************************/
/*****************  FIRST Team 1065  ************************/
/***************** Updated 2/02/2011 ************************/
/************************************************************/

//RELATE THE CONVERSION OF MILLIVOLTS TO INCHES


/**********Package Files***********************/
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.AnalogChannel;
/**********************************************/

/****************SonicAssist Class*************/
//Contains:
//SonicAssist Constructor
//Sense Front Method
//Sense Back  Method
//Triangulate Method

public class SonicAssist {

    AnalogChannel uFront;      //Front Side Ultrasonic Sensor
    AnalogChannel uBack;       //Back  Side Ultrasonic Sensor
    AnalogChannel uLeft;       //Left Ultrasonic Sensor for deployment triangulation
    AnalogChannel uRight;      //Right Ultrsonic Sensor for deployment triangulation
    Drive mooseDrive;       //Drive System used with Ultrasonic Assistance
    final double sonicConstant = .0098;

    //Create SonicAssist constructor based on 4 Ultrasonic Sensors
    /*public SonicAssist(AnalogChannel uL, AnalogChannel uR,AnalogChannel uF, AnalogChannel uB){
        uLeft=uL;
        uRight=uR;
        uFront=uF;
        uBack=uB;
        }*/

    public SonicAssist(AnalogChannel uF, AnalogChannel uB, Drive mDrive){
        uFront=uF;
        uBack=uB;
        mooseDrive = mDrive;
        }
    //Sense Front Method allows drive assistance when approaching objects from the front
    public void senseFront(double dis, double tol, double speed){
        //If bot is too far from object
        dis = dis * (sonicConstant);
        tol = tol * (sonicConstant);

        if(uFront.getVoltage()>(dis+tol)){
            mooseDrive.tank(speed,-speed);}   //Move towards object
        //If bot is too close to object
        else if(uFront.getVoltage()<(dis-tol)){
            mooseDrive.tank(-speed,speed);} //Move away from object
        //Bot is within object tolerance range
        else{
            mooseDrive.tank(0,0);}   //Stop
        }
    //Sense Back Method allows drive assistance when approaching objects from the back
    public void senseBack(double dis, double tol, double speed){
        //If bot is too far from object
        dis = dis * (sonicConstant);
        tol = tol * (sonicConstant);
        if(uBack.getVoltage()>(dis+tol))
            mooseDrive.tank(-speed,speed);     //Move towards object
        //If bot is too close to object
        else if(uBack.getVoltage()<(dis-tol))
            mooseDrive.tank(speed,-speed);       //Move away from object
        //Bot is within objecter tolerance range
        else
            mooseDrive.tank(0,0);               //Stop
        }
    public void test(){
        mooseDrive.tank(-1,1);
        }
    //Triangulate method allows triangulation of bot to object
   /* public void triangulate(double tol, double speed){
        
        double dif =(uLeft.getVoltage()-uRight.getVoltage());

        if(dif>tol)                     //If bot is shifted left of object
            mooseDrive.tank(speed,0);   //Shift bot right
           
        else if(dif<-tol)               //If bot is shifted right of object
            mooseDrive.tank(0,speed);   //Shift bot left
            
        else                            //Bot is straight with object
            mooseDrive.tank(0,0);       //Stop
        }  */
}
