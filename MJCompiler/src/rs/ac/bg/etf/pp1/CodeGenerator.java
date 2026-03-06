package rs.ac.bg.etf.pp1;

import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

    private Map<SyntaxNode, Obj> objects;
    private Map<SyntaxNode, Struct> types;
    private Map<SyntaxNode, Obj> arrayObjects;
    
    Stack<Integer> ifFixupStack = new Stack<>();
    Stack<Integer> caseFixupStack = new Stack<>();
    Stack<Integer> breakStack = new Stack<>();
    Stack<Integer> breakStackCounter = new Stack<>();
    Stack<Integer> forLoopStartStack = new Stack<>();
    Stack<Integer> forLoopExitStack = new Stack<>();
    Stack<Integer> jumpToBodyStack = new Stack<>();
    Stack<Integer> forIncrementStartStack = new Stack<>();
    Stack<Integer> ternaryStackJumpOver1 = new Stack<>();
    Stack<Integer> ternaryStackJumpOver2 = new Stack<>();
    Stack<Integer> continueStack = new Stack<>();
    Stack<Integer> switchDepth = new Stack<>();
    Stack<Integer> switchesToPop = new Stack<>();  // Novi stek
    private int switchNestingBeforeMethod = 0;
    Stack<Integer> trueJumpStack = new Stack<>();
    Stack<Integer> falseJumpStack = new Stack<>();
    private int maxAddr = -1; // <--- DODAJ OVU LINIJU
    private Stack<Integer> fallThroughStack = new Stack<>();
    
    int switchValue = 0;
    
    public int mainPc = 0;
    public int nVars;

    public CodeGenerator(Map<SyntaxNode, Obj> objects, Map<SyntaxNode, Struct> types,Map<SyntaxNode, Obj> obj2) {
        this.objects = objects;
        this.types = types;
        this.arrayObjects = obj2;
    }

  
    @Override
    public void visit(MethodName methodName) {
        /*
    	Code.put(Code.jmp);
        int patchMain = Code.pc; // Čuvamo poziciju za fixup
        Code.put2(0); 

        maxAddr = Code.pc; 

        Code.put(Code.enter); Code.put(1); Code.put(3); // Adresa 3
        
        Code.loadConst(0);
        Code.put(Code.load_n + 0);
        Code.put(Code.dup_x1); Code.put(Code.pop);
        Code.put(Code.aload);
        Code.put(Code.store_n + 2);

        Code.loadConst(1);
        Code.put(Code.store_n + 1);

        Code.put(Code.load_n + 1);
        Code.put(Code.load_n + 0);
        Code.put(Code.arraylength);
        
        Code.put(Code.jcc + Code.ge); Code.put2(7); 
        
        Code.loadConst(1);
        Code.put(Code.jmp); Code.put2(4);
        
        Code.loadConst(0);
        
        Code.loadConst(1);
        
        Code.put(Code.jcc + Code.ne); Code.put2(43); 

        Code.put(Code.jmp); Code.put2(10);

        Code.put(Code.load_n + 1);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.put(Code.store_n + 1);
        
        Code.put(Code.jmp); Code.put2(-22);

        Code.put(Code.load_n + 1);
        Code.put(Code.load_n + 0);
        Code.put(Code.dup_x1); Code.put(Code.pop);
        Code.put(Code.aload);
        Code.put(Code.load_n + 2);
        
        Code.put(Code.jcc + Code.le); Code.put2(7);

        Code.loadConst(1);
        Code.put(Code.jmp); Code.put2(4);

        Code.loadConst(0);

        Code.loadConst(1);
        Code.put(Code.jcc + Code.ne); Code.put2(12);

        Code.put(Code.load_n + 1);
        Code.put(Code.load_n + 0);
        Code.put(Code.dup_x1); Code.put(Code.pop);
        Code.put(Code.aload);
        Code.put(Code.store_n + 2);

        Code.put(Code.jmp); Code.put2(-34);

        Code.put(Code.load_n + 2);
        Code.put(Code.exit);
        Code.put(Code.return_);
        */
        Obj methObj = objects.get(methodName);
        
        if (methodName.getI1().equals("main")) {
            this.mainPc = Code.pc;
            //Code.fixup(patchMain); !!!!!
        }
        
        methObj.setAdr(Code.pc);
        
        Code.put(Code.enter);
        Code.put(methObj.getLevel());
        Code.put(methObj.getLocalSymbols().size());
    }
    
    @Override
    public void visit(MethodDecl methodDecl) {
    	Obj methObj = objects.get(methodDecl.getMethodName());
    	
    	if (methObj.getType() != Tab.noType) {
            
            String msg = "Greska: Nedostaje return iskaz u telu funkcije.";
            
            for (int i = 0; i < msg.length(); i++) {
                Code.loadConst(msg.charAt(i));
                
                Code.loadConst(0);
                
                Code.put(Code.bprint);
            }
            
            //nobi rea
            Code.loadConst('\n');
            Code.loadConst(0);
            Code.put(Code.bprint);

            Code.put(Code.trap);
            Code.put(1);
        }	    	
        Code.put(Code.exit);
        Code.put(Code.return_);
    }
    

    public void visit(StmtPrint stmtPrint) {
        if (types.get(stmtPrint.getExpr()) == Tab.charType) {
            Code.put(Code.bprint);
        } else {
            Code.put(Code.print);
        }
    }
    
    public void visit(WidthValue widthValue) {
    	Code.loadConst(widthValue.getN1()); 
    }

    public void visit(NoWidth noWidth) {
        Code.loadConst(0); 
    }
    
    public void visit(IfConditionEnd ifCondEnd) {
    	pushConditionValue();
    	Code.loadConst(1);
        
        Code.putFalseJump(Code.eq, 0); 
        ifFixupStack.push(Code.pc - 2);
    }
    
    public void visit(IfEnded ifEnded) {
        Code.putJump(0);
        
        int adrToPatch = ifFixupStack.pop();
        Code.fixup(adrToPatch);
        
        ifFixupStack.push(Code.pc - 2);
    }    
    
    public void visit(StmtIf stmtIf) {
    	Code.fixup(ifFixupStack.pop());
    }
    
    public void visit(StmtContinue stmtContinue) {
        // skida switch vr unutar ovog posljednjeg fora
        if (!switchesToPop.isEmpty()) {
            int popsNeeded = switchesToPop.peek(); 
            for (int i = 0; i < popsNeeded; i++) {
                Code.put(Code.pop);
            }
        }
        
        // najugnjedzeniji for
        Code.putJump(continueStack.peek());
    }    
    
    public void visit(StmtBreak stmtBreak) {
        Code.putJump(0);
        breakStack.push(Code.pc - 2);
        
        int currentCount = breakStackCounter.pop();
        breakStackCounter.push(currentCount + 1);   
    }

    public void visit(ForStart forStart) {
        breakStackCounter.push(0);
//        continueStack.push(0);
        switchesToPop.push(0);  // For počinje sa 0 switch-eva iznad sebe
    }

    
    public void visit(FirstSemiFor firstSemi) {
        forLoopStartStack.push(Code.pc);
    }

    public void visit(OptCond optCond) {
    	pushConditionValue(); // Pretvara skokove u 0 ili 1
        Code.loadConst(1);
        Code.putFalseJump(Code.eq, 0);
        forLoopExitStack.push(Code.pc - 2);
        
        Code.putJump(0);
        jumpToBodyStack.push(Code.pc - 2);  
        
        forIncrementStartStack.push(Code.pc);  
        continueStack.push(Code.pc);
    }

    public void visit(NoOptCond noOptCond) {
        forLoopExitStack.push(-1);
        
        Code.putJump(0);
        jumpToBodyStack.push(Code.pc - 2);  
        
        forIncrementStartStack.push(Code.pc);  
        continueStack.push(Code.pc);
    }
    
    public void visit(ForBracketEnd forBracketEnd) {
        Code.putJump(forLoopStartStack.pop());
        Code.fixup(jumpToBodyStack.pop());  
    }

    public void visit(StmtFor stmtFor) {
        Code.putJump(forIncrementStartStack.pop()); 
        continueStack.pop();
        switchesToPop.pop();  // Ukloni brojač za ovu petlju
        
        int exitAddr = forLoopExitStack.pop();
        
        if (exitAddr != -1) {
            Code.fixup(exitAddr);
        }	        
        
        int cnt = breakStackCounter.pop();
        
        while (cnt > 0) {
            Code.fixup(breakStack.pop());
            cnt--;
        }
    }
    
    public void visit(CaseStart caseStart) {
        if (!caseFixupStack.isEmpty() && caseFixupStack.peek() != -1) {
            Code.fixup(caseFixupStack.pop());
        }

        Code.put(Code.dup);
        Code.put(Code.const_);
        Code.put4(caseStart.getN1());
        Code.putFalseJump(Code.eq, 0); 
        caseFixupStack.push(Code.pc - 2);

        if (!fallThroughStack.isEmpty() && fallThroughStack.peek() != -1) {
            Code.fixup(fallThroughStack.pop());
        }
    }    
    public void visit(SwitchStart switchStart) {
        breakStackCounter.push(0);
        caseFixupStack.push(-1);    // Marker za pocetak switch-a
        fallThroughStack.push(-1);  // Marker za pocetak switch-a
        
        // ... tvoj postojeci kod za depth i switchesToPop
        if (switchDepth.isEmpty()) switchDepth.push(1);
        else switchDepth.push(switchDepth.peek() + 1);

        if (!switchesToPop.isEmpty()) {
            int current = switchesToPop.pop();
            switchesToPop.push(current + 1);
        }
    }
    
    public void visit(JumpOverJump joj) {
        Code.putJump(0);
        fallThroughStack.push(Code.pc - 2);
    }

    public void visit(StmtSwitch stmtSwitch) {
        // 1. Zakrpi skokove koji su išli na kraj poslednjeg case-a
    	if (!caseFixupStack.isEmpty() && caseFixupStack.peek() != -1) 
            Code.fixup(caseFixupStack.pop());
        if (!caseFixupStack.isEmpty()) 
            caseFixupStack.pop(); // Skini marker (-1)

        if (!fallThroughStack.isEmpty() && fallThroughStack.peek() != -1) 
            Code.fixup(fallThroughStack.pop());
        if (!fallThroughStack.isEmpty()) 
            fallThroughStack.pop(); // Skini marker (-1)
        	
        // fixup za breakove
        int cnt = breakStackCounter.pop();
        while (cnt > 0) {
            Code.fixup(breakStack.pop()); 
            cnt--;
        }

        // ovaj pop ide posle da bi ppovali breakovi ovo iz swithca
        Code.put(Code.pop);

        switchDepth.pop();
        if (!switchesToPop.isEmpty()) {
            int current = switchesToPop.pop();
            switchesToPop.push(current - 1);
        }
    }    
    
    public void visit(RelationalOp relOp) {
        int relOpCode = getRelopCode(relOp.getRelop());
        // Ako uslov nije ispunjen, skačemo na FALSE granu
        Code.putFalseJump(relOpCode, 0);
        falseJumpStack.push(Code.pc - 2);
    }

    public void visit(BooleanArith boolArith) {
        // skaci ako je false
        Code.loadConst(1);
        Code.putFalseJump(Code.eq, 0);
        falseJumpStack.push(Code.pc - 2);
    }

    public void visit(LeftOr leftOr) {
        // Ako je levi deo bio TRUE, odmah imamo uspeh celog Condition-a
        Code.putJump(0);
        trueJumpStack.push(Code.pc - 2);

        // One koji su do sada bili FALSE, krpimo da dođu ovde i probaju desni deo OR-a
        while (!falseJumpStack.isEmpty()) {
            Code.fixup(falseJumpStack.pop());
        }
    }
    
    public void visit(LeftAnd leftAnd) {
        // Ako je lijeva strana FALSE, cijeli AND term je false
        // false skokovi ostaju u falseJumpStack i čekaju
        
        // Oni koji su TRUE nastavljaju dalje (ne radimo ništa)
        // Ali true skokove trebamo fixupati da dođu ovdje:
        while (!trueJumpStack.isEmpty()) {
            Code.fixup(trueJumpStack.pop());
        }
        // falseJumpStack ostaje kako jeste - čeka kraj uslova
    }
    
    private void pushConditionValue() {
        // svi koji su true
        while (!trueJumpStack.isEmpty()) {
            Code.fixup(trueJumpStack.pop());
        }
        
        Code.loadConst(1); // Stavi 1 na stek
        Code.putJump(Code.pc + 4); // Preskoči granu za nulu
        int adrToPatch = Code.pc - 2;

        //svi koji su false
        while (!falseJumpStack.isEmpty()) {
            Code.fixup(falseJumpStack.pop());
        }
        //Code.put(Code.const_);Code.put4(3);
        Code.loadConst(0); // o za false

        Code.fixup(adrToPatch); 
    }

    public void visit(ConditionList conditionList) { /* prazno */ }
    public void visit(CondTermList condTermList) { /* prazno */ }
    
    public void visit(StmtReturn stmtReturn) {
    	Code.put(Code.exit);
    	Code.put(Code.return_);
    }
    public void visit(ReturnStart returnStart) {
    	int currentSwitchNesting = switchDepth.size() - switchNestingBeforeMethod;
        
        for (int i = 0; i < currentSwitchNesting; i++) {
            Code.put(Code.pop);
        }
    }
    
    
    public void visit(StmtRead StmtRead) {
    	Obj des = objects.get(StmtRead.getDesignator());
    	if (des.getType().equals(Tab.charType)) {
    		Code.put(Code.bread);
    		Code.store(des);
    		return;
    	}
    	Code.put(Code.read);
		Code.store(des);
    }
    
    public void visit(CallOperation callOperation) {
        DesignatorStatement parent = (DesignatorStatement) callOperation.getParent();
        Obj des = objects.get(parent.getDesignator());
        
        System.out.println(des.getAdr() - (Code.pc -1));
        
    	Code.put(Code.call);
    	Code.put2(des.getAdr() - (Code.pc -1));
    	
    	if (des.getType() != Tab.noType) {
            Code.put(Code.pop);
        }
    }
    
    
    
    public void visit(AssignOperation assignOperation) {
        DesignatorStatement parent = (DesignatorStatement) assignOperation.getParent();
        
        Obj destObj = objects.get(parent.getDesignator());
        
        Code.store(destObj);
    }    
    
    public void visit(IncOperation incOperation) {
        DesignatorStatement parent = (DesignatorStatement) incOperation.getParent();
        Obj obj = objects.get(parent.getDesignator());

        if (obj.getKind() == Obj.Elem) {
            Code.put(Code.dup2); 
        }

        Code.load(obj); 
        
        Code.loadConst(1);
        Code.put(Code.add);
        
        Code.store(obj);
    }
    
    public void visit(DecOperation decOperation) {
        DesignatorStatement parent = (DesignatorStatement) decOperation.getParent();
        Obj obj = objects.get(parent.getDesignator());

        if (obj.getKind() == Obj.Elem) {
            Code.put(Code.dup2); 
        }

        Code.load(obj); 
        
        Code.loadConst(1);
        Code.put(Code.sub);
        
        Code.store(obj);
    }
    
    public void visit(TernaryQuestion ternaryQuestion) {
    	pushConditionValue(); // Pretvara skokove u 0 ili 1
    	Code.loadConst(1);
    	Code.putFalseJump(Code.eq, 0);
    	ternaryStackJumpOver1.push(Code.pc-2);
    }
    
    public void visit(SecondExpr secondExpr) {
    	Code.putJump(0);
    	ternaryStackJumpOver2.push(Code.pc-2);
    	
    	Code.fixup(ternaryStackJumpOver1.pop());
    }


    public void visit(TernaryExpr TernaryExpr) {
    	Code.fixup(ternaryStackJumpOver2.pop());
    }
    
	public void visit(AddExpr addExpr) {
		if (addExpr.getAddop() instanceof AddPlus) {
			Code.put(Code.add);
			return;
		}
		if (addExpr.getAddop() instanceof AddMinus) {
			Code.put(Code.sub);
			return;	
		}
	}
	
	public void visit(TermList termList) {
		if (termList.getMulop() instanceof MulMultiply) {
			Code.put(Code.mul);
			return;
		}
		if (termList.getMulop() instanceof MulDivide) {
			Code.put(Code.div);
			return;
		}
		if (termList.getMulop() instanceof MulMod) {
			Code.put(Code.rem);
			return;
		}
	}
    
	public void visit(FactDesignator factDesignator) {
	    if (factDesignator.getDesignator() instanceof DesignatorLength) {
	        return;
	    }
	    
	    Code.load(objects.get(factDesignator.getDesignator()));
	}    
    public void visit(FactNew factNew) {
    	Struct arrayType = types.get(factNew.getType());
    	
    	Code.put(Code.newarray);
    	
    	if (arrayType.equals(Tab.charType)) {
    		Code.put(0);
    	}
    	else {
    		Code.put(1);
    	}
    	
    }
    
    public void visit(FactMinus factMinus) {
        Code.put(Code.neg);
    }
    
    public void visit(FactCall factCall) {
        Obj des = objects.get(factCall.getDesignator());
        
        if (des.getName().equals("ord") || des.getName().equals("chr")) {
            return;
        }
        
        if (des.getName().equals("len")) {
            Code.put(Code.arraylength);
            return; 
        }
        
        System.out.println(des.getAdr() - (Code.pc -1));
        
    	Code.put(Code.call);
    	Code.put2(des.getAdr() - (Code.pc -1));
    }
    
    public void visit(DesignatorIdent designatorIdent) {
    }
    
    public void visit(DesignatorDotIdent designatorDotIdent) {
    	
    }

    public void visit(DesignatorArray designatorArray) {
    	Obj arrayObj = arrayObjects.get(designatorArray);
    	
        Code.load(arrayObj);
        
        Code.put(Code.dup_x1); 
        Code.put(Code.pop);
    }
    
    public void visit(DesignatorLength designatorLength) {
        Obj arrayObj = arrayObjects.get(designatorLength);
        System.out.println("duzina niza" + arrayObj.getName());
        Code.load(arrayObj);
        Code.put(Code.arraylength);
		/*
    	Obj arrayObj = arrayObjects.get(designatorLength);
        Code.load(arrayObj); // Stavlja adresu niza na stek
        
        // Code.put(Code.arraylength); <-- OVO ZAKOMENTARIŠI
        
        // DODAJ OVO:
        Code.put(Code.call);
        Code.put2(maxAddr - (Code.pc - 1)); 
    	*/
    }
    
    public void visit(NumConst numConst) {
    	Code.loadConst(numConst.getN1());
    }
    public void visit(CharConst charConst) {
    	Code.loadConst(charConst.getC1());
    }
    public void visit(BoolConst boolConst) {
    	Code.loadConst(boolConst.getB1());
    }
    
    private int getRelopCode(Relop relop) {
        if (relop instanceof RelEq) return Code.eq;
        if (relop instanceof RelNe) return Code.ne;
        if (relop instanceof RelLt) return Code.lt;
        if (relop instanceof RelLe) return Code.le;
        if (relop instanceof RelGt) return Code.gt;
        if (relop instanceof RelGe) return Code.ge;
        return Code.eq; 
    } 
}