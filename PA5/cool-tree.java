// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////



import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;
import java.util.*;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();
    public abstract void cgen(PrintStream s);

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract AbstractSymbol getParent();
    public abstract AbstractSymbol getFilename();
    public abstract Features getFeatures();

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }
    // public abstract void code(PrintStream s);

    public abstract void code(PrintStream s, CgenNode current_node, CgenClassTable class_table);

}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
    public void semant() {
	/* ClassTable constructor may do some semantic analysis */
	ClassTable classTable = new ClassTable(classes);
	
	/* some semantic analysis code may go here */

	if (classTable.errors()) {
	    System.err.println("Compilation halted due to static semantic errors.");
	    System.exit(1);
	}
    }
    /** This method is the entry point to the code generator.  All of the work
      * of the code generator takes place within CgenClassTable constructor.
      * @param s the output stream 
      * @see CgenClassTable
      * */
    public void cgen(PrintStream s) {
	CgenClassTable codegen_classtable = new CgenClassTable(classes, s);
    }

}


/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public AbstractSymbol getFilename() { return filename; }
    public Features getFeatures()       { return features; }

}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
	expr.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
	expr.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // expr.code()
        // [*] = acc
        expr.code(s, current_node, class_table);
        ObjectInfo info = (ObjectInfo) current_node.object_table.lookup(name);
        if(info.obj_type == ObjectInfo.OBJTYPE.LOCAL) {
            CgenSupport.emitStore(CgenSupport.ACC, -1 - info.offset, CgenSupport.FP, s);
            return;
        }
        if(info.obj_type == ObjectInfo.OBJTYPE.FORMAL) {
            //sw	$a0 16($fp)
            // should plus 2 here because the offset of formal is 1-based
            CgenSupport.emitStore(CgenSupport.ACC, 2 + info.offset, CgenSupport.FP, s);
            return;
        }
        if(info.obj_type == ObjectInfo.OBJTYPE.ATTR) {
            //sw	$a0 12($s0)
            CgenSupport.emitStore(CgenSupport.ACC, 3 + info.offset, CgenSupport.SELF, s);
            //handle garbage collector for attr
            if(Flags.cgen_Memmgr != Flags.GC_NOGC) {
                //addiu   $a1 $s0 12
                //jal     _GenGC_Assign
                CgenSupport.emitAddiu(CgenSupport.A1, CgenSupport.SELF, (3 + info.offset) * CgenSupport.WORD_SIZE, s);
                CgenSupport.emitJal(CgenSupport.GENGC_ASSIGN, s);
            }
        }
    }


}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // push formals into stack
//        la	$a0 int_const0
//        sw	$a0 0($sp)
//        addiu	$sp $sp -4
        for(int i = 0; i < actual.getLength(); i++) {
            Expression e = (Expression)actual.getNth(i);
            e.code(s, current_node, class_table);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            class_table.getLocalVariableCount();
        }

        // dispatch from expr
//        lw	$a0 12($s0)
//        bne	$a0 $zero label0
//        la	$a0 str_const0
//        li	$t1 26
//        jal	_dispatch_abort
//        label0:
//        lw	$t1 8($a0)
//        lw	$t1 12($t1)
//        jalr		$t1
        expr.code(s, current_node, class_table);
        int label = CgenClassTable.getCurrentLabel();
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, label, s);
        // error handling _dispatch_abort
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        StringSymbol.codeRefByString(current_node.getFilename().toString(), s);
        s.println();
        CgenSupport.emitLoadImm(CgenSupport.T1, expr.getLineNumber(), s);
        CgenSupport.emitJal(CgenSupport.DISPATCH_ABORT, s);
        CgenSupport.emitLabelDef(label, s);
        // load dispatch table
        CgenSupport.emitPartialLoadAddress(CgenSupport.T1, s); // we load the dispatch table from the static class directly
        CgenSupport.emitDispTableRef(type_name, s);
        s.println();
        // only difference from dispatch is that we look up the specified type_name instead of the the type of expr
        int offset = ((CgenNode)class_table.lookup(type_name)).method_map.get(name);
        CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s);
        CgenSupport.emitJalr(CgenSupport.T1, s);

        // pop formals from stack already done in codeClassMethods
        // CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE * actual.getLength(), s);        
        for(int i = 0; i < actual.getLength(); i++) {
            class_table.decreaseLocalVariableCount();
        }
    }


}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // push formals into stack
//        la	$a0 int_const0
//        sw	$a0 0($sp)
//        addiu	$sp $sp -4
        for(int i = 0; i < actual.getLength(); i++) {
            Expression e = (Expression)actual.getNth(i);
            e.code(s, current_node, class_table);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            class_table.getLocalVariableCount();
        }

        // dispatch from expr
