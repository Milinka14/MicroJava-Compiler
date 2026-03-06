package rs.ac.bg.etf.pp1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

import javax.lang.model.type.NoType;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemAnalyzer extends VisitorAdaptor {

    public boolean error = false;
    private Logger log = Logger.getLogger(SemAnalyzer.class);
    
    HashMap<String, Obj> savedObjects = new HashMap<String, Obj>();
    
    List<Obj> constList = new ArrayList<>();
    
	private Struct currentType;
	
	private int currentConstant;
	
	private boolean isArray;
	
	private Struct boolType = Tab.find("bool").getType();
	
	private Struct lastConstType;
    
	boolean mainExists;
	
	private int formalParamCount;
	
	private Obj currentMeth;
	
	private Struct currentFuncRet;
	
	private int formalParams = 0;
	
	private SymbolDataStructure currentFields;
	
	private Struct currentEnum;
	
	private int enumValue;
	
	private HashSet<Integer> enumValues;
	
	private Struct lastDesignatorType;
	
	 Map<SyntaxNode, Struct> types = new HashMap<>();
	
	Map<SyntaxNode, Obj> objects = new HashMap<>();
	
	 Map<SyntaxNode, Obj> arrayObj = new HashMap<>();
		
	
	private boolean canBreak = false;
	
	private boolean canContinue = false;
	
	private int parametersToCheck = 0;
	
	private List<Set<Integer>> switchValues = new ArrayList<>();
	
	private int breakLevel = 0;
	
	private int continueLevel = 0;
	
	private int globalEnumNumbers = 0;
	
	public int nVars = 0;
	
	private Map<Integer, Struct> enumPointers = new HashMap<Integer, Struct>();
	private Map<Struct, Integer> enumReversePointers = new HashMap<Struct, Integer>();
	
 enum DesignatorKind {
	    VARIABLE,      // Običan IDENT
	    ARRAY_ELEMENT, // IDENT[Expr]
	    MEMBER,        // IDENT.IDENT (Enum konstanta ili polje klase)
	    LENGTH         // IDENT.length
	}

	private DesignatorKind currentDesignatorKind;	

	@Override
    public void visit(Program p) {
    	if (!mainExists) {
            reportError("Program mora imati definisanu main metodu!", p);
        }
    	
    	//System.err.println(Tab.currentScope.getnVars());
    	
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(savedObjects.get("program")); // ulancavamo na program
    	Tab.closeScope();//zatvarmo zavrsni scope    	
   	}
    
    
    @Override
    public void visit(ProgName p) {
        Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool)); // ubacivanje tyoe cvora za bool

    	reportInfo("brojjj" + 
    	    	Tab.currentScope.getnVars(),p);

    	savedObjects.put("program", Tab.insert(Obj.Prog, p.getI1(), Tab.noType));
    	Tab.openScope(); // scope ovaj zatvarmo na kraj
    }

    public void visit(DeclarationsList DeclarationsList) { 
    	
    }
    public void visit(NoDeclarations NoDeclarations) { }
    public void visit(DeclConst DeclConst) { }
    public void visit(DeclVar DeclVar) { }
    public void visit(DeclEnum DeclEnum) { }

    public void visit(ConstDecl ConstDecl) {
    	//type 	
    	currentType = Tab.noType;
    }
    
    private void addConstant(String name, SyntaxNode node) {
        if (Tab.currentScope().findSymbol(name) != null) {
            reportError("Vec postoji deklaracija sa istim imenom u ovom opsegu: " + name, node);
            return;
        }
        if (!lastConstType.assignableTo(currentType)) {
            reportError("Nekompatibilni tipovi pri dodjeli vrijednosti konstanti: " + name, node);
            return;
        }
        Obj newConst = Tab.insert(Obj.Con, name, currentType);
        newConst.setAdr(currentConstant);
    }

    public void visit(ConstItem ConstItem) { 
        addConstant(ConstItem.getI1(), ConstItem);
    }

    public void visit(ConstListItems ConstListItems) {
        addConstant(ConstListItems.getI2(), ConstListItems);
    }

    public void visit(VarDecl VarDecl) {
    	
    }
    
    public void visit(VarItem varItem) {
        addVariable(varItem.getI1(), varItem);
    }
    
    public void visit(VarListItems varListItems) {
        addVariable(varListItems.getI2(), varListItems);
    }

    private void addVariable(String name, SyntaxNode node) {

    	if (Tab.currentScope().findSymbol(name) != null) {
            reportError("Vec postoji deklaracija sa istim imenom: " + name, node);
            return;
        }

        Obj existingObj = Tab.find(name);
        
        if (existingObj != Tab.noObj && existingObj.getKind() == Obj.Type) {
            reportError("Vec postoji deklaracija sa istim imenom: " + name, node);
            return;
        }
        
        Struct typeToInsert = currentType;

        if (isArray) {
            typeToInsert = new Struct(Struct.Array, currentType);
        }

        Obj newObj = Tab.insert(Obj.Var, name, typeToInsert);        
        

        if (currentType.getKind() == Struct.Enum) {
        	newObj.setFpPos(enumReversePointers.get(currentType));
        }
        
    }
    
    public void visit(IsArray isArrayNode) { 
        this.isArray = true;
    }
    
    public void visit(NoArray noArrayNode) {
        this.isArray = false;
    }
    
    public void visit(EnumDecl enumDecl) { 
    	if (currentEnum != null && currentEnum != Tab.noType) {
	    	Tab.chainLocalSymbols(currentEnum);
	    	
	    	System.out.println("DEBUG: Provjera enuma " + enumDecl.getEnumName().getI1() + ":");
	        for (Obj o : currentEnum.getMembers()) {
	            System.out.println("   -> Nasao sam konstantu: " + o.getName() + " sa vrednoscu: " + o.getAdr());
	        }
	    }
        
    	Tab.closeScope();
    	currentEnum = null;
    }
        
    public void visit(EnumName enumName) {
        String name = enumName.getI1();
        enumValue = -1;
        enumValues = new HashSet<Integer>();
        globalEnumNumbers++;
        if (Tab.find(name) != Tab.noObj) {
            reportError("Vec postoji deklaracija sa istim imenom: " + name, enumName);
            currentEnum = Tab.noType; // Neutralni tip da izbegnemo null
        } else {
            currentEnum = new Struct(Struct.Enum);
            enumPointers.put(globalEnumNumbers, currentEnum);
            enumReversePointers.put(currentEnum,globalEnumNumbers);

            Tab.insert(Obj.Type, name, currentEnum).setFpPos(globalEnumNumbers);    
        }
        
        Tab.openScope();
    }
    
    public void visit(EnumItem enumItem) {
    	if (currentEnum == null) return; // Sigurnosni ventil
    	if (enumValues.contains(enumValue)) {
    		reportError("ENUM tip mora da sadzri jedinstvene vrijednosti polja",enumItem);
    		return;
    	}
    	Obj newField = Tab.insert(Obj.Con, enumItem.getI1(),Tab.intType);
    	newField.setFpPos(globalEnumNumbers);
    	newField.setAdr(enumValue);
    	enumValues.add(enumValue);
    	newField.setLevel(2);
    }

    public void visit(EnumListItems enumListItems) { 
    	if (currentEnum == null) return; // Sigurnosni ventil
    	if (enumValues.contains(enumValue)) {
    		reportError("ENUM tip mora da sadzri jedinstvene vrijednosti polja",enumListItems);
    		return;
    	}
    	Obj newField = Tab.insert(Obj.Con, enumListItems.getI2(),Tab.intType);
    	newField.setFpPos(globalEnumNumbers);
    	newField.setAdr(enumValue);
    	enumValues.add(enumValue);
    	newField.setLevel(2);
    }
    
    public void visit(IsInit ssInit) { 
    	enumValue = ssInit.getN1();
    }
    
    public void visit(NoInit noInit) {
    	enumValue++;
    }
    
    public void visit(MethodDeclarations methodDeclarations) { 
    }

    public void visit(NoMethodDeclarations noMethodDeclarations) { 
    	
    }

    public void visit(MethodDecl methodDecl) {
    	String methodName = methodDecl.getMethodName().getI1();

        if ("main".equals(methodName)) {
            if (currentFuncRet == Tab.noType && formalParamCount == 0) {
                mainExists = true;
            } else {
                reportError("Metoda main mora biti void i bez parametara!", methodDecl);
            }
        }

        Obj meth = savedObjects.get("currentMeth");
        if (meth != null) {
            Tab.chainLocalSymbols(meth); 
            savedObjects.remove("currentMeth");
        }
        Tab.closeScope();
    }
    
    public void visit(MethodName methodName) {
    	formalParamCount = 0; // ovde resetujem jer ce ovde sigurno kada dodje sledci metod da se udje
    	Obj obb = Tab.insert(Obj.Meth, methodName.getI1(), currentFuncRet);
    	savedObjects.put("currentMeth", obb);
    	objects.put(methodName, obb);
    	Tab.openScope();
    }

    public void visit(RetType retType) {
    	currentFuncRet = currentType;
    }

    public void visit(RetVoid retVoid) { 
    	currentFuncRet = Tab.noType;
    }

    public void visit(FormalParameters formalParameters) {
        // Čim smo prošli kroz sve parametre, upišimo njihov broj u Obj čvor
        Obj meth = savedObjects.get("currentMeth");
        if (meth != null) {
            meth.setLevel(formalParamCount);
            Tab.chainLocalSymbols(meth);
        }
    }
    
    public void visit(NoFormalParameters noFormalParameters) {
        Obj meth = savedObjects.get("currentMeth");
        if (meth != null) {
            meth.setLevel(0);
            Tab.chainLocalSymbols(meth);
        }
    }
    
    public void visit(FormalParamItem formalParamItem) {
    	addVariable(formalParamItem.getI2(), formalParamItem);
    	formalParamCount++;
    }

    public void visit(FormalParamList formalParamList) { 
    	addVariable(formalParamList.getI3(), formalParamList);
    	formalParamCount++;
    }

    public void visit(VarDeclF varDeclF) { }

    public void visit(NoVarDeclF noVarDeclF) { }
    
    
    /*Kontektsni uslovi--------------------------------------------------------------*/
    
    @Override
    public void visit(StmtIf stmtIf) {
        if (types.get(stmtIf.getCondition()) == Tab.noType) {
            // Greška je već ispisana negde dole, ćutimo.
        } else if (types.get(stmtIf.getCondition()) != boolType) {
            // Ovo se teoretski ne bi smelo desiti ako su visiti za CondFact dobri,
            // ali ako jesu, Condition će uvek biti boolType.
        }
    }
    
    public void visit(StmtFor stmtFor) {
        if (stmtFor.getOptCondition() instanceof OptCond) {
        	Condition cond = ((OptCond)stmtFor.getOptCondition()).getCondition();
     
            if (types.get(cond) != boolType) {
                reportError("Uslov u for petlji mora biti tipa bool!", stmtFor);
            }
        }
        
        breakLevel--;
        continueLevel--;    	
    }
    
    public void visit(ForStart fs) {
    	breakLevel++;
    	continueLevel++;
    }
    
    
    public void visit(StmtSwitch stmtSwitch) {
        //System.out.println("SWITCH nonterminal START");

    	Struct exprType = types.get(stmtSwitch.getExpr());
    	
    	
    	if (exprType != Tab.intType) {
    		reportError("Tip expr u switchu mora biti tipa int", stmtSwitch);
    	}
    	
    	if (!switchValues.isEmpty()) {
            switchValues.remove(switchValues.size() - 1);
        }
    	breakLevel--;
    }
    
    public void visit(SwitchStart ss) {
        //System.out.println("SWITCH START start");	
    	breakLevel++;
    	switchValues.add(new HashSet<Integer>());
    }
    
    public void visit(SwitchBodyList switchBodyList) {
    	
    }
    
    public void visit(CaseItem caseItem) {
    	if (switchValues.get(switchValues.size()-1).contains(caseItem.getCaseStart().getN1())) {
    		reportError("Vec postoji case sa ovim brojem!",caseItem);
    		return;
    	}
    	
    	switchValues.get(switchValues.size()-1).add(caseItem.getCaseStart().getN1());
    }
    
    @Override
    public void visit(StmtBreak stmtBreak) {
    	if (breakLevel <= 0) {
    		reportError("Break mora da se nadje unutar for-a ili switch-a", stmtBreak);
    	}
    }
    	
    
    public void visit(StmtContinue stmtContinue) {
    	if (continueLevel <= 0) {
    		reportError("Continue mora da se nadje unutar for-a", stmtContinue);
    	}
    }
    
    public void visit(StmtRead readStmt) {
        Obj obj = objects.get(readStmt.getDesignator());
        Struct type = obj.getType();

        if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem && obj.getKind() != Obj.Fld) {
            reportError("Naredba read se moze koristiti samo sa promenljivama, elementima niza ili poljima!", readStmt);
        } 
        
        if (type == Tab.intType || type == Tab.charType || type == Tab.find("bool").getType()) {
        	
        } else {
            reportError("Read podrzava samo int, char i bool!", readStmt);
        }
    }
    
    public void visit(StmtPrint stmtPrint) {
    	Struct type = types.get(stmtPrint.getExpr());
        if (type == Tab.intType || type == Tab.charType || type == Tab.find("bool").getType()) {
        }
        else
            reportError("Print podrzava samo int, char i bool!", stmtPrint);
    }
    
    public void visit(StmtReturn stmtReturn) {
        Obj currentMethod = savedObjects.get("currentMeth");
        if (currentMethod == null) {
            reportError("Return mora da se nadje unutar tijela funkcije.", stmtReturn);
            return;
        }

        if (stmtReturn.getOptExpr() instanceof NoReturnExpr) {
            if (currentMethod.getType() != Tab.noType) {
                reportError("Funkcija '" + currentMethod.getName() + "' nije void i mora vracati vrijednost!", stmtReturn);
            }
            return;
        }
        
        ReturnExpr retE = (ReturnExpr) stmtReturn.getOptExpr();
        Struct destType = currentMethod.getType();
        Obj srcObj = getOriginObj(retE.getExpr());
        Struct srcType = types.get(retE.getExpr());

        if (destType == Tab.noType) {
            reportError("Void metoda ne sme vracati vrijednost!", stmtReturn);
            return;
        }

        if (destType.getKind() == Struct.Enum) {
            int srcEnumId = (srcObj != Tab.noObj && srcObj.getKind() == Obj.Meth) ? 
                             enumReversePointers.getOrDefault(srcObj.getType(), -1) : 
                             (srcObj != Tab.noObj ? srcObj.getFpPos() : -1);

            if (srcEnumId == -1) {
                reportError("Funkcija mora vratiti Enum vrednost, a ne obican broj!", stmtReturn);
            } 
            else if (destType != enumPointers.get(srcEnumId)) {
                reportError("Povratni tip enuma se ne poklapa sa definicijom funkcije!", stmtReturn);
            }
        } 
        else {
            if (!srcType.assignableTo(destType)) {
                reportError("Povratni tip nije kompatibilan!", stmtReturn);
            }
        }    	
    }
    
    public void visit(SemiLine s) {
    	//reportInfo("ovde",s);
    }
        
    @Override 
    public void visit(DesignatorStatement designatorStatement) {
    	
    }
    
    public void visit(AssignOperation assignOp) {
        DesignatorStatement parent = (DesignatorStatement) assignOp.getParent();
        Obj dstObj = objects.get(parent.getDesignator());
        
        Obj srcObj = getOriginObj(assignOp.getExpr()); 
        Struct srcType = types.get(assignOp.getExpr());
        
        if (dstObj.getKind() != Obj.Var && dstObj.getKind() != Obj.Elem) {
            reportError("Vrednost se moze dodeliti samo promenljivoj ili elementu niza!", assignOp);
            return;
        }

        if (dstObj.getType().getKind() == Struct.Enum) {
            if (srcObj == Tab.noObj) {
                reportError("Enum varijabli se mora dodeliti Enum vrednost!", assignOp);
            } else {
                int srcEnumId = (srcObj.getKind() == Obj.Meth) ? 
                                 enumReversePointers.get(srcObj.getType()) : srcObj.getFpPos();
                
                if (dstObj.getFpPos() != srcEnumId) {
                    reportError("Nekompatibilni enumi u dodeli!", assignOp);
                }
            }
        } else {
            if (!srcType.assignableTo(dstObj.getType())) {
                reportError("Nekompatibilni tipovi!", assignOp);
            }
        }    }
    
    private Obj getOriginObj(Expr e) {
        if (e instanceof ArExp) {
            ArithExpr ae = ((ArExp)e).getArithExpr();
            if (ae instanceof TermExpr) {
                Term t = ((TermExpr)ae).getTerm();
                if (t instanceof TermItem) {
                    Factor f = ((TermItem)t).getFactor();
                    if (f instanceof FactDesignator) {
                        return objects.get(((FactDesignator)f).getDesignator());
                    }
                    if (f instanceof FactCall) {
                        return objects.get(f); // Ovo je onaj methObj koji smo gore sačuvali
                    }
                }
            }
        }
        return Tab.noObj;
    }
    
    @Override
    public void visit(IncOperation incOperation) {
        DesignatorStatement parent = (DesignatorStatement) incOperation.getParent();
        Obj obj = objects.get(parent.getDesignator());

        if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem && obj.getKind() != Obj.Fld) {
            reportError("++ se moze koristiti samo nad promenljivama!", incOperation);
        } 
        else if (obj.getType() != Tab.intType) {
            reportError("++ se moze koristiti samo nad int tipom!", incOperation);
        }
    }

    public void visit(DecOperation decOperation) {
        DesignatorStatement parent = (DesignatorStatement) decOperation.getParent();
        Obj obj = objects.get(parent.getDesignator());

        if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem && obj.getKind() != Obj.Fld) {
            reportError("-- se moze koristiti samo nad promenljivama!", decOperation);
        } else if (obj.getType() != Tab.intType) {
            reportError("-- se moze koristiti samo nad int tipom!", decOperation);
        }
    }
    
    @Override
    public void visit(CallOperation callOperation) {
        DesignatorStatement parent = (DesignatorStatement) callOperation.getParent();
        
        Obj methObj = objects.get(parent.getDesignator());
        Designator des = parent.getDesignator();
        String name = ((DesignatorIdent)parent.getDesignator()).getI1();
        
        if (methObj == null || methObj.getKind() != Obj.Meth) {
            reportError("Ne postoji metoda sa nazivom " + name , des);
        	return;
        }

        checkParameters(methObj, callOperation.getOptActPars(), callOperation);
    }
    
    private void checkParameters(Obj methObj, OptActPars oap, SyntaxNode node) {
        List<Expr> actualExprs = new ArrayList<>();
        if (oap instanceof OptionalActPars) {
            collectActPars(((OptionalActPars)oap).getActPars(), actualExprs);
        }

        int formalParamCount = methObj.getLevel();
        if (actualExprs.size() != formalParamCount) {
            reportError("Metoda " + methObj.getName() + " ocekuje " + formalParamCount + " parametara!", node);
            return;
        }

        Iterator<Obj> formalIt = methObj.getLocalSymbols().iterator();
        for (int i = 0; i < actualExprs.size(); i++) {
            Obj formalParam = formalIt.next();
            Expr actualExpr = actualExprs.get(i);
            
            Struct formalType = formalParam.getType();
            Struct actualType = types.get(actualExpr);
            Obj srcObj = getOriginObj(actualExpr);

            if (formalType.getKind() == Struct.Enum) {
                // Izvlacimo ID enuma (fpPos za var/const, ili preko mape za povratni tip fje)
                int srcEnumId = (srcObj != Tab.noObj && srcObj.getKind() == Obj.Meth) ? 
                                 enumReversePointers.getOrDefault(srcObj.getType(), -1) : 
                                 (srcObj != Tab.noObj ? srcObj.getFpPos() : -1);

                if (srcEnumId == -1) {
                    reportError("Parametar na poziciji " + (i + 1) + " mora biti Enum (konstanta, varijabla ili poziv fje)!", node);
                } 
                else if (formalType != enumPointers.get(srcEnumId)) {
                    reportError("Parametar na poziciji " + (i + 1) + " nije odgovarajuci Enum tip!", node);
                }
            } 
            else {
                if (!actualType.assignableTo(formalType)) {
                    reportError("Parametar na poziciji " + (i + 1) + " nije kompatibilan!", node);
                }
            }
        }
    }
    
    private void collectActPars(ActPars actPars, List<Expr> exprsList) {
        if (actPars instanceof ActParsList) {
            ActParsList list = (ActParsList) actPars;
            collectActPars(list.getActPars(), exprsList); 
            exprsList.add(list.getExpr());     
        } else if (actPars instanceof ActParItem) {
            exprsList.add(((ActParItem)actPars).getExpr()); 
        }
    }    
    
    public void visit(OptionalActPars optionalActPars) {
    	
    }
    
    public void visit(ActParsList actParsList) {
    	types.put(actParsList, types.get(actParsList.getExpr()));
    	parametersToCheck++;
    }

    public void visit(ActParItem actParItem) {
    	types.put(actParItem, types.get(actParItem.getExpr()));
    	parametersToCheck++;
    }
   
    public void visit(DesignatorIdent designatorIdent) {
        Obj obj = Tab.find(designatorIdent.getI1());
        
        if (obj == Tab.noObj) {
            reportError("Ime " + designatorIdent.getI1() + " nije deklarisano!", designatorIdent);
        } else {
            switch (obj.getKind()) {
                case Obj.Var:
                    break;
                case Obj.Con:
                    break;
                case Obj.Meth:
                    break;
                default:
                    reportError(designatorIdent.getI1() + " se ne moze koristiti kao promenljiva!", designatorIdent);
            }
        }

        objects.put(designatorIdent, obj);
    }

    public void visit(DesignatorArray designatorArray) {
        Obj obj = Tab.find(designatorArray.getI1());
        
        Obj resultObj = Tab.noObj; 
        
        arrayObj.put(designatorArray, obj);

        if (obj == Tab.noObj) {
            reportError("Ime " + designatorArray.getI1() + " nije deklarisano!", designatorArray);
        } else if (obj.getType().getKind() != Struct.Array) {
            reportError(designatorArray.getI1() + " nije niz!", designatorArray);
        } else {

            Struct indexType = types.get(designatorArray.getExpr());
            if (indexType != null && !indexType.equals(Tab.intType)) {
                reportError("Indeks niza mora biti tipa int!", designatorArray);
            }
            
            resultObj = new Obj(Obj.Elem, "elem", obj.getType().getElemType());
            
            if (obj.getType().getElemType().getKind() == Struct.Enum) {
                resultObj.setFpPos(obj.getFpPos());
            }
        }
        
        objects.put(designatorArray, resultObj);
    }
    
    public void visit(DesignatorDotIdent designatorDotIdent) {
        String typeName = designatorDotIdent.getI1(); 
        String constantName = designatorDotIdent.getI2();
        
        Obj typeObj = Tab.find(typeName);
        
        if (typeObj == Tab.noObj) {
            reportError("Promjenjiva " + typeName + " nije deklarisana!", designatorDotIdent);
            objects.put(designatorDotIdent, Tab.noObj);
            return;
        }
        
        if (typeObj.getKind() != Obj.Type || typeObj.getType().getKind() != Struct.Enum) {
            reportError(typeName + " nije ENUM tip!", designatorDotIdent);
            objects.put(designatorDotIdent, Tab.noObj);
            return;
        }

        Obj constantObj = Tab.noObj;

        for (Obj m : typeObj.getType().getMembers()) {
            if (m.getName().equals(constantName)) {
            	constantObj = m;
                break;
            }
        }

        if (constantObj == Tab.noObj) {
            reportError("Enum " + typeName + " nema konstantu " + constantName, designatorDotIdent);
            objects.put(designatorDotIdent, Tab.noObj);
        } else {
        	//reportInfo("evonas",designatorDotIdent);
            objects.put(designatorDotIdent, constantObj);
        }
    }

    public void visit(DesignatorLength designatorLength) {
        Obj obj = Tab.find(designatorLength.getI1());
        Struct resType = Tab.intType; 
        
        if (obj == Tab.noObj) {
            reportError("Ime" + designatorLength.getI1() + " nije deklarisano", designatorLength);
            resType = Tab.noType;
        } else if (obj.getType().getKind() != Struct.Array) {
            reportError("Samo nizovi imaju length polje", designatorLength);
            resType = Tab.noType;
        }
        
        arrayObj.put(designatorLength, obj);
        objects.put(designatorLength, new Obj(Obj.Con, "length", resType));
    }
    
    public void visit(ArExp arExp) {
        // Običan aritmetički izraz
    	types.put(arExp,types.get(arExp.getArithExpr()));
    }

    public void visit(ConditionExpr conditionExpr) {
        // Izraz koji sadrži ternarni operator
    	types.put(conditionExpr,types.get(conditionExpr.getCondExpr()));
    }
    
    public void visit(TernaryExpr ternaryExpr) {
        // Logika za: Condition ? Expr : Expr
    	if (types.get(ternaryExpr.getCondition()) != boolType) {
            reportError("Uslov u ternarnom operatoru mora biti tipa bool!", ternaryExpr);
            types.put(ternaryExpr, Tab.noType);
            return;
    	}
    	
    	Struct t1 = types.get(ternaryExpr.getExpr());
        Struct t2 = types.get(ternaryExpr.getExpr1());    	 
        
        if (t1.compatibleWith(t2)) {
            types.put(ternaryExpr, t1);
        } else {
            reportError("Tipovi grana ternarnog operatora nisu kompatibilni!", ternaryExpr);
            types.put(ternaryExpr, Tab.noType);
        }
        
        
    }
       
    public void visit(AddExpr addExpr) {
        // Sabiranje/Oduzimanje: ArithExpr Addop Term
    	Struct left = types.get(addExpr.getArithExpr());
        Struct right = types.get(addExpr.getTerm());    
        
        if (left == Tab.intType && right == Tab.intType) {
            types.put(addExpr, Tab.intType);
        } else {
            reportError("Operandi kod sabiranja/oduzimanja moraju biti tipa int!", addExpr);
            types.put(addExpr, Tab.noType);
        }
    }
    
    public void visit(ReturnExpr returnExpr) {
        // Za return sa izrazom
    }

    public void visit(NoReturnExpr noReturnExpr) {
        // Za prazan return;
    }

    public void visit(TermExpr termExpr) {
        types.put(termExpr, types.get(termExpr.getTerm()));
    }
    
    public void visit(CondItem item) {
        types.put(item, types.get(item.getCondTerm()));
    }

    public void visit(ConditionList list) {
        Struct left = types.get(list.getCondition());
        Struct right = types.get(list.getCondTerm());
        
        if (left == Tab.noType || right == Tab.noType) {
            types.put(list, Tab.noType);
        } else {
            types.put(list, boolType);
        }
    }
    
    
    public void visit(CondTermItem item) {
        types.put(item, types.get(item.getCondFact()));
    }

    public void visit(CondTermList list) {
        Struct left = types.get(list.getCondTerm());
        Struct right = types.get(list.getCondFact());
        
        if (left == Tab.noType || right == Tab.noType) {
            types.put(list, Tab.noType);
        } else {
            types.put(list, boolType);
        }
    }
    
    public void visit(RelationalOp relationalOp) {
        Struct t1 = types.get(relationalOp.getArithExpr());
        Struct t2 = types.get(relationalOp.getArithExpr1()); 

        if (t1 == Tab.noType || t2 == Tab.noType) {
            types.put(relationalOp, Tab.noType);
            return;
        }

        boolean error = false;
        if (!t1.compatibleWith(t2)) {
            reportError("Tipovi u relacionom izrazu nisu kompatibilni!", relationalOp);
            error = true;
        } else if (t1.isRefType() || t2.isRefType()) {
            Relop op = relationalOp.getRelop();
            if (!(op instanceof RelEq) && !(op instanceof RelNe)) {
                reportError("Uz nizove/klase mogu se koristiti samo == ili !=", relationalOp);
                error = true;
            }
        }

        types.put(relationalOp, error ? Tab.noType : boolType);
    }
    
    public void visit(BooleanArith booleanArith) {
        Struct t = types.get(booleanArith.getArithExpr());
        
        if (t == Tab.noType) {
            types.put(booleanArith, Tab.noType);
            return;
        }

        if (t != boolType) {
            reportError("Uslov mora biti tipa bool!", booleanArith);
            types.put(booleanArith, Tab.noType);
        } else {
            types.put(booleanArith, boolType);
        }
    }
    
    public void visit(TermItem termItem) {
        types.put(termItem, types.get(termItem.getFactor()));
    }

    public void visit(TermList termList) {
        Struct termType = types.get(termList.getTerm());
        Struct factorType = types.get(termList.getFactor());
        
        if (termType == Tab.intType && factorType == Tab.intType) {
            types.put(termList, Tab.intType);
        } else {
            reportError("Operandi u mnozenju/deljenju/modovanju moraju biti tipa int!", termList);
            types.put(termList, Tab.noType);
        }
    }

    public void visit(FactConst factConst) {
        // konstatne (odtadjeno vec)
    	types.put(factConst, types.get(factConst.getConstant()));
    }

    public void visit(FactMinus factMinus) {
        Struct t = types.get(factMinus.getFactor());
        if (!t.equals(Tab.intType)) {
            reportError("Unarni minus se moze koristiti samo uz tip int!", factMinus);
            types.put(factMinus, Tab.noType);
        } else {
            types.put(factMinus, Tab.intType);
        }
    }

    public void visit(FactDesignator factDesignator) {
        // Upotreba promenljive/konstante/člana enuma u izrazu
    	if (objects.get(factDesignator.getDesignator()).getKind() == Obj.Meth) {
            reportError(objects.get(factDesignator.getDesignator()).getName() + " je metoda i ne moze se koristiti bez poziva!", factDesignator);
            types.put(factDesignator, Tab.noType);
            return;
        } 
    	if (objects.get(factDesignator.getDesignator()).getType().getKind() == Struct.Enum) {
    		types.put(factDesignator, Tab.intType); 
    	}
    	else {
            types.put(factDesignator, objects.get(factDesignator.getDesignator()).getType());
        }
    }

    public void visit(FactCall factCall) {
        Obj methObj = objects.get(factCall.getDesignator());
        
        if (methObj == null || methObj.getKind() != Obj.Meth) {
            reportError("Pokusaj poziva necega sto nije metoda!", factCall);
            types.put(factCall, Tab.noType);
            return;
        }
        
        if (methObj.getType().equals(Tab.noType)) { 
            reportError("Funkcija " + methObj.getName() + " ne vraca vrednost i ne moze se koristiti u izrazu!", factCall);
        }
        
        checkParameters(methObj, factCall.getOptActPars(), factCall);
        
        System.out.println(methObj.getName() + methObj.getLevel());

        objects.put(factCall, methObj);

        Struct typee = methObj.getType();
        
        if (objects.get(factCall.getDesignator()).getType().getKind() == Struct.Enum) {
    		typee = Tab.intType; 
        }
        
        types.put(factCall, typee);    
    }
    
    public void visit(FactNew factNew) {
    	//pravljenje niza sa new
    	if (types.get(factNew.getExpr()) != Tab.intType) {
    		types.put(factNew, Tab.noType);
    		reportError("Velicina niza mora biti tipa int",factNew);
    		return;
    	}
    	
    	//System.out.println("ovo je cureent type " + currentType.getKind());
    	
    	types.put(factNew, new Struct(Struct.Array, currentType)); // ?????
    }

    
    public void visit(FactParen factParen) {
        // Izraz u zagradama: (Expr)
    	types.put(factParen, types.get(factParen.getExpr()));
    }
   
    @Override
    public void visit(Type type) {
    	if (Tab.find(type.getI1()) == Tab.noObj) {
    		reportError("Ne postoji zadati tip podataka: " + type.getI1(), type);
    		currentType = Tab.noType;
    	}
    	else if (Tab.find(type.getI1()).getKind() != Obj.Type) {
    		reportError("Ne postoji zadati tip podataka: " + type.getI1(), type);
    		currentType = Tab.noType;
    	}
    	else {
    		currentType = Tab.find(type.getI1()).getType();
    	}
    	types.put(type, currentType);
    }
    
    @Override
    public void visit(NumConst c) {
    	//reportInfo("ovdenc" ,c);
        currentConstant = c.getN1();
        lastConstType = Tab.intType;
        types.put(c, Tab.intType); // Za kasniju propagaciju u izrazima
    }

    @Override
    public void visit(BoolConst c) {
        currentConstant = c.getB1();
        // Pretraga za bool jer on nije ugrađen u osnovni Tab kao int i char
        lastConstType = Tab.find("bool").getType(); 
        types.put(c, Tab.find("bool").getType()); // Za kasniju propagaciju u izrazima

    }

    @Override
    public void visit(CharConst c) {
        currentConstant = c.getC1();
        lastConstType = Tab.charType;
        types.put(c, Tab.charType); // Za kasniju propagaciju u izrazima
    }
    
   
    
    public void reportError(String message, SyntaxNode node) {
        error = true;

        String msg = new String("Greska( ");
        
        msg += message;

        if (node != null) {
            msg += (" [na liniji ");
            msg += node.getLine();
            msg += "]";
        }
        
        msg += ")";
        
        log.error(msg.toString());
    }

    public void reportInfo(String message, SyntaxNode node) {
        String msg = new String("Info( ");
        msg += message;

        if (node != null) {
            msg += " [na liniji ";
			msg += node.getLine();
			msg += "]";
        }

        msg += ")";
        
        log.info(msg.toString());
    }
    
    private boolean checkCompatibility(Struct destType, Obj srcObj) {
        if (destType == Tab.intType) {
            return srcObj.getType().assignableTo(Tab.intType);
        }

        if (destType.getKind() == Struct.Enum) {
            return srcObj.getType() == destType;
        }

        return srcObj.getType().assignableTo(destType);
    }
}
