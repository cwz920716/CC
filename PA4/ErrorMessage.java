/**
 * @author Yuhao Zhang
 */
public class ErrorMessage {
    private TreeNode node;
    private String message;

    public ErrorMessage(TreeNode node, String message) {
        this.node = node;
        this.message = message;
    }

    public TreeNode getErrorNode() {
        return node;
    }

    public String getMessage() {
        return message;
    }

    public void setErrorNode(TreeNode node) {
        this.node = node;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return node.lineNumber + " > " + message;
    }
}
