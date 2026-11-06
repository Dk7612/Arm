package org.tahomarobotics.robot.roller;

import static edu.wpi.first.units.Units.Amps;
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

public class RollerSubsystem extends AbstractSubsystem {
private final TalonFX rollerMotor = new TalonFX(RobotMap.ROLLER_MOTOR_ID, RobotMap.CANBUS_NAME);
private final StatusSignal<Current> rollerCurrent;
private final StatusSignal<AngularVelocity> rollerVelocity;
private final NeutralOut neutralOutRequest = new NeutralOut();
private final VelocityVoltage velocityVoltageRequest = new VelocityVoltage(0);


public RollerSubsystem() {
    rollerCurrent = rollerMotor.getSupplyCurrent();
    rollerVelocity = rollerMotor.getVelocity();

    BaseStatusSignal.setUpdateFrequencyForAll(50, rollerCurrent);
    rollerMotor.optimizeBusUtilization();
}

public void setVelocity(AngularVelocity velocity) {
    rollerMotor.setControl(velocityVoltageRequest.withVelocity(velocity));
}

public void setNeutralmode() {
    rollerMotor.setControl(neutralOutRequest);
}

public AngularVelocity getVelocity() {
    return rollerMotor.getVelocity().getValue();
}

public boolean isSpinning() {
    return Math.abs(rollerVelocity.getValue().in(RotationsPerSecond)) > 2.0;
}

    @Override
public void subsystemPeriodic(){
    BaseStatusSignal.refreshAll(rollerVelocity, rollerCurrent);
    Logger.recordOutput("Roller/velocityRPS", rollerVelocity.getValue().in(RotationsPerSecond));
    Logger.recordOutput("Roller/currentAmps", rollerCurrent.getValue().in(Amps));
    Logger.recordOutput("Roller/isSpinning", isSpinning());

}

@Override
public Current getTotalCurrent() {
    return Amps.of(0);
}
}
