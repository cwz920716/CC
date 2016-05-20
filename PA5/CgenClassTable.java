/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Stack;

/** This class is used for representing the inheritance tree during code
    generation. You will need to fill in some of its methods and
    potentially extend it in other useful ways. */
class CgenClassTable extends SymbolTable {

    /** All classes in the program, represented as CgenNode */
    private Vector nds;

    /** This is the stream to which assembly instructions are output */
    private PrintStream str;

    public static IntSymbol EMPTY_INT_SLOT;
    public static StringSymbol EMPTY_STR_SLOT;

    private int stringclasstag = 4;
    private int intclasstag = 2;
    private int boolclasstag = 3;
    private int objectclasstag = 0;
    private int ioclasstag = 1;

    private int class_tag_accu = 5;

    private int local_variable_count = 0;

    public static int LABEL_ACCU = 0;


    // The following methods emit code for constants and global
    // declarations.

    /** Emits code to start the .data segment and to
     * declare the global names.
     * */
    private void codeGlobalData() {
	// The following global names must be defined first.

	str.print("\t.data\n" + CgenSupport.ALIGN);
	str.println(CgenSupport.GLOBAL + CgenSupport.CLASSNAMETAB);
	str.print(CgenSupport.GLOBAL); 
	CgenSupport.emitProtObjRef(TreeConstants.Main, str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	CgenSupport.emitProtObjRef(TreeConstants.Int, str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	CgenSupport.emitProtObjRef(TreeConstants.Str, str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	BoolConst.falsebool.codeRef(str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	BoolConst.truebool.codeRef(str);
	str.println("");
	str.println(CgenSupport.GLOBAL + CgenSupport.INTTAG);
	str.println(CgenSupport.GLOBAL + CgenSupport.BOOLTAG);
	str.println(CgenSupport.GLOBAL + CgenSupport.STRINGTAG);

	// We also need to know the tag of the Int, String, and Bool classes
	// during code generation.

	str.println(CgenSupport.INTTAG + CgenSupport.LABEL 
		    + CgenSupport.WORD + intclasstag);
	str.println(CgenSupport.BOOLTAG + CgenSupport.LABEL 
		    + CgenSupport.WORD + boolclasstag);
	str.println(CgenSupport.STRINGTAG + CgenSupport.LABEL 
		    + CgenSupport.WORD + stringclasstag);

    }

    /** Emits code to start the .text segment and to
     * declare the global names.
     * */
    private void codeGlobalText() {
	str.println(CgenSupport.GLOBAL + CgenSupport.HEAP_START);
	str.print(CgenSupport.HEAP_START + CgenSupport.LABEL);
	str.println(CgenSupport.WORD + 0);
	str.println("\t.text");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Main, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Int, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Str, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Bool, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitMethodRef(TreeConstants.Main, TreeConstants.main_meth, str);
	str.println("");
    }

    /** Emits code definitions for boolean constants. */
    private void codeBools(int classtag) {
	BoolConst.falsebool.codeDef(classtag, str);
	BoolConst.truebool.codeDef(classtag, str);
    }

    /** Generates GC choice constants (pointers to GC functions) */
    private void codeSelectGc() {
	str.println(CgenSupport.GLOBAL + "_MemMgr_INITIALIZER");
	str.println("_MemMgr_INITIALIZER:");
	str.println(CgenSupport.WORD 
		    + CgenSupport.gcInitNames[Flags.cgen_Memmgr]);

	str.println(CgenSupport.GLOBAL + "_MemMgr_COLLECTOR");
	str.println("_MemMgr_COLLECTOR:");
	str.println(CgenSupport.WORD 
		    + CgenSupport.gcCollectNames[Flags.cgen_Memmgr]);

	str.println(CgenSupport.GLOBAL + "_MemMgr_TEST");
	str.println("_MemMgr_TEST:");
	str.println(CgenSupport.WORD 
		    + ((Flags.cgen_Memmgr_Test == Flags.GC_TEST) ? "1" : "0"));
    }

    /** Emits code to reserve space for and initialize all of the
     * constants.  Class names should have been added to the string
     * table (in the supplied code, is is done during the construction
     * of the inheritance graph), and code for emitting string constants
     * as a side effect adds the string's length to the integer table.
     * The constants are emmitted by running through the stringtable and
     * inttable and producing code for each entry. */
    private void codeConstants() {
	// Add constants that are required by the code generator.
        EMPTY_STR_SLOT = (StringSymbol)AbstractTable.stringtable.addString("");
        EMPTY_INT_SLOT = (IntSymbol)AbstractTable.inttable.addString("0");

	AbstractTable.stringtable.codeStringTable(stringclasstag, str);
	AbstractTable.inttable.codeStringTable(intclasstag, str);
	codeBools(boolclasstag);
    }


    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// A few special class names are installed in the lookup table
	// but not the class list.  Thus, these classes exist, but are
	// not part of the inheritance hierarchy.  No_class serves as
	// the parent of Object and the other special classes.
	// SELF_TYPE is the self class; it cannot be redefined or
	// inherited.  prim_slot is a class known to the code generator.

	addId(TreeConstants.No_class,
	      new CgenNode(new class_c(0,
				      TreeConstants.No_class,
				      TreeConstants.No_class,
				      new Features(0),
				      filename),
			   CgenNode.Basic, this));

	addId(TreeConstants.SELF_TYPE,
	      new CgenNode(new class_c(0,
				      TreeConstants.SELF_TYPE,
				      TreeConstants.No_class,
				      new Features(0),
				      filename),
			   CgenNode.Basic, this));
	
	addId(TreeConstants.prim_slot,
	      new CgenNode(new class_c(0,
				      TreeConstants.prim_slot,
				      TreeConstants.No_class,
				      new Features(0),
				      filename),
			   CgenNode.Basic, this));

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
		       TreeConstants.Object_, 
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0, 
					      TreeConstants.cool_abort, 
					      new Formals(0), 
					      TreeConstants.Object_, 
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);

	installClass(new CgenNode(Object_class, CgenNode.Basic, this));
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);

	CgenNode IO_node = new CgenNode(IO_class, CgenNode.Basic, this);
	installClass(IO_node);

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	installClass(new CgenNode(Int_class, CgenNode.Basic, this));

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	installClass(new CgenNode(Bool_class, CgenNode.Basic, this));

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	installClass(new CgenNode(Str_class, CgenNode.Basic, this));
    }
	
    // The following creates an inheritance graph from
    // a list of classes.  The graph is implemented as
    // a tree of `CgenNode', and class names are placed
    // in the base class symbol table.
    
    private void installClass(CgenNode nd) {
	AbstractSymbol name = nd.getName();
	if (probe(name) != null) return;
	nds.addElement(nd);
	addId(name, nd);
    }

    private void installClasses(Classes cs) {
        for (Enumeration e = cs.getElements(); e.hasMoreElements(); ) {
	    installClass(new CgenNode((Class_)e.nextElement(), 
				       CgenNode.NotBasic, this));
        }
    }

    private void buildInheritanceTree() {
	for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
	    setRelations((CgenNode)e.nextElement());
	}
    }

