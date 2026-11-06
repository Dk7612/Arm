package org.tahomarobotics.robot.roller;
 
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RotationsPerSecond;
 
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;
 
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
 
public class RollerConstants {
 
    public static final AngularVelocity ROLLER_INTAKE_RPS = RotationsPerSecond.of(40.0);
    public static final AngularVelocity ROLLER_EJECT_RPS = RotationsPerSecond.of(-20);
   
    public static final Current ROLLER_CURRENT_LIMIT = Amps.of(35.0);
 
    public static TalonFXConfiguration createRollerConfig() {
        TalonFXConfiguration config = new TalonFXConfiguration();
 
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
       
        config.CurrentLimits.SupplyCurrentLimit = ROLLER_CURRENT_LIMIT.in(Amps);
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        return config;
    }
}
 