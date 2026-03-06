// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class StmtPrint extends Statement {

    private Expr Expr;
    private Width Width;
    private Character S3;

    public StmtPrint (Expr Expr, Width Width, Character S3) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.Width=Width;
        if(Width!=null) Width.setParent(this);
        this.S3=S3;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Width getWidth() {
        return Width;
    }

    public void setWidth(Width Width) {
        this.Width=Width;
    }

    public Character getS3() {
        return S3;
    }

    public void setS3(Character S3) {
        this.S3=S3;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(Width!=null) Width.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(Width!=null) Width.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(Width!=null) Width.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtPrint(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Width!=null)
            buffer.append(Width.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+S3);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtPrint]");
        return buffer.toString();
    }
}