    private void setRelations(CgenNode nd) {
	CgenNode parent = (CgenNode)probe(nd.getParent());
	nd.setParentNd(parent);
	parent.addChild(nd);
    }

    /** Constructs a new class table and invokes the code generator */
    public CgenClassTable(Classes cls, PrintStream str) {
	nds = new Vector();

	this.str = str;

	enterScope();
	if (Flags.cgen_debug) System.out.println("Building CgenClassTable");
	
	installBasicClasses();
	installClasses(cls);
	buildInheritanceTree();

	code();

	exitScope();
    }

    /** This method is the meat of the code generator.  It is to be
        filled in programming assignment 5 */
    public void code() {
	if (Flags.cgen_debug) System.out.println("coding global data");
	codeGlobalData();

	if (Flags.cgen_debug) System.out.println("choosing gc");
	codeSelectGc();

	if (Flags.cgen_debug) System.out.println("coding constants");
	codeConstants();

	//                 Add your code to emit
	//                   - prototype objects
	//                   - class_nameTab
	//                   - dispatch tables

        codePrototypeObjects();
        codeClassNameTab();
        codeObjectTab();
        codeDispatchTables();

	if (Flags.cgen_debug) System.out.println("coding global text");
	codeGlobalText();

	//                 Add your code to emit
	//                   - object initializer
	//                   - the class methods
	//                   - etc...
        codeObjectInit();
        codeClassMethods();
    }

