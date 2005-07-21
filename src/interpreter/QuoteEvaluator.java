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

package interpreter;

import util.*;
import abssyn.*;
import env.*;

public class QuoteEvaluator extends Tree.Visitor<Value,Void> {

	private static final QuoteEvaluator EVAL = new QuoteEvaluator();

	private QuoteEvaluator() { }

	public static Value eval (Expr e) {
		return e.visit(EVAL, null);
	}

	public Value _case (BooleanLiteral b, Void arg) {
		return BooleanValue.getBoolean(b.getValue());
	}
	
	public Value _case (Identifier i, Void arg) {
		return new SymbolValue(i.getName());
	}

	public Value _case (IntegerLiteral i, Void arg) {
		return new IntegerValue(i.getValue());
	}

	public Value _case (PairDatum p, Void arg) {
		return new PairValue(eval(p.getHead()), eval(p.getTail()));
	}

	public Value _case (UnitDatum p, Void arg) {
		return UnitValue.UNIT;
	}

	public Value _case (Quote q, Void arg) {
		return new PairValue(
			new SymbolValue("quote"), 
			new PairValue(eval(q.getDatum()), UnitValue.UNIT));
	}

}
