package core;

public class Time {
	
	private static final long SECOND = 1000000000L;	// in nanoseconds
	private static double delta;

	/**
	 * 
	 * @return time in seconds
	 */
	public static double getTime() 
	{
		return (double)System.nanoTime()/(double)SECOND;
	}
	
	public static double getDelta()	// time between frames
	{
		return delta;
	}
	
	public static void setDelta(double delta) 
	{
		Time.delta = delta;
	}
}

