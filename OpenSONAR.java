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

public class OpenSONAR {

	///////////////////////////////////////////////////////////
	// INSTANCE VARIABLES \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	///////////////////////////////////////////////////////////
	
	// STANDARD PROGRAM IDENTIFICATION INSTANCE VARIABLES \\
	public static final double VERSION = 1.0;
	public static final double MIN_JAVA_VERSION_REQ = 1.5;
	public static final String VERSION_PREFIX = "OpenSONAR_v";
	public static final String VERSION_STRING = VERSION_PREFIX + VERSION;
	public static final String VERSION_CHECK_FILE = "http://opensonar.svn.sourceforge.net/viewvc/*checkout*/opensonar/trunk/versions.txt";
	public static final String SITE_URL = "http://robertrawls.net/software/jwolfpack/";
	public static final String GPL_URL = "http://www.gnu.org/licenses/gpl.html";

	public static void main(String[] args) {

		/*
		 * VERIFIES JAVA VERSION This program requires 1.5 or greater due to its
		 * use of generics. 
		 */
		if(!Methods.enoughJavaInside(MIN_JAVA_VERSION_REQ)){
			throwErrorMessage("Your copy of Java is out of date.  Please get the latest version");
			System.exit(1);
		}

		boolean debug = false;

		// APPLIES DEBUG FROM PROGRAM INVOCATION PARAMETERS
		if (args.length > 0) {

			ArrayList<String> argList = new ArrayList<String>();
			int n = args.length;
			for (int i = 0; i < n; i++) {
				argList.add(args[i]);
			}

			if (Methods.strArrayContainsToken(argList, "-debug"))
				debug = true;
		}

		if(debug) 
			System.out.println("^^^ DEBUG ENABLED ^^^");
		
		System.out.println("Program Version: " + VERSION_STRING + "\n");
		
		System.out.println(""
			   	 + "* OpenSONAR  Copyright (C) 2007  Robert A. Rawls\n"
			   	 + "* This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.\n"
			   	 + "* This is free software, and you are welcome to redistribute it under\n"
			   	 + "* certain conditions; see full GPL that came with OpenSONAR or online at\n"
			   	 + "* http://www.gnu.org/licenses/gpl.txt\n"
			   	 );
		
		Methods.isLatestVersion(VERSION, VERSION_CHECK_FILE);
		
		System.out.println("\nPress ENTER to start simulator...");
		
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.print("$> ");
			String line = scanner.nextLine().toLowerCase().trim();
			
			if(line.equals("continue") || line.equals("")){					// CASE START SIMULATOR!!!
				// BEGINS SIMULATOR
				System.out.println("\nStarting OpenSONAR SONAR Simulation ...");

				try{
					new Sonarman(debug);
				}catch (Exception e){
					e.printStackTrace();
				}
			} else if (line.equals("show w")) {								// CASE SHOW W!!!
				System.out.println("IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING WILL ANY\n" +
								   "COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MODIFIES AND/OR CONVEYS THE PROGRAM AS\n" +
								   "PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL,\n" +
								   "INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE\n" +
								   "PROGRAM (INCLUDING BUT NOT LIMITED TO LOSS OF DATA OR DATA BEING RENDERED\n" +
								   "INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD PARTIES OR A FAILURE OF THE PROGRAM\n" +
								   "TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN\n" +
									"ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.\n");
			}else if(line.equals("exit") || line.equals("quit")){
				System.out.println("Exiting OpenSONAR . . .");
				System.exit(0);
			}else{
				Methods.errorMessage("Please enter a valid command (`continue' starts simulator)");
			}
		}
	}
	
	/**
	 * Outputs an error message
	 * 
	 * @param msg
	 *         Error message to be displayed
	 */
	public static void throwErrorMessage(String msg){
		System.out.println(msg);
	}	
}

