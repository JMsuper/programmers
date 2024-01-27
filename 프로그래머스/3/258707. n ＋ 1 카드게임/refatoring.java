package com.example.mvctest.board.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Math.random;

public class TestClass2 {

    public static void main(String[] args) {
        int result = solution(4, new int[]{3, 6, 7, 2, 1, 10, 5, 9, 8, 12, 11, 4});
        System.out.println(result);

    }

    public static int solution(int coin, int[] cards){
        int totalRound = cards.length / 3;

        ArrayList<Integer>[] roundArray = new ArrayList[totalRound];
        int curRound = 0;
        int roundTicket = 0;

        HashMap<Integer, Integer> cardMap = new HashMap<>();

        for(int i = 0; i < cards.length; i++){
            cardMap.put(cards[i],i);
        }

        HashSet<Integer> checkedNumSet = new HashSet<>();
        for(int i = 0; i < cards.length; i++){
            int iNum = cards[i];
            int iRound = i < cards.length / 3 ? -1 : (i - cards.length / 3) / 2;

            if(checkedNumSet.contains(iNum)){
                continue;
            }
            checkedNumSet.add(iNum);

            int targetValue = cards.length + 1 - iNum;
            int targetIdx = cardMap.get(targetValue);
            int targetRound = targetIdx < cards.length / 3 ? -1 : (targetIdx - cards.length / 3) / 2;

            checkedNumSet.add(targetValue);

            if(iRound < 0 && targetRound < 0){
                roundTicket += 1;
                continue;
            }

            if(roundArray[targetRound] == null){
                roundArray[targetRound] = new ArrayList<>();
            }

            if(iRound >= 0){
                roundArray[targetRound].add(2);
            }else{
                roundArray[targetRound].add(1);
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

//테스트 1 〉	통과 (0.07ms, 80.5MB)
//테스트 2 〉	통과 (0.11ms, 92.7MB)
//테스트 3 〉	통과 (0.11ms, 72.8MB)
//테스트 4 〉	통과 (0.15ms, 76.7MB)
//테스트 5 〉	통과 (0.28ms, 77.3MB)
//테스트 6 〉	통과 (0.15ms, 73.4MB)
//테스트 7 〉	통과 (0.31ms, 85.6MB)
//테스트 8 〉	통과 (0.29ms, 76.2MB)
//테스트 9 〉	통과 (0.58ms, 75.7MB)
//테스트 10 〉	통과 (0.44ms, 76.1MB)
//테스트 11 〉	통과 (1.04ms, 74.3MB)
//테스트 12 〉	통과 (1.14ms, 77.2MB)
//테스트 13 〉	통과 (1.43ms, 74.7MB)
//테스트 14 〉	통과 (1.06ms, 70.4MB)
//테스트 15 〉	통과 (0.91ms, 75.8MB)
//테스트 16 〉	통과 (1.41ms, 79.8MB)
//테스트 17 〉	통과 (1.16ms, 71.1MB)
//테스트 18 〉	통과 (1.31ms, 73.1MB)
//테스트 19 〉	통과 (0.96ms, 79.4MB)
//테스트 20 〉	통과 (0.94ms, 77.4MB)
