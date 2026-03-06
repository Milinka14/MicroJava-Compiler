// generated with ast extension for cup
// version 0.8
// 17/1/2026 18:13:54


package rs.ac.bg.etf.pp1.ast;

public class VarDeclF extends VarDeclFunc {

    private VarDeclFunc VarDeclFunc;
    private VarDecl VarDecl;

    public VarDeclF (VarDeclFunc VarDeclFunc, VarDecl VarDecl) {
        this.VarDeclFunc=VarDeclFunc;
        if(VarDeclFunc!=null) VarDeclFunc.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public VarDeclFunc getVarDeclFunc() {
        return VarDeclFunc;
    }

    public void setVarDeclFunc(VarDeclFunc VarDeclFunc) {
        this.VarDeclFunc=VarDeclFunc;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclFunc!=null) VarDeclFunc.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclFunc!=null) VarDeclFunc.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclFunc!=null) VarDeclFunc.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclF(\n");

        if(VarDeclFunc!=null)
            buffer.append(VarDeclFunc.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclF]");
        return buffer.toString();
    }
}
