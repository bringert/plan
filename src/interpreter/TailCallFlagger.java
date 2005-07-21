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

import java.util.*;

public class TailCallFlagger extends Tree.Visitor<Void,Boolean> {

	private static final TailCallFlagger FLAG = new TailCallFlagger();

	private TailCallFlagger() { }

	public static Void flag (Expr expr, Boolean tail) {
		return expr.visit(FLAG, tail);
	}


	public Void _case (Definition def, Boolean tail) {
		return flag(def.getExpr(), Boolean.FALSE);
	}

	public Void _case (BooleanLiteral b, Boolean tail) {
		return null;
	}

	public Void _case (Identifier i, Boolean tail) {
		return null;
	}

	public Void _case (If i, Boolean tail) {
		flag(i.getTest(), Boolean.FALSE);
		flag(i.getConsequence(), tail);
		flag(i.getAlternative(), tail);
		return null;
	}

	public Void _case (IntegerLiteral i, Boolean tail) {
		return null;
	}

	public Void _case (Lambda l, Boolean tail) {
		return flag(l.getBody(), Boolean.TRUE);
	}

	public Void _case (Letrec l, Boolean tail) {
		Iterator<Pair<Identifier,Expr>> it = 
			l.getBindings().iterator(); 
		while (it.hasNext())
			flag(it.next().snd(), Boolean.FALSE);
		return flag(l.getBody(), tail);
	}	

	public Void _case (ProcedureCall pc, Boolean tail) {
		pc.setIsTailCall(tail.booleanValue());

		Expr operator = pc.getOperator();
		flag(operator, Boolean.FALSE);

		boolean t = false; // whether last expr is in tail context
		if (tail.booleanValue() && operator instanceof Identifier) {
                        String op = ((Identifier)operator).getName();
                        t = op.equals("and") || op.equals("or");
                }

                Iterator<Expr> it = pc.getOperands().iterator();
                while (it.hasNext()) {
                        // in and / or last expression is in tail context
                        flag(it.next(), Boolean.valueOf(t && !it.hasNext()));
                }

		return null;
	}

	public Void _case (Quote q, Boolean tail) {
		return null;
	}

	public Void _case (Sequence s, Boolean tail) {
		flag(s.getHead(), Boolean.FALSE);
		return flag(s.getTail(), tail);
	}
}
