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

import abssyn.*;
import interpreter.Evaluator;

import java.util.*;

public class Closure extends Value {

	private Lambda lambda;

	private Env env;

	public Closure (Lambda lambda, Env env) {
		this.lambda = lambda;
		this.env = env;
	}

	public Lambda getLambda () {
		return lambda;
	}

	public Env getEnv () {
		return env;
	}

	public Value apply (Env dynEnv, List<Value> args) {
		Env env2 = new Env(env);
		assign(env2, lambda.getFormals(), args);
		return Evaluator.eval(lambda.getBody(), env2);
	}

        private void assign (Env env, Formals f, List<Value> args) {
		ListIterator<Value> aIt = args.listIterator();

		Iterator<Identifier> fIt = f.getFormals().iterator();
                while (fIt.hasNext()) {
			if (!aIt.hasNext())
                                throw new SchemeException(
                                        "Too few arguments " +
					"in procedure call.");
                        env.set(fIt.next().getName(), aIt.next());
                }

                if (f.hasListFormal()) {
			Value l = PairValue.fromList(
				args.subList(aIt.nextIndex(), args.size()));
                        env.set(f.getListFormal().getName(), l);
                } else if (aIt.hasNext()) {
			throw new SchemeException(
				"Too many arguments in procedure call.");
                }
	}

	public String toString () {
		return "<closure>";
	}

}
