// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class RelationalOp extends CondFact {

    private ArithExpr ArithExpr;
    private Relop Relop;
    private ArithExpr ArithExpr1;

    public RelationalOp (ArithExpr ArithExpr, Relop Relop, ArithExpr ArithExpr1) {
        this.ArithExpr=ArithExpr;
        if(ArithExpr!=null) ArithExpr.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.ArithExpr1=ArithExpr1;
        if(ArithExpr1!=null) ArithExpr1.setParent(this);
    }

    public ArithExpr getArithExpr() {
        return ArithExpr;
    }

    public void setArithExpr(ArithExpr ArithExpr) {
        this.ArithExpr=ArithExpr;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public ArithExpr getArithExpr1() {
        return ArithExpr1;
    }

    public void setArithExpr1(ArithExpr ArithExpr1) {
        this.ArithExpr1=ArithExpr1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArithExpr!=null) ArithExpr.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(ArithExpr1!=null) ArithExpr1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArithExpr!=null) ArithExpr.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(ArithExpr1!=null) ArithExpr1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArithExpr!=null) ArithExpr.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(ArithExpr1!=null) ArithExpr1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelationalOp(\n");

        if(ArithExpr!=null)
            buffer.append(ArithExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ArithExpr1!=null)
            buffer.append(ArithExpr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RelationalOp]");
        return buffer.toString();
    }
}
