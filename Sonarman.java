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

import java.util.*;

public class Sonarman{

	// INSTANCE VARIABLES\\

	protected final int DEFAULT_STEP_INTERVAL = 3;

	public OwnShip ownShip;

	protected ArrayList<Contact> contacts = new ArrayList<Contact>(10);;
	protected int time = 0;
	protected int step = 0;

	protected String helpStr = "|=================== > HELP < ======================\n" 
						   	 + "| Valid Commands:\n"
						   	 + "|\n"
						   	 + "| help            : brings up this dialog\n"
						   	 + "| exit,quit       : exits program \n"
						   	 + "| reset           : resets simulation \n"
						   	 + "| new contact     : promts for info, and creates new contact\n"
						   	 + "| report contacts : prints brg, rng, crs, and spd for all contacts\n"
						   	 + "| step #          : steps simulator (leaving blank steps 3 minutes)\n"
						   	 + "| own ship        : prompts for and applies crs and spd changes\n"
						   	 + "|                   * leave blank to maintain current value\n"
						   	 + "| s# [drop][pick-up][splash][report][conn]\n" 
						   	 + "|      drop   : will remove contact from reports\n"
						   	 + "|      pick-up: will re-enable contact in reports\n"
						   	 + "|      splash : will permanently delete contact\n"
						   	 + "|      report : has contact report brg and rng\n" 
						   	 + "|      conn   : prompts for new crs and spd\n"
						   	 + "|               * leave blank to maintain current values\n"
						   	 + "|\n"
						   	 + "| Program written by Robert A. Rawls\n"
						   	 + "| MIDN 2/c at NROTC unit at UVA, Class '09\n"
						   	 + "| Send bug reports and suggestions to rawls008@gmail.com\n"
						   	 + "| See " + OpenSONAR.SITE_URL + " for updates\n"
						   	 + "|=================== > HELP < ======================\n";

	// CONSTRUCTOR\\

	Sonarman(boolean DUBUG) {		
		runSimulation();
		//Methods.runTests();		
	}
	
	// ABSTRACT METHODS \\
	
	public void stepSimulator() {
		stepSimulator(DEFAULT_STEP_INTERVAL);
	}
	
	public void stepSimulator(int minutes) {
		time += minutes;
		System.out.println("\n==== STEP " + ++step + " [Time " + time + "] =======");

		ownShip.step(minutes);

		Iterator<Contact> iter = contacts.iterator();
		while (iter.hasNext()) {
			Contact contact = iter.next();
			contact.step(minutes);
		}

		reportContacts();
		
		//Methods.centerWindow(ownShip, contacts); //TODO
		
		System.out.println("============================\n");
	}
	
	protected void dropContact(Contact contact) {
		contact.setActive(false);
	}

	protected void pickUpContact(Contact contact) {
		contact.setActive(true);
	}

	protected boolean splashContact(Contact contact) {
		return contacts.remove(contact);
	}
	
	protected void resetSimulation(){
		time = 0;
		step = 0;
		Combatant.resetCombatantCount();
		contacts.clear();
		ownShip.setCourse(0);
		ownShip.setSpeed(0);
		ownShip.setLocation(new Location(0,0));
	}
	
