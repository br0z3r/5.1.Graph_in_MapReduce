import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by air on 25.01.16.
 */
public class Main {

    /**
     * {distance} - дистанция
     * {visitedNode} - посещенные узлы
     */
    private static HashMap<Integer,Integer> distance = new HashMap<>();
    private static  ArrayList<Integer> visitedNode = new ArrayList<>();
    private static HashMap<Integer, HashMap<Integer,Integer>> graph = new HashMap<>();
    private static  Integer baseNode, destNode;


    public static void main(String[] args) throws IOException {
        InputStream stdin = null;
        stdin = System.in;
        FileInputStream stream = new FileInputStream("/home/air/IdeaProjects/BigData/5.Algorithms_on_graph_MapReduce/5.1.Graph_in_MapReduce/Dijkstra/test");
        System.setIn(stream);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        readTask(bufferedReader);
        System.out.println(baseNode);
        System.out.println(destNode);

    }


    private static boolean readTask(BufferedReader bufferedReader) throws IOException {
        StringTokenizer st;
        String line;
        line = bufferedReader.readLine();

        Integer countNodes , countArcs;

        st = new StringTokenizer(line);
        if(st.countTokens()!=2) {
            return false;
        }

        countNodes = new Integer(st.nextToken());
        countArcs = new Integer(st.nextToken());

        readGraph(bufferedReader,countNodes,countArcs);

        line = bufferedReader.readLine();
        st = new StringTokenizer(line);
        if(st.countTokens()!=2) {
            return false;
        }

        baseNode = new Integer(st.nextToken());
        destNode = new Integer(st.nextToken());

        return true;
    }

    private static void readGraph(BufferedReader bufferedReader,int countNodes, int countArcs) throws IOException {

        StringTokenizer st;
        String line;
        Integer node , destNode ,weigth;

        HashMap<Integer,Integer> tmpValue;

        graph.clear();

        for (int i = 1; i <= countNodes ; i++) {
            tmpValue = new HashMap<>();
            tmpValue.put(i,0);
            graph.put(i, tmpValue);
        }

        for (int i = 0; i < countArcs; i++) {
            line = bufferedReader.readLine();
            if(line == null || !line.isEmpty()) {
                continue;
            }

            st = new StringTokenizer(line);
            node = new Integer(st.nextToken());
            destNode = new Integer(st.nextToken());
            weigth =   new Integer(st.nextToken());

            tmpValue = graph.get(node);
            tmpValue.put(destNode,weigth);
        }
    }

    private static HashMap<Integer,Integer> getDistance(HashMap<Integer,HashMap<Integer,Integer>>graph,
                                                        Integer node, Integer offset) {

        HashMap<Integer,Integer>result = new HashMap<>();
        if(!graph.containsKey(node)) {
            return null;
        }

        for (Map.Entry<Integer,Integer> key:graph.get(node).entrySet()) {
            result.put(key.getKey(),key.getValue() + offset);
        }

        return result;
    }
    
}
