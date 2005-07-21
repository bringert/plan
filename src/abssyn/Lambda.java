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

public class Lambda extends Expr {

	private Formals formals;

	private Expr body;

	public Lambda (Formals formals, Expr body) {
		this.formals = formals;
		this.body = body;
	}
	
	public Formals getFormals () {
		return formals;
	}
	
	public Expr getBody () {
		return body;
	}

	public <R,A> R visit (Visitor<R,A> v, A arg) {
		return v._case(this, arg);
	}
}