//        lw	$a0 12($s0)
//        bne	$a0 $zero label0
//        la	$a0 str_const0
//        li	$t1 26
//        jal	_dispatch_abort
//        label0:
//        lw	$t1 8($a0)
//        lw	$t1 12($t1)
//        jalr		$t1
        expr.code(s, current_node, class_table);
        int label = CgenClassTable.getCurrentLabel();
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, label, s);
        // error handling _dispatch_abort
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        StringSymbol.codeRefByString(current_node.getFilename().toString(), s);
        s.println();
        CgenSupport.emitLoadImm(CgenSupport.T1, expr.getLineNumber(), s);
        CgenSupport.emitJal(CgenSupport.DISPATCH_ABORT, s);
        CgenSupport.emitLabelDef(label, s);
        // load dispatch table
        CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s);
        int offset = 0;
        if(expr.get_type() == TreeConstants.SELF_TYPE) {
            offset = current_node.method_map.get(name);
        } else {
            offset = ((CgenNode)class_table.lookup(expr.get_type())).method_map.get(name);
        }
        CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s);
        CgenSupport.emitJalr(CgenSupport.T1, s);

        // pop formals from stack already done in codeClassMethods
        // CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE * actual.getLength(), s);        
        for(int i = 0; i < actual.getLength(); i++) {
            class_table.decreaseLocalVariableCount();
        }

    }


}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
	pred.dump_with_types(out, n + 2);
	then_exp.dump_with_types(out, n + 2);
	else_exp.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // pred.code()
        // bne [ACC+3], 0, true_br
        // else_exp.code()
        // j after_br
        // true_br
        // then_exp.code()
        // after_br:
        pred.code(s, current_node, class_table);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, s);
        int label_after = CgenClassTable.getCurrentLabel();
        int label_true = CgenClassTable.getCurrentLabel();
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, label_true, s);
        else_exp.code(s, current_node, class_table);
        CgenSupport.emitBranch(label_after, s);
        CgenSupport.emitLabelDef(label_true, s);
        then_exp.code(s, current_node, class_table);
        CgenSupport.emitLabelDef(label_after, s);
    }


}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
	pred.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // loop:
        // pred.code()
        // beqz [ACC+3], after_loop
        // body.code()
        // j loop
        // after_loop:

        int label_start = CgenClassTable.getCurrentLabel();
        int label_end = CgenClassTable.getCurrentLabel();
        CgenSupport.emitLabelDef(label_start, s);
        pred.code(s, current_node, class_table);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, s);
        CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.ZERO, label_end, s);
        body.code(s, current_node, class_table);
        CgenSupport.emitBranch(label_start, s);
        CgenSupport.emitLabelDef(label_end, s);
    }


}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
	expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // expr.code()
        // t1 = a0.classtag
        // Get N [0...N-1] labels
        // LabelN:
        // t2 = br.type.classtag
        // bne t1, t2, Label_N+1
        // push a0
        // br.body.code()
        // j after
        // ...
        // after:
        // pop t1

        class BranchTagPair {
            branch b;
            int tag;

            public BranchTagPair(branch b, int tag) {
                this.b = b;
                this.tag = tag;
            }
        }

        // loop through branches and sort classes from highest tag to lowest
        ArrayList<BranchTagPair> branchList = new ArrayList<>();
        for(int i = 0; i < cases.getLength(); i++) {
            branch b = (branch)cases.getNth(i);
            branchList.add(new BranchTagPair(b, CgenSupport.getLowerBoundForBranch(b.type_decl, class_table)));
        }
        Collections.sort(branchList, new Comparator<BranchTagPair>() {
            @Override
            public int compare(BranchTagPair p1, BranchTagPair p2) {
                return p2.tag - p1.tag;
            }
        });

        int label_end = CgenClassTable.getCurrentLabel();
        int label_branch = CgenClassTable.getCurrentLabel();
        // evaluate expr
        expr.code(s, current_node, class_table);
        // handle error: case on void
