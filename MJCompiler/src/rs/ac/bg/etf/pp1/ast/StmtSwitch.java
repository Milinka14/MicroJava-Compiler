// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class StmtSwitch extends Statement {

    private SwitchStart SwitchStart;
    private Expr Expr;
    private SwitchBody SwitchBody;

    public StmtSwitch (SwitchStart SwitchStart, Expr Expr, SwitchBody SwitchBody) {
        this.SwitchStart=SwitchStart;
        if(SwitchStart!=null) SwitchStart.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.SwitchBody=SwitchBody;
        if(SwitchBody!=null) SwitchBody.setParent(this);
    }

    public SwitchStart getSwitchStart() {
        return SwitchStart;
    }

    public void setSwitchStart(SwitchStart SwitchStart) {
        this.SwitchStart=SwitchStart;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public SwitchBody getSwitchBody() {
        return SwitchBody;
    }

    public void setSwitchBody(SwitchBody SwitchBody) {
        this.SwitchBody=SwitchBody;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchStart!=null) SwitchStart.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(SwitchBody!=null) SwitchBody.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchStart!=null) SwitchStart.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(SwitchBody!=null) SwitchBody.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchStart!=null) SwitchStart.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(SwitchBody!=null) SwitchBody.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtSwitch(\n");

        if(SwitchStart!=null)
            buffer.append(SwitchStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SwitchBody!=null)
            buffer.append(SwitchBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtSwitch]");
        return buffer.toString();
    }
}