	public void runSimulation() {
		System.out.println("Seaman 2nd Class E.T. 'Sonar' Lovacelli Reporting in, Sir");
		System.out.println("If you're lost, type 'help'\n");

		// initialize own ship crs spd

		ownShip = new OwnShip(this);

		Scanner scanner = new Scanner(System.in);
		while (true) {
			// gets input from user
			System.out.print("SONAR :> ");
			String line = scanner.nextLine().toLowerCase().trim();

			if (line.startsWith("step")) { 									// CASE STEP!!!
				line = line.replace("step", "").trim();
				if (line.isEmpty()) {
					stepSimulator();
				} else {
					if (!Methods.isInteger(line)) {
						Methods.errorMessage("Incorrect step format - Integers only");
					} else {
						int mins = Integer.valueOf(line);
						if(mins > 0)
							stepSimulator(Integer.valueOf(line));
						else
							Methods.errorMessage("Must step at least 1 minute");
					}
				}
			} else if (line.equals("quit") || line.equals("exit")) { 		// CASE QUIT!!!
				System.out.println("\n\nExiting OpenSONAR ...");
				System.exit(0);
			} else if (line.equals("reset")) {						 		// CASE RESET!!!
				System.out.println("^^^ Simulation reset! ^^^");
				resetSimulation();
			} else if (line.equals("help")) {								// CASE HELP!!!
				System.out.println(helpStr);
			} else if (line.equals("report contacts")) {
				reportContacts();
			} else if ((line.startsWith("s") || line.startsWith("S")) 
						//&& Methods.isInteger("" + line.charAt(1)) && line.charAt(2) == ' ') {
						&& Methods.isInteger("" + line.substring(1, line.indexOf(" ")))){
																			// CASE SX!!!
				
				String contactName = line.substring(0, line.indexOf(" "));
				int contactIndex = contacts.indexOf(new Contact(contactName));
				if (contactIndex < 0) {
					Methods.errorMessage("Contact '" + contactName + "' does not exist!");
				} else {

					Contact contact = contacts.get(contactIndex);

					line = line.replace(contactName, "").trim();

					if (line.startsWith("drop")) {
						dropContact(contact);
						System.out.println("Contact " + contact.getName() + " dropped");
					} else if (line.startsWith("pick-up") || line.startsWith("pickup")) {
						pickUpContact(contact);
						System.out.println("Contact " + contact.getName() + " picked up");
					} else if (line.startsWith("splash")) {
						splashContact(contact);
						System.out.println("Contact " + contact.getName() + " splashed");
					} else if (line.startsWith("report")) {
						contact.report();
					} else if (line.startsWith("conn")) {

						ArrayList<String> commandParameters = new ArrayList<String>(5);
						commandParameters.add("New Course");
						commandParameters.add("New Speed");

						System.out.println("Enter new course and/or speed: (leave blank to leave unchanged)");
						Object[] inputs = Methods.parseCommandParameters(commandParameters);

						// VALIDATION AND INITIALIZATION

						String crs = Methods.stripLeadingZeros((String) inputs[0]);

						// validates COURSE and continues
						if (courseValidates(crs)) {
							double course = 0;// this crap is just exception
												// handling
							if (crs.length() > 0)
								course = Double.valueOf(crs);

							String spd = (String) inputs[1];

							// validates SPEED and continues
							if (speedValidates(spd)) {
								int speed = 0; // this crap is just exception
												// handling
								if (spd.length() > 0)
									speed = Integer.valueOf(spd);

								// MAKE APPROPRIATE CHANGES
								if (crs.length() < 1 && spd.length() < 1) {
									System.out.println("No changes to course or speed have been made");
								} else if (crs.length() < 1) {
									System.out.println("Contact " + contact.getName() + " spd " + contact.getSpeed() + " -> "
											+ spd);
									contact.setSpeed(speed);
								} else if (spd.length() < 1) {
									System.out.println("Contact " + contact.getName() + " crs "
											+ Methods.readableBrgOrCrs(contact.getCourse()) + " -> "
											+ Methods.readableBrgOrCrs(crs));
									contact.setCourse(course);
								} else {
									System.out.println("Contact " + contact.getName() + " crs "
											+ Methods.readableBrgOrCrs(contact.getCourse()) + " -> "
											+ Methods.readableBrgOrCrs(crs) + ", spd " + contact.getSpeed() + " -> "
											+ spd);
									contact.setSpeed(speed);
									contact.setCourse(course);
								}
							}
						}

					} else {
						Methods.errorMessage("Invalid contact command");
					}
				}
			} else if (line.startsWith("ownship") || line.startsWith("own ship")) { // CASE OWNSHIP!!!

				line = Methods.replaceAllArgsWith(line, new String[] { "ownship", "own ship" }, "").trim()
						.toLowerCase();

				ArrayList<String> commandParameters = new ArrayList<String>(5);
				commandParameters.add("New Course");
				commandParameters.add("New Speed");

				System.out.println("Enter new course and/or speed: (leave blank to leave unchanged)");
				Object[] inputs = Methods.parseCommandParameters(commandParameters);

				// VALIDATION AND INITIALIZATION

				String crs = Methods.stripLeadingZeros((String) inputs[0]);

				// validates COURSE and continues
				if (courseValidates(crs)) {
					double course = 0;// this crap is just exception handling
					if (crs.length() > 0)
						course = Double.valueOf(crs);

					String spd = (String) inputs[1];

					// validates SPEED and continues
					if (speedValidates(spd)) {
						int speed = 0; // this crap is just exception handling
						if (spd.length() > 0)
							speed = Integer.valueOf(spd);

						// MAKE APPROPRIATE CHANGES
						if (crs.length() < 1 && spd.length() < 1) {
							System.out.println("No changes to course or speed have been made");
						} else if (crs.length() < 1) {
							System.out.println("Own Ship speed " + ownShip.getSpeed() + " -> " + spd);
							ownShip.setSpeed(speed);
						} else if (spd.length() < 1) {
							System.out.println("Own Ship course " + ownShip.course + " -> "
									+ Methods.readableBrgOrCrs(crs));
							ownShip.setCourse(course);
						} else {
							System.out.println("Own Ship course " + ownShip.course + " -> "
									+ Methods.readableBrgOrCrs(crs) + ", speed " + ownShip.getSpeed() + " -> " + spd);
							ownShip.setSpeed(speed);
							ownShip.setCourse(course);
						}
					}
				}

			} else if (line.startsWith("new contact")) { 						// CASE NEW CONTACT!!!

				ArrayList<String> commandParameters = new ArrayList<String>(5);
				commandParameters.add("Type (sub, surf)");
				commandParameters.add("Name (ex. S14)");
				commandParameters.add("Bearing (ex. 040)");
				commandParameters.add("Range (ex. 4.5k, 2nm, 700y)");
				commandParameters.add("Course (ex. 340)");
				commandParameters.add("Speed (ex. 18)");

				System.out.println("Please enter information about new contact: ");
				Object[] inputs = Methods.parseCommandParameters(commandParameters);

				// VALIDATION AND INITIALIZATION

				String typ = ((String) inputs[0]).toLowerCase().trim();
				
				if (!(typ.equals("sub") || typ.equals("surf"))) {
					Methods.errorMessage("Valid types are: 'sub' and 'surf'");
				} else {
					String type = typ; //LABELS STRING TYPE
					
					String brg = Methods.stripLeadingZeros((String) inputs[2]);

					// validates BEARING and continues
					if (bearingValidates(brg) && brg.length() > 0) {
						double bearing = Double.valueOf(brg); //LABELS DOUBLE BEARING
						bearing = (bearing > 360 ? bearing - 360 : bearing);
						
						String rng = (String) inputs[3];
						rng = Methods.applyYorKorNM(rng);

						// validates RANGE and continues
						if (rangeValidates(rng) && rng.length() > 0) {
							double range = Double.valueOf(rng); //LABELS DOUBLE RANGE

							String crs = Methods.stripLeadingZeros((String) inputs[4]);

							// validates COURSE and continues
							if (courseValidates(crs) && crs.length() > 0) {
								double course = Double.valueOf(crs);//LABELS DOUBLE COURSE
								course = (course > 360 ? course - 360 : course);								
								
								String spd = (String) inputs[5];

								// validates SPEED and continues
								if (speedValidates(spd) && spd.length() > 0) {
									int speed = Integer.valueOf(spd); //LABELS INT SPEED

									//TODO hacked out of place!!!
									
									String nm = ((String) inputs[1]).trim();
									if(nameValidates(nm)){
									
										String name = nm.toUpperCase();
										
										Contact newContact = new Contact(this, type, name, bearing, range, course, speed);
										System.out.print("New contact designate ");
										newContact.report();
										contacts.add(newContact);
									}
								}
							}
						}
					}
				}
			} else {
				Methods.errorMessage("Invalid command");
			}
		} // END COMMAND LINE LOOP
	}// END runSonarSimulation()
	
