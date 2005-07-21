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

public class Env {

	private Env parent;

	private Map<String,Value> map = null;

	
	public Env () {
		this(new EmptyEnv());
	}

	public Env (Env parent) {
		this.parent = parent;
	}


	public Value get (String name) {
		Value ret = (map == null) ? null : map.get(name);
		return (ret != null) ? ret : parent.get(name);
	}

	public Value set (String name, Value value) {
		if (map == null)
			map = new HashMap<String,Value>();

		return map.put(name, value);
	} 

	public String toString () {
		StringBuffer sb = new StringBuffer();

		sb.append(parent);
		sb.append("----------\n");
		if (map != null) {
			Iterator<Map.Entry<String,Value>> it = 
				map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String,Value> e = it.next();
				sb.append(e.getKey());
				sb.append(" = ");
				sb.append(e.getValue());
				sb.append('\n');
			}
		}

		return sb.toString();
	}

	private static class EmptyEnv extends Env {
		
		public EmptyEnv () {
			super(null);
		}

		public Value get (String name) {
			return null;
		}

		/** Should never be called. */
		public Value set (String name, Value value) {
			throw new RuntimeException();
		}

		public String toString () {
			return "";
		}

	}

}
