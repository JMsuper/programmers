import java.util.HashMap;

class Solution {
    public int solution(String[] friends, String[] gifts) {
        
        // (1) 자료구조 세팅
        int friendsLeng = friends.length;
        int[][] giftTransferInfo = new int[friendsLeng][friendsLeng];
        HashMap<String, Integer> nameToIndex = new HashMap<>();
        int[][] totalTransferInfo = new int[friendsLeng][3];
        int[] expectedReceiveCountList = new int[friendsLeng];
        
        for(int i = 0; i < friendsLeng; i++){
            String name = friends[i];
            nameToIndex.put(name,i);
        }
        
        // (2) 데이터 수집 : 누가 누구와 선물을 얼만큼 주고 받았는가?
        for(int i = 0; i < gifts.length; i++){
            String[] txAndrx  = gifts[i].split(" ");
            
            String txName = txAndrx[0];
            int txIdx = nameToIndex.get(txName);
            
            String rxName = txAndrx[1];
            int rxIdx = nameToIndex.get(rxName);
            
            giftTransferInfo[txIdx][rxIdx] += 1;
            
            totalTransferInfo[txIdx][0] += 1;
            totalTransferInfo[rxIdx][1] += 1;
        }
        
        // (3) 데이터 수집 : 선물 지수 계산
        for(int i = 0; i < friendsLeng; i++){
            int txCnt = totalTransferInfo[i][0];
            int rxCnt = totalTransferInfo[i][1];
            int giftScore = txCnt - rxCnt;
            
            totalTransferInfo[i][2] = giftScore;
        }
        
        // (4) 데이터 활용 : 이번달 받을 선물 개수 예측
        for(int a = 0; a < friendsLeng; a++){
            for(int b = a + 1; b < friendsLeng; b++){
                int a2bCnt = giftTransferInfo[a][b];
                int b2aCnt = giftTransferInfo[b][a];
                
                if(a2bCnt < b2aCnt){
                    expectedReceiveCountList[b] += 1;
                }
                if(b2aCnt < a2bCnt){
                    expectedReceiveCountList[a] += 1;
                }
                if(a2bCnt == b2aCnt){
                    int giftScoreOfA = totalTransferInfo[a][2];
                    int giftScoreOfB = totalTransferInfo[b][2];
                    
                    if(giftScoreOfA < giftScoreOfB){
                        expectedReceiveCountList[b] += 1;
                    }
                    if(giftScoreOfB < giftScoreOfA){
                        expectedReceiveCountList[a] += 1;
                    }
                }
            }
        }
        
        int result = 0;
        
        for(int i = 0; i < friendsLeng; i++){
            int cur = expectedReceiveCountList[i];
            result = result < cur ? cur : result;
        }
        return result;
        
    }
}