	private void reportContacts() {
		ownShip.report();

		if (!contacts.isEmpty()) {
			System.out.println("Contacts: ");
			Collections.sort(contacts);
			Iterator<Contact> iter = contacts.iterator();
			while (iter.hasNext()) {
				Contact contact = iter.next();
				contact.report();
			}
		} else {
			System.out.println("No contacts on SONAR, Sir");
		}
	}

	// INPUT VALIDATOR METHODS
	// NOTE: WILL RETURN TRUE IF STRING IS OF LENGTH ZERO 
	
	private boolean courseValidates(String crs) {
		if ((!Methods.isNumber(crs) || crs.length() > 5) && crs.length() != 0) {
			Methods.errorMessage("Please enter a valid course, that means a positive number genius");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean nameValidates(String nm) {
		
		if(!nm.substring(0, 1).equalsIgnoreCase("s") && Methods.isNumber(nm.substring(1))){
			Methods.errorMessage("Incorrect name format");
			return false;
		}
		
		Iterator<Contact> iter = contacts.iterator();		
		while(iter.hasNext()){
			if(iter.next().getName().equalsIgnoreCase(nm)){
				Methods.errorMessage("Contact already exists - try another name");
				return false;
			}
					
		}

		return true;
	}

	private boolean speedValidates(String spd) {
		if ((!Methods.isNumber(spd) || Integer.valueOf(spd) > 40) && spd.length() != 0) {
			Methods.errorMessage("Unless you know something I don't, ships don't go over 40 KTS...");
			return false;
		} else {
			return true;
		}
	}

	private boolean rangeValidates(String rng) {
		if ((rng.length() < 1 || !Methods.isNumber(rng)) && rng.length() != 0) {
			Methods.errorMessage("Please enter a valid range, that means a positive number genius");
			return false;
		} else {
			return true;
		}
	}

	private boolean bearingValidates(String brg) {
		if ((!Methods.isNumber(brg) || brg.length() > 5) && brg.length() != 0) {
			Methods.errorMessage("Please enter a valid bearing, that means a positive number genius");
			return false;
		} else {
			return true;
		}
	}
}

