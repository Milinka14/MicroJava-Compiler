// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class BooleanArith extends CondFact {

    private ArithExpr ArithExpr;

    public BooleanArith (ArithExpr ArithExpr) {
        this.ArithExpr=ArithExpr;
        if(ArithExpr!=null) ArithExpr.setParent(this);
    }

    public ArithExpr getArithExpr() {
        return ArithExpr;
    }

    public void setArithExpr(ArithExpr ArithExpr) {
        this.ArithExpr=ArithExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArithExpr!=null) ArithExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArithExpr!=null) ArithExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArithExpr!=null) ArithExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BooleanArith(\n");

        if(ArithExpr!=null)
            buffer.append(ArithExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BooleanArith]");
        return buffer.toString();
    }
}
