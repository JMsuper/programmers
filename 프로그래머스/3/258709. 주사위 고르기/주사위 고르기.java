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
        // diceCombiList.forEach(i -> print(i));
        
        
        // 각 조합에 대한 승 횟수 계산
        // (1) HashMap : Key = 조합의 경우, Value = 승 횟수
        // - 함수 호출 조건 : 나의 조합이 승 횟수를 담은 맵에 없어야 함 
        //   -> 있다면, 다른 조합을 통해 이미 도출된것
        // (2) 나의 조합과 상대 조합을 함수에 넘겨줌
        //   - 승리 횟수 반환
        //   -> HashMap에 나의 조합에 해당하는 Key에 승 횟수 입력
        
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
            // print(myDiceList);
            // print(otherDiceList);
            Integer winCnt = getWinCnt(dice, myDiceArr, otherDiceArr);

            winCntMap.put(myDiceArr, winCnt);
        }

        int maxWinCnt = 0;
        // System.out.println(winCntMap);
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

    void print(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
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
            
            // System.out.println(mySum);
            // System.out.println(otherSumList);

            int[] winCntAndIdx = binarySearch(otherSumList, mySum, 0, otherSumList.size(), curIdx,1);
            result += winCntAndIdx[0];
            curIdx = winCntAndIdx[1];
            // System.out.println(winCntAndIdx[0]);
            // System.out.println(curIdx);
            // System.out.println(result);
            // System.out.println();
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
        if(cnt > 50){
            System.out.println(target + " " +  lower + " " +   upper + " " +   curIdx + " " +   cnt);
            System.out.println("HERE~");
            return new int[]{-1,-1};
            
        }
        if(curIdx >= otherSumList.size() || otherSumList.get(otherSumList.size() - 1) < target){
            return new int[]{ otherSumList.size() , curIdx};
        }
        if(curIdx < 0 || otherSumList.get(0) >= target){
            return new int[]{ 0, curIdx};
        }

        // val >= target -> curIdx = curIdx / 2
        // val < target -> curIdx = curIdx + ( arr.length - curIdx ) / 2

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