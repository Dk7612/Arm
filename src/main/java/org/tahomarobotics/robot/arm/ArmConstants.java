//MINE I CALL IT -DHRUV
package org.tahomarobotics.robot.arm;

import static edu.wpi.first.units.Units.DegreesPerSecond;

 
import edu.wpi.first.units.measure.AngularVelocity;


public class ArmConstants {
    public static final AngularVelocity ARM_MAX_ANGLE = DegreesPerSecond.of(180); // degrees
    public static final AngularVelocity ARM_MIN_ANGLE = DegreesPerSecond.of(0); // degrees
    public static final AngularVelocity ARM_FORWARD_DPS = DegreesPerSecond.of(30); // degrees per second
    public static final AngularVelocity ARM_REVERSE_DPS = DegreesPerSecond.of(-30); // degrees per second

}