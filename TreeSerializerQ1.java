// preorder traversal format was used for the order of printing the strings
import java.util.*;
// node class representing a binary tree node
class Node {
    Node right;
    Node left;
    int num;
}
public class TreeSerializerQ1 {
    // interface defining tree serialization and deserialization methods
    public interface TreeSerializer {
        String serialize(Node root);
        Node deserialize(String str);
    }

    // implementation of the TreeSerializer interface
    public static class TreeSerializer1 implements TreeSerializer {

        // using StringBuilder for string concatenation, taking node root as input, and returning a string
        public String serialize(Node root) {
            StringBuilder answer = new StringBuilder();
            serializeHelper(root, answer);
            return answer.toString();
        }

        // serialize helper function, using recursion in preorder traversal
        private void serializeHelper(Node node, StringBuilder answer) {
            if (node == null) {
                answer.append("null,");
            } else {
                answer.append(node.num).append(",");
                serializeHelper(node.left, answer);
                serializeHelper(node.right, answer);
            }
        }
        // making a Queue of strings from a serialized string for deserialization
        public Node deserialize(String str) {
            Queue<String> nodes = new LinkedList<>(Arrays.asList(str.split(",")));
            return deserializeHelper(nodes);
        }
        // deserialize helper function
        private Node deserializeHelper(Queue<String> nodes) {
            // gets and removes the first element of the queue from nodes
            String value = nodes.poll();
            if (value.equals("null")) {
                return null;
            } else {
                Node node = new Node();
                node.num = Integer.parseInt(value);
                node.left = deserializeHelper(nodes);
                node.right = deserializeHelper(nodes);
                return node;
            }
        }
    }
    public static void main(String[] args) {
        // creating a binary tree from the example
        Node root = new Node();
        root.num = 1;
        root.left = new Node();
        root.left.num = 2;
        root.right = new Node();
        root.right.num = 1;
        root.left.left = new Node();
        root.left.left.num = 7;
        root.left.left.left = new Node();
        root.left.left.left.num = 4;
        root.left.right = new Node();
        root.left.right.num = 5;
        root.right.right = new Node();
        root.right.right.num = 28;

        // instance for serialization
        TreeSerializer1 serializer = new TreeSerializer1();
        String serializedTree = serializer.serialize(root);
        System.out.println("Serialized tree: " + serializedTree);

        // instance for deserialization
        Node deserializedTree = serializer.deserialize(serializedTree);
        System.out.println("Deserialized tree: " + serializer.serialize(deserializedTree));
    }
}