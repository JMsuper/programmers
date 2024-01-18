import java.util.*;
import java.util.Collections;

class Solution {
    public int[] solution(int[][] dice) {
        int[] answer = {};
        
        // dice 내부 숫자 오름차순 정렬
        for(int i = 0; i < dice.length; i++){
            Arrays.sort(dice[i]);
        }
        
        // 모든 경우의 수를 포함하는 조합 생성
        int iterArr[] = new int[dice.length];
        Arrays.setAll(iterArr, i -> i+1);
            
        boolean[] visited = new boolean[dice.length];
        int m = dice.length / 2;
        
        ArrayList<int[]> diceCombiList = new ArrayList<>();
    
        combi(iterArr, visited,diceCombiList,0,m,m);

        // (1) 승리 횟수를 담는 맵 생성
        HashMap<int[], Integer> winCntMap = new HashMap<>();
        
        // (2) getWinAndLoseCnt 호출
        for(int[] myDiceArr : diceCombiList){
            
            int[] otherDiceArr = new int[myDiceArr.length];
            int cur = 0;
            for(int i = 1; i <= iterArr.length; i++){
                boolean isContain = false;
                for(int item : myDiceArr){
                    if(i == item){
                        isContain = true;
                        break;
                    }
                }
                if(isContain){
                    continue;
                }
                otherDiceArr[cur] = i;
                cur += 1;
            }

            Integer winCnt = getWinCnt(dice, myDiceArr, otherDiceArr);

            winCntMap.put(myDiceArr, winCnt);
        }

        int maxWinCnt = 0;

        for(int[] key : winCntMap.keySet()){
            if(winCntMap.get(key) > maxWinCnt){
                maxWinCnt = winCntMap.get(key);
                answer = key;
            }
        }
        
        return answer;
    }
    
    void combi(int[] arr, boolean[] visited, ArrayList<int[]> combiList, int start, int r, int m) {
        if(r == 0) {
            int[] newCombi = new int[m];
            int cur = 0;
            for(int i = 0; i < arr.length; i++){
                if(visited[i]){
                    newCombi[cur] = arr[i];
                    cur += 1;
                }
            }
            combiList.add(newCombi);
        } else {
            for(int i = start; i < arr.length; i++) {
                visited[i] = true;
                combi(arr, visited, combiList,i + 1, r - 1, m);
                visited[i] = false;
            }
        }
    }
        
    public int getWinCnt(int[][] dice, int[] myDiceArr, int[] otherDiceArr){
        int result = 0;
        
        ArrayList<Integer> mySumList = new ArrayList<>();
        ArrayList<Integer> otherSumList = new ArrayList<>();
        
        calculateDiceSumList(dice, myDiceArr, mySumList, 0, 0);
        calculateDiceSumList(dice, otherDiceArr, otherSumList, 0, 0);
        
        Collections.sort(mySumList);
        Collections.sort(otherSumList);
        
        int curIdx = otherSumList.size() / 2;
        for(int mySum : mySumList){

            int[] winCntAndIdx = binarySearch(otherSumList, mySum, 0, otherSumList.size(), curIdx,1);
            result += winCntAndIdx[0];
            curIdx = winCntAndIdx[1];
        }
        
        return result;
    }
    
    public void calculateDiceSumList(int[][] dice, int[] diceArr, ArrayList<Integer> sumList, int sum, int width){
        if(width >= diceArr.length){
            sumList.add(sum);
            return;
        }
        
        for(int i = 0 ; i < 6 ; i++){
            int value = dice[diceArr[width] - 1][i];
            calculateDiceSumList(dice, diceArr, sumList, sum + value, width + 1);
        }
    }
    
    // target 보다 작지만, 그 다음 인덱스는 target 보다 큰 인덱스를 찾는, 이진 탐색
    public static int[] binarySearch(ArrayList<Integer> otherSumList, int target, int lower, int upper, int curIdx, int cnt){
        if(curIdx >= otherSumList.size() || otherSumList.get(otherSumList.size() - 1) < target){
            return new int[]{ otherSumList.size() , curIdx};
        }
        if(curIdx < 0 || otherSumList.get(0) >= target){
            return new int[]{ 0, curIdx};
        }

        int val = otherSumList.get(curIdx);

        if(val >= target){
            return binarySearch(otherSumList, target, lower,curIdx, (int)Math.floor(lower + (double)(curIdx - lower) / 2), cnt + 1);
        }

        if(val < target){
            if (curIdx < otherSumList.size() - 1 && otherSumList.get(curIdx + 1) >= target){
                return new int[]{curIdx + 1 ,curIdx};
            }

            return binarySearch(otherSumList, target, curIdx,upper,(int) Math.floor((curIdx + ( (double)(upper - curIdx) / 2) )), cnt + 1);
        }

        return new int[]{-1,-1};
    }

}