//        bne	$a0 $zero label1
//        la	$a0 str_const0
//        li	$t1 44
//        jal	_case_abort2
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, label_branch, s);
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        StringSymbol.codeRefByString(current_node.getFilename().toString(), s);
        s.println();
        CgenSupport.emitLoadImm(CgenSupport.T1, expr.getLineNumber(), s);
        CgenSupport.emitJal(CgenSupport.CASE_ABORT_VOID, s);
        CgenSupport.emitLabelDef(label_branch, s);

        // load class tag of expr0
        CgenSupport.emitLoad(CgenSupport.T2, 0, CgenSupport.ACC, s);

        for(BranchTagPair p : branchList) {
            branch b = p.b;
            label_branch = CgenClassTable.getCurrentLabel();
            // do two jumps
            // write a function that output the class tag of current id type (minimum for the range)
            CgenSupport.emitBlti(CgenSupport.T2, CgenSupport.getLowerBoundForBranch(b.type_decl, class_table), label_branch, s);
            // write a function that check the maximum number of all possible subclasses class tags
            CgenSupport.emitBgti(CgenSupport.T2, CgenSupport.getUpperBoundForBranch(b.type_decl, class_table), label_branch, s);
            // move	$s1 $a0 -- skip this now
            // store the value of expr_0 into id_k, and push id_k onto stack
            CgenSupport.emitPush(CgenSupport.ACC, s);
            current_node.object_table.enterScope();
            current_node.object_table.addId(b.name, new ObjectInfo(class_table.getLocalVariableCount(), ObjectInfo.OBJTYPE.LOCAL));
            b.expr.code(s, current_node, class_table);
            // jump to the end
            CgenSupport.emitBranch(label_end, s);
            CgenSupport.emitLabelDef(label_branch, s);
            // exit scope and decrease the local offset
            current_node.object_table.exitScope();
            class_table.decreaseLocalVariableCount();
        }

        // handling error for no branch matched
        //jal	_case_abort
        //label0:
        CgenSupport.emitJal(CgenSupport.CASE_ABORT_NO_MATCH, s);
        CgenSupport.emitLabelDef(label_end, s);

        // pop out the local variable on the stack
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 4, s);

        
    }


}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        for(int i = 0; i < body.getLength(); i++) {
            Expression e = (Expression)body.getNth(i);
            e.code(s, current_node, class_table);
        }
    }


}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
	dump_AbstractSymbol(out, n + 2, identifier);
	dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        current_node.object_table.enterScope();
        // if init is empty, we push all zero onto the stack so that the local variable is void
        if(init.get_type() == null || init.get_type() == TreeConstants.No_type) {
            // handle the default initialization for the three base classes
            if(type_decl == TreeConstants.Int) {
                CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
                CgenClassTable.EMPTY_INT_SLOT.codeRef(s);
                s.println();
            } else if (type_decl == TreeConstants.Bool) {
                CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
                BoolConst.falsebool.codeRef(s);
                s.println();
            } else if (type_decl == TreeConstants.Str) {
                CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
                CgenClassTable.EMPTY_STR_SLOT.codeRef(s);
                s.println();
            } else {
                CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s);
            }
        } else {
            init.code(s, current_node, class_table);
        }
        // sp = sp - 4
        // sp[1] = acc
        // push the local variable onto stack
        CgenSupport.emitPush(CgenSupport.ACC, s);
        // store the information about this local variable in the object_table
        int offset = class_table.getLocalVariableCount();
        current_node.object_table.addId(identifier, new ObjectInfo(offset, ObjectInfo.OBJTYPE.LOCAL));
        body.code(s, current_node, class_table);
        // pop the local variable
        // sp = sp + 4
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
        // decrease the local variable count
        class_table.decreaseLocalVariableCount();
        current_node.object_table.exitScope();
    }


}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        if (Flags.cgen_debug) System.out.println("\t# enter evaluation of a plus expression");
        e1.code(s, current_node, class_table);
        //lw	$t1 12($a0)
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, s);
        // push $t1 onto stack
        class_table.getLocalVariableCount();
        CgenSupport.emitPush(CgenSupport.T1, s);
        e2.code(s, current_node, class_table);
        CgenSupport.emitJal(CgenSupport.OBJECT_COPY, s);
        //lw	$t2 12($a0)
        CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, s);
        // pop $t1 from stack
        CgenSupport.emitPop(CgenSupport.T1, s);
        class_table.decreaseLocalVariableCount();
        //add	$t1 $t1 $t2
        //sw	$t1 12($a0)
        CgenSupport.emitAdd(CgenSupport.T1, CgenSupport.T1, CgenSupport.T2, s);
        CgenSupport.emitStore(CgenSupport.T1, 3, CgenSupport.ACC, s);
        if (Flags.cgen_debug) System.out.println("\t# exit evaluation of a plus expression");
    }


}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
    }


}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
    }


}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
    }


}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
    }


}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // e1.code()
        // t1 = [ACC+3]
        // push t1
        // e2.code()
        // t1 = [ACC + 3]
        // t2 = pop
        // a0 = true
        // blt t2, t1, after_br
        // a0 = false
        // after_br:

        e1.code(s, current_node, class_table);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, s);
        // push val of e1 onto stack
        class_table.getLocalVariableCount();
        CgenSupport.emitPush(CgenSupport.T1, s);
        e2.code(s, current_node, class_table);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, s);
        // pop val of e1 from stack into $t1 again
        CgenSupport.emitPop(CgenSupport.T2, s);
        class_table.decreaseLocalVariableCount();
        //la	$a0 bool_const1
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        BoolConst.truebool.codeRef(s);
        s.println();
        int label = CgenClassTable.getCurrentLabel();
        CgenSupport.emitBlt(CgenSupport.T2, CgenSupport.T1, label, s);
        //la	$a0 bool_const0
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        BoolConst.falsebool.codeRef(s);
        s.println();
        CgenSupport.emitLabelDef(label, s);
    }


}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
    }


}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
    }


}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
    }


}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
	dump_AbstractSymbol(out, n + 2, token);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
	CgenSupport.emitLoadInt(CgenSupport.ACC,
                                (IntSymbol)AbstractTable.inttable.lookup(token.getString()), s);
    }

}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
	dump_Boolean(out, n + 2, val);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(val), s);
    }

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
	out.print(Utilities.pad(n + 2) + "\"");
	Utilities.printEscapedString(out, token.getString());
	out.println("\"");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
	CgenSupport.emitLoadString(CgenSupport.ACC,
                                   (StringSymbol)AbstractTable.stringtable.lookup(token.getString()), s);
    }

}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
	dump_AbstractSymbol(out, n + 2, type_name);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // a0 <- protObj
        // init
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        // if type_name is SELF_TYPE, we need to look up the type of current class
        if(type_name == TreeConstants.SELF_TYPE) {
            CgenSupport.emitProtObjRef(current_node.name, s);
            s.println();
            CgenSupport.emitJal(CgenSupport.OBJECT_COPY, s);
            CgenSupport.emitJal(current_node.name + CgenSupport.CLASSINIT_SUFFIX, s);
        } else {
            CgenSupport.emitProtObjRef(type_name, s);
            s.println();
            CgenSupport.emitJal(CgenSupport.OBJECT_COPY, s);
            CgenSupport.emitJal(type_name + CgenSupport.CLASSINIT_SUFFIX, s);
        }
    }


}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // e1.code()
        // t1 = a0
        // a0 = true
        // beqz t1, after
        // a0 = false
        // after:

        e1.code(s, current_node, class_table);
        CgenSupport.emitMove(CgenSupport.T1, CgenSupport.ACC, s);
        //la	$a0 bool_const1
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        BoolConst.truebool.codeRef(s);
        s.println();
        int label = CgenClassTable.getCurrentLabel();
        CgenSupport.emitBeqz(CgenSupport.T1, label, s);
        //la	$a0 bool_const0
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, s);
        BoolConst.falsebool.codeRef(s);
        s.println();
        CgenSupport.emitLabelDef(label, s);
    }


}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        // $a0 = 0
        
    }


}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
	dump_AbstractSymbol(out, n + 2, name);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, CgenNode current_node, CgenClassTable class_table) {
        if(name == TreeConstants.self) {
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
            return;
        }
        ObjectInfo info = (ObjectInfo) current_node.object_table.lookup(name);
        if(info.obj_type == ObjectInfo.OBJTYPE.FORMAL) {
            //lw	$a0 ?($fp)
            // should plus 2 here because the offset of formal is 1-based
            CgenSupport.emitLoad(CgenSupport.ACC, 2 + info.offset, CgenSupport.FP, s);
            return;
        }
        if(info.obj_type == ObjectInfo.OBJTYPE.ATTR) {
            //lw	$a0 ?($s0)
            // should plus 3 here because the offset of formal is 0-based
            CgenSupport.emitLoad(CgenSupport.ACC, 3 + info.offset, CgenSupport.SELF, s);
            return;
        }
        if(info.obj_type == ObjectInfo.OBJTYPE.LOCAL) {
            //lw	$a0 -?($fp)
            // 
            CgenSupport.emitLoad(CgenSupport.ACC, -(1 + info.offset), CgenSupport.FP, s);
            return;
        }
    }


}


