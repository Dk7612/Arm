package org.tahomarobotics.robot.arm;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import org.tahomarobotics.robot.RobotMap;
import org.tahomarobotics.robot.util.AbstractSubsystem;
import org.littletonrobotics.junction.Logger;
import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public class ArmSubsystems extends AbstractSubsystem {
private final TalonFX armMotor = new TalonFX(RobotMap.ROLLER_MOTOR_ID, RobotMap.CANBUS_NAME);
private final StatusSignal<Current> armCurrent;
private final StatusSignal<AngularVelocity> armVelocity;
private final NeutralOut neutralOutRequest = new NeutralOut();
private final VelocityVoltage velocityVoltageRequest = new VelocityVoltage(0);


public ArmSubsystems() {
    armCurrent = armMotor.getSupplyCurrent();
    armVelocity = armMotor.getVelocity();

    BaseStatusSignal.setUpdateFrequencyForAll(50, armCurrent);
    armMotor.optimizeBusUtilization();
}

public void setVelocity(AngularVelocity velocity) {
    armMotor.setControl(velocityVoltageRequest.withVelocity(velocity));
}

public void setNeutralmode() {
    armMotor.setControl(neutralOutRequest);
}

public AngularVelocity getVelocity() {
    return armMotor.getVelocity().getValue();
}

public boolean isSpinning() {
    return Math.abs(armMotor.getVelocity().getValue().in(DegreesPerSecond)) > 2.0;
}

    @Override
public void subsystemPeriodic(){
    BaseStatusSignal.refreshAll(armVelocity, armCurrent);
    Logger.recordOutput("Roller/velocityRPS", armVelocity.getValue().in(RotationsPerSecond));
    Logger.recordOutput("Roller/currentAmps", armCurrent.getValue().in(Amps));
    Logger.recordOutput("Roller/isSpinning", isSpinning());

}

@Override
public Current getTotalCurrent() {
    return Amps.of(0);
}
}