    private void setClassTag(CgenNode node) {
        if(node.name == TreeConstants.Object_) {
            node.setClassTag(objectclasstag);
            return;
        }
        if(node.name == TreeConstants.IO) {
            node.setClassTag(ioclasstag);
            return;
        }
        if(node.name == TreeConstants.Int) {
            node.setClassTag(intclasstag);
            return;
        }
        if(node.name == TreeConstants.Str) {
            node.setClassTag(stringclasstag);
            return;
        }
        if(node.name == TreeConstants.Bool) {
            node.setClassTag(boolclasstag);
            return;
        }
        node.setClassTag(class_tag_accu);
        class_tag_accu++;
    }

    /**
     * Generate code for the class name table.
     */
    private void codeClassNameTab() {
        str.print(CgenSupport.CLASSNAMETAB + CgenSupport.LABEL);
        // iterate through the class node vector
        for (int i = 0; i < nds.size(); i++) {
            CgenNode n = (CgenNode) nds.get(i);
            str.print(CgenSupport.WORD);
            StringSymbol.codeRefByString(n.name.getString(), str);
            str.println();
        }
    }

    /**
     * Generate code for the class object table.
     */
    private void codeObjectTab() {
        str.print(CgenSupport.CLASSOBJTAB + CgenSupport.LABEL);
        // iterate through the class node vector
        for (int i = 0; i < nds.size(); i++) {
            CgenNode n = (CgenNode) nds.get(i);
            str.print(CgenSupport.WORD);
            CgenSupport.emitProtObjRef(n.name, str);
            str.println();
            str.print(CgenSupport.WORD);
            CgenSupport.emitInitRef(n.name, str);
            str.println();
        }
    }

    /**
     * Generate code for the class dispatch tables.
     */
    private void codeDispatchTables() {
        // DFS over the inheritance graph
        Stack<CgenNode> class_stack = new Stack<>();
        CgenNode current_class = (CgenNode)lookup(TreeConstants.Object_);
        class_stack.push(current_class);
        while(!class_stack.empty()) {
            current_class = class_stack.pop();
            // push each children into stack
            for(Enumeration e = current_class.getChildren(); e.hasMoreElements(); ) {
                class_stack.push((CgenNode)e.nextElement());
            }
            // skip for Object class
            if(current_class.getParent() != TreeConstants.No_class) {
                // push methods from parent
                ArrayList<ClassFeaturePair> parent_table = current_class.getParentNd().getMethodTable();
                for(ClassFeaturePair pair : parent_table) {
                    current_class.getMethodTable().add(pair);
                }
            }
            // push local methods
            for(int j = 0 ; j < current_class.getFeatures().getLength(); j++) {
                Feature f = (Feature)current_class.getFeatures().getNth(j);
                if(f instanceof method) {
                    method m = (method) f;
                    current_class.pushMethod(current_class.name, m.name);
                    current_class.pushLocalMethod(m);
                }
            }
            // generate the dispatch table for current class
            CgenSupport.emitDispTableRef(current_class.name, str);
            str.print(CgenSupport.LABEL);

            int offset = 0;
            for(ClassFeaturePair pair : current_class.getMethodTable()) {
                str.print(CgenSupport.WORD);
                str.println(pair.toString());

                // put method into method_map
                current_class.method_map.put(pair.feature_name, offset++);
            }
        }
    }

    private void codeObjectInit() {
        for (int i = 0; i < nds.size(); i++) {
            CgenNode n = (CgenNode) nds.get(i);
            CgenSupport.emitObjectInit(n, this, str);
        }
    }

