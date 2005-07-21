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

package abssyn;

public abstract class Tree {

	public abstract <R,A> R visit (Visitor<R,A> v, A arg);

	public abstract static class Visitor <R,A> {
		public R _case (Tree t, A arg) {
			throw new RuntimeException(
				this.getClass().getName() + " has no case for "
				+ t.getClass().getName());
		}

		public R _case (Definition t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (BooleanLiteral t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (Identifier t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (If t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (IntegerLiteral t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (Lambda t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (Letrec t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (PairDatum t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (ProcedureCall t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (Quote t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (Sequence t, A arg) {
			return _case((Tree)t, arg);
		}

		public R _case (UnitDatum t, A arg) {
			return _case((Tree)t, arg);
		}
	}

}
