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

package env;

import java.util.*;

public class PairValue extends Value {

	private Value ar;

	private Value dr;

	public PairValue (Value ar, Value dr) { 
		this.ar = ar;
		this.dr = dr;
	}


	public Value car () {
		return ar;
	}

	public Value cdr () {
		return dr;
	}

	public boolean isList () {
		return dr.isList();
	}

	public String toString () {
		return "(" + listToString() + ")";
	}

	private String listToString  () {
		if (dr.equals(UnitValue.UNIT))
			return ar.toString();

		if (dr instanceof PairValue)
			return ar.toString() + ' ' 
				+ ((PairValue)dr).listToString();

		return ar.toString() + " . " + dr.toString();
	}

	public boolean equals (PairValue p) {
		return p.ar.equals(ar) && p.dr.equals(dr);
	}

	public boolean equals (Object o) {
		return (o instanceof PairValue) && equals((PairValue)o);
	}

	public static Value fromList (List<Value> l) {
		Value ret = UnitValue.UNIT;

		ListIterator<Value> it = l.listIterator(l.size());
		while (it.hasPrevious())
			ret = new PairValue(it.previous(), ret);

		return ret;
	}

        public static List<Value> toList (Value l) {
                if (!l.isList())
                        throw new IllegalArgumentException(
                                "Argument must be a list: " + l);

                LinkedList<Value> list = new LinkedList<Value>();

                while (l instanceof PairValue) {
                        PairValue p = (PairValue)l;
                        list.addLast(p.car());
                        l = p.cdr();
                }

                return list;
        }


}
