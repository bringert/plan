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

public class Apply extends Primitive {

	public Apply () {
		super("apply");
	}

	public Value apply (Env env, java.util.List<Value> args) {
		checkMinArgumentCount(args, 2);
		Value l = checkListArgument(args, args.get(args.size()-1));

		java.util.List<Value> args2 = PairValue.toList(l);
		args2.addAll(0, args.subList(1, args.size()-1));
		
		return args.get(0).apply(env, args2); 
	}

}
