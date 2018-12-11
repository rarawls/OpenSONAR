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

public class Location {

	// INSTANCE VARIABLES\\
	private double x;
	private double y;

	// CONSTRUCTOR\\
	Location(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// GETTERS & SETTERS\\

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		return "[" + Math.round(x) + "," + Math.round(y) + "]";
	}

}
