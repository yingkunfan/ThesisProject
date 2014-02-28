package sriracha.simulator.model.elements.sources.transient_functions;

/**
 * Created by yiqing on 27/02/14.
 */
public class SinTransFun {
    /**
     * Offset voltage or current in V oe A, by default 0.
     */
    private double vo = 0;

    /**
     * RMS amplitude in V or A, by default 1.
     */
    private double va = 1;

    /**
     * Frequency in Hz, by default 1 kHz
     */
    private double freq = 1e3;

    /**
     * Time delay before beginning the sinusoidal variation in seconds, by default 0.
     * NOTE that total value is 0V or 0A until delay is finished.
     */
    private double td = 0;

    /**
     * Damping factor in units of 1/seconds, by default 0
     */
    private double theta = 0;

    /**
     * Phase delay in units of degrees, by default 0.  This value is evaluated independently of
     * the AC phasor when transient analysis is performed.
     */
    private double phi = 0;

    public SinTransFun(String[]params){

    }
}
