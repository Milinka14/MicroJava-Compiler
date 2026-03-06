// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class EnumListItems extends EnumList {

    private EnumList EnumList;
    private String I2;
    private IsInitialized IsInitialized;

    public EnumListItems (EnumList EnumList, String I2, IsInitialized IsInitialized) {
        this.EnumList=EnumList;
        if(EnumList!=null) EnumList.setParent(this);
        this.I2=I2;
        this.IsInitialized=IsInitialized;
        if(IsInitialized!=null) IsInitialized.setParent(this);
    }

    public EnumList getEnumList() {
        return EnumList;
    }

    public void setEnumList(EnumList EnumList) {
        this.EnumList=EnumList;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
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
        if(EnumList!=null) EnumList.accept(visitor);
        if(IsInitialized!=null) IsInitialized.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumList!=null) EnumList.traverseTopDown(visitor);
        if(IsInitialized!=null) IsInitialized.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumList!=null) EnumList.traverseBottomUp(visitor);
        if(IsInitialized!=null) IsInitialized.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumListItems(\n");

        if(EnumList!=null)
            buffer.append(EnumList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(IsInitialized!=null)
            buffer.append(IsInitialized.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumListItems]");
        return buffer.toString();
    }
}
