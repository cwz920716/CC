import java.util.*;

/**
 * @author Michael Hang, Yuhao Zhang
 */
public class SemantUtils {

    // Util functions for naming and scoping
    public static boolean existsClass(AbstractSymbol class_name) {
        if(ClassTable.class_map.containsKey(class_name)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compare two formal list to see if the types match with each other. This is used to evaluate whether
     * a valid overriding is done by a method in a class. Note that the variable name does not need to match.
     * @param fl1
     * @param fl2
     * @return whether the two formal list match
     */
    public static boolean equalsFormalList(Formals fl1, Formals fl2) {
        if(fl1.getLength() != fl2.getLength()) {
            return false;
        }
        for(int i = 0; i < fl1.getLength(); i++) {
            formalc f1 = (formalc)fl1.getNth(i);
            formalc f2 = (formalc)fl2.getNth(i);
            if(f1.type_decl != f2.type_decl) {
                return false;
            }
        }
        return true;
    }

    // Util functions for type checking and type inference

    /**
     * Get the type of an object from a symbol table. If the symbol table does not contain the object, return null.
     * @param name
     * @param attr_table
     * @return type of object 'name'
     */
    public static AbstractSymbol typeOfObject(AbstractSymbol name, SymbolTable attr_table) {
        attr value = (attr)attr_table.lookup(name);
        if(value == null) return null;
        return value.type_decl;
    }

    public static boolean conformsTo(AbstractSymbol t1, AbstractSymbol t2, AbstractSymbol class_name) {
        if(t1 == TreeConstants.SELF_TYPE && t2 == TreeConstants.SELF_TYPE) return true;
        if(t1 == TreeConstants.SELF_TYPE && t2 != TreeConstants.SELF_TYPE) {
            t1 = class_name;
            return isSubType(t1, t2);
        }
        if(t1 != TreeConstants.SELF_TYPE && t2 == TreeConstants.SELF_TYPE) return false; // it is possible t1 issubtype of t2?
        return isSubType(t1, t2);
    }

    public static boolean isSubType(AbstractSymbol t1, AbstractSymbol t2) {
        if(!ClassTable.hasClass(t1) || !ClassTable.hasClass(t2)) {
            return false;
        }
        if (t1 == t2) return true;
        ClassDef c1 = ClassTable.getClassDef(t1);
        ClassDef c2 = ClassTable.getClassDef(t2);
        AbstractSymbol parent_class = c1.parent_class;
        while(true) {
            if(parent_class == null) return false;
            if(parent_class == c2.class_name) {
                return true;
            } else {
                parent_class = ClassTable.getClassDef(parent_class).parent_class;
            }
        }
    }

    /**
     * Get the least upper bound of two types.
     * @param t1
     * @param t2
     * @param class_name
     * @return lub
     */
    public static AbstractSymbol getlub(AbstractSymbol t1, AbstractSymbol t2, AbstractSymbol class_name) {
        if(t1 == TreeConstants.SELF_TYPE && t2 == TreeConstants.SELF_TYPE) return t1;
        if(t1 == TreeConstants.SELF_TYPE) t1 = class_name;
        if(t2 == TreeConstants.SELF_TYPE) t2 = class_name;
        if(t1 == t2) return t1;
        // Get the path to root for both types
        List<AbstractSymbol> p1 = pathToRoot(t1);
        List<AbstractSymbol> p2 = pathToRoot(t2);
        Collections.reverse(p1);
        Collections.reverse(p2);
        // Look for common ancestor
        AbstractSymbol lub = TreeConstants.Object_;
        int shortest_length = p1.size() < p2.size() ? p1.size() : p2.size();
        for(int i = 0; i < shortest_length; i++) {
            AbstractSymbol a1 = p1.get(i);
            AbstractSymbol a2 = p2.get(i);
            if (a1 != a2) {
                return p1.get(i-1);
            }
        }
        // If one list is a sublist of the other: return the last element
        return p1.get(shortest_length - 1);
    }

    /**
     * Get the path from the type t to the root type Object.
     * @param t
     * @return path
     */
    public static List<AbstractSymbol> pathToRoot(AbstractSymbol t) {
        ArrayList<AbstractSymbol> path = new ArrayList<>();
        while(t != null) {
            path.add(t);
            t = ClassTable.getClassDef(t).parent_class;
        }
        return path;
    }

    /**
     * Get the least upper bound of multiple types.
     * @param s
     * @param class_name
     * @return
     */
    public static AbstractSymbol getlubFromTypes(Set<AbstractSymbol> s, AbstractSymbol class_name) {
        Iterator<AbstractSymbol> iter = s.iterator();
        if(s.size() == 1) {
            return iter.next();
        }
        AbstractSymbol result = iter.next();
        while(iter.hasNext()) {
            result = getlub(result, iter.next(), class_name);
        }
        return result;
    }

//    public static void conformToFormalList(Formals fs, ArrayList<AbstractSymbol> types, List<ErrorMessage> error_msg_list, Expression current_expr) {
//        if(fs.getLength() != types.size()) {
//            error_msg_list.add(new ErrorMessage(current_expr, "Method init called with wrong number of arguments."));
//        }
//        for(int i = 0; i < fs.getLength(); i++) {
//            formalc f = (formalc)fs.getNth(i);
//            if(!isSubType(types.get(i), f.type_decl, )) {
//                error_msg_list.add(new ErrorMessage(current_expr, "In call of method init, type Bool of parameter y does not conform to declared type Int."));
//            }
//        }
//    }
}
