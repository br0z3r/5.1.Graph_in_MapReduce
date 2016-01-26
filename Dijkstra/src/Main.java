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
    private static ArrayList<Integer> visitedNode = new ArrayList<>();
    private static HashMap<Integer, HashMap<Integer,Integer>> graph = new HashMap<>();
    private static  Integer baseNode, destNode;


    public static void main(String[] args) throws IOException {
        InputStream stdin = null;
        stdin = System.in;
        FileInputStream stream = new FileInputStream("/home/air/IdeaProjects/BigData/5.Algorithms_on_graph_MapReduce/5.1.Graph_in_MapReduce/Dijkstra/test2");
        System.setIn(stream);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        readTask(bufferedReader);
        System.out.println(baseNode);
        System.out.println(destNode);
        System.out.println(getDistance(baseNode,destNode));

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
        Integer node , destNode ,weight;

        HashMap<Integer,Integer> tmpValue;

        graph.clear();

        for (int i = 1; i <= countNodes ; i++) {
            tmpValue = new HashMap<>();
            tmpValue.put(i,0);
            graph.put(i, tmpValue);
        }

        for (int i = 0; i < countArcs; i++) {
            line = bufferedReader.readLine();
            if(line == null || line.isEmpty()) {
                continue;
            }

            st = new StringTokenizer(line);
            node = new Integer(st.nextToken());
            destNode = new Integer(st.nextToken());
            weight =   new Integer(st.nextToken());

            tmpValue = graph.get(node);
            if(tmpValue.containsKey(destNode)){
                if(tmpValue.get(destNode)<weight) {
                    tmpValue.put(destNode,weight);
                } else {
                    continue;
                }
            }
            tmpValue.put(destNode,weight);
        }
    }

    private static void optimizeDistance(Integer node, Integer offset) {

        Integer tmpNode, tmpDistance;

        if(!graph.containsKey(node)) {
            return ;
        }
        for (Map.Entry<Integer,Integer> key:graph.get(node).entrySet()) {
            tmpNode = key.getKey();
            tmpDistance =key.getValue() + offset;
            if(!distance.containsKey(tmpNode) || distance.get(tmpNode)> tmpDistance){
                distance.put(tmpNode,tmpDistance);
            }
        }

    }

    private static Integer getMinUnvisited() {
        Integer result = -1;
        Integer minDistance = Integer.MAX_VALUE;
        Integer tmpNode, tmpDistance;

        for (Map.Entry<Integer,Integer> key:distance.entrySet()) {
            tmpNode = key.getKey();
            tmpDistance = key.getValue();
            if(minDistance > tmpDistance && graph.containsKey(tmpNode)) {
                result = tmpNode;
                minDistance = tmpDistance;
            }
        }
//        System.out.println("getMinUnvisited() =" + minDistance + " node = " + result);
        return result;
    }

    private static void removeNode(Integer node) {
        if(!graph.containsKey(node)) {
            return;
        }
        graph.remove(node).clear();

    }

    private static int getDistance(Integer startNode, Integer finishNode) {
        Integer pathLength = -1;

        Integer visitedNode = startNode;
        Integer offset = graph.get(startNode).get(startNode);
        optimizeDistance(startNode,offset);
        removeNode(startNode);

        while (visitedNode.compareTo(finishNode)!=0 && (visitedNode = getMinUnvisited())>0) {
            offset = distance.get(visitedNode);
            optimizeDistance(visitedNode,offset);
            removeNode(visitedNode);
        }

        if(distance.containsKey(finishNode)) {
            pathLength = distance.get(finishNode).intValue();
        }

        return pathLength;
    }



}
