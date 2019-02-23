package techguns.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import techguns.client.ClientProxy;

public class MathUtil {
	
	public static final double D2R = Math.PI/180.0;
	public static final double R2D = 180.0/Math.PI;

	/**
	 * sin with argument in degrees (0-360)
	 * @param arg
	 * @return
	 */
    public static float sin360(float arg){
    	return MathHelper.sin(arg / 180.0F * (float)Math.PI);
    }
    /**
	 * cos with argument in degrees (0-360)
	 * @param arg
	 * @return
	 */
    public static float cos360(float arg){
    	return MathHelper.cos(arg / 180.0F * (float)Math.PI);
    }
	
	public static Vec2f polarOffsetXZ(float x, float z, float radius, float angle) {
    	x = (float) (x + (radius*MathHelper.cos(angle)));
    	z = (float) (z + (radius*MathHelper.sin(angle)));

    	return new Vec2f(x, z);
    }
	
	public static float clamp(float value, float min, float max) {
		if (value>max) {
			return max;
		} else if (value<min) {
			return min;
		} else {
			return value;
		}
	}
	
	public static double clamp(double value, double min, double max) {
		if (value>max) {
			return max;
		} else if (value<min) {
			return min;
		} else {
			return value;
		}
	}
	
	public static int clamp(int value, int min, int max) {
		if (value>max) {
			return max;
		} else if (value<min) {
			return min;
		} else {
			return value;
		}
	}
	
	public static int randomInt(Random rand, int min, int max) {
    	if (max >= min)
    		return min+rand.nextInt((max-min)+1);
    	else
    		return max+rand.nextInt((min-max)+1);
    }
    
    public static float randomFloat(Random rand, float min, float max) {
    	return min+(rand.nextFloat()*(max-min));
    }
    
    public static Vec3d getInterpolatedEntityPos(Entity entityIn) {
    	double partialTicks = (double)ClientProxy.get().PARTIAL_TICK_TIME;
        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
        return new Vec3d(d0,d1,d2);
    }
    
    /**
     * X and Y methods are Vec3d.rotatePitch and Vec3d.rotateYaw
     * @return
     */
    public static Vec3d rotateVec3dAroundZ(Vec3d vec, float angle) {
        float f1 = MathHelper.cos(angle);
        float f2 = MathHelper.sin(angle);
        double d0 = vec.x * (double)f1 + vec.y * (double)f2;
        double d1 = vec.y * (double)f1 - vec.x * (double)f2;
        double d2 = vec.z;
        return new Vec3d(d0,d1,d2);
    }
    
    /**
     * Return if all passed integers are withing bounds
     * @param lowerBound
     * @param upperBound
     * @param values
     * @return
     */
    public static boolean allInRange(int lowerBound, int upperBound, Integer... values) {
    	for(int i=0;i<values.length;i++) {
    		if(values[i]<lowerBound || values[i]> upperBound) {
    			return false;
    		}
    	}
    	
		return true;
    }
    
    public static int min(Integer... values) {
    	int min=Integer.MAX_VALUE;
    	for(int i=0;i<values.length;i++) {
    		if(values[i]<min) {
    			min=values[i];
    		}
    	}
    	
		return min;
    }
    
    public static int max(Integer... values) {
    	int max=Integer.MIN_VALUE;
    	for(int i=0;i<values.length;i++) {
    		if(values[i]>max) {
    			max=values[i];
    		}
    	}
    	
		return max;
    }
    
