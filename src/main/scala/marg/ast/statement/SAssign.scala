package marg.ast.statement

import marg.ast.base.ASTree
import marg.ast.leaf.SVariable
import marg.ast.other.Expr
import marg.exception.ParseException
import marg.parser.Env
import marg.token.TokenSet

class SAssign extends ASTree {
  var variable: SVariable = null
  var expr: ASTree = null

  def this(ls: TokenSet) {
    this()
    variable = new SVariable(ls)
    if (!ls.read("="))
      throw new ParseException("Syntax Error: invalid syntax.", ls)
    expr = new Expr(ls)
  }

  override def eval(e: Env) = {
    val key = variable.string
    val ret = expr.eval(e)

    if (!e.find(key)) sys error "Assignment to the undefined variable."
    e += (key -> ret)

    ret
  }
}
