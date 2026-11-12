/*
* MIT License
*
* Copyright (c) 2026 Bear Metal
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package org.tahomarobotics.robot.arm;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import org.littletonrobotics.junction.Logger;
import org.tahomarobotics.robot.RobotMap;
import org.tahomarobotics.robot.util.AbstractSubsystem;

public class ArmSubsystem extends AbstractSubsystem {
  private final TalonFX armMotor = new TalonFX(RobotMap.ARM_MOTOR_ID);

  private final StatusSignal<Current> armCurrent;
  private final StatusSignal<AngularVelocity> armVelocity;
  private final StatusSignal<Angle> armAngle;

  private final NeutralOut neutralOutRequest = new NeutralOut();
  private final VelocityVoltage velocityVoltageRequest = new VelocityVoltage(0);

  public ArmSubsystem() {
    armCurrent = armMotor.getSupplyCurrent();
    armVelocity = armMotor.getVelocity();
    armAngle = armMotor.getPosition();

    BaseStatusSignal.setUpdateFrequencyForAll(50, armCurrent, armVelocity, armAngle);
    armMotor.optimizeBusUtilization();
  }

  public void setVelocity(AngularVelocity velocity) {
    armMotor.setControl(velocityVoltageRequest.withVelocity(velocity));
  }

  public void setAngle(Angle)

  public void setNeutralMode() {
    armMotor.setControl(neutralOutRequest);
  }

  public AngularVelocity getVelocity() {
    return armMotor.getVelocity().getValue();
  }

  public boolean isSpinning() {
    return Math.abs(armVelocity.getValue().in(DegreesPerSecond)) > 2.0;
  }

  @Override
  public void subsystemPeriodic() {
    BaseStatusSignal.refreshAll(armVelocity, armCurrent, armAngle);
    Logger.recordOutput("Arm/velocityRPS", armVelocity.getValue().in(RotationsPerSecond));
    Logger.recordOutput("Arm/positionRotations", armAngle.getValue().in(Rotations));
    Logger.recordOutput("Arm/currentAmps", armCurrent.getValue().in(Amps));
    Logger.recordOutput("Arm/isSpinning", isSpinning());
  }

  @Override
  public Current getTotalCurrent() {
    return armCurrent.getValue();
  }
}
