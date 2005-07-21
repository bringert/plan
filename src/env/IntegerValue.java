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

import java.math.BigInteger;

public class IntegerValue extends Value {

	public static final IntegerValue ZERO = 
	new IntegerValue(BigInteger.ZERO);
	public static final IntegerValue ONE = 
	new IntegerValue(BigInteger.ONE);
	

	private BigInteger value;

	public IntegerValue (BigInteger value) {
		this.value = value;
	}

	public IntegerValue add (IntegerValue n) {
		return new IntegerValue(value.add(n.value));
	}

	public IntegerValue subtract (IntegerValue n) {
		return new IntegerValue(value.subtract(n.value));
	}

	public IntegerValue multiply (IntegerValue n) {
		return new IntegerValue(value.multiply(n.value));
	}

	public IntegerValue divide (IntegerValue n) {
		return new IntegerValue(value.divide(n.value));
	}

	public IntegerValue negate () {
		return new IntegerValue(value.negate());
	}

	public int compareTo (IntegerValue n) {
		return value.compareTo(n.value);
	}

	public String toString () {
		return value.toString();
	}

	public boolean equals (Object o) {
		return (o instanceof IntegerValue) &&
			((IntegerValue)o).value.equals(value);
	}

}
