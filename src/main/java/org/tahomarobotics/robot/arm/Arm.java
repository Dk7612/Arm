//THIS IS ASWANTH's TERRITORY  
package org.tahomarobotics.robot.arm;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Arm implements ArmIF {
    private final ArmSubsystems subsystem;

    public Arm() {
        this(new ArmSubsystems());
    }

    
    Arm(ArmSubsystems subsystem) {
        this.subsystem = subsystem;
    }
    @Override
    public Command forward() {
        return subsystem.run(() -> subsystem.setVelocity(ArmConstants.ARM_FORWARD_DPS))
        .finallyDo(subsystem::setNeutralmode)
        .withName("Roller Intake Command");

    }
        @Override
        public Trigger isDeployed() {
            return new Trigger(subsystem::isSpinning);
        }

    @Override
    public Command backward() {
        return subsystem.run(() -> subsystem.setVelocity(ArmConstants.ARM_REVERSE_DPS))
        .finallyDo(subsystem::setNeutralmode)
        .withName("Roller Eject Command");
    }

    @Override
    public Command stop() {
        return subsystem.run(subsystem::setNeutralmode)
        .withName("Roller Stop Command");
    }
}


