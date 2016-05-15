import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuhao Zhang
 */
public class ClassDef {
    public class_c class_src = null;
    public AbstractSymbol class_name = null;

    public AbstractSymbol parent_class = null;
    public ArrayList<AbstractSymbol> child_classes = new ArrayList<>();
    public SymbolTable method_table = new SymbolTable();
    public SymbolTable attr_table = new SymbolTable();

    public ClassDef(class_c class_src, AbstractSymbol class_name, AbstractSymbol parent_class) {
        this.class_src = class_src;
        this.class_name = class_name;
        this.parent_class = parent_class;
    }

    public void addChildClass(AbstractSymbol child_class) {
        this.child_classes.add(child_class);
    }

    public void addChildClasses(List<AbstractSymbol> child_classes) {
        this.child_classes.addAll(child_classes);
    }

    public void enterBothScopes() {
        method_table.enterScope();
        attr_table.enterScope();
    }

    public void exitBothScopes() {
        method_table.exitScope();
        attr_table.exitScope();
    }
}
