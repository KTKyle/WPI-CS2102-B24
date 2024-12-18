package HW1;

/**
 * Represents a copter which includes its battery, propellers, and destination distance
 */
public class Copter {

    /** The name of the copter. */
    public String name;

    /** The propeller used by the copter. */
    Propellers propeller;

    /** The battery used by the copter. */
    Battery battery;

    /** Distance in meters to the copter's destination. */
    public double metersToDestination;


    /**
     * Constructs a Copter with specified propellers, battery, and destination distance
     * @param name the name of the copter
     * @param propeller the propellers of the copter
     * @param battery the battery of the copter
     * @param metersToDestination the initial distance to the destination in meters
     */
    public Copter(String name, Propellers propeller, Battery battery, double metersToDestination){
        this.name = name;
        this.propeller = propeller;
        this.battery = battery;
        this.metersToDestination = metersToDestination;
    }

    /**
     * Calculates the maximum distance the copter can travel with a full battery
     * @return the potential travel distance in meters
     */
    public double potentialDistance(){
        return battery.capacity / propeller.totalCurrentDraw() * propeller.speed;
    }

    /**
     * Calculates the ratio of remaining battery over distance until reaching the destination
     * @return a value between 0.0 and 1.0, where 1.0 means enough charge to reach the destination
     */
    public double rechargeIndex (){
        return (this.propeller.totalCurrentDraw() * this.battery.amountLeft)/this.metersToDestination;
    }

    public double progressUntilRecharge(){
        if(rechargeIndex() <= 1.0){
            return rechargeIndex();
        }
        else return 1.0;
    }

    /**
     * Checks if the copter can reach its destination without needing a recharge
     * using the progressUntilRecharge function
     * @return true if the copter can reach its destination, false otherwise
     */
    public boolean canReachDest(){
        return progressUntilRecharge() == 1.0;
    }

    /**
     * Compares two copters to see which one can travel further distances assuming a full battery
     * returning the name of the one that travels further or both names conjoined by a "&"
     * if they can travel the same distance
     *
     * @param anotherCopter the copter to compare against
     * @return the name of the copter that can travel further, or both names if equal
     */
    public String whoGoesFurther(Copter anotherCopter){
        double currentCopterDist = this.potentialDistance();
        double anotherCopterDist = anotherCopter.potentialDistance();

        if(currentCopterDist < anotherCopterDist){
            return anotherCopter.name;
        }
        else if (currentCopterDist > anotherCopterDist){
            return this.name;
        }
        else return this.name + "&" + anotherCopter.name;
    }


    /**
     * Simulates the copter traveling for some time, adjusting the remaining battery and distance
     * proportionate to the amount of time spent flying
     * If the battery or distance would go negative, they are set to 0.0.
     *
     * @param seconds the time in seconds the copter travels
     */
    public void travelFor(double seconds){
        double distanceTravelled = propeller.distanceTravelled(seconds);
        double batteryUsed =  propeller.totalCurrentDraw() * seconds;

        this.metersToDestination -= distanceTravelled;
        this.battery.amountLeft -= batteryUsed;

        if(battery.amountLeft < 0.0){
            battery.amountLeft = 0;
        }
        if(metersToDestination < 0.0){
            metersToDestination = 0.0;
        }
    }
}
