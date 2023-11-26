import java.util.*;
// node class is used from the TreeSerializerQ1 file
public class TreeSerializerQ2Bonus {
    // interface defining tree serialization and deserialization methods
    public interface TreeSerializer2B {
        String serialize(Node root);
        Node deserialize(String str);
    }
    // implementation of TreeSerializer2B for cyclic tree serialization
    public static class CyclicTreeSerializer implements TreeSerializer2B {
        public String serialize(Node root) {
            // map to store visited nodes and respective ids
            Map<Node, Integer> visited = new HashMap<>();
            // StringBuilder for  string concatenation
            StringBuilder answer = new StringBuilder();
            // calls the helper function for serialization
            serializeHelper(root, answer, visited);
            return answer.toString();
        }
        // helper function for serialize
        private void serializeHelper(Node node, StringBuilder answer, Map<Node, Integer> visited) {
            if (node == null) {
                answer.append("null,");
            } else {
                // if the node is already visited, add that it is a reference
                if (visited.containsKey(node)) {
                    answer.append("ref").append(visited.get(node)).append(",");
                } else {
                    // if the node is not visited, assign it a new id and add it to the visited map
                    int id = visited.size() + 1;
                    visited.put(node, id);
                    // append the node value to the answer string
                    answer.append(node.num).append(",");
                    // recursively call the helper function for left and right children
                    serializeHelper(node.left, answer, visited);
                    serializeHelper(node.right, answer, visited);
                }
            }
        }
        public Node deserialize(String str) {
            // converts the serialized string into a queue of node values
            Queue<String> nodes = new LinkedList<>(Arrays.asList(str.split(",")));
            // map to store visited ids and corresponding nodes
            Map<Integer, Node> visited = new HashMap<>();
            // calls the helper function for deserialization
            return deserializeHelper(nodes, visited);
        }
        // deserialize helper function
        private Node deserializeHelper(Queue<String> nodes, Map<Integer, Node> visited) {
            // gets and removes the first element of the queue
            String val = nodes.poll();
            // if the value is "null", return null
            if (val.equals("null")) {
                return null;
            } else if (val.startsWith("ref")) {
                // if the value starts with "ref", there's a reference to a previously visited node
                int id = Integer.parseInt(val.substring(3));
                return visited.get(id);
            } else {
                // if the value is a node value, create a new node and assign an id
                Node node = new Node();
                node.num = Integer.parseInt(val);
                int id = visited.size() + 1;
                visited.put(id, node);
                // recursively call the helper function for left and right children
                node.left = deserializeHelper(nodes, visited);
                node.right = deserializeHelper(nodes, visited);

                return node;
            }
        }

        public static void main(String[] args) {
            // creating cyclic tree from given example
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

            // instance of CyclicTreeSerializer
            CyclicTreeSerializer serializer = new CyclicTreeSerializer();

            // serializing the tree and printing the answer
            String serializedTree = serializer.serialize(root);
            System.out.println("Serialized tree: " + serializedTree);

            // deserializing the tree and printing
            Node deserializedTree = serializer.deserialize(serializedTree);
            System.out.println("Deserialized tree: " + serializer.serialize(deserializedTree));
        }
    }
}