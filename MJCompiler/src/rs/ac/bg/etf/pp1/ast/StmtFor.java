// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class StmtFor extends Statement {

    private ForStart ForStart;
    private OptDesignatorStatement OptDesignatorStatement;
    private FirstSemiFor FirstSemiFor;
    private OptCondition OptCondition;
    private Character S5;
    private OptDesignatorStatement OptDesignatorStatement1;
    private ForBracketEnd ForBracketEnd;
    private Statement Statement;

    public StmtFor (ForStart ForStart, OptDesignatorStatement OptDesignatorStatement, FirstSemiFor FirstSemiFor, OptCondition OptCondition, Character S5, OptDesignatorStatement OptDesignatorStatement1, ForBracketEnd ForBracketEnd, Statement Statement) {
        this.ForStart=ForStart;
        if(ForStart!=null) ForStart.setParent(this);
        this.OptDesignatorStatement=OptDesignatorStatement;
        if(OptDesignatorStatement!=null) OptDesignatorStatement.setParent(this);
        this.FirstSemiFor=FirstSemiFor;
        if(FirstSemiFor!=null) FirstSemiFor.setParent(this);
        this.OptCondition=OptCondition;
        if(OptCondition!=null) OptCondition.setParent(this);
        this.S5=S5;
        this.OptDesignatorStatement1=OptDesignatorStatement1;
        if(OptDesignatorStatement1!=null) OptDesignatorStatement1.setParent(this);
        this.ForBracketEnd=ForBracketEnd;
        if(ForBracketEnd!=null) ForBracketEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForStart getForStart() {
        return ForStart;
    }

    public void setForStart(ForStart ForStart) {
        this.ForStart=ForStart;
    }

    public OptDesignatorStatement getOptDesignatorStatement() {
        return OptDesignatorStatement;
    }

    public void setOptDesignatorStatement(OptDesignatorStatement OptDesignatorStatement) {
        this.OptDesignatorStatement=OptDesignatorStatement;
    }

    public FirstSemiFor getFirstSemiFor() {
        return FirstSemiFor;
    }

    public void setFirstSemiFor(FirstSemiFor FirstSemiFor) {
        this.FirstSemiFor=FirstSemiFor;
    }

    public OptCondition getOptCondition() {
        return OptCondition;
    }

    public void setOptCondition(OptCondition OptCondition) {
        this.OptCondition=OptCondition;
    }

    public Character getS5() {
        return S5;
    }

    public void setS5(Character S5) {
        this.S5=S5;
    }

    public OptDesignatorStatement getOptDesignatorStatement1() {
        return OptDesignatorStatement1;
    }

    public void setOptDesignatorStatement1(OptDesignatorStatement OptDesignatorStatement1) {
        this.OptDesignatorStatement1=OptDesignatorStatement1;
    }

    public ForBracketEnd getForBracketEnd() {
        return ForBracketEnd;
    }

    public void setForBracketEnd(ForBracketEnd ForBracketEnd) {
        this.ForBracketEnd=ForBracketEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForStart!=null) ForStart.accept(visitor);
        if(OptDesignatorStatement!=null) OptDesignatorStatement.accept(visitor);
        if(FirstSemiFor!=null) FirstSemiFor.accept(visitor);
        if(OptCondition!=null) OptCondition.accept(visitor);
        if(OptDesignatorStatement1!=null) OptDesignatorStatement1.accept(visitor);
        if(ForBracketEnd!=null) ForBracketEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForStart!=null) ForStart.traverseTopDown(visitor);
        if(OptDesignatorStatement!=null) OptDesignatorStatement.traverseTopDown(visitor);
        if(FirstSemiFor!=null) FirstSemiFor.traverseTopDown(visitor);
        if(OptCondition!=null) OptCondition.traverseTopDown(visitor);
        if(OptDesignatorStatement1!=null) OptDesignatorStatement1.traverseTopDown(visitor);
        if(ForBracketEnd!=null) ForBracketEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForStart!=null) ForStart.traverseBottomUp(visitor);
        if(OptDesignatorStatement!=null) OptDesignatorStatement.traverseBottomUp(visitor);
        if(FirstSemiFor!=null) FirstSemiFor.traverseBottomUp(visitor);
        if(OptCondition!=null) OptCondition.traverseBottomUp(visitor);
        if(OptDesignatorStatement1!=null) OptDesignatorStatement1.traverseBottomUp(visitor);
        if(ForBracketEnd!=null) ForBracketEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtFor(\n");

        if(ForStart!=null)
            buffer.append(ForStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptDesignatorStatement!=null)
            buffer.append(OptDesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FirstSemiFor!=null)
            buffer.append(FirstSemiFor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptCondition!=null)
            buffer.append(OptCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+S5);
        buffer.append("\n");

        if(OptDesignatorStatement1!=null)
            buffer.append(OptDesignatorStatement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForBracketEnd!=null)
            buffer.append(ForBracketEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtFor]");
        return buffer.toString();
    }
}
