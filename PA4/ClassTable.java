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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;

    public static HashMap<AbstractSymbol, ClassDef> class_map = new HashMap<>();
    public static List<AbstractSymbol> default_classes = Arrays.asList(TreeConstants.Object_, TreeConstants.IO);
    public static List<AbstractSymbol> base_classes = Arrays.asList(TreeConstants.Int, TreeConstants.Bool, TreeConstants.Str);
    public static List<AbstractSymbol> all_basic_classes = Arrays.asList(TreeConstants.Object_, TreeConstants.IO,
            TreeConstants.Int, TreeConstants.Bool, TreeConstants.Str);

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

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

	/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */


        // Add all basic classes into class_map
        ClassDef object_class_def = new ClassDef(Object_class, TreeConstants.Object_, null);
        object_class_def.addChildClasses(base_classes);
        object_class_def.addChildClass(TreeConstants.IO);
        class_map.put(TreeConstants.Object_, object_class_def);

        ClassDef io_class_def = new ClassDef(IO_class, TreeConstants.IO, TreeConstants.Object_);
        class_map.put(TreeConstants.IO, io_class_def);

        ClassDef bool_class_def = new ClassDef(Bool_class, TreeConstants.Bool, TreeConstants.Object_);
        class_map.put(TreeConstants.Bool, bool_class_def);

        ClassDef int_class_def = new ClassDef(Int_class, TreeConstants.Int, TreeConstants.Object_);
        class_map.put(TreeConstants.Int, int_class_def);

        ClassDef str_class_def = new ClassDef(Str_class, TreeConstants.Str, TreeConstants.Object_);
        class_map.put(TreeConstants.Str, str_class_def);

	// NOT TO BE INCLUDED IN SKELETON
	
	// Object_class.dump_with_types(System.err, 0);
	// IO_class.dump_with_types(System.err, 0);
	// Int_class.dump_with_types(System.err, 0);
	// Bool_class.dump_with_types(System.err, 0);
	// Str_class.dump_with_types(System.err, 0);
    }
	


    public ClassTable(Classes cls) {
	semantErrors = 0;
	errorStream = System.err;
	
	/* fill this in */

        // pass 0: load placeholder definitions for base classes
        installBasicClasses();

        // pass 1: read in class names
        // checks: duplicate class names
        for(int i = 0; i < cls.getLength(); i++) {
            class_c current_class = (class_c)cls.getNth(i);
            AbstractSymbol class_name = current_class.getName();
            if(class_name == TreeConstants.SELF_TYPE || all_basic_classes.contains(class_name)) {
                semantError(current_class).println("Redefinition of basic class " + class_name + ".");
                continue;
            } else if(class_map.containsKey(class_name)) {
                semantError(current_class).println("Class " + class_name.str + " was previously defined.");
                // why not continue here?
            }
            ClassDef new_class_def = new ClassDef(current_class, class_name, null);
            class_map.put(class_name, new_class_def);
        }

        // pass 2: read in parent and children information
        // checks: undefined parent
        for (int i = 0; i < cls.getLength(); i++) {
            class_c current_class = (class_c)cls.getNth(i);
            AbstractSymbol class_name = current_class.getName();
            AbstractSymbol parent_name = current_class.getParent();
            // SELF_TYPE is not in class_map, skip it
            if(class_name == TreeConstants.SELF_TYPE) {
                continue;
            }
            if (base_classes.contains(parent_name) || parent_name == TreeConstants.SELF_TYPE) {
                semantError(current_class).println("Class " + class_name + " cannot inherit class " + parent_name + ".");
            } else if(!class_map.containsKey(parent_name)) {
                semantError(current_class).println("Class " + class_name + " inherits from an undefined class " + parent_name + ".");
            } else {
                class_map.get(class_name).parent_class = parent_name;
                class_map.get(parent_name).child_classes.add(class_name);
            }
        }

        // inheritance check: cycles
        // do we really cheack cycles?
        for(AbstractSymbol current_class_name : class_map.keySet()) {
            if (default_classes.contains(current_class_name) || base_classes.contains(current_class_name))
                continue;
            AbstractSymbol parent_class_name = class_map.get(current_class_name).parent_class;
            List<AbstractSymbol> parents = new ArrayList<AbstractSymbol>();
            while(true) {
                if (parent_class_name == null) { System.out.println("is it possible to have parent_class_name == null?");}
                if (parent_class_name == current_class_name || parents.contains(parent_class_name)) {
                    semantError(class_map.get(current_class_name).class_src).println(
                            "Class " + current_class_name.str + ", or an ancestor of " + current_class_name.str + ", is involved in an inheritance cycle.");
                    break;
                } else if (default_classes.contains(parent_class_name) || base_classes.contains(parent_class_name)
                        || parent_class_name == null) { // we need to add null here to handle the case where the parent class is base class 
                        // is it possible to have parent_class_name == null?
                    break;
                }
                parents.add(parent_class_name);
                parent_class_name = class_map.get(parent_class_name).parent_class;
            }
        }
    }

    public static ClassDef getClassDef(AbstractSymbol a) {
        return class_map.get(a);
    }

    public static boolean hasClass(AbstractSymbol a) {
        return class_map.containsKey(a);
    }


    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
	errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }

    // NOT TO BE INCLUDED IN SKELETON
    public static void main(String[] args) {
	new ClassTable(null).installBasicClasses();
    }
}
			  
    
