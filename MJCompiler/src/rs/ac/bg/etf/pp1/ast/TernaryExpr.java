// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class TernaryExpr extends CondExpr {

    private Condition Condition;
    private TernaryQuestion TernaryQuestion;
    private Expr Expr;
    private SecondExpr SecondExpr;
    private Expr Expr1;

    public TernaryExpr (Condition Condition, TernaryQuestion TernaryQuestion, Expr Expr, SecondExpr SecondExpr, Expr Expr1) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.TernaryQuestion=TernaryQuestion;
        if(TernaryQuestion!=null) TernaryQuestion.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.SecondExpr=SecondExpr;
        if(SecondExpr!=null) SecondExpr.setParent(this);
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public TernaryQuestion getTernaryQuestion() {
        return TernaryQuestion;
    }

    public void setTernaryQuestion(TernaryQuestion TernaryQuestion) {
        this.TernaryQuestion=TernaryQuestion;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public SecondExpr getSecondExpr() {
        return SecondExpr;
    }

    public void setSecondExpr(SecondExpr SecondExpr) {
        this.SecondExpr=SecondExpr;
    }

    public Expr getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr Expr1) {
        this.Expr1=Expr1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(TernaryQuestion!=null) TernaryQuestion.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(SecondExpr!=null) SecondExpr.accept(visitor);
        if(Expr1!=null) Expr1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(TernaryQuestion!=null) TernaryQuestion.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(SecondExpr!=null) SecondExpr.traverseTopDown(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(TernaryQuestion!=null) TernaryQuestion.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(SecondExpr!=null) SecondExpr.traverseBottomUp(visitor);
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TernaryExpr(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TernaryQuestion!=null)
            buffer.append(TernaryQuestion.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SecondExpr!=null)
            buffer.append(SecondExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TernaryExpr]");
        return buffer.toString();
    }
}
