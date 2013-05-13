package com.minecraft.app;

/**
 * @author Lucas Stuyvesant
 * @author Andrea Sanchez
 * Contains methods to create and store information for a
 *         minecraft coordinate location
 */
public class Location {

	private String name;
	private Integer x;
	private Integer y;
	private Integer z;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @param l
	 *            the x to set
	 */
	public void setX(Integer l) {
		this.x = l;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public Integer getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(Integer z) {
		this.z = z;
	}

	/**
	 * @return string representation of Location object
	 */
	@Override
	public String toString() {
		return name + "\n - x: " + x + "\n - y: " + y + "\n - z: " + z;
	}

}
