package algorithm;

// 2024 카카오 겨울 인턴 코테 - 주사위 고르기 관련
// https://school.programmers.co.kr/learn/courses/30/lessons/258709
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};

        int target = 6;
        for(int j = 0; j < 7; j++){
            target = j;
            int[] result = binarySearch(arr,target , 0,arr.length,(int)Math.round((double) arr.length / 2));
//            for(int i = 0; i < 2; i++){
//                System.out.println(result[i]);
//            }
        }


    }

    // target 보다 작지만, 그 다음 인덱스는 target 보다 큰 인덱스를 찾는, 이진 탐색
    public static int[] binarySearch(int[] arr, int target, int lower, int upper, int curIdx){
        if(curIdx >= arr.length || arr[arr.length - 1] < target){
            return new int[]{ arr.length , curIdx};
        }
        if(curIdx < 0 || arr[0] >= target){
            return new int[]{ 0, curIdx};
        }

        int val = arr[curIdx];

        if(val >= target){
            return binarySearch(arr, target, lower,curIdx,(int)Math.floor( lower + (double)(curIdx - lower) / 2));
        }

        if(val < target){
            if (curIdx < arr.length - 1 && arr[curIdx + 1] >= target){
                return new int[]{curIdx + 1 ,curIdx};
            }

            return binarySearch(arr, target, curIdx,upper,(int) Math.floor(curIdx + (double)(upper - curIdx) / 2));
        }

        return new int[]{-1,-1};
    }
}
