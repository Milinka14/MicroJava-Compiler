// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class SwitchBodyList extends SwitchBody {

    private SwitchBody SwitchBody;
    private CaseItem CaseItem;

    public SwitchBodyList (SwitchBody SwitchBody, CaseItem CaseItem) {
        this.SwitchBody=SwitchBody;
        if(SwitchBody!=null) SwitchBody.setParent(this);
        this.CaseItem=CaseItem;
        if(CaseItem!=null) CaseItem.setParent(this);
    }

    public SwitchBody getSwitchBody() {
        return SwitchBody;
    }

    public void setSwitchBody(SwitchBody SwitchBody) {
        this.SwitchBody=SwitchBody;
    }

    public CaseItem getCaseItem() {
        return CaseItem;
    }

    public void setCaseItem(CaseItem CaseItem) {
        this.CaseItem=CaseItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchBody!=null) SwitchBody.accept(visitor);
        if(CaseItem!=null) CaseItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchBody!=null) SwitchBody.traverseTopDown(visitor);
        if(CaseItem!=null) CaseItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchBody!=null) SwitchBody.traverseBottomUp(visitor);
        if(CaseItem!=null) CaseItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SwitchBodyList(\n");

        if(SwitchBody!=null)
            buffer.append(SwitchBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseItem!=null)
            buffer.append(CaseItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SwitchBodyList]");
        return buffer.toString();
    }
}
