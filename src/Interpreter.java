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

import parser.*;
import abssyn.*;
import env.*;
import primitives.*;
import interpreter.*;

import java.io.*;

public class Interpreter {

	private Env env;

	public Interpreter () {
		env = new Env();
		Primitive.definePrimitives(env);
	}

	private Value readOneAndEval (parser parser) 
		throws ParseException, SchemeException {
		Expr item = parser.ItemOrEnd();
		if (item == null) 
			return null;
		TailCallFlagger.flag(item, Boolean.FALSE);
		return Evaluator.eval(item, env);
	}

	public void source (String file) 	
		throws FileNotFoundException, IOException, ParseException, 
		SchemeException {

		Reader f = new BufferedReader(new FileReader(file));
		parser parser = new parser(f);
		while (readOneAndEval(parser) != null);
		f.close();
	}

	public void interactive (InputStream in) throws ParseException {
		parser parser = new parser(in);
			
		System.err.println("Welcome!");
			
		while (true) {
			try {
				System.out.print("> ");
				Value v = readOneAndEval(parser);
				if (v == null)
					break;
				System.out.println(v.toString());
			} catch (SchemeException ex) {
				System.out.println("Error: " 
						   + ex.getMessage());
			}
		}
		System.err.println("Bye bye!");
	}

}
