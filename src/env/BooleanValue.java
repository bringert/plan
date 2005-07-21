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

public class BooleanValue extends Value {

	public static final BooleanValue TRUE = new BooleanValue(true);
	
	public static final BooleanValue FALSE = new BooleanValue(false);


	private boolean value;

	private BooleanValue (boolean value) {
		this.value = value;
	}

	public boolean isTrue () {
		return value;
	}

	public String toString () {
		return value ? "#t" : "#f";
	}

	public boolean equals (Object o) {
		return (o instanceof BooleanValue) 
			&& ((BooleanValue)o).value == value;
	}

	public static final BooleanValue getBoolean (boolean b) {
		return b ? TRUE : FALSE;
	}

}
