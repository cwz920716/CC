// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////


import java.util.*;
import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

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
    /* The abstract semant() method. Each subclass of Expression should override this method,
     * and recursively call its children expressions semant() method.
     */
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) { return null; }

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
        // Pass 1 and Pass 2 are done in the constructor of ClassTable
        // Pass 3: feature population
        for (int i = 0; i < classes.getLength(); i++) {
            class_c current_class_c = (class_c)classes.getNth(i);
            ClassDef current_class_def = ClassTable.getClassDef(current_class_c.getName());
            Features features = current_class_c.features;
            // enter scope of this class
            current_class_def.enterBothScopes();
            for (int j = 0; j < features.getLength(); j++) {
                Feature f = (Feature)features.getNth(j);
                if(f instanceof method) {
                    // method feature
                    method mf = (method)f;
                    if(current_class_def.method_table.probe(mf.name) != null) {
                        classTable.semantError(current_class_def.class_src.getFilename(),
                                mf).println("Method " + mf.name.str + " is multiply defined.");
                    } else {
                        current_class_def.method_table.addId(mf.name, mf);
                    }
                } else if (f instanceof attr) {
                    // attr feature
                    attr af = (attr)f;
                    // attr name cannot be self
                    if(af.name == TreeConstants.self) {
                        classTable.semantError(current_class_def.class_src.getFilename(),
                                af).println("'self' cannot be the name of an attribute.");
                    } else if(current_class_def.attr_table.probe(af.name) != null) {
                        classTable.semantError(current_class_def.class_src.getFilename(),
                                af).println("Attribute " + af.name + " is multiply defined in class.");
                    } else {
                        // TODO: Now check for method decl correctness
                        current_class_def.attr_table.addId(af.name, af);
                    }
                }
            }
        }

        // Also populate features for the five basic classes
        for(AbstractSymbol basic_class : ClassTable.all_basic_classes) {
            ClassDef current_class_def = ClassTable.getClassDef(basic_class);
            Features features = current_class_def.class_src.features;
            current_class_def.enterBothScopes();
            for (int j = 0; j < features.getLength(); j++) {
                Feature f = (Feature)features.getNth(j);
                if(f instanceof method) {
                    method mf = (method)f;
                    current_class_def.method_table.addId(mf.name, mf);
                } else if (f instanceof attr) {
                    attr af = (attr)f;
                    current_class_def.attr_table.addId(af.name, af);
                }
            }
        }

        // Pass 4: Check for invalid overriding and populate the symbol tables with ancestor information.
        Stack<ClassDef> class_stack = new Stack<>();
        // start from the root class: Object
        ClassDef current_class = ClassTable.getClassDef(TreeConstants.Object_);
        class_stack.push(current_class);
        while(!class_stack.empty()) {
            current_class = class_stack.pop();
            // push each child into stack
            if(current_class.child_classes != null) {
                for(AbstractSymbol child : current_class.child_classes) {
                    class_stack.push(ClassTable.getClassDef(child));
                }
            }
            if(current_class.parent_class == null) continue;
            // 1) Check for overriding
            SymbolTable parent_attr_table = ClassTable.getClassDef(current_class.parent_class).attr_table;
            SymbolTable parent_method_table = ClassTable.getClassDef(current_class.parent_class).method_table;
            // prepare new symbol tables for merging
            SymbolTable new_attr_table = parent_attr_table.clone();
            SymbolTable new_method_table = parent_method_table.clone();

            for(Object f : current_class.attr_table.getValues()) {
                attr af = (attr)f;
                // check for attr overriding
                if (parent_attr_table.probe(af.name) != null) {
                    classTable.semantError(current_class.class_src.getFilename(), af).println(
                            "Attribute " + af.name + " is an attribute of an inherited class.");
                } else {
                    // store the attr into the new attr table
                    new_attr_table.addId(af.name, af);
                }
            }

            for(Object f : current_class.method_table.getValues()) {
                // check for method overriding
                method mf = (method)f;
                method parent_mf = (method)parent_method_table.probe(mf.name);
                if(parent_mf != null) {
                    // method with the same name exists in a parent class, check formal list and return type
                    if (parent_mf.return_type != mf.return_type) {
                        classTable.semantError(current_class.class_src.getFilename(), mf).println(
                                "In redefined method " + mf.name + ", return type " + mf.return_type +
                                        " is different from original return type " + parent_mf.return_type + ".");
                        continue;
                    }
                    // check for parameter number mismatch
                    if(parent_mf.formals.getLength() != mf.formals.getLength()) {
                        classTable.semantError(current_class.class_src.getFilename(), mf).println(
                                "Incompatible number of formal parameters in redefined method " + mf.name + ".");
                        continue;
                    } else {
                        // check for parameter type mismatch
                        boolean mismatch = false;
                        for(int j = 0; j < mf.formals.getLength(); j++) {
                            formalc f1 = (formalc)parent_mf.formals.getNth(j);
                            formalc f2 = (formalc)mf.formals.getNth(j);
                            if(f1.type_decl != f2.type_decl) {
                                classTable.semantError(current_class.class_src.getFilename(), mf).println(
                                        "In redefined method " + mf.name + ", parameter type " + f2.type_decl +
                                                " is different from original type " + f1.type_decl + ".");
                                mismatch = true;
                                break;
                            }
                        }
                        if(mismatch) continue;
                    }
                }
                // Replace the old method in parent with the new one
                new_method_table.addId(mf.name, mf);
            }
            // 2) Replace the old symbol tables with the merged tables
            current_class.attr_table = new_attr_table;
            current_class.method_table = new_method_table;
        }

        // Check for Main class and main method
        ClassDef main_class = ClassTable.getClassDef(TreeConstants.Main);
        if(main_class == null) {
            classTable.semantError().println("Class Main is not defined.");
        } else {
            if(main_class.method_table.probe(TreeConstants.main_meth) == null) {
                classTable.semantError(main_class.class_src).println("No 'main' method in class Main.");
            }
        }

        // Pass 5: type checking and type inference for expressions
        for (int i = 0; i < classes.getLength(); i++) {
            class_c current_class_c = (class_c) classes.getNth(i);
            ClassDef current_class_def = ClassTable.getClassDef(current_class_c.getName());
            Features features = current_class_c.features;
            ArrayList<ErrorMessage> error_msg_list = new ArrayList<>();
            for (int j = 0; j < features.getLength(); j++) {
                Feature f = (Feature) features.getNth(j);
                if (f instanceof method) {
                    // type checking for expressions
                    method mf = (method)f;
                    // enter a new naming scope for the method
                    current_class_def.attr_table.enterScope();
                    for (int k = 0; k < mf.formals.getLength(); k++) {
                        formalc formal = (formalc)mf.formals.getNth(k);
                        if(formal.type_decl == TreeConstants.SELF_TYPE) {
                            // formal parameter of a method cannot have SELF_TYPE
                            error_msg_list.add(new ErrorMessage(mf, "Formal parameter " + formal.name + " cannot have type TreeConstants.SELF_TYPE."));
                        } else if(!SemantUtils.existsClass(formal.type_decl)) {
                            // formal parameter type undefined.
                            error_msg_list.add(new ErrorMessage(mf, "Class " + formal.type_decl +
                                    " of formal parameter " + formal.name + " is undefined."));
                        }
                        // formal parameter cannot be named as self
                        if(formal.name == TreeConstants.self) {
                            error_msg_list.add(new ErrorMessage(mf, "'self' cannot be the name of a formal parameter."));
                        } else {
                            if (current_class_def.attr_table.probe(formal.name) == null) {
                                current_class_def.attr_table.addId(formal.name, new attr(formal.lineNumber, formal.name, formal.type_decl, new no_expr(formal.lineNumber)));
                            } else {
                                error_msg_list.add(new ErrorMessage(mf, "Formal parameter " + formal.name + " is multiply defined."));
                            }
                        }
                    }
                    AbstractSymbol returned_type = mf.expr.semant(error_msg_list, current_class_def);
                    if(mf.return_type != TreeConstants.SELF_TYPE && !SemantUtils.existsClass(mf.return_type)) {
                        error_msg_list.add(new ErrorMessage(mf, "Undefined return type " + mf.return_type + " in method " + mf.name + "."));
                    }
                    if (!SemantUtils.conformsTo(returned_type, mf.return_type, current_class_def.class_name)) {
                        error_msg_list.add(new ErrorMessage(mf, "Inferred return type " + returned_type + " of method " + mf.name +
                                " does not conform to declared return type " + mf.return_type + "."));
                    }
                    current_class_def.attr_table.exitScope();
                } else if (f instanceof attr) {
                    attr af = (attr)f;
                    // check whether declared type has been defined
                    if(af.type_decl != TreeConstants.SELF_TYPE && !SemantUtils.existsClass(af.type_decl)) {
                        error_msg_list.add(new ErrorMessage(af, "Class " + af.type_decl +
                                " of attribute " + af.name + " is undefined."));
                    } else {
                        AbstractSymbol init_type = af.init.semant(error_msg_list, current_class_def);
                        if (init_type != TreeConstants.No_type && !SemantUtils.conformsTo(init_type, af.type_decl, current_class_def.class_name)) {
                            error_msg_list.add(new ErrorMessage(af, "Inferred type " + init_type + " of initialization of attribute " + af.name +
                                    " does not conform to declared type " + af.type_decl + "."));
                        }
                    }
                }
            }
            // Report errors
            for(ErrorMessage em : error_msg_list) {
                classTable.semantError(current_class_def.class_src.getFilename(), em.getErrorNode()).println(em.getMessage());
            }
            // exit scope of this class
            // current_class_def.exitBothScopes();
        }

	if (classTable.errors()) {
	    System.err.println("Compilation halted due to static semantic errors.");
	    System.exit(1);
	}
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

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        AbstractSymbol expr_type = expr.semant(error_msg_list, class_def);
        AbstractSymbol name_type = SemantUtils.typeOfObject(name, class_def.attr_table);
        if(name_type == null) {
            error_msg_list.add(new ErrorMessage(this, "Assignment to undeclared variable " + name.str + "."));
            this.set_type(expr_type);
        } else if(SemantUtils.conformsTo(expr_type, name_type, class_def.class_name)) {
            this.set_type(expr_type);
        } else {
            error_msg_list.add(new ErrorMessage(this, "Type " + expr_type + " of assigned expression does not conform to declared type "
                    + name_type + " of identifier " + name + "."));
            this.set_type(TreeConstants.Object_);
        }
        return this.get_type();
    }

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

}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        AbstractSymbol expr_type = expr.semant(error_msg_list, class_def);
        // cannot static dispatch to SELF_TYPE
        if(type_name == TreeConstants.SELF_TYPE) {
            error_msg_list.add(new ErrorMessage(this, "Static dispatch to SELF_TYPE."));
            this.set_type(TreeConstants.Object_);
            return this.get_type();
        }
        if(!SemantUtils.conformsTo(expr_type, type_name, class_def.class_name)) {
            error_msg_list.add(new ErrorMessage(this, "Expression type " + expr_type +
                    " does not conform to declared static dispatch type " + type_name + "."));
        }
        ArrayList<AbstractSymbol> param_types = new ArrayList<>();
        for(int i = 0; i < actual.getLength(); i++) {
            Expression e = (Expression)actual.getNth(i);
            param_types.add(e.semant(error_msg_list, class_def));
        }

        ClassDef dispatch_class;
        // TODO: What if the expr is self? What are we gonna get here? Test Later!!!!!
        if(expr_type == TreeConstants.No_type || expr_type == TreeConstants.SELF_TYPE) {
            dispatch_class = class_def;
        } else {
            dispatch_class = ClassTable.class_map.get(expr_type);
        }
        if(dispatch_class == null) {
            error_msg_list.add(new ErrorMessage(this, "Static dispatch to undefined class " + type_name + "."));
        } else {
            method m = (method) dispatch_class.method_table.lookup(name);
            if(m == null) {
                // method not found, error
                error_msg_list.add(new ErrorMessage(this, "Static dispatch to undefined method " + name + "."));
            } else {
                // check formal list
                if(m.formals.getLength() != param_types.size()) {
                    error_msg_list.add(new ErrorMessage(this, "Method " + m.name + " invoked with wrong number of arguments."));
                }
                for(int i = 0; i < m.formals.getLength(); i++) {
                    formalc f = (formalc)m.formals.getNth(i);
                    if(!SemantUtils.conformsTo(param_types.get(i), f.type_decl, class_def.class_name)) {
                        error_msg_list.add(new ErrorMessage(this, "In call of method " + m.name + ", type " + param_types.get(i) +
                                " of parameter " + f.name + " does not conform to declared type " + f.type_decl + "."));
                    }
                }
                // check return type
                if (m.return_type == TreeConstants.SELF_TYPE) {
                    if(expr_type == TreeConstants.No_type) {
                        this.set_type(TreeConstants.SELF_TYPE); // why expr_type is No_type? (expr_type cannot be void) Shouldn't we throw an error?
                    } else {
                        this.set_type(expr_type);
                    }
                } else {
                    this.set_type(m.return_type);
                }
                return this.get_type();
            }
        }
        this.set_type(TreeConstants.Object_);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        AbstractSymbol expr_type = expr.semant(error_msg_list, class_def);
        ArrayList<AbstractSymbol> param_types = new ArrayList<>();
        for(int i = 0; i < actual.getLength(); i++) {
            Expression e = (Expression)actual.getNth(i);
            param_types.add(e.semant(error_msg_list, class_def));
        }
        ClassDef dispatch_class;
        // TODO: What if the expr is self? What are we gonna get here? Test Later!!!!!
        if(expr_type == TreeConstants.No_type || expr_type == TreeConstants.SELF_TYPE) {
            dispatch_class = class_def;
        } else {
            dispatch_class = ClassTable.class_map.get(expr_type);
        }
        if(dispatch_class == null) {
            // We add this to avoid potential NULLPointerException
            error_msg_list.add(new ErrorMessage(this, "Dispatch to undefined class " + expr_type + "."));
        } else {
            method m = (method) dispatch_class.method_table.lookup(name);
            if(m == null) {
                // method not found, error
                error_msg_list.add(new ErrorMessage(this, "Dispatch to undefined method " + name + "."));
            } else {
                // check formal list
                if(m.formals.getLength() != param_types.size()) {
                    error_msg_list.add(new ErrorMessage(this, "Method " + m.name + " invoked with wrong number of arguments."));
                }
                for(int i = 0; i < m.formals.getLength(); i++) {
                    formalc f = (formalc)m.formals.getNth(i);
                    if(!SemantUtils.conformsTo(param_types.get(i), f.type_decl, class_def.class_name)) {
                        error_msg_list.add(new ErrorMessage(this, "In call of method " + m.name + ", type " + param_types.get(i) +
                                " of parameter " + f.name + " does not conform to declared type " + f.type_decl + "."));
                    }
                }
                // check return type
                if (m.return_type == TreeConstants.SELF_TYPE) {
                    if(expr_type == TreeConstants.No_type) {
                        this.set_type(TreeConstants.SELF_TYPE);
                    } else {
                        this.set_type(expr_type);
                    }
                } else {
                    this.set_type(m.return_type);
                }
                return this.get_type();
            }
        }
        this.set_type(TreeConstants.Object_);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        AbstractSymbol pred_type = pred.semant(error_msg_list, class_def);
        AbstractSymbol then_type = then_exp.semant(error_msg_list, class_def);
        AbstractSymbol else_type = else_exp.semant(error_msg_list, class_def);

        if (pred_type != TreeConstants.Bool) {
            error_msg_list.add(new ErrorMessage(this, "Predicate in if condition must have type Bool."));
        }

        this.set_type(SemantUtils.getlub(then_type, else_type, class_def.class_name));

        return this.get_type();
    }
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

}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        AbstractSymbol pred_type = pred.semant(error_msg_list, class_def);
        body.semant(error_msg_list, class_def);
        if(pred_type != TreeConstants.Bool) {
            error_msg_list.add(new ErrorMessage(this, "Condition in loop must have type of Bool."));
        }
        this.set_type(TreeConstants.Object_);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        expr.semant(error_msg_list, class_def);
        HashSet<AbstractSymbol> seenBranchClasses = new HashSet<>();
        HashSet<AbstractSymbol> branchExpressionTypes = new HashSet<>();
        for(int i = 0; i < cases.getLength(); i++) {
            branch b = (branch)cases.getNth(i);
            // Check whether the type of this branch exists
            if(b.type_decl == TreeConstants.SELF_TYPE) {
                // -- identifier in case branch cannot have type SELF_TYPE
                error_msg_list.add(new ErrorMessage(b, "Identifier " + b.name + " declared with type SELF_TYPE in case branch."));
            } else if(!SemantUtils.existsClass(b.type_decl)) {
                // -- type of identifier is undefined
                error_msg_list.add(new ErrorMessage(b, "Class " + b.type_decl + " of case branch is undefined."));
            }
            // Check whether type is distinct
            if(seenBranchClasses.contains(b.type_decl)) {
                // -- duplicate branch type in case
                error_msg_list.add(new ErrorMessage(b, "Duplicate branch " + b.type_decl + " in case statement."));
            } else {
                seenBranchClasses.add(b.type_decl);
            }
            // Enterscope and evaluate expression
            class_def.attr_table.enterScope();
            // -- branch object id cannot be self
            if(b.name == TreeConstants.self) {
                error_msg_list.add(new ErrorMessage(b, "'self' bound in 'case'."));
            }
            class_def.attr_table.addId(b.name, new attr(b.lineNumber, b.name, b.type_decl, new no_expr(b.lineNumber)));
            AbstractSymbol returned_type = b.expr.semant(error_msg_list, class_def);
            branchExpressionTypes.add(returned_type);
            class_def.attr_table.exitScope();
        }
        this.set_type(SemantUtils.getlubFromTypes(branchExpressionTypes, class_def.class_name));
        return this.get_type();
    }
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

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        // For the first n - 1 expressions, we only do type checking and name checking
        for (int i = 0; i < body.getLength() - 1; i++) {
            Expression e = (Expression)body.getNth(i);
            e.semant(error_msg_list, class_def);
        }
        // For the last expression, we need the return type
        Expression e = (Expression)body.getNth(body.getLength() - 1);
        AbstractSymbol return_type = e.semant(error_msg_list, class_def);
        this.set_type(return_type);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        // 1. Evaluate init
        AbstractSymbol init_type = init.semant(error_msg_list, class_def);
        // -- check whether type_decl is a defined class
        if(type_decl != TreeConstants.SELF_TYPE && ClassTable.getClassDef(type_decl) == null) {
            error_msg_list.add(new ErrorMessage(this, "Class " + type_decl + " of let-bound identifier "
                    + identifier + " is undefined."));
        }

        // 2. type check for type_decl
        // init_type should be subtype of type_decl
        if(init_type != TreeConstants.No_type && !SemantUtils.conformsTo(init_type, type_decl, class_def.class_name)) {
            error_msg_list.add(new ErrorMessage(this, "Inferred type " + init_type + " of initialization of "
                    + identifier + " does not conform to identifier's declared type " + type_decl + "."));
        }
        // 3. Enter scope and evaluate body
        class_def.attr_table.enterScope();
        if(identifier == TreeConstants.self) {
            error_msg_list.add(new ErrorMessage(this, "'self' cannot be bound in a 'let' expression."));
        } else {
            class_def.attr_table.addId(identifier, new attr(this.lineNumber, identifier, type_decl, new no_expr(this.lineNumber)));
        }
        AbstractSymbol returned_type = body.semant(error_msg_list, class_def);
        class_def.attr_table.exitScope();

        this.set_type(returned_type);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        e2.semant(error_msg_list, class_def);
        if(e1.get_type() != TreeConstants.Int || e2.get_type() != TreeConstants.Int) {
            String msg = "non-Int arguments: " + e1.get_type().str + " + " + e2.get_type().str;
            error_msg_list.add(new ErrorMessage(this, msg));
        }
        this.set_type(TreeConstants.Int);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        e2.semant(error_msg_list, class_def);
        if(e1.get_type() != TreeConstants.Int || e2.get_type() != TreeConstants.Int) {
            String msg = "non-Int arguments: " + e1.get_type().str + " - " + e2.get_type().str;
            error_msg_list.add(new ErrorMessage(this, msg));
        }
        this.set_type(TreeConstants.Int);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        e2.semant(error_msg_list, class_def);
        if(e1.get_type() != TreeConstants.Int || e2.get_type() != TreeConstants.Int) {
            String msg = "non-Int arguments: " + e1.get_type().str + " * " + e2.get_type().str;
            error_msg_list.add(new ErrorMessage(this, msg));
        }
        this.set_type(TreeConstants.Int);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        e2.semant(error_msg_list, class_def);
        if(e1.get_type() != TreeConstants.Int || e2.get_type() != TreeConstants.Int) {
            String msg = "non-Int arguments: " + e1.get_type().str + " / " + e2.get_type().str;
            error_msg_list.add(new ErrorMessage(this, msg));
        }
        this.set_type(TreeConstants.Int);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        AbstractSymbol returned_type = e1.semant(error_msg_list, class_def);
        if(returned_type != TreeConstants.Int) {
            error_msg_list.add(new ErrorMessage(this, "Neg: expected expression of type Int."));
        }
        this.set_type(TreeConstants.Int);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        e2.semant(error_msg_list, class_def);
        if(e1.get_type() != TreeConstants.Int || e2.get_type() != TreeConstants.Int) {
            String msg = "non-Int arguments: " + e1.get_type().str + " < " + e2.get_type().str;
            error_msg_list.add(new ErrorMessage(this, msg));
        }
        this.set_type(TreeConstants.Bool);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        e2.semant(error_msg_list, class_def);
        if(ClassTable.base_classes.contains(e1.get_type()) || ClassTable.base_classes.contains(e2.get_type())) {
            if (e1.get_type() != e2.get_type()) {
                String msg = "Illegal comparison with a basic type.";
                error_msg_list.add(new ErrorMessage(this, msg));
            }
        }
        this.set_type(TreeConstants.Bool);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        e2.semant(error_msg_list, class_def);
        if(e1.get_type() != TreeConstants.Int || e2.get_type() != TreeConstants.Int) {
            String msg = "non-Int arguments: " + e1.get_type().str + " <= " + e2.get_type().str;
            error_msg_list.add(new ErrorMessage(this, msg));
        }
        this.set_type(TreeConstants.Bool);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        AbstractSymbol returned_type = e1.semant(error_msg_list, class_def);
        if(returned_type != TreeConstants.Bool) {
            error_msg_list.add(new ErrorMessage(this, "Comp: expected expression of type Bool."));
        }
        this.set_type(TreeConstants.Bool);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        this.set_type(TreeConstants.Int);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        this.set_type(TreeConstants.Bool);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        this.set_type(TreeConstants.Str);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        if(type_name == TreeConstants.SELF_TYPE) {
            this.set_type(TreeConstants.SELF_TYPE); // set_type(TreeConstants.SELF_TYPE) vs. get type then set type to static type?
        } else if (SemantUtils.existsClass(type_name)){
            this.set_type(type_name);
        } else {
            error_msg_list.add(new ErrorMessage(this, "'new' used with undefined class " + type_name + "."));
            this.set_type(TreeConstants.Object_);
        }
        return this.get_type();
    }
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

}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        e1.semant(error_msg_list, class_def);
        this.set_type(TreeConstants.Bool);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        this.set_type(TreeConstants.No_type);
        return this.get_type();
    }
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

}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;

    @Override
    public AbstractSymbol semant(ArrayList<ErrorMessage> error_msg_list, ClassDef class_def) {
        // if object is self: set node as SELF_TYPE
        if(name == TreeConstants.self) {
            this.set_type(TreeConstants.SELF_TYPE);
            return this.get_type();
        }
        AbstractSymbol type = SemantUtils.typeOfObject(name, class_def.attr_table);
        if(type == null) {
            error_msg_list.add(new ErrorMessage(this, "Undeclared identifier " + name + "."));
            this.set_type(TreeConstants.Object_);
        } else {
            this.set_type(type);
        }
        return this.get_type();
    }
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

}


