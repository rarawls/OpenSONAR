/*
 * This file is part of OpenSONAR: A Simple Submarine Combat Simulator
 * Copyright (C) 2007  Robert Alexander Rawls
 * Contact e-mail: rawls008@gmail.com
 *
 * OpenSONAR is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenSONAR is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenSONAR.  If not, see <http://www.gnu.org/licenses/>.
 */

public abstract class Combatant implements Comparable<Object>{

	// STATIC VARIABLES \\
	
	protected static int combatantCount;
	
	//INSTANCE VARIABLES \\
	protected Location location;
	protected String name;
	protected String type;
	protected double course;
	protected int speed;
	protected boolean active;
	
	//ABSTRACT METHODS\\
	
	public abstract String toString();
	public abstract void report();
	public abstract String reportString();
	
	//METHODS\\
	
	public int compareTo(Object o2) {
		Contact other = (Contact)o2;
		String name = other.getName();
		
		return name.compareToIgnoreCase(other.getName());
	}
	
	public boolean equals(Object other){
		return this.name.equalsIgnoreCase(((Combatant)other).getName());
	}
	
	public void step(int minutes) {
		if (speed == 0)
			return;
	
		location = Methods.getDestinationLocation(this.location, this.course, this.speed, minutes);
	}
	
	// STATIC METHODS \\
	
	public static void resetCombatantCount(){
		combatantCount = 0;
	}
	
	// GETTERS\\

	public boolean isActive() {
		return active;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public double getCourse() {
		return course;
	}

	public int getSpeed() {
		return speed;
	}
	
	public Location getLocation() {
		return location;
	}

	// SETTERS\\

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setCourse(double course) {
		this.course = course;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
}

