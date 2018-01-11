import org.apache.commons.math3.filter.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import java.lang.reflect.ParameterizedType;

public class KalhmanFilterTest {



//
//    A - state transition matrix
//    B - control input matrix
//    H - measurement matrix
//    Q - process noise covariance matrix
//    R - measurement noise covariance matrix
//    P - error covariance matrix


    public static void main(String[] args) throws InterruptedException {

        // A = [ 1 ]
        RealMatrix A = MatrixUtils.createRealMatrix(1, 1);
        A.setEntry(0, 0, 1.0);
        // no control input
        RealMatrix B = null;
        // H = [ 1 ]
        RealMatrix H = new Array2DRowRealMatrix(new double[]{1d});
        // Q = [ 0 ]
        RealMatrix Q = new Array2DRowRealMatrix(new double[]{0});
        // R = [ 0 ]
        RealMatrix R = new Array2DRowRealMatrix(new double[]{0});

        System.out.println("Vectors are ready");

        KalmanForVehicle();

    }

    public static void KalmanForVehicle() throws InterruptedException {
        // discrete time interval
        double dt = 0.1d;
        // position measurement noise (meter)
        double measurementNoise = 10d;
        // acceleration noise (meter/sec^2)
        double accelNoise = 0.2d;

        // A = [ 1 dt ]
        //     [ 0  1 ]
        RealMatrix A = new Array2DRowRealMatrix(new double[][]{{1, dt}, {0, 1}});
        // B = [ dt^2/2 ]
        //     [ dt     ]
        RealMatrix B = new Array2DRowRealMatrix(new double[][]{{Math.pow(dt, 2d) / 2d}, {dt}});
        // H = [ 1 0 ]
        RealMatrix H = new Array2DRowRealMatrix(new double[][]{{1d, 0d}});
        // x = [ 0 0 ]
        RealVector x = new ArrayRealVector(new double[]{150, 20});

        RealMatrix tmp = new Array2DRowRealMatrix(new double[][]{
                {Math.pow(dt, 4d) / 4d, Math.pow(dt, 3d) / 2d},
                {Math.pow(dt, 3d) / 2d, Math.pow(dt, 2d)}});
        // Q = [ dt^4/4 dt^3/2 ]
        //     [ dt^3/2 dt^2   ]
        RealMatrix Q = tmp.scalarMultiply(Math.pow(accelNoise, 2));
        // P0 = [ 1 1 ]
        //      [ 1 1 ]
        RealMatrix P0 = new Array2DRowRealMatrix(new double[][]{{1, 1}, {1, 1}});
        // R = [ measurementNoise^2 ]
        RealMatrix R = new Array2DRowRealMatrix(new double[]{Math.pow(measurementNoise, 2)});

        // constant control input, increase velocity by 0.1 m/s per cycle
        RealVector u = new ArrayRealVector(new double[]{-4.0d});

        ProcessModel pm = new DefaultProcessModel(A, B, Q, x, P0);
        MeasurementModel mm = new DefaultMeasurementModel(H, R);
        KalmanFilter filter = new KalmanFilter(pm, mm);



        RandomGenerator rand = new JDKRandomGenerator();
        RealVector tmpPNoise = new ArrayRealVector(new double[]{Math.pow(dt, 2d) / 2d, dt});
        RealVector mNoise = new ArrayRealVector(1);

            // iterate 60 steps
        for (int i = 0; i < 60; i++) {
            filter.predict(u);

            // simulate the process
            RealVector pNoise = tmpPNoise.mapMultiply(accelNoise * rand.nextGaussian());

            // x = A * x + B * u + pNoise
            x = A.operate(x).add(B.operate(u)).add(pNoise);

            // simulate the measurement
            mNoise.setEntry(0, measurementNoise * rand.nextGaussian());

            // z = H * x + m_noise
            RealVector z = H.operate(x).add(mNoise);

            filter.correct(z);

            double position = filter.getStateEstimation()[0];
            double velocity = filter.getStateEstimation()[1];
            Thread.currentThread().sleep(100);
            System.out.print("Estimated position " + position );
            System.out.println(" and velocity " + velocity);
            System.out.println("--------");
        }


    }

    public static void generealKalman() {
        // A = [ 1 ]
        RealMatrix A = new Array2DRowRealMatrix(new double[]{1d});
        // no control input
        RealMatrix B = null;
        // H = [ 1 ]
        RealMatrix H = new Array2DRowRealMatrix(new double[]{1d});
        // Q = [ 0 ]
        RealMatrix Q = new Array2DRowRealMatrix(new double[]{0});
        // R = [ 0 ]
        RealMatrix R = new Array2DRowRealMatrix(new double[]{0});

        ProcessModel pm = new DefaultProcessModel(A, B, Q, new ArrayRealVector(new double[]{0}), null);
        MeasurementModel mm = new DefaultMeasurementModel(H, R);
        KalmanFilter filter = new KalmanFilter(pm, mm);

        for (; ; ) {
            // predict the state estimate one time-step ahead
            // optionally provide some control input
            filter.predict();

            // obtain measurement vector z
            RealVector z = getMeasurement();

            // correct the state estimate with the latest measurement
            filter.correct(z);

            double[] stateEstimate = filter.getStateEstimation();
            // do something with it
        }
    }

    private static RealVector getMeasurement() {
        return new ArrayRealVector();
    }

}
