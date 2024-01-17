import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

class Solution {
    public int[] solution(int[][] edges) {
        // * 생성된 정점이 될 가능성이 있는 조건
        // 1. 생성된 정점(을) 가리키는 정점이 없어야됨
        // 2. 생성된 정점(이) 가리키는 정점은 2개 이상이여야 함
        int[] answer = {0,0,0,0};
        
        HashMap<Integer, ArrayList<Integer>> adjList = new HashMap<>();
        HashSet<Integer> toNodeSet = new HashSet<>();
        
        for(int i = 0; i < edges.length; i++){
            int from = edges[i][0];
            int to = edges[i][1];
            
            if(adjList.containsKey(from) == false){
                adjList.put(from,new ArrayList<Integer>());
            }
            
            ArrayList<Integer> edgeList = adjList.get(from);
            edgeList.add(to);
            toNodeSet.add(to);
        }
        
        HashSet<Integer> noReverseEdgeNodeSet = new HashSet(adjList.keySet());
        noReverseEdgeNodeSet.removeAll(toNodeSet);
        
        int newNodeNum = -1;
        for(int nodeNum : noReverseEdgeNodeSet){
            if(adjList.get(nodeNum).size() >= 2){
                newNodeNum = nodeNum;
            }
        }
        answer[0] = newNodeNum;
        

        for(int nodeNum : adjList.get(newNodeNum)){
            String type = getGraphType(nodeNum, adjList);
            
            switch(type){
                case "donut": 
                    answer[1] += 1;
                    break;
                case "bar":
                    answer[2] += 1;
                    break;
                case "eight":
                    answer[3] += 1;
                    break;
            }
        }
        
        return answer;
    }
    
    // * 생성된 점점과 연결된 그래프가 어떤 그래프인지 특정하는 방법
    //
    // - 막대 모양 그래프
    //   - 간선을 따라가다 보면 아무것도 연결되지 않은 정점에 도달한다
    // - 도넛 모양 그래프
    //   - 특정 노드를 두 번 방문했는데, 해당 노드의 간선이 1개라면 도넛 모양 그래프
    // - 8자 모양 그래프
    //   - 위와 연결지어, 간선이 2개라면 8자 모양 그래프
    //
    // "donut" , "bar" , "eight"
    public String getGraphType(int firstNodeNum, final HashMap<Integer,ArrayList<Integer>> adjList){
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer,Integer> visitCntMap = new HashMap<>();
        
        stack.push(firstNodeNum);
        
        while(stack.isEmpty() == false){
            int nodeNum = stack.pop();
            
            if(visitCntMap.containsKey(nodeNum) == false){
                visitCntMap.put(nodeNum, 0);
            }

            visitCntMap.put(nodeNum, visitCntMap.get(nodeNum) + 1);
            if(visitCntMap.get(nodeNum) >= 2){
                return "eight";
            }
        
            ArrayList<Integer> edgeList = adjList.get(nodeNum);
            if(edgeList == null){
                return "bar";
            }

            for(int num : edgeList){
                if( 
                   visitCntMap.keySet().contains(num) && adjList.get(num).size() == 1){
                    continue;
                }
                stack.push(num);
            } 
        }
        return "donut";
        
    }
    
}