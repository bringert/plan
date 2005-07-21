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

public class Subtract extends Primitive {

	public Subtract () {
		super("-");
	}

	public Value apply (Env env, java.util.List<Value> args) {
		checkMinArgumentCount(args, 1);

		IntegerValue ret = argToNumeric(args, args.get(0));

		if (args.size() == 1)
			return ret.negate();
		
		java.util.Iterator<Value> it = args.listIterator(1);
		while (it.hasNext())
			ret = ret.subtract(argToNumeric(args, it.next()));

		return ret;
	}

}