    private void codeClassMethods() {
        for(int i = 5; i < nds.size(); i++) {
            CgenNode n = (CgenNode) nds.get(i);
            for(int j = 0; j < n.getLocalMethodTable().size(); j++) {
                method m = n.getLocalMethodTable().get(j);
                // add all formal parameters into object table
                n.object_table.enterScope();
                for(int k = 0; k < m.formals.getLength(); k++) {
                    formalc f = (formalc) m.formals.getNth(k);
                    n.object_table.addId(f.name, new ObjectInfo(m.formals.getLength() - k, ObjectInfo.OBJTYPE.FORMAL));
                }
                // TODO: Do we need estimate temp space?
                str.print(n.name + CgenSupport.METHOD_SEP + m.name + CgenSupport.LABEL);
                // addiu	$sp $sp -12
                // sw	$fp 12($sp)
                // sw	$s0 8($sp)
                // sw	$ra 4($sp)
                // addiu	$fp $sp 4
                // move	$s0 $a0
                CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, -3 * CgenSupport.WORD_SIZE, str);
                CgenSupport.emitStore(CgenSupport.FP, 3, CgenSupport.SP, str);
                CgenSupport.emitStore(CgenSupport.SELF, 2, CgenSupport.SP, str);
                CgenSupport.emitStore(CgenSupport.RA, 1, CgenSupport.SP, str);
                CgenSupport.emitAddiu(CgenSupport.FP, CgenSupport.SP, CgenSupport.WORD_SIZE, str);
                CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, str);
                m.expr.code(str, n, this);
                // lw	$fp 12($sp)
                // lw	$s0 8($sp)
                // lw	$ra 4($sp)
                // addiu	$sp $sp 12
                // jr	$ra
                CgenSupport.emitLoad(CgenSupport.FP, 3, CgenSupport.SP, str);
                CgenSupport.emitLoad(CgenSupport.SELF, 2, CgenSupport.SP, str);
                CgenSupport.emitLoad(CgenSupport.RA, 1, CgenSupport.SP, str);
                // here we should count for arguments when we recover the stack pointer
                CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, (3 + m.formals.getLength()) * CgenSupport.WORD_SIZE, str);
                CgenSupport.emitReturn(str);
                // exit scope
                n.object_table.exitScope();
            }
        }
    }

    public void codePrototypeObjects() {
        // emit Object, IO, Int, Bool and String
        // DFS over the inheritance graph
        Stack<CgenNode> class_stack = new Stack<>();
        CgenNode current_class = (CgenNode)lookup(TreeConstants.Object_);
        class_stack.push(current_class);
        while(!class_stack.empty()) {
            current_class = class_stack.pop();
            // set the class tag
            setClassTag(current_class);
            // push each children into stack
            // (in order to match with the ref_cgen, we push in reverse order)
            Vector v = current_class.getChildrenVector();
            for(int i = v.size() - 1; i >= 0; i--) {
                class_stack.push((CgenNode)v.get(i));
            }
            int offset = 0;
            // prepare object_table
            current_class.object_table.enterScope();
            // skip for Object class
            if(current_class.getParent() != TreeConstants.No_class) {
                // push attrs from parent
                ArrayList<ClassFeaturePair> parent_table = current_class.getParentNd().getAttrTable();
                for(ClassFeaturePair pair : parent_table) {
                    current_class.getAttrTable().add(pair);
                    current_class.object_table.addId(pair.feature_name, new ObjectInfo(offset++, ObjectInfo.OBJTYPE.ATTR));
                }
            }
            // push local attrs
            for(int j = 0 ; j < current_class.getFeatures().getLength(); j++) {
                Feature f = (Feature)current_class.getFeatures().getNth(j);
                if(f instanceof attr) {
                    attr a = (attr) f;
                    // push attr into attr_table
                    current_class.pushAttr(current_class.name, a.name, a.type_decl);
                    // push attr into local_attr_table (why have a local attribute table)
                    current_class.pushLocalAttr(a);
                    // push attr into object_table
                    current_class.object_table.addId(a.name, new ObjectInfo(offset++, ObjectInfo.OBJTYPE.ATTR));
                }
            }
            // generate the prot object for current class
            // Add -1 eye catcher
            str.println(CgenSupport.WORD + "-1");
            CgenSupport.emitProtObjRef(current_class.name, str);
            str.print(CgenSupport.LABEL);
            str.println(CgenSupport.WORD + current_class.getClassTag()); // tag
            str.println(CgenSupport.WORD + (CgenSupport.DEFAULT_OBJFIELDS + current_class.getAttrTable().size())); //size
            str.print(CgenSupport.WORD);
            CgenSupport.emitDispTableRef(current_class.name, str);
            str.println(); // dispatch table pointer
            // emit all the attrs
            for(ClassFeaturePair pair : current_class.getAttrTable()) {
                str.print(CgenSupport.WORD);
                CgenSupport.emitProtObjAttr(pair.decl_type, str);
                str.println();
            }
            
        }
    }

    public int getLocalVariableCount() {
        return local_variable_count++;
    }

    public static int getCurrentLabel() {
        return LABEL_ACCU++;
    }

    public void decreaseLocalVariableCount() {
        local_variable_count--;
    }

    /** Gets the root of the inheritance tree */
    public CgenNode root() {
	return (CgenNode)probe(TreeConstants.Object_);
    }
}
			  
    
