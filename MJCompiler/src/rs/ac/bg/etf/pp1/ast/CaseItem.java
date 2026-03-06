// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class CaseItem implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private CaseStart CaseStart;
    private Statements Statements;
    private JumpOverJump JumpOverJump;

    public CaseItem (CaseStart CaseStart, Statements Statements, JumpOverJump JumpOverJump) {
        this.CaseStart=CaseStart;
        if(CaseStart!=null) CaseStart.setParent(this);
        this.Statements=Statements;
        if(Statements!=null) Statements.setParent(this);
        this.JumpOverJump=JumpOverJump;
        if(JumpOverJump!=null) JumpOverJump.setParent(this);
    }

    public CaseStart getCaseStart() {
        return CaseStart;
    }

    public void setCaseStart(CaseStart CaseStart) {
        this.CaseStart=CaseStart;
    }

    public Statements getStatements() {
        return Statements;
    }

    public void setStatements(Statements Statements) {
        this.Statements=Statements;
    }

    public JumpOverJump getJumpOverJump() {
        return JumpOverJump;
    }

    public void setJumpOverJump(JumpOverJump JumpOverJump) {
        this.JumpOverJump=JumpOverJump;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CaseStart!=null) CaseStart.accept(visitor);
        if(Statements!=null) Statements.accept(visitor);
        if(JumpOverJump!=null) JumpOverJump.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseStart!=null) CaseStart.traverseTopDown(visitor);
        if(Statements!=null) Statements.traverseTopDown(visitor);
        if(JumpOverJump!=null) JumpOverJump.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseStart!=null) CaseStart.traverseBottomUp(visitor);
        if(Statements!=null) Statements.traverseBottomUp(visitor);
        if(JumpOverJump!=null) JumpOverJump.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseItem(\n");

        if(CaseStart!=null)
            buffer.append(CaseStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statements!=null)
            buffer.append(Statements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(JumpOverJump!=null)
            buffer.append(JumpOverJump.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseItem]");
        return buffer.toString();
    }
}
