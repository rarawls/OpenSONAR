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

public class Contact extends Combatant {

	// INSTANCE VARIABLES \\

	private static OwnShip ownShip;
	private int id;

	// CONSTRUCTORS \\
	
	Contact(String name){
		this.name = name;
	}
	
	Contact(Sonarman app, String type, String name, double bearingFromOwnShip, double range,
			double course, int speed) {

		this.active = true;
		this.type = type;
		this.id = ++combatantCount;
		this.name = (name==null ? "S" + combatantCount : name );
		this.speed = speed;
		this.course = course;

		ownShip = app.ownShip;

		location = Methods.getDestinationLocation(ownShip.location,	bearingFromOwnShip, range);
	}

	// OVERRIDES \\

	@Override
	public void report() {
		System.out.println((active ? reportString() : name + "(" + type + "):" + " dropped"));
	}
	
	@Override
	public String reportString(){
		String myBearing = Methods.readableBrgOrCrs(Math.round(this.getBearing()));
		String myRange = "" + Math.round(this.getRange());
		String myCourse = Methods.readableBrgOrCrs(Math.round(this.course));
		String mySpeed = "" + this.speed;

		return name + "(" + type + "):" + " bears "
					+ myBearing + " at " + myRange + " yards " + " (crs: "
					+ myCourse + " spd: " + mySpeed + ")";
		
	}

	@Override
	public String toString() {
		return name + " " + location + " crs:" + course + " spd:" + speed;
	}

	// METHODS \\

	public double getBearing() {
		return Methods.getBearing(ownShip, this);
	}

	public double getRange() {
		return Methods.getRange(this, ownShip);
	}
	
	//GETTERS AND SETTERS\\
	public int getId(){
		return id;
	}
}

