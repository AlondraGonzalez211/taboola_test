import java.util.*;
// node class is used from the TreeSerializerQ1 file
public class TreeSerializerQ2 {
    // interface with tree serialization and deserialization methods
    public interface TreeSerializer {
        String serialize(Node root);
        Node deserialize(String str);
    }

    // implementation of TreeSerializer interface
    public static class TreeSerializer2 implements TreeSerializer {
        // serializes the tree into a string
        public String serialize(Node root) {
            // keeps track of visited nodes during serialization
            Set<Node> visited = new HashSet<>();
            // StringBuilder for string concatenation
            StringBuilder answer = new StringBuilder();
            serializeHelper(root, answer, visited);
            return answer.toString();
        }

        // serialize helper function
        private void serializeHelper(Node node, StringBuilder answer, Set<Node> visited) {
            // if node is null, add "null" to answer
            if (node == null) {
                answer.append("null,");
            } else {
                // checks if the visited node is cyclic
                if (visited.contains(node)) {
                    // Throws a runtime exception when a cyclic tree is found
                    throw new RuntimeException("Cyclic tree found during serialization");
                }
                visited.add(node);

                // appends the node value to the answer
                answer.append(node.num).append(",");
                // recursively calls the helper function for left and right children
                serializeHelper(node.left, answer, visited);
                serializeHelper(node.right, answer, visited);
                // removes the node from visited after processing its children
                visited.remove(node);
            }
        }
        // deserializes the string into a tree
        public Node deserialize(String str) {
            // Converts the serialized string into a queue of node values
            Queue<String> nodes = new LinkedList<>(Arrays.asList(str.split(",")));
            // HashSet to keep track of visited nodes during deserialization
            Set<Node> visited = new HashSet<>();
            return deserializeHelper(nodes, visited);
        }
        // deserialize helper function
        private Node deserializeHelper(Queue<String> nodes, Set<Node> visited) {
            // gets and removes the first element of the queue
            String val = nodes.poll();
            if (val.equals("null")) {
                return null;
            } else {
                // creates a new node with parsed value
                Node node = new Node();
                node.num = Integer.parseInt(val);
                // throws a runtime exception if cyclic tree is found
                if (visited.contains(node)) {
                    throw new RuntimeException("Cyclic tree detected during deserialization");
                }
                visited.add(node);

                // recursively calls the helper function for left and right children
                node.left = deserializeHelper(nodes, visited);
                node.right = deserializeHelper(nodes, visited);
                // removes the node from the visited after processing its children
                visited.remove(node);
                return node;
            }
        }
        public static void main(String[] args) {
            // creating cyclic tree from the example
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
            // Creating a cyclic node
            root.left.right.right = root;
            root.right.right = new Node();
            root.right.right.num = 28;

            // instance of TreeSerializer2
            TreeSerializer2 serializer = new TreeSerializer2();
            try {
                // serialize and print
                String serializedTree = serializer.serialize(root);
                System.out.println("Serialized tree: " + serializedTree);
            } catch (RuntimeException e) {
                // handling exception if a cyclic tree is found during serialization
                System.out.println("Exception during serialization: " + e.getMessage());
            }
            try {
                // deserialize and print
                String serializedTree = serializer.serialize(root);
                Node deserializedTree = serializer.deserialize(serializedTree);
                System.out.println("Deserialized tree: " + serializer.serialize(deserializedTree));
            } catch (RuntimeException e) {
                // handling exception if a cyclic tree is found during deserialization
                System.out.println("Exception during deserialization: " + e.getMessage());
            }
        }
    }
}