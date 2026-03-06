// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class EnumItem extends EnumList {

    private String I1;
    private IsInitialized IsInitialized;

    public EnumItem (String I1, IsInitialized IsInitialized) {
        this.I1=I1;
        this.IsInitialized=IsInitialized;
        if(IsInitialized!=null) IsInitialized.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public IsInitialized getIsInitialized() {
        return IsInitialized;
    }

    public void setIsInitialized(IsInitialized IsInitialized) {
        this.IsInitialized=IsInitialized;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IsInitialized!=null) IsInitialized.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IsInitialized!=null) IsInitialized.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IsInitialized!=null) IsInitialized.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumItem(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(IsInitialized!=null)
            buffer.append(IsInitialized.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumItem]");
        return buffer.toString();
    }
}
