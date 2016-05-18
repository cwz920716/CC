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

import java.util.*;

class CgenNode extends class_c {
    /**
     * The parent of this node in the inheritance tree
     */
    private CgenNode parent;

    /**
     * The children of this node in the inheritance tree
     */
    private Vector children;

    /**
     * Indicates a basic class
     */
    final static int Basic = 0;

    /**
     * Indicates a class that came from a Cool program
     */
    final static int NotBasic = 1;

    /**
     * Does this node correspond to a basic class?
     */
    private int basic_status;

    private int class_tag;

    /**
     * method_table and attr_table keep the methods and attrs from ancestors
     * in the current class, and are used for generating dispatch tables.
     */
    private ArrayList<ClassFeaturePair> method_table = new ArrayList<>();
    private ArrayList<ClassFeaturePair> attr_table = new ArrayList<>();

    /**
     * method_table and attr_table only keep the local methods and attrs
     * in the current class, and are used for generating prototype objects.
     */
    private ArrayList<method> local_method_table = new ArrayList<>();
    private ArrayList<attr> local_attr_table = new ArrayList<>();

    /**
     * object_table is used for scoping and retrieving object information.
     */
    public SymbolTable object_table = new SymbolTable();

    /**
     * method_map is used for mapping method name to offset in the dispatch table.
     */
    public HashMap<AbstractSymbol, Integer> method_map = new HashMap<>();

    public void pushLocalAttr(attr a) {
        this.local_attr_table.add(a);
    }

    public ArrayList<attr> getLocalAttrTable() {
        return this.local_attr_table;
    }

    public void pushLocalMethod(method m) {
        this.local_method_table.add(m);
    }

    public ArrayList<method> getLocalMethodTable() {
        return this.local_method_table;
    }

    public int getClassTag() {
        return class_tag;
    }

    public void setClassTag(int t) {
        this.class_tag = t;
    }

    public ArrayList<ClassFeaturePair> getMethodTable() {
        return this.method_table;
    }

    public ArrayList<ClassFeaturePair> getAttrTable() {
        return this.attr_table;
    }

    public void pushMethod(AbstractSymbol className, AbstractSymbol methodName) {
        ClassFeaturePair pair = new ClassFeaturePair(className, methodName);
        int index = method_table.indexOf(pair);
        if(index != -1) {
            method_table.remove(index);
            method_table.add(index, pair);
            return;
        }
        method_table.add(pair);
    }

    public void pushAttr(AbstractSymbol class_name, AbstractSymbol attr_name, AbstractSymbol decl_type) {
        attr_table.add(new ClassFeaturePair(class_name, attr_name, decl_type));
    }

    /**
     * Constructs a new CgenNode to represent class "c".
     *
     * @param c            the class
     * @param basic_status is this class basic or not
     * @param table        the class table
     */
    CgenNode(Class_ c, int basic_status, CgenClassTable table) {
        super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
        this.parent = null;
        this.children = new Vector();
        this.basic_status = basic_status;
        AbstractTable.stringtable.addString(name.getString());
    }

    void addChild(CgenNode child) {
        children.addElement(child);
    }

    /**
     * Gets the children of this class
     *
     * @return the children
     */
    Enumeration getChildren() {
        return children.elements();
    }

    public Vector getChildrenVector() {
        return children;
    }

    /**
     * Sets the parent of this class.
     *
     * @param parent the parent
     */
    void setParentNd(CgenNode parent) {
        if (this.parent != null) {
            Utilities.fatalError("parent already set in CgenNode.setParent()");
        }
        if (parent == null) {
            Utilities.fatalError("null parent in CgenNode.setParent()");
        }
        this.parent = parent;
    }


    /**
     * Gets the parent of this class
     *
     * @return the parent
     */
    CgenNode getParentNd() {
        return parent;
    }

    /**
     * Returns true is this is a basic class.
     *
     * @return true or false
     */
    boolean basic() {
        return basic_status == Basic;
    }
}

class ClassFeaturePair {
    AbstractSymbol class_name;
    AbstractSymbol feature_name;
    AbstractSymbol decl_type;

    public ClassFeaturePair(AbstractSymbol className, AbstractSymbol methodName) {
        this.class_name = className;
        this.feature_name = methodName;
        this.decl_type = null;
    }

    public ClassFeaturePair(AbstractSymbol className, AbstractSymbol methodName, AbstractSymbol decl_type) {
        this.class_name = className;
        this.feature_name = methodName;
        this.decl_type = decl_type;
    }

    @Override
    public String toString() {
        return class_name.toString() + CgenSupport.METHOD_SEP + feature_name.toString();
    }

    @Override
    public boolean equals(Object o) {
        ClassFeaturePair pair = (ClassFeaturePair)o;
        return this.feature_name == pair.feature_name;
    }
}

class ObjectInfo {
    public int offset;
    public OBJTYPE obj_type;

    public enum OBJTYPE {
        ATTR, FORMAL, LOCAL
    }

    public ObjectInfo(int offset, OBJTYPE obj_type) {
        this.offset = offset;
        this.obj_type = obj_type;
    }
}
    

    