    /**
     * get average from multiple ints, rounded to next int.
     * @param values
     * @return
     */
    public static int getAverageHeight(Integer... values) {
    	int sum=0;
    	for(int i=0;i<values.length;i++) {
    		sum+=values[i];
    	}
		return (int) Math.round(sum/((double)values.length));
    }
    
    
    /**
     * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
     * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
     * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
     */
    public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks)
    {
        float f;

        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }
    
    public static Vec2 polarOffsetXZ(double x, double z, double radius, double angle) {
    	x = x + (radius*Math.cos(angle));
    	z = z + (radius*Math.sin(angle));
    	
    	return new Vec2(x, z);
    }
    
    public static class Vec2{
    	public double x;
    	public double y;
    	
    	public Vec2(double x, double y){
    		this.x=x;
    		this.y=y;
    	}
    	
    	/**
    	 * returns the squared vector length;
    	 * @return
    	 */
    	public double lenSquared(){
    		return x*x + y*y;
    	}
    	
    	public double len(){
    		return Math.sqrt(this.lenSquared());
    	}
    	
    	/**
    	 * normalizes THIS vector
    	 */
    	public void normalize(){
    		double len = this.len();
    		if(len >0){
	    		double f = 1/len;
				x=x*f;
				y=y*f;
    		}
    	}
    	
    	/**
    	 * Gets a new vec2 that is a normalized version of THIS
    	 * @return
    	 */
    	public Vec2 getNormalized(){
    		double len = this.len();
    		if(len >0){
	    		double f = 1/len;
				return new Vec2(this.x*f, this.y*f);
    		}
    		return new Vec2(0,0);
    	}
    	
    	/**
    	 * Returns Vector A - Vector B
    	 * @param A
    	 * @param B
    	 * @return
    	 */
    	public static Vec2 substract(Vec2 A, Vec2 B){
    		return new Vec2(B.x-A.x, B.y-A.y);
    	}
    	
    	/**
    	 * Returns Vector A + Vector B
    	 * @param A
    	 * @param B
    	 * @return
    	 */
    	public static Vec2 add(Vec2 A, Vec2 B){
    		return new Vec2(B.x+A.x, B.y+A.y);
    	}
    	
    	/**
    	 * get new vector from THIS position to endpoint position
    	 * @param endpoint
    	 * @return
    	 */
    	public Vec2 getVecTo(Vec2 endpoint){
    		return substract(endpoint, this);
    	}
    	
    	/**
    	 * get new vector from startpoint position to THIS position
    	 * @param endpoint
    	 * @return
    	 */
    	public Vec2 getVecFrom(Vec2 startpoint){
    		return substract(this,startpoint);
    	}
    	
    	/**
    	 * return dot product with other vector: this dot other
    	 * @param other
    	 * @return
    	 */
    	public double dot(Vec2 other){
    		return (this.x*other.x+this.y*other.y);
    	}
    	
		/**
		 * get a new vec2 rotated by degree
		 */
    	public Vec2 getRotated(float degree){
    		double ynew = y*cos360(degree) - x*sin360(degree);
    		double xnew = y*sin360(degree) + x*cos360(degree);
    		return new Vec2(xnew,ynew);
    	}
    }
    
    public static Vec3d rotateVector(Vec3d vec, Vec3d axis, double theta) {
    	double u = axis.x;
    	double v = axis.y;
    	double w = axis.z;
    	
    	double xPrime = u*(u*vec.x + v*vec.y + w*vec.z)*(1d - Math.cos(theta)) 
                + vec.x*Math.cos(theta)
                + (-w*vec.y + v*vec.z)*Math.sin(theta);
		double yPrime = v*(u*vec.x + v*vec.y + w*vec.z)*(1d - Math.cos(theta))
		                + vec.y*Math.cos(theta)
		                + (w*vec.x - u*vec.z)*Math.sin(theta);
		double zPrime = w*(u*vec.x + v*vec.y + w*vec.z)*(1d - Math.cos(theta))
		                + vec.z*Math.cos(theta)
		                + (-v*vec.x + u*vec.y)*Math.sin(theta);
		return new Vec3d(xPrime, yPrime, zPrime);
    }
	public static boolean inRange(int val, int min, int max) {
		return val>=min && val <=max;
	}
	
    /**
     * Absolute value of integer
     * @param i
     */
    public static int abs(int i){
    	if(i<0){
    		return -i;
    	}
    	return i;
    }
}
