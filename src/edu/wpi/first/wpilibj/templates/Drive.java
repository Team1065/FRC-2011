/*************************************************************/
/*****************   Drive System     ************************/
/*****************  FIRST Team 1065   ************************/
/***************** Updated 2/02/2011  ************************/
/*************************************************************/

/**************Package Files******************************/
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Jaguar;
/*********************************************************/


/*******************Drive Class**************************/
// Contains:
// Drive Constructor
// Tank Drive Method
// Mecanum Drive Method

public class Drive {

    //Create motor objects based on the following:
    //     Front
    // (1)-------(2)
    //  |         |
    //  |         |
    // (3)-------(4)

    Jaguar frontLeft;
    Jaguar frontRight;
    Jaguar backLeft;
    Jaguar backRight;

    //Create Drive Constructor based on 4 drive motors
    public Drive(Jaguar fL, Jaguar fR, Jaguar bL, Jaguar bR){
        //Set Jaguars to motor values passed into Drive Constructor
        frontLeft=fL;
        frontRight=fR;
        backLeft=bL;
        backRight=bR;
        }

    //Tank Drive Method allows normal tank drive movement
    public void tank(double leftMotors , double rightMotors){
        //Left Motors run at left input speed
        //Right Motors run at right input speed
        frontLeft.set(leftMotors);
        backLeft.set(leftMotors);
        frontRight.set(rightMotors);
        backRight.set(rightMotors);
        }

    //Mecanum Drive Method allows bot to slide along predefined axis paths
    public void mecanum(double xAxis, double yAxis){
        //If input corresponds to robot sliding along y=x line
        if( (yAxis>.2 && xAxis>.2) || (yAxis<-.2 && xAxis <-.2)){
            frontLeft.set(yAxis);
            frontRight.set(0);
            backLeft.set(0);
            backRight.set(-yAxis);
            }
        //If input corresponds to robot sliding along y=-x line
        else if((yAxis>.2 && xAxis<-.2) || (yAxis<-.2 && xAxis >.2)){
            frontLeft.set(0);
            frontRight.set(-yAxis);
            backLeft.set(yAxis);
            backRight.set(0);
            }
        //If input corresponds to robot sliding along x axis
        else if((xAxis<-.2 ||xAxis>.2) && (yAxis< .2 && yAxis> -.2)){
            frontLeft.set(xAxis);
            frontRight.set(xAxis);
            backLeft.set(-xAxis);
            backRight.set(-xAxis);
            }
        //Stop bot for all other input values
        else{
            frontLeft.set(0);
            frontRight.set(0);
            backLeft.set(0);
            backRight.set(0);
            }
        }
}
