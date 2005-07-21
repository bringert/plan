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

import java.util.*;

public class Evaluator extends Tree.Visitor<Value,Env> {

	private static final Evaluator EVAL = new Evaluator();

	private Evaluator() { }

	public static Value eval (Expr expr, Env env) {
		return expr.visit(EVAL, env);
	}

	public Value _case (Definition def, Env env) {
		env.set(def.getName().getName(), eval(def.getExpr(), env));
		return new SymbolValue(def.getName().getName());
	}

	public Value _case (BooleanLiteral b, Env env) {
		return BooleanValue.getBoolean(b.getValue());
	}
	
	public Value _case (Identifier i, Env env) {
		Value v = env.get(i.getName());
		if (v == null) 
			throw new SchemeException(i.getName() 
						  + " is undefined.");
		return v;
	}

	public Value _case (If i, Env env) {
		Value v = eval(i.getTest(), env);

		if (v.isTrue()) {
			return eval(i.getConsequence(), env);
		} else {
			return eval(i.getAlternative(), env);
		}
	}

	public Value _case (IntegerLiteral i, Env env) {
		return new IntegerValue(i.getValue());
	}

	public Value _case (Lambda l, Env env) {
		return new Closure(l, env);
	}

	public Value _case (Letrec l, Env env) {
		Env env2 = new Env(env);

                Iterator<Pair<Identifier,Expr>> it = 
			l.getBindings().iterator();
                while (it.hasNext()) {
                        Pair<Identifier,Expr> b = it.next();
                        Value v = eval(b.snd(), env2);
                        env2.set(b.fst().getName(), v);
                }

                return eval(l.getBody(), env2);
	}

	public Value _case (ProcedureCall pc, Env env) {
		Value p = eval(pc.getOperator(), env);

		List<Value> args = new LinkedList<Value>();
		Iterator<Expr> it = pc.getOperands().iterator();
		while (it.hasNext())
			args.add(eval(it.next(), env));

		if (pc.isTailCall())
			return new TailCall(p, args);

		Value v = p.apply(env, args);
		while (v instanceof TailCall)
			v = ((TailCall)v).execute(env);

		return v;
	}

	public Value _case (Quote q, Env env) {
		return QuoteEvaluator.eval(q.getDatum());
	}

	public Value _case (Sequence s, Env env) {
		eval(s.getHead(), env);
		return eval(s.getTail(), env);
	}

}
