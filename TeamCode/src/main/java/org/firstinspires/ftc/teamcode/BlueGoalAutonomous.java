package org.firstinspires.ftc.teamcode;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.Constants;

@Autonomous(name = "Blue Side Shooting")
public class BlueGoalAutonomous extends OpMode {


    private Follower follower;
    private DcMotorEx shooter;
    private DcMotorEx intake;
    private DcMotorEx storage;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;


    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;


    public void buildPaths() {
        Path1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(56.000, 8.000),
                                new Pose(79.618, 15.099)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(302))
                .addPath(
                        new BezierLine(
                                new Pose(79.618, 15.099),
                                new Pose(79.618, 15.099)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(302))
                .build();
    }


    public void autonomousPathUpdate() {

        switch (pathState) {
            case 0:
                follower.followPath(Path1);
                setPathState(-1);
                break;
            case 1:
            /* You could check for
            - Follower State: "if(!follower.isBusy()) {}"
            - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
            - Robot Position: "if(follower.getPose().getX() > 36) {}"
            */
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if (!follower.isBusy()) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(Path2);
                    setPathState(2);
                }
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if (!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Path3);
                    setPathState(3);
                }
                break;

            case 3:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if (!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Path4);
                    setPathState(-1);
                }
                break;
            case 4:
                if (!follower.isBusy()){
                    follower.followPath(Path5);
                    setPathState(-1);
                }
        }
    }

    /**
     * These change the states of the paths and actions. It will also reset the timers of the individual switches
     **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


    @Override
    public void init() {

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setDirection(DcMotorEx.Direction.REVERSE);
        shooter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reset the motor encoder so it reads 0 ticks
        intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        storage = hardwareMap.get(DcMotorEx.class, "storage");
        buildPaths();
        follower.setStartingPose(new Pose(34.000, 136.000, Math.toRadians(270.0)));
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        shooter.setVelocity(2800.0);

        setPathState(0);

    }

    @Override
    public void loop() {
        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
        if (opmodeTimer.getElapsedTimeSeconds() > 3 && opmodeTimer.getElapsedTimeSeconds() < 9){
            storage.setVelocity(1800.0);
            intake.setVelocity(2800.0);

        }
        if (opmodeTimer.getElapsedTimeSeconds() > 10){
            shooter.setPower(0.0);
            intake.setPower(0.0);
            storage.setPower(0.0);
        }

    }


}
