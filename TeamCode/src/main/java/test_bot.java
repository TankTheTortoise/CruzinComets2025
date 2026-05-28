import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;
@TeleOp
public class test_bot extends OpMode {
   private DcMotor left_motor;
   private DcMotor right_motor;
   private DcMotorEx claw;
   private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void init(){

        left_motor = hardwareMap.get(DcMotor.class, "left_motor");
        right_motor = hardwareMap.get(DcMotor.class, "right_motor");
        claw = hardwareMap.get(DcMotorEx.class, "claw");
        claw.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        claw.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        runtime.reset();

        telemetry.addData("Status", "Initialized");
        telemetry.update();


    }
    @Override
    public void loop(){



            left_motor.setPower(gamepad1.left_stick_y);
            right_motor.setPower(-gamepad1.right_stick_y);

            claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            claw.setTargetPosition(100*((gamepad1.dpad_up?1:0) - (gamepad1.dpad_down?1:0)));
            claw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            claw.setVelocity(100);

            telemetry.addData("Number of Seconds in Phase 1", runtime.seconds());
            telemetry.update();



    }
}