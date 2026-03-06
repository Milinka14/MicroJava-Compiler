// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class StmtIf extends Statement {

    private Condition Condition;
    private IfConditionEnd IfConditionEnd;
    private Statement Statement;
    private IfEnded IfEnded;
    private ElsePart ElsePart;

    public StmtIf (Condition Condition, IfConditionEnd IfConditionEnd, Statement Statement, IfEnded IfEnded, ElsePart ElsePart) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.IfConditionEnd=IfConditionEnd;
        if(IfConditionEnd!=null) IfConditionEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.IfEnded=IfEnded;
        if(IfEnded!=null) IfEnded.setParent(this);
        this.ElsePart=ElsePart;
        if(ElsePart!=null) ElsePart.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public IfConditionEnd getIfConditionEnd() {
        return IfConditionEnd;
    }

    public void setIfConditionEnd(IfConditionEnd IfConditionEnd) {
        this.IfConditionEnd=IfConditionEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public IfEnded getIfEnded() {
        return IfEnded;
    }

    public void setIfEnded(IfEnded IfEnded) {
        this.IfEnded=IfEnded;
    }

    public ElsePart getElsePart() {
        return ElsePart;
    }

    public void setElsePart(ElsePart ElsePart) {
        this.ElsePart=ElsePart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(IfConditionEnd!=null) IfConditionEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(IfEnded!=null) IfEnded.accept(visitor);
        if(ElsePart!=null) ElsePart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(IfConditionEnd!=null) IfConditionEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(IfEnded!=null) IfEnded.traverseTopDown(visitor);
        if(ElsePart!=null) ElsePart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(IfConditionEnd!=null) IfConditionEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(IfEnded!=null) IfEnded.traverseBottomUp(visitor);
        if(ElsePart!=null) ElsePart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtIf(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfConditionEnd!=null)
            buffer.append(IfConditionEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfEnded!=null)
            buffer.append(IfEnded.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElsePart!=null)
            buffer.append(ElsePart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtIf]");
        return buffer.toString();
    }
}
