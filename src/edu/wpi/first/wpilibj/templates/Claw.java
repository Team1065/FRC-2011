/**************************************************************/
/*******************       Claw        ************************/
/*******************  FIRST Team 1065  ************************/
/******************* Updated 2/02/2011 ************************/
/**************************************************************/

/********Package Files*****************************/
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
/**************************************************/

/****************Claw Class************************/
//Contains:
//Claw Constructor
//Sense Position Method
//Close Method
//Open Method
public class Claw {

    Solenoid s;             //Pneumatic solenoid
    DigitalInput lim1;
    DigitalInput lim2;

    //Create Claw constructor based on 2 limit switches and 1 solenoid
    public Claw(Solenoid sol, DigitalInput limit1, DigitalInput limit2){
        s=sol;
        lim1=limit1;
        lim2=limit2;
        }
    //Sense Method allows claw to open or close automatically
    public void sense(){
        if(lim1.get()== false ||lim2.get()== false)  //If either limit switch is pressed
            s.set(false);            //Close solenoid/claw
        else                        //Neither limit switch is pressed
            s.set(true);           //Open solenoid/claw
        }
    
    //Open Method allows claw to open based on command
    public void open(){
        s.set(false); }             //Open solenoid/claw

    //Close Method allows claw to close based on command
    public void close(){            //Close solenoid/claw
        s.set(true);   }

    }

