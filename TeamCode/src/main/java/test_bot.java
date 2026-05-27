import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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
   private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void init(){

        left_motor = hardwareMap.get(DcMotor.class, "left_motor");
        right_motor = hardwareMap.get(DcMotor.class, "right_motor");
        runtime.reset();

        telemetry.addData("Status", "Initialized");
        telemetry.update();


    }
    @Override
    public void loop(){


        while (runtime.seconds()<10.0){
            left_motor.setPower(gamepad1.left_stick_y);
            right_motor.setPower(-gamepad1.right_stick_y);
            telemetry.addData("Number of Seconds in Phase 1", gamepad1.left_stick_y);
            telemetry.update();
        }


    }
}