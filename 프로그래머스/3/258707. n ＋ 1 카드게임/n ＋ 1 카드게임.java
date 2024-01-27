import java.util.*;

class Solution {
    public int solution(int coin, int[] cards) {
        int totalRound = cards.length / 3;

        ArrayList<Integer>[] roundArray = new ArrayList[totalRound];
        int curRound = 0;
        int roundTicket = 0;

        HashSet<Integer> checkedNumSet = new HashSet<>();
        // 0 ~ n/3 까지 초기 덱 처리
        for(int i = 0; i < cards.length; i++){
            int iNum = cards[i];
            int iRound = i < cards.length / 3 ? -1 : (i - cards.length / 3) / 2;

            if(checkedNumSet.contains(iNum)){
                continue;
            }
            checkedNumSet.add(iNum);

            for(int j = i + 1; j < cards.length; j++){
                int jNum = cards[j];
                int jRound = j < cards.length / 3 ? -1 : (j - cards.length / 3) / 2;

                if (checkedNumSet.contains(jNum)){
                    continue;
                }

                if( (iNum + jNum) == (cards.length + 1) ){
                    if(iRound < 0 && jRound < 0){
                        roundTicket += 1;
                        checkedNumSet.add(jNum);
                        break;
                    }

                    if(roundArray[jRound] == null){
                        roundArray[jRound] = new ArrayList<>();
                    }

                    if(iRound >= 0){
                        roundArray[jRound].add(2);
                    }else{
                        roundArray[jRound].add(1);
                    }
                }
            }
        }

        int oneCoin = 0;
        int twoCoin = 0;

        for(int i = 0; i <= cards.length / 3; i++) {
            curRound += 1;

            if(i < cards.length / 3){
                ArrayList<Integer> cardSetList = roundArray[i];
                if(cardSetList != null){
                    for(int j = 0; j < cardSetList.size(); j++){
                        if (cardSetList.get(j) == 2){
                            twoCoin += 1;
                        }else {
                            oneCoin += 1;
                        }
                    }
                }
            }
            
            if(roundTicket > 0){
                roundTicket -= 1;
                continue;
            }
            if(oneCoin > 0 && coin >= 1){
                coin -= 1;
                oneCoin -= 1;
            }else if(twoCoin > 0 && coin >= 2){
                coin -= 2;
                twoCoin -= 1;
            }else{
                break;
            }
        }

        return curRound;
    }
}