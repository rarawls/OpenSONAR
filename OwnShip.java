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

public class OwnShip extends Combatant{

	OwnShip(Sonarman app){
		
		this.active = true;
		this.type = "sub";
		this.name = "OwnShip";
		this.speed = 0;
		this.course = 0;
		
		this.location = new Location(0,0);
	}

	@Override
	public void report() {
		System.out.println(reportString());
	}
	
	@Override
	public String reportString(){
		return "OwnShip crs: " + Methods.readableBrgOrCrs(course) + " spd: " + speed;
	}

	@Override
	public String toString() {
		return "OwnShip: " + location + " crs:" + Methods.readableBrgOrCrs(course) + " spd:" + speed;
	}
	
}
