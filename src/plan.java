/* Copyright (C) 2003 Bjorn Bringert (bjorn@bringert.net)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import parser.ParseException;
import env.SchemeException;

import java.io.*;

public class plan {

	private static final String PRELUDE = "../prelude.scheme";

	private static void error (String msg) {
		System.err.println(msg);
		System.exit(1); 
	}

	public static void main (String args[]) {
		Interpreter inter = new Interpreter();

		try {
			System.err.println("Loading " + PRELUDE);
			inter.source(PRELUDE);
			
			for (int i = 0; i < args.length; i++) {
				System.err.println("Loading " + args[i]);
				inter.source(args[i]);
			}
			
			inter.interactive(System.in);
		} catch (SchemeException ex) {
			error(ex.toString());
		} catch (ParseException ex) {
			error(ex.toString());
		} catch (IOException ex) {
			error(ex.toString());
		}
	}

}
