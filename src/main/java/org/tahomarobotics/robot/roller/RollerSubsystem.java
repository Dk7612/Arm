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
package org.tahomarobotics.robot.roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import org.littletonrobotics.junction.Logger;
import org.tahomarobotics.robot.RobotMap;
import org.tahomarobotics.robot.util.AbstractSubsystem;

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
  public void subsystemPeriodic() {
    BaseStatusSignal.refreshAll(rollerVelocity, rollerCurrent);
    Logger.recordOutput("Roller/velocityRPS", rollerVelocity.getValue().in(RotationsPerSecond));
    Logger.recordOutput("Roller/currentAmps", rollerCurrent.getValue().in(Amps));
    Logger.recordOutput("Roller/isSpinning", isSpinning());
  }

  @Override
  public Current getTotalCurrent() {
    return rollerCurrent.getValue();
  }
}
