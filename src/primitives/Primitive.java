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

package primitives;

import env.*;

public abstract class Primitive extends Value {

	protected static final Primitive[] primitives = {
		new Add(),
		new Multiply(),
		new Subtract(),
		new Divide(),
		new IsEqual(),
		new IsNull(),
		new IsNumber(),
		new IsList(),
		new IsSymbol(),
		new IsBoolean(),
		new Car(),
		new Cdr(),
		new Cons(),
		new List(),
		new Display(),
		new Newline(),
		new DumpEnvironment(),
		new Apply(),
		new Eq(),
		new LT(),
		new GT(),
		new LE(),
		new GE(),
	};


	private String name;


	public Primitive (String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public String toString () {
		return "<primitive: " + name + ">";
	}


	protected void checkArgumentCount (java.util.List<Value> args, int n) {
		if (args.size() != n) 
			throw new SchemeException(
				"Wrong number of arguments in: "
				+ callToString(args));
	}

	protected void checkMinArgumentCount (java.util.List<Value> args, 
					      int min) {
		if (args.size() < min) 
			throw new SchemeException(
				"Too few arguments in: " + callToString(args));
	}

	public String callToString (java.util.List<Value> args) {
		StringBuffer sb = new StringBuffer();
		sb.append('(').append(name);
		java.util.Iterator<Value> it = args.iterator();
		while (it.hasNext()) {
			sb.append(' ').append(it.next());
		}
		sb.append(')');
		return sb.toString();
	}

	protected IntegerValue argToNumeric (java.util.List<Value> args, 
					     Value arg) {
		checkArgumentType(IntegerValue.class, args, arg);
		return (IntegerValue)arg;
	}

	protected Value checkListArgument (java.util.List<Value> args, 
					   Value arg) {
		if (!arg.isList()) 
			throw new SchemeException(
				arg + " is not a list in " 
				+ callToString(args));

		return arg;
	}

	protected void checkArgumentType (Class c, java.util.List<Value> args, 
					  Value arg) {
		if (!(c.isInstance(arg))) 
			throw new SchemeException(
				arg + " has wrong type in " 
				+ callToString(args));	
	}



	public static void definePrimitives (Env env) {
		for (int i = 0; i < primitives.length; i++) {
			env.set(primitives[i].getName(), primitives[i]);
		}
	}

}
