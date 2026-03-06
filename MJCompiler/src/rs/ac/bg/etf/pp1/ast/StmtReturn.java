// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class StmtReturn extends Statement {

    private ReturnStart ReturnStart;
    private OptExpr OptExpr;
    private SemiLine SemiLine;

    public StmtReturn (ReturnStart ReturnStart, OptExpr OptExpr, SemiLine SemiLine) {
        this.ReturnStart=ReturnStart;
        if(ReturnStart!=null) ReturnStart.setParent(this);
        this.OptExpr=OptExpr;
        if(OptExpr!=null) OptExpr.setParent(this);
        this.SemiLine=SemiLine;
        if(SemiLine!=null) SemiLine.setParent(this);
    }

    public ReturnStart getReturnStart() {
        return ReturnStart;
    }

    public void setReturnStart(ReturnStart ReturnStart) {
        this.ReturnStart=ReturnStart;
    }

    public OptExpr getOptExpr() {
        return OptExpr;
    }

    public void setOptExpr(OptExpr OptExpr) {
        this.OptExpr=OptExpr;
    }

    public SemiLine getSemiLine() {
        return SemiLine;
    }

    public void setSemiLine(SemiLine SemiLine) {
        this.SemiLine=SemiLine;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnStart!=null) ReturnStart.accept(visitor);
        if(OptExpr!=null) OptExpr.accept(visitor);
        if(SemiLine!=null) SemiLine.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnStart!=null) ReturnStart.traverseTopDown(visitor);
        if(OptExpr!=null) OptExpr.traverseTopDown(visitor);
        if(SemiLine!=null) SemiLine.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnStart!=null) ReturnStart.traverseBottomUp(visitor);
        if(OptExpr!=null) OptExpr.traverseBottomUp(visitor);
        if(SemiLine!=null) SemiLine.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtReturn(\n");

        if(ReturnStart!=null)
            buffer.append(ReturnStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptExpr!=null)
            buffer.append(OptExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SemiLine!=null)
            buffer.append(SemiLine.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtReturn]");
        return buffer.toString();
    }
}
