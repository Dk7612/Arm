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

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Roller implements RollerIF {
  private final RollerSubsystem subsystem;

  public Roller() {
    this(new RollerSubsystem());
  }

  Roller(RollerSubsystem subsystem) {
    this.subsystem = subsystem;
  }

  @Override
  public Command intake() {
    return subsystem
        .run(() -> subsystem.setVelocity(RollerConstants.ROLLER_INTAKE_RPS))
        .finallyDo(subsystem::setNeutralmode)
        .withName("Roller Intake Command");
  }

  @Override
  public Trigger isSpinning() {
    return new Trigger(subsystem::isSpinning);
  }

  @Override
  public Command eject() {
    return subsystem
        .run(() -> subsystem.setVelocity(RollerConstants.ROLLER_EJECT_RPS))
        .finallyDo(subsystem::setNeutralmode)
        .withName("Roller Eject Command");
  }

  @Override
  public Command stop() {
    return subsystem.run(subsystem::setNeutralmode).withName("Roller Stop Command");
  }
}
