package timer;

import java.util.Random;

/**
 * Represents a timer with various random distributions.
 * This timer generates random time intervals based on different probability distributions such as exponential, Poisson, possibilist, or Gaussian.
 */
public class RandomTimer implements Timer {

    /**
     * Enumeration of supported random distributions.
     */
    public static enum randomDistribution {
        POISSON, EXP, POSIBILIST, GAUSSIAN;
    }

    private Random r = new Random(); // Random number generator
    private randomDistribution distribution; // The selected distribution
    private double rate; // The rate parameter for exponential distribution
    private double mean; // The mean parameter for Poisson distribution
    private double lolim; // The lower limit for possibilist and Gaussian distributions
    private double hilim; // The upper limit for possibilist and Gaussian distributions

    /**
     * Converts a string representation of a distribution into the corresponding enum value.
     *
     * @param distributionName The name of the distribution.
     * @return The enum value representing the distribution.
     */
    public static randomDistribution string2Distribution(String distributionName) {
        return RandomTimer.randomDistribution.valueOf(RandomTimer.randomDistribution.class, distributionName.toUpperCase());
    }

    /**
     * Converts a distribution enum value into its string representation.
     *
     * @param distribution The distribution enum value.
     * @return The string representation of the distribution.
     */
    public static String distribution2String(randomDistribution distribution) {
        return distribution.name();
    }

    /**
     * Constructs a RandomTimer with the specified distribution and parameter.
     *
     * @param distribution The random distribution.
     * @param param        The parameter value for the selected distribution.
     * @throws Exception if the constructor is called with invalid parameters for the selected distribution.
     */
    public RandomTimer(randomDistribution distribution, double param) throws Exception {
        if (distribution == randomDistribution.EXP) {
            this.distribution = distribution;
            this.rate = param;
            this.mean = 1 / param;
            this.lolim = 0;
            this.hilim = Double.POSITIVE_INFINITY;
        } else if (distribution == randomDistribution.POISSON) {
            this.distribution = distribution;
            this.rate = Double.NaN;
            this.mean = param;
            this.lolim = 0;
            this.hilim = Double.POSITIVE_INFINITY;
        } else {
            throw new Exception("Bad Timer constructor for selected distribution");
        }
    }

    /**
     * Constructs a RandomTimer with the specified distribution and range.
     *
     * @param distribution The random distribution.
     * @param lolim        The lower limit of the range.
     * @param hilim        The upper limit of the range.
     * @throws Exception if the constructor is called with invalid parameters for the selected distribution.
     */
    public RandomTimer(randomDistribution distribution, int lolim, int hilim) throws Exception {
        if (distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN) {
            this.distribution = distribution;
            this.mean = lolim + (hilim - lolim) / 2;
            this.rate = Double.NaN;
            this.lolim = lolim;
            this.hilim = hilim;
        } else {
            throw new Exception("Bad Timer constructor for selected distribution");
        }
    }

    /**
     * Retrieves the name of the distribution.
     *
     * @return The name of the distribution.
     */
    public String getDistribution() {
        return this.distribution.name();
    }

    /**
     * Retrieves the parameter of the distribution as a string representation.
     *
     * @return The parameter of the distribution.
     */
    public String getDistributionParam() {
        if (distribution == randomDistribution.EXP) {
            return "rate: " + this.rate;
        } else if (distribution == randomDistribution.POISSON) {
            return "mean: " + this.mean;
        } else if (distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN) {
            return "lolim: " + this.lolim + " hilim: " + this.hilim;
        }
        return "null";
    }

    /**
     * Retrieves the mean of the distribution.
     *
     * @return The mean of the distribution.
     */
    public double getMean() {
        return this.mean;
    }

    /**
     * Retrieves a string representation of the RandomTimer.
     *
     * @return A string representation of the RandomTimer.
     */
    @Override
    public String toString() {
        String s = this.getDistribution();
        switch (this.distribution) {
            case POSIBILIST:
                s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
                break;
            case POISSON:
                s += " mean:" + this.mean;
                break;
            case EXP:
                s += " rate:" + this.rate;
                break;
            case GAUSSIAN:
                s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
                break;
        }

        return s;
    }

    /**
     * Generates the next random value based on the selected distribution.
     *
     * @return The next random value.
     */
    @Override
    public Integer next() {
        switch (this.distribution) {
            case POSIBILIST:
                return this.nextTimePosibilist();
            case POISSON:
                return this.nextTimePoisson();
            case EXP:
                return this.nextTimeExp();
            case GAUSSIAN:
                return this.nextTimeGaussian();
        }
        return -1; // Theoretically impossible !!!
    }

    /**
     * Generates the next random time interval for the possibilist distribution.
     *
     * @return The next random time interval.
     */
    private int nextTimePosibilist() {
        return (int) this.lolim + (int) (this.r.nextDouble() * (this.hilim - this.lolim));
    }

    /**
     * Generates the next random time interval for the exponential distribution.
     *
     * @return The next random time interval.
     */
    private int nextTimeExp() {
        return (int) (-Math.log(1.0 - this.r.nextDouble()) / this.rate);
    }

    /**
     * Generates the next random time interval for the Poisson distribution.
     *
     * @return The next random time interval.
     */
    private int nextTimePoisson() {
        double L = Math.exp(-this.mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * this.r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    /**
     * Generates the next random time interval for the Gaussian distribution.
     *
     * @return The next random time interval.
     */
    private int nextTimeGaussian() {
        return (int) this.lolim + (int) ((this.r.nextGaussian() + 1.0) / 2.0 * (this.hilim - this.lolim));
    }

    /**
     * Checks if the RandomTimer has a next value.
     *
     * @return Always returns true, as the RandomTimer is designed to always have a next value.
     */
    @Override
    public boolean hasNext() {
        return true;
    }
}
