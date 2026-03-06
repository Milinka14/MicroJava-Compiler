// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class VarItem extends VarList {

    private String I1;
    private IsList IsList;

    public VarItem (String I1, IsList IsList) {
        this.I1=I1;
        this.IsList=IsList;
        if(IsList!=null) IsList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public IsList getIsList() {
        return IsList;
    }

    public void setIsList(IsList IsList) {
        this.IsList=IsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IsList!=null) IsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IsList!=null) IsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IsList!=null) IsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarItem(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(IsList!=null)
            buffer.append(IsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarItem]");
        return buffer.toString();
    }
}
