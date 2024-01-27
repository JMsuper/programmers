import java.util.*;

class Solution {
    public int solution(int coin, int[] cards) {
        HashSet<Integer> handCardSet = new HashSet<>();
        HashSet<Integer> remainedCardSet = new HashSet<>();

        int sumValue = cards.length + 1;
        int roundIdx = cards.length / 3;
        int curRound = 0;

        for(int i = 0; i < cards.length / 3; i++){
            handCardSet.add(cards[i]);
        }

        boolean canHandCardSet = true;
        for(int i = 0; i <= cards.length / 3; i++){
            curRound += 1;

            if(roundIdx + (i * 2) < cards.length){
                remainedCardSet.add(cards[roundIdx + (i * 2)]);
                remainedCardSet.add(cards[roundIdx + (i * 2) + 1]);
            }

            if(canHandCardSet && cardSetJoin(handCardSet, sumValue)){
                continue;
            }else {
                canHandCardSet = false;
            }

            if (coin >= 1 && containedCardJoinWithRemained(handCardSet,remainedCardSet,sumValue)) {
                coin -= 1;
                continue;
            }

            if(coin >= 2 &&cardSetJoin(remainedCardSet,sumValue)){
                coin -= 2;
            }else{
                break;
            }
        }
        return curRound;
    }

    public boolean cardSetJoin(HashSet<Integer> cardSet, int sumValue){
        for(Integer item : cardSet){
            if(cardSet.contains(sumValue - item)){
                cardSet.remove(sumValue - item);
                cardSet.remove((item));
                return true;
            }
        }
        return false;
    }

    public boolean containedCardJoinWithRemained(HashSet<Integer> containedCardSet, HashSet<Integer> remainedCardSet, int sumValue){
        for(Integer item : containedCardSet){
            if(remainedCardSet.contains(sumValue - item)){
                remainedCardSet.remove(sumValue - item);
                containedCardSet.remove((item));
                return true;
            }
        }
        return false;
    }
}