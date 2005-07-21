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

public abstract class ComparisonPrimitive extends Primitive {

	public ComparisonPrimitive (String name) {
		super(name);
	}

	/**
	 * @param curr -1: z1 < z2, 0: z1 == z2, 1: z1 > z2
	 */
	public abstract boolean test (int comp);

	public Value apply (Env env, java.util.List<Value> args) {
		checkMinArgumentCount(args, 2);

		IntegerValue prev = argToNumeric(args, args.get(0));
		java.util.Iterator<Value> it = args.listIterator(1);
		while (it.hasNext()) {
			IntegerValue curr = argToNumeric(args, it.next());
			if (!test(prev.compareTo(curr)))
				return BooleanValue.FALSE;
			prev = curr;
		}

		return BooleanValue.TRUE;
	}

}
