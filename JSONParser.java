import java.util.*;
public class JSONParser {
    // parsing function that converts a JSON string to a Map
    public static Map<String, Object> parse(String json) {
        return parseObject(json);
    }
    // recursive function to parse an object within the JSON string
    private static Map<String, Object> parseObject(String json) {
        Map<String, Object> map = new HashMap<>();
        json = json.trim();

        // checks if it is an object
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
            String[] keyValuePairs = json.split(",");

            // iterate through key-value pairs
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":", 2);
                String key = keyValue[0].trim().replaceAll("\"", ""); // Remove quotes from the key
                String value = keyValue[1].trim();
                map.put(key, parseValue(value)); // Parse the value and add to the map
            }
        }
        return map;
    }

    // parse helper function to parse values based on their type (object, string, integer)
    private static Object parseValue(String value) {
        if (value.startsWith("{")) {
            return parseObject(value);
        } else if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else {
            // Remove non-digit characters before parsing the integer
            String cleanedValue = value.replaceAll("[^0-9]", "");
            return Integer.parseInt(cleanedValue);
        }
    }
    public static void main(String[] args) {
    // input using example from the doc
        String input = "{\n" +
                "\"debug\" : \"on\",\n" +
                "\"window\" : {\n" +
                "\"title\" : \"sample\",\n" +
                "\"size\": 500\n" +
                "}\n" +
                "}";
        // System.out.println(input);
        // checking if parsing was successful
        Map<String, Object> output = JSONParser.parse(input);
        assert output.get("debug").equals("on");
        assert ((Map<String, Object>) output.get("window")).get("title").equals("sample");
        assert ((Map<String, Object>) output.get("window")).get("size").equals(500);

        System.out.println("JSON parsing successful!");
//        System.out.print(output);
    }
}