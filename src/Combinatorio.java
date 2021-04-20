import java.util.ArrayList;

public class Combinatorio {


    private static ArrayList<Integer> chosenValue= new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> combinazioneEsiti= (ArrayList<ArrayList<Integer>>) SessionManager.getInstance().getSESSION().get("combinazioneEsiti");

    static void CombinationRepetitionUtil(int chosen[], int arr[],
                                          int index, int r, int start, int end) {
        // Since index has become r, current combination is
        // ready to be printed, print
        if (index == r) {
            chosenValue=new ArrayList<>();
            for (int i = 0; i < r; i++) {
                System.out.printf("%d ", arr[chosen[i]]);
                if(i<4)
                    chosenValue.add(arr[chosen[i]]);

            }
            combinazioneEsiti.add(chosenValue);

            System.out.printf("\n");
            return;
        }

        // One by one choose all elements (without considering
        // the fact whether element is already chosen or not)
        // and recur
        for (int i = start; i <= end; i++) {
            chosen[index] = i;
            CombinationRepetitionUtil(chosen, arr, index + 1,
                    r, i, end);

        }
        return;
    }

    // The main function that prints all combinations of size r
// in arr[] of size n with repitions. This function mainly
// uses CombinationRepetitionUtil()
    static void CombinationRepetition(int arr[], int n, int r) {
        // Allocate memory
        int chosen[] = new int[r + 1];

        // Call the recursice function
        CombinationRepetitionUtil(chosen, arr, 0, r, 0, n - 1);
    }

    static void CombinationRepetition( int r) {
        // Allocate memory
        int chosen[] = new int[r + 1];
        int arr[] = {1, 0, 2};
        // Call the recursice function
        CombinationRepetitionUtil(chosen, arr, 0, r, 0, 3 - 1);
    }
}